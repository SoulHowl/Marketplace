package ControllersViews;

import DBlogic.CartFuncs;
import Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuVC extends AbstractVC {

    public MenuVC(Scanner in_, Connection conn_){

        this.in = in_;
        this.conn = conn_;
    }

    public String loop() throws SQLException {
        var page = 1;
        var per = 3;
        while(true) {
            System.out.print("U'r in menu!");
            CartFuncs cartFuncs = new CartFuncs(in, conn);
            ArrayList<Item> items = new ArrayList<Item>();

            System.out.print("\nCommand list:\n1 - log out\n2 - check profile/login" +
                    "\n" + "6- balance\n4 - visit order history\n5 - visit cart" +
                    (me.isAdmin() ? "\n999 - admin console " : "") + "\n9 - back");
            var escape_from_main = false;
            int num = in.nextInt();
            switch (num) {
                case 1:
                    // log out
                    return "log_out";
                case 2:
                    System.out.println("Entered user's profile");
                    while (true) {
                        if (me.isAuthorized()) {
                            System.out.print(" Your name: " + me.getNick() + "\n your email: " + me.getEmail() + "\n id " + me.getId() +
                                    "\nCommand list:\n 0 - back\n");
                            var escape9 = true;
                            int num9 = in.nextInt();
                            if (num9 != 0) {
                                escape9 = false;
                            }
                            if (escape9) break;
                        } else {
                            System.out.print("Log in first!");
                            escape_from_main = true;

                        }
                    }
                    break;
                case 3:
                    if(me.isSeller()){
                        return "shop";
                }
                    break;
                case 4:
                    System.out.println("Entered order history");
                    ArrayList<Order> orders = cartFuncs.getHistory(me);

                    for(Order order: orders){
                        order.print();
                    }
                    while (true) {
                        System.out.print("\nCommand list:\n 0 - back\n1 - check current order");
                        var escape_from_order_history = false;
                        num = in.nextInt();
                        switch (num) {
                            case 0:
                                escape_from_order_history = true;
                                break;
                            case 1:
                                System.out.print("\nSelect order by its number\n");
                                long num_ = in.nextLong();

                                ArrayList<OrderedItem> orderedItems = cartFuncs.getPurchasedItem(num_);

                                for (OrderedItem odIt: orderedItems){
                                    odIt.print();
                                }
                                break;
                        }
                        if(escape_from_order_history)break;
                    }
                    break;
                case 5:
                    return "cart";
                case 6:
                    System.out.println("Balance" + me.getBalance());
                    System.out.println("put some money");
                    var num_ = in.nextDouble();
                    var newBlance = cartFuncs.updateBalance(me, num_);
                    if (newBlance != -1){
                        me.setBalance(newBlance);
                        System.out.println("Success");
                    }
                    else{
                        System.out.println("Error");
                    }

                case 999:
                    System.out.println("Entered admin console");
//                    while (true){
//                        System.out.print("\nCommand list:\n 0 - find user\n1 - give seller role" +
//                                "\n2 - delete user from sellers\n3 - back");
//                        var escape_from_admin_console = false;
//                        num = in.nextInt();
//                        switch (num){
//                            case 0:
//                                System.out.print("\ninsert nick or email\n");
//                                var input = in.nextLine();
//                                CallableStatement cs = conn.prepareCall("select * from findUser(?)");
//                                cs.setString(1, input);
//                                ResultSet results = cs.executeQuery();
//                                while (results.next()) {
//                                    System.out.print("Name: " + results.getString("email")
//                                            + "\n Email: " + results.getString("email") +
//                                            "Balance: " + results.getDouble("balance")
//                                            + "Id: " + results.getLong("id") +"\n");
//                                }
//                                results.close();
//                                cs.close();
//                                break;
//                            case 1:
//                                System.out.print("\ninsert id of user \n");
//                                var id = in.nextLong();
//                                cs = conn.prepareCall("call updateUserRoleToSeller(?, ?)");
//                                cs.setLong(1, id);
//                                cs.registerOutParameter(2, Types.BOOLEAN);
//                                cs.execute();
//                                if(cs.getBoolean(2)){
//                                    System.out.print("\nSuccess\n");
//                                }else{
//                                    System.out.print("\nError\n");
//                                }
//                                break;
//                            case 2:
//                                System.out.print("\ninsert id of seller to kick out\n");
//                                id = in.nextLong();
//                                cs = conn.prepareCall("call deleteUserFromSellers(?,?)");
//                                cs.setLong(1, id);
//                                cs.registerOutParameter(2, Types.BOOLEAN);
//                                cs.execute();
//                                if(cs.getBoolean(2)){
//                                    System.out.print("\nSuccess\n");
//                                }else{
//                                    System.out.print("\nError\n");
//                                }
//                                break;
//                            case 3:
//                                escape_from_admin_console = true;
//                                break;
//                        }
//                        if(escape_from_admin_console) break;
//                    }
                    break;
                case 9:
                    escape_from_main = true;
                    break;
                default:
                    System.out.print("Wrong input");
                    escape_from_main = true;
            }
            if (escape_from_main) break;
        }
        return "main";
    }
}
