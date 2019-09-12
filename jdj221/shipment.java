import java.io.*;
import java.sql.*;
import java.util.*;
public class shipment {
	public void main(Connection c, Statement st) throws SQLException, IOException, java.lang.ClassNotFoundException {
        try {
    		Scanner scan = new Scanner(System.in);
    		Connection con = c;
    		Statement s = st;
            boolean flag = false;
            int task = 0;
            int sup_num = 30;
            PreparedStatement stmt = null;
            System.out.print("\033[H\033[2J");  
            System.out.flush();
    		while (!flag) {
    	        System.out.println((char)27 + "[92m" + "What would you like to do?");
    	        System.out.println((char)27 + "[97m" + "Press:");
    	        System.out.println((char)27 + "[97m" + "  -1 view all shipments");
    	        System.out.println((char)27 + "[97m" + "  -2 see what products a shipment contains and its price");
    	        System.out.println((char)27 + "[97m" + "  -3 see supplier information for a shipment");
    	        System.out.println((char)27 + "[97m" + "  -4 to return to manager menu");
    	        if (!scan.hasNextInt()) {
    	            System.out.print((char)27 + "[31m" + "Not an integer. Try again.");
    	            System.out.println((char)27 + "[97m" + "");
    	            scan.next();
    	            continue;
    	        }
    	        task = scan.nextInt();
    	        if (task == 1) {
    	        	System.out.println((char)27 + "[96m" + "You have selected to view shipment information.");
    		        String p;
    	            p = "select * from shipment";
    	            try (ResultSet result = s.executeQuery(p);) {
    	                if (!result.next()) {
    	                    System.out.println("No results found. Going back to shipment menu...");
    	                    continue;
    	                }
                        System.out.print((char)27 + "[97m" + "");
                        System.out.printf("%-15s%-15s%-15s\n","Shipment_ID","Quantity","Date");
                        System.out.print((char)27 + "[96m" + "");
    	                do {
                            System.out.printf("%-15s%-15s%-15s\n",result.getString("shipment_id"),result.getString("quantity"),result.getString("DT"));
    	                    // System.out.println("  " + result.getString("shipment_id") + " " + result.getString("quantity") + " " + result.getString("DT"));
    	                } while (result.next());

    	            } catch (Exception e) {
    	                System.out.println("This is an excetion and this is unacceptable");
    	            }
    	        }

    	        else if (task == 2) {
    	        	System.out.println((char)27 + "[97m" + "Input the shipment_id you wish to use."); 
                    int ship_id = 0;
                    if (!scan.hasNextInt()) {
                        System.out.print((char)27 + "[31m" + "Not an valid input. Going back to shipment menu...");
                        System.out.println((char)27 + "[97m" + "");
                        scan.next();
                        continue;
                    }
                    ship_id = scan.nextInt();
                    if (ship_id <= 0) {
                        System.out.print((char)27 + "[31m" + "Input not in range. Going back to supplier menu...");
                        System.out.println((char)27 + "[97m" + "");
                        continue;
                    }
                    stmt = con.prepareStatement("select * from shipment s, contains c, product p where s.shipment_id = c.shipment_id and c.product_id = p.product_id and s.shipment_id = ?");
                    stmt.setInt(1, ship_id);  
                    try (ResultSet result1 = stmt.executeQuery();) {
                        if (!result1.next()) {
                            System.out.println("No results found. Going back to supplier menu.");
                            continue;
                        }
                        int marker = 1;
                        do {
                            System.out.println((char)27 + "[96m" + "Result number " + marker + ":");
                            System.out.println((char)27 + "[97m" + "  Product ID => " + result1.getString("product_id"));
                            System.out.println((char)27 + "[97m" + "  Product Name => " + result1.getString("p_name"));
                            System.out.println((char)27 + "[97m" + "  Quantity => " + result1.getString("quantity"));
                            System.out.println((char)27 + "[97m" + "  PPU => $" + result1.getString("PPU"));
                            marker++;
                        } while (result1.next());

                    } catch (Exception e) {
                        System.out.println("This is an excetion and this is unacceptable");
                    } 
    	        }

    	        else if (task == 3) { 
                    System.out.println((char)27 + "[97m" + "Input the shipment_id you wish to use."); 
                    int ship_id = 0;
                    if (!scan.hasNextInt()) {
                        System.out.print((char)27 + "[31m" + "Not an valid input. Going back to shipment menu...");
                        System.out.println((char)27 + "[97m" + "");
                        scan.next();
                        continue;
                    }
                    ship_id = scan.nextInt();
                    if (ship_id <= 0) {
                        System.out.print((char)27 + "[31m" + "Input not in range. Going back to supplier menu...");
                        System.out.println((char)27 + "[97m" + "");
                        continue;
                    }
                    stmt = con.prepareStatement("select * from shipment s, ships sh, supplier su where s.shipment_id = sh.shipment_id and sh.supplier_id = su.supplier_id and su.supplier_id = ?");
                    stmt.setInt(1, ship_id);  
                    try (ResultSet result1 = stmt.executeQuery();) {
                        if (!result1.next()) {
                            System.out.println("No results found. Going back to supplier menu.");
                            continue;
                        }
                        int marker = 1;
                        do {
                            System.out.println((char)27 + "[96m" + "Result number " + marker + ":");
                            System.out.println((char)27 + "[97m" + "  Supplier ID => " + result1.getString("supplier_id"));
                            System.out.println((char)27 + "[97m" + "  Supplier Name => " + result1.getString("sup_name"));
                            System.out.println((char)27 + "[97m" + "  Address => " + result1.getString("address"));
                            marker++;
                        } while (result1.next());

                    } catch (Exception e) {
                        System.out.println("This is an excetion and this is unacceptable");
                    }
    	        }

    	        else if (task == 4) {
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