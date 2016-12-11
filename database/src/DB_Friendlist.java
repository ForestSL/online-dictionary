import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB_Friendlist extends DB_con{

	public String friendlist(String name) {
		String result=null;
		try {
			connection(); 		//加载conn_db类，连接数据库；
			result = listWithDB(name);
		} 
		catch (Exception e) {
			result="fail";
			e.printStackTrace();
		}
		return result;
	}

	public String listWithDB(String pname) throws SQLException {
		String result="";
		Connection con = super.con;
		Statement stmt = con.createStatement();
		
		String sql = "select * from friend";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()){	
			String name=rs.getString(1);
			String friend=rs.getString(2);
			String state=rs.getString(3);
			if(name.equals(pname)&&state.equals("1"))//该用户当前好友
			{
				result+=friend;
				result+=" ";
			}
			else if(friend.equals(pname)&&state.equals("1"))
			{
				result+=name;
				result+=" ";
			}
		}
		
		return result;
		
	}

}
