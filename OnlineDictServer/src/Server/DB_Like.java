package Server;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Like extends DB_con{
	
	public String likecount(String word,String n1,String n2,String n3) {
		String result=null;
		try {
			connection();
			result=saveInDB(word,n1,n2,n3);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			result="点赞数记录失败";
			e.printStackTrace();
		}
		return result;
		
	}
	
	//存入数据库
	public String saveInDB(String m_word,String m_baidu,String m_youdao,String m_bing) throws SQLException{
		String result=null;
		Connection con=super.con;
		Statement stmt=con.createStatement();
		
		//创建表userinfo
		String sql="create table if not exists likecount(word varchar(20),baidu varchar(10),youdao varchar(10),bing varchar(10))";
		stmt.executeUpdate(sql);
		//System.out.println("创建点赞表成功");
		//result="建点赞表成功";
		//result+="\n";
		
		sql = "select * from likecount";
		ResultSet rs = stmt.executeQuery(sql);
		boolean mjudge=false;//判断该单词点赞数是否有记录
		while(rs.next()){				
			//用户输入的信息和数据库中的信息做比较，判断输入是否正确；
			String userword = rs.getString(1);
			String baidu=rs.getString(2);
			String youdao=rs.getString(3);
			String bing=rs.getString(4);
			if(userword.equals(m_word))
			{
				//单词已存在，修改点赞数
				m_baidu=String.valueOf(Integer.parseInt(baidu)+Integer.parseInt(m_baidu));
				m_youdao=String.valueOf(Integer.parseInt(youdao)+Integer.parseInt(m_youdao));
				m_bing=String.valueOf(Integer.parseInt(bing)+Integer.parseInt(m_bing));
				sql="update likecount set baidu='"+m_baidu+"',youdao='"+m_youdao+"',bing='"+m_bing+"' where word='"+m_word+"'";
				int judge=stmt.executeUpdate(sql);
				if(judge>0)
					result="点赞数修改成功";
					//System.out.println("状态修改成功");
				else
					result="点赞数修改失败";
					//System.out.println("状态修改失败");
				mjudge=true;
				break;
			}
		}
		
		if(mjudge==false)//单词不存在，插入点赞数
		{
			//插入信息
			sql="insert into likecount(word,baidu,youdao,bing) values('"+m_word+"','"+m_baidu+"','"+m_youdao+"','"+m_bing+"')";
			int judge=stmt.executeUpdate(sql);
			if(judge>0)
				result="点赞数记录成功";
				//System.out.println("注册成功");
			else
				result="点赞数记录失败";
		 		//System.out.println("注册失败");
		}
		
		return result;
		
	}

}