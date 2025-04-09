package util;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
	public static String getPropertyString(String filename) throws IOException{
		String constr=null;
		Properties prop=new Properties();
		FileInputStream fis=new FileInputStream(filename);
		prop.load(fis);
		String user=prop.getProperty("user");
		String password=prop.getProperty("password");
		String port=prop.getProperty("port");
		String protocol=prop.getProperty("protocol");
		String system=prop.getProperty("system");
		String database=prop.getProperty("database");
		constr=protocol+"//"+system+":"+port+"/"+database+"?"+"user="+user+"&password="+password;
		return constr;  	  
  } 
}
