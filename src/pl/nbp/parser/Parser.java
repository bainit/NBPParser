package pl.nbp.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Parser {
	
	public static String getXML(String request) throws Exception{
		
		String result = "";
		
		URL nbp = new URL(request);
	    BufferedReader in = new BufferedReader(new InputStreamReader(nbp.openStream()));
	
	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	    	result += inputLine;
	    in.close();
	    
		return result;
		
	}
	
	public static ArrayList<Double> getValues(String xmlr, String tag) throws Exception{
		
		Document doc = null;
		ArrayList<Double> vals = null;
		
		DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
		DocumentBuilder bldr = fctr.newDocumentBuilder();
		InputSource insrc = new InputSource(new StringReader(xmlr));
		doc=bldr.parse(insrc);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName(tag);
			
		vals = new ArrayList<Double>();
			
		for(int i=0;i<nList.getLength();i++) {
			Node nNode = nList.item(i);
			vals.add(Double.valueOf(nNode.getTextContent()));
		}
		
		return vals;
		
	}
	
	public static double avg(ArrayList<Double> values) {
		
		if(values.size()==0)
			return 0;
		
		double r = 0;
		
		for(Double d : values) {
			r += d;
		}
		
		return r/values.size();
		
	}
	
	public static double stddev(ArrayList<Double> values, double avg) {
		
		if(values.size()<2)
			return 0;
		
		double sum = 0;
		
		for(Double d : values) {
			sum += Math.pow((d-avg),2);			
		}		
		
		return Math.sqrt(sum/(values.size()-1));		
		
	}

}
