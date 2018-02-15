/**
 * 
 */
package edu.ncsu.csc316.security_log.manager;

import static org.junit.Assert.*;


import org.junit.Test;

import edu.ncsu.csc316.security_log.data.LogEntry;
import edu.ncsu.csc316.security_log.dictionary.SkipListEntry;
import edu.ncsu.csc316.security_log.util.ArrayList;
import edu.ncsu.csc316.security_log.util.Sorter;

/**
 * This is the test class for SecurityLogManager.
 * @author David Lovato
 */
public class SecurityLogManagerTest {

	/** SecurityLogManager for testing */
	private SecurityLogManager manager;
	

	/**
	 * Tests functionality for LogEntry.
	 */
	@Test
	public void logEntryTest() {
		LogEntry log1 = new LogEntry("jtking, 01/18/2018 01:22:21PM, view, prescription information");
		LogEntry log2 = new LogEntry("jtking, 01/18/2018 12:58:14PM, delete, demographics information");
		
		int cmp = log1.getTimestamp().compareTo(log2.getTimestamp());
		assertTrue(cmp > 0);
	}
	
	/**
	 * Tests functionality for getting the user report.
	 */
	@Test
	public void getUserReportTest() {
		manager = new SecurityLogManager("input/input1.txt");
		String output = manager.getUserReport("jtking");
		String expected = "Activity Report for jtking[\n" +
							"   01/18/2018 12:58:14PM - delete demographics information\n" + 
							"   01/18/2018 01:22:21PM - view prescription information\n" + 
							"]";
		assertEquals(output, expected);
		
		output = manager.getUserReport("ssoulcrusher");
		expected = "Activity Report for ssoulcrusher[\n" +
					"   01/18/2018 01:22:01PM - delete prescription information\n" +
					"]";
				
		manager = new SecurityLogManager("input/activityLog_small.txt");
		output = manager.getUserReport("fzalcala");
		expected = "Activity Report for fzalcala[\n" +
					"   05/04/2015 02:09:40PM - sort ICD-9 Code 196\n" +
					"   05/04/2015 02:09:40PM - sort ICD-9 Code 197\n" +
					"   05/04/2015 02:09:40PM - view ICD-9 Code 197\n" +
					"   10/04/2015 12:17:49PM - sort ICD-9 Code 196\n" +
					"   08/04/2016 06:57:34AM - resolve message M2964\n" +
					"   10/07/2016 07:08:47AM - sort ICD-9 Code 196\n" +
					"   04/26/2017 12:33:15PM - sort ICD-9 Code 196\n" +
				    "   07/03/2017 12:36:05AM - sort ICD-9 Code 196\n" +	
				    "   08/10/2017 05:10:54AM - sort ICD-9 Code 196\n" +
				    "   08/26/2017 08:15:06AM - sort ICD-9 Code 196\n" +
				    "   10/24/2017 11:38:02AM - sort ICD-9 Code 196\n" +
				    "   11/20/2017 11:38:22AM - sort ICD-9 Code 196\n" +
				    "]";
		
		assertEquals(output, expected);	
		
		output = manager.getUserReport("dalovato");
		expected = "Activity Report for dalovato[\n" +
					"   No activity was recorded.\n" +
					"]";
		assertEquals(output, expected);
				
		manager = new SecurityLogManager("input/input2.txt");
		output = manager.getUserReport("jtking");
		expected = "Activity Report for jtking[\n" +
					"   01/18/2018 12:22:21PM - view prescription information\n" +
					"   01/18/2018 01:20:21PM - view prescription information\n" +
					"   01/18/2018 01:22:21PM - view prescription information\n" +
					"   01/18/2018 01:23:21PM - view prescription information\n" +
					"]";
		
		assertEquals(output, expected);
	}
	
	/**
	 * Tests functionality for getting an operational profile for a timespan.
	 */
	@Test
	public void getOperationalProfile() {
		manager = new SecurityLogManager("input/input1.txt");
		String output = manager.generateOperationalProfile("01/17/2018 12:00:00AM", "01/20/2018 12:00:00PM");
		String expected = "OperationalProfile[\n" +
						  "   view prescription information: frequency: 2, percentage: 40.0%\n" +
						  "   create immunization order: frequency: 1, percentage: 20.0%\n" +
						  "   delete demographics information: frequency: 1, percentage: 20.0%\n" + 
						  "   delete prescription information: frequency: 1, percentage: 20.0%\n" +
						  "]";
		assertEquals(output, expected);
		
		output = manager.generateOperationalProfile("01/17/2018 12:00:00AM", "01/20/2017 12:00:00PM");
		expected = "No activity was recorded.";
		assertEquals(output, expected);
	}	
	
	/**
	 * This will test the compareTo function of LogEntry.
	 */
	@Test
	public void testLogCompareTo() {
		LogEntry log1 = new LogEntry("fzalcala, 04/26/2017 12:33:15PM, sort, ICD-9 Code 196");
		LogEntry log2 = new LogEntry("fzalcala, 04/26/2017 12:33:15PM, sort, ICD-9 Code 197");
		int comp = log1.compareTo(log2);
		assertTrue(comp < 0);
		@SuppressWarnings("unused")
		Sorter sort = new Sorter();
	}
	
	/**
	 * Testing SkipList methods
	 */
	@Test
	public void testSkipList() {
		SkipListEntry entry = new SkipListEntry(null, 0);
		assertTrue(entry.getLeft() == null);
	}
	
	/**
	 * Testing ArrayList
	 */
	@Test
	public void testArrayList() {
		ArrayList arr = new ArrayList(2);
		arr.add(1);
		assertTrue(arr.size() == 1);
		arr.add(1, 1);
		assertTrue(arr.size() == 2);
	}
}
