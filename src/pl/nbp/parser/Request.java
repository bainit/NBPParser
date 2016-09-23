package pl.nbp.parser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Request {	
	
	private final static String REQUESBASE = "http://api.nbp.pl/api/exchangerates/rates";	
	private final static String TABLE = "C";
	private final static String FORMAT = "format=xml";
	
	private String request;
	
	private Date dateStart;
	private Date dateEnd;
	private String currCode;
	
	SimpleDateFormat sdf;
	
	public Request(Date sd, String cc){
		
		this.dateStart = sd;
		this.dateEnd = null;
		this.currCode = cc;
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		request = REQUESBASE+"/"+TABLE+"/"+currCode+"/"+sdf.format(dateStart)+"?"+FORMAT;
		
	}
	
	public Request(Date sd, Date ed, String cc){
		
		this.dateStart = sd;
		this.dateEnd = ed;
		this. currCode = cc;
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		request = REQUESBASE+"/"+TABLE+"/"+currCode+"/"+sdf.format(dateStart)+"/"+sdf.format(dateEnd)+"?"+FORMAT;
		
	}	

	public String getCurrCode() {
		return currCode;
	}

	public String getRequest() {
		return request;
	}
	
	public String dateRange() {
		StringBuffer r = new StringBuffer(sdf.format(dateStart));
		if(dateEnd!=null)
			r.append(" to "+sdf.format(dateEnd));
		return r.toString();
	}
	
}
