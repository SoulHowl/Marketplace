package Utils;

import java.util.Scanner;

public class Input {

   public  int  read(String line){
      Scanner in = new Scanner(System.in);
      System.out.print(line);
      int num = in.nextInt();
      in.close();
      return num;
   }
}
