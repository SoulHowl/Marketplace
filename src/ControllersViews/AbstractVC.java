package ControllersViews;

import Models.User;

import java.sql.Connection;
import java.util.Scanner;

public abstract class AbstractVC {
    public Scanner in;
    public Connection conn;

    public User me;
    public User getMe(){
        return me;
    }
    public void setUser(User me){
        this.me = me;
    }
}
