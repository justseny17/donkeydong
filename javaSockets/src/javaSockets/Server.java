package javaSockets;

import java.net.*;
import java.io.*;

public class Server {
	
	private ServerSocket serverObject = null;
	private Socket newSocket1 = null;
        private Socket newSocket2 = null;
        private DataInputStream clientInput1 = null;
        private DataInputStream clientInput2 = null;
        private DataOutputStream toClient2 = null;
        private DataOutputStream toClient1 = null;
        
	public static void main(String[] args) {
		Server serverObject = new Server(8000);
	}
	
	public Server(int port)
    {
        try
        {
            serverObject = new ServerSocket(port);
            
            System.out.println("I am the TCP Server. I am listening on port " + port + ", and waiting for a client.");
            
            newSocket1 = serverObject.accept();
            toClient1 = new DataOutputStream(newSocket1.getOutputStream());
            System.out.println("I received a connection from a client.");
            clientInput1 = new DataInputStream(new BufferedInputStream(newSocket1.getInputStream()));
            
            System.out.println("I am waiting for a second client.");
            
            newSocket2 = serverObject.accept();
            toClient2 = new DataOutputStream(newSocket2.getOutputStream());
            System.out.println("I received a connection from a second client.");
            clientInput2 = new DataInputStream(new BufferedInputStream(newSocket2.getInputStream()));
            
            String clientData1 = "void";
            String clientData2 = "void";
            
            while (!clientData1.equals("exit") && (!clientData2.equals("exit")))
            {
                try
                {
                    clientData1 = clientInput1.readUTF();      // Unicode Translation Format
                    System.out.println("Client 1 has written: ");
                    System.out.println(clientData1);
                    toClient2.writeUTF("Client 1 has written: \n" + clientData1);
                    
                    clientData2 = clientInput2.readUTF();
                    System.out.println("Client 2 has written: ");
                    System.out.println(clientData2);
                    toClient1.writeUTF("Client 2 has written: \n" + clientData2);
  
                }
                catch(IOException io)
                {
                    System.out.println(io);
                }
            }
            
            System.out.println("The client has asked to leave.");
            System.out.println("I am now closing the socket connection.");
            
            newSocket1.close();
            newSocket2.close();
        }
        catch(IOException io)
        {
            System.out.println(io);
        }
    }
        
}
