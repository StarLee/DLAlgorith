package cuc.digital.transform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cuc.digital.bean.Feature;
import cuc.digital.bean.RateRecord;
import cuc.digital.bean.Record;

public class CollectClusterFeature {

	private Map<RateRecord, List<RateRecord>> map;
	private Map<String, Feature> map_feature = new HashMap<String, Feature>();

	// 进来的是每个聚类的特征提取
	public void collect(List<Record> listRecord) {
		CollectClusterItem collectClusterItem = CollectClusterItem
				.getInstance();
		Iterator<Record> reit = listRecord.iterator();
		map = new HashMap<RateRecord, List<RateRecord>>();
		while (reit.hasNext()) {
			Record re = reit.next();
			try {
				List<RateRecord> list = collectClusterItem.collect(re.getId());
				for (RateRecord record : list) {
					if (record.getRate() < 3) {
						continue;// 小于3分的直接舍弃，表不喜欢，这个地方其它可以优化的，目前只是简单处理
					}
					List<RateRecord> rateRecords;
					if (map.containsKey(record)) {
						rateRecords = map.get(record);
						rateRecords.add(record);
					} else {
						rateRecords = new ArrayList<RateRecord>();
						rateRecords.add(record);
						map.put(record, rateRecords);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		collectClusterItem.close();
		collectFeature();
	}

	private void collectFeature() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/movielens",
							"root", "lixing");
			Set<RateRecord> keySet = map.keySet();

			for (RateRecord record : keySet) {
				stmt = connection
						.prepareStatement("select * from ml_movie where Movie_ID=?");
				ResultSetMetaData metaData = stmt.getMetaData();

				stmt.setInt(1, record.getMoive_id());
				stmt.execute();
				rs = stmt.getResultSet();
				if (rs.first()) {
					for (int n = 6; n <= 19; n++) {
						int i = rs.getInt(n);
						if (i == 1) {
							String name = metaData.getColumnName(n);
							if (map_feature.containsKey(name)) {
								Feature feature = map_feature.get(name);
								int amount = feature.getAmount();
								List<RateRecord> ratingRecord=map.get(record);
								feature.setAmount(amount+ratingRecord.size());
								double rating = feature.getRating();
								for(RateRecord rate:ratingRecord)
								{
									rating=rating + rate.getRate();
								}
								feature.setRating(rating);
							} else {
								Feature feature = new Feature();
								List<RateRecord> ratingRecord=map.get(record);
								feature.setAmount(ratingRecord.size());
								double rating = feature.getRating();
								for(RateRecord rate:ratingRecord)
								{
									rating=rating + rate.getRate();
								}
								feature.setRating(rating);
								feature.setName(name);
								map_feature.put(name, feature);
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Map<String, Feature> getMap_feature() {
		return map_feature;
	}

}
