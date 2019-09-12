import java.io.*;
import java.sql.*;
import java.util.*;
public class recall {
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
		        System.out.println((char)27 + "[97m" + "  -1 view product information for recall");
		        System.out.println((char)27 + "[97m" + "  -2 place a recall");
		        System.out.println((char)27 + "[97m" + "  -3 return to manager menu");
		        if (!scan.hasNextInt()) {
		            System.out.print((char)27 + "[31m" + "Not an integer. Try again.");
		            System.out.println((char)27 + "[97m" + "");
		            scan.next();
		            continue;
		        }
		        task = scan.nextInt();
		        if (task == 1) {
	            	System.out.println((char)27 + "[96m" + "You have selected to view product information.");
			        String p;
		            p = "select * from product";
		            try (ResultSet result4 = s.executeQuery(p);) {
		                if (!result4.next()) {
		                    System.out.println("No results found. Going back to menu.");
		                    continue;
		                }
	                    System.out.print((char)27 + "[97m" + "");
	                    System.out.printf("%-12s%-20s%-15s\n","Product_ID","Name","Batch_ID");
	                    System.out.print((char)27 + "[96m" + "");
		                do {
	                        System.out.printf("%-12s%-20s%-15s\n",result4.getString("product_id"),result4.getString("p_name"),result4.getString("batch_id"));
		                } while (result4.next());

		            } catch (Exception e) {
	                    System.out.println((char)27 + "[31m" + "This is an excetion and this is unacceptable");
		            }
	            }

		        else if (task == 2) {
		            System.out.println((char)27 + "[97m" + "Input the product ID of the item you need to recall");
		            int prod_id = 0;
			        if (!scan.hasNextInt()) {
			            System.out.print((char)27 + "[31m" + "Not an valid input. Going back to menu...");
			            System.out.println((char)27 + "[97m" + "");
			            scan.next();
			            continue;
			        }
			        prod_id = scan.nextInt();
			        if (prod_id <= 0) {
	                    System.out.print((char)27 + "[31m" + "Input not in range. Going back to supplier menu...");
	                    System.out.println((char)27 + "[97m" + "");
	                    continue;
	                }
	                stmt = con.prepareStatement("select * from product natural join contains natural join shipment natural join " + 
	                	"ships natural join supplier where product_id = ?");
	                stmt.setInt(1, prod_id);
	                try (ResultSet result = stmt.executeQuery();) {
	                    if (!result.next()) {
	                        System.out.println("No results found for this product. Going back to product menu.");
	                        continue;
	                    }
	                    int marker = 1;
	                    do {
	                        System.out.println((char)27 + "[96m" + "Result number " + marker + ":");
	                        System.out.println((char)27 + "[97m" + "  Product name => " + result.getString("p_name"));
	                        System.out.println((char)27 + "[97m" + "  Supplier ID => " + result.getString("supplier_id"));
	                        System.out.println((char)27 + "[97m" + "  Supplier Name => " + result.getString("sup_name"));
	                        System.out.println((char)27 + "[97m" + "  Supplier Address => " + result.getString("address"));
	                        marker++;
	                    } while (result.next());
	                    System.out.println((char)27 + "[96m" + "We will notify these suppliers right away and order the recall.");

	                } catch (Exception e) {
	                    System.out.println("This is an excetion and this is unacceptable");
	                } 
		        }

		        else if (task == 3) {
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