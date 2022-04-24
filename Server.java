import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

// Server class
public class Server extends Thread
{       final static HashMap<String, String[]> stocks = new HashMap<>();
        static csvRead csv;
	public static void main(String[] args) throws IOException, ParseException
	{
                Server thread = new Server();
                thread.start();
                long time = System.currentTimeMillis(); //starting time
		//server is listening on port 2022
                //server for publishers
		ServerSocket ss = new ServerSocket(2022);
                
                System.out.println("Waiting for clients...");
                
		//running infinite loop for getting clients
		while (true)
		{
			Socket s = null;
			
			try
			{
				//accepting new client requests
				s = ss.accept();
				
				System.out.println("A new client is connected to local port 2022 ");
				
				//creating a new thread object
				Thread t = new PublisherHandler(s, time, csv);

				//using start method
				t.start();
				
			}
			catch (IOException e){
				s.close();
			}
		}
	}

    public Server() throws ParseException {
        Server.csv = new csvRead("stocks.csv", stocks);
    }
        @Override
        public void run() {
                long time = System.currentTimeMillis();
                
                ServerSocket ss;
                 try {
                        //server is listening on port 2021
                        //server for clients
                        ss = new ServerSocket(2021);
                        //running infinite loop for getting clients
                        while (true)
                        {
                                Socket s = null;

                                try
                                {
                                        //accepting new client requests
                                        s = ss.accept();

                                        System.out.println("A new client is connected to local port 2021" );

                                        //createing a new thread object
                                        Thread t = new ClientHandler(s, time, csv);

                                        //using start method
                                        t.start();

                                }
                                catch (IOException e){
                                        s.close();
                                }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
        }
           
}

