import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
* Dense Bean Salad randomizer.
* @author Laura Burroughs
* @version 21 April 2025
*/

public class DenseBeanSalad {


// Instance Variables
   private ArrayList<String> vegetables;
   private ArrayList<String> bean;
   private ArrayList<String> protein;
   private ArrayList<String> sauce;
   private ArrayList<String> extra;


/**
* Constructor.
* @throws IOException for file not found
*/
   public DenseBeanSalad() 
      throws IOException {
      this.vegetables = loadListFromFile("Vegetables.txt");
      Collections.shuffle(this.vegetables);
      this.bean = loadListFromFile("Beans.txt");
      Collections.shuffle(this.bean);
      this.protein = loadListFromFile("Proteins.txt");
      Collections.shuffle(this.protein);
      this.sauce = loadListFromFile("Sauces.txt");
      Collections.shuffle(this.sauce);
      this.extra = loadListFromFile("Extra Additions.txt");
      Collections.shuffle(this.extra);
   
   }


/**
* Load an ingredient list file.
* @param fileName name of the file
* @return list from file
* @throws IOException for file not found
*/
   public static ArrayList<String> loadListFromFile(String fileName) 
      throws IOException {
   
      ArrayList<String> list = new ArrayList<>();
      
      try (BufferedReader reader = new BufferedReader(new 
               FileReader(fileName))) {
         String line;
         while ((line = reader.readLine()) != null) {
            list.add(line.trim());
         }
      } 
      return list;
   }


/**
* Display info.
* @return output salad info
*/
   public String toString() {
   
   // set up objects
      String output = "";
      Random random = new Random();
      
   // print information
      output += ("Get ready for a healthy, delicious meal!\n");
      output += ("Your Dense Bean Salad of the day is...\n");
      output += ("Vegetable: " + vegetables.get(0) + "\n");
      output += ("Vegetable: " + vegetables.get(1) + "\n");
      output += ("Vegetable: " + vegetables.get(2) + "\n");
      output += ("Bean: " + bean.get(0) + "\n");
      output += ("Protein: " + protein.get(0) + "\n");
      output += ("Sauce: " + sauce.get(0) + "\n");
      output += ("Extra Addition: " + extra.get(0) + "\n");
      
      return output;
   }

}