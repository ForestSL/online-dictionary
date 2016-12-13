package Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class DB_Num extends DB_con{

	public String num() {
		String result=null;
		try {
			connection(); 		//加载conn_db类，连接数据库；
			result = countWithDB();
		} 
		catch (Exception e1) {
			result="fail";
			e1.printStackTrace();
		}
		return result;
	}
	
	//判断数据是否在数据库中
	public String countWithDB() throws Exception{	
		String result=null;
		Connection con = super.con;
		Statement stmt = con.createStatement();
		
		ResultSet rs1 = con.getMetaData().getTables(null, null, "userinfo", null);  //判断是否存在该表
		if (rs1.next()) {  
		String sql = "select * from userinfo";
		ResultSet rs = stmt.executeQuery(sql);
		int all=0;
		int current=0;
		
		while(rs.next()){				
			all++;
			String state=rs.getString(3);
			if(state.equals("2"))
				current++;
		}
		
		String sall=Integer.toString(all);
		String scurrent=Integer.toString(current);
		result=sall;
		result+=" ";
		result+=scurrent;
		}
		else
			result="0 0";

		return result;
		
	}

}
