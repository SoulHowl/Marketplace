package DBlogic;

import Models.User;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class AuthFuncs {
    public Scanner in;
    public Connection conn;
    public AuthFuncs(Scanner in_, Connection conn_){
        this.in = in_;
        this.conn = conn_;
    }

    public User authorizeUser(User me, String email, String pass){
        try {
            CallableStatement func = conn.prepareCall("select * from authorizeuser(?,?)");
            func.setString(1, email);
            func.setString(2, pass);

            ResultSet results = func.executeQuery();
            while (results.next()) {
                var id = results.getInt("id");
                var _role = results.getString("role_name");
                var _name = results.getString("nickname");
                var _mail = results.getString("email");
                me.setUser(id, _role, _mail, _name);
            }
            results.close();
            func.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return me;
    };
    public User registerUser(User me, String email, String nickname, String password, String passwordConfirmed){
        if (Objects.equals(password, passwordConfirmed)) {
            System.out.println("Registration processing...");
            try {
                CallableStatement cs = conn.prepareCall("{call createUser(?,?,?,?,?)}");
                cs.setString(1, email);
                cs.setString(2, password);
                cs.setString(3, nickname);
                cs.setString(4, null);
                cs.registerOutParameter(5, Types.INTEGER);
                cs.execute();
                System.out.println("Creation : " + cs.getInt(5));
                if (cs.getInt(5) != -1) {
                    me.setUser(cs.getInt(5) ,"client", email, nickname);

                }
                else{
                    System.out.println("this user's already been created");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("pass doesnt match");
            return null;
        }

        return me;
    };

}
