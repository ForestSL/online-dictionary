package Server;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Register  extends DB_con{
	//注册
	public String register(String name,String pwd){	
		String result=null;
			
		String state="1";//1表示离线
			
		try {
			connection();
			result=saveInDB(name,pwd,state);
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			result="插入数据失败";
			e1.printStackTrace();
		}
		return result;
	}
	
	//注册信息写入数据库
	public String saveInDB(String name,String password,String state) throws SQLException{
		String result=null;
		Connection con=super.con;
		Statement stmt=con.createStatement();
		
		//创建表userinfo
		String sql="create table if not exists userinfo(username varchar(10),userpassword varchar(20),userstate varchar(5))";
		stmt.executeUpdate(sql);
		//System.out.println("创建用户信息表成功");
		//result="创建用户信息表成功";
		//result+="\n";
		
		sql = "select * from userinfo";
		ResultSet rs = stmt.executeQuery(sql);
		boolean mjudge=false;//判断该用户是否已存在
		while(rs.next()){				
			//用户输入的信息和数据库中的信息做比较，判断输入是否正确；
			String username = rs.getString(1);
			if(username.equals(name))
			{
				result="exist";
				mjudge=true;
				break;
			}
		}
		
		if(mjudge==false)//用户不存在即注册
		{
			//插入信息
			sql="insert into userinfo(username,userpassword,userstate) values('"+name+"','"+password+"','"+state+"')";
			int judge=stmt.executeUpdate(sql);
			if(judge>0)
				result="success";
				//System.out.println("注册成功");
			else
				result="fail";
				//System.out.println("注册失败");
		}
		
		return result;
		
	}
	
}
