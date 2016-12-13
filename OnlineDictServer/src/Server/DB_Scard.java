package Server;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB_Scard extends DB_con{

	public String scard(String sendname,String card, String getname) {
		String result=null;
		try {
			connection();
			try {
				result=cardInDB(sendname,card,getname);
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			result="fail";
			e.printStackTrace();
		}
		return result;
	}
	
	public String cardInDB(String sendname,String pcard,String getname) throws IOException, SQLException {
		
		String result=null;
		Connection con=super.con;
		Statement stmt=con.createStatement();
		
		//������friend
		String sql="create table if not exists card(sendname varchar(10),picture varchar(50),name varchar(10))";
		stmt.executeUpdate(sql);
		
		sql = "select * from card";
		ResultSet rs = stmt.executeQuery(sql);
		boolean mjudge=false;//�ж��Ƿ��ѷ��͹���ͼƬ
		while(rs.next()){				
			//�û��������Ϣ�����ݿ��е���Ϣ���Ƚϣ��ж������Ƿ���ȷ��
			String username1 = rs.getString(1);
			//byte[] userpicture = rs.getBytes(2);
			String userpicture=rs.getString(2);
			String username=rs.getString(3);
			if(username1.equals(sendname)&&username.equals(getname)&&userpicture.equals(pcard))
			{
				result="exist";
				mjudge=true;
				break;
			}
		}
		
		if(mjudge==false)//�û�û�з��͹�
		{
			//�ж�Ҫ��ӵĺ����Ƿ�Ϊ�����û�
			sql = "select * from userinfo";
			ResultSet trs = stmt.executeQuery(sql);
			boolean tjudge=false;//�жϸ��û��Ƿ��Ѵ���
			while(trs.next()){				
				//�û��������Ϣ�����ݿ��е���Ϣ���Ƚϣ��ж������Ƿ���ȷ��
				String username = trs.getString(1);
				if(username.equals(getname))
				{
					tjudge=true;
					break;
				}
			}
			if(tjudge==false)
				result="notexist";
			else{
				PreparedStatement preparedStatement = null;
				//������Ϣ
				sql="insert into card(sendname,picture,name) values(?,?,?)";
				preparedStatement = con.prepareStatement(sql);
				
	            preparedStatement.setString(1, sendname);
	           // preparedStatement.setBytes(2, pcard);
	            preparedStatement.setString(2,pcard);
	            preparedStatement.setString(3, getname);
	            
	            //System.out.println(sql);
	            
	            if(preparedStatement.executeUpdate()>0)
	            	result="success";
	            else
	            	result="fail";
			}
		}
		
		return result;
	}

}