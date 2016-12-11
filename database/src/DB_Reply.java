import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB_Reply extends DB_con{

	public String reply(String name,String applicant,String state) {
		String result=null;
		try {
			connection(); 		//����conn_db�࣬�������ݿ⣻
			result = replyWithDB(name,applicant,state);
		
		} 
		catch (Exception e1) {
			result="fail";
			e1.printStackTrace();
		}
		return result;
	}

	public String replyWithDB(String name,String applicant,String state) throws SQLException {
		Connection con = super.con;
		Statement stmt = con.createStatement();
		
		String sql = "select * from friend";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()){				
			//�û��������Ϣ�����ݿ��е���Ϣ���Ƚϣ��ж������Ƿ���ȷ��
			String username = rs.getString(1);
			String userfriend = rs.getString(2);
			String userstate=rs.getString(3);
			if(username.equals(applicant)&&userfriend.equals(name))
			{
				if(state.equals("1"))//ͬ������
				{
					String temp="1";//��ʾ�ѳ�Ϊ����
					sql="update friend set state='"+temp+"' where name='"+applicant+"'and friend='"+name+"'";
					int judge=stmt.executeUpdate(sql);
					if(judge>0)
						return "success";
					else
						return "fail";
				}
				else if(state.equals("2"))//�ܾ�����
				{
					sql="delete from friend where name='"+applicant+"'and friend='"+name+"'";
					int judge=stmt.executeUpdate(sql);
					if(judge>0)
						return "success";
					else
						return "fail";
				}
			}
		}
		return "fail";
	}

}
