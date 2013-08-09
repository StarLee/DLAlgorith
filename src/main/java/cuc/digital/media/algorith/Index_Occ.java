package cuc.digital.media.algorith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


/**
 * Hello world!
 * 
 */
public class Index_Occ
{
	private String indexPath = "D:\\index_occupation";
	//private String sourcePath="D:\\files";
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException
	{
		Index_Occ index=new Index_Occ();
		//String doc1="";
		//String doc2="";
		//CosineSimilarAlgorithm.getSimilarity(doc1, doc2);
		index.setup_O();
	}
	
	public void setup_O() throws IOException, ClassNotFoundException, SQLException
	{
		// FSDirectory.open方法获取真实文件在文件系统的存储路径
		Directory directory = FSDirectory.open(new File(indexPath));
		//是否可以用SmartChineseAnalyzer：使用基于词典的方式将中文文本转换为词语片段
		Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_42);
		//Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_42);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_42,
				analyzer);
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter write = new IndexWriter(directory, iwc);
		//File source=new File(this.sourcePath);
		//index(write,source);
		index_O(write);
		write.close();
	}

	
	//对文件进行索引
	/*
	 * public void index(IndexWriter writer, File file) throws IOException
	{
		
		if (file.canRead())
		{
			if (file.isDirectory())
			{
				File[] files = file.listFiles();
				for (File f : files)
				{
					index(writer, f);
				}
			} else
			{
				FileInputStream fis = new FileInputStream(file);
				try
				{
					Document document = new Document();
					System.out.println(file.getPath());
					Field pathField = new StringField("PATH", file.getPath(),
							Store.YES);
					document.add(pathField);
					Field contentField = new TextField("CONTENT",
							new BufferedReader(new InputStreamReader(fis,
									"GBK")));
					document.add(contentField);
					writer.addDocument(document);
				} finally
				{
					fis.close();
				}
			}
		}
	}
	*/
	 
	//对数据库表进行索引
	public void index_O(IndexWriter writer) throws ClassNotFoundException, SQLException, IOException
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/lucene","root","root");
		PreparedStatement  stmt=connection.prepareStatement("select * from OCCUPATION");
		stmt.execute();
		ResultSet rs=stmt.getResultSet();
		while(rs.next())
		{
			Document document=new Document();
			Field idField=new StringField("ID", String.valueOf(rs.getInt("ID")), Store.YES);
			document.add(idField);
			/*
			 * String OCCUPATIOIN_NAME=rs.getString("OCCUPATION_NAME");
			String OCCUPATIOIN_TYPE=rs.getString("OCCUPATION_TYPE");
			String WORK_ADDRESS=rs.getString("WORK_ADDRESS");
			String WORK_TYPE=rs.getString("WORK_TYPE");
			String EDU_BACKGROUND=rs.getString("EDU_BACKGROUND");
			String COMPANY_FIELD=rs.getString("COMPANY_FIELD");
			StringBuilder builder=new StringBuilder();
			builder.append(OCCUPATIOIN_NAME+" ");
			builder.append(OCCUPATIOIN_TYPE);
			builder.append(WORK_ADDRESS);
			builder.append(WORK_TYPE);
			builder.append(EDU_BACKGROUND);			
			builder.append(COMPANY_FIELD);
			Field occupation=new TextField("CONTENT", builder.toString(), Store.NO);
			document.add(occupation);
			
			*/
			Field occupation_name=new TextField("OCCUPATION_NAME", String.valueOf(rs.getString("OCCUPATION_NAME")), Store.NO);
			Field occupation_type=new TextField("OCCUPATION_TYPE", String.valueOf(rs.getString("OCCUPATION_TYPE")), Store.NO);
			Field work_address=new TextField("WORK_ADDRESS", String.valueOf(rs.getString("WORK_ADDRESS")), Store.NO);
			//Field work_type=new StringField("WORK_TYPE", String.valueOf(rs.getString("WORK_TYPE")), Store.NO);
			//Field edu_background=new StringField("EDU_BACKGROUND", String.valueOf(rs.getString("EDU_BACKGROUND")), Store.NO);
			Field company_field=new TextField("COMPANY_FIELD", String.valueOf(rs.getString("COMPANY_FIELD")), Store.NO);
			occupation_name.setBoost(1.5F);
			document.add(occupation_name);
			occupation_type.setBoost(1.0F);
			document.add(occupation_type);
			work_address.setBoost(2.8F);
			document.add(work_address);
			//document.add(work_type);
			//document.add(edu_background);
			company_field.setBoost(1.2F);
			document.add(company_field);
			
			writer.addDocument(document);
		}
	}
}
