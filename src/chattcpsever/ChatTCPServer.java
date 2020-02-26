
package chattcpsever;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatTCPServer{
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static int numOfClients = 10;
    private static clientThread[] threads = new clientThread[numOfClients];
    
    
    public static void main(String[] args) throws IOException{
        int portnumber = 2222;
        try{
            serverSocket = new ServerSocket(portnumber);
            clientSocket = serverSocket.accept();
        }catch (IOException ex){

}
        int i;
        for (i = 0; i < numOfClients; i++) {
            if(threads[i] == null){
                (threads[i] = new clientThread(clientSocket, threads)).start();  
                break;
            }
          
        }
            if(i == numOfClients){
                PrintStream os = new PrintStream(clientSocket.getOutputStream());
                os.println("Server busy - try later");
            }
}
}

class clientThread extends Thread{
    private Socket clientSocket = null;
    private Scanner sis = null;
    private clientThread[] threads = null;
    private PrintStream os = null;
    public clientThread(Socket clientSocket,clientThread[] threads){
        this.clientSocket = clientSocket;
        this.threads = threads;
        
    }
    public void run(){
        try 
        {
            //læs fra socket
            sis = new Scanner(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
        } 
         catch (IOException ex) 
         {
            Logger.getLogger(ChatTCPServer.class.getName()).log(Level.SEVERE, null, ex);
         }    
            os.println("Enter your name: ");
            String name = sis.nextLine();
            
            while(true){
                String line = sis.nextLine();
                
                for (int i = 0; i < threads.length; i++) 
                {
                    if(threads[i]!=null){
                        threads[i].os.println("<" + name + ">: " + line);
                    }
                }
                
                //skriv line til alle clienter ved at gennemløbe threads
            }

    }
}
