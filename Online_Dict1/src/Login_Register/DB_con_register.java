package Login_Register;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DB_con_register extends DB_connect implements ActionListener{

	JTextField textname,textpassword;
	JButton okButton,resetButton;
	
	public void setTextname(JTextField textname) {
		this.textname = textname;
	}

	public void setTextpassword(JTextField textpassword) {
		this.textpassword = textpassword;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public void setResetButton(JButton resetButton) {
		this.resetButton = resetButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==okButton){
			if(textname.getText()==null)
				JOptionPane.showMessageDialog(null, "请输入用户名","警告对话框",JOptionPane.WARNING_MESSAGE);
			else if(textpassword.getText()==null)
				JOptionPane.showMessageDialog(null,"请输入密码","警告对话框",JOptionPane.WARNING_MESSAGE);
			else{
				String name = textname.getText();
				String password = textpassword.getText();
				String state="1";//1表示离线
				
					try {
						connection();
						saveInDB(name,password,state);
					} catch (SQLException e1) {
						// TODO 自动生成的 catch 块
						System.out.println("插入数据失败");
						e1.printStackTrace();
					}
			}
		}
		
		//重置为空
		else if(e.getSource()==resetButton){
			textname.setText("");
			textpassword.setText("");
		}
			
		
	}
	
	//用户信息写入数据库
	void saveInDB(String name,String password,String state) throws SQLException{
		Connection con=super.con;
		Statement stmt=con.createStatement();
		
		//创建表userinfo
		String sql="create table if not exists userinfo(username varchar(10),userpassword varchar(20),userstate varchar(5))";
		stmt.executeUpdate(sql);
		System.out.println("创建用户信息表成功");
		
		//插入信息
		sql="insert into userinfo(username,userpassword,userstate) values('"+name+"','"+password+"','"+state+"')";
		int judge=stmt.executeUpdate(sql);
		if(judge>0)
			System.out.println("注册成功");
		else
			System.out.println("注册失败");
		
	}

}
