package Server;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class BaiduTranslate{
	
	private static final String UTF8 = "utf-8";
	private static final String appId = "20161121000032475";
	private static final String token = "5fdYaD6XQrR3qCX62Ltj";
	private static final String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";

	private static final Random random = new Random();
	
	public String translate(String q, String from, String to) throws Exception{
		//����md5����
		int salt = random.nextInt(10000);
		
		// ��appId+Դ��+�����+token����md5ֵ
		StringBuilder md5String = new StringBuilder();
		md5String.append(appId).append(q).append(salt).append(token);
		String md5 = DigestUtils.md5Hex(md5String.toString());

		//ʹ��Post��ʽ����װURL��ַ����
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
		nvps.add(new BasicNameValuePair("q", q));  
		nvps.add(new BasicNameValuePair("from", from));  
		nvps.add(new BasicNameValuePair("to", to));  
		nvps.add(new BasicNameValuePair("appid", appId));  
		nvps.add(new BasicNameValuePair("salt", String.valueOf(salt)));  
		nvps.add(new BasicNameValuePair("sign", md5));  
		httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));  

		//����httpclient���ӣ���ִ��
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    CloseableHttpResponse response = httpclient.execute(httpost);//����װ�Ĳ�����ȡ����ֵ
	    
	    //���ڷ���ʵ����н���
		HttpEntity entity = response.getEntity();
		InputStream returnStream = entity.getContent();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(returnStream, UTF8));
		StringBuilder result = new StringBuilder();
		String str = null;
		while ((str = reader.readLine()) != null) {
			result.append(str).append("\n");
		}
		
		//ת��Ϊjson����ע��Json������jar����ѡ����
		JSONObject resultJson = new JSONObject(result.toString());

		//���������д�����󣬱�ʾ��ʧ�ܷ���Ϊnull
		try {
			String error_code = resultJson.getString("error_code");
			if (error_code != null) {
				//System.out.println("�������:" + error_code);
				//System.out.println("������Ϣ:" + resultJson.getString("error_msg"));
				//return null;
				return "���ҳ��������ڸõ�������";
			}
		} catch (Exception e) {}
		
		//��ȡ���ط�����
		JSONArray array = (JSONArray) resultJson.get("trans_result");
		JSONObject dst = (JSONObject) array.get(0);
		String text = dst.getString("dst");
		text = URLDecoder.decode(text, UTF8);

		return text;
	}
	
	public static  String translateToZh(String q){
		ApplicationContext container=new FileSystemXmlApplicationContext("src//spring//resource//baidu.xml");
		BaiduTranslate baidu = (BaiduTranslate)container.getBean("baidu");
		
		String result = "";
		try {
			result += baidu.translate(q, "en", "zh");//������Ӣ��
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result.equals(q))
			return "�ٶȣ������ڸõ���";
		else
		{
			String result1="�ٶȣ�";
			result1+=result;
			return result1;
		}
	}
}
