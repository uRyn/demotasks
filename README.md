# About demotasks

This is a demo for create a web service to manage your task list written by my almost no experience programming language java.
Development in `macOS`, coding with `Eclipse`, tested in `Debian 11 (bullseye)` and `openEuler 22.03`.
No optimization, no much verification, some hardcodes and no persistent storage, it only cached in runtime.


# Intruduction

## Server
- This project is using maven to build and management, using Jersey to create RESTful service, using Grizzly as container, junit for test.

- Project sources are located under tasks-server/src/main/java.
- Project test sources are located under tasks-server/src/test/java.

- There are 2 classes in the project source directory in the com.uRyn package. The Main class is responsible for bootstrapping the Grizzly container as well as configuring and deploying the project's JAX-RS application to the container. Another class in the same package is Tasks class, that contains implementation of a simple REST api.
- The DataAccess class is the task list backed storage, it store the data in a array and it will only cached in run-time.
- A simple logger will log the tasks operations in a log file tasks.log

- A TasksTest unit test class that is located in the same com.uRyn package as the Tasks class, however, this unit test class is placed into the maven project test source directory tasks-server/src/test/javal

## Client
- The client is a bash shell script located in the project's root directory named `tasks.sh`. This shell script rely on CURL.

# Prerequisite

## Install Maven
- Debian/Ubuntu
```
$ sudo apt install -y maven
```
- RHEL/CentOS/Fedora/openEuler
 ```
 $ sudo yum install -y maven | sudo dnf install -y maven
 ```

## Install CURL (optional)
If the server which you want to run the client shell script tasks.sh you need to install the CURL.
- Debian/Ubuntu
```
$ sudo apt install -y curl
```
- RHEL/CentOS/Fedora/openEuler
 ```
 $ sudo yum install -y curl | sudo dnf install -y curl
 ```

# How to build
## Get code
- Assume you will clone into `~demotasks`:
```
$ git clone https://github.com/uRyn/demotasks.git
```
- Open `~/demotasks` in terminal
```
$ cd ~/demotasks
```
## Run test
```
$ mvn -f tasks-server/ clean test
```
This will download the dependencies, compile the code and run the unit tests. Download may be slow due to network speed. In my side, it took me 5 minutes.
If all succeed, the web service will automatic start and stop. you will see this output when finished:
```
Results :

Tests run: 5, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  9.854 s
[INFO] Finished at: 2022-07-10T10:26:27+08:00
[INFO] ------------------------------------------------------------------------

```

## Run the web service
if you don't run the test in the previous step, you need to compile the project first before it can run:
```
$ mvn -f tasks-server/ compile
$ mvn -f tasks-server/ exec:java
```
Now, you can use the client shell scrpit `tasks.sh` to manage your task list.
if you want to stop the web service, just hit **Ctrl-C** int the terminal to stop it.

# Client usage
1. Run the shell script without and parameter will print the help/usage description (Make sure to install the CURL first)
	```
	$ ./tasks.sh
	For help: ./tasks.sh -help

	Notes:
		1. This script rely on CURL, please make sure to install CURL first!
			 Debian/Ubuntu:      sudo apt install curl
			 RHEL/CentOS/Fedora: sudo yum install curl
		2. Please start the rest server first!
		3. The default URL is http://localhost:8080/tasks, if you want to run this script from a remote server, please manual modify the value of the URL variable: BASE_URL

	Usage:
		./tasks.sh add "task title" 21/08/2019
		./tasks.sh list
		./tasks.sh list --expiring-today
		./tasks.sh done 3
		./tasks.sh delete 3
	```
2. If you want to run the shell script in a remote server rather the server which the web server running, please make sure the firewall is opened the default port 8080 and client have CURL install.
Here is how to open port in RHEL/centOS/openEuler:
	```
	$ sudo firewall-cmd --zone=public --add-port=8080/tcp --permanent
	$ sudo firewall-cmd --reload
	```
3. Add new task
	```
	$ ./tasks.sh add "task title1" 21/08/2019
	$ ./tasks.sh add "task title2" 10/07/2022
	```
4. List existing task
	```
	$ ./tasks.sh list

	output:
	Title: task title1, date: 21/08/2019, Status: not done, id: 0
	Title: task title2, date: 10/07/2022, Status: not done, id: 1
	```
5. List task expiring-today
	```
	$ ./tasks.sh list --expiring-today

	output:
	Title: task title2, date: 10/07/2022, Status: not done, id: 1
	```
6. Task set done with the according id and list the tasks
	```
	$ ./tasks.sh done 1
	$ ./tasks.sh list

	output:
	Title: task title1, date: 21/08/2019, Status: not done, id: 0
	Title: task title2, date: 10/07/2022, Status: done, id: 1
	```
7. Delete task with according task id and list tasks
	```
	$ ./tasks.sh delete 1
	$ ./tasks.sh list

	output:
	Title: task title1, date: 21/08/2019, Status: not done, id: 0
	```
