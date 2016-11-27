
public class Translate {

	public String translate(String word){
		String result=null;
		String[] temp=word.split(" ");
		result=temp[1];
		result+="!!!!";
		result+=temp[2];
		result+="!!!!";
		result+=temp[3];
		result+="!!!!";
		
		//baidu
		result+=BaiduTranslate.translateToZh(temp[0]);
		result+="!!!!";
		
		//youdao
		result+=YoudaoTranslate.translateToZh(temp[0]);
		result+="!!!!";
		
		//bing
		result+=BingTranslate.translateToZh(temp[0]);
		
		return result;
	}
	
}
