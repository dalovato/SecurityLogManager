This project was designed to manage user activity on a healthcare system called PackHealth.  The way this project works is to use an
input file (example ones are provided in the /input/ folder), and then use the commands to generate user profiles or activity reports.

For the "generate user profile" command, the command line will ask the user to input a user's name. 
Then, the program will output a report of all the user's activity, in chronological order, then alphabetical order. 

For the "generate operational profile" command, the command line will ask the user to input two timestamps, from which the program will
output all the activity that occurred in between those two time stamps, ordering the report by frequency of activity.

This project is meant to be run with a command line interface and was developed in Java using Eclipse as the IDE.  Some interesting data
structures this project uses is a sorted array (sorted using my own implementation of mergesort) and a skip list, which was also implemented by me.

Enjoy!
