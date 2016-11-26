
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
		
		if(temp[0].equals("0"))//返回提示
		{
			DB_Register re=new DB_Register();
			result=re.register(temp[1],temp[2]);
		}
		else if(temp[0].equals("1"))//返回提示
		{
			DB_Login lo=new DB_Login();
			result=lo.login(temp[1],temp[2]);
		}
		else if(temp[0].equals("2"))//返回单词解释和点赞数
		{
			DB_Word wo=new DB_Word();
			result=wo.word(temp[1]);
		}
		else if(temp[0].equals("3"))//存储点赞数返回提示
		{
			DB_Like li=new DB_Like();
			result=li.likecount(temp[1],temp[2],temp[3],temp[4]);
		}
			
		return result;
			
	}
	
	public static void main(String[] args){
		DB_Server se=new DB_Server();
		System.out.println(se.Check_DB("2 apple"));
	}
	
}
