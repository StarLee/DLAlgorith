package cuc.digital.transform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaticInfo {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/movielens","root","lixing");
		PreparedStatement  stmt=connection.prepareStatement("select * from ml_users");
		stmt.execute();
		ResultSet rs=stmt.getResultSet();
		File file=new File("D://StaticInfo.txt");
		file.createNewFile();
		FileWriter writer=new FileWriter(file);
		writer.append(new StaticInfoBean(0,0.0,"M","title").toString());
		while(rs.next())
		{
			int userId=rs.getInt("User_ID");
			int age=rs.getInt("Age");
			String gender=rs.getString("Gender");
			String occ=rs.getString("Occupation");
			StaticInfoBean bean=new StaticInfoBean(userId, age/100.0, gender, occ);
			System.out.print(bean.toString());
			writer.append(bean.toString());
			writer.flush();
		}
	}
}
