import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class placeOrder {
	public void main(Connection c, Statement st) throws SQLException, IOException, java.lang.ClassNotFoundException {
        try {
    		Scanner scan = new Scanner(System.in);
    		Connection con = c;
    		Statement s = st;
            boolean flag = false;
            int task = 0;
            PreparedStatement stmt = null;
            PreparedStatement stmt2 = null;
            PreparedStatement stmt3 = null;
            System.out.print("\033[H\033[2J");  
            System.out.flush();
    		while (!flag) {
    	        System.out.println((char)27 + "[92m" + "What would you like to do?");
    	        System.out.println((char)27 + "[97m" + "Press:");
    	        System.out.println((char)27 + "[97m" + "  -1 view all products");
    	        System.out.println((char)27 + "[97m" + "  -2 view all suppliers");
                System.out.println((char)27 + "[97m" + "  -3 view all shipments");
                System.out.println((char)27 + "[97m" + "  -4 create a new shipment");
                System.out.println((char)27 + "[97m" + "  -5 delete a shipment");
                System.out.println((char)27 + "[97m" + "  -6 view number of shipments");
    	        System.out.println((char)27 + "[97m" + "  -7 to return to main menu");
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
                    System.out.println((char)27 + "[96m" + "You have selected to view supplier information.");
                    String p;
                    p = "select * from supplier";
                    try (ResultSet result = s.executeQuery(p);) {
                        if (!result.next()) {
                            System.out.println("No results found. Going back to menu...");
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

                else if (task == 3) { 
                    System.out.println((char)27 + "[96m" + "You have selected to view shipment information.");
                    String p;
                    p = "select * from shipment";
                    try (ResultSet result = s.executeQuery(p);) {
                        if (!result.next()) {
                            System.out.println("No results found. Going back to menu...");
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

    	        else if (task == 4) {
    	        	System.out.println((char)27 + "[97m" + "Input the product ID for the product you wish to order");
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
                    stmt = con.prepareStatement("select * from product p, manufactures m where p.product_id = m.product_id and p.product_id = ?");
                    stmt.setInt(1, prod_id);
                	try (ResultSet result = stmt.executeQuery();) {
                        if (!result.next()) {
                            System.out.println("No results found. Going back to product menu.");
                            continue;
                        }
                        int marker = 1;
                        do {
                        	System.out.println((char)27 + "[96m" + "Result number " + marker + ":");
                            System.out.println((char)27 + "[97m" + "  Product name => " + result.getString("p_name"));
                            System.out.println((char)27 + "[97m" + "  Supplier ID => " + result.getString("supplier_id"));
                            System.out.println((char)27 + "[97m" + "  Offering Price => $" + result.getString("off_price"));
                            marker++;
                        } while (result.next());
                    } catch (Exception e) {
                        System.out.println("This is an excetion and this is unacceptable");
                    }  
                    System.out.println((char)27 + "[97m" + "Input the supplier ID for the supplier you wish to use");
                    int sup_id = 0;
                    if (!scan.hasNextInt()) {
                        System.out.print((char)27 + "[31m" + "Not an valid input. Going back to menu...");
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
                    Date dt = new Date();
                    DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
    				String date = df.format(dt);
           			int ship_id = num_Shipments(con, s) + 1;
           			int buyer_id = 241;
                    int price = 0;
                    System.out.println((char)27 + "[97m" + "Input the PPU for this product");
                    if (!scan.hasNextInt()) {
                        System.out.print((char)27 + "[31m" + "Not an valid input. Going back to menu...");
                        System.out.println((char)27 + "[97m" + "");
                        scan.next();
                        continue;
                    }
                    price = scan.nextInt();
                    int quantity = 0;
                    System.out.println((char)27 + "[97m" + "Input how many you would like to order");
                    if (!scan.hasNextInt()) {
                        System.out.print((char)27 + "[31m" + "Not an valid input. Going back to menu...");
                        System.out.println((char)27 + "[97m" + "");
                        scan.next();
                        continue;
                    }
                    quantity = scan.nextInt();
                    if (quantity <= 0 || price <= 0) {
                        System.out.print((char)27 + "[31m" + "Input not in range. Going back to supplier menu...");
                        System.out.println((char)27 + "[97m" + "");
                        continue;
                    }
                    stmt = con.prepareStatement("insert into shipment (shipment_id, quantity, DT) values (?, ?, ?)");
                    stmt.setInt(1, ship_id); 
                    stmt.setInt(2, quantity);
                    stmt.setString(3, date); 
                    stmt2 = con.prepareStatement("insert into ships (shipment_id, supplier_id, buyer_id) values (?, ?, ?)");
                    stmt2.setInt(1, ship_id); 
                    stmt2.setInt(2, sup_id);
                    stmt2.setInt(3, buyer_id);
                    stmt3 = con.prepareStatement("insert into contains (product_id, shipment_id, ppu) values (?, ?, ?)");
                    stmt3.setInt(1, prod_id); 
                    stmt3.setInt(2, ship_id);
                    stmt3.setInt(3, price);
                    try {
                        stmt.executeUpdate();
                        stmt2.executeUpdate();
                        stmt3.executeUpdate();
                        System.out.println((char)27 + "[96m" + "Shipment has been added.");
                    } catch (Exception e) {
                        System.out.println("Not able to add shipment. Going back to menu...");
                    } 
                }

                else if (task == 5) { 
                    System.out.println((char)27 + "[97m" + "Input the shipment ID for the shipment you wish to delete.");
                    int ship_id = 0;
                    if (!scan.hasNextInt()) {
                        System.out.print((char)27 + "[31m" + "Not an valid input. Going back to menu...");
                        System.out.println((char)27 + "[97m" + "");
                        scan.next();
                        continue;
                    }
                    ship_id = scan.nextInt();
                    if (ship_id <= 30) {
                        System.out.print((char)27 + "[31m" + "Not authorized to delete this shipment. Going back to menu...");
                        System.out.println((char)27 + "[97m" + "");
                        continue;
                    }
                    stmt = con.prepareStatement("delete from shipment where shipment_id = ?");
                    stmt.setInt(1, ship_id);
                    stmt2 = con.prepareStatement("delete from ships where shipment_id = ?");
                    stmt2.setInt(1, ship_id);
                    stmt3 = con.prepareStatement("delete from contains where shipment_id = ?");
                    stmt3.setInt(1, ship_id);
                    try {
                        stmt3.executeUpdate();
                        stmt2.executeUpdate();
                        stmt.executeUpdate();
                        System.out.println((char)27 + "[96m" + "Shipment has been deleted.");
                    } catch (Exception e) {
                        System.out.println("Not able to delete shipment. Going back to menu...");
                    }    
                }

                else if (task == 6) { 
                    int ship_num = num_Shipments(con, s);
                    System.out.println((char)27 + "[96m" + "You have " + ship_num + " shipments");
                }

                else if (task == 7) {
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

	public int num_Shipments(Connection c, Statement st) {
		String p;
        p = "select * from shipment";
        int count = 0;
        try (ResultSet result = st.executeQuery(p);) {
            if (!result.next()) {
                System.out.println("No results found. Going back to menu.");
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