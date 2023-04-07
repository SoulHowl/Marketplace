package DBlogic;

import Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ShopFuncs {
    public Scanner in;
    public Connection conn;
    public ShopFuncs(Scanner in_, Connection conn_){
        this.in = in_;
        this.conn = conn_;
    }

    public Shop setRating(User me, Shop shop, int rate){
        try{
            CallableStatement cs = conn.prepareCall("{call setRating(?,?,?,?)}");
            cs.setInt(1, me.getId());
            cs.setLong(2, shop.getId());
            cs.setInt(3, rate);
            cs.registerOutParameter(4, Types.FLOAT);
            cs.execute();
            shop.setShop(shop.getId(), cs.getDouble(4), shop.getName());
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return shop;
    }

    public ArrayList<Item> getShopItems(Shop myShop){
        ArrayList<Item> myItems = new ArrayList<Item>();
        try {

            CallableStatement func = conn.prepareCall("select * from getShopItems(?)");
            func.setLong(1, myShop.getId());
            //func.setInt(2, page);
            ResultSet results = func.executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                String title = results.getString("title");
                String description = results.getString("description");
                String platform = results.getString("platform_name");
                double price = results.getDouble("price");
                int quantity = results.getInt("quantity");
                String category = results.getString("category_name");
                String shop = results.getString("shop_name");
                myItems.add(new Item(id, title, description, price, quantity, platform, category, shop));
            }
            results.close();
            func.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return myItems;
    }

    public Shop getShopInfo(User me){
        Shop myShop = new Shop();
        try{
            CallableStatement func = conn.prepareCall("select * from getShop(?)");
            func.setInt(1, me.getId());
            ResultSet results = func.executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                double rating = results.getDouble("overall_score");
                myShop.setShop(id, rating, name);
            }
            results.close();
            func.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return myShop;
    }

    public ArrayList<Category> getCategories(){
        ArrayList<Category> categories = new ArrayList<Category>();
        try {
            CallableStatement func1 = conn.prepareCall("select * from getCategories()");
            ResultSet results1 = func1.executeQuery();
            while (results1.next()) {
                int id = results1.getInt("id");
                String categoryName = results1.getString("name");
                categories.add(new Category(id, categoryName));
            }
            results1.close();
            func1.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return categories;
    }

    public ArrayList<Platform> getPlatforms(){
        ArrayList<Platform> platforms = new ArrayList<Platform>();
        try {
            CallableStatement func2 = conn.prepareCall("select * from getPlatforms()");
            ResultSet results2 = func2.executeQuery();
            while (results2.next()) {
                int id = results2.getInt("id");
                String platformName = results2.getString("name");
                platforms.add(new Platform(id, platformName));
            }
            results2.close();
            func2.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return platforms;
    }



}
