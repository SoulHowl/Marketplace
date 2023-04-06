package ControllersViews;

import DBlogic.AuthFuncs;

import java.sql.*;
import java.util.Scanner;

public class AuthVC extends AbstractVC {

    public AuthVC(Scanner in_, Connection conn_){
        this.in = in_;
        this.conn = conn_;
    }
    public String loop() throws SQLException {
        AuthFuncs authFuncs = new AuthFuncs(in, conn);
        while (true) {
            System.out.print("To use full options you " +
                    "must login or may continue without it:\n" +
                    "1 - sign up \n2 - sign in \n3 - continue without \n0 - exit\n");
            var escape = true;
            int num = in.nextInt();
            switch (num) {
                case 0:
                    return "none";
                case 1:
                    in.nextLine();
                    System.out.println("input email");
                    String newEmail = in.nextLine();

                    System.out.println("input nickname");
                    String newNick = in.nextLine();

                    System.out.println("input password");
                    String newPass = in.nextLine();

                    System.out.println("confirm password");
                    String passConfirmed = in.nextLine();


                    me = authFuncs.registerUser(me, newEmail, newNick, newPass, passConfirmed);
                    if (me == null){
                        escape = false;
                    }
                    break;
                case 2:
                    in.nextLine();
                    System.out.println("input email");
                    String email = in.nextLine();

                    System.out.println("input password");
                    String pass = in.nextLine();
                    me = authFuncs.authorizeUser(me, email, pass);
                    if (me.isAuthorized()) {
                        System.out.println("authorized successfully!");
                    } else {
                        System.out.println("wrong pass or email.");
                        escape = false;
                    }
                    break;
                case 3:

                default:
                    System.out.print("Wrong input");
                    escape = false;
            }
            if (escape) break;
        }
        return "main";
    }
}
