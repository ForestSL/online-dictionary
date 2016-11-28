package Server;
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
	 * etc...
	 */
	public String Check_DB(String word){
		
		String[] temp=word.split(" ");
		String result=null;
		
		if(temp[0].equals("0"))//������ʾ
		{
			DB_Register re=new DB_Register();
			result=re.register(temp[1],temp[2]);
		}
		else if(temp[0].equals("1"))//������ʾ
		{
			DB_Login lo=new DB_Login();
			result=lo.login(temp[1],temp[2]);
		}
		else if(temp[0].equals("2"))//���ص��ʽ��ͺ͵�����
		{
			DB_Word wo=new DB_Word();
			result=wo.word(temp[1]);//word_baidu_youdao_bing
			//���ط���baidu_youdao_bing_�ٶ�_�е�_��Ӧ
			Translate tr=new Translate();
			result=tr.translate(result);
		
		}
		else if(temp[0].equals("3"))//�洢������������ʾ
		{
			DB_Like li=new DB_Like();
			result=li.likecount(temp[1],temp[2],temp[3],temp[4]);
		}
		else if(temp[0].equals("5"))//�˳���¼
		{
			DB_Exit ex=new DB_Exit();
			result=ex.exit(temp[1]);
		}

			
		return result;
			
	}
	
	
}
