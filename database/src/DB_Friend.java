import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB_Friend extends DB_con{

	public String friend(String name,String friend) {
		String result=null;
		try {
			connection();
			result=friendInDB(name,friend);
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			result="fail";
			e1.printStackTrace();
		}
		return result;
	}

	public String friendInDB(String name, String friend) throws SQLException {
		String result=null;
		Connection con=super.con;
		Statement stmt=con.createStatement();
		
		//创建表friend
		String sql="create table if not exists friend(name varchar(10),friend varchar(10),state varchar(5))";
		stmt.executeUpdate(sql);
		
		sql = "select * from friend";
		ResultSet rs = stmt.executeQuery(sql);
		boolean mjudge=false;//判断该用户好友申请是否已存在
		while(rs.next()){				
			//用户输入的信息和数据库中的信息做比较，判断输入是否正确；
			String username = rs.getString(1);
			String userfriend=rs.getString(2);
			String userstate=rs.getString(3);
			if(username.equals(name)&&userfriend.equals(friend))
			{
				if(userstate.equals("1"))//已添加好友
				{
					result="exist";
					mjudge=true;
					break;
				}
				else
					result="wait";//等待回复
				mjudge=true;
			}
			else if(username.equals(friend)&&userfriend.equals(name))
			{
				mjudge=true;
				String state="1";
				sql="update friend set state='"+state+"' where name='"+name+"'and friend='"+friend+"'";
				int judge=stmt.executeUpdate(sql);
				if(judge>0)
					return "sendsuccess";
				else
					return "fail";
			}
		}
		
		if(mjudge==false)//用户没有申请加过该好友
		{
			//判断要添加的好友是否为存在用户
			sql = "select * from userinfo";
			ResultSet trs = stmt.executeQuery(sql);
			boolean tjudge=false;//判断该用户是否已存在
			while(trs.next()){				
				//用户输入的信息和数据库中的信息做比较，判断输入是否正确；
				String username = trs.getString(1);
				if(username.equals(friend))
				{
					tjudge=true;
					break;
				}
			}
			if(tjudge==false)
				result="notexist";
			else{
				//插入信息
				String state="2";//等待回复
				sql="insert into friend(name,friend,state) values('"+name+"','"+friend+"','"+state+"')";
				int judge=stmt.executeUpdate(sql);
				if(judge>0)
					result="sendsuccess";
					//System.out.println("发送申请成功");
				else
					result="fail";
					//System.out.println("失败");
			}
		}
		
		return result;
	}

}
