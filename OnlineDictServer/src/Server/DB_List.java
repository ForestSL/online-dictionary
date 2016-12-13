package Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB_List extends DB_con{

	public String list() {
		String result=null;
		try {
			connection(); 		//加载conn_db类，连接数据库；
			result = listWithDB();
		} 
		catch (Exception e1) {
			result="fail";
			e1.printStackTrace();
		}
		return result;
	}

	public String listWithDB() throws SQLException {
		String result="";
		Connection con = super.con;
		Statement stmt = con.createStatement();
		
		String sql = "select * from userinfo";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()){	
			String name=rs.getString(1);
			String state=rs.getString(3);
			if(state.equals("2")){
				result+=name;
				result+=" ";
			}
		}
		
		return result;
		
	}

}
