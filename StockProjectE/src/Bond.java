/**
   This is a subclass of Security and represents bonds, bond funds, and other securities covered by the "ce"
   issueType tag provided by the API.
*/
class Bond extends Security{
   
   /**
      Constructor for Bond.
   */
   public Bond(CompanyDTO companyInfo, QuoteDTO quoteInfo){
      super(companyInfo, quoteInfo);
   }
   
   /**
      Prints company information.
   */
   public void getCompanyInfo(){
      System.out.println("This security is a bond.");
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