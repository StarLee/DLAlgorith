package cuc.digital.application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cuc.digital.media.algorith.OcpScore;
import cuc.digital.media.algorith.Search;
import cuc.digital.media.algorith.UserScore;

public class SearchUser {
	private String indexPath = "D:\\index_user";
	public SearchUser(){
		
		try {
			get();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
}
	private Connection conn;
	public  void get() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/lucene","root","root");
		this.conn=connection;
	}
	//public int getoccupation(){
		
	//}
	public void setup(int ID) throws SQLException,ClassNotFoundException,IOException, ParseException{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
		IndexSearcher search=new IndexSearcher(reader);
		Analyzer analyzer=new CJKAnalyzer(Version.LUCENE_42);
		QueryParser parser = new QueryParser(Version.LUCENE_42, "CONTENT", analyzer);
		//调用类UserScore的detail方法
		OcpScore score=new OcpScore();
		String line = score.detail(ID);
		Query query=parser.parse(line);
		TopDocs top=search.search(query, 20);
		ScoreDoc[] docs=top.scoreDocs;
		this.conn.setAutoCommit(false);
		for(ScoreDoc doc:docs)
		{
			Document document=search.doc(doc.doc);
			System.out.println(document.get("ID")+":"+doc.score);
			PreparedStatement  stmt=this.conn.prepareStatement("insert into APPLY(UID,OID) values(?,?)");
			stmt.setString(1, document.get("ID"));
			stmt.setInt(2, ID);
			
			stmt.execute();
			
		}
				
		this.conn.commit();
		this.conn.close();
	}
	
	/*public void insert() throws ClassNotFoundException, SQLException, IOException{
		
		PreparedStatement  stmt=connection.prepareStatement("insert into OCCUPATION_STORE(ID,USER_ID,OCCUPATION_ID) values(?,?,?)");
		
		stmt.execute();
		connection.commit();
		connection.close();
		}
	*/
	public static void main(String[] args) throws SQLException,ClassNotFoundException,IOException, ParseException
	{
		SearchUser search=new SearchUser();
		Scanner in=new Scanner(System.in);
		int ID = in.nextInt();
		search.setup(ID);
		in.close();
		
	}

}
