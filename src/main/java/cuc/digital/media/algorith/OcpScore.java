package cuc.digital.media.algorith;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cuc.digital.application.Jdbc;

public class OcpScore {
	/*public OcpScore(){
		
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
	*/
	private Connection conn;
	
	/*public Connection getConn() {
		return conn;
	*/

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public  String detail(int ID) throws ClassNotFoundException, SQLException {
	
		PreparedStatement  stmt=Jdbc.openConn().prepareStatement("select * from OCCUPATION where ID="+ID);
		stmt.execute();
		ResultSet rs=stmt.getResultSet();
		rs.first();
		String OCCU_ID=rs.getString("ID");
		String OCCUPATION_NAME=rs.getString("OCCUPATION_NAME");
		OCCUPATION_NAME=OCCUPATION_NAME.replaceAll("/", "");
		OCCUPATION_NAME=OCCUPATION_NAME.replaceAll(" ", "");
		String OCCUPATION_TYPE=rs.getString("OCCUPATION_TYPE");
		OCCUPATION_TYPE=OCCUPATION_TYPE.replaceAll("/", "");
		OCCUPATION_TYPE=OCCUPATION_TYPE.replaceAll(" ", "");
		/*String WORK_ADDRESS=rs.getString("WORK_ADDRESS");
		WORK_ADDRESS=WORK_ADDRESS.replaceAll("/", "");
		WORK_ADDRESS=WORK_ADDRESS.replaceAll(" ", "");
		//String WORK_TYPE=rs.getString("WORK_TYPE");
		//String EDU_BACKGROUND=rs.getString("EDU_BACKGROUND");
		String COMPANY_FIELD=rs.getString("COMPANY_FIELD");
		COMPANY_FIELD=COMPANY_FIELD.replaceAll("/", "");
		COMPANY_FIELD=COMPANY_FIELD.replaceAll(" ", "");
		*/
		StringBuilder builder=new StringBuilder();
		//builder.append(OCCU_ID+" ");
		builder.append(OCCUPATION_NAME+" ");
		builder.append(OCCUPATION_TYPE+" ");
		//builder.append(WORK_ADDRESS+" ");
		//builder.append(EDU_BACKGROUND+" ");
		//builder.append(WORK_TYPE+" ");
		//builder.append(COMPANY_FIELD);
		//System.out.println(builder);
		String line = builder.toString();
		//line = line.replaceAll("/", "");
		//line = line.replaceAll(" ", "");
		//line = line.replaceAll(")", "）");
		//line = line.replaceAll("*", " ");
		//line = line.replaceAll("-", " ");
		return (line);
	}
	
	/*public static String otherOcp(int n) throws ClassNotFoundException, SQLException, IOException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/lucene","root","root");
		PreparedStatement  stmt=connection.prepareStatement("select * from OCCUPATION where ID="+n);
		stmt.execute();
		ResultSet rs=stmt.getResultSet();
		rs.first();
		//String OCCU_ID=rs.getString("ID");
		String OCCUPATIOIN_NAME=rs.getString("OCCUPATION_NAME");
		//String OCCUPATIOIN_TYPE=rs.getString("OCCUPATION_TYPE");
		String WORK_ADDRESS=rs.getString("EDU_BACKGROUND");
		//String WORK_TYPE=rs.getString("WORK_TYPE");
		String EDU_BACKGROUND=rs.getString("WORK_ADDRESS");
		String COMPANY_TYPE=rs.getString("COMPANY_FIELD");
		StringBuilder builder=new StringBuilder();
		//builder.append(OCCU_ID+" ");
		builder.append(OCCUPATIOIN_NAME+" ");
		//builder.append(OCCUPATIOIN_TYPE+" ");
		builder.append(WORK_ADDRESS+" ");
		builder.append(EDU_BACKGROUND+" ");
		//builder.append(WORK_TYPE+" ");
		builder.append(COMPANY_TYPE+" ");
		//System.out.println(builder);
		return (builder.toString());	
	}
	*/
	
	public static void main(String[] args) throws Throwable{
		OcpScore score = new OcpScore();
		score.setup();
		//Jdbc jdbc = new Jdbc();
		/*
		 * score.setConn(Jdbc.openConn());
		String doc1 = score.detail(2);
		List simList= new ArrayList();
		for(int n=1;n<=9000;n++) {
			String doc2 = score.detail(n); 
			double similarity=CosineSimilarAlgorithm.getSimilarity(doc1, doc2);
			//ArrayList可以改变元素个数
			simList.add(similarity);
		}
		//return simList.toArray();
		Collections.sort(simList,Collections.reverseOrder());
		System.out.println(simList);
		Jdbc.closeConn();
		*/
	}
	public void setup() throws ClassNotFoundException, SQLException {
		OcpScore score = new OcpScore();
		score.setConn(Jdbc.openConn());
		String doc1 = score.detail(100);
		System.out.println(doc1);
		/*List simList= new ArrayList();
		for(int n=1;n<=9000;n++) {
			String doc2 = score.detail(n); 
			double similarity=CosineSimilarAlgorithm.getSimilarity(doc1, doc2);
			//ArrayList可以改变元素个数
			simList.add(similarity);
		}
		//return simList.toArray();
		Collections.sort(simList,Collections.reverseOrder());
		System.out.println(simList);
		*/
		Jdbc.closeConn();
	}
	//public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException{
		//OcpScore ocpscore=new OcpScore();
		//ArrayList
		//System.out.println(ocpscore.setup(100));
	//}	
}
