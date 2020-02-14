/**
   This data transfer object contains price and market data for a security.
*/
class QuoteDTO{
   public double open = -1.0;
   public double close = -1.0;
   public double high = -1.0;
   public double low = -1.0;
   public double latestPrice = -1.0;
   public String latestSource = null;
   public String latestTime = null;
   public String latestVolume = null;
   public double change = -1.0;
   public double changePercent = 0.0;
   public long marketCap = -1;
   public double peRatio = -1.0;
   public double week52High = -1.0;
   public double week52Low = -1.0;
   public double ytdChange = 0.0;
}