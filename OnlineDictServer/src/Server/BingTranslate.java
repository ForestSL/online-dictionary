package Server;
import com.memetix.mst.language.Language;  
import com.memetix.mst.translate.Translate;

public class BingTranslate {

	public static String translateToZh(String source) {  
            
		//��Java�����ڷ���  
        //Translate.setKey( "4CC9C98872B3CB8A3D7E1E2AA035ACD" );  
		String result="��Ӧ��"; 
		try {
			Translate.setClientId("online-dict");
			Translate.setClientSecret("rtgsf7IYB8Gywnr8ifDCileDu/RFmdZ5YSo+h0jC/T0=");
		}catch (Exception e) {
			result+="���粻�ȶ�";
			e.printStackTrace(); 
		}
         
        try {  
        	result+= Translate.execute(source, Language.ENGLISH, Language.CHINESE_SIMPLIFIED);   
        } catch (Exception e) {  
        	result+= "δ�ҵ��õ���";
        	// TODO Auto-generated catch block  
            e.printStackTrace();  
        }        
       
        return result;
    }  
	
}
