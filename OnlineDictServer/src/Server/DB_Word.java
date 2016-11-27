package Server;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB_Word extends DB_con{
	
	public String word(String word) {
		String result=null;
		
		//��������
		result=word;
		result+=" ";
		
		//������
		try {
			connection();
			result+=likenum(word);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			result="���ʲ�ѯʧ��";
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String likenum(String word) throws SQLException{
		String result=null;
		
		Connection con = super.con;
		Statement stmt = con.createStatement();
		
		String sql = "select * from likecount";
		ResultSet rs = stmt.executeQuery(sql);

		while(rs.next()){				
			//�û��������Ϣ�����ݿ��е���Ϣ���Ƚϣ��ж������Ƿ���ȷ��
			String m_word = rs.getString(1);
			String n1=rs.getString(2);
			String n2=rs.getString(3);
			String n3=rs.getString(4);
			if(m_word.equals(word))
			{
				result=n1;
				result+=" ";
				result+=n2;
				result+=" ";
				result+=n3;
			}
		}
		
		return result;
	}
	
	
}
