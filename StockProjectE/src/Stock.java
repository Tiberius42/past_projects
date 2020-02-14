/**
   This subclass of Security represents stocks.
*/
class Stock extends Security{
   
   /**
      Constructor for Stock.
   */
   public Stock(CompanyDTO companyInfo, QuoteDTO quoteInfo){
      super(companyInfo, quoteInfo);
   }
   
   /**
      Prints company information.
   */
   public void getCompanyInfo(){
      System.out.println("This security is a stock.");
      super.getCompanyInfo();
   }
   
   /**
      Prints price and market information.
   */
   public void getQuoteInfo(){
      super.getQuoteInfo();
   }
}