package spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class YoudaoTranslate {

	private String url = "http://fanyi.youdao.com/openapi.do"; 
	private String keyfrom = "sldictionary-123";
	private String key = "1344542956";
	private String doctype = "xml";

	private String sendGet(String str) {

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
		buf.append(key);
		buf.append("&type=data&doctype=");
		buf.append(doctype);
		buf.append("&version=1.1&q=");
		buf.append(str);

		String param =buf.toString();

		String result = "";
		//BufferedReader in = null;
		
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

	public String getYouDaoValue(String str) {
		String result = null;
		String in1=null;
		String in2=null;
		
		// 发送GET请求翻译
		result = sendGet(str);

		// 处理XML中的值
		int re1 =result.indexOf("<errorCode>");
		int re2 =result.indexOf("</errorCode>");
		String in =result.substring(re1 + 11, re2);
		//System.out.println("===========翻译是否成功============"+ in);

		if (in.equals("0")) {
			//System.out.println("翻译正常");

			re1 =result.indexOf("<paragraph><![CDATA[");
			re2 =result.indexOf("]]></paragraph>");
			in =result.substring(re1 + 20, re2);
			//System.out.println("==========有道翻译================"+ in);
			in1=in;

			re1 =result.indexOf("<ex><![CDATA[");
			re2 =result.indexOf("]]></ex>");
			in =result.substring(re1 + 13, re2);
			//System.out.println("==========有道词典-网络释义================"+ in);
			in2=in;

		}/* else if (in.equals("20")){
			System.out.println("要翻译的文本过长");
			return"要翻译的文本过长";
		} else if (in.equals("30")){
			System.out.println("无法进行有效的翻译");
			return"无法进行有效的翻译";
		} else if (in.equals("40")){
			System.out.println("不支持的语言类型");
			return"不支持的语言类型";
		} else if (in.equals("50")){
			System.out.println("无效的key");
			return"无效的key";
		}*/

		result="";
		result+=in1;
		result+="\n";
		result+="有道-网络释义：";
		result+=in2;
		result+="\n";
		return result;
	}
}