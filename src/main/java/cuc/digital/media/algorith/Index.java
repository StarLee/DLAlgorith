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
public class Index
{
	private String indexPath = "D:\\index";
	private String sourcePath="D:\\files";
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException
	{
		Index index=new Index();
		//String doc1="";
		//String doc2="";
		//CosineSimilarAlgorithm.getSimilarity(doc1, doc2);
		index.setup();
	}
	
	public void setup() throws IOException, ClassNotFoundException, SQLException
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
		File source=new File(this.sourcePath);
		//index(write,source);
		index(write);
		write.close();
	}

	
	//对文件进行索引
	public void index(IndexWriter writer, File file) throws IOException
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
	 
	//对数据库表进行索引
	public void index(IndexWriter writer) throws ClassNotFoundException, SQLException, IOException
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/lucence","root","lixing");
		PreparedStatement  stmt=connection.prepareStatement("select * from OCCUPATION");
		stmt.execute();
		ResultSet rs=stmt.getResultSet();
		while(rs.next())
		{
			Document document=new Document();
			Field idField=new StringField("ID", String.valueOf(rs.getInt("ID")), Store.YES);
			document.add(idField);
			String OCCUPATIOIN_NAME=rs.getString("OCCUPATION_NAME");
			String OCCUPATIOIN_TYPE=rs.getString("OCCUPATION_TYPE");
			String WORK_ADDRESS=rs.getString("EDU_BACKGROUND");
			String WORK_TYPE=rs.getString("WORK_TYPE");
			String EDU_BACKGROUND=rs.getString("WORK_ADDRESS");
			String COMPANY_TYPE=rs.getString("COMPANY_FIELD");
			StringBuilder builder=new StringBuilder();
			builder.append(OCCUPATIOIN_NAME+" ");
			builder.append(OCCUPATIOIN_TYPE);
			builder.append(WORK_ADDRESS);
			builder.append(EDU_BACKGROUND);
			builder.append(WORK_TYPE);
			builder.append(COMPANY_TYPE);
			Field occupation=new TextField("CONTENT", builder.toString(), Store.NO);
			document.add(occupation);
			writer.addDocument(document);
		}
	}
}
