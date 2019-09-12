import java.io.*;
import java.sql.*;
import java.util.*;
public class manager {
	public void main(Connection c, Statement st) throws SQLException, IOException, java.lang.ClassNotFoundException {
		try {
			Scanner scan = new Scanner(System.in);
			Connection con = c;
			Statement s = st;
	        boolean flag = false;
	        int task = 0;
	        System.out.print("\033[H\033[2J");  
	    	System.out.flush(); 
			while (!flag) {
		        System.out.println((char)27 + "[92m" + "What would you like to do?");
		        System.out.println((char)27 + "[97m" + "Press:");
		        System.out.println((char)27 + "[97m" + "  -1 to view product information");
		        System.out.println((char)27 + "[97m" + "  -2 to view supplier information");
		        System.out.println((char)27 + "[97m" + "  -3 to view shipment information");
		        System.out.println((char)27 + "[97m" + "  -4 to order a recall");
		        System.out.println((char)27 + "[97m" + "  -5 to promote Jimmy Johnson");
		        System.out.println((char)27 + "[97m" + "  -6 to return to main menu");
		        if (!scan.hasNextInt()) {
		            System.out.print((char)27 + "[31m" + "Not an integer. Try again.");
		            System.out.println((char)27 + "[97m" + "");
		            scan.next();
		            continue;
		        }
		        task = scan.nextInt();
		        if (task == 1) {
		        	product prod = new product();
		        	prod.main(con, s);
		        }
		        else if (task == 2) {
		        	supplier sup = new supplier();
		        	sup.main(con, s);
		        }
		        else if (task == 3) {
		        	shipment ship = new shipment();
		        	ship.main(con, s);
		        }
		        else if (task == 4) {
		        	recall rec = new recall();
		            rec.main(con, s);
		        }
		        else if (task == 5) {
		        	promotion prom = new promotion();
		        	prom.main();
		        }
		        else if (task == 6) {
		        	System.out.println((char)27 + "[96m" + "Taking user back to main menu");
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