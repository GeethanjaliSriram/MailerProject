**Mailer Project**

Program to mock sending emails to 1 million recipients with the help of Threads and Semaphores with a wait of 500 milliseconds. The large *List* consisting of recipients is sliced between 500 emailWorkers. The concept of parallelism is implemented to perform ‘send email’ tasks time and performance efficiently. The Semaphore concept assures the completion of all the tasks assigning to each emailWorker. The program can handle the failures and successes of email sending, all though it is just faking email sending, by implementing a logic to randomly generate ‘true’ or ‘false’ with a higher percentage of ‘true’.

#Pre-requisites to run project
- The project can be run either from an IDE (Ecliipse, IntelliJ) or from Command Prompt
- Java 1.8

#Setting up project on local environment
Clone the project into your local repository.
```
git clone https://github.com/GeethanjaliSriram/MailerProject.git
cd mailerproject
```

If using Eclipse, the project can be run directly as the repository includes project files

OR from command promt, the code should be compiled and executed
```
javac EmailClass.javac
java EmailClass
```

#Sample output looks like below
```
Number of recipients: 1000000
Workers used to send Emails: 500
Successfully sent Emails: 970000
Total Execution Time: 1002 secs
```

- The emails are sliced equally between all the *EmailWorkers* and processed parallelly.

- **In real-world scenario, there can be few emails not successfully delivered due to some issues. Such a scenario is mocked in the program where few records are stored as Not Sent. Hence, in the output *Successfully sent Eamils* are lesser than the *Number of recipients* as seen in Sample output.**

- The time taken to execute program is also tracked and displayed in seconds.

