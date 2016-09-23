package pl.nbp.parser;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.nbp.parser.exceptions.RequestException;

public class RequestCreator {
	
	public static Request createRequest(String args[]) throws RequestException {
		
		final String DATEFORMAT = "yyyy-MM-dd";
		final String EARLIESTDATESTRING = "2002-01-02";	
		final String CURRPATTERN = "^[A-Za-z]{3}$";
		
		String currencyCode;		
		
		if(args.length<2 || args.length>3)
			throw new RequestException("Wrong number of arguments");
		
		Matcher matcher;
		Pattern pattern = Pattern.compile(CURRPATTERN);		
		matcher = pattern.matcher(args[0]);	
		if(!matcher.find()) {
			throw new RequestException("Wrong currency pattern");
		}else {
			currencyCode = args[0];
		}
				
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		sdf.setLenient(false);

		Date dateStart = sdf.parse(args[1], new ParsePosition(0));
		if(dateStart==null)
			throw new RequestException("Wrong start date");
		
		Date earliestDate = sdf.parse(EARLIESTDATESTRING,  new ParsePosition(0));		
		if(dateStart.before(earliestDate))
			throw new RequestException("The earliest date is " + EARLIESTDATESTRING);
		
		Date now = new Date();
		if(dateStart.after(now))
			throw new RequestException("Nobody knows the future: " + sdf.format(dateStart));
		
		if(args.length==2) {			
			return new Request(dateStart, currencyCode);			
		}else {

			Date dateEnd = sdf.parse(args[2], new ParsePosition(0));
			
			if(dateEnd==null)
				throw new RequestException("Wrong end date");
			
			if(dateEnd.before(dateStart))
				throw new RequestException("End date must be newest than start date");			
			
			if(dateEnd.after(now))
				throw new RequestException("Nobody knows the future: " + sdf.format(dateEnd));			
			
			return new Request(dateStart,dateEnd,currencyCode);
		}		
	}

}
