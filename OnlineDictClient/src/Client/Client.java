package Client; 
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
			Socket socket=new Socket("localhost",7000);
			
			//create an input stream to receive data from server
			fromServer=new DataInputStream(socket.getInputStream());
			//create an output stream to send data to server
			toServer=new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException ex){
			System.err.println(ex);
		}
		
		//Panel p to log in/register
		Login login=new Login(fromServer,toServer);
				
	}
	

}
