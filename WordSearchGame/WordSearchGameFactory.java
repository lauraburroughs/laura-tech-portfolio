
/**
 * Provides a factory method for creating word search games. 
 * M5 Assignment
 * Returns an instance of MY CLASS
 * @author Laura Burroughs
 * @version 24 June 2025
 */
  
public class WordSearchGameFactory {

   /**
    * Returns an instance of a class that implements the WordSearchGame
    * interface.
    */
   public static WordSearchGame createGame() {
      // You must return an instance of your solution class here.
      
      return new MyWordSearchGame();
      
   }

}