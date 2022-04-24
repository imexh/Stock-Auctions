import java.io.*;
import java.net.Socket;
import java.util.HashMap;

// ClientHandler class
class ClientHandler extends Thread 
{
    long endTime = 120000; // time to end bidding in milliseconds
    final Socket s;
    final long time;
    final csvRead csv;
      
  
    // Constructor
    public ClientHandler(Socket s, long time, csvRead csv) 
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
                //accepting id
                String id = in.readLine();
                out.println(id + " is accepted successfully");

                while(true)
                {
                    //handling the decision
                    String decision = in.readLine();
                    if("0".equals(decision))
                    {
                        break;
                    }
                    //sending the highest bid to client
                    if("1".equals(decision))
                    {

                        String symbol = in.readLine();
                        if(csv.checkStocks(symbol) == 1)
                        {
                            out.println("Current highest bid: " + csv.getPrice(symbol));
                        }
                        if(csv.checkStocks(symbol) == 0)
                        {
                            out.println("-1");
                        }

                    }
                    //accepting new bids
                    if("2".equals(decision))
                    {
                        long startTime = System.currentTimeMillis();
                        String symbol = in.readLine();
                        String bid = in.readLine();
                        //closing bidding after endTime
                        if((System.currentTimeMillis()-time) > endTime)
                        {
                            out.println("Bidding is closed now!!");
                            continue;
                        }

                        if(csv.checkStocks(symbol) == 1)
                        {   
                            //replying if new bid is less than or equal to previous highest bid
                            //bid is not valid if it is not submitted within 30 seconds of selecting the bidding option
                            if(csv.getPrice(symbol) >= Float.parseFloat(bid) || (System.currentTimeMillis()-startTime) > 30000)
                            {
                                out.println("-2");
                                continue;
                            }
                            //updating new bid
                            csv.updatePrice(symbol, Float.parseFloat(bid));
                            out.println("New price: " + csv.getPrice(symbol));
                            if(endTime - (System.currentTimeMillis()-time) < 60000)
                            {
                                endTime += 60000;
                            }
                        }
                        //replying when symbol not found
                        if(csv.checkStocks(symbol) == 0)
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

                    //assigning the current prices to a hashmap for subscribed symbols 
                    HashMap <String, Float> subs = new HashMap<>(); 
                    for(String symbol : symbols)
                    {
                        subs.put(symbol, csv.getPrice(symbol));
                    }
                    //sending notification to subscriber when prices are updated
                    while(true)
                    {
                        for(String symbol : symbols)
                        {
                            if(subs.get(symbol) != csv.getPrice(symbol)){
                                subs.put(symbol, csv.getPrice(symbol));
                                out.println("Price of " + symbol + " is updated to " + subs.get(symbol));     
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


