package DBlogic;

import Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemFuncs {
    public Scanner in;
    public Connection conn;
    public ItemFuncs(Scanner in_, Connection conn_){
        this.in = in_;
        this.conn = conn_;
    }

    public ArrayList<Item> findItems(String gameName, int page, int per){
        ArrayList<Item> items = new ArrayList<Item>();
        try {
            CallableStatement func = conn.prepareCall("select * from getItemByName(?,?,?)");
            func.setString(1, gameName);
            func.setInt(2, page);
            func.setInt(3, per);
            ResultSet results = func.executeQuery();
            var count = 0;
            while (results.next()) {

                int id = results.getInt("itemid");
                String title = results.getString("title");
                String description = results.getString("description");
                String platform = results.getString("platform_name");
                double price = results.getDouble("price");
                int quantity = results.getInt("quantity");
                String category = results.getString("category_name");
                String shop = results.getString("shop_name");
                items.add(new Item(id, title, description, price, quantity, platform, category, shop));
                count++;
            }
            results.close();
            func.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public FullItemData viewItem (User me, Item it ) {
        try {
            var inCart = false;
            var isClient = true;
            CallableStatement cs = conn.prepareCall("select * from checkShop(?,?)");
            cs.setLong(1, it.getId());
            cs.setInt(2, me.getId());
            ResultSet results = cs.executeQuery();
            Shop shop = new Shop();
            FullItemData data = new FullItemData();
            while (results.next()) {
                long shopID = results.getLong("seller_id");
                String shopName = results.getString("name");
                double price = results.getDouble("price");
                inCart = results.getBoolean("inCart");
                isClient = results.getBoolean("isClient");
                double rating = results.getDouble("overall_score");
                shop.setShop(shopID, rating, shopName);
                data.id = results.getLong("currentitemid");
                data.category = results.getString("category_name");
                data.platform = results.getString("platform_name");
                data.price = price;
                data.description = results.getString("description");
                data.shop = shop;
                data.title = results.getString("title");
                data.quantity = results.getInt("quantity");
            }
            results.close();
            cs.close();

            return data;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addItemToCart(User me, Shop shop, int num){
        var res = false;
        try{
            CallableStatement cs = conn.prepareCall("{call addItemToCart(?,?,?,?)}");
            cs.setInt(1, me.getId());
            cs.setLong(2, shop.getId());
            cs.setInt(3, num);
            cs.registerOutParameter(4, Types.BOOLEAN);
            cs.execute();

            res = cs.getBoolean(4);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public long addNewItem(Shop myShop, String title, String description, int platformID, int categoryID, int quantity, double price){
        long res = -1;
        try {
            CallableStatement func = conn.prepareCall(
                    "{call createItem(?,?,?,?,?,?,?,?)}");
            func.setLong(1, myShop.getId());
            func.setString(2, title);
            func.setString(3, description);
            func.setInt(4, quantity);
            func.setInt(5, platformID);
            func.setInt(6, categoryID);
            func.setDouble(7, price);
            func.registerOutParameter(8, Types.INTEGER);
            func.execute();
            res = func.getLong(8);
            func.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    };
    public boolean updateItem(Item item, String title, String description, int categoryID, int platformID, int quantity, double price){
        var res = false;
        try {
            CallableStatement func = conn.prepareCall(
                    "{call updateItem(?,?,?,?,?,?,?,?)}");
            func.setLong(1, item.getId());
            func.setString(2, title);
            func.setString(3, description);
            func.setInt(4, quantity);
            func.setInt(5, platformID);
            func.setInt(6, categoryID);
            func.setDouble(7, price);
            func.registerOutParameter(8, Types.BOOLEAN);
            func.execute();
            res = func.getBoolean(8);
            func.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    };
    public boolean deleteItem(Item item ){
        boolean res = false;
        try {
            CallableStatement func = conn.prepareCall(
                    "{call hideItem(?,?,?)}");
            func.setLong(1, item.getId());
            func.setBoolean(2, true);
            func.registerOutParameter(3, Types.BOOLEAN);
            func.execute();
            res = func.getBoolean(3);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    };

    public boolean updateCartItem(long id, long quantity){
        var res = false;
        try {
            CallableStatement cs = conn.prepareCall("{call updateCartItem(?,?,?)}");
            cs.setLong(1, id);
            cs.setLong(2, quantity);
            cs.registerOutParameter(3, Types.BOOLEAN);
            cs.execute();
            res = cs.getBoolean(3);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public boolean  deleteCarItem(long id){
        var res = false;
        try {
            CallableStatement cs = conn.prepareCall("{call deleteItemFromCart(?)}");
            cs.setLong(1, id);
            cs.registerOutParameter(2, Types.BOOLEAN);
            cs.execute();
            res = cs.getBoolean(2);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }
}
