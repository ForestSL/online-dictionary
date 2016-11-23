package spring;

import com.memetix.mst.language.Language;  
import com.memetix.mst.translate.Translate;

public class BingTranslate {

	public String getBingValue(String source) {  
            
		//在Java程序内翻译  
        Translate.setKey( "4+d8H2ib9o/833OIUIBduE0j9qAbCJ47jq84xDVcEKQ=" );    
        String translatedText="";  
        try {  
        	translatedText = Translate.execute(source, Language.ENGLISH, Language.CHINESE_SIMPLIFIED);   
        } catch (Exception e) {  
        	// TODO Auto-generated catch block  
            e.printStackTrace();  
        }        
       
        return translatedText;
    }  
	
}
