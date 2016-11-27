package Server;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Like extends DB_con{
	
	public String likecount(String word,String n1,String n2,String n3) {
		String result=null;
		try {
			connection();
			result=saveInDB(word,n1,n2,n3);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			result="��������¼ʧ��";
			e.printStackTrace();
		}
		return result;
		
	}
	
	//�������ݿ�
	public String saveInDB(String m_word,String m_baidu,String m_youdao,String m_bing) throws SQLException{
		String result=null;
		Connection con=super.con;
		Statement stmt=con.createStatement();
		
		//������userinfo
		String sql="create table if not exists likecount(word varchar(20),baidu varchar(10),youdao varchar(10),bing varchar(10))";
		stmt.executeUpdate(sql);
		//System.out.println("�������ޱ�ɹ�");
		//result="�����ޱ�ɹ�";
		//result+="\n";
		
		sql = "select * from likecount";
		ResultSet rs = stmt.executeQuery(sql);
		boolean mjudge=false;//�жϸõ��ʵ������Ƿ��м�¼
		while(rs.next()){				
			//�û��������Ϣ�����ݿ��е���Ϣ���Ƚϣ��ж������Ƿ���ȷ��
			String userword = rs.getString(1);
			String baidu=rs.getString(2);
			String youdao=rs.getString(3);
			String bing=rs.getString(4);
			if(userword.equals(m_word))
			{
				//�����Ѵ��ڣ��޸ĵ�����
				m_baidu=String.valueOf(Integer.parseInt(baidu)+Integer.parseInt(m_baidu));
				m_youdao=String.valueOf(Integer.parseInt(youdao)+Integer.parseInt(m_youdao));
				m_bing=String.valueOf(Integer.parseInt(bing)+Integer.parseInt(m_bing));
				sql="update likecount set baidu='"+m_baidu+"',youdao='"+m_youdao+"',bing='"+m_bing+"' where word='"+m_word+"'";
				int judge=stmt.executeUpdate(sql);
				if(judge>0)
					result="�������޸ĳɹ�";
					//System.out.println("״̬�޸ĳɹ�");
				else
					result="�������޸�ʧ��";
					//System.out.println("״̬�޸�ʧ��");
				mjudge=true;
				break;
			}
		}
		
		if(mjudge==false)//���ʲ����ڣ����������
		{
			//������Ϣ
			sql="insert into likecount(word,baidu,youdao,bing) values('"+m_word+"','"+m_baidu+"','"+m_youdao+"','"+m_bing+"')";
			int judge=stmt.executeUpdate(sql);
			if(judge>0)
				result="��������¼�ɹ�";
				//System.out.println("ע��ɹ�");
			else
				result="��������¼ʧ��";
		 		//System.out.println("ע��ʧ��");
		}
		
		return result;
		
	}

}