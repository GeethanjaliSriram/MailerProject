package email;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.TimeUnit;

class EmailClass 
{
	public static ArrayList<String> recipientDetails = new ArrayList<String>();
	static Semaphore allDone;
	long startTime, endTime;

 public EmailClass(int recipients) 
 {
	 for (int i=0; i<recipients; i++)
	 {		 
		 recipientDetails.add(RandomString()); //Populate list with random strings
	 }
 }
 
 public static void main(String[] args) throws InterruptedException 
 { //The program starts from here
	 int recipients =1000000; //Number of Email recipients

	 EmailClass ec = new EmailClass(recipients);
	 
	 ec.executeWorkers();
 } 

//Method to simultaneously run tasks
 public void executeWorkers() throws InterruptedException 
 {
	int numEmailWorkers = 500; //Number of emailWorkers
	allDone = new Semaphore(0); //Declaring Semaphore
 
	List<EmailWorker> emailWorkers = new ArrayList<EmailWorker>();
	int loadPerWorker = recipientDetails.size() / numEmailWorkers; //Load per emailWorker is 1000000/5000 = 200
 
	startTime = System.currentTimeMillis(); //Time before creating threads
	for (int i=0; i<numEmailWorkers; i++) 
	{
		//Indexes of arrays sliced to feed each emailWorker
		int firstRecord = i * loadPerWorker;
		int lastRecord = (i+1) * loadPerWorker;

		if (i==numEmailWorkers-1) lastRecord = recipientDetails.size(); // To pick up left out load by last emailWorker
		
		EmailWorker singleWorker = new EmailWorker(firstRecord, lastRecord); 
		
		emailWorkers.add(singleWorker);
		
		
		singleWorker.start(); //Starting thread
	}

	try 
	{
		allDone.acquire(numEmailWorkers); //When all the emailWorkers have finished the tasks
		endTime = System.currentTimeMillis(); //Time after all the threads have finished tasks
	} 
	catch (InterruptedException ignored) {
	}
 
	long totalSent = 0; //Total emails successfully sent
	List<String> totalStatus = new ArrayList<String>(); //List with recipients and their email status

	//For each emailWorker calculating the total number of successful emails sent to recipients
	for (EmailWorker ew: emailWorkers) 
	{	
		totalStatus.addAll(ew.getTotalSent());
		for (int i=0; i<ew.getTotalSent().size(); i++)
		 {
			//System.out.println(totalStatus.get(i));
			 if (totalStatus.get(i).contains("true"))
				 totalSent++; //Calculate if the email status is true
		 }
	}
	
	long duration = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);

	System.out.println("Recepients: " + recipientDetails.size() + 
			"\nWorkers used to send Emails: " + numEmailWorkers +
			"\nSuccessfully sent Emails: " + totalSent + 			
			"\nTotal Execution Time: " + duration + " secs");		 
 }
 
//Method to generate random strings for mocking recipients mail IDs. Can be replaced with method with actual recipients IDs
//Reference: Internet page - https://www.baeldung.com/java-random-string
 public String RandomString()
 { 
	int leftLimit = 97; // letter 'a'
	int rightLimit = 122; // letter 'z'
	int targetStringLength = 10;
	Random random = new Random();
	StringBuilder buffer = new StringBuilder(targetStringLength);
	
	for (int i = 0; i < targetStringLength; i++) 
	{
     int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
     buffer.append((char) randomLimitedInt);
	}
	
	String generatedString = buffer.toString();
	return generatedString;
 }
} 
