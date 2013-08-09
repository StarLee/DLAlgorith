package cuc.digital.media.algorith;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Index_User {
	private String indexPath = "D:\\index_user";
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException
	{
		Index_User index=new Index_User();
		index.setup_U();
	}
	
	public void setup_U() throws IOException, ClassNotFoundException, SQLException
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
		//index(write,source);
		index_U(write);
		write.close();
	}
	public void index_U(IndexWriter writer) throws ClassNotFoundException, SQLException, IOException
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/lucene","root","root");
		PreparedStatement  stmt=connection.prepareStatement("select * from STU_RESUME");
		stmt.execute();
		ResultSet rs=stmt.getResultSet();
		while(rs.next())
		{
			Document document=new Document();
			Field idField=new StringField("ID", String.valueOf(rs.getInt("ID")), Store.YES);
			document.add(idField);
			String MAJOR=rs.getString("MAJOR");
			String STU_ADDRESS=rs.getString("ADDRESS");
			String ORIGIN=rs.getString("ORIGIN");
			String POLITICS_STATUS=rs.getString("POLITICS_STATUS");
			String EDU_BACKGROUND=rs.getString("EDU_BACKGROUND");
			String LANGUAGE_ABILITY=rs.getString("LANGUAGE_ABILITY");
			StringBuilder builder=new StringBuilder();
			builder.append(MAJOR+" ");
			builder.append(STU_ADDRESS+" ");
			builder.append(ORIGIN+" ");
			builder.append(POLITICS_STATUS+" ");
			builder.append(EDU_BACKGROUND+" ");
			builder.append(LANGUAGE_ABILITY+" ");
			Field stu_resume=new TextField("CONTENT", builder.toString(), Store.NO);
			document.add(stu_resume);
			writer.addDocument(document);
		}
	}
}
