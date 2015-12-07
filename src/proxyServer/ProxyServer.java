package proxyServer;

import java.net.*;
import java.io.*; 

public class ProxyServer extends Thread { 
 
 public Socket client;

 public static void main(String[] args) throws IOException { 
    ServerSocket serverSocket = null; 
    int port = 8282;
    try { 
         serverSocket = new ServerSocket(port); 
         System.out.println (port+ " Portundan baglanti saglandi");
         
         while (true){
                  new ProxyServer (serverSocket.accept()); 
                 }
         } 
         
    catch (IOException e) { 
         System.err.println(port+ " Portuna baglanti basarisiz oldu."); 
         System.exit(1); 
        } 
    
    finally{
         serverSocket.close(); 
     }
   }

 public ProxyServer (Socket socket) {
    client = socket;
    start();
 } 

 public void run() {
    try { 
         PrintWriter out = new PrintWriter(client.getOutputStream()); 
         BufferedReader in = new BufferedReader( 
                 new InputStreamReader( client.getInputStream())); 

         String inputLine; 
         while ((inputLine = in.readLine()) != null) { 
              System.out.println (inputLine); 
              out.println(inputLine); // burdaki line'lari ekrana degil http request'e bas!
         }

         out.close(); 
         in.close(); 
         client.close(); 
    } 
    catch (IOException e) { 
         System.err.println("Baglantida bir sorun olustu. Dinleme basarisiz...");
         System.exit(1); 
    } 
  }
} 