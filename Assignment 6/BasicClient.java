/* Assignment 6
 * ZE LIU (zl265)
 * Client: Java
 */ 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Pattern;


public class BasicClient {
	public static void main(String[] args) {
	    if (args.length != 2) { // Test for correct num. of arguments: The server host name and its port number
	        System.err.println(
	            "ERROR server host name AND port number not given");
	        System.exit(1);
	    }
    	int port_num = Integer.parseInt(args[1]); // The Second argument

    	 
		try {
			Socket c_sock = new Socket(args[0], port_num); 

			BufferedReader in = new BufferedReader(new InputStreamReader(c_sock.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(c_sock.getOutputStream()), true);
        	BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));

        	boolean temp = true;
        	innerLoop:
        	while(temp){
	        	System.out.print("User, enter your command: "); 
	        	String input = userEntry.readLine();

				String pattern = "\\b(GET|BOUNCE|EXIT)\\b<.*>|\\bEXIT\\b";
                boolean isMatch = Pattern.matches(pattern, input);
                if(!isMatch){
                	System.out.println("Warning! No such command");
                	continue;
                }
                else{
                	System.out.println("Your Input: " + input);
                	out.println(input); // Post command to the server
                }

                if(input.length() >= 4 && input.substring(0, 4).equals("EXIT")){
                	temp = false;
                }

                System.out.println("Server says \"" + in.readLine() +"\""); // Get the information from server

                if(input.length() >= 4 && input.substring(0, 4).equals("GET<")) {
                	int times = Integer.parseInt(in.readLine());
                	for(int i = 0; i < times; i++){
                		String tem = in.readLine();
                		System.out.println(tem);
                	}
                	// while(true){
                	// 	String ServerInput = in.readLine();
                	// 	if(ServerInput == null) {
                	// 		break innerLoop;
                	// 	}
                	// 	System.out.println(ServerInput);
                	// }
                	
                }
                System.out.println(); 
                System.out.println(); 
        	} 
			c_sock.close();
    	} catch (IOException ex) { ex.printStackTrace(); }
    	System.exit(0);
  	}
}