package Login_Register;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class login extends JFrame{
	DB_con_login m_login;
	
	login(){
		init_login();
	}
	
	void init_login(){
		m_login=new DB_con_login();
		
		JFrame jFrame = new JFrame("用户登录");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		jFrame.setBounds(((int)dimension.getWidth() - 200) / 2, ((int)dimension.getHeight() - 300) / 2, 200, 150);
		jFrame.setResizable(false);
		jFrame.setLayout(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel label_name = new JLabel("用户名");
		label_name.setBounds(10, 10, 100, 30);
		jFrame.add(label_name);
		JLabel label_password = new JLabel("密码");
		label_password.setBounds(10, 40, 100, 30);
		jFrame.add(label_password);
		
		final JTextField text_name = new JTextField();
		text_name.setBounds(50, 15, 130, 20);
		jFrame.add(text_name);
		final JPasswordField text_password = new JPasswordField();
		text_password.setBounds(50, 45, 130, 20);
		jFrame.add(text_password);
		
		JButton login_button = new JButton("LOGIN");
		login_button.setBounds(10, 80, 70, 20);
		JButton register_button = new JButton("REGISTER");
		register_button.setBounds(100, 80, 90, 20);
		
		login_button.addActionListener(m_login);
		register_button.addActionListener(m_login);
		m_login.setTextname(text_name);
		m_login.setTextpassword(text_password);
		m_login.setLoginButton(login_button);
		m_login.setRegisterButton(register_button);
		
		jFrame.add(login_button);
		jFrame.add(register_button);
		
		jFrame.setVisible(true);
		
	}
	/*
	public static void main(String[] args){
		login re=new login();
	}*/
	
}
