import java.io.*;
import java.sql.*;
import java.util.*;

public class projectCode {
    public static void main(String[] arg) throws SQLException, IOException, java.lang.ClassNotFoundException {
    	try {
	        Scanner scan = new Scanner(System.in);
	        boolean flag = false;
	        boolean flag2 = false;
	        String uid = "";
	        String input = "";
	        int task = 0;
		    while (flag == false) {
		        System.out.println((char)27 + "[0m" + "Enter userID: ");
		        uid = scan.nextLine();
		        System.out.println((char)27 + "[0m" + "Enter Password:");
		        input = scan.nextLine();
		        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",
		                uid, input); Statement s = con.createStatement();) {
		        	System.out.print("\033[H\033[2J");  
	    			System.out.flush(); 
		        	System.out.print("\033[40m\033[30m");
		        	System.out.println((char)27 + "[1;96m" + "---------------------");
		            System.out.println((char)27 + "[1;96m" + "| Welcome To Regork |");
		            System.out.println((char)27 + "[1;96m" + "---------------------\n");
		            flag = true;
		            while (!flag2) {
		                System.out.println((char)27 + "[0;92m" + "Who are you?");
		                System.out.println((char)27 + "[97m" + "Press:");
		                System.out.println((char)27 + "[97m" + "  -1 General Manager");
		                System.out.println((char)27 + "[97m" + "  -2 Supplier Manager");
		                System.out.println((char)27 + "[97m" + "  -3 Product Manager");
		                System.out.println((char)27 + "[97m" + "  -4 Shipment Manager");
		                System.out.println((char)27 + "[97m" + "  -5 Quit");
		                if (!scan.hasNextInt()) {
		                    System.out.print((char)27 + "[31m" + "Not an integer. Try again.");
		                	System.out.println((char)27 + "[97m" + "");
		                    scan.next();
		                    continue;
		                }
		                task = scan.nextInt();
		                if (task == 1) {
		                    manager man = new manager();
		                    man.main(con, s);
		                }
		                else if (task == 2) {
		                    supChainManager chain = new supChainManager();
		                    chain.main(con, s);
		                }
		                else if (task == 3) {
		                    prodManager prodM = new prodManager();
		                    prodM.main(con, s);
		                }
		                else if (task == 4) {
		                    placeOrder order = new placeOrder();
		                    order.main(con, s);
		                }
		                else if (task == 5) {
		                    System.out.print("\033[H\033[2J");  
	    					System.out.flush();
	    					System.out.println((char)27 + "[1;96m" + "Have a wonderful day!");
		                    con.close();
		                    flag2 = true;
		                }	               
		                else {
		                    System.out.print((char)27 + "[31m" + "Not a valid task. Try again.");
		                	System.out.println((char)27 + "[97m" + "");    
		                }
		            }
		        } catch (Exception e) {
		            System.out.print((char)27 + "[31m" + "Connection not successfull. Try again.");
		            System.out.println((char)27 + "[0m" + "");
		            flag = false;
		        }
		        System.out.print((char)27 + "[0m" + "");
		    }
		} catch (Throwable e) {
			System.out.print((char)27 + "[31m" + "Something went wrong. Our staff will fix this as soon as possible");
		    System.out.println((char)27 + "[0m" + "");
		    return;
		}
    }
}
