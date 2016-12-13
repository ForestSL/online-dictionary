package Server;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class DB_Server extends DB_con{

	/*word: 
	 * reg:0_name_pass
	 * log:1_name_pass
	 * word:2_word
	 * like:3_word_n1_n2_n3
	 * picture:
	 * exit:5_name
	 * friend:6_name_friend
	 * message:7_name
	 * reply:8_myname_applicant_replystate
	 * friendlist:9_name
	 * num:10
	 * userlist:11
	 * sendCard:12_sendname_card_getname
	 * getCard:13_name
	 * deletecard:14_sendname_card_getname
	 * etc...
	 */
	public String Check_DB(String word) {
		
		String[] temp=word.split(" ");
		String result=null;
		
		if(temp[0].equals("0"))//������ʾ
		{
			DB_Register re=new DB_Register();
			String info=word.substring(2);
			info=decrypt(info);
			String[] newtemp=info.split(" ");
			result=re.register(newtemp[0],newtemp[1]);
		}
		else if(temp[0].equals("1"))//������ʾ
		{
			DB_Login lo=new DB_Login();
			String info=word.substring(2);
			info=decrypt(info);
			String[] newtemp=info.split(" ");
			result=lo.login(newtemp[0],newtemp[1]);
		}
		else if(temp[0].equals("2"))//���ص��ʽ��ͺ͵�����
		{
			DB_Word wo=new DB_Word();
			result=wo.word(temp[1]);//word_baidu_youdao_bing
			//���ط���baidu_youdao_bing_�ٶ�_�е�_��ɽ
			Translate tr=new Translate();
			result=tr.translate(result);
			/*������
			 * String[] mtemp=result.split(" ");
			result=mtemp[1];
			result+="!!!!";
			result+=mtemp[2];
			result+="!!!!";
			result+=mtemp[3];
			result+="!!!!";
			result+=mtemp[0];
			result+="!!!!";
			result+=mtemp[0];
			result+="!!!!";
			result+=mtemp[0];*/
		}
		else if(temp[0].equals("3"))//�洢������������ʾ
		{
			DB_Like li=new DB_Like();
			result=li.likecount(temp[1],temp[2],temp[3],temp[4]);
		}else if(temp[0].equals("5"))//�˳���¼
		{
			DB_Exit ex=new DB_Exit();
			result=ex.exit(temp[1]);
		}else if(temp[0].equals("6"))//��Ӻ���
		{
			DB_Friend nu=new DB_Friend();
			result=nu.friend(temp[1],temp[2]);
		}else if(temp[0].equals("7"))//��Ϣ����
		{
			DB_Message nu=new DB_Message();
			result=nu.message(temp[1]);
		}else if(temp[0].equals("8"))//�����������
		{
			DB_Reply nu=new DB_Reply();
			result=nu.reply(temp[1],temp[2],temp[3]);
		}else if(temp[0].equals("9"))//���غ����б�
		{
			DB_Friendlist nu=new DB_Friendlist();
			result=nu.friendlist(temp[1]);
		}else if(temp[0].equals("10"))//�����û����������û���
		{
			DB_Num nu=new DB_Num();
			result=nu.num();
		}else if(temp[0].equals("11"))//���������û��б�
		{
			DB_List nu=new DB_List();
			result=nu.list();
		}else if(temp[0].equals("12"))//���뿨Ƭ���û�
		{
			DB_Scard nu=new DB_Scard();
			result=nu.scard(temp[1],temp[2],temp[3]);
		}else if(temp[0].equals("13"))//�û���ȡ��Ƭ
		{
			DB_Gcard nu=new DB_Gcard();
			result=nu.gcard(temp[1]);
		}else if(temp[0].equals("14"))//ɾ���Ѿ����͵Ŀ�Ƭ��¼
		{
			DB_Dcard nu=new DB_Dcard();
			result=nu.dcard(temp[1],temp[2],temp[3]);
		}/*else//�˴�������!@#!@#!@#�ָ��������
		{
			String[] temp1=word.split("!@#!@#!@#!@#!@#");
			if(temp1[0].equals("12"))//���뿨Ƭ���û�
			{
				byte[] cardbyte = null;
				try {
					cardbyte = temp1[2].getBytes("UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				//byte[] cardbyte = new sun.misc.BASE64Decoder().decodeBuffer(temp1[2]);
				DB_Scard nu=new DB_Scard();
				result=nu.scard(temp1[1],cardbyte,temp1[3]);
			}else if(temp1[0].equals("14"))//ɾ���Ѿ����͵Ŀ�Ƭ��¼
			{
				byte[] cardbyte = null;
				try {
					cardbyte = temp1[2].getBytes("UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				//byte[] cardbyte = new sun.misc.BASE64Decoder().decodeBuffer(temp1[2]);
				DB_Dcard nu=new DB_Dcard();
				result=nu.dcard(temp1[1],cardbyte,temp1[3]);
			}
			
		}*/
			
		return result;
			
	}
	public static String decrypt(String ssoToken)  
    {  
      try  
      {  
        String name = new String();  
        java.util.StringTokenizer st=new java.util.StringTokenizer(ssoToken,"%");  
        while (st.hasMoreElements()) {  
          int asc =  Integer.parseInt((String)st.nextElement()) - 27;  
          name = name + (char)asc;  
        }  

        return name;  
      }catch(Exception e)  
      {  
        e.printStackTrace() ;  
        return null;  
      }  
    }  
	/*
	public static void main(String[] args){
		DB_Server se=new DB_Server();
		System.out.println(se.Check_DB("14 forest word1 123456"));
		//Translate tr=new Translate();
		//System.out.println(tr.translate("hello 1 2 3"));
	}*/
	
}