package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class YoudaoTranslate {

	private static String url = "http://fanyi.youdao.com/openapi.do"; 
	private static String keyfrom = "sldictionary-123";
	private static String APIkey = "1344542956";
	private static String doctype = "xml";

	private static String translate(String str) {

		// �����UTF-8
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

			//�򿪺�URL֮�������
			try {
				URLConnection conn = realUrl.openConnection();
				conn.setReadTimeout(5*1000);
			    //conn.setRequestProperty("accept", "");
			    BufferedReader bufr=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));//�����ļ��������ı��룬��url�ı���һ��
			    String lines="";
		        while ((lines=bufr.readLine())!=null) {
		        	result+=lines;
			   }
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return result;
	}

	public static String translateToZh(String str) {
		String result1 = "";
		String in1=null;
		String in2=null;
		
		// ����GET������
		String result = translate(str);//���ֽ�
		//System.out.println(result);

		// ����XML�е�ֵ
		int re1 =result.indexOf("<errorCode>");
		int re2 =result.indexOf("</errorCode>");
		String in =result.substring(re1 + 11, re2);

		if (in.equals("0")) {

			re1 =result.indexOf("<paragraph><![CDATA[");
			re2 =result.indexOf("]]></paragraph>");
			in =result.substring(re1 + 20, re2);
			in1=in;//����
			result1+=in1;

			/*
			re1 =result.indexOf("<ex><![CDATA[");
			re2 =result.indexOf("]]></ex>");
			in =result.substring(re1 + 13, re2);
			in2=in;//��������
			result1+=in2;*/

		} else if (in.equals("20")){
			result1+="Ҫ������ı�����";
		} else if (in.equals("30")){
			result1+="�޷�������Ч�ķ���";
		} else if (in.equals("40")){
			result1+="��֧�ֵ���������";
		} else if (in.equals("50")){
			result1+="��Ч��key";
		} else if (in.equals("60")){
			result1+="�����ڸõ���";
		}

		if(result1.equals(str))
			return "�е��������ڸõ���";
		else
		{
			String result2="�е���";
			result2+=result1;
			return result2;
		}
	}
}