import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB_Message extends DB_con{

	public String message(String name) {
		String result=null;
		try {
			connection(); 		//����conn_db�࣬�������ݿ⣻
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
		
		boolean judge=false;//�ж��Ƿ����µ�����
		while(rs.next()){				
			//�û��������Ϣ�����ݿ��е���Ϣ���Ƚϣ��ж������Ƿ���ȷ��
			String username = rs.getString(1);
			String userfriend = rs.getString(2);
			String state=rs.getString(3);
			if(userfriend.equals(name)&&state.equals("2"))//���ڻ�δͬ��ĺ�������
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
