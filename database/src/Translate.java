
public class Translate {

	/*
	public static void main(String[] args){
		Translate tr=new Translate();
		System.out.println(tr.translate("apple 1 1 1"));
	}*/
	
	//返回点赞数和解释以!!!!分隔
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
		
		//jinshan 
		result+=JinshanTranslate.translateToZh(temp[0]);
		result+="!!!!";
		
		//bing
		//result+=BingTranslate.translateToZh(temp[0]);
		
		return result;
	}
	
	
}
