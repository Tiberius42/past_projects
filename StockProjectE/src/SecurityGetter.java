import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
   This class defines methods to receive user input, retrieve JSON from the IEX API, construct SecurityInfo instances
   based on that JSON, and output information about the selected securities. 
*/
class SecurityGetter{
   private List<String> symbolList = new ArrayList<String>();
   private List<SecurityInfo> securityList = new ArrayList<SecurityInfo>();
   
   /**
      Constructor for SecurityGetter.
   */
   public SecurityGetter() throws FileNotFoundException, IOException{
      getSymbols();
      addSecurities();
   }
   
   /**
      Returns a List of security tickers.
   */
   public List<String> getSymbolList(){
      return symbolList;
   }
   
   /**
      Prompts user input for security tickers.
   */
   private void getSymbols(){
      Scanner console = new Scanner(System.in);
      System.out.print("Input one security symbol at a time (e.g. AAPL; enter n to stop): ");
      String symbol = console.next().toLowerCase(); 
      while (!(symbol.equals("n"))){
         symbolList.add(symbol);
         System.out.print("Input one security symbol at a time (e.g. AAPL; enter n to stop): ");
         symbol = console.next().toLowerCase(); 
      }
      System.out.println();
      console.close();
   }
   
   /**
      Generates instances of SecurityInfo.
   */
   private void addSecurities() throws FileNotFoundException, IOException{
      for (int i = 0; i < symbolList.size(); i++){
         SecurityInfo si =  generateSpecificSecurity(symbolList.get(i));
         
         if (si != null){
            securityList.add(si);
         }
      }
   }
   
   /**
      Retrieves JSON from API. This method takes a security ticker and a "type" paramter for the type of
      information to be retrieved from the API.
   */
   private String getJson(String stockSymbol, String type) throws FileNotFoundException, IOException{   
      String address = "https://api.iextrading.com/1.0/stock/" + stockSymbol + "/" + type;
      URL pageLocation = new URL(address);
      
      //https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html
      try(BufferedReader in = new BufferedReader(new InputStreamReader(pageLocation.openStream()))){
         String json = in.readLine();         
         return json;
      }
      catch(IOException e){
         return "{}";
      }
   }
   
   /**
      Prints company, price, and market information for all the valid security tickers input by the user.
   */
   public void printCompanyInfo(){
      for (int i = 0; i < securityList.size(); i++){
         securityList.get(i).getCompanyInfo();
         securityList.get(i).getQuoteInfo();
      }
   }
   
   /**
      Returns an instance of SecurityInfo based on the paramater "symbol"'s (ticker) "issueType" tag from the API. 
   */
   private SecurityInfo generateSpecificSecurity(String symbol) throws FileNotFoundException, IOException{
      
      String companyJson = getJson(symbol, "company");
      String quoteJson = getJson(symbol, "quote");
      
      Gson gson = new Gson();
      
      CompanyDTO compDTO = gson.fromJson(companyJson, CompanyDTO.class);
      QuoteDTO quoteDTO = gson.fromJson(quoteJson, QuoteDTO.class);
      
      if (compDTO.issueType == null){
         return null;
      }
      
      if (compDTO.issueType.equals("cs")){
         SecurityInfo temp = new Stock(compDTO, quoteDTO);
         return temp;
      }
      
      if (compDTO.issueType.equals("et")){
         SecurityInfo temp = new ETF(compDTO, quoteDTO);
         return temp;
      } 
      
      // the "ce" issueType tag covers securities other than bonds, but for simplicity's sake 
      // I just count them as bonds
      if (compDTO.issueType.equals("ce")){
         SecurityInfo temp = new Bond(compDTO, quoteDTO);
         return temp;
      } 
      
      SecurityInfo temp = new Security(compDTO, quoteDTO);
      
      return temp;
   }
}