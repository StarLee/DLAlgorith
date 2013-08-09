package cuc.digital.media.algorith;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
public class Search{
	private String indexPath = "D:\\index_occupation";
	public List<String> setup() throws IOException, ClassNotFoundException, SQLException, ParseException{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
		IndexSearcher search=new IndexSearcher(reader);
		Analyzer analyzer=new CJKAnalyzer(Version.LUCENE_42);
		//String[] fields = {"OCCUPATION_NAME","OCCUPATION_TYPE","WORK_ADDRESS","COMPANY_FIELD"};
		//构造布尔查询
		//BooleanClause.Occur[] flags = new BooleanClause.Occur[] {
		//	     BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
		BooleanQuery bq = new BooleanQuery();
		
		QueryParser parser1 = new QueryParser(Version.LUCENE_42, "OCCUPATION_NAME",analyzer);  
	    Query query1 = parser1 .parse("前端工程师"); 
	    bq.add(query1,BooleanClause.Occur.SHOULD); 
	    
	    QueryParser parser2 = new QueryParser(Version.LUCENE_42, "OCCUPATION_TYPE",analyzer);  
	    Query query2 = parser2 .parse("111"); 
	    bq.add(query2,BooleanClause.Occur.SHOULD);
	    
	    QueryParser parser3 = new QueryParser(Version.LUCENE_42, "WORK_ADDRESS",analyzer);  
	    Query query3 = parser3 .parse("上海市"); 
	    bq.add(query3,BooleanClause.Occur.SHOULD);
	    
	    QueryParser parser4 = new QueryParser(Version.LUCENE_42, "COMPANY_FIELD",analyzer);  
	    Query query4 = parser4 .parse("互联网"); 
	    bq.add(query4,BooleanClause.Occur.SHOULD); 
		/*TermQuery query1 = new TermQuery(new Term("OCCUPATION_NAME", "计算机"));
		TermQuery query2 = new TermQuery(new Term("OCCUPATION_TYPE", "aaa"));
		TermQuery query3 = new TermQuery(new Term("WORK_ADDRESS", "北京市"));
		TermQuery query4 = new TermQuery(new Term("COMPANY_FIELD", "计算机"));
		bq.add(query1, BooleanClause.Occur.SHOULD);
		bq.add(query2, BooleanClause.Occur.SHOULD);
		bq.add(query3, BooleanClause.Occur.SHOULD);
		bq.add(query4, BooleanClause.Occur.SHOULD);
		*/
		//QueryParser parser = new QueryParser(Version.LUCENE_42,"OCCUPATION_NAME",analyzer);
		//调用类UserScore的detail方法
		//OcpScore score=new OcpScore();
		//String line = score.detail(ID);
		//System.out.println(line);
		//String line = "计算机 北京 硕士 中国传媒大学";
		//Query query=parser.parse(line);
		//System.out.println(bq);
		TopDocs top=search.search(bq,15);
		ScoreDoc[] docs=top.scoreDocs;
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/ucareer","root","lixing");
		connection.setAutoCommit(false);
		List<String> ocpList = new ArrayList<String>();
		for(ScoreDoc doc:docs)
		{
			Document document=search.doc(doc.doc);
			//System.out.println("******");
			//System.out.println(document.get("ID")+":"+doc.score );
			ocpList.add(document.get("ID"));
			//PreparedStatement  stmt=connection.prepareStatement("insert into PERSONAL_OCCUPATION_REC(USER_ID,OCCUPATION_ID) values(?,?)");
			//stmt.setInt(1, n);
			//stmt.setInt(1, ID);
			//stmt.setString(2,document.get("ID"));
			//stmt.execute();
			//n++;
		}
		return ocpList;
	}
	
	/*public void insert() throws ClassNotFoundException, SQLException, IOException{
		
		PreparedStatement  stmt=connection.prepareStatement("insert into OCCUPATION_STORE(ID,USER_ID,OCCUPATION_ID) values(?,?,?)");
		
		stmt.execute();
		connection.commit();
		connection.close();
		}
	*/
	public static void main(String[] args) {
		Search search=new Search();
		//Scanner in=new Scanner(System.in);
		//int ID = in.nextInt();
		try {
			search.setup();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//in.close();
		
	}
}
