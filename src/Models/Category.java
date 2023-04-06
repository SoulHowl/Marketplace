package Models;

public class Category {

    private int id;
    private String name;
    public Category(int id, String name){
        this.id = id;
        this.name = name;
    }
    public boolean checkId(int id){
        return id == this.id;
    }
    public String getName(){
        return  name ;
    }

    public int getId(){
        return id;
    }
    public void print(){
        System.out.printf("\n%2d - \n%s\n",
                id, name);
    }


}
