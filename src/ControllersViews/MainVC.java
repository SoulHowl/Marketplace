package ControllersViews;

import DBlogic.ItemFuncs;
import DBlogic.ShopFuncs;
import Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainVC extends AbstractVC {

    public MainVC(Scanner in_, Connection conn_){

        this.in = in_;
        this.conn = conn_;
    }

    public String loop() throws SQLException {
        var page = 1;
        var per = 3;
        while(true) {
            System.out.print("U'r in marketplace Hypefye!");

            ArrayList<Item> items = new ArrayList<Item>();
            ItemFuncs itemFuncs = new ItemFuncs(in, conn);
            ShopFuncs shopFuncs = new ShopFuncs(in, conn);
            System.out.print("\nCommand list:\n 1 - find game\n"+ (me.isAuthorized()?"2 - open menu":"2 - log in") +
                    (me.isSeller() ? "\n3 - visit my shop\n" : "\n"));
            var escape_from_main = false;
            int num = in.nextInt();
            switch (num) {
                case 1:
                        in.nextLine();
                        System.out.println("Input game name");
                        String gameName = in.nextLine();
                        var count = 0;
                        while (true) {
                            var escapeItemSearchCycle = false;

                             items.addAll(itemFuncs.findItems(gameName,page,per));
                             if(count == items.size()){
                                 System.out.print("\nEnd of pages");
                                 break;
                             }else{
                                 count = items.size();
                             };
                            if (!items.isEmpty()) {
                                for (int i = (page - 1) * per; i<items.size(); i++) {
                                    items.get(i).print();
                                }
                                System.out.print("\nWanna see next items?:\n 0 - yes\n1 - no");

                                num = in.nextInt();
                                switch (num) {
                                    case 0:
                                        page++;
                                        break;
                                    case 1:
                                        escapeItemSearchCycle = true;
                                        break;
                                }
                            } else {
                                System.out.println("There is no item left");
                                escapeItemSearchCycle = true;
                            }
                            if (escapeItemSearchCycle) break;
                        }
                    page = 1;

                    System.out.print("\nCommand list:\n 1 - view game to order\n2 - back");
                    num = in.nextInt();
                    switch (num) {
                        case 1:
                            System.out.println("select item");
                            var iNum= in.nextInt();
                            for (Item it:items)
                            {
                                if (it.getId() == num){
                                    FullItemData data = itemFuncs.viewItem(me, it);
                                    data.shop.print();
                                    System.out.print("\nCommand list:\n" +
                                                    (data.inCart?"1:view cart":"1 - add game to cart") +
                                                    "\n2 - set rating to shop\n3 - back\n");
                                    num = in.nextInt();
                                    switch (num) {
                                        case 1:
                                            if(!data.inCart){
                                                System.out.println("number of item");
                                                num = in.nextInt();
                                                if(it.getQuantity() <= num){
                                                    // insert into cart
                                                  if(itemFuncs.addItemToCart(me, data.shop, num))
                                                    {
                                                        System.out.println("added to cart");
                                                    }
                                                }
                                                else{
                                                    System.out.println("order limit is exceeded");
                                                }
                                            }
                                            else {
                                                return "cart";
                                            }
                                            break;
                                        case 2:

                                                System.out.println("choose rating from1 to 5");
                                                num = in.nextInt();
                                                if(num > 5 || num < 1){
                                                    System.out.println("wrong rating");
                                                    break;
                                                }
                                                data.shop = shopFuncs.setRating(me, data.shop, num);
                                                System.out.println("Rating set!");

                                                    break;
                                                case 3:
                                                    break;
                                            }
                                        }
                                        else{
                                            return "shop";
                                        }

                                    }
                                break;

                        case 2:
                            break;
                    }
                    break;
                case 2:
                    if(me.isAuthorized()) {
                        return "menu";
                    }
                    else{
                        return "auth";
                    }
                case 3:
                    if(me.isSeller()) {
                        return "shop";
                    }
                    break;

                default:
                    System.out.print("Wrong input");
                    escape_from_main = false;
            }
            if (escape_from_main) break;
        }
        return "none";
    }
}
