import java.net.Socket;
import java.io.*;

public class Publisher {
    
    public static void main(String[] args) {
        // TODO code application logic here
        try
        {
            System.out.println("Publisher started");
            Socket soc = new Socket("localhost",2022);
            
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));   
            PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
            
            String decision;
            out.println("publisher");
            while(true)
            {
                System.out.println("\nTo view profits:    Enter 1\nTo update profits: Enter 2\nTo exit:           Enter 0\n");
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
                    //getting profit
                    System.out.print("\nEnter a company symbol: ");
                    String symbol = userInput.readLine();
                    out.println(symbol);
                    System.out.println(in.readLine());
                    
                }
                //updating profits
                if("2".equals(decision))
                {
                    out.println(decision);
                    System.out.print("\nEnter a company symbol: ");
                    String symbol = userInput.readLine();
                    System.out.print("Enter the security number: ");
                    String security = userInput.readLine();
                    System.out.print("Enter the profit: ");
                    String profit = userInput.readLine();
                    out.println(symbol);
                    out.println(security);
                    out.println(profit);
                    System.out.println(in.readLine());
                    
                }
            }
                
        }
        catch(IOException e)
        {
            
        }
}
}
