/* Assignment 6
 * ZE LIU (zl265)
 * Server: Java
 */ 

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class BasicServer {
    public static void main(String[] args) {
        if (args.length != 1) { // Test for correct num. of arguments
            System.err.println( "ERROR server port number not given");
            System.exit(1);
        }
        int port_num = Integer.parseInt(args[0]);
        ServerSocket rv_sock = null;
        try {
            rv_sock = new ServerSocket(port_num);
        } catch (IOException ex) { ex.printStackTrace(); }


        while (true) { // run forever, waiting for clients to connect
            System.out.println("\nWaiting for client to connect...");
            try {
                Socket s_sock = rv_sock.accept(); 
                BufferedReader in = new BufferedReader(new InputStreamReader(s_sock.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(s_sock.getOutputStream()), true);

                boolean temp = true;
                while(temp){
                    String clientInput = in.readLine();
                    System.out.println("Client's command: " + clientInput);  // Get the command from client.

                    // Normal Exit
                    if(clientInput.length() == 4 && clientInput.equals("EXIT")){
                        System.out.println("Normal Exit. The connection has ended!");
                        out.println("Normal Exit. The connection has ended!"); // Post information to client
                        temp = false;
                        continue;
                    }

                    // Abnormal Exit
                    else if(clientInput.length() >= 5 && clientInput.substring(0,5).equals("EXIT<")) {
                        System.out.println("Exit with code: " + clientInput.substring(5, clientInput.length() - 1));
                        out.println("Exit with code: " + clientInput.substring(5, clientInput.length() - 1)); // Post information to client
                        temp = false;
                    }

                    // BOUNCE
                    else if(clientInput.length() >= 7 && clientInput.substring(0, 7).equals("BOUNCE<")) {
                        System.out.println(clientInput.substring(7, clientInput.length() - 1)   );
                        out.println("Succeed in getting your BOUNCE information: " + clientInput.substring(7, clientInput.length() - 1));
                    }

                    // GET
                    else if(clientInput.length() >= 4 && clientInput.substring(0, 4).equals("GET<")) {
                        String fileName = clientInput.substring(4, clientInput.length() - 1);
                        File file = new File(fileName);
                        if(file.exists()){
                            Scanner sc = new Scanner(file);
                            System.out.println("Reading file: " + fileName);
                            out.println("Reading file: " + fileName);
                            ArrayList<String> al = new ArrayList<String>();
                            int times = 0;
                            while(sc.hasNextLine()){ 
                                String line = sc.nextLine();
                                System.out.println(line);
                                al.add(line);
                                // out.println(line);
                                times++;
                            }
                            sc.close();
                            out.println(times);
                            for(String s : al){
                                out.println(s);
                            }
                        }
                        else{
                            System.out.println("ERROR: no such file");
                            out.println("ERROR: no such file");
                        }
                    }

                    // Error Command
                    else{
                        System.out.println("Error Command!");
                        out.println("Error Command!");

                    }
                    System.out.println();  
                    System.out.println(); 
                }
                s_sock.close();
            } catch (IOException ex) { ex.printStackTrace(); }
        }
    } 
}