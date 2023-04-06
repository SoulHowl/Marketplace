package Models;

import java.util.Objects;

public class User {
    private int id;
    private String role;
    private String email;
    private String nickname;

    private double balance = 0.0;

    public boolean isAuthorized(){
        if (!Objects.isNull(this.nickname)){
            return true;
        }
        return false;
    }

    public void logoutUser(){
        this.nickname = null;
    }
    public void setUser(int id, String role, String mail, String nick){
        this.id =id;
        this.role = role;
        this.nickname = nick;
        this.email = mail;
    }

    public boolean isAdmin(){
        return Objects.equals(role, "admin");
    }
    public boolean isSeller(){
        return Objects.equals(role, "seller");
    }

    public void setBalance(double sum){
        this.balance += sum;
    }
    public double getBalance(){
        return balance;
    }
    public int getId(){
        return id;
    }
    public String getNick(){
        return this.nickname;
    }
    public String getEmail(){
        return this.email;
    }
}
