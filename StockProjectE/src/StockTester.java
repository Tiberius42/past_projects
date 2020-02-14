import com.google.gson.*;
import java.net.*;
import java.util.*;
import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.stage.Stage;

/**
 * Data provided for free by IEX
 * <a href="https://iextrading.com/developer/">IEX</a>. View IEX’s
 * <a href="https://iextrading.com/api-exhibit-a/">Terms of Use</a>.
 * 
 * @author Samuel Sommerer
 * 
 *         Instructions: When the program prompts, enter one tickers and press
 *         enter. Repeat until you have entered all the tickers you want
 *         information on, then press "n" and pres enter. Information about the
 *         companies and tickers you selected will be printed out, as well as
 *         price and market data. The company descriptions preceded by
 *         "Description:" may go off screen. This is just the way the
 *         description is formatted in the JSON. Additionally, a single line
 *         chart will be generated containing lines for the One Day price
 *         changes for all the valid tickers you entered. Some of these lines
 *         may suddenly drop down and disappear, then rise up again. This is
 *         because the API does not have the market closing price data of those
 *         tickers in the range of time that the line disappears for. I have
 *         corrected for some of these absences, but in cases of large absences
 *         of data I just left the chart as it is. The chart will pop up in its
 *         own window.
 * 
 *         This class is a tester and also contains the code for generating a
 *         line chart showing price changes of a security for one day.
 */
// https://stackoverflow.com/questions/1082050/linking-to-an-external-url-in-javadoc
public class StockTester extends Application {
	private List<XYChart.Series> seriesList = new ArrayList<XYChart.Series>();
	private static List<String> symbolList = new ArrayList<String>();

	/**
	 * This start method is part of the Application class and needs to be
	 * implemented, otherwise GsonTester will be abstract. This method retrieves
	 * price data of the securities that the user input for the most recent day.
	 * With this data, it generates a line chart.
	 */
	// https://docs.oracle.com/javafx/2/charts/line-chart.htm#CIHGBCFI
	// https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/line-chart.htm#CIHGBCFI
	@SuppressWarnings("unchecked")
	public void start(Stage stage) throws IOException {

		stage.setTitle("1 Day Chart");
		// defining the axes
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Time");
		yAxis.setLabel("Stock Price");
		// creating the chart
		final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
		lineChart.setTitle("1 Day Chart");
		// https://stackoverflow.com/questions/17404425/setting-javafx-linechart-line-width
		lineChart.setCreateSymbols(false);

		for (int i = 0; i < symbolList.size(); i++) {
			List<OneDayCoordinateDTO> temp = generateCoordList(getCoordinateDTO(symbolList.get(i)));
			for (int k = 0; k < temp.size(); k++) {
				if (temp.get(k).marketClose == -1) {
					if (k == 0) {
						temp.get(k).minute = temp.get(k + 1).minute;
						temp.get(k).marketClose = temp.get(k + 1).marketClose;
					}
					temp.get(k).minute = temp.get(k - 1).minute;
					temp.get(k).marketClose = temp.get(k - 1).marketClose;
				}
			}
			seriesList.add(new XYChart.Series());
			for (int j = 0; j < temp.size(); j++) {
				seriesList.get(i).getData().add(new XYChart.Data(temp.get(j).minute, temp.get(j).marketClose));
			}
		}

		for (int i = 0; i < seriesList.size(); i++) {
			lineChart.getData().add(seriesList.get(i));
			seriesList.get(i).setName(symbolList.get(i).toUpperCase() + " Performance");
		}

		Scene scene = new Scene(lineChart, 800, 600);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Returns a JSON list from the IEX API containing stock data for the most
	 * recent day.
	 * 
	 * @param stockSymbol is the stock ticker whose data is to be retrieved.
	 */
	private String getCoordinateDTO(String stockSymbol) throws IOException {
		String address = "https://api.iextrading.com/1.0/stock/" + stockSymbol + "/chart/1d";
		URL pageLocation = new URL(address);

		// https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html
		try (BufferedReader in = new BufferedReader(new InputStreamReader(pageLocation.openStream()))) {
			String json = in.readLine();
			return json;
		} catch (IOException e) {
			return "[]";
		}
	}

	/**
	 * Returns a List of OneDayCoordinateDTOs generated from a JSON list.
	 * 
	 * @param json is the json list to be converted.
	 */
	// https://stackoverflow.com/questions/4318458/how-to-deserialize-a-list-using-gson-or-another-json-library-in-java
	private List<OneDayCoordinateDTO> generateCoordList(String json) {
		Gson gson = new Gson();
		OneDayCoordinateDTO[] chartArray = gson.fromJson(json, OneDayCoordinateDTO[].class);
		List<OneDayCoordinateDTO> chartList = Arrays.asList(chartArray);
		return chartList;
	}

	/**
	 * Main method where the code is run. launch(args) creates the line charts
	 * displaying price data for a security.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("IEX's Terms of Service: https://iextrading.com/api-exhibit-a/\n");

		SecurityGetter test1 = new SecurityGetter();
		test1.printCompanyInfo();
		symbolList = test1.getSymbolList();

		launch(args);
	}
}
