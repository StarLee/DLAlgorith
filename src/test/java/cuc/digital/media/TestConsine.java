package cuc.digital.media;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import cuc.digital.media.algorith.Cosine;
import cuc.digital.media.algorith.Kmeans;
import cuc.digital.media.algorith.Record;
import cuc.digital.media.algorith.RecordMeta;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestConsine {

	@Test
	//@Ignore
	public void testPath()
	{
		
		System.out.println(Cosine.class.getResource("/"));
	}
	
	@Test
	public void testCosine() throws IOException
	{
		//String re_one="1;0.24;1.0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0";
				//String re_two="16;0.21;1.0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0";
				Cosine cosine=new Cosine();
				//System.out.println(re_one.substring(re_one.indexOf(";")+1));
				//double[] d1=cosine.str2Double(re_one.substring(re_one.indexOf(";")+1));
				//double[] d2=cosine.str2Double(re_two.substring(re_two.indexOf(";")+1));
				//System.out.println(cosine.getCosine(d1, d2));
				//System.out.println(cosine.getCosine(cosine.str2Record(re_one).getVector(), cosine.str2Record(re_two).getVector()));
				
				//String path=Cosine.class.getResource("/").toString()+"/../classes/data/StaticInfo.txt";
				String path="/home/starlee/workspace/algorith/target/classes/data/StaticInfo.txt";
				File file=new File(path);
				BufferedReader ra=new BufferedReader(new FileReader(file));
				List<Record> records=new ArrayList<Record>();
				String line=null;
				int i=0;
				while((line=ra.readLine())!=null)
				{
					records.add(cosine.str2Record(line));
					i++;
				}
				RecordMeta meta=new RecordMeta();
				meta.setRecords(i);
				meta.setDimension(23);
				Kmeans means=new Kmeans();
				means.setK(6);
				means.setMeta(meta);
				means.setRecords(records);
				means.doRun();
				
				List<Record>[] all=means.getResult();
				Iterator<Record> reit=all[0].iterator();//以下只是测试结果
				while(reit.hasNext())
				{
					Record re=reit.next();
					System.out.print(re.getId()+"(");
					for(i=0;i<re.getVector().length;i++)
					{
						System.out.print(re.getVector()[i]+";");
					}
					System.out.println(")");
				}
	}
}
