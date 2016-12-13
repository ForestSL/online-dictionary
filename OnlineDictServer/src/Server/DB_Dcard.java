package Server;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;


public class DB_Dcard extends DB_con{

	public String dcard(String sendname, String cardbyte, String getname) {
		String result=null;
		try {
			connection(); 		//加载conn_db类，连接数据库；
			result= deleteWithDB(sendname,cardbyte,getname);
		} 
		catch (Exception e1) {
			result="fail";
			e1.printStackTrace();
		}
		return result;
	}

	public String deleteWithDB(String sendname, String cardbyte, String getname) throws UnsupportedEncodingException, SQLException {
		String result="";
		Connection con = super.con;
		Statement stmt = con.createStatement();
		
		ResultSet rs1 = con.getMetaData().getTables(null, null, "card", null);  //判断是否存在该表
		if (rs1.next()) {  
			String sql = "select * from card";
			ResultSet rs = stmt.executeQuery(sql);
			
			boolean judge=false;//判断是否有新的申请
			while(rs.next()){				
				//用户输入的信息和数据库中的信息做比较，判断输入是否正确；
				String username1=rs.getString(1);
				//byte[] userpicture = rs.getBytes(2);
				String userpicture = rs.getString(2);
				String username = rs.getString(3);
				
				if(username1.equals(sendname)&&userpicture.equals(cardbyte)&&username.equals(getname))//存在待发送卡片
				{
					judge=true;
					//String userpicturestr=new String(userpicture,"UTF-8");
					//删除该记录
					PreparedStatement preparedStatement = null;
					sql="delete from card where name=? and picture=? and sendname=?";
					preparedStatement = con.prepareStatement(sql);
					
		            preparedStatement.setString(1, username);
		           // preparedStatement.setBytes(2, userpicture);
		            preparedStatement.setString(2,userpicture);
		            preparedStatement.setString(3, username1);
		            if(preparedStatement.executeUpdate()>0)
		            	result+="success";
		            else
		            	result="fail";
		            
					break;
				}
			}

			if(judge==false)
				result+="none";
		}else {  
			result+="none";
		} 
		
		return result;
	}

}

