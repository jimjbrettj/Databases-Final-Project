import java.io.*;
import java.sql.*;
import java.util.*;
public class prodManager {
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
            PreparedStatement stmt4 = null;
            System.out.print("\033[H\033[2J");  
            System.out.flush();
    		while (!flag) {
    	        System.out.println((char)27 + "[92m" + "What would you like to do?");
    	        System.out.println((char)27 + "[97m" + "Press:");
    	        System.out.println((char)27 + "[97m" + "  -1 view all products");
    	        System.out.println((char)27 + "[97m" + "  -2 add a new product");
                System.out.println((char)27 + "[97m" + "  -3 delete a product");
                System.out.println((char)27 + "[97m" + "  -4 view the current number of products");
    	        System.out.println((char)27 + "[97m" + "  -5 to return to main menu");
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
                    int prod_id = num_Products(con, s) + 1;
                    String input = "";
                    scan.nextLine();
                    System.out.println((char)27 + "[97m" + "Input the new product's name: ");
                    input = scan.nextLine();
                    int batch = prod_id + 100;
                    if (input.length() > 19) {
                        System.out.println((char)27 + "[31m" + "Invalid name length. Going back to supplier menu...");
                        continue;
                    }
                    stmt = con.prepareStatement("insert into product (product_id, p_name, batch_id) values (?, ?, ?)");
                    stmt.setInt(1, prod_id); 
                    stmt.setString(2, input);
                    stmt.setInt(3, batch); 
                    System.out.println((char)27 + "[97m" + "Press 1 if the product is a brand or 2 if it is a commodity: ");
                    int tell = 0;
                    if (!scan.hasNextInt()) {
                        System.out.print((char)27 + "[31m" + "Not an valid input. Going back to menu...");
                        System.out.println((char)27 + "[97m" + "");
                        continue;
                    }
                    tell = scan.nextInt();
                    if (tell < 1 || tell > 2){
                        System.out.print((char)27 + "[31m" + "Not an valid input. Going back to menu...");
                        System.out.println((char)27 + "[97m" + "");
                        continue;
                    }
                    if (tell == 1) {
                        stmt2 = con.prepareStatement("insert into brand (product_id, brand_name) values (?, ?)");
                        stmt2.setInt(1, prod_id); 
                        stmt2.setString(2, input);
                        try {
                            stmt.executeUpdate();
                            stmt2.executeUpdate();
                            System.out.println((char)27 + "[96m" + "Product has been added.");
                        } catch (Exception e) {
                            System.out.println("Not able to add product. Going back to menu...");
                        } 
                    }
                    if (tell == 2) {
                        stmt2 = con.prepareStatement("insert into commodity (product_id, com_name) values (?, ?)");
                        stmt2.setInt(1, prod_id); 
                        stmt2.setString(2, input);
                        try {
                            stmt.executeUpdate();
                            stmt2.executeUpdate();
                            System.out.println((char)27 + "[96m" + "Product has been added.");
                        } catch (Exception e) {
                            System.out.println("Not able to add product. Going back to menu...");
                        } 
                    }
    	        }
                else if (task == 3) {
                    System.out.println((char)27 + "[97m" + "Input the id for the product you wish to delete");
                    int prod_id = 0;
                    if (!scan.hasNextInt() ) {
                        System.out.print((char)27 + "[31m" + "Not an valid input. Going back to menu...");
                        System.out.println((char)27 + "[97m" + "");
                        continue;
                    }
                    prod_id = scan.nextInt();
                    int max = num_Products(con, s);
                    if (prod_id <= max){
                        System.out.print((char)27 + "[31m" + "Input not in range. Going back to menu...");
                        System.out.println((char)27 + "[97m" + "");
                        continue;
                    }
                    stmt = con.prepareStatement("select * from product p, manufactures m where p.product_id = m.product_id and p.product_id = ?");
                    stmt.setInt(1, prod_id);
                    stmt2 = con.prepareStatement("select * from product p, contains c where p.product_id = c.product_id and p.product_id = ?");
                    stmt2.setInt(1, prod_id);
                    try (ResultSet result = stmt.executeQuery();) {
                        if (!result.next()) {
                            ResultSet result2 = stmt2.executeQuery();
                            if (!result2.next()) {
                                System.out.println((char)27 + "[97m" + "You can delete this product");
                                int type = prod_Type(con, s, prod_id);
                                if (type < 1 || type > 2){
                                    System.out.print((char)27 + "[31m" + "Something went wrong. Going back to menu...");
                                    System.out.println((char)27 + "[97m" + "");
                                    continue;
                                }
                                stmt3 = con.prepareStatement("DELETE FROM product WHERE product_id = ?");
                                stmt3.setInt(1, prod_id);
                                if (type == 1) {
                                    stmt4 = con.prepareStatement("DELETE FROM brand WHERE product_id = ?");
                                    stmt4.setInt(1, prod_id);
                                    try {
                                        stmt4.executeUpdate();
                                        stmt3.executeUpdate();
                                        System.out.println((char)27 + "[96m" + "Product has been deleted.");
                                    } catch (Exception e) {
                                        System.out.println("Not able to delete product. Going back to menu...");
                                    }
                                }
                                else if (type == 2) {
                                    stmt4 = con.prepareStatement("DELETE FROM commodity WHERE product_id = ?");
                                    stmt4.setInt(1, prod_id);
                                    try {
                                        stmt4.executeUpdate();
                                        stmt3.executeUpdate();
                                        System.out.println((char)27 + "[96m" + "Product has been deleted.");
                                    } catch (Exception e) {
                                        System.out.println("Not able to delete product. Going back to menu...");
                                    }
                                }
                            }
                            else {
                                System.out.println((char)27 + "[31m" + "Not authorized to delete this supplier. Going back to supplier menu...");
                                continue;
                            }                           
                        }
                        else {
                            System.out.println((char)27 + "[31m" + "Not authorized to delete this product. Going back to product menu...");
                            continue;
                        }

                    } catch (Exception e) {
                        System.out.println("This is an excetion and this is unacceptable");
                    }  
                }

                else if (task == 4) {
                    int num_prod = num_Products(con, s);
                    System.out.println((char)27 + "[96m" + "You have " + num_prod + " products");
                }

    	        else if (task == 5) {
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

	public int num_Products(Connection c, Statement st) {
		String p;
        p = "select * from product";
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

    public int prod_Type(Connection c, Statement st, int id) throws SQLException, IOException, java.lang.ClassNotFoundException {
        int prod_id = id;
        int type = 0;
        PreparedStatement stmt = null;
        stmt = c.prepareStatement("select * from product p, commodity c where c.product_id = p.product_id and p.product_id = ?");
        stmt.setInt(1, prod_id);
        try (ResultSet result3 = stmt.executeQuery();) {
            if (!result3.next()) {
                type = 1;
            }
            else {
                type = 2;
            }
        } catch (Exception e) {
            System.out.println("This is an excetion and this is unacceptable");
        } 
        return type;
    }
}