package Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_con {
	Connection con=null;
	String url=null;
	
	public void connection() throws SQLException{
		url="jdbc:mysql://localhost:3306/onlinedict?user=root&password=&characterEnunicode=UTF8";
		// MySQL��JDBC URL��д��ʽ��jdbc:mysql://�������ƣ����Ӷ˿�/���ݿ������?����=ֵ
        // ������������Ҫָ��useUnicode��characterEncoding
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("�ɹ�����mysql����");
			con=DriverManager.getConnection(url);
			System.out.println("���ݿ����ӳɹ�");
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			System.out.println("���ݿ��������");
			e.printStackTrace();
		}
	}
	
	/*
	public static void main(String[] args) {
		DB_con lo = new DB_con();
		try {
			lo.connection();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}*/
	
}
