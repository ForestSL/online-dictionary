package spring;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws Exception {
		Scanner input=new Scanner(System.in);
		String source = input.nextLine();
		String Baidu_result = BaiduTranslateDemo.translateToZh(source);
		System.out.println("百度：" + Baidu_result);
		
		YoudaoTranslate test = new YoudaoTranslate();
		String Youdao_result=test.getYouDaoValue(source);
		System.out.println("有道：" + Youdao_result);
		
		BingTranslate test2 = new BingTranslate();
		String Bing_result=test2.getBingValue(source);
		System.out.println("必应：" + Bing_result);
		  
		input.close();
	}
}
