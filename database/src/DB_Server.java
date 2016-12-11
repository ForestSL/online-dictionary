
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
			result=wo.word(temp[1]);//word_baidu_youdao_bing
			//返回翻译baidu_youdao_bing_百度_有道_金山
			Translate tr=new Translate();
			result=tr.translate(result);
			/*测试用
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
		else if(temp[0].equals("3"))//存储点赞数返回提示
		{
			DB_Like li=new DB_Like();
			result=li.likecount(temp[1],temp[2],temp[3],temp[4]);
		}else if(temp[0].equals("5"))//退出登录
		{
			DB_Exit ex=new DB_Exit();
			result=ex.exit(temp[1]);
		}else if(temp[0].equals("6"))//添加好友
		{
			DB_Friend nu=new DB_Friend();
			result=nu.friend(temp[1],temp[2]);
		}else if(temp[0].equals("7"))//消息提醒
		{
			DB_Message nu=new DB_Message();
			result=nu.message(temp[1]);
		}else if(temp[0].equals("8"))//处理好友申请
		{
			DB_Reply nu=new DB_Reply();
			result=nu.reply(temp[1],temp[2],temp[3]);
		}else if(temp[0].equals("9"))//返回好友列表
		{
			DB_Friendlist nu=new DB_Friendlist();
			result=nu.friendlist(temp[1]);
		}else if(temp[0].equals("10"))//返回用户数及在线用户数
		{
			DB_Num nu=new DB_Num();
			result=nu.num();
		}else if(temp[0].equals("11"))//返回在线用户列表
		{
			DB_List nu=new DB_List();
			result=nu.list();
		}
			
		return result;
			
	}
	
	/*
	public static void main(String[] args){
		//DB_Server se=new DB_Server();
		//System.out.println(se.Check_DB("9 forest"));
		Translate tr=new Translate();
		System.out.println(tr.translate("environment 1 2 3"));
	}*/
	
}
