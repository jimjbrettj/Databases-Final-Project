import java.io.*;
import java.sql.*;
import java.util.*;
public class supChainManager {
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
    	        System.out.println((char)27 + "[97m" + "  -2 add a new supplier");
                System.out.println((char)27 + "[97m" + "  -3 update a suppliers name");
                System.out.println((char)27 + "[97m" + "  -4 update a suppliers address");
                System.out.println((char)27 + "[97m" + "  -5 view the current number of suppliers");
                System.out.println((char)27 + "[97m" + "  -6 delete a supplier");
    	        System.out.println((char)27 + "[97m" + "  -7 to return to main menu");
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
                    int sup_id = num_Suppliers(con, s) + 1;
                    System.out.println((char)27 + "[97m" + "Sup_ id =  " + sup_id);
                    String []input = new String[2];
                    scan.nextLine();
                    System.out.println((char)27 + "[97m" + "Input the supplier name: ");
                    input[0] = scan.nextLine();
                    System.out.println((char)27 + "[97m" + "Input the supplier address: ");
                    input[1] = scan.nextLine();
                    String subString1 = input[0];
                    if (subString1.length() > 18) {
                        System.out.println((char)27 + "[31m" + "Invalid name length. Going back to supplier menu...");
                    	continue;
                    }
                    String subString2 = input[1];
                    if (subString2.length() > 28) {
                        System.out.println((char)27 + "[31m" + "Invalid address. Going back to supplier menu...");
                    	continue;
                    }
                    stmt = con.prepareStatement("insert into supplier (supplier_id, sup_name, address) values (?, ?, ?)");
                    stmt.setInt(1, sup_id); 
                    stmt.setString(2, subString1);
                    stmt.setString(3, subString2); 
                    try {
                        stmt.executeUpdate();
                		System.out.println((char)27 + "[96m" + "Supplier has been added.");
            		} catch (Exception e) {
                		System.out.println("Not able to add supplier. Going back to supplier menu...");
            		} 
    	        }

                else if (task == 3) {
                    System.out.println((char)27 + "[97m" + "Input the supplier_id of the supplier whos name you wish to update");
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
                    scan.nextLine(); 
                    System.out.println((char)27 + "[97m" + "Input the new name for the supplier");
                    String subString1 = scan.nextLine();
                    if (subString1.length() > 18) {
                        System.out.println((char)27 + "[31m" + "Invalid name length. Going back to supplier menu...");
                        continue;
                    }
                    stmt = con.prepareStatement("UPDATE supplier SET sup_name = ? WHERE supplier_id = ?");
                    stmt.setString(1, subString1);
                    stmt.setInt(2, sup_id);
                    System.out.println((char)27 + "[97m" + "String is = " + subString1);
                    System.out.println((char)27 + "[97m" + "Id is = " + sup_id);
                    try {
                        stmt.executeUpdate();
                        System.out.println((char)27 + "[96m" + "Supplier name has been updated.");
                    } catch (Exception e) {
                        System.out.println("Not able to change supplier name. Going back to supplier menu...");
                    } 
                }

                else if (task == 4) {
                    System.out.println((char)27 + "[97m" + "Input the supplier_id of the supplier whos address you wish to update"); 
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
                    scan.nextLine(); 
                    System.out.println((char)27 + "[97m" + "Input the new address for the supplier");
                    String subString1 = scan.nextLine();
                    if (subString1.length() > 28) {
                        System.out.println((char)27 + "[31m" + "Invalid name length. Going back to supplier menu...");
                        continue;
                    }
                    stmt = con.prepareStatement("UPDATE supplier SET address = ? WHERE supplier_id = ?");
                    stmt.setString(1, subString1);
                    stmt.setInt(2, sup_id);
                    System.out.println((char)27 + "[97m" + "String is = " + subString1);
                    System.out.println((char)27 + "[97m" + "Id is = " + sup_id);
                    try {
                        stmt.executeUpdate();
                        System.out.println((char)27 + "[96m" + "Supplier address has been updated.");
                    } catch (Exception e) {
                        System.out.println("Not able to change supplier address. Going back to supplier menu...");
                    }
                }


                else if (task == 5) {
                    int num_sup = num_Suppliers(con, s);
                    System.out.println((char)27 + "[96m" + "You have " + num_sup + " suppliers"); 
                }

                else if (task == 6) {
                    System.out.println((char)27 + "[97m" + "Input the id for the supplier you wish to delete");
                    int sup_id = 0;
                    if (!scan.hasNextInt()) {
                        System.out.print((char)27 + "[31m" + "Not an valid input. Going back to supplier menu...");
                        System.out.println((char)27 + "[97m" + "");
                        scan.next();
                        continue;
                    }
                    sup_id = scan.nextInt();
                    int max = num_Suppliers(con, s);
                    if (sup_id <= max){
                        System.out.print((char)27 + "[31m" + "Not in range. Going back to supplier menu...");
                        System.out.println((char)27 + "[97m" + "");
                        continue;
                    }
                    stmt = con.prepareStatement("select * from supplier s, ships sh where s.supplier_id = sh.supplier_id and s.supplier_id = ?");
                    stmt.setInt(1, sup_id);
                    try (ResultSet result = stmt.executeQuery();) {
                        if (!result.next()) {
                            System.out.println("This supplier can be deleted");
                            stmt = con.prepareStatement("DELETE FROM supplier WHERE supplier_id = ?");
                            stmt.setInt(1, sup_id);
                            try {
                                stmt.executeUpdate();
                                System.out.println((char)27 + "[96m" + "Supplier has been deleted.");
                            } catch (Exception e) {
                                System.out.println("Not able to delete supplier. Going back to supplier menu...");
                            }
                        }
                        else {
                            System.out.println((char)27 + "[31m" + "Not authorized to delete this supplier. Going back to supplier menu...");
                            continue;
                        }

                    } catch (Exception e) {
                        System.out.println("This is an excetion and this is unacceptable");
                    }

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

	public int num_Suppliers(Connection c, Statement st) {
		String p;
        p = "select * from supplier";
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