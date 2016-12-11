import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class multiServer extends JFrame{
	//text area for infomation
	private JPanel serverPanel=new JPanel();
	private JTextField userInfo=new JTextField();
	private JTextField jta0=new JTextField();
	private JTextArea jta=new JTextArea();
	private DB_Server DBs=new DB_Server();

	public static void main(String[] args){
		new multiServer();
	}
	
	public multiServer(){
		//place text area on the frame
		add(serverPanel);
		serverPanel.setLayout(new BorderLayout());
		serverPanel.add(userInfo,BorderLayout.NORTH);
		serverPanel.add(jta,BorderLayout.CENTER);
		serverPanel.add(jta0,BorderLayout.SOUTH);
		setBackground(new Color(240, 250, 255));
		serverPanel.setBackground(new Color(240, 250, 255));
		serverPanel.setVisible(true);
		// frame size and location
		setTitle("OnlineDictServer");
		setSize(500,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		userInfo.setVisible(true);
		jta0.setVisible(true);
		jta.setVisible(true);
		userInfo.setEditable(false);
		jta0.setEditable(false);
		//jta.setEditable(false);
		userInfo.setPreferredSize(new Dimension(440, 40));
		userInfo.setBackground(new Color(240, 250, 255));
		jta0.setPreferredSize(new Dimension(440, 40));
		jta0.setBackground(new Color(240, 250, 255));
		jta.setPreferredSize(new Dimension(440, 400));
		jta.setBackground(new Color(240, 250, 255));
		
		try{
			//Create a server socket
			ServerSocket serverSocket = new ServerSocket(8000);
			jta0.setText("Server startes at "+new Date()+'\n');
			
			//Number a client
			int clientNo=1;
			while(true){
				//Listen for a new connection request
				Socket socket=serverSocket.accept();
				
				//Display info of client number
				jta.setText("Starting thread for client"+clientNo+"at"+new Date()+'\n');
				
				//Find the client's host name and IP address
				InetAddress inetAddress=socket.getInetAddress();
				jta.setText(jta.getText()+"Client"+clientNo+"'s host name is"+inetAddress.getHostName()+"\n");
				jta.setText(jta.getText()+"Client"+clientNo+"'s IP Address is"+inetAddress.getHostAddress()+"\n");
				
				//Create a new task for the connection
				HandleAClient task=new HandleAClient(socket);
				//start the new thread
				//task.run();
				Thread t=new Thread(task); 
				t.start();
				
				//Increment clientNo
				clientNo++;
				
				//String check=(DBs.Check_DB("10"));//get client's num
				//String[] cNum=new String[2];
				//cNum=check.split(" ");
				//userInfo.setText("Total Client Number: "+cNum[0]+"		Current Online Client Number: "+cNum[1]);
			}
			
		}
		catch(IOException ex){
			System.err.println(ex);
		}
	}
	
	//Inner class
	//Define the thread class for handing new connection
	class HandleAClient implements Runnable{
		private Socket socket;//A connected socket
		
		//construct a thread
		public HandleAClient(Socket socket){
			this.socket=socket;
		}
		
		//Run a thread
		public void run(){
			try{
				//Create data input and output streams
				
				DataInputStream inputFromClient=new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient=new DataOutputStream(socket.getOutputStream());
				
				//Continuously serve the client
				while(true){
					//Receive data from the client
					String rec=inputFromClient.readUTF();
					jta.setText("info received from client : "+rec+"\n");
					//handle data
					/*word: 
					 * reg:0_name_pass
					 * log:1_name_pass
					 * word:2_word
					 * like:3_word_n1_n2_n3
					 * word card
					 * exit:5_name
					 * 
					 * clentNum:10
					 * etc...
					 */
					DB_Server se=new DB_Server();
					String check2=(se.Check_DB(rec));
					
					//jta.setText(jta.getText()+"info received from database : "+check2+"\n");
					String check=(DBs.Check_DB("10"));//get client's num
					String[] cNum=new String[2];
					cNum=check.split(" ");
					userInfo.setText("Total Client Number: "+cNum[0]+"		Current Online Client Number: "+cNum[1]);
					
					//d data back to the client
					String set=check2;
					outputToClient.writeUTF(set);
					jta.setText(jta.getText()+"info sent to client : "+set+"\n\n");
				}
			}
			catch(IOException e){
				System.err.println(e);
			}
		}
	}
}
