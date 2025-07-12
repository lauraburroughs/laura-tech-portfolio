import java.io.IOException;

/**
* Dense Bean Salad Main Method.
* @author Laura Burroughs
* @version 21 April 2025
*/

public class DenseBeanSaladMain {

/**
* User interaction for DBS randomizer.
* @param args command line args
*/

   public static void main(String[] args) {
      try {
         DenseBeanSalad salad = new DenseBeanSalad();
         System.out.println(salad.toString());
      }
      
      catch (IOException e) {
         e.printStackTrace();
      }
   }

}