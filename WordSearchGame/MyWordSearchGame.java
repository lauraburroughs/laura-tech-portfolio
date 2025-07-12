import java.util.TreeSet;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;


/**
 * Implements WordSearchGame program. 
 * M5 Assignment
 * @author Laura Burroughs
 * @version 24 June 2025
 */
 
 //Stores and manages the lexicon
 //Stores and manages the board
 //Performs DFS/backtracking to search for and score words
 //Use a red black tree

 
   /**
   * Implements the WordSearchGame interface.
   */
   
public class MyWordSearchGame implements WordSearchGame {


   private TreeSet<String> lexicon; 
   private boolean lexiconLoaded = false; //no calls before it's loaded
   private String[][] board;
   private int boardSize;
   
 
 /**
 * Constructor.
 */
 
   public MyWordSearchGame() {
      lexicon = new TreeSet<>();
   }
   

   //////////////////////////
   /// PHASE ONE: LEXICON ///
   //////////////////////////
   
   
  /**
  * Loads the lexicon into a data structure for later use. 
  * 
  * @param fileName A string containing the name of the file to be opened.
  * @throws IllegalArgumentException if fileName is null
  * @throws IllegalArgumentException if fileName cannot be opened.
  */
     
   @Override
   public void loadLexicon(String fileName) {
   
      //exceptions
      if (fileName == null) {
         throw new IllegalArgumentException("Null file name!");
      }
         
      try {
         Scanner scanner = new Scanner(new File(fileName));
         while (scanner.hasNext()) {
            String word = scanner.next().toUpperCase();
            lexicon.add(word);
         }
         
         lexiconLoaded = true;
         scanner.close();
      }
      catch (FileNotFoundException e) {
         throw new IllegalArgumentException("File cannot be opened: " + fileName);
      }    
      
   }
   
   
   /**
   * Determines if the given word is in the lexicon.
   * 
   * @param wordToCheck The word to validate
   * @return true if wordToCheck appears in lexicon, false otherwise.
   * @throws IllegalArgumentException if wordToCheck is null.
   * @throws IllegalStateException if loadLexicon has not been called.
   */
   
   @Override
   public boolean isValidWord(String wordToCheck) {
   
      //exceptions
      if (wordToCheck == null) {
         throw new IllegalArgumentException("Word cannot be read!");
      }
      
      if (!lexiconLoaded) {
         throw new IllegalStateException("Lexicon is not loaded!");
      }
      
      wordToCheck = wordToCheck.toUpperCase();
      return lexicon.contains(wordToCheck);
   
   }


  /**
   * Determines if there is at least one word in the lexicon with the 
   * given prefix.
   * 
   * @param prefixToCheck The prefix to validate
   * @return true if prefixToCheck appears in lexicon, false otherwise.
   * @throws IllegalArgumentException if prefixToCheck is null.
   * @throws IllegalStateException if loadLexicon has not been called.
   */
   
   @Override
   public boolean isValidPrefix(String prefixToCheck) {
      
      //exceptions
      if (prefixToCheck == null) {
         throw new IllegalArgumentException("Prefix cannot be read!");
      }
      
      if (!lexiconLoaded) {
         throw new IllegalStateException("Lexicon is not loaded!");
      }
      
      prefixToCheck = prefixToCheck.toUpperCase();
      String ceilingWord = lexicon.ceiling(prefixToCheck);
      
      if (ceilingWord == null) {
         return false;
      }
         
      return ceilingWord.startsWith(prefixToCheck);
      
   }
 
 
 
  
   ////////////////////////
   /// PHASE TWO: BOARD ///
   ////////////////////////
 
  
  /**
   * Stores the incoming array of Strings in a data structure that will make
   * it convenient to find words.
   * 
   * @param letterArray This array of length N^2 stores the contents of the
   *     game board in row-major order. Thus, index 0 stores the contents of board
   *     position (0,0) and index length-1 stores the contents of board position
   *     (N-1,N-1). Note that the board must be square and that the strings inside
   *     may be longer than one character.
   * @throws IllegalArgumentException if letterArray is null, or is  not
   *     square.
   */
   
   @Override
   public void setBoard(String[] letterArray) {
   
      //exceptions
      if (letterArray == null) {
         throw new IllegalArgumentException("Array is null!");
      }
      
      int size = (int)Math.sqrt(letterArray.length);
   
      if (size * size != letterArray.length) {
         throw new IllegalArgumentException("Board must be square!");
      }
      
      boardSize = size;
      board = new String[size][size];
   
      for (int i = 0; i < letterArray.length; i++) {
         int row = i / size;
         int col = i % size;
         board[row][col] = letterArray[i].toUpperCase();
      }
      
   }


  /**
   * Creates a String representation of the board, suitable for printing to
   *   standard out. Note that this method can always be called since
   *   implementing classes should have a default board.
   */
   
   @Override
   public String getBoard() {
   
      StringBuilder sb = new StringBuilder();
   
      for (int row = 0; row < boardSize; row++) {
         for (int col = 0; col < boardSize; col++) {
            sb.append(board[row][col]);
            sb.append(" ");
         }
        
         sb.append("\n");
        
      }
         
      return sb.toString();
   
   }




   ////////////////////////////////////
   /// PHASE THREE: LOGIC/GAME PLAY ///
   ////////////////////////////////////

   /**
   * DFS private service method.
   *
   */
   private boolean dfs(int row, int col, String wordToCheck, 
      int indexInWord, boolean[][] visited, List<Integer>path) {
   
   //full word is found!
      if (indexInWord == wordToCheck.length()) {
         return true;
      }
      
   //out of bounds checks
      if (row < 0 || row >= board.length) {
         return false;
      }
   
      if (col < 0 || col >= board.length) {
         return false;
      }
      
      
   //cell already used
      if (visited[row][col]) {
         return false;
      }
      
   //check board cell letters against the right part of the word
      String cell = board[row][col];
      if (!wordToCheck.startsWith(cell, indexInWord)) {
         return false;
      }
       
         
      visited[row][col] = true;   
         
         
      //build the path
      int position = row * boardSize + col;
      path.add(position);
      
      
      //recurse down all the paths
      for (int dRow = -1; dRow <= 1; dRow++) {
         for (int dCol = -1; dCol <= 1; dCol++) {
            if (dRow == 0 && dCol == 0) {
               continue; // skip self
            }
            if (dfs(row + dRow, col + dCol, wordToCheck, indexInWord
               + cell.length(), visited, path)) {
               return true;
            }    
         }
      }
   
      //backtrack: unmark visited[row][col]
      //remove current position from path
      visited[row][col] = false;
      path.remove(path.size() - 1);
      return false;  
   }



  /**
   * Retrieves all scorable words on the game board, according to the stated game
   * rules.
   * 
   * @param minimumWordLength The minimum allowed length (i.e., number of
   *     characters) for any word found on the board.
   * @return java.util.SortedSet which contains all the words of minimum length
   *     found on the game board and in the lexicon.
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */
   
   @Override
   public SortedSet<String> getAllScorableWords(int minimumWordLength){
   
   //exceptions
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException("Word is too short!");
      }
      
      if (!lexiconLoaded) {
         throw new IllegalStateException("Lexicon did not load!");
      }
      
   //hold scorable words
      SortedSet<String> scorableWords = new TreeSet<>();
   
   //find those words
      for (String word : lexicon) {
         if (word.length() >= minimumWordLength) {
            if (!isOnBoard(word).isEmpty()) {
               scorableWords.add(word);
            }
         }
      }
   
      return scorableWords;
   
   }



/**
  * Computes the cummulative score for the scorable words in the given set.
  * To be scorable, a word must (1) have at least the minimum number of characters,
  * (2) be in the lexicon, and (3) be on the board. Each scorable word is
  * awarded one point for the minimum number of characters, and one point for 
  * each character beyond the minimum number.
  *
  * @param words The set of words that are to be scored.
  * @param minimumWordLength The minimum number of characters required per word
  * @return the cummulative score of all scorable words in the set
  * @throws IllegalArgumentException if minimumWordLength < 1
  * @throws IllegalStateException if loadLexicon has not been called.
  */  
  
   @Override
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength){
   
   //exceptions
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException("Word is too short!");
      }
      
      if (!lexiconLoaded) {
         throw new IllegalStateException("Lexicon did not load!");
      }
   
      //loop over words to calculate score
      int score = 0;
      
      for (String word : words) {
         if (word.length() > minimumWordLength) {
            if (lexicon.contains(word)) {
               if (!isOnBoard(word).isEmpty()) {
                  int wordScore = minimumWordLength + (word.length() - minimumWordLength);
                  score += wordScore;
               }
            }
         }
      }
   
      return score;
   
   }


  /**
   * Determines if the given word is in on the game board. If so, it returns
   * the path that makes up the word.
   * @param wordToCheck The word to validate
   * @return java.util.List containing java.lang.Integer objects with  the path
   *     that makes up the word on the game board. If word is not on the game
   *     board, return an empty list. Positions on the board are numbered from zero
   *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
   *     board, the upper left position is numbered 0 and the lower right position
   *     is numbered N^2 - 1.
   * @throws IllegalArgumentException if wordToCheck is null.
   * @throws IllegalStateException if loadLexicon has not been called.
   */
   
   @Override
   public List<Integer> isOnBoard(String wordToCheck) {
   
      //exceptions
      if (wordToCheck == null) {
         throw new IllegalArgumentException("Word is null!");
               
      }
      
      if (!lexiconLoaded) {
         throw new IllegalStateException("Lexicon did not load!");
      }
       
       //uppercase, will store path if found
      wordToCheck = wordToCheck.toUpperCase();
      List<Integer> resultPath = new ArrayList<>();
      
      
      //path
      for (int row = 0; row < board.length; row++) {
         for (int col = 0; col < board[0].length; col++) {
            String cell = board[row][col];
            
            if (wordToCheck.startsWith(cell)) {
               boolean[][] visited = new boolean[board.length][board[0].length];
               List<Integer> pathList = new ArrayList<>();     
                  
               if (dfs(row, col, wordToCheck, 0, visited, pathList)) {
                  return pathList;
               }
            }      
         }  
      }
       
      return new ArrayList<>();
      
   }
 
}