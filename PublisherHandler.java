import java.io.*;
import java.net.Socket;
import java.util.HashMap;

// ClientHandler class
class PublisherHandler extends Thread 
{
    long endTime = 120000; //time to end bidding in milliseconds
    final Socket s;
    final long time;
    final csvRead csv;
      
  
    // Constructor
    public PublisherHandler(Socket s, long time, csvRead csv) 
    {
        this.s = s;
        this.time = time;
        this.csv = csv;
    }
  
    @Override
    public void run() 
    {
        try
        {
            PrintWriter out = new PrintWriter(s.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String model = in.readLine();
            //implementing the handler for publishers
            if(model.equals("publisher"))
            {
                while(true)
                {
                    //handling the decision
                    String decision = in.readLine();
                    if("0".equals(decision))
                    {
                        break;
                    }
                    //sending the current profit to publisher
                    if("1".equals(decision))
                    {

                        String symbol = in.readLine();
                        if(csv.checkStocks(symbol) == 1)
                        {
                            out.println("Current profit: " + csv.getProfit(symbol));
                        }
                        if(csv.checkStocks(symbol) == 0)
                        {
                            out.println("-1");
                        }

                    }
                    //updating profits
                    if("2".equals(decision))
                    {
                        String symbol = in.readLine();
                        String security = in.readLine();
                        String profit = in.readLine();

                        if(csv.checkStocks(symbol) == 1 && csv.checkSecurity(symbol, Integer.parseInt(security)) == 1)
                        {   
                            //updating profit
                            csv.updateProfit(symbol, Integer.parseInt(profit));
                            out.println("New profit: " + csv.getProfit(symbol));
                        }
                        //replying when symbol not found
                        else
                        {
                            out.println("-1");
                        }
                    }
                }
            }
            //implementing the handler for subscribers
            if (model.equals("subscriber"))
            {
                String[] symbols = in.readLine().split(" ");
                int check = 1;
                //checking the availability of symbols
                for (String symbol : symbols) {
                    if (csv.checkStocks(symbol) == 0) {
                        check = 0;
                    }
                }
                if(check == 0)
                {
                    out.println("-1");
                }
                out.println("Subscribed Successfully");
                
                //assigning the current profits to a hashmap for subscribed symbols 
                HashMap <String, Integer> subs = new HashMap<>(); 
                for(String symbol : symbols)
                {
                    subs.put(symbol, csv.getProfit(symbol));
                }
                //sending notification to subscriber when profits are updated
                while(true)
                {
                    for(String symbol : symbols)
                    {
                        if(subs.get(symbol) != csv.getProfit(symbol)){
                            subs.put(symbol, csv.getProfit(symbol));
                            out.println("Profit of " + symbol + " is updated to " + subs.get(symbol));     
                        }
                    }
                }
                
            }
        
        }
        catch (IOException e)
        {   
        }
        }
    
}


