/**
 * 
 */
package edu.ncsu.csc316.security_log.data;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * This class will hold all the functionality for creating LogEntry objects.
 * @author David Lovato
 */
public class LogEntry implements Comparable<LogEntry> {
	
	/** String that holds the username for LogEntry */
	private String username;
	
	/** Time/Date for the timestamp of the LogEntry */
	private LocalDateTime timestamp;
	
	/** String that holds the action for the LogEntry */
	private String action;
	
	/** String that holds the resource for the LogEntry */
	private String resource;
	
	/**
	 * Constructs a log entry object, setting the username, timestamp, action,
	 * and resource.
	 * @param entry the entry String
	 */
	public LogEntry(String entry) {
		StringTokenizer str = new StringTokenizer(entry, ",");
		setUsername(str.nextToken().trim());
		setTimestamp(str.nextToken().trim());
		setAction(str.nextToken().trim());
		setResource(str.nextToken().trim());
	}

	/**
	 * Gets the username
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the Timestamp
	 * @return the timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * timeStamp format: MM/dd/yyyy hh:mm:ssa
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ssa");
		this.timestamp = LocalDateTime.parse(timestamp, formatter);
	}

	/**
	 * Getter for action
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Setter for action
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Getter for resource
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * Setter for resource
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	/**
	 * CompareTo method for LogEntries.  First, it is sorted by username.  If those are equal
	 * then it is sorted by timestamp. If those are equal then it is sorted by action and then
	 * resource.
	 * @param entry LogEntry to be compared to 
	 */
	@Override
	public int compareTo(LogEntry entry) {
		int usernameCmp = this.username.compareTo(entry.getUsername());
		if (usernameCmp != 0) {
			return usernameCmp;
		} else {
			int timeCmp = this.timestamp.compareTo(entry.getTimestamp());
			if (timeCmp != 0) {
				return timeCmp;
			} else {
				int actionCmp = this.getAction().compareTo(entry.getAction());
				if (actionCmp != 0) {
					return actionCmp;
				} else {
					return this.getResource().compareTo(entry.getResource());
				}
			}
			
		}
	}
	
	
	/**
	 * Comparator function for comparing two entries and sorting by username.
	 */
	public static Comparator<LogEntry> userNameComparator = new Comparator<LogEntry>() {

		/**
		 * This function compares two log entries the same way the compareTo() function compares them
		 * @param log1, first log entry
		 * @param log2, second log entry
		 */
		public int compare(LogEntry log1, LogEntry log2) {
			int usernameCmp = log1.getUsername().compareTo(log2.getUsername());
			if (usernameCmp != 0) {
				return usernameCmp;
			} else {
				int timeCmp = log1.getTimestamp().compareTo(log2.getTimestamp());
				if (timeCmp != 0) {
					return timeCmp;
				} else {
					int actionCmp = log1.getAction().compareTo(log2.getAction());
					if (actionCmp != 0) {
						return actionCmp;
					} else {
						return log1.getResource().compareTo(log2.getResource());
					}
				}
			}
		}
	};
	
	
	/**
	 * Comparator function for comparing two entries and sorting by frequency.
	 */
	public static Comparator<String> frequencyComparator = new Comparator<String>() {

		/**
		 * This function compares two frequencies that are contained a string composed of
		 * frequency + action + resource.  The strings are sorted so that the larger frequency
		 * will come first, giving a descending order array.
		 * @param String string1, first string
		 * @param String string2, second string
		 */
		public int compare(String string1, String string2) {
			int j = 0;
			StringBuilder sb = new StringBuilder();
			while (Character.isDigit(string1.charAt(j))) {
				sb.append(string1.charAt(j));
				j++;
			}
			int int1 = Integer.parseInt(sb.toString());
			j = 0;
			sb = new StringBuilder();
			while (Character.isDigit(string2.charAt(j))) {
				sb.append(string2.charAt(j));
				j++;
			}
			int int2 = Integer.parseInt(sb.toString());
			return (int2 - int1);
		}
	};
	
}