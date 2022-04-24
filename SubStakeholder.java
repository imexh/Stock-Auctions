import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SubStakeholder {
    public static void main(String[] args) throws IOException {
            //connecting to server
            System.out.println("Subscribing Stakeholder started");
            Socket soc = new Socket("localhost",2021);
            
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));   
            PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
            //asking user for company symbols
            out.println("subscriber");
            System.out.println("Enter company symbols to subscribe: ");
            String input = userInput.readLine();
            out.println(input);
            String availability = in.readLine();
            System.out.println(availability);
            //getting notifications
            if(!availability.equals("-1"))
            {
                while(true)
                {
                    System.out.println(in.readLine());
                }
            }
            
    }
}
