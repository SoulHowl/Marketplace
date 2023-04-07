package DBlogic;

import Models.Cart;
import Models.Order;
import Models.OrderedItem;
import Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CartFuncs {
    public Scanner in;
    public Connection conn;
    public CartFuncs(Scanner in_, Connection conn_){
        this.in = in_;
        this.conn = conn_;
    }

    public Cart showCart(User me) {
        ArrayList<OrderedItem> cartItems = new ArrayList<OrderedItem>();
        Cart cart = new Cart();
        try {
            CallableStatement cs = conn.prepareCall("select * from showCart(?)");
            cs.setLong(1, me.getId());
            ResultSet results = cs.executeQuery();
            var count = 0;
            long orderID = -1;
            double sum = 0;
            while (results.next()) {
                orderID = results.getLong("orderid");
                sum = results.getDouble("ordersum");
                long id = results.getLong("odereditemid");
                String name = results.getString("title");
                double price = results.getDouble("price");
                long quantity = results.getLong("quantity");
                String shopName = results.getString("shop_name");
                cartItems.add(new OrderedItem(id, name, price, quantity, "", shopName, ""));
                cart.sum = sum;
                cart.id = orderID;
            }
            cart.cartItems = cartItems;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return cart;
    }

    public boolean makeOrder(User me){
        var res = false;
        try{
            CallableStatement cs = conn.prepareCall("{call makeOrder(?,?)}");
            cs.setLong(1, me.getId());
            cs.registerOutParameter(2, Types.BOOLEAN);
            cs.execute();
            res = cs.getBoolean(2);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<Order> getHistory(User me){
        ArrayList<Order> orders = new ArrayList<Order>();
        try {
            CallableStatement cs = conn.prepareCall("select * from showOrdersForUser(?)");
            cs.setLong(1, me.getId());
            ResultSet results = cs.executeQuery();

            while (results.next()) {
                orders.add(new Order(results.getLong("orderid"),
                        results.getDouble("total_sum"),
                        results.getString("date_purch")));
            }
            results.close();
            cs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return orders;
    }

    public ArrayList<OrderedItem> getPurchasedItem(long num){
        ArrayList<OrderedItem> orderedItems = new ArrayList<OrderedItem>();
        try {
            CallableStatement cs = conn.prepareCall("select * from showOrderStuffForUser(?)");
            cs.setLong(1, num);
            ResultSet results = cs.executeQuery();
            var count = 0;
            while (results.next()) {
                long id = results.getLong("odereditemid");
                String name = results.getString("title");
                double price = results.getDouble("price");
                long quantity = results.getLong("quantity");
                String status = results.getString("item_status");
                String value = results.getString("val");

                String shopName = results.getString("shop_name");
                orderedItems.add(new OrderedItem(id, name, price, quantity, status, shopName, value));

            }
            for (OrderedItem odIt : orderedItems) {
                odIt.print();
            }
            results.close();
            cs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return orderedItems;
    }

    public double updateBalance(User me, double val){
        double sum = -1;
        try{
            CallableStatement cs = conn.prepareCall("{call updateBalance(?,?,?)}");
            cs.setInt(1, me.getId());
            cs.setDouble(2, val);
            cs.registerOutParameter(3,Types.FLOAT);

            cs.execute();
            sum = cs.getDouble(3);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return sum;
    }
}
