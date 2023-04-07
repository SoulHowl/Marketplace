package ControllersViews;

import DBlogic.ItemFuncs;
import DBlogic.ShopFuncs;
import Models.Category;
import Models.Item;
import Models.Platform;
import Models.Shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ShopVC extends AbstractVC {

    public ShopVC(Scanner in_, Connection conn_){

        this.in = in_;
        this.conn = conn_;
    }

    public String loop() {
        ShopFuncs shopFuncs = new ShopFuncs(in, conn);
        ItemFuncs itemFuncs = new ItemFuncs(in, conn);
        System.out.println("Entered shop console");
        Shop myShop = shopFuncs.getShopInfo(me);
        myShop.print();
        ArrayList<Item> myItems = shopFuncs.getShopItems(myShop);
        for (Item it : myItems) {
            it.print();
        }

        while (true) {
            ArrayList<Category> categories = shopFuncs.getCategories();
            ArrayList<Platform> platforms = shopFuncs.getPlatforms();

            System.out.print("\nCommand list:\n 0 - add new item" +
                    "\n 1 - updateItem\n2 - deleteItem\n3 - back\n4 - view users orders\n");
            var escape_from_shop = false;
            int num = in.nextInt();
            switch (num) {
                case 0:
                    in.nextLine();
                    System.out.println("input new name");
                    var title = in.nextLine();

                    System.out.println("input description");
                    var description = in.nextLine();

                    System.out.println("input price");
                    var price = in.nextDouble();

                    System.out.println("input quantity");
                    var quantity = in.nextInt();

                    System.out.println("choose category:\n");
                    for (Category it : categories) {
                        it.print();
                    }
                    var categoryID = in.nextInt();

                    System.out.println("choose platform");
                    for (Platform it : platforms) {
                        it.print();
                    }
                    var platformID = in.nextInt();
                    var newID = itemFuncs.addNewItem(myShop, title, description, platformID, categoryID, quantity, price);
                    if (newID != -1) {
                        String platformName = "";
                        for (Platform it : platforms) {
                            if (it.checkId(platformID)) {
                                platformName = it.getName();
                            }
                        }
                        String categoryName = "";
                        for (Category it : categories) {
                            if (it.checkId(categoryID)) {
                                categoryName = it.getName();
                            }
                        }
                        myItems.add(new Item(newID, title,
                                description, price, quantity,
                                platformName, categoryName, myShop.getName()));
                        System.out.println("\nSuccess\n");
                    } else {
                        System.out.println("cant create new item");
                    }
                    break;
                case 1:

                    System.out.print("\ninput num of item to update");
                    num = in.nextInt();
                    for (Item it : myItems) {
                        if (it.getId() == num) {
                            in.nextLine();
                            num = in.nextInt();
                            System.out.print("\ninput 0 - input new title or 1 - to continue");
                            if (num == 0) {
                                in.nextLine();
                                title = in.nextLine();
                            } else {
                                title = it.getTitle();
                            }

                            System.out.print("\ninput 0 - input new description or 1 - to continue");
                            num = in.nextInt();
                            if (num == 0) {
                                description = in.nextLine();
                            } else {
                                description = it.getDescription();
                            }

                            System.out.print("\ninput 0 - input new quantity or 1 - to continue");
                            num = in.nextInt();
                            if (num == 0) {
                                quantity = in.nextInt();
                            } else {
                                quantity = it.getQuantity();
                            }

                            System.out.print("\ninput 0 - input new price or 1 - to continue");
                            num = in.nextInt();

                            if (num == 0) {
                                price = in.nextDouble();
                            } else {
                                price = it.getPrice();
                            }
                            int categoryId = -1;
                            System.out.print("\ninput 0 - input new category or 1 - to continue");
                            num = in.nextInt();
                            if (num == 0) {
                                for (Category cat : categories) {
                                    cat.print();
                                }
                                categoryId = in.nextInt();
                            } else {
                                for (Category cat : categories) {
                                    if (Objects.equals(cat.getName(), it.getCategory())) {
                                        categoryId = cat.getId();
                                        break;
                                    }
                                }
                            }
                            int platformId = -1;
                            System.out.print("\ninput 0 - input new platform or 1 - to continue");
                            num = in.nextInt();
                            if (num == 0) {
                                for (Platform plt : platforms) {
                                    plt.print();
                                }
                                platformId = in.nextInt();
                            } else {
                                System.out.println("choose platform");
                                for (Platform plt : platforms) {
                                    if (Objects.equals(plt.getName(), it.getPlatform())) {
                                        platformId = plt.getId();
                                        break;
                                    }
                                }
                            }

                            if (itemFuncs.updateItem(it, title, description, categoryId, platformId, quantity, price)) {

                                System.out.println("\nSuccessfully changed!\n");
                            } else {
                                System.out.println("Cant change item");
                            }

                        }
                        break;
                    }
                    break;
                case 2:
                    System.out.print("\ninput num of item to delete");
                    num = in.nextInt();
                    for (Item it : myItems) {
                        if (it.getId() == num) {
                        }
                        if (itemFuncs.deleteItem(it)) {
                            System.out.print("\nitem hidden!");
                        } else {
                            System.out.print("\nerror");
                        }
                        break;
                    }
                    break;
                case 3:
                    return "main";
                case 4:
                    System.out.print("\nCommand list:\n 0 - check completed orders" +
                            "\n1 - check not completed orders \n");


//                                    switch ()
//                                    {
//                                        case 0:
//                                            break;
//                                        case 1:
//                                            System.out.print("\nSelect order to complete\n");
//                                            break;
//                                    }
                    break;
            }

            if (escape_from_shop)
                break;
        }
        return "shop";
    }
}
