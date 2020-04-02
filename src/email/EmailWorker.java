package email;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmailWorker extends Thread 
{
	 int first;
	 int last;
	 long totalSent;
	 String tempString;
	 boolean status;
	 List<String> listStatus = new ArrayList<String>();


	 EmailWorker(int first, int last) 
	 { //Initialize variables
		 this.first = first;
		 this.last = last;
		 totalSent = 0;
	 }

	 @Override
	 public void run() 
	 {					 
		 try 
		 {
			  for (int i=first; i<last; i++) 
			  {
				  //Sending Emails and storing the status
				  
				  status = (Math.random() < 0.95);	//generate 'true' or 'false' as status. Mocking status of sending emails 				  
				  tempString = EmailClass.recipientDetails.get(i) + " " 
				  + Boolean.toString(status);//Update the status 'true' if email was successfully sent else 'false'				  
				  this.listStatus.add(tempString); //Adding status with recipient ID
	 
				  TimeUnit.MILLISECONDS.sleep(500); //Delay of half a second
			  }
		 } 
		 catch (InterruptedException e) 
		 {
			 e.printStackTrace();
		 }
	 
		 EmailClass.allDone.release(); //Once the task is completed
	 }

	 public List<String> getTotalSent() throws InterruptedException 
	 {
		 return listStatus;
	 }
}
