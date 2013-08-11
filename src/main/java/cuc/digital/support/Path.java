package cuc.digital.support;
/**
 * 主要是为了获取路径到classes下面，否则在test-classes下，拿不到resources中的数据
 * @author starlee
 *
 */
public class Path {

	public static String getApplicationPath(String path)
	{
		String pathL=Path.class.getResource("").getPath();
		StringBuilder strb=new StringBuilder();
		strb.append(pathL.substring(0,pathL.indexOf("/cuc/digital/support/")+1));
		strb.append(path);
		return strb.toString();
	}
	public static void main(String[] args)
	{
		System.out.println(Path.getApplicationPath(""));
	}
}
