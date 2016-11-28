package Client; 
import java.net.InetAddress ;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client{
	//IOstream
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	
	public static void main(String[] args){
		new Client();
	}
	
	public Client(){
		try{
			//create a socket to connect the server
			Socket socket=new Socket("114.212.133.81",8000);
			
			
			//create an input stream to receive data from server
			fromServer=new DataInputStream(socket.getInputStream());
			//create an output stream to send data to server
			toServer=new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException ex){
			System.err.println(ex);
		}
		
		//Panel p to log in/register
		LoginPanel login=new LoginPanel(fromServer,toServer);
				
	}
	

}
