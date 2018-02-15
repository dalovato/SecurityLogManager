/**
 * 
 */
package edu.ncsu.csc316.security_log.util;

import java.io.Serializable;

/**
 * This class holds the functionality of ArrayList.
 * 
 * CSC316 NOTE: This ArrayList implementation comes from Project 3 in CSC216, github repo
 * csc216-002-P3-031.  Created by me and my partner, Randy Jaouhari.
 * 
 * @author David Lovato
 * @author Randy Jaouhari
 */
public class ArrayList implements Serializable {
	
	/** Variable holding the size of an ArrayList */
	private int size;
	
//	/** Variable for when to resize Array */
//	private static final int RESIZE = 10;

	/** Version UID for reading the file */
	private static final long serialVersionUID = 28592L;
	
	/** Variable for an array of objects */
	private Object[] list;
	
//	/**
//	 * Constructor for arrayList.
//	 */
//	public ArrayList() {
//		this(RESIZE);
//	}
	
	/** 
	 * Arraylist Constructor 
	 * @param i index
	 * @throws IllegalArgumentException if i less than or equal zero.
	 */
	public ArrayList(int i) {
//		if (i <= 0) {
//			throw new IllegalArgumentException();
//		}
		list = new Object[i];
		size = 0;
	}

	/**
	 * Getter for size
	 * @return int the size of the list
	 */
	public int size() {
		return size;
	}

//	/**
//	 * Checks to see if Array is empty
//	 */
//	public boolean isEmpty() {
//		return (size == 0);
//	}

//	/** 
//	 * Method for checking if an array contains an object
//	 * @param o Object to be checked
//	 * @return boolean of whether or not array contains the object in the list.
//	 */
//	
//	public boolean contains(Object o) {
//		for (int i = 0; i < size; i++) {
//			if (list[i].equals(o)) {
//				return true;
//			}
//		}
//		return false;
//	}

	/**
	 * Method for adding objects to the array
	 * @param o Object to be added
	 * @return boolean of whether or not the array can add the object
	 * @throws IllegalArgumentException if a duplicate object is added.
	 */
	public boolean add(Object o) {
//		if (this.contains(o)) {
//			throw new IllegalArgumentException();
//		}
		if (size == 0) {
			this.add(0, o);
		} else {
			this.add(size, o);
		}
		return true;
	}

	/**
	 * Method for getting an object at an index
	 * @param index The index being checked
	 * @return Object at that index
	 * @throws IllegalArgumentException if index is less than 0 or greater 
	 * than size.
	 */
	public Object get(int index) {
//		if (index < 0 || index >= size) {
//			throw new IndexOutOfBoundsException();
//		}
		return list[index];
	}

	/**
	 * This method adds an object at a cetain index
	 * @param element Object to be added
	 * @param index to be added
	 */
	public void add(int index, Object element) {
//		if (this.contains(element)) {
//			throw new IllegalArgumentException();
//		}
//		if (element == null)
//            throw new NullPointerException();
//        if (index > size || index < 0)
//            throw new IndexOutOfBoundsException();
//        if (size == list.length) {
//        	Object[] list2 = new Object[size * 2];
//        	for (int i = 0; i < size; i++) {
//        		list2[i] = list[i];
//        	}
//        	list = list2;
//        }
//        for (int i = size() - 1; i >= index; i--) {
//            list[i + 1] = list[i];
//        }
        list[index] = element;
        size++;
	}

//	/**
//	 * This method removes an object at the given index.
//	 * @param index Index at which the object is removed
//	 * @return Object the object to be removed
//	 */
//	public Object remove(int index) {
//		if (index < 0 || index >= size()) {
//			throw new IndexOutOfBoundsException();
//		}
//		int i = index;
//		Object temp = list[index];
//		while (i <= size() - 1) {
//			list[i] = list[i + 1];
//			i++;
//		}
//		list[size - 1] = null;
//		size--;
//		return temp;
//	}
//
//	/**
//	 * This method checks the index of a given object.
//	 * @param o Object to be checked
//	 * @return int the index of the object
//	 */
//	public int indexOf(Object o) {
//		for (int i = 0; i < size; i++) {
//			if (this.get(i).equals(o)) {
//				return i;
//			}
//		}
//		return -1;
//	}
	
	/**
	 * Getter for the array.
	 * @return Object[], the actual array of objects
	 */
	public Object[] getArray() {
		return this.list;
	}

}
