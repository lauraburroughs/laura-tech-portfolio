import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Set;
import java.util.Queue;
import java.util.Collections;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Laura Burroughs
 * @version 30 June 2025
 */
 
public class Doublets implements WordLadderGame {

   // The word list used to validate words.
   // Must be instantiated and populated in the constructor.
   /////////////////////////////////////////////////////////////////////////////
   // DECLARE A FIELD NAMED lexicon HERE. THIS FIELD IS USED TO STORE ALL THE //
   // WORDS IN THE WORD LIST. YOU CAN CREATE YOUR OWN COLLECTION FOR THIS     //
   // PURPOSE OF YOU CAN USE ONE OF THE JCF COLLECTIONS. SUGGESTED CHOICES    //
   // ARE TreeSet (a red-black tree) OR HashSet (a closed addressed hash      //
   // table with chaining).
   /////////////////////////////////////////////////////////////////////////////
   
   private HashSet<String> lexicon;


   /**
    * Instantiates a new instance of Doublets with the lexicon populated with
    * the strings in the provided InputStream. The InputStream can be formatted
    * in different ways as long as the first string on each line is a word to be
    * stored in the lexicon.
    */
   public Doublets(InputStream in) {
      try {
      
         //////////////////////////////////////
         // INSTANTIATE lexicon OBJECT HERE  //
         //////////////////////////////////////
         
         lexicon = new HashSet<>();
         
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next();
            
            /////////////////////////////////////////////////////////////
            // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
            /////////////////////////////////////////////////////////////
            
            lexicon.add(str.toLowerCase());
            
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }


   //////////////////////////////////////////////////////////////
   // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
   //////////////////////////////////////////////////////////////
   
   
   
    /**
    * Returns the total number of words in the current lexicon.
    *
    * @return number of words in the lexicon
    */
   public int getWordCount() {
      return lexicon.size();
   }


   /**
    * Checks to see if the given string is a word.
    *
    * @param  str the string to check
    * @return     true if str is a word, false otherwise
    */
   public boolean isWord(String str) {
   
      str = str.toLowerCase();
   
      if (lexicon.contains(str)) {
         return true;
      }
   
      return false;
   
   }


   /**
    * Returns the Hamming distance between two strings, str1 and str2. The
    * Hamming distance between two strings of equal length is defined as the
    * number of positions at which the corresponding symbols are different. The
    * Hamming distance is undefined if the strings have different length, and
    * this method returns -1 in that case. See the following link for
    * reference: https://en.wikipedia.org/wiki/Hamming_distance
    *
    * @param  str1 the first string
    * @param  str2 the second string
    * @return      the Hamming distance between str1 and str2 if they are the
    *                  same length, -1 otherwise
    */
   public int getHammingDistance(String str1, String str2){
   
      if (str1.length() != str2.length()) {
         return -1;
      }
      
      int counter = 0;
   
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            counter++;
         }
      }
      
      return counter;
   }


   /**
    * Returns all the words that have a Hamming distance of one relative to the
    * given word.
    *
    * @param  word the given word
    * @return      the neighbors of the given word
    */
   public List<String> getNeighbors(String word) {
      
      ArrayList<String> neighborsList = new ArrayList<>();
      
      for (String candidate : lexicon) {
         if (candidate.length() == word.length() && getHammingDistance(candidate, word) == 1) {
            neighborsList.add(candidate);
         }
      }
      return neighborsList;
   }


   /**
    * Checks to see if the given sequence of strings is a valid word ladder.
    *
    * @param  sequence the given sequence of strings
    * @return          true if the given sequence is a valid word ladder,
    *                       false otherwise
    */
   public boolean isWordLadder(List<String> sequence) {
   
      if (sequence == null || sequence.isEmpty()) {
         return false;
      }
      
      for (String word : sequence) {
         if (!lexicon.contains(word)) {
            return false;
         }
      }
         
      for (int i = 0; i < sequence.size() - 1; i++) {
         String word1 = sequence.get(i);
         String word2 = sequence.get(i + 1);
      
         if (getHammingDistance(word1, word2) != 1) {
            return false;
         }
      }
         
      return true;
      
   }


  /**
   * Returns a minimum-length word ladder from start to end. If multiple
   * minimum-length word ladders exist, no guarantee is made regarding which
   * one is returned. If no word ladder exists, this method returns an empty
   * list.
   *
   * Breadth-first search must be used in all implementing classes.
   *
   * @param  start  the starting word
   * @param  end    the ending word
   * @return        a minimum length word ladder from start to end
   */
   public List<String> getMinLadder(String start, String end) {
   
   
      if (!lexicon.contains(start) || !lexicon.contains(end)) {
         ArrayList<String> emptyList = new ArrayList<>();
         return emptyList;
      }
      
      if (start.length() != end.length()) {
         ArrayList<String> emptyList = new ArrayList<>();
         return emptyList;
      }
   
      if (start.equals(end)) {
         ArrayList<String> startList = new ArrayList<>();
         startList.add(start);
         return startList;
      }
      
      Set<String> visited = new HashSet<>();
      visited.add(start);
   
      Queue<Node> bfsNodes = new LinkedList<>();
      Node startNode = new Node(start, null);
      bfsNodes.add(startNode);
      
      
      while (!bfsNodes.isEmpty()) {
         Node currentNode = bfsNodes.remove();
         
         for (String neighborWord : getNeighbors(currentNode.word)) {
            if (!visited.contains(neighborWord)) {
               visited.add(neighborWord);
               Node neighborNode = new Node(neighborWord, currentNode);
               
               if (neighborWord.equals(end)) {
                  ArrayList<String> ladder = new ArrayList<>();
                  Node current = neighborNode;
                  while (current != null) {
                     ladder.add(current.word);
                     current = current.parent;
                  }
                  
                  Collections.reverse(ladder);
                  return ladder;
               }
               
               bfsNodes.add(neighborNode);
            }
         }
      }
   
      ArrayList<String> emptyList = new ArrayList<>();
      return emptyList;
   
   }


   private class Node {
      String word;
      Node parent;
      
      private Node (String word, Node parent) {
         this.word = word;
         this.parent = parent;
         
      }
   }

}

