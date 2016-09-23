package pl.nbp.parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import pl.nbp.parser.exceptions.RequestException;

public class NBPParser {
	
	private final static String BUYTAG = "Bid";
	private final static String SELLTAG = "Ask";

	public static void main(String args[]) {

		try {
			Request request = RequestCreator.createRequest(args);
			
			String xmlResult=null;
			xmlResult = Parser.getXML(request.getRequest());			
			
			ArrayList<Double> valuesBuy = null;
			valuesBuy = Parser.getValues(xmlResult, BUYTAG);
				
			double avgBuy = Parser.avg(valuesBuy);
			double stdBuy = Parser.stddev(valuesBuy,avgBuy);		
			
			ArrayList<Double> valuesSell = null;
			valuesSell = Parser.getValues(xmlResult, SELLTAG);			
				
			double avgSell = Parser.avg(valuesSell);
			double stdSell = Parser.stddev(valuesSell,avgSell);
				
			System.out.println("Covered time period: " + request.dateRange());
			System.out.println("Currency code: " + request.getCurrCode());
			System.out.format("Average buy price: %.4f, ", avgBuy);
			System.out.format("standard deviation: %.4f \n", stdBuy);
			
			System.out.format("Average sell price: %.4f, ", avgSell);
			System.out.format("standard deviation: %.4f \n", stdSell);
			
			System.out.println("...done");
			
		}catch (RequestException re) {
			re.getMessage();
			printUsage();			
		}catch (FileNotFoundException fne) {
			System.out.println("No data were found.");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Please contact the author sul5@gazeta.pl");
		}
		
	}	
	
	public static void printUsage() {

		System.out.println("usage: java pl.parser.nbp.NBPParser currency start_date end_date");
		System.out.println("currency format: /^[A-Za-z]{3}$/");
		System.out.println("date format: yyyy-mm-dd");
		System.out.println("e.g. java pl.parser.nbp.NBPParser EUR 2013-01-28 2013-01-31");
		System.out.println();		

	}
	


}