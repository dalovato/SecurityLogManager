/**
 * 
 */
package edu.ncsu.csc316.security_log.dictionary;

import java.util.Random;

/**
 * This class holds the functionality of a skip list.
 * @author David Lovato
 */
public class SkipList {
	
	/** Array should be have 2 elements in each row, the value and key */
	public static final int VALUE_AND_KEY = 2;
	/** Value to return when key is not found */
	public static final int NOT_FOUND = -1;
	/** Pointer to the head node, this is the top left node in the skip list */
	private SkipListEntry head;
	/** Pointer to the tail node, this is the top right node in the skip list */
	private SkipListEntry tail;
	
	/** The number of SkipListEntries in this list */
	private int size;
	/** The height of this list */
	private int height;
	/** This is the mechanism that simulates a coin toss */
	private Random r;
	
	/**
	 * Constructor for the skip list
	 */
	public SkipList() {
		this.head = new SkipListEntry(SkipListEntry.NEGATIVE_INFINITY, 0);
		this.tail = new SkipListEntry(SkipListEntry.POSITIVE_INFINITY, 0);
		
		//make neg inf and pos inf point at each other
		this.head.setRight(this.tail);
		this.head.setLeft(this.head);
		
		this.size = 0;
		this.height = 0;
		this.r = new Random();
	}
	
	/**
	 * Search for a SkipListEntry, returning the largest key that is less
	 * than or equal to the given given to the method, on the lowest level
	 * of the skip list.  This method is not useful in itself, so it is actually
	 * a helper method for the skip list, used in the get, insert, and remove operations.
	 * @param key, the key for the entry (action + resource)
	 * @return SkipListEntry, the entry if it is found, null if not
	 */
	private SkipListEntry lookUp(String key) {
		//first make pointer to the top left node (head)
		SkipListEntry pointer = this.head;
		
		//continuing indefinitely
		while(true) {
			//scan right until a key is found that is greater this key, but not interested
			//in pointing to the end of the list, hence the "does not equal pos inf"
			while ((!pointer.getRight().getKey().equals(SkipListEntry.POSITIVE_INFINITY)) &&
					(pointer.getRight().getKey().compareTo(key) <= 0)) {
				pointer = pointer.getRight();
			}
			
			//go down to the lowest level possible
			if (pointer.getDown() != null) {
				pointer = pointer.getDown();
			} else {
				break; //we have reached the lowest point in the list
			}
		}
		
		return pointer;
	}
	
//	/**
//	 * This method will search the skip list for the given key, returning the value of that key if it
//	 * is in the skip list, or -1 if not found in the list.
//	 * @param key
//	 * @param val
//	 * @return value of that key
//	 */
//	public int get(String key, int val) {
//		SkipListEntry pointer = lookUp(key); //first find the key in the list
//		
//		if (pointer.getKey().equals(key)) {
//			return pointer.getValue(); //found the key
//		} else {
//			return NOT_FOUND;
//		}
//	}
	
	/**
	 * This is the method that will insert a SkipListEntry into the skip list.  It will first
	 * search for where to insert the skip list using lookUp(key), then insert it if there is no
	 * duplicate; otherwise, it will increment the frequency if there is a duplicate.
	 * @param key the key of the SkipListEntry
	 * @return frequency of this entry should be 1 if newly inserted into list, greater than 1 if duplicate
	 */
	public int insert(String key) {
		SkipListEntry pointer = lookUp(key);
		
		//see if we found the key
		if (pointer.getKey().equals(key)) {
			int value = pointer.getValue();
			value++; //increment frequency
			pointer.setValue(value);
			return value;
		}
		
		//key not found 
		//need to insert into the bottom list, then make a column of random height
		
		//first create new SLE to insert
		SkipListEntry q = new SkipListEntry(key, 1);
		
		//the pointer is now pointing to the element that q is supposed to be after
		q.setLeft(pointer);
		q.setRight(pointer.getRight());
		pointer.getRight().setLeft(q);
		pointer.setRight(q);
		
		
		int currentLevel = 0;  //keep track of current level
		
		//q has been inserted, need to add it to a random number of lists above the bottom now
		while(r.nextDouble() < 0.5) { //criteria for moving up one list
			
			//first check if a new layer needs to be added
			if (currentLevel >= height) {
				//create new entries at neg inf and pos inf
				SkipListEntry p1 = new SkipListEntry(SkipListEntry.NEGATIVE_INFINITY, 0);
				SkipListEntry p2 = new SkipListEntry(SkipListEntry.POSITIVE_INFINITY, 0);
				
				//link to to the skip list
				p1.setDown(this.head);
				p1.setRight(p2);
				p2.setDown(this.tail);
				p2.setLeft(p1);
				this.head.setUp(p1);
				this.tail.setUp(p2);
				
				//update head and tail pointers
				this.head = p1;
				this.tail = p2;
				
				//increment height
				this.height++;
			}
			
			//scan left until an element is found with a non-null above element
			while (pointer.getUp() == null) {
				pointer = pointer.getLeft();
			}
			
			//found element with non-null up pointer, point to this entry
			pointer = pointer.getUp();
			
			//update this skip list level with new entry to be added and make it point to q on list below
			SkipListEntry e = new SkipListEntry(key, 0);
			
			//initialize e
			e.setLeft(pointer);
			e.setRight(pointer.getRight());
			e.setDown(q);
			
			//change links around e
			q.setUp(e);
			pointer.getRight().setLeft(e);
			pointer.setRight(e);
			
			q = e; //for next iteration, we start at this level of the skip list
			
			currentLevel++; //moving on up
		}
		
		this.size++;
		
		return 1;
	}

	/**
	 * Getter for the number of elements in the skip list
	 * @return the number of entries in the list
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Iterates the skip list and returns the contents as a 2-d object array, first entry is the
	 * value (frequency) and the second is the key (the action + resource)
	 * @return Object[][], all the actions + resources, sorted by frequency.
	 */
	public String[] getArray() {
		SkipListEntry p = this.head;
		while (p.getDown() != null) {
			p = p.getDown();
		}
		
		p = p.getRight();  //move off of neg inf
		
		String[] arr = new String[this.size];
		
		int index = 0;
		//now the pointer is on the bottom list
		while (!p.getKey().equals(SkipListEntry.POSITIVE_INFINITY)) {
			StringBuilder sb = new StringBuilder();
			sb.append(p.getValue());
			sb.append(" ");
			sb.append(p.getKey());
			arr[index] = sb.toString();
			p = p.getRight();
			index++;
		}
		
		return arr;
	}
	
	/**
	 * Getter for sum of all the values
	 * @return double, sum of all values
	 */
	public double getValueSum() {
		SkipListEntry p = this.head;
		//go to bottom list
		while (p.getDown() != null) {
			p = p.getDown();
		}
		
		p = p.getRight();  //move off of neg inf
		
		double sum = 0.0;
		
		//now the pointer is on the bottom list
		while (!p.getKey().equals(SkipListEntry.POSITIVE_INFINITY)) {
			sum += (double) p.getValue();
			p = p.getRight();
		}
		
		return sum;
	}
	
}