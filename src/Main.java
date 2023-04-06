import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import Models.*;
import ControllersViews.*;
//import static sun.net.www.http.KeepAliveCache.result;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner in = new Scanner(System.in);
        User me = new User();
        var programEnd = false;
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/practice", "shinocayo", "1111")
        ) {
            AuthVC authVC = new AuthVC(in, conn);
            MainVC mainVC = new MainVC(in, conn);
            ShopVC shopVC = new ShopVC(in, conn);
            CartVC cartVC = new CartVC(in, conn);
            MenuVC menuVC = new MenuVC(in, conn);
            String pageName = "auth";


            while(true) {
                switch (pageName) {
                    case "auth":
                        authVC.setUser(me);
                        pageName = authVC.loop();
                        me = authVC.getMe();
                        break;
                    case "main":
                        mainVC.setUser(me);
                        pageName = mainVC.loop();
                        break;
                    case "menu":
                        menuVC.setUser(me);
                        pageName = menuVC.loop();
                        break;
                    case "shop":
                        shopVC.setUser(me);
                        pageName = shopVC.loop();
                        break;
                    case "cart":
                        cartVC.setUser(me);
                        pageName = cartVC.loop();
                        break;
                    case "log_out":
                        me.logoutUser();
                        pageName="main";
                    default:
                        programEnd = true;
                        break;
                }
                if(programEnd) break;
            }





        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        //int num = Input.read("input num");


    }
//    public static void main(String[] args) throws SQLException {
//        Scanner in = new Scanner(System.in);
//        SQL_STRINGS sql_str = new SQL_STRINGS();
//        User me = new User();
//        var programEnd = true;
//        var page = 1;
//        var per = 3;
//            try (Connection conn = DriverManager.getConnection(
//                    "jdbc:postgresql://localhost:5432/practice", "shinocayo", "1111")
//           ) {
//
//                while (true) {
//                    System.out.print("Greetings traveller!");
//                    // Auth
//                    while (true) {
//                        System.out.print("To use full options you " +
//                                "must login or may continue without it:\n" +
//                                "1 - sign up \n2 - sign in \n3 - continue without \n0 - exit\n");
//                        var escape = true;
//                        int num = in.nextInt();
//                        switch (num) {
//                            case 0:
//                                break;
//                            case 1:
//                                in.nextLine();
//                                System.out.println("input email");
//                                String newEmail = in.nextLine();
//
//                                System.out.println("input nickname");
//                                String newNick = in.nextLine();
//
//                                System.out.println("input password");
//                                String newPass = in.nextLine();
//
//                                System.out.println("confirm password");
//                                String passConfirmed = in.nextLine();
//
//                                if (Objects.equals(newPass, passConfirmed)) {
//                                    System.out.println("Registration processing...");
//                                    try {
//                                        CallableStatement cs = conn.prepareCall("{call createUser(?,?,?,?,?)}");
//                                        cs.setString(1, newEmail);
//                                        cs.setString(2, newPass);
//                                        cs.setString(3, newNick);
//                                        cs.setString(4, null);
//                                        cs.registerOutParameter(5, Types.INTEGER);
//                                        cs.execute();
//                                        System.out.println("Creation : " + cs.getInt(5));
//                                        if (cs.getInt(5) != -1) {
//                                            me.setUser(cs.getInt(5) ,"client", newEmail, newNick);
//                                        }
//                                        else{
//                                            System.out.println("this user's already been created");
//                                        }
//                                    } catch (SQLException e) {
//                                        e.printStackTrace();
//                                        escape = false;
//                                    }
//                                } else {
//                                    System.out.println("pass doesnt match");
//                                    escape = false;
//                                }
//                                break;
//                            case 2:
//                                in.nextLine();
//                                System.out.println("input email");
//                                String email = in.nextLine();
//
//                                System.out.println("input password");
//                                String pass = in.nextLine();
//                                try {
//                                    CallableStatement func = conn.prepareCall("select * from authorizeuser(?,?)");
//                                    func.setString(1, email);
//                                    func.setString(2, pass);
//
//                                    ResultSet results = func.executeQuery();
//                                    while (results.next()) {
//                                        var id = results.getInt("id");
//                                        var _role = results.getString("role_name");
//                                        var _name = results.getString("nickname");
//                                        var _mail = results.getString("email");
//                                        me.setUser(id, _role, _mail, _name);
//                                    }
//                                    results.close();
//                                    func.close();
//                                } catch (SQLException e) {
//                                    e.printStackTrace();
//                                }
//                                if (me.isAuthorized()) {
//                                    System.out.println("authorized successfully!");
//                                } else {
//                                    System.out.println("wrong pass or email.");
//                                    escape = false;
//                                }
//                                break;
//                            case 3:
//                                break;
//                            default:
//                                System.out.print("Wrong input");
//                                escape = false;
//                        }
//                        if (escape) break;
//                    }
//                    // Shop
//                    while(true) {
//                        System.out.print("U'r in marketplace Hypefye!");
//
//                        ArrayList<Item> items = new ArrayList<Item>();
//
//                        System.out.print("\nCommand list:\n 1 - find game\n2 - check profile/login" +
//                                (me.isSeller() ? "\n3 - visit my shop " : "") +
//                                "\n" + "4 - visit order history\n5 - visit cart" +
//                                (me.isAdmin() ? "\n999 - admin console " : ""));
//                        var escape_from_main = false;
//                        int num = in.nextInt();
//                        switch (num) {
//                            case 1:
//                                try {
//                                    in.nextLine();
//                                    System.out.println("Input game name");
//                                    String gameName = in.nextLine();
//                                    while (true) {
//                                        var escape_from_items_search = false;
//                                        CallableStatement func = conn.prepareCall("select * from getItemByName(?,?,?)");
//                                        func.setString(1, gameName);
//                                        func.setInt(2, page);
//                                        func.setInt(3, per);
//                                        ResultSet results = func.executeQuery();
//                                        var count = 0;
//                                        while (results.next()) {
//
//                                            int id = results.getInt("itemid");
//                                            String title = results.getString("title");
//                                            String description = results.getString("description");
//                                            String platform = results.getString("platform_name");
//                                            double price = results.getDouble("price");
//                                            int quantity = results.getInt("quantity");
//                                            String category = results.getString("category_name");
//                                            String shop = results.getString("shop_name");
//                                            items.add(new Item(id, title, description, price, quantity, platform, category, shop));
//                                            count++;
//                                        }
//                                        results.close();
//                                        func.close();
//                                        if (count != 0) {
//                                            for (Item it : items) {
//                                                it.print();
//                                            }
//                                            System.out.println("Entered shop console");
//
//                                            System.out.print("\nWanna see next items?:\n 0 - yes\n1 - no");
//
//                                            num = in.nextInt();
//                                            switch (num) {
//                                                case 0:
//                                                    page++;
//                                                    break;
//                                                case 1:
//                                                    break;
//                                            }
//                                        } else {
//                                            System.out.println("There is no item left");
//                                            escape_from_items_search = true;
//                                        }
//                                        if (escape_from_items_search) break;
//                                    }
//                                } catch (SQLException e) {
//                                    e.printStackTrace();
//                                }
//                                page = 1;
//
//                                System.out.print("\nCommand list:\n 1 - view game to order\n2 - back");
//                                num = in.nextInt();
//                                switch (num) {
//                                    case 1:
//                                        System.out.println("select item");
//                                        var iNum= in.nextInt();
//                                        for (Item it:items)
//                                        {
//                                            if (it.getId() == num){
//                                                var isClient = false;
//                                                if(me.isSeller()) {
//                                                    CallableStatement cs = conn.prepareCall("select * from checkShop(?,?)");
//                                                    cs.setLong(1, it.getId());
//                                                    cs.setInt(2, me.getId());
//                                                    ResultSet results = cs.executeQuery();
//                                                    Shop shop = new Shop();
//                                                    while (results.next()) {
//                                                        if(!results.getBoolean("res"))
//                                                        {
//                                                            isClient = true;
//                                                            int shopID = results.getInt("seller_id");
//                                                            String name = results.getString("name");
//                                                            double rating = results.getDouble("overall_score");
//                                                            shop.setShop(shopID, rating, name);
//                                                        }
//                                                    }
//                                                    results.close();
//                                                    cs.close();
//
//
//                                                if (isClient){
////                                                    if(item in cart){
////                                                        System.out.print("this item's already in cart!");
////                                                        break;
////                                                    }
//
//                                                    shop.print();
//                                                    System.out.print("\nCommand list:\n 1 - add game to cart\n2 - set rating to shop\n" +
//                                                            "3 - back\n");
//                                                    num = in.nextInt();
//                                                    switch (num) {
//                                                        case 1:
//                                                            try {
//                                                                System.out.println("number of item");
//                                                                num = in.nextInt();
//                                                                if(it.getQuantity() <= num){
//                                                                    // insert into cart
//                                                                    cs = conn.prepareCall("{call (?,?,?,?)}");
//                                                                    cs.setInt(1, me.getId());
//                                                                    cs.setInt(2, shop.getId());
//                                                                    cs.setInt(3, num);
//                                                                    cs.registerOutParameter(4, Types.FLOAT);
//                                                                    cs.execute();
//                                                                    shop.setShop(shop.getId(), cs.getDouble(4), shop.getName());
//                                                                    System.out.println("");
//                                                                }
//                                                                else{
//                                                                    System.out.println("order limit is exceeded");
//                                                                }
//                                                            }
//                                                            catch(SQLException e){
//                                                                e.printStackTrace();
//                                                            }
//                                                            break;
//                                                        case 2:
//                                                            try{
//                                                                System.out.println("choose rating from1 to 5");
//                                                                num = in.nextInt();
//                                                                if(num > 5 || num < 1){
//                                                                    System.out.println("wrong rating");
//                                                                    break;
//                                                                }
//                                                                cs = conn.prepareCall("{call setRating(?,?,?,?)}");
//                                                                cs.setInt(1, me.getId());
//                                                                cs.setInt(2, shop.getId());
//                                                                cs.setInt(3, num);
//                                                                cs.registerOutParameter(4, Types.FLOAT);
//                                                                cs.execute();
//                                                                shop.setShop(shop.getId(), cs.getDouble(4), shop.getName());
//                                                                System.out.println("Rating set!");
//                                                            }
//                                                            catch(SQLException e){
//                                                                e.printStackTrace();
//                                                            }
//                                                            break;
//                                                        case 3:
//                                                            break;
//                                                    }
//                                                }
//                                                else{
//                                                    System.out.println("There we go to my shop instead");
//                                                }
//
//                                            }
//                                            }
//                                            break;
//                                        }
//
//                                        break;
//                                    case 2:
//                                        break;
//                                }
//                                break;
//                            case 2:
//                                System.out.println("Entered user's profile");
//                                while (true) {
//                                    if (me.isAuthorized()) {
//                                        System.out.print(" Your name: " + me.getNick() + "\n your email: " + me.getEmail() +
//                                                "\nCommand list:\n 0 - back\n");
//                                        var escape9 = true;
//                                        int num9 = in.nextInt();
//                                        if (num9 != 0) {
//                                            escape9 = false;
//                                        }
//                                        if (escape9) break;
//                                    } else {
//                                        System.out.print("Log in first!");
//                                        escape_from_main = true;
//                                        programEnd = false;
//                                    }
//                                }
//                                break;
//                            case 3:
//                                if(me.isSeller()) {
//                                    System.out.println("Entered shop console");
//                                    Shop myShop = new Shop();
//                                    CallableStatement func = conn.prepareCall("select * from getShop(?)");
//                                    func.setInt(1, me.getId());
//                                    ResultSet results = func.executeQuery();
//                                    while (results.next()) {
//                                        int id = results.getInt("id");
//                                        String name = results.getString("name");
//                                        double rating = results.getDouble("overall_score");
//                                        myShop.setShop(id, rating, name);
//                                    }
//                                    results.close();
//                                    func.close();
//                                    myShop.print();
//
//                                    ArrayList<Category> categories = new ArrayList<Category>();
//                                    ArrayList<Platform> platforms = new ArrayList<Platform>();
//
//                                    CallableStatement func1 = conn.prepareCall("select * from getCategories()");
//                                    ResultSet results1 = func1.executeQuery();
//                                    while (results1.next()) {
//                                        int id = results1.getInt("id");
//                                        String categoryName = results1.getString("name");
//                                        categories.add(new Category(id, categoryName));
//                                    }
//                                    results1.close();
//                                    func1.close();
//
//                                    CallableStatement func2 = conn.prepareCall("select * from getPlatforms()");
//                                    ResultSet results2 = func2.executeQuery();
//                                    while (results2.next()) {
//                                        int id = results2.getInt("id");
//                                        String platformName = results2.getString("name");
//                                        platforms.add(new Platform(id, platformName));
//                                    }
//                                    results2.close();
//                                    func2.close();
//                                    while (true) {
//                                        System.out.print("\nCommand list:\n 0 - add new item" +
//                                                "\n1 - view my items\n2 - back\n3 - view users orders\n");
//                                        var escape_from_shop = false;
//                                        num = in.nextInt();
//                                        switch (num) {
//                                            case 0:
//                                                try {
//                                                    in.nextLine();
//                                                    System.out.println("input new name");
//                                                    var title = in.nextLine();
//
//                                                    System.out.println("input description");
//                                                    var description = in.nextLine();
//
//                                                    System.out.println("input price");
//                                                    var price = in.nextDouble();
//
//                                                    System.out.println("input quantity");
//                                                    var quantity = in.nextInt();
//
//                                                    System.out.println("choose category:\n");
//                                                    for (Category it : categories) {
//                                                        it.print();
//                                                    }
//                                                    var categoryID = in.nextInt();
//
//                                                    System.out.println("choose platform");
//                                                    for (Platform it : platforms) {
//                                                        it.print();
//                                                    }
//                                                    var platformID = in.nextInt();
//                                                    boolean res;
//                                                    func = conn.prepareCall(
//                                                            "{call createItem(?,?,?,?,?,?,?,?)}");
//                                                    func.setInt(1, myShop.getId());
//                                                    func.setString(2, title);
//                                                    func.setString(3, description);
//                                                    func.setInt(4, quantity);
//                                                    func.setInt(5, platformID);
//                                                    func.setInt(6, categoryID);
//                                                    func.setDouble(7, price);
//                                                    func.registerOutParameter(8, Types.INTEGER);
//                                                    func.execute();
//
//                                                        if(func.getInt(8) != -1){
//                                                        String platformName = "";
//                                                            for (Platform it : platforms) {
//                                                                if(it.checkId(platformID)){
//                                                                    platformName = it.getName();
//                                                                }
//                                                            }
//                                                        String categoryName = "";
//                                                        for (Category it : categories) {
//                                                            if(it.checkId(categoryID)){
//                                                                categoryName = it.getName();
//                                                            }
//                                                        }
//                                                        items.add(new Item(results.getInt(8), title,
//                                                                description, price, quantity,
//                                                                platformName, categoryName, myShop.getName()));
//                                                            System.out.println("\nSuccess\n");
//                                                        }
//                                                        else{
//                                                            System.out.println("cant create new item");
//                                                        }
//
//                                                    results.close();
//                                                    func.close();
//                                                }
//                                                catch (SQLException e){
//                                                    e.printStackTrace();
//                                                }
//                                                break;
//                                            case 1:
//                                                try {
//                                                    ArrayList<Item> myItems = new ArrayList<Item>();
//                                                    func = conn.prepareCall("select * from getShopItems(?)");
//                                                    func.setInt(1, myShop.getId());
//                                                    //func.setInt(2, page);
//                                                    results = func.executeQuery();
//                                                    while (results.next()) {
//                                                        int id = results.getInt("id");
//                                                        String title = results.getString("title");
//                                                        String description = results.getString("description");
//                                                        String platform = results.getString("platform_name");
//                                                        double price = results.getDouble("price");
//                                                        int quantity = results.getInt("quantity");
//                                                        String category = results.getString("category_name");
//                                                        String shop = results.getString("shop_name");
//                                                        myItems.add(new Item(id, title, description, price, quantity, platform, category, shop));
//                                                    }
//                                                    results.close();
//                                                    func.close();
//                                                    for (Item it : myItems) {
//                                                        it.print();
//                                                    }
//
//                                                        while (true) {
//                                                            System.out.print("\nCommand list:\n 0 - updateItem" +
//                                                                    "\n1 - deleteItem\n2 - back\n");
//                                                            var escape_from_item_redaction = false;
//                                                            num = in.nextInt();
//                                                            Item item;
//
//                                                            switch (num) {
//                                                                case 0:
//                                                                    try {
//
//                                                                    System.out.print("\ninput num of item to update");
//                                                                    num = in.nextInt();
//                                                                    for (Item it : myItems) {
//                                                                        if (it.getId() == num) {
//                                                                        item = it;
//                                                                            in.nextLine();
//                                                                        System.out.print("\ninput 0 - input new title or 1 - to continue");
//
//                                                                        num = in.nextInt();
//
//                                                                        String title;
//                                                                        if (num == 0) {
//                                                                            in.nextLine();
//                                                                            title = in.nextLine();
//                                                                        } else {
//                                                                            title = item.getTitle();
//                                                                        }
//                                                                        String description;
//                                                                        System.out.print("\ninput 0 - input new description or 1 - to continue");
//                                                                        num = in.nextInt();
//                                                                        if (num == 0) {
//                                                                             description = in.nextLine();
//                                                                        } else {
//                                                                            description = item.getDescription();
//                                                                        }
//                                                                        int quantity;
//                                                                        System.out.print("\ninput 0 - input new quantity or 1 - to continue");
//                                                                        num = in.nextInt();
//                                                                        if (num == 0) {
//                                                                             quantity = in.nextInt();
//                                                                        } else {
//                                                                             quantity = item.getQuantity();
//                                                                        }
//
//                                                                        System.out.print("\ninput 0 - input new price or 1 - to continue");
//                                                                        num = in.nextInt();
//                                                                        double price;
//                                                                        if (num == 0) {
//                                                                            price = in.nextDouble();
//                                                                        } else {
//                                                                            price = item.getPrice();
//                                                                        }
//                                                                        int categoryID = -1;
//                                                                        System.out.print("\ninput 0 - input new category or 1 - to continue");
//                                                                        num = in.nextInt();
//                                                                        if (num == 0) {
//                                                                            for (Category cat : categories) {
//                                                                                cat.print();
//                                                                            }
//                                                                            categoryID = in.nextInt();
//                                                                        } else {
//                                                                            for (Category cat : categories) {
//                                                                                if (Objects.equals(cat.getName(), item.getCategory())) {
//                                                                                    categoryID = cat.getId();
//                                                                                    break;
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                        int platformID = -1;
//                                                                        System.out.print("\ninput 0 - input new platform or 1 - to continue");
//                                                                        num = in.nextInt();
//                                                                        if (num == 0) {
//                                                                            for (Platform plt : platforms) {
//                                                                                plt.print();
//                                                                            }
//                                                                            platformID = in.nextInt();
//                                                                        } else {
//                                                                            System.out.println("choose platform");
//                                                                            for (Platform plt : platforms) {
//                                                                                if (Objects.equals(plt.getName(), item.getPlatform())) {
//                                                                                    platformID = plt.getId();
//                                                                                    break;
//                                                                                }
//                                                                            }
//                                                                        }
//
//
//                                                                        func = conn.prepareCall(
//                                                                                "{call updateItem(?,?,?,?,?,?,?,?)}");
//                                                                        func.setLong(1, item.getId());
//                                                                        func.setString(2, title);
//                                                                        func.setString(3, description);
//                                                                        func.setInt(4, quantity);
//                                                                        func.setInt(5, platformID);
//                                                                        func.setInt(6, categoryID);
//                                                                        func.setDouble(7, price);
//                                                                        func.registerOutParameter(8, Types.BOOLEAN);
//                                                                        func.execute();
//
//                                                                        if (func.getBoolean(8)) {
//
//                                                                            System.out.println("\nSuccessfully changed!\n");
//                                                                        } else {
//                                                                            System.out.println("Cant change item");
//                                                                        }
//                                                                        func.close();
//                                                                        break;
//                                                                        }
//                                                                    }
//                                                                    }catch(SQLException e){
//                                                                        e.printStackTrace();
//                                                                    }
//                                                                    break;
//                                                            case 1:
//                                                                // hide item
//                                                                try {
//                                                                    System.out.print("\ninput num of item to delete");
//                                                                    num = in.nextInt();
//                                                                    for (Item it : myItems) {
//                                                                        if (it.getId() == num) {
//                                                                            item = it;
//
//                                                                            func = conn.prepareCall(
//                                                                                    "{call hideItem(?,?,?)}");
//                                                                            func.setLong(1, item.getId());
//                                                                            func.setBoolean(2, true);
//                                                                            func.registerOutParameter(3, Types.BOOLEAN);
//                                                                            func.execute();
////                                                                            while (results.next()) {
////                                                                                if (results.getBoolean(8)) {
////
////                                                                                    System.out.println("\nSuccessfully changed!\n");
////                                                                                } else {
////                                                                                    System.out.println("Cant change item");
////                                                                                }
////                                                                            }
//                                                                            if (func.getBoolean(3)){
//                                                                                System.out.print("\nitem hidden!");
//                                                                            }
//                                                                            else{
//                                                                                System.out.print("\nerror");
//                                                                            }
//                                                                            results.close();
//                                                                            func.close();
//                                                                            break;
//                                                                        }
//                                                                    }
//                                                                }catch(SQLException e){
//                                                                    e.printStackTrace();
//                                                                }
//                                                                break;
//                                                            case 2:
//                                                                escape_from_item_redaction = true;
//                                                                break;
//                                                        }
//                                                        if(escape_from_item_redaction)break;
//                                                    }
//
//                                                }
//                                                catch (SQLException e){
//                                                    e.printStackTrace();
//                                                }
//                                                break;
//                                            case 2:
//                                                escape_from_shop = true;
//                                                break;
//                                            case 3:
//                                                System.out.print("\nCommand list:\n 0 - check completed orders" +
//                                                        "\n1 - check not completed orders \n");
//
//
//                                                switch ()
//                                                {
//                                                    case 0:
//                                                        break;
//                                                    case 1:
//                                                        System.out.print("\nSelect order to complete\n");
//                                                        break;
//                                                }
//                                                break;
//                                        }
//
//                                        if (escape_from_shop)
//                                            break;
//                                    }
//                                }
//                                break;
//                            case 4:
//                                System.out.println("Entered order history");
//                                while (true) {
//                                    System.out.print("\nCommand list:\n 0 - back\n1 - check current order");
//                                    var escape_from_order_history = false;
//                                    num = in.nextInt();
//                                    switch (num) {
//                                        case 0:
//                                            escape_from_order_history = true;
//                                            break;
//                                        case 1:
//                                            ArrayList<Order> orders = new ArrayList<Order>();
//                                            CallableStatement cs = conn.prepareCall("select * from showOrdersForUser(?)");
//                                            cs.setLong(1, me.getId());
//                                            ResultSet results = cs.executeQuery();
//                                            Shop shop = new Shop();
//
//                                            while (results.next()) {
//                                               orders.add(new Order(results.getLong("orderid"),
//                                                       results.getDouble("total_sum"),
//                                                       results.getString("date_purch") ));
//                                            }
//                                            results.close();
//                                            cs.close();
//                                            System.out.print("\nSelect order by its number\n");
//                                            num = in.nextInt();
//
//                                            ArrayList<OrderItem> orderedItems = new ArrayList<OrderItem>();
//                                            cs = conn.prepareCall("select * from showOrderStuffForUser(?)");
//                                            cs.setLong(1, num);
//                                            results = cs.executeQuery();
//                                            var count = 0;
//                                            while (results.next()) {
//                                                long id = results.getLong("odereditemid");
//                                                String name =  results.getString("title");
//                                                double price=  results.getDouble("price");
//                                                long quantity=  results.getLong("quantity");
//                                                String status=  results.getString("item_status");
//                                                String value = results.getString("date_purch");;
//                                                String shopName=  results.getString("shop_name");
//                                                orderedItems.add(new OrderItem(id, name, price, quantity, status, shopName, value));
//
//                                            }
//                                            for (OrderItem odIt: orderedItems){
//                                                odIt.print();
//                                            }
//                                            results.close();
//                                            cs.close();
//                                            break;
//                                    }
//                                    if(escape_from_order_history)break;
//                                }
//                            case 5:
//                                System.out.println("Entered cart");
//                                while (true) {
//                                    ArrayList<OrderItem> cartItems = new ArrayList<OrderItem>();
//                                    CallableStatement cs = conn.prepareCall("select * from showCart(?)");
//                                    cs.setLong(1, me.getId());
//                                    ResultSet results = cs.executeQuery();
//                                    var count = 0;
//                                    long orderID = -1;
//                                    double sum = 0;
//                                    while (results.next()) {
//                                        orderID = results.getLong("orderid");
//                                        sum = results.getDouble("ordersum");
//                                        long id = results.getLong("odereditemid");
//                                        String name =  results.getString("title");
//                                        double price=  results.getDouble("price");
//                                        long quantity=  results.getLong("quantity");
//                                        String shopName=  results.getString("shop_name");
//                                        cartItems.add(new OrderItem(id, name, price, quantity, "", shopName, ""));
//                                    }
//                                    count = 0;
//                                    for (OrderItem odIt: cartItems){
//                                        System.out.println("Number: " + count);
//                                        count++;
//                                        odIt.print();
//                                    }
//                                    results.close();
//                                    cs.close();
//
//                                    System.out.print("\nCommand list:\n 0 - change product\n1 - make an order\n" +
//                                            "2 - delete item\n3 - back\n");
//                                    var escape_from_cart = false;
//                                    num = in.nextInt();
//                                    switch (num) {
//                                        case 0:
//                                            System.out.print("\nSelect item to update\n");
//                                            num = in.nextInt();
//                                            System.out.print("\nselect new quantity\n");
//                                            // поставить ограничение на количество потом
//                                            long quantity = in.nextInt();
//                                            cs = conn.prepareCall("{call updateCartItem(?,?)}");
//                                            cs.setLong(1, cartItems.get(num).getId());
//                                            cs.setLong(2, quantity);
//                                            cs.execute();
//                                            num = in.nextInt();
//                                            break;
//                                        case 1:
//                                            System.out.print("\nPress 0 to confirm\n");
//                                            num = in.nextInt();
//                                            if (num == 0){
//                                                if(me.getBalance() < sum){
//                                                    System.out.print("\nNot enough money\n");
//                                                    break;
//                                                }
//                                                else{
//                                                    cs = conn.prepareCall("{call makeOrder(?,?)}");
//                                                    cs.setLong(1, me.getId());
//                                                    cs.registerOutParameter(2, Types.BOOLEAN);
//                                                    cs.execute();
//                                                    if(cs.getBoolean(2)){
//                                                        System.out.print("\nPurchased successfully\n");
//                                                    }
//                                                    else{
//                                                        System.out.print("\nOps, smth s gone wrong\n");
//                                                    }
//                                                }
//                                            }
//                                            break;
//                                        case 2:
//                                            System.out.print("\nselect item to delete\n");
//                                            num = in.nextInt();
//                                            cs = conn.prepareCall("{call deleteItemFromCart(?)}");
//                                            cs.setLong(1, cartItems.get(num).getId());
//                                            cs.execute();
//                                            break;
//                                        case 3:
//                                            escape_from_cart = true;
//                                            break;
//                                    }
//
//                                    if(escape_from_cart)break;
//                                }
//                                break;
//                            case 999:
//                                System.out.println("Entered admin console");
//                                while (true){
//                                    System.out.print("\nCommand list:\n 0 - find user\n1 - give seller role" +
//                                            "\n2 - delete user from sellers\n3 - back");
//                                    var escape_from_admin_console = false;
//                                    num = in.nextInt();
//                                    switch (num){
//                                        case 0:
//                                            System.out.print("\ninsert nick or email\n");
//                                            var input = in.nextLine();
//                                            CallableStatement cs = conn.prepareCall("select * from findUser(?)");
//                                            cs.setString(1, input);
//                                            ResultSet results = cs.executeQuery();
//                                            while (results.next()) {
//                                            System.out.print("Name: " + results.getString("email")
//                                                    + "\n Email: " + results.getString("email") +
//                                                    "Balance: " + results.getDouble("balance")
//                                                    + "Id: " + results.getLong("id") +"\n");
//                                            }
//                                            results.close();
//                                            cs.close();
//                                            break;
//                                        case 1:
//                                            System.out.print("\ninsert id of user \n");
//                                            var id = in.nextLong();
//                                            cs = conn.prepareCall("call updateUserRoleToSeller(?, ?)");
//                                            cs.setLong(1, id);
//                                            cs.registerOutParameter(2, Types.BOOLEAN);
//                                            cs.execute();
//                                            if(cs.getBoolean(2)){
//                                                System.out.print("\nSuccess\n");
//                                            }else{
//                                                System.out.print("\nError\n");
//                                            }
//                                            break;
//                                        case 2:
//                                            System.out.print("\ninsert id of seller to kick out\n");
//                                            id = in.nextLong();
//                                            cs = conn.prepareCall("call deleteUserFromSellers(?,?)");
//                                            cs.setLong(1, id);
//                                            cs.registerOutParameter(2, Types.BOOLEAN);
//                                            cs.execute();
//                                            if(cs.getBoolean(2)){
//                                                System.out.print("\nSuccess\n");
//                                            }else{
//                                                System.out.print("\nError\n");
//                                            }
//                                            break;
//                                        case 3:
//                                            escape_from_admin_console = true;
//                                            break;
//                                    }
//                                    if(escape_from_admin_console) break;
//                                }
//
//
//                                break;
//                            default:
//                                System.out.print("Wrong input");
//                                escape_from_main = true;
//                        }
//                        if (escape_from_main) break;
//                    }
//                    if (programEnd) {
//                        break;
//                    }
//                }
//
//
//
//} catch (SQLException e) {
//                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println(e.getMessage());
//            }
//            //int num = Input.read("input num");
//
//
//    }
}