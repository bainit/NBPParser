package pl.nbp.parser.tests;

import static org.junit.Assert.*;
import pl.nbp.parser.*;

import java.util.ArrayList;

import org.junit.Test;

public class AvgStdDevTest {

	@Test
	public void avgsdtTest() {
		
		double avgTest = 1.6666666666666667;
		double stddevTest = 2.160246899469287;

		ArrayList<Double> numbers = new ArrayList<Double>();
		
		numbers.add(new Double(1));
		numbers.add(new Double(5));
		numbers.add(new Double(2));
		numbers.add(new Double(-1));
		numbers.add(new Double(0));
		numbers.add(new Double(3));
		
		double avg = Parser.avg(numbers);
		
		assertTrue(avg==avgTest);
		
		double stddev = Parser.stddev(numbers, avg);
		
		assertTrue(stddev==stddevTest);
		
	}
	
	@Test
	public void emptyValuesTest() {
		
		ArrayList<Double> numbers = new ArrayList<Double>();
		
		double avg = Parser.avg(numbers);
		
		assertTrue(avg==0);
		
		double stddev = Parser.stddev(numbers, avg);
		
		assertTrue(stddev==0);
		
	}
	
	@Test
	public void onlyOneValueTest() {
		
		double someVal = 3.14;
		
		ArrayList<Double> numbers = new ArrayList<Double>();
		
		numbers.add(someVal);
		
		double avg = Parser.avg(numbers);
		
		assertTrue(avg==someVal);
		
		double stddev = Parser.stddev(numbers, avg);
		
		assertTrue(stddev==0);
		
	}
	
	

}
