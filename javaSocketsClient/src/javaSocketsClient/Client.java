package javaSocketsClient;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    
	private Socket newSocket = null;
        private DataOutputStream toServer = null;
        private DataInputStream fromServer = null;
	
	public static void main(String[] args) {
            //
            String sel;
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter an address to connect to."
                    + "\n(Hit Enter to try localhost)");
            sel = scan.nextLine();
            if (sel.equals("")) {
                    sel = "127.0.0.1";
            }
            System.out.println("Connecting to " + sel + ":" + 8000);
            //
		Client client = new Client(sel, 8000);
	}
	
	public Client(String address, int port)
    {
        try
        {
        	System.out.println("I am the TCP Client. I am about to establish a connection with the server.");
        	
        	newSocket = new Socket(address, port);
                
            toServer = new DataOutputStream(newSocket.getOutputStream());
            fromServer = new DataInputStream(new BufferedInputStream(newSocket.getInputStream()));
            
            System.out.println("I am now connected to the server.");
        }
        catch(UnknownHostException un)
        {
            System.out.println("Connection failed:\n" + un);
        }
        catch(IOException io)
        {
            System.out.println("Connection failed:\n" + io);
        }
        
        String clientData = "void", serverData = "void";
        Scanner scan = new Scanner(System.in);
        
        while (!clientData.equals("exit"))
        {
            try
            {
            	System.out.println("Enter data that you want me to send to the server: ");
            	clientData = scan.nextLine();
            	toServer.writeUTF(clientData);
                System.out.println("awaiting response");
                serverData = fromServer.readUTF();
                System.out.println(serverData);
            }
            catch(IOException io)
            {
                System.out.println(io);
            }
        }
        
        try
        {
            toServer.close();
            newSocket.close();
        }
        
        catch(IOException io)
        {
            System.out.println(io);
        }
    }

}