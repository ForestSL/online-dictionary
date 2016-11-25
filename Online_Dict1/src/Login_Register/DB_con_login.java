package Login_Register;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DB_con_login extends DB_connect implements ActionListener{
	
	register re;//用于注册

	JTextField textname,textpassword;
	JButton loginButton,registerButton;
	
	public void setTextname(JTextField textname) {
		this.textname = textname;
	}

	public void setTextpassword(JTextField textpassword) {
		this.textpassword = textpassword;
	}

	public void setLoginButton(JButton loginButton) {
		this.loginButton = loginButton;
	}

	public void setRegisterButton(JButton registerButton) {
		this.registerButton = registerButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource() == loginButton){
			if(textname.getText()==null)			//判断用户输入是否为空；
				JOptionPane.showMessageDialog(null, "请输入姓名");
			else if(textpassword.getText()==null)
				JOptionPane.showMessageDialog(null, "请输入密码");
			else{
				String name = textname.getText();
				String password = textpassword.getText();
				try {
					connection(); 		//加载conn_db类，连接数据库；
					boolean com = compareWithDB(name,password);
					if(com==true)
						JOptionPane.showMessageDialog(null, "登录成功");
					else{
						JOptionPane.showMessageDialog(null, "用户名或者密码不正确，请重新输入");
						textname.setText("");
						textpassword.setText("");
					}
				} 
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		//注册
		else if(e.getSource()==registerButton){
			new JFrame().dispose();//消除当前界面
			re = new register();
		}
			
		
	}
	
	//判断数据是否在数据库中
	boolean compareWithDB(String name,String password) throws Exception{	
		Connection con = super.con;
		Statement stmt = con.createStatement();
		
		String sql = "select * from userinfo";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()){				
			//用户输入的信息和数据库中的信息做比较，判断输入是否正确；
			String username = rs.getString(1);
			String userpassword = rs.getString(2);
			String state=rs.getString(3);
			if(username.equals(name) && userpassword.equals(password))
			{
				state="1";//2表示在线
				sql="update userinfo set userstate='"+state+"' where username='"+name+"'";
				int judge=stmt.executeUpdate(sql);
				if(judge>0)
					System.out.println("状态修改成功");
				else
					System.out.println("状态修改失败");
				return true;
			}
		}

		return false;
		
	}

}
