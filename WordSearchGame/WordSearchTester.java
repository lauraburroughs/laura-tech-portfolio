public class WordSearchTester {
   public static void main(String[] args) {
      MyWordSearchGame game = new MyWordSearchGame();
   
      // 1. Load the lexicon
      game.loadLexicon("wordfiles/words_medium.txt");
   
      // 2. Set a sample board
      String[] board = {
         "C", "A",
         "T", "S"
         };
      game.setBoard(board);
   
      // 3. Print the board
      System.out.println("Board:");
      System.out.println(game.getBoard());
   
      // 4. Try checking a word
      String testWord = "CAT";
      System.out.println("Is '" + testWord + "' valid? " + game.isValidWord(testWord));
      System.out.println("Is '" + testWord + "' on board? " + !game.isOnBoard(testWord).isEmpty());
      System.out.println("Path: " + game.isOnBoard(testWord));
   
      // 5. Get all scorable words
      System.out.println("All scorable words (min 3 letters):");
      System.out.println(game.getAllScorableWords(3));
   
      // 6. Score them
      System.out.println("Total score: " + game.getScoreForWords(game.getAllScorableWords(3), 3));
   }
}
