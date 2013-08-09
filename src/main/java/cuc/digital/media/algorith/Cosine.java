package cuc.digital.media.algorith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cosine {
	public double getCosine(double[] item_one,double[] item_two)
	{
		int length=item_one.length;
		if(length>0&&(item_one.length==item_two.length))
		{
			double denominator=0;
			double sqItem_one=0;
			double sqItem_two=0;
			for(int i=0;i<length;i++)
			{
				sqItem_one+=item_one[i]*item_one[i];
				sqItem_two+=item_two[i]*item_two[i];
				denominator+=item_one[i]*item_two[i];
			}
			double result=denominator/Math.sqrt(sqItem_one*sqItem_two);
			return result;
		}
		else
		{
			throw new RuntimeException("无法计算,length:"+length);
		}
	}
	public static void main(String[] args) throws IOException {
		//String re_one="1;0.24;1.0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0";
		//String re_two="16;0.21;1.0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0";
		Cosine cosine=new Cosine();
		//System.out.println(re_one.substring(re_one.indexOf(";")+1));
		//double[] d1=cosine.str2Double(re_one.substring(re_one.indexOf(";")+1));
		//double[] d2=cosine.str2Double(re_two.substring(re_two.indexOf(";")+1));
		//System.out.println(cosine.getCosine(d1, d2));
		//System.out.println(cosine.getCosine(cosine.str2Record(re_one).getVector(), cosine.str2Record(re_two).getVector()));
		
		
		File file=new File("D://StaticInfo.txt");
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

	private Record str2Record(String str)
	{
		Record record=new Record();
		String[] st=str.split(";");
		record.setId(Integer.parseInt(st[0]));
		record.setVector(str2Double(str.substring(str.indexOf(";")+1)));
		return record;
	}
	
	private double[] str2Double(String str) {
		
		String[] res=str.split(";");
		int i=0;
		double[] red=new double[res.length];
		for(;i<res.length;i++)
		{
			red[i]=Double.parseDouble(res[i]);
		}
		return red;
	}
}
