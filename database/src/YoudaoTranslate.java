
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;

public class YoudaoTranslate {//解析xml格式

	private static String url = "http://fanyi.youdao.com/openapi.do"; 
	private static String keyfrom = "sldictionary-123";
	private static String APIkey = "1344542956";
	private static String doctype = "xml";

	private static String translate(String str) {

		// 编码成UTF-8
		try {
			str =URLEncoder.encode(str, "utf-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		StringBuffer buf = new StringBuffer();
		buf.append("keyfrom=");
		buf.append(keyfrom);
		buf.append("&key=");
		buf.append(APIkey);
		buf.append("&type=data&doctype=");
		buf.append(doctype);
		buf.append("&version=1.1&q=");
		buf.append(str);

		String param =buf.toString();
		String result="";
				
		String urlName = url + "?" + param;
		URL realUrl;
		try {
			realUrl = new URL(urlName);

			//打开和URL之间的连接
			try {
				URLConnection conn = realUrl.openConnection();
				conn.setReadTimeout(5*1000);
			    //conn.setRequestProperty("accept", "");
			    BufferedReader bufr=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));//设置文件输入流的编码，和url的编码一致
			    String lines="";
		        while ((lines=bufr.readLine())!=null) {
		        	result+=lines;
			   }
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	public static String translateToZh(String str) {
		String result1 = "";
		String in1=null;
		String in2=null;
		
		// 发送GET请求翻译
		String result = translate(str);//待分解
		//System.out.println(result);
		//return result;

		// 处理XML中的值
		int re1 =result.indexOf("<errorCode>");
		int re2 =result.indexOf("</errorCode>");
		String in =result.substring(re1 + 11, re2);

		if (in.equals("0")) {

			re1 =result.indexOf("<paragraph><![CDATA[");
			re2 =result.indexOf("]]></paragraph>");
			in =result.substring(re1 + 20, re2);
			in1=in;//翻译
			result1+=in1;

			if(!result1.equals(str))
			{
			re1 =result.indexOf("<ex><![CDATA[");
			re2 =result.indexOf("]]></ex>");
			//if((re1+13)==null)
			try {
				in =result.substring(re1 + 13, re2);
				in2=in;//网络释义
				result1+=in2;
			} catch (Exception e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			}

		} else if (in.equals("20")){
			result1+="要翻译的文本过长";
		} else if (in.equals("30")){
			result1+="无法进行有效的翻译";
		} else if (in.equals("40")){
			result1+="不支持的语言类型";
		} else if (in.equals("50")){
			result1+="无效的key";
		} else if (in.equals("60")){
			result1+="不存在该单词";
		}

		if(result1.equals(str))
			return "有道：不存在该单词";
		else
		{
			String result2="有道：";
			result2+=result1;
			return result2;
		}
	}
}