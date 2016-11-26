package Client;
import java.util.regex.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Login extends JFrame{
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	private String info;
	private JFrame handle=this;
	
	private NameInputPanel namePanel=new NameInputPanel();
	private PassInputPanel passPanel=new PassInputPanel();
	private ButtonPanel buttonPanel=new ButtonPanel();
	private TipPanel tipPanel=new TipPanel();
	
	public Login(DataInputStream fs,DataOutputStream ts){
		fromServer=fs;
		toServer=ts;

		setTitle("Online Dictionary");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setVisible(true);	
		setLayout(new GridLayout(4,1,0,0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		namePanel.label_name.setText("Name");
		//namePanel.text_name.setText("Enter name without space");
		passPanel.label_name.setText("Password");
		//passPanel.text_name.setText("Enter 6-18 letters or numbers");
	
		add(namePanel);
		add(passPanel);
		add(buttonPanel);
		add(tipPanel);
	}
	
	class ButtonPanel extends JPanel{
		private JButton jbtlog=new JButton("Log in");
		private JButton jbtreg=new JButton("Register");
		public ButtonPanel(){
			jbtlog.setVisible(true);	
			jbtreg.setLayout(new FlowLayout(FlowLayout.CENTER,30,30));
			setBackground(Color.WHITE);
			add(jbtlog);
			add(jbtreg);
			jbtlog.setPreferredSize(new Dimension(100, 20));
			jbtreg.setPreferredSize(new Dimension(100, 20));
			
			jbtlog.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String name=namePanel.text_name.getText();
					String pass=passPanel.text_name.getText();
					
					tipPanel.label_name.setText("");
					if(name.length()>18||name.length()<6){
						tipPanel.setVisible(true);
						tipPanel.label_name.setText("Length of name should between 6 and 18\n");
						return;
					}
					if(!IsName(name)){
						tipPanel.setVisible(true);
						tipPanel.label_name.setText("Name should not include space\n");
						return;
					}
					if(pass.length()>18||pass.length()<6){
						tipPanel.setVisible(true);
						tipPanel.label_name.setText("Length of password should between 6 and 18\n");
						return;
					}
					if(!IsPassword(pass)){
						tipPanel.setVisible(true);
						tipPanel.label_name.setText("Password should only include letters and numbers\n");
						return;
					}
					
					tipPanel.setVisible(false);
					info=1+" "+name+" "+pass;
					
					try {
						//send login info to server
						toServer.writeUTF(info);
						toServer.flush();
						
						//get return tips from server
						info=fromServer.readUTF(); 
						
						//login/register success
						//goto word panel
						handle.setVisible(false);
						//DicPanel dic=new DicPanel(fromServer,toServer);
						
					} catch (IOException ex) {
						System.err.println(ex);
					}
					
				}
				
			});
			
		}
	}
			
	class NameInputPanel extends JPanel{
		private JLabel label_name = new JLabel("Tip");
		private JTextField text_name = new JTextField("Enter name without space");
		private boolean start=false;
		public NameInputPanel(){
			setLayout(new FlowLayout(FlowLayout.CENTER,10,30));
			setBackground(Color.WHITE);
			setVisible(true);	
			add(label_name);
			add(text_name);
			label_name.setPreferredSize(new Dimension(60, 20));
			text_name.setPreferredSize(new Dimension(200, 20));
			text_name.setForeground(Color.GRAY);
			
			text_name.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					if(start==false){
						text_name.setText(null);
						text_name.setForeground(Color.BLACK);
						start=true;
					}
					
				}
			});
		}
	}
	class PassInputPanel extends JPanel{
		private JLabel label_name = new JLabel("Tip");
		private JTextField text_name = new JTextField("Enter 6-18 letters or numbers");
		private boolean start=false;
		public PassInputPanel(){
			setLayout(new FlowLayout(FlowLayout.CENTER,10,30));
			setBackground(Color.WHITE);
			
			setVisible(true);	
			add(label_name);
			add(text_name);
			label_name.setPreferredSize(new Dimension(60, 20));
			text_name.setPreferredSize(new Dimension(200, 20));
			text_name.setForeground(Color.GRAY);
			
			text_name.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					if(start==false){
						text_name.setText(null);
						text_name.setForeground(Color.BLACK);
						start=true;
					}
					
				}
			});
		}
	}
	
	class TipPanel extends JPanel{
		private JLabel label_name = new JLabel();
		public TipPanel(){
			setLayout(new FlowLayout(FlowLayout.CENTER,1,1));
			setBackground(Color.WHITE);
			//setVisible(true);
			add(label_name );
			label_name.setBackground(Color.LIGHT_GRAY);
			label_name.setBorder(null);
			label_name.setFont(new Font("Serif",Font.BOLD,12));
			label_name.setPreferredSize(new Dimension(300, 10));
		}
	}

	public static boolean IsPassword(String str) {
		String pattern = "[A-Za-z0-9]+";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		return m.matches();
	}
	public static boolean IsName(String str) {
		String pattern = " ";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		return (!m.find());
	}
}