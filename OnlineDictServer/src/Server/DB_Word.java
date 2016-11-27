package Server;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB_Word extends DB_con{
	
	public String word(String word) {
		String result=null;
		
		//单词释义
		result=word;
		result+=" ";
		
		//点赞数
		try {
			connection();
			result+=likenum(word);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			result="单词查询失败";
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
			//用户输入的信息和数据库中的信息做比较，判断输入是否正确；
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
