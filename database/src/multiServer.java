
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;

import javax.swing.*;

public class multiServer extends JFrame{
	//text area for infomation
	private JTextArea jta=new JTextArea();

	public static void main(String[] args){
		new multiServer();
	}
	
	public multiServer(){
		//place text area on the frame
		setLayout(new BorderLayout());
		add(new JScrollPane(jta),BorderLayout.CENTER);
		//set frame size and location
		setTitle("OnlineDictServer");
		setSize(500,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		try{
			//Create a server socket
			ServerSocket serverSocket = new ServerSocket(8000);
			jta.append("Server startes at "+new Date()+'\n');
			
			//Number a client
			int clientNo=1;
			while(true){
				//Listen for a new connection request
				Socket socket=serverSocket.accept();
				
				//Display info of client number
				jta.append("Starting thread for client"+clientNo+"at"+new Date()+'\n');
				
				//Find the client's host name and IP address
				InetAddress inetAddress=socket.getInetAddress();
				jta.append("Client"+clientNo+"'s host name is"+inetAddress.getHostName()+"\n");
				jta.append("Client"+clientNo+"'s IP Address is"+inetAddress.getHostAddress()+"\n");
				
				//Create a new task for the connection
				HandleAClient task=new HandleAClient(socket);
				//start the new thread
				//task.start();
				task.run();
				
				//Increment clientNo
				clientNo++;
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
					//handle data
					/*word: 
					 * reg:0_name_pass
					 * log:1_name_pass
					 * word:2_word
					 * like:3_word_n1_n2_n3
					 * etc...
					 */
					DB_Server se=new DB_Server();
					System.out.println(se.Check_DB(rec));
					//send data back to the client
					String set=rec;
					outputToClient.writeUTF(set);
					
					jta.append("info received from client"+rec+"\n");
					jta.append("info sent to client"+set+"\n\n");
				}
			}
			catch(IOException e){
				System.err.println(e);
			}
		}
	}
}
