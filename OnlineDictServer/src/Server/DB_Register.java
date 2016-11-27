package Server;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Register  extends DB_con{
	//ע��
	public String register(String name,String pwd){	
		String result=null;
			
		String state="1";//1��ʾ����
			
		try {
			connection();
			result=saveInDB(name,pwd,state);
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			result="��������ʧ��";
			e1.printStackTrace();
		}
		return result;
	}
	
	//ע����Ϣд�����ݿ�
	public String saveInDB(String name,String password,String state) throws SQLException{
		String result=null;
		Connection con=super.con;
		Statement stmt=con.createStatement();
		
		//������userinfo
		String sql="create table if not exists userinfo(username varchar(10),userpassword varchar(20),userstate varchar(5))";
		stmt.executeUpdate(sql);
		//System.out.println("�����û���Ϣ��ɹ�");
		//result="�����û���Ϣ��ɹ�";
		//result+="\n";
		
		sql = "select * from userinfo";
		ResultSet rs = stmt.executeQuery(sql);
		boolean mjudge=false;//�жϸ��û��Ƿ��Ѵ���
		while(rs.next()){				
			//�û��������Ϣ�����ݿ��е���Ϣ���Ƚϣ��ж������Ƿ���ȷ��
			String username = rs.getString(1);
			if(username.equals(name))
			{
				result="exist";
				mjudge=true;
				break;
			}
		}
		
		if(mjudge==false)//�û������ڼ�ע��
		{
			//������Ϣ
			sql="insert into userinfo(username,userpassword,userstate) values('"+name+"','"+password+"','"+state+"')";
			int judge=stmt.executeUpdate(sql);
			if(judge>0)
				result="success";
				//System.out.println("ע��ɹ�");
			else
				result="fail";
				//System.out.println("ע��ʧ��");
		}
		
		return result;
		
	}
	
}
