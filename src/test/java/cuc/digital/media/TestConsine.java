package cuc.digital.media;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.exceptions.AlreadyExistsException;

import cuc.digital.bean.ClusterFeature;
import cuc.digital.bean.Feature;
import cuc.digital.bean.Record;
import cuc.digital.bean.RecordMeta;
import cuc.digital.media.algorith.Cosine;
import cuc.digital.media.algorith.Kmeans;
import cuc.digital.support.Path;
import cuc.digital.transform.CollectClusterFeature;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestConsine {

	private Cluster cluster ;

	private Session session ;
	
	@Before
	public void setup()
	{
		cluster = Cluster.builder().addContactPoint("localhost")
				.build();
		session = cluster.connect();
		
	}
	
	@Test
	@Ignore
	public void testPath() {
		System.out.println(Path.getApplicationPath(""));
	}

	@Test
	// @Ignore
	public void testCosine() throws IOException {
		// String re_one="1;0.24;1.0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0";
		// String
		// re_two="16;0.21;1.0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0";
		Cosine cosine = new Cosine();
		// System.out.println(re_one.substring(re_one.indexOf(";")+1));
		// double[]
		// d1=cosine.str2Double(re_one.substring(re_one.indexOf(";")+1));
		// double[]
		// d2=cosine.str2Double(re_two.substring(re_two.indexOf(";")+1));
		// System.out.println(cosine.getCosine(d1, d2));
		// System.out.println(cosine.getCosine(cosine.str2Record(re_one).getVector(),
		// cosine.str2Record(re_two).getVector()));

		// String
		// path=Cosine.class.getResource("/").toString()+"/../classes/data/StaticInfo.txt";
		String path = Path.getApplicationPath("/data/StaticInfo.txt");
		File file = new File(path);
		BufferedReader ra = new BufferedReader(new FileReader(file));
		List<Record> records = new ArrayList<Record>();
		String line = null;
		int i = 0;
		while ((line = ra.readLine()) != null) {
			records.add(cosine.str2Record(line));
			i++;
		}
		RecordMeta meta = new RecordMeta();
		meta.setRecords(i);
		meta.setDimension(23);
		Kmeans means = new Kmeans();
		means.setK(6);
		means.setMeta(meta);
		means.setRecords(records);
		means.doRun();

		List<Record>[] all = means.getResult();
		Record[] centroid = means.getCentoids();

		for (int n = 0; n < all.length; n++) {
			Result result=new Result();
			result.setId(n+1);
			result.setCentroid(printCentroid(centroid[n], all[n].size()));
			result.setFeatures(printFeature(all[n]));
			insertResult(result);
			System.out.println("------------------------------------");
		}
		/*
		 * while(reit.hasNext()) { Record re=reit.next();
		 * System.out.print(re.getId()+"(");
		 * for(i=0;i<re.getVector().length;i++) {
		 * System.out.print(re.getVector()[i]+";"); } System.out.println(")"); }
		 */
	}

	private String printFeature(List<Record> firstCluster) {

		int size = firstCluster.size();

		CollectClusterFeature feature = new CollectClusterFeature();
		feature.collect(firstCluster);

		Map<String, Feature> map = feature.getMap_feature();
		Set<Entry<String, Feature>> set = map.entrySet();
		List<ClusterFeature> clusterFeatureList = new ArrayList<ClusterFeature>();
		for (Entry<String, Feature> entry : set) {
			String key = entry.getKey();
			Feature features = entry.getValue();
			ClusterFeature f = new ClusterFeature();
			f.setName(key);
			f.setWeight((double) features.getAmount() / (double) size);
			// System.out.println(key+":"+features.getAmount()/size+":"+features.getRating()/features.getAmount());
			clusterFeatureList.add(f);
		}
		Collections.sort(clusterFeatureList, new Comparator<ClusterFeature>() {

			@Override
			public int compare(ClusterFeature o1, ClusterFeature o2) {
				if (o1.getWeight() > o2.getWeight())
					return -1;
				else if (o1.getWeight() < o2.getWeight())
					return 1;
				return 0;
			}
		});
		// 输出结果
		StringBuilder sb=new StringBuilder();
		for (ClusterFeature cf : clusterFeatureList) {
			//System.out.println(cf.getName() + ":" + cf.getWeight());
			sb.append(cf.getName() + ":" + cf.getWeight());
			sb.append(";");
		}
		return sb.toString();
	}

	public static void connect_cassandra() {
		Cluster cluster = Cluster.builder().addContactPoint("localhost")
				.build();

		Session session = cluster.connect();
		String keySpace = "CREATE KEYSPACE feature WITH replication "
				+ "= {'class':'SimpleStrategy', 'replication_factor':3};";
		try {
			ResultSet rs = session.execute(keySpace);
			
		} catch (AlreadyExistsException ex) {
			try
			{
				session.execute("create table feature.all (id int primary key,centroid text,features text)");
			}
			catch(AlreadyExistsException e)
			{
				
			}
		}

		cluster.shutdown();
	}

	private void insertResult(Result result)
	{
		PreparedStatement statement=session.prepare("insert into feature.all(id,centroid,features) values(?,?,?)");
		BoundStatement bound=new BoundStatement(statement);
		bound.setInt(0, result.getId());
		bound.setString(1, result.getCentroid());
		bound.setString(2, result.getFeatures());
		session.executeAsync(bound);
	}
	
	
	private String printCentroid(Record centroid, int size) {
		System.out.println("数量:" + size + "中心如下");
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < centroid.getVector().length; i++) {
			sb.append(centroid.getVector()[i]);
			sb.append(":");
			if (i == centroid.getVector().length) {
				
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		connect_cassandra();
	}

}
