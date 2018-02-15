/**
 * This program will run the security log manager, which holds PackHealth
 * files and performs lookups on those files based on timestamps and usernames.
 */
package edu.ncsu.csc316.security_log.manager;

//Working program using BufferedReader
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ncsu.csc316.security_log.data.LogEntry;
import edu.ncsu.csc316.security_log.dictionary.SkipList;
import edu.ncsu.csc316.security_log.util.ArrayList;
import edu.ncsu.csc316.security_log.util.Sorter;


/**
 * SecurityLogManager class which holds all the functionality for
 * manipulating the log files.
 * @author David Lovato
 */
public class SecurityLogManager {

	/** Array for holding LogEntry objects */
	private ArrayList list;
	/** How many hours are in half a day */
	private static final int AM_PM = 12;
	/** Number at which double digits are required */
	private static final int DOUBLE_DIGIT = 10;
	/** Adjustment to change 24 hour to 12 hour time */
	private static final int HOURS_THAT_ARE_PM = 13;
	
	/**
	 * Constructs a new SecurityLogManager given
	 * the path to the input user activity log file.
	 * 
	 * @param filePath - the path to the user activity log file
	 */
	public SecurityLogManager(String filePath) {
		int count = 0;
		String line = null;
		try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
			in.readLine(); //discard the first line
			while ((line = in.readLine()) != null) { //loop will run from the 2nd line
				count++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
			in.readLine(); //discard the first line
			this.list = new ArrayList(count);
			while ((line = in.readLine()) != null) { //loop will run from the 2nd line
				LogEntry entry = new LogEntry(line);
				list.add(entry);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Produces an operational profile of user activity
	 * performed between the given start and end dates (inclusive)
	 * 
	 * @param start - the start date in the format "MM/DD/YYYY HH:MM:SSXM"
	 * @param end - the end date in the format "MM/DD/YYYY HH:MM:SSXM"
	 * @return a string representing the operational profile
	 */
	public String generateOperationalProfile(String start, String end) {
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ssa");
		LocalDateTime startTime = LocalDateTime.parse(start, formatter1);
		
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ssa");
		LocalDateTime endTime = LocalDateTime.parse(end, formatter2);
		
		SkipList skipList = new SkipList();
		
		for (int i = 0; i < this.list.size(); i++) {
			LogEntry entry = (LogEntry) this.list.get(i);
			if ((entry.getTimestamp().compareTo(startTime) >= 0) &&
					(entry.getTimestamp().compareTo(endTime) <= 0)) {
				StringBuffer key = new StringBuffer();
				key.append(entry.getAction());
				key.append(' ');
				key.append(entry.getResource());
				skipList.insert(key.toString());
			}
		}
		
		if (skipList.getSize() == 0) {
			return "No activity was recorded.";
		}
		
		//everything is in the skip list, now use the getArray() method to sort array by frequencies
		//and then loop through array and build string
		
		String[] arr = skipList.getArray();
		
		Sorter.mergeSort(arr, LogEntry.frequencyComparator);
		double sum = skipList.getValueSum();
		
		StringBuilder sb = new StringBuilder();
		sb.append("OperationalProfile[\n");
		
		for (int i = 0; i < arr.length; i++) {
			StringBuilder freq = new StringBuilder();
			int j = 0;
			while (Character.isDigit(arr[i].charAt(j))) {
				freq.append(arr[i].charAt(j));
				j++;
			}
			j++;
			sb.append("   " + arr[i].substring(j, arr[i].length()) + ": frequency: ");
			sb.append(Integer.valueOf(freq.toString()));
			sb.append(", percentage: ");
			double frequency = (double) Integer.valueOf(freq.toString());
			double percent = Math.round((frequency / sum * 100) * 10) / 10.0;
			sb.append(percent);
			sb.append("%\n");
		}
		sb.append(']');
		
		return sb.toString();
	}
	
	/**
	 * Produces a list of log entries for a given 
	 * user. The output list is sorted chronologically.
	 * 
	 * @param username - the user for which to generate a report
	 * @return a string representing the user report
	 */
	public String getUserReport(String username) {
		Sorter.mergeSortUserName(this.list.getArray(), LogEntry.userNameComparator);
		
		int beginIndex = 0;
		int endIndex = 0;
		boolean matchUserName = false;
		int numMatches = 0;
		
		for (int i = 0; i < this.list.size(); i++) {
			LogEntry entry = (LogEntry) this.list.get(i);
			if (entry.getUsername().equals(username)) {
				numMatches++;
				if (!matchUserName) {
					matchUserName = true;
					beginIndex = i;
				}
			} else {
				if (matchUserName) {
					matchUserName = false;
					endIndex = i - 1;
					//this is a sorted list, exit here since begin & end are found
					break;
				}
			}
		}
		
		if (numMatches > 0 && matchUserName) {
			endIndex = list.size() - 1;
		}
		
		if (numMatches == 0) {
			return "Activity Report for " + username + "[\n" +
					"   No activity was recorded.\n" +
					"]";
		}
		
		StringBuilder output = new StringBuilder();
		output.append("Activity Report for " + username + "[\n");
		for (int i = beginIndex; i <= endIndex; i++) {
			LogEntry entry = (LogEntry) list.get(i);
			LocalDateTime ldt = entry.getTimestamp();
			StringBuilder time = new StringBuilder();
			if (ldt.getMonthValue() < DOUBLE_DIGIT) {
				time.append('0');
			}
			time.append(ldt.getMonthValue());
			time.append("/");
			if (ldt.getDayOfMonth() < DOUBLE_DIGIT) {
				time.append('0');
			}
			time.append(ldt.getDayOfMonth());
			time.append("/");
			time.append(ldt.getYear());
			time.append(' ');
			String amOrPm = null;
			//first need to find hour and if it's AM/PM
			int hour = ldt.getHour();
			if (hour >= AM_PM) {
				amOrPm = "PM";
			} else {
				amOrPm = "AM";
			}
			if (hour >= HOURS_THAT_ARE_PM) {
				hour -= AM_PM;
			} else if (hour == 0) {
				hour += AM_PM;
			}
			if (hour < 10) {
				time.append('0');
			}
			time.append(Integer.toString(hour));
			time.append(':');
			if (ldt.getMinute() < DOUBLE_DIGIT) {
				time.append('0');
			}
			time.append(ldt.getMinute());
			time.append(':');
			if (ldt.getSecond() < DOUBLE_DIGIT) {
				time.append('0');
			}
			time.append(ldt.getSecond());
			time.append(amOrPm);
			output.append("   " + time + " - " + entry.getAction() + " " + entry.getResource());
			output.append('\n');
		}
		output.append(']');
		
		return output.toString();
	}
}