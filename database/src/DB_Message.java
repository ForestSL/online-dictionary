import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB_Message extends DB_con{

	public String message(String name) {
		String result=null;
		try {
			connection(); 		//加载conn_db类，连接数据库；
			result= messageWithDB(name);
		} 
		catch (Exception e1) {
			result="fail";
			e1.printStackTrace();
		}
		return result;
	}

	public String messageWithDB(String name) throws SQLException {
		String result="";
		Connection con = super.con;
		Statement stmt = con.createStatement();
		
		String sql = "select * from friend";
		ResultSet rs = stmt.executeQuery(sql);
		
		boolean judge=false;//判断是否有新的申请
		while(rs.next()){				
			//用户输入的信息和数据库中的信息做比较，判断输入是否正确；
			String username = rs.getString(1);
			String userfriend = rs.getString(2);
			String state=rs.getString(3);
			if(userfriend.equals(name)&&state.equals("2"))//存在还未同意的好友申请
			{
				judge=true;
				result+=username;
				result+=" ";
			}
		}

		if(judge==false)
			result+="none";
		
		return result;
	}

}
