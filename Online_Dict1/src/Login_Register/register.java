package Login_Register;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class register extends JFrame{
	DB_con_register m_register;
	
	register(){
		init_register();
	}
	
	void init_register(){
		m_register=new DB_con_register();
		
		JFrame jFrame = new JFrame("新用户注册");
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
		
		JButton ok_button = new JButton("OK");
		ok_button.setBounds(10, 80, 70, 20);
		JButton reset_button = new JButton("RESET");
		reset_button.setBounds(100, 80, 90, 20);
		
		ok_button.addActionListener(m_register);
		reset_button.addActionListener(m_register);
		m_register.setTextname(text_name);
		m_register.setTextpassword(text_password);
		m_register.setOkButton(ok_button);
		m_register.setResetButton(reset_button);
		
		jFrame.add(ok_button);
		jFrame.add(reset_button);
		
		jFrame.setVisible(true);
		
	}
	
}
