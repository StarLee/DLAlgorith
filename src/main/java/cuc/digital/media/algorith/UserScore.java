package cuc.digital.media.algorith;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class UserScore {
	public UserScore(){
		
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
		Connection connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/lucence","root","lixing");
		this.conn=connection;
	}
	public  String detail(int ID) throws SQLException, IOException {
		PreparedStatement  stmt=this.conn.prepareStatement("select * from STU_RESUME where ID="+ID);
		stmt.execute();
		ResultSet rs=stmt.getResultSet();
		rs.first();
		String STU_ID=rs.getString("ID");
		String MAJOR=rs.getString("MAJOR");
		//String OCCUPATIOIN_TYPE=rs.getString("OCCUPATION_TYPE");
		String STU_ADDRESS=rs.getString("ADDRESS");
		//String WORK_TYPE=rs.getString("WORK_TYPE");
		String ORIGIN=rs.getString("ORIGIN");
		String POLITICS_STATUS=rs.getString("POLITICS_STATUS");
		String EDU_BACKGROUND=rs.getString("EDU_BACKGROUND");
		String LANGUAGE_ABILITY=rs.getString("LANGUAGE_ABILITY");
		StringBuilder builder=new StringBuilder();
		builder.append(STU_ID+" ");
		builder.append(MAJOR+" ");
		//builder.append(OCCUPATIOIN_TYPE+" ");
		builder.append(STU_ADDRESS+" ");
		builder.append(ORIGIN+" ");
		builder.append(POLITICS_STATUS+" ");
		builder.append(EDU_BACKGROUND+" ");
		builder.append(LANGUAGE_ABILITY+" ");
		//System.out.println(builder);
		return (builder.toString());
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
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException{
		
		UserScore score=new UserScore();
		String doc1 = score.detail(1000);
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
	}
	//public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException{
		//OcpScore ocpscore=new OcpScore();
		//ArrayList
		//System.out.println(ocpscore.setup(100));
	//}	
	
}
