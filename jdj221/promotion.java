import java.io.*;
import java.sql.*;
import java.util.*;
public class promotion {
	public void main() throws SQLException, IOException, java.lang.ClassNotFoundException {
		try {
			Scanner scan = new Scanner(System.in);
	        boolean flag = false;
	        int task = 0;
	        System.out.print("\033[H\033[2J");  
	        System.out.flush();
			while (!flag) {
		        System.out.println((char)27 + "[92m" + "What would you like to do?");
		        System.out.println((char)27 + "[97m" + "Press:");
		        System.out.println((char)27 + "[97m" + "  -1 Promote Jimmy Johnson");
		        System.out.println((char)27 + "[97m" + "  -2 return to manager menu");
		        if (!scan.hasNextInt()) {
		            System.out.print((char)27 + "[31m" + "Not an integer. Try again.");
		            System.out.println((char)27 + "[97m" + "");
		            scan.next();
		            continue;
		        }
		        task = scan.nextInt();
		        if (task == 1) {
		        	System.out.println((char)27 + "[1;93m" + "");
		            System.out.println((char)27 + "[1;93m" + "---------------------------------------------------");
		            System.out.println((char)27 + "[1;93m" + "| You have promoted Jimmy Johnson! Great Choice!! |");
		            System.out.println((char)27 + "[1;93m" + "---------------------------------------------------");
		            System.out.println((char)27 + "[1;93m" + "");
		            System.out.println((char)27 + "[96m" + "Taking user back to manager menu");
		            return;
		        }

		        else if (task == 2) {
		        	System.out.println((char)27 + "[96m" + "Taking user back to manager menu");
		        	System.out.print("\033[H\033[2J");  
	                System.out.flush();
		        	return;
		        }
		        
		        else {
		        	System.out.print((char)27 + "[31m" + "Not a valid task. Try again.");
	                System.out.println((char)27 + "[97m" + "");
		        }
		    }
		} catch (Throwable e) {
			System.out.println((char)27 + "[96m" + "Something went wrong. Our staff will fix this as soon as possible"); 
            return;
		}
	}
}