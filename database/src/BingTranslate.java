
import com.memetix.mst.language.Language;  
import com.memetix.mst.translate.Translate;

public class BingTranslate {

	public static String translateToZh(String source) {  
            
		//在Java程序内翻译  
        //Translate.setKey( "4CC9C98872B3CB8A3D7E1E2AA035ACD" );  
		String result="必应："; 
		try {
			Translate.setClientId("online-dict");
			Translate.setClientSecret("rtgsf7IYB8Gywnr8ifDCileDu/RFmdZ5YSo+h0jC/T0=");
		}catch (Exception e) {
			result+="网络不稳定";
			e.printStackTrace(); 
		}
         
        try {  
        	result+= Translate.execute(source, Language.ENGLISH, Language.CHINESE_SIMPLIFIED);   
        } catch (Exception e) {  
        	result+= "未找到该单词";
        	// TODO Auto-generated catch block  
            e.printStackTrace();  
        }        
       
        return result;
    }  
	
}