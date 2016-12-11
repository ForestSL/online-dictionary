
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JinshanTranslate {
	
	private static String translate(String str) {

		String url = "http://dict-co.iciba.com/api/dictionary.php?w="+str+"&key=971806797407009AC831EDD57B55A3BE"; 
		
		// �����UTF-8
		try {
			str =URLEncoder.encode(str, "utf-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String result="";
		URL realUrl;
		try {
			realUrl = new URL(url);

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
		String result = "";
		String tstr=translate(str);
		//System.out.println(tstr);
		Pattern p = Pattern.compile("<acceptation>(.*)</acceptation>");
		Matcher m = p.matcher(tstr);
		while(m.find())
		{
			result+=m.group();
		}
		//System.out.println(result);
		String temp="��ɽ��";
		if(result.equals(""))
			temp+="�����ڸõ���";
		else
			temp+=result.substring(result.indexOf("</pos><acceptation>")+14, result.indexOf("</acceptation>"));
		return temp;
	}
}