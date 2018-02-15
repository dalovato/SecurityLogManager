/**
 * 
 */
package edu.ncsu.csc316.security_log.ui;

import java.io.File;
import java.util.Scanner;

import edu.ncsu.csc316.security_log.manager.SecurityLogManager;

/**
 * This class holds the UI code for running the program and interacting with the user.
 * @author David Lovato
 */
public class SecurityLogManagerUI {

	/**
	 * Runs the main method of the program by interacting with a user utilizing the
	 * command line.
	 * @param args array of String arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to PackHealth!");
		System.out.println("Type 'quit' at any time to quit.");
		System.out.println("To get started, please enter an input filename: ");
		String filePath = sc.nextLine().trim();
		for (;;) {
			File f = new File(filePath);
			if (filePath.equalsIgnoreCase("quit")) {
				sc.close();
				System.exit(0);
			} else if (!f.exists()) {
				System.out.println("File does not exist. Please enter valid file name.");
				System.out.println("To get started, please enter an input filename: ");
				filePath = sc.nextLine().trim();
			} else {
				SecurityLogManager manager = new SecurityLogManager(filePath);
				System.out.println("Please use the following commands: ");
				System.out.println("'generate profile' - Generates operational profile for a given time period.");
				System.out.println("'produce user report' - Produces activity report for a given user");
				System.out.println("Type 'quit' at any time to quit.");
				System.out.println("What do you want to do?");
				String command = sc.nextLine().trim();
				if (command.equalsIgnoreCase("quit")) {
					sc.close();
					System.exit(0);
				} else if (command.equalsIgnoreCase("generate profile")) {
					System.out.println("Please enter start and end times in the format MM/DD/YYYY HH:MM:SSXM");
					System.out.println("Start time: ");
					String start = sc.nextLine().trim();
					System.out.println("End time: ");
					String end = sc.nextLine().trim();
					System.out.println(manager.generateOperationalProfile(start, end));
				} else if (command.equalsIgnoreCase("produce user report")) {
					System.out.println("Please enter user name: ");
					String username = sc.nextLine().trim();
					System.out.println(manager.getUserReport(username));
				} else {	
					System.out.println("Invalid command. Please enter a valid command, or enter 'quit' to exit.");
				}
			}
		}
	}
}
