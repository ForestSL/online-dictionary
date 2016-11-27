package Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_con {
	Connection con=null;
	String url=null;
	
	public void connection() throws SQLException{
		url="jdbc:mysql://localhost:3306/onlinedict?user=root&password=&characterEnunicode=UTF8";
		// MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载mysql驱动");
			con=DriverManager.getConnection(url);
			System.out.println("数据库连接成功");
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			System.out.println("数据库操作错误");
			e.printStackTrace();
		}
	}
	
	/*
	public static void main(String[] args) {
		DB_con lo = new DB_con();
		try {
			lo.connection();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}*/
	
}
