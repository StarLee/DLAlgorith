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

	public Record str2Record(String str)
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
