import java.net.Socket;
import java.io.*;

public class Client {
    
    public static void main(String[] args) {
        // TODO code application logic here
        try
        {
            System.out.println("Client started");
            Socket soc = new Socket("localhost",2021);
            
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));   
            PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
            out.println("publisher");
            //verifying ID
            System.out.print("Enter your ID: ");
            String id = userInput.readLine();
            out.println(id);
            System.out.println(in.readLine());
            
            String decision;

            while(true)
            {
                System.out.println("\nTo view prices:    Enter 1\nTo bid for stocks: Enter 2\nTo exit:           Enter 0\n");
                decision = userInput.readLine();
                
                //exit
                if("0".equals(decision))
                {
                    out.println(decision);
                    break;
                }
                //view prices
                if("1".equals(decision))
                {
                    out.println(decision);
                    //getting highest bid
                    System.out.print("\nEnter a company symbol: ");
                    String symbol = userInput.readLine();
                    out.println(symbol);
                    System.out.println(in.readLine());
                    
                }
                //bid for prices
                if("2".equals(decision))
                {
                    out.println(decision);
                    System.out.print("\nEnter a company symbol: ");
                    String symbol = userInput.readLine();
                    System.out.print("Enter a new bid: ");
                    String bid = userInput.readLine();
                    out.println(symbol);
                    out.println(bid);
                    System.out.println(in.readLine());
                }
            }
                
        }
        catch(IOException e)
        {
            
        }
}
}
