import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB_Friend extends DB_con{

	public String friend(String name,String friend) {
		String result=null;
		try {
			connection();
			result=friendInDB(name,friend);
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			result="fail";
			e1.printStackTrace();
		}
		return result;
	}

	public String friendInDB(String name, String friend) throws SQLException {
		String result=null;
		Connection con=super.con;
		Statement stmt=con.createStatement();
		
		//������friend
		String sql="create table if not exists friend(name varchar(10),friend varchar(10),state varchar(5))";
		stmt.executeUpdate(sql);
		
		sql = "select * from friend";
		ResultSet rs = stmt.executeQuery(sql);
		boolean mjudge=false;//�жϸ��û����������Ƿ��Ѵ���
		while(rs.next()){				
			//�û��������Ϣ�����ݿ��е���Ϣ���Ƚϣ��ж������Ƿ���ȷ��
			String username = rs.getString(1);
			String userfriend=rs.getString(2);
			String userstate=rs.getString(3);
			if(username.equals(name)&&userfriend.equals(friend))
			{
				if(userstate.equals("1"))//����Ӻ���
				{
					result="exist";
					mjudge=true;
					break;
				}
				else
					result="wait";//�ȴ��ظ�
				mjudge=true;
			}
			else if(username.equals(friend)&&userfriend.equals(name))
			{
				mjudge=true;
				String state="1";
				sql="update friend set state='"+state+"' where name='"+name+"'and friend='"+friend+"'";
				int judge=stmt.executeUpdate(sql);
				if(judge>0)
					return "sendsuccess";
				else
					return "fail";
			}
		}
		
		if(mjudge==false)//�û�û������ӹ��ú���
		{
			//�ж�Ҫ��ӵĺ����Ƿ�Ϊ�����û�
			sql = "select * from userinfo";
			ResultSet trs = stmt.executeQuery(sql);
			boolean tjudge=false;//�жϸ��û��Ƿ��Ѵ���
			while(trs.next()){				
				//�û��������Ϣ�����ݿ��е���Ϣ���Ƚϣ��ж������Ƿ���ȷ��
				String username = trs.getString(1);
				if(username.equals(friend))
				{
					tjudge=true;
					break;
				}
			}
			if(tjudge==false)
				result="notexist";
			else{
				//������Ϣ
				String state="2";//�ȴ��ظ�
				sql="insert into friend(name,friend,state) values('"+name+"','"+friend+"','"+state+"')";
				int judge=stmt.executeUpdate(sql);
				if(judge>0)
					result="sendsuccess";
					//System.out.println("��������ɹ�");
				else
					result="fail";
					//System.out.println("ʧ��");
			}
		}
		
		return result;
	}

}
