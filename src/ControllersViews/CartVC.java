package ControllersViews;

import DBlogic.CartFuncs;
import DBlogic.ItemFuncs;
import Models.Cart;
import Models.OrderedItem;

import java.sql.*;
import java.util.Scanner;

public class CartVC extends AbstractVC {

    public CartVC(Scanner in_, Connection conn_){

        this.in = in_;
        this.conn = conn_;
    }

    public String loop() throws SQLException {
        System.out.println("Entered cart");
        CartFuncs cartFuncs = new CartFuncs(in, conn);
        ItemFuncs itemFuncs = new ItemFuncs(in, conn);
        while (true) {
            Cart cart = cartFuncs.showCart(me);
            var count = 0;
            System.out.println("Cart sum: " + cart.sum);
            for (OrderedItem odIt: cart.cartItems){
                System.out.println("Number: " + count);
                count++;
                odIt.print();
            }
            System.out.print("\nCommand list:" + (cart.sum > 0?"\n 0 - change product\n1 - make an order\n" +
                    "2 - delete item":"") + "\n3 - back\n");
            var escape_from_cart = false;
            int num = in.nextInt();
            switch (num) {
                case 0:
                    System.out.print("\nSelect item to update\n");
                    num = in.nextInt();
                    System.out.print("\nselect new quantity\n");
                    long quantity = in.nextLong();
                    if(itemFuncs.updateCartItem(cart.cartItems.get(num).getId(), quantity)){
                        OrderedItem newItem = cart.cartItems.get(num);
                        newItem.setQuantity(quantity);
                    }
                    // limit quantity
                    break;
                case 1:
                    System.out.print("\nPress 0 to confirm\n");
                    num = in.nextInt();
                    if (num == 0){
                        if(me.getBalance() < cart.sum){
                            System.out.print("\nNot enough money\n");
                            break;
                        }
                        else{
                            if(cartFuncs.makeOrder(me)){
                                System.out.print("\nPurchased successfully\n");
                                cart.cartItems.clear();
                                cart.sum = 0;
                            }
                            else{
                                System.out.print("\nOps, smth s gone wrong\n");
                            }
                        }
                    }
                    break;
                case 2:
                    System.out.print("\nselect item to delete\n");
                    num = in.nextInt();
                    if(itemFuncs.deleteCarItem(cart.cartItems.get(num).getId())){
                        cart.cartItems.remove(num);
                    }
                    break;
                case 3:
                    escape_from_cart = true;
                    break;
            }

            if(escape_from_cart)break;
        }
        return "main";
    }

}
