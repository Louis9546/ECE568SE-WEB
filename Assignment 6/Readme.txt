This Readme is for Homework 6: Basic Server and Basic Client 
Author: Ze Liu (zl265)

1: For normal EXIT, my server and client will show: "Normal Exit. The connection has ended!”.
2: For abnormal EXIT<xxxx>, my server and client will show: "Exit with code: xxxx”.
3: For BOUNCE<xxx>, my server will show the text: xxx, while client will show "Succeed in getting your BOUNCE information: “xxxx”
4: For GET command, both the server and client will show the content of txt file if it exists.
5: My program is single threaded, so the server can serve only one client at a time.



A set of example commands on how to run the code (MacOS):
1: Open two terminals: Terminal 1 and Terminal 2
2: On Terminal 1: Use “cd” command to go to the file location; then use “javac BasicServer.java” to get the class file; Use “java BasicServer 5500” to run the server.
3: On Terminal 2: Use “cd” command to go to the file location; then use “javac BasicClient.java” to get the class file; Use “java BasicClient hostname 5500” to run the server.

Warning: hostname is different even at the same computer, it depends on your network. Please use command “hostname” in terminal to get your hostname.