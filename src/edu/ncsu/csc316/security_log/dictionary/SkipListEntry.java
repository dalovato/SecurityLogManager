/**
 * 
 */
package edu.ncsu.csc316.security_log.dictionary;

/**
 * This class will implement the behavior of a skip list entry
 * @author David Lovato
 */
public class SkipListEntry {
	
	/** Key for skip list entry is the action + resource */
	private String key;
	/** Value for the skip list entry is the frequency of that action + resource */
	private int value;
	
	/** This is the pointer to the node below */
	private SkipListEntry down;
	/** This is the pointer to the node above */
	private SkipListEntry up;
	/** This is the pointer to the node to the right */
	private SkipListEntry right;
	/** This is the pointer to the node to the left */
	private SkipListEntry left;
	
	/** This is the string representing negative infinity */
	public static final String NEGATIVE_INFINITY = "-oo";
	/** This is the string representing positive infinity */
	public static final String POSITIVE_INFINITY = "+oo";
	
	/**
	 * Constructor for a skip list entry
	 * @param key the string of action + resource
	 * @param value the frequency of that action + resouce
	 */
	public SkipListEntry(String key, int value) {
		this.key = key;
		this.value = value;
		this.down = null;
		this.up = null;
		this.right = null;
		this.left = null;
	}
	
	/**
	 * Getter for the key
	 * @return key, the string for this SkipListEntry
	 */
	public String getKey() {
		return this.key;
	}
	
	/**
	 * Getter for the value
	 * @return value, the frequency for this entry
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Setter for the frequency of this string
	 * @param newVal the new freq
	 * @return the old frequency (may not be very useful)
	 */
	public int setValue(int newVal) {
		int oldVal = this.value;
		this.value = newVal;
		return oldVal;
	}

	/**
	 * Getter for the entry below
	 * @return the SkipListEntry below this one
	 */
	public SkipListEntry getDown() {
		return this.down;
	}

	/**
	 * Setter for the entry below
	 * @param down the entry below to set
	 */
	public void setDown(SkipListEntry down) {
		this.down = down;
	}

	/**
	 * Getter for the entry above
	 * @return the entry above this entry
	 */
	public SkipListEntry getUp() {
		return this.up;
	}

	/**
	 * Setter for the entry above this entry
	 * @param up the entry above to set
	 */
	public void setUp(SkipListEntry up) {
		this.up = up;
	}

	/**
	 * Getter for the entry to the right of this entry
	 * @return the right entry
	 */
	public SkipListEntry getRight() {
		return this.right;
	}

	/**
	 * Setter for the entry to the right of this entry
	 * @param right the entry to the right to set
	 */
	public void setRight(SkipListEntry right) {
		this.right = right;
	}

	/**
	 * Getter for the entry to the left of this entry
	 * @return the left the entry to the left
	 */
	public SkipListEntry getLeft() {
		return this.left;
	}

	/**
	 * Setter for the entry left of this entry
	 * @param left the left entry to set
	 */
	public void setLeft(SkipListEntry left) {
		this.left = left;
	}
	
	
}
