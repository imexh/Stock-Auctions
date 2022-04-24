import java.io.*;
import java.text.ParseException;
import java.util.HashMap;

public final class csvRead {
        
    HashMap stocks;
        
    csvRead(String csv, HashMap stocks)throws ParseException
    {
        String line;  
        String splitBy = ",";
        String[] read_line;
        this.stocks = stocks;
        
        try   
        {  
            //parsing a CSV file into BufferedReader class constructor  
            BufferedReader br = new BufferedReader(new FileReader(csv));  
            //Reading the csv line by line
            br.readLine();
            
            while ((line = br.readLine()) != null) 
            {  //adding the lines to the hashmap
                read_line = line.split(splitBy);              
                addStocks(read_line[0], read_line[1], read_line[2], read_line[3]);

            }
        }   
        
        catch (IOException e)   
        {  
        } 
        
    }
    
    void addStocks(String name, String price, String security, String profit)
    {
        stocks.put(name, new String[] {price, security, profit});
    }
    
    //function to remove stocks
    void removeStocks(String name)
    {
        stocks.remove(name);
    }
    //function to view all the details of a stock
    String[] getStocks(String name)
    {
        return (String[]) stocks.get(name);
    }
    //function to view the price of a stock
    float getPrice(String name)
    {
        return Float.parseFloat(getStocks(name)[0]);
    }
    //function to change the price of a stock
    void updatePrice(String name, float new_price)
    {
        stocks.put(name, new String[] {String.valueOf(new_price), getStocks(name)[1], getStocks(name)[2]});
    }
    //function to check stock availability
    int checkStocks(String name)
    {
        if(stocks.containsKey(name) == true)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    //function to view security number
    int getSecurity(String name)
    {
        return Integer.parseInt(getStocks(name)[1]);
    }
    //function to update profit
    void updateProfit(String name, int new_profit)
    {
        stocks.put(name, new String[] {getStocks(name)[0], getStocks(name)[1], String.valueOf(new_profit)});
    }
    //function to check security
    int checkSecurity(String name, int security)
    {
        if(security == getSecurity(name))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    //function to ge profit
    int getProfit(String name)
    {
        return Integer.parseInt(getStocks(name)[2]);
    }
    
}