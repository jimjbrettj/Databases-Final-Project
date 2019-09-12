import java.io.*;
import java.sql.*;
import java.util.*;
public class product {
	public void main(Connection c, Statement st) throws SQLException, IOException, java.lang.ClassNotFoundException {
        try {
    		Connection con = c;
    		Statement s = st;
    		Scanner scan = new Scanner(System.in);
    		boolean flag = false;
    		int num = 0;
            PreparedStatement stmt = null; 
            System.out.print("\033[H\033[2J");  
            System.out.flush();      
            while (!flag) {
                System.out.println((char)27 + "[92m" + "What would you like to do?");
    	        System.out.println((char)27 + "[97m" + "Press:");
    	        System.out.println((char)27 + "[97m" + "  -1 see supplier info for a given product");
    	        System.out.println((char)27 + "[97m" + "  -2 see what components make up the product");
    	        System.out.println((char)27 + "[97m" + "  -3 see if a product is a brand or a comodity");
    	        System.out.println((char)27 + "[97m" + "  -4 view all products");
                System.out.println((char)27 + "[97m" + "  -5 find the price purchase price of a product");
    	        System.out.println((char)27 + "[97m" + "  -6 go back to manager menu");
    	        if (!scan.hasNextInt()) {
                    System.out.print((char)27 + "[31m" + "Not an valid input. Going back to product menu...");
                    System.out.println((char)27 + "[97m" + "");
                    scan.next();
                    continue;
                }
                num = scan.nextInt();
                if (num == 1) {
                    System.out.println((char)27 + "[97m" + "Input the product_id you want to find the supplier info for."); 
                    int prod_id = 0;
                    if (!scan.hasNextInt()) {
    	                System.out.print((char)27 + "[31m" + "Not an valid input. Going back to product menu...");
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
                    stmt = con.prepareStatement("select s.supplier_id, s.sup_name, s.address from manufactures m, supplier s where m.product_id = ? and s.supplier_id = m.supplier_id");
                    stmt.setInt(1, prod_id);  
            		try (ResultSet result1 = stmt.executeQuery();) {
                		if (!result1.next()) {
                    		System.out.println("No results found. Going back to product menu.");
                    		continue;
                		}
                		int marker = 1;
                		do {
                			System.out.println((char)27 + "[96m" + "Result number " + marker + ":");
                    		System.out.println((char)27 + "[97m" + "  Supplier ID => " + result1.getString("supplier_id"));
                    		System.out.println((char)27 + "[97m" + "  Supplier name => " + result1.getString("sup_name"));
                    		System.out.println((char)27 + "[97m" + "  Supplier address => " + result1.getString("address"));
                    		marker++;
                		} while (result1.next());

            		} catch (Exception e) {
                		System.out.println("This is an excetion and this is unacceptable");
            		} 			
                }

                else if (num == 2) {
                    System.out.println((char)27 + "[97m" + "Input the product_id of the item you wish to find the component information of.");
                    int prod_id = 0;
                    if (!scan.hasNextInt()) {
    	                System.out.print((char)27 + "[31m" + "Not an valid input. Going back to product menu...");
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
                    stmt = con.prepareStatement("select * from ( select product_id, component_id from product natural join components where product_id = ?)" +
                                                    "outer left join product on component_id = product.product_id");
                    stmt.setInt(1, prod_id);
                    try (ResultSet result2 = stmt.executeQuery();) {
                		if (!result2.next()) {
                    		System.out.println("This product contains no components.");
                    		continue;
                		}
                		System.out.println((char)27 + "[96m" + "The component that make up this product are:");
                        int marker = 1;
                		do {
                            System.out.println((char)27 + "[96m" + "Result number " + marker + ":");
                    		System.out.println((char)27 + "[97m" + "Component ID => " + result2.getString("component_id"));
                            System.out.println((char)27 + "[97m" + "Name => " + result2.getString("p_name"));
                            marker++;
                		} while (result2.next());

            		} catch (Exception e) {
                        System.out.println((char)27 + "[31m" + "This is an excetion and this is unacceptable");
            		} 		
                }

                else if (num == 3) {
                    System.out.println((char)27 + "[97m" + "Input the product id to see if its a brand or a comodity.");
                    int prod_id = 0;
                    if (!scan.hasNextInt()) {
    	                System.out.print((char)27 + "[31m" + "Not an valid input. Going back to product menu...");
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
                    stmt = con.prepareStatement("select * from product p, commodity c where c.product_id = p.product_id and p.product_id = ?");
                    stmt.setInt(1, prod_id);
                    try (ResultSet result3 = stmt.executeQuery();) {
                		if (!result3.next()) {
                    		System.out.println((char)27 + "[96m" + "This product is a brand");
                		}
                		else {
                			System.out.println((char)27 + "[96m" + "This product is a commodity");
                		}
            		} catch (Exception e) {
                		System.out.println("This is an excetion and this is unacceptable");
            		} 
                }

                else if (num == 4) {
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

                else if (num == 5) {
                    System.out.println((char)27 + "[97m" + "Input the product id you wish to find the price of");
                    int prod_id = 0;
                    if (!scan.hasNextInt()) {
                        System.out.print((char)27 + "[31m" + "Not an valid input. Going back to product menu...");
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
                    stmt = con.prepareStatement("select p_name, PPU, shipment_id from product p, contains c where c.product_id = p.product_id and p.product_id = ?");
                    stmt.setInt(1, prod_id);
                    try (ResultSet result5 = stmt.executeQuery();) {
                        if (!result5.next()) {
                            System.out.println("No results found. Going back to product menu.");
                            continue;
                        }
                        int marker = 1;
                        do {
                            System.out.println((char)27 + "[96m" + "Shipment number " + marker + ":");
                            System.out.println((char)27 + "[97m" + "  Product name => " + result5.getString("p_name"));
                            System.out.println((char)27 + "[97m" + "  Price per unit => $" + result5.getString("PPU"));
                            System.out.println((char)27 + "[97m" + "  Shipment ID => " + result5.getString("shipment_id"));
                            marker++;
                        } while (result5.next());

                    } catch (Exception e) {
                        System.out.println("This is an excetion and this is unacceptable");
                    }     
                }

                else if (num == 6) {
                	System.out.println((char)27 + "[96m" + "Taking user back to manager menu");
                    scan.nextLine();
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