package pl.nbp.parser.tests;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.nbp.parser.*;
import pl.nbp.parser.exceptions.RequestException;
import static org.junit.Assert.*;

import org.junit.Test;

public class RequestCreatorTest {

	@Test
	public void testCreateRequest() {
		
		String args[] = {"EUR", "2013-02-02", "2015-11-03"};
		String test = "http://api.nbp.pl/api/exchangerates/rates/C/EUR/2013-02-02/2015-11-03?format=xml";
		
		try {
			Request r = RequestCreator.createRequest(args);
			assertTrue(r.getRequest().equals(test));
		}catch(RequestException re) {
			fail();
		}		
	}
	
	@Test
	public void testCreateRequestOnlyDateStart() {
		
		String args[] = {"GBP", "2013-02-02"};
		String test = "http://api.nbp.pl/api/exchangerates/rates/C/GBP/2013-02-02?format=xml";
		
		try {
			Request r = RequestCreator.createRequest(args);
			assertTrue(r.getRequest().equals(test));
		}catch(RequestException re) {
			fail();
		}
	}
	
	@Test
	public void testCreateRequestSmallLettersCurrency() {
		
		String args[] = {"usd", "2013-02-02", "2015-11-03"};
		String test = "http://api.nbp.pl/api/exchangerates/rates/C/usd/2013-02-02/2015-11-03?format=xml";
		
		try {
			Request r = RequestCreator.createRequest(args);
			assertTrue(r.getRequest().equals(test));
		}catch(RequestException re) {
			fail();
		}
		
	}
	
	@Test
	public void testCreateRequestCurrencyToShort() {
		
		String args[] = {"us", "2013-02-02", "2015-11-03"};
		String test = "Wrong currency pattern";
		
		try {
			Request r = RequestCreator.createRequest(args);
			fail();
		}catch(RequestException re) {
			assertTrue(re.getMessage().equals(test));
		}	
	}
	
	@Test
	public void testCreateRequestCurrencyToLong() {
		
		String args[] = {"PLNA", "2013-02-02", "2015-11-03"};
		String test = "Wrong currency pattern";
		
		try {
			Request r = RequestCreator.createRequest(args);
			fail();
		}catch(RequestException re) {
			assertTrue(re.getMessage().equals(test));
		}	
		
	}
	
	@Test
	public void testCreateRequestCurrencyOdd() {
		
		String args[] = {"P4A", "2013-02-02", "2015-11-03"};
		String test = "Wrong currency pattern";
		
		try {
			Request r = RequestCreator.createRequest(args);
			fail();
		}catch(RequestException re) {
			assertTrue(re.getMessage().equals(test));
		}	
		
	}
	
	@Test
	public void testCreateRequestWrongStartDate() {
		
		String args[] = {"PLN", "2012-02-30", "2015-11-03"};
		String test = "Wrong start date";
		
		try {
			Request r = RequestCreator.createRequest(args);
			fail();
		}catch(RequestException re) {
			assertTrue(re.getMessage().equals(test));
		}	
		
	}
	
	@Test
	public void testCreateRequestStartBeforeEarliestDate() {
		
		String args[] = {"PLN", "2002-01-01", "2015-11-03"};
		String test = "The earliest date is 2002-01-02";
		
		try {
			Request r = RequestCreator.createRequest(args);
			fail();
		}catch(RequestException re) {
			assertTrue(re.getMessage().equals(test));
		}	
		
	}
	
	@Test
	public void testCreateRequestStartDateAfterNow() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date tomorrow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));		
		
		String args[] = {"PLN", sdf.format(tomorrow), "2015-11-03"};
		String test = "Nobody knows the future: " + sdf.format(tomorrow);
		
		try {
			Request r = RequestCreator.createRequest(args);
			fail();
		}catch(RequestException re) {
			assertTrue(re.getMessage().equals(test));
		}
		
	}	
	
	@Test
	public void testCreateRequestWrongEndDate() {
				
		String args[] = {"PLN", "2013-12-05", "2013-11-31"};
		String test = "Wrong end date";
		
		try {
			Request r = RequestCreator.createRequest(args);
			fail();
		}catch(RequestException re) {
			assertTrue(re.getMessage().equals(test));
		}
		
	}
	
	@Test
	public void testCreateRequestEndBeforeStart() {
				
		String args[] = {"PLN", "2013-12-05", "2012-11-30"};
		String test = "End date must be newest than start date";
		
		try {
			Request r = RequestCreator.createRequest(args);
			fail();
		}catch(RequestException re) {
			assertTrue(re.getMessage().equals(test));
		}	
		
	}
	
	@Test
	public void testCreateRequestEndDateAfterNow() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date tomorrow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));		
		
		String args[] = {"PLN", "2015-11-03", sdf.format(tomorrow)};
		String test = "Nobody knows the future: " + sdf.format(tomorrow);
		
		try {
			Request r = RequestCreator.createRequest(args);
			fail();
		}catch(RequestException re) {
			assertTrue(re.getMessage().equals(test));
		}	
		
	}
	
}
