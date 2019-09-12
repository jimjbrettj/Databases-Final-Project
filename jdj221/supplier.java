import java.io.*;
import java.sql.*;
import java.util.*;
public class supplier {
	public void main(Connection c, Statement st) throws SQLException, IOException, java.lang.ClassNotFoundException {
        try {
    		Scanner scan = new Scanner(System.in);
    		Connection con = c;
    		Statement s = st;
            boolean flag = false;
            int task = 0;
            PreparedStatement stmt = null;
            System.out.print("\033[H\033[2J");  
            System.out.flush();
    		while (!flag) {
    	        System.out.println((char)27 + "[92m" + "What would you like to do?");
    	        System.out.println((char)27 + "[97m" + "Press:");
    	        System.out.println((char)27 + "[97m" + "  -1 view all suppliers");
    	        System.out.println((char)27 + "[97m" + "  -2 see what products a supplier supplies");
    	        System.out.println((char)27 + "[97m" + "  -3 view shipments from a specific supplier");
                System.out.println((char)27 + "[97m" + "  -4 view the current number of supplier");
    	        System.out.println((char)27 + "[97m" + "  -5 to return to manager menu");
    	        if (!scan.hasNextInt()) {
    	            System.out.print((char)27 + "[31m" + "Not an integer. Try again.");
    	            System.out.println((char)27 + "[97m" + "");
    	            scan.next();
    	            continue;
    	        }
    	        task = scan.nextInt();
    	        if (task == 1) {
    	        	System.out.println((char)27 + "[96m" + "You have selected to view supplier information.");
    		        String p;
    	            p = "select * from supplier";
    	            try (ResultSet result = s.executeQuery(p);) {
    	                if (!result.next()) {
    	                    System.out.println("No results found. Going back to supplier menu...");
    	                    continue;
    	                }
                        System.out.print((char)27 + "[97m" + "");
                        System.out.printf("%-12s%-20s%-20s\n","Supplier_ID","Name","Address");
                        System.out.print((char)27 + "[96m" + "");
    	                do {
                            System.out.printf("%-12s%-20s%-15s\n",result.getString("supplier_id"),result.getString("sup_name"),result.getString("address"));
    	                } while (result.next());

    	            } catch (Exception e) {
    	                System.out.println("This is an excetion and this is unacceptable");
    	            }
    	        }

    	        else if (task == 2) {
    	        	System.out.println((char)27 + "[97m" + "Input the supplier_id you wish to view."); 
                    int sup_id = 0;
                    if (!scan.hasNextInt()) {
    	                System.out.print((char)27 + "[31m" + "Not an valid input. Going back to supplier menu...");
    	                System.out.println((char)27 + "[97m" + "");
    	                scan.next();
    	                continue;
    	            }
    	            sup_id = scan.nextInt();
    	            if (sup_id <= 0) {
                        System.out.print((char)27 + "[31m" + "Input not in range. Going back to supplier menu...");
                        System.out.println((char)27 + "[97m" + "");
                        continue;
                    }
                    stmt = con.prepareStatement("select * from manufactures where supplier_id = ?");
                    stmt.setInt(1, sup_id);  
                    try (ResultSet result1 = stmt.executeQuery();) {
                		if (!result1.next()) {
                    		System.out.println("No results found. Going back to supplier menu.");
                    		continue;
                		}
                		int marker = 1;
                		do {
                			System.out.println((char)27 + "[96m" + "Result number " + marker + ":");
                    		System.out.println((char)27 + "[97m" + "  Product ID => " + result1.getString("product_id"));
                    		System.out.println((char)27 + "[97m" + "  Offering Price => " + result1.getString("off_price"));
                    		marker++;
                		} while (result1.next());

            		} catch (Exception e) {
                		System.out.println("This is an excetion and this is unacceptable");
            		} 
    	        }

    	        else if (task == 3) {
    	        	System.out.println((char)27 + "[97m" + "Input the supplier_id you wish to view."); 
                    int sup_id = 0;
                    if (!scan.hasNextInt()) {
    	                System.out.print((char)27 + "[31m" + "Not an valid input. Going back to supplier menu...");
    	                System.out.println((char)27 + "[97m" + "");
    	                scan.next();
    	                continue;
    	            }
    	            sup_id = scan.nextInt();
    	            if (sup_id <= 0) {
                        System.out.print((char)27 + "[31m" + "Input not in range. Going back to supplier menu...");
                        System.out.println((char)27 + "[97m" + "");
                        continue;
                    }
                    stmt = con.prepareStatement("select * from ships s, shipment sh where sh.shipment_id = s.shipment_id and s.supplier_id = ?");
                    stmt.setInt(1, sup_id);  
                    try (ResultSet result1 = stmt.executeQuery();) {
                		if (!result1.next()) {
                    		System.out.println("No results found. Going back to supplier menu...");
                    		continue;
                		}
                		int marker = 1;
                		do {
                			System.out.println((char)27 + "[96m" + "Result number " + marker + ":");
                    		System.out.println((char)27 + "[97m" + "  Shipment ID => " + result1.getString("shipment_id"));
                    		System.out.println((char)27 + "[97m" + "  Date => " + result1.getString("DT"));
                    		marker++;
                		} while (result1.next());

            		} catch (Exception e) {
                		System.out.println("This is an excetion and this is unacceptable");
            		} 
    	        }

                else if (task == 4) {
                    int num_sup = num_Suppliers(con, s);
                    System.out.println((char)27 + "[96m" + "You have " + num_sup + " suppliers"); 
                }

    	        else if (task == 5) {
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

	public int num_Suppliers(Connection c, Statement st) {
		String p;
        p = "select * from supplier";
        int count = 0;
        try (ResultSet result = st.executeQuery(p);) {
            if (!result.next()) {
                System.out.println("No results found. Going back to supplier menu.");
                return 0;
            }
            do {
                ++count;
            } while (result.next());

        } catch (Exception e) {
            System.out.println("This is an excetion and this is unacceptable");
        }
        return count;
	}
}