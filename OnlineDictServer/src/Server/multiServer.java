package Server;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class multiServer extends JFrame{
	//text area for infomation
	private JPanel serverPanel=new JPanel();
	private JTextField clientNumInfo=new JTextField();
	private JTextField serverInfo=new JTextField();
	private JTextArea jta=new JTextArea();
	private JScrollPane scrollPane=new JScrollPane(jta);
	private JTextArea jta1=new JTextArea();
	private JScrollPane scrollPane1=new JScrollPane(jta1);
	private DB_Server DBs=new DB_Server();

	public static void main(String[] args){
		new multiServer();
	}
	
	public multiServer(){
		//place text area on the frame
		add(serverPanel);
		JPanel panel=new JPanel();
		panel.add(scrollPane);
		panel.add(scrollPane1);
		panel.setBackground(new Color(240, 250, 255));
		serverPanel.setLayout(new BorderLayout());
		serverPanel.add(clientNumInfo,BorderLayout.NORTH);
		serverPanel.add(panel,BorderLayout.CENTER);
		serverPanel.add(serverInfo,BorderLayout.SOUTH);
		serverPanel.setBackground(new Color(240, 250, 255));
		serverPanel.setVisible(true);
		
		//set frame size and location
		setTitle("OnlineDictServer");
		setSize(500,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
		clientNumInfo.setVisible(true);
		clientNumInfo.setEditable(false);
		clientNumInfo.setPreferredSize(new Dimension(440, 20));
		clientNumInfo.setBackground(new Color(240, 250, 255));
		serverInfo.setVisible(true);
		serverInfo.setEditable(false);
		serverInfo.setPreferredSize(new Dimension(440, 20));
		serverInfo.setBackground(new Color(240, 250, 255));
		panel.setSize(new Dimension(500, 500));
		scrollPane.setBorder(null);
		scrollPane1.setBorder(null);
		scrollPane.setPreferredSize(new Dimension(480, 200));
		scrollPane1.setPreferredSize(new Dimension(480, 300));
		jta.setVisible(true);
		jta.setEditable(false);
		//jta.setPreferredSize(new Dimension(400, 22000));
		jta.setBackground(new Color(240, 250, 255));
		jta1.setVisible(true);
		jta1.setEditable(false);
		//jta1.setPreferredSize(new Dimension(400, 32000));
		jta1.setBackground(new Color(240, 250, 255));
		
		jta.setText("ClientNo	ClientName");
		String check=(DBs.Check_DB("10"));//get client's num
		String[] cNum=new String[2];
		cNum=check.split(" ");
		clientNumInfo.setText("Total Client Number: "+cNum[0]+"		   Current Online Client Number: "+cNum[1]);
		
		
		try{
			//Create a server socket
			ServerSocket serverSocket = new ServerSocket(8000);
			String serverStartTime="Server startes at "+new Date();
			serverInfo.setText(serverStartTime);
			
			//Number a client
			int clientNo=0;
			while(true){
				//Listen for a new connection request
				Socket socket=serverSocket.accept();
				
				//Find the client's host name and IP address
				InetAddress inetAddress=socket.getInetAddress();
				//Display info of client number
				jta1.setText(jta1.getText()+"Starting thread for a client"+'\n');
				jta1.setText(jta1.getText()+"New client's host name is"+inetAddress.getHostName()+"\n");
				jta1.setText(jta1.getText()+"New client's IP Address is"+inetAddress.getHostAddress()+"\n");
				//Create a new task for the connection
				HandleAClient task=new HandleAClient(socket);
				//start the new thread
				Thread t=new Thread(task); 
				t.start();
				
				//Increment clientNo
				clientNo++;
				serverInfo.setText(serverStartTime+"	      Total flux: "+clientNo);
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
					//jta.setText("info received from client : "+rec+"\n");
					//handle data
					/*word: 
					 * reg:0_name_pass
					 * log:1_name_pass
					 * word:2_word
					 * like:3_word_n1_n2_n3
					 * word card
					 * exit:5_name
					 * 
					 * clientNum:10
					 * online client list:11
					 * etc...
					 */
					DB_Server se=new DB_Server();
					jta1.setText(jta1.getText()+"info received from client : "+rec+"\n");
					String check2=(se.Check_DB(rec));
					
					//update server info
					String check=(DBs.Check_DB("10"));//get client's num
					String[] cNum=new String[2];
					cNum=check.split(" ");
					clientNumInfo.setText("Total Client Number: "+cNum[0]+"		   Current Online Client Number: "+cNum[1]);
					int cOnNum=Integer.parseInt(cNum[1]);
					check=(DBs.Check_DB("11"));//get online client's list
					String[] currentClientList=new String[cOnNum];
					currentClientList=check.split(" ");
					jta.setText("ClientNo	ClientName");
					for(int i=0;i<cOnNum;i++)
						jta.setText(jta.getText()+'\n'+(i+1)+"	"+currentClientList[i]);
					
					//send data back to the client
					String set=check2;
					outputToClient.writeUTF(set);
					jta1.setText(jta1.getText()+"info sent to client : "+set+"\n\n");
				}
			}
			catch(IOException e){
				System.err.println(e);
			}
		}
	}
}
