package cuc.digital.transform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cuc.digital.bean.RateRecord;

public class CollectClusterItem {
	private static CollectClusterItem collect;
	private Connection connection;

	private CollectClusterItem() {
		try {
			setup();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static CollectClusterItem getInstance() {
		return collect = new CollectClusterItem();
	}

	public void setup() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/movielens", "root", "lixing");
	}

	public List<RateRecord> collect(int userID) throws SQLException {
		List<RateRecord> list = new ArrayList<RateRecord>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection
					.prepareStatement("select * from ml_data where User_ID=?");
			stmt.setInt(1, userID);
			stmt.execute();
			rs = stmt.getResultSet();

			while (rs.next()) {
				RateRecord record = new RateRecord();
				record.setUser_id(rs.getInt("User_ID"));
				record.setMoive_id(rs.getInt("Item_ID"));
				record.setRate(rs.getDouble("Raiting"));
				list.add(record);
			}
		} finally {
			if(rs!=null)
			{
				rs.close();
			}
			if(stmt!=null)
			{
				stmt.close();
			}
		}
		return list;
	}
	
	public void close()
	{
		if(connection!=null)
		{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
