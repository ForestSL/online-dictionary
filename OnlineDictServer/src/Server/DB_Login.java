package Server;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Login extends DB_con{
	
	public String login(String name,String pwd){
	
		String result=null;
		try {
			connection(); 		//加载conn_db类，连接数据库；
			boolean com = compareWithDB(name,pwd);
			if(com==true)
				result="success";
			else{
				result="wrong";
			}
		} 
		catch (Exception e1) {
			result="fail";
			e1.printStackTrace();
		}
		return result;
	}
	
	//判断数据是否在数据库中
	boolean compareWithDB(String name,String password) throws Exception{	
		Connection con = super.con;
		Statement stmt = con.createStatement();
		
		String sql = "select * from userinfo";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()){				
			//用户输入的信息和数据库中的信息做比较，判断输入是否正确；
			String username = rs.getString(1);
			String userpassword = rs.getString(2);
			String state=rs.getString(3);
			if(username.equals(name) && userpassword.equals(password))
			{
				state="2";//2表示在线
				sql="update userinfo set userstate='"+state+"' where username='"+name+"'";
				int judge=stmt.executeUpdate(sql);
				if(judge>0)
					System.out.println("状态修改成功");
				else
					System.out.println("状态修改失败");
				return true;
			}
		}

		return false;
		
	}

}
