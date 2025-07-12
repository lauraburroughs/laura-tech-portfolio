import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* @author Laura Burroughs
* @version 2 July 2025
*/

public class WordSearchGUI {
   public static void main(String[] args) {
      // Create the game
      MyWordSearchGame game = new MyWordSearchGame();
   
      // Load the lexicon
      try {
         game.loadLexicon("wordfiles/words_medium.txt");
      } catch (IllegalArgumentException e) {
         System.out.println("Error loading lexicon: " + e.getMessage());
         return;
      }
   
      // Set a small test board (2x2: C A T S)
      String[] board = new String[] {"C", "A", "T", "S"};
      game.setBoard(board);
   
      // Launch GUI
      JFrame frame = new JFrame("Word Search Game");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(400, 200);
   
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(4, 1));
   
      JLabel boardLabel = new JLabel("<html><pre>" + game.getBoard() + "</pre></html>");
      JTextField inputField = new JTextField();
      JButton checkButton = new JButton("Check Word");
      JLabel resultLabel = new JLabel("");
   
      checkButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String input = inputField.getText().toUpperCase().trim();
               if (input.isEmpty()) {
                  resultLabel.setText("Please enter a word.");
                  return;
               }
               if (!game.isValidWord(input)) {
                  resultLabel.setText("Not in lexicon.");
               } else if (!game.isOnBoard(input).isEmpty()) {
                  resultLabel.setText("✓ Word is on the board!");
               } else {
                  resultLabel.setText("✗ Word is not on the board.");
               }
            }
         });
   
      panel.add(boardLabel);
      panel.add(inputField);
      panel.add(checkButton);
      panel.add(resultLabel);
   
      frame.add(panel);
      frame.setVisible(true);
   }
}
