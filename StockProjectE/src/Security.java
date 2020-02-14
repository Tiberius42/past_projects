/**
 * This implements the SecurityInfo interface and is the base class for
 * different types of securities.
 */
class Security implements SecurityInfo {
	public CompanyDTO companyInfo;
	public QuoteDTO quoteInfo;

	/**
	 * Constructor for Security.
	 */
	public Security(CompanyDTO companyInfo, QuoteDTO quoteInfo) {
		this.companyInfo = companyInfo;
		this.quoteInfo = quoteInfo;
	}

	/**
	 * Prints company information.
	 */
	public void getCompanyInfo() {
		System.out.println(companyInfo.companyName + " (" + companyInfo.symbol + ")");
		System.out.println("Exchange: " + companyInfo.exchange);
		System.out.println("Sector: " + companyInfo.sector);
		System.out.println("Industry: " + companyInfo.industry);
		System.out.println("Website: " + companyInfo.website);
		System.out.println("Description: " + companyInfo.description + "\n");
	}

	/**
	 * Prints price and market information.
	 */
	public void getQuoteInfo() {
		System.out.printf(
				"Latest price: " + quoteInfo.latestPrice + " (" + ((quoteInfo.change >= 0.0) ? "+" : "-")
						+ quoteInfo.change + ", " + ((quoteInfo.changePercent >= 0.0) ? "+" : "-") + "%.3f%%)\n",
				(quoteInfo.changePercent * 100));
		System.out.println("Latest time: " + quoteInfo.latestTime);// check if this is right
		System.out.println("Open price: " + quoteInfo.open);
		System.out.println("Close price: " + quoteInfo.close);
		System.out.println("High: " + quoteInfo.high);
		System.out.println("Low: " + quoteInfo.low);
		System.out.println("Latest source: " + quoteInfo.latestSource);
		System.out.println("Latest volume: " + quoteInfo.latestVolume);
		System.out.println("Market cap: " + quoteInfo.marketCap);
		System.out.println("peRatio: " + quoteInfo.peRatio);
		System.out.println("52 week high: " + quoteInfo.week52High);
		System.out.println("52 week low: " + quoteInfo.week52Low);
		System.out.printf("YTD change: " + ((quoteInfo.changePercent >= 0.0) ? "+" : "-") + "%.3f%%\n\n",
				(quoteInfo.ytdChange * 100));
	}
}