/**
   This subclass of Security represents ETFs (exchange traded funds).
*/
class ETF extends Security{
   
   /**
      Constructor for ETF.
   */
   public ETF(CompanyDTO companyInfo, QuoteDTO quoteInfo){
      super(companyInfo, quoteInfo);
   }
   
   /**
      Prints company information.
   */
   public void getCompanyInfo(){
      System.out.println("This security is an ETF.");
      System.out.println(companyInfo.companyName + " (" + companyInfo.symbol + ")");
      System.out.println("Exchange: " + companyInfo.exchange);
      System.out.println("Website: " + companyInfo.website);
      System.out.println("Description: " + companyInfo.description + "\n");

   }
   
   /**
      Prints price and market information.
   */
   public void getQuoteInfo(){
      super.getQuoteInfo();
   }
}