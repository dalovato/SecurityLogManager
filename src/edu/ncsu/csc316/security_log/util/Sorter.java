package edu.ncsu.csc316.security_log.util;

import java.util.Arrays;
import java.util.Comparator;

import edu.ncsu.csc316.security_log.data.LogEntry;

/**
 * This class will use mergesort to sort objects.
 * @author David Lovato
 */
public class Sorter {

	/**
	 * Merge algorithm from the textbook.
	 * Goodrich, Michael T.; Tamassia, Roberto; Goldwasser, Michael H.
	 * Data Structures and Algorithms in Java, 6th Edition (Page 537). Wiley. Kindle Edition. 
	 * 
	 * Merge contents of arrays S1 and S2 into properly sized array S. This algorithm is specific to
	 * sorting usernames.
	 * @param s1 the first array
	 * @param s2 the second array
	 * @param objects the final in which the first and second are being merged to
	 * @param userNameComparator the comparator
	 * @param <E> generic object
	 */
	public static <E> void mergeUserName(Object[] s1, Object[] s2, Object[] objects, Comparator<LogEntry> userNameComparator) { 
		int i = 0, j = 0; 
		while (i + j < objects.length) { 
			if (j == s2.length || (i <  s1.length && userNameComparator.compare( (LogEntry) s1[i], (LogEntry) s2[j]) < 0))
				objects[i + j] =  s1[i++]; // copy ith element of S1 and increment i
			else
				objects[i + j] = (Object) s2[j++]; // copy jth element of S2 and increment j 
		}
	}

	
	/**
	 * Mergesort algorithm from the textbook.
	 * Goodrich, Michael T.; Tamassia, Roberto; Goldwasser, Michael H. 
	 * Data Structures and Algorithms in Java, 6th Edition (Page 538). Wiley. Kindle Edition. 
	 * 
	 * Merge-sort contents of array. This algorithm is specific to sorting the usernames.
	 * @param objects the array to be sorted
	 * @param userNameComparator the comparator
	 * @param <E> generic object
	 */
	public static <E> void mergeSortUserName(Object[] objects, Comparator<LogEntry> userNameComparator) { 
		int n = objects.length;
		if (n < 2) return; // array is trivially sorted 
		// divide
		int mid = n / 2;
		Object[ ] s1 = Arrays.copyOfRange(objects, 0, mid); // copy of first half
		Object[ ] s2 = Arrays.copyOfRange(objects, mid, n); // copy of second half
		// conquer (with recursion)
		mergeSortUserName(s1, userNameComparator); // sort copy of first half
		mergeSortUserName(s2, userNameComparator); // sort copy of second half
		// merge results 
		mergeUserName(s1, s2, objects, userNameComparator); // merge sorted halves back into original 	
	}
		

	/**
	 * Merge algorithm from the textbook.
	 * Goodrich, Michael T.; Tamassia, Roberto; Goldwasser, Michael H.
	 * Data Structures and Algorithms in Java, 6th Edition (Page 537). Wiley. Kindle Edition. 
	 * 
	 * Merge contents of arrays S1 and S2 into properly sized array S. This algorithm is specific
	 * to just sorting integer arrays.
	 * @param s1 string array
	 * @param s2 string array
	 * @param s string array
	 * @param comp the comparator function
	 * @param <K> generic object
	 */
	public static <K> void merge(String[] s1, String[] s2, String[] s, Comparator<String> comp) {
		int i = 0, j = 0;
		while (i + j < s.length) {
			if (j == s2.length || (i < s1.length && comp.compare( s1[i], s2[j]) <= 0))
				s[i + j] = s1[i++]; // copy ith element of S1 and increment i
			else
				s[i + j] = s2[j++]; // copy jth element of S2 and increment j
		}
	}

	/**
	 * Mergesort algorithm from the textbook.
	 * Goodrich, Michael T.; Tamassia, Roberto; Goldwasser, Michael H. 
	 * Data Structures and Algorithms in Java, 6th Edition (Page 538). Wiley. Kindle Edition.
	 * 
	 * Merge-sort contents of array. This algorithm is specific to sorting integers..
	 * @param s string array
	 * @param comp string comparator
	 * @param <K> generic object
	 */
	public static <K> void mergeSort(String[] s, Comparator<String> comp) {
		int n = s.length;
		if (n < 2) return; // array is trivially sorted
		// divide
		int mid = n / 2;
		String[] s1 = Arrays.copyOfRange(s, 0, mid); // copy of first half
		String[] s2 = Arrays.copyOfRange(s, mid, n); // copy of second half
		// conquer (with recursion)
		mergeSort(s1, comp); // sort copy of first half
		mergeSort(s2, comp); // sort copy of second half
		// merge results
		merge(s1, s2, s, comp); // merge sorted halves back into original
	}

}
