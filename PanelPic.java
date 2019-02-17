//Medarametla & Pal
//stores everything
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.util.*;
import java.io.PrintStream;
import java.io.FileOutputStream;
public class PanelPic extends JPanel
{

   private DisplayPic display;                                          //have a default display, which shows the guessthepicture image
   private Player p1; CardLayout cl; JPanel switcher; LeaderboardPanel lider; 
   public PanelPic()
   {
      setLayout(new BorderLayout());                                    //PanelPic has a borderLayout type organization
      p1 = new Player();                                                //the user
   
      JPanel subpanel = new JPanel();                                   //essentially the returnToMainMenu panel, number 1
      subpanel.setLayout(new BorderLayout());
      
     // center, picture in display
      
      display = new DisplayPic();                                       
      display.setFocusable(true);
      subpanel.add(display, BorderLayout.CENTER);
   
   // main subpanel, w/ MM buttons
   
      JPanel main = new JPanel();                                      //the main will hold all the buttons, answer choices and instructions
      main.setLayout(new GridLayout(4,1));
   /******************************************************************  Instructions */
      JButton instruc = new JButton("Instructions");
      instruc.addActionListener(new InstrucListener());
      main.add(instruc);
   /******************************************************************  Leaderboard */
      JButton leaderboard = new JButton("Leaderboard");
      leaderboard.addActionListener(new LeaderListener());
      main.add(leaderboard); 
   /******************************************************************   Plays */
      JButton play = new JButton("Play");
      play.addActionListener(new PlayListener());
      main.add(play);  
   /******************************************************************   Quit */      
      JButton quit = new JButton("Quit");
      quit.addActionListener(new Listener_exit());
      main.add(quit);
   /********************************************************************/      
      subpanel.add(main, BorderLayout.SOUTH);                           //add the main panel to subpanel
            
   //Each content button calls a different panel   
      InstructionsPanel instruct = new InstructionsPanel();           //instantiating the other panels that CardLayout switches between
      lider = new LeaderboardPanel();
      PlayaPanel playa = new PlayaPanel();
      
      
   // south, holder of all buttons
      cl = new CardLayout();
     
      switcher = new JPanel();
      switcher.setLayout(cl);                                           //setting to CardLayout
   
      switcher.add(subpanel, "1");                                      //each different panel has a number
      switcher.add(instruct,"2");                           
      switcher.add(lider, "3");
      switcher.add(playa, "4");
      
      add(switcher, BorderLayout.CENTER);                               //switcher is essentially in the Panel
   }
   private void update(int x, int y)                                    //calls display's methods to update the pixels, called after every event
   {
      int rgb = display.getRGB(x,y);
      display.update(x,y);
      display.repaint();
      display.requestFocus();
   }
//LISTENERS
   private class InstrucListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         cl.show(switcher, "2");                                        //when someone clicks the instructions button, show the instructions panel
         update( display.getXval() , display.getYval() );
      }
   }
   private class LeaderListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      { 
         Player[] list = Leaderboard.getTop3();
         for(int k=0; k<3; k++)
         {
            lider.labels[k].setText("Rank: " + (k+1) + "." + list[k].toString());
         }
         try{                                                         //utilizes try/catch
            Leaderboard.displayAllLeaderboard("savedLider.txt");      //when the user ends game, leaderboard is lost...so print to this file to make sure the Leaderboard is available
         }
         catch(Exception ex)                                       
         {
            System.out.println("savedLider could not be saved");
         }
         cl.show(switcher, "3");
         update( display.getXval() , display.getYval() );
      }
   }
   private class PlayListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         String fielder = JOptionPane.showInputDialog("Have you played this game before? \n (Has your data been saved previously before in Leaderboard?) \n Warning: IF you wish to play under your old username (and thus type Yes to this question), your old data will be reset. Thus, choose carefully whether to play under a new user, or an old one. \n Type 'Yes' or 'No' \n Type -1 to return to Main Menu");
         if(fielder == null)
         {
            return;                                                  //no green messages if user hits "Cancel"
         }
         else if(fielder.equals("-1"))
         {
            cl.show(switcher, "1");                                  //that way user doesn't have to follow through and play
         }
         else if (fielder.equalsIgnoreCase("yes") || fielder.equalsIgnoreCase("no"))
         {
            String name = JOptionPane.showInputDialog("What is your name?");
            if(name == null)
            {
               return;                                                  //no green messages if user hits "Cancel"
            }
            else if(fielder.equalsIgnoreCase("yes"))
            {
               int index = Leaderboard.inList(name);
               if(index >= 0)
               {
                  p1 = Leaderboard.getPlayerAt(index);
                  p1.reset();
                  
                  JOptionPane.showMessageDialog(null, "If gameboard has not reset to 0 (level does not equal 1) from a previous player's game, please click reset, then you may start. Have fun playing The Missing Piece!!");
                  cl.show(switcher, "4");                                                             //shows the game
                  update( display.getXval() , display.getYval() );
               }
               else
               {
                  JOptionPane.showMessageDialog(null, "Sorry. No one with this name is registered in our leaderboard. Please try again if you wish to play.");
               }                                                    //ask the player for their name, and make a new player with that name
            }
            else if(fielder.equalsIgnoreCase("No"))
            {
               p1.setName(name);
               Leaderboard.add(p1);
               
               JOptionPane.showMessageDialog(null, "If gameboard has not reset to 0 (level does not equal 1) from a previous player's game, please click reset, then you may start. Have fun playing The Missing Piece!!");
               cl.show(switcher, "4");                                                             //shows the game
               update( display.getXval() , display.getYval() );
            }
         }
         else
         {
            JOptionPane.showMessageDialog(null, "Sorry.  You did not type a valid response to this question");
         }
      }
   }
   private class Listener_exit implements ActionListener                                     //gracefully ends the game
   {
      public void actionPerformed(ActionEvent e)
      {
         String yes = JOptionPane.showInputDialog("Do you really want to quit? Type 'Yes' or 'No'");
         if(yes == null )
         {
            return;
         }
         if(yes.equalsIgnoreCase("yes"))
         {
            System.exit(0);
         }
         else if(yes.equalsIgnoreCase("no"))
         {
            JOptionPane.showMessageDialog(null, "Okay. Your game will resume");
         }
         update( display.getXval() , display.getYval() );
      }
   }
//******************************************************   
   //************************************************
   class InstructionsPanel extends JPanel
   {
      public InstructionsPanel()
      {
         setLayout(new BorderLayout());
      
         Scanner infile = null;
         try
         {
            infile = new Scanner(new File("instructions.txt"));
         }
         catch(Exception e)
         {
            System.out.println("Sorry. Instructions file not found");
         }
         String numOfLines = infile.nextLine().trim();
         String[] instrucs = new String[Integer.parseInt(numOfLines)];  //for each line of intstructions.. 
         for(int k = 0; k < instrucs.length; k++)
         {
            instrucs[k] = infile.nextLine().trim();
         }      
         JPanel subpanel = new JPanel();
         subpanel.setLayout(new GridLayout(instrucs.length, 1));   
      
         JLabel[] labels = new JLabel[instrucs.length];               //another JLabel
         for(int k = 0; k < labels.length; k++)
         {
            labels[k] = new JLabel(instrucs[k]);   
            subpanel.add(labels[k]);
         }
      
         add(subpanel, BorderLayout.CENTER);
      
         JButton returnMM = new JButton("Return to Main Menu");
         returnMM.addActionListener(new Listener_returnMM());
         add(returnMM, BorderLayout.SOUTH);
      }
      private void update(int x, int y)                           //this method must be remade in every private class
      {
         int rgb = display.getRGB(x,y);
         display.update(x,y);
         display.repaint();
         display.requestFocus();
      }
      private class Listener_returnMM implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            cl.show(switcher, "1");                                //takes the user back to the main screen
            update( display.getXval() , display.getYval() );                                
         }
      }
   }
   class LeaderboardPanel extends JPanel
   {
      JLabel[] labels;
      public LeaderboardPanel()                                  
      {
         setLayout(new BorderLayout());
         Leaderboard.readAllLeaderboard("savedLider.txt");        //readIn the old Leaderboard    
         
         JPanel labelpanel = new JPanel();
         labelpanel.setLayout(new GridLayout(3, 1));
         
         labels = new JLabel[3];
         
         Player[] list = Leaderboard.getTop3();
         for(int k = 0; k < 3; k ++)
         {
            labels[k] = new JLabel(k + "." + list[k].toString());
            labels[k].setHorizontalAlignment(JLabel.CENTER);
            labelpanel.add(labels[k]);
         }
         add(labelpanel, BorderLayout.CENTER);                                     //adds the label and button to panel
         
         JPanel subpanel = new JPanel();
         
         JButton save = new JButton("Save");
         save.addActionListener(new SaveListener());
         subpanel.add(save);
         
         JButton returnMM = new JButton("Return to Main Menu");
         returnMM.addActionListener(new Listener_returnMM());
         subpanel.add(returnMM);
         
         add(subpanel, BorderLayout.SOUTH);
      }
      private void update(int x, int y)
      {
         int rgb = display.getRGB(x,y);
         display.update(x,y);
         display.repaint();
         display.requestFocus();
      }
      private class Listener_returnMM implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            cl.show(switcher, "1");                                                             //takes the user back to the main screen
            update( display.getXval() , display.getYval() );
         }
      }
      private class SaveListener implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            String yes = JOptionPane.showInputDialog("Do you wish to save this Leaderboard? Type 'Yes' if so, otherwise press any key.");
            if(yes == null )
            {
               return;
            }
            if(yes.equalsIgnoreCase("yes"))
            {
               try
               {
                  Leaderboard.displayTop3("top3.txt");
                  JOptionPane.showMessageDialog(null, "Your file has been saved as \"top3.txt\"");   
               }
               catch(Exception ex)
               {
                  JOptionPane.showMessageDialog(null, "Sorry. This leaderboard could not be saved.");
               }
            }
         }
      
      }
   }
   class PlayaPanel extends JPanel
   {
      private MPLevels[] levels = new MPLevels[20]; private int numOfG; private boolean guess;
      private JButton[] acs = new JButton[4]; JButton next; JButton check; private JTextField key;
      private JPanel subpanel; DisplayPic display;
      private int level;
      private CardLayout clt;
      private ScoreboardPic scoreboard;
      public PlayaPanel()
      {
         setLayout(new BorderLayout());
         levelData();                                                           //reads in all the levels         
         
         numOfG = 5;
         guess = false;
         level = 0;
         
         JPanel lvl110 = new JPanel();                                        //cardLayout, switches between the south panels depending on Level
         for(int k = 0; k < 4; k++)
         {
            acs[k] = new JButton(levels[level].getAnswerChoice(k));
            acs[k].addActionListener(new GuessButtonListener());
            lvl110.add(acs[k]);                                             //adding all the answerchoices to the subsubpanel
         }                                             
         JPanel lvl1120 = new JPanel();
         
         key = new JTextField("Type Answer Here", 20);
         check = new JButton("Check Answer."); check.addActionListener(new CheckListener());    //making level11 things
         lvl1120.add(key); lvl1120.add(check);
         
         clt = new CardLayout();
         subpanel = new JPanel();
         subpanel.setLayout(clt);
         
         subpanel.add(lvl110, "1");                                            
         subpanel.add(lvl1120, "2");
         add(subpanel, BorderLayout.SOUTH); 
         
         
         next = new JButton("Next"); 
         next.addActionListener(new NextButtonListener()); 
         add(next, BorderLayout.EAST); next.setEnabled(false);                                             //the next button
         
         display = new DisplayPic();
         add(display, BorderLayout.CENTER);
         
         display.setImage(level);
         for(int k = 0; k < 2; k ++)
         {
            display.blackPixels(levels[level].getPixelCall(k,0), levels[level].getPixelCall(k,1), levels[level].getPixelCall(k,2), levels[level].getPixelCall(k,3));   //hide the pixels at these locations
         }
         
         JPanel northpanel = new JPanel();
         
         scoreboard = new ScoreboardPic();
         northpanel.add(scoreboard);
         
         JButton returnMM = new JButton("Return to Main Menu");      //which returns to main menu, not necessarily resetting the gameboard
         returnMM.addActionListener(new Listener_returnMM());
         northpanel.add(returnMM);
         
         JButton reset = new JButton("Reset");                       //resets gameboard (everything to level 1, player stats reset to 0)
         reset.addActionListener(new Listener_reset());
         northpanel.add(reset);
         
         add(northpanel, BorderLayout.NORTH);
      }
      private void update(int x, int y)
      {
         int rgb = display.getRGB(x,y);
         display.update(x,y);
         display.repaint();
         display.requestFocus();
      }
      private class Listener_reset implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            String yes = JOptionPane.showInputDialog("Do you reallly want to reset? Your progress will likely be lost. Please type 'Yes' or 'No'");
            
            clt.show(subpanel, "1");
            if(yes == null )
            {
               return;
            }
            if(yes.equalsIgnoreCase("yes"))
            {
               if(level > 9)
               {
                  scoreboard.removeGuessLabel();
               }
               numOfG = 5;
               guess = false;
               level = 0;
               for(int k = 0; k < acs.length; k ++)
               {
                  acs[k].setText(levels[level].getAnswerChoice(k));    //setting the appropriate text of the JButtons
                  acs[k].setEnabled(true);
               }
               display.setImage(level);
               next.setEnabled(false);
               for(int k = 0; k < 2; k ++)
               {
                  display.blackPixels(levels[level].getPixelCall(k,0), levels[level].getPixelCall(k,1), levels[level].getPixelCall(k,2), levels[level].getPixelCall(k,3));   //hide the pixels at these locations
               }
               p1.reset();
               scoreboard.set(p1.getRun(), p1.getHigh(), level);                                                             //takes the user back to the main screen
               update( display.getXval() , display.getYval() );
            }
            else if(yes.equalsIgnoreCase("no"))
            {
               JOptionPane.showMessageDialog(null, "Okay, your game will resume.");
            }
         }
      }
   
      private class Listener_returnMM implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            
            String yes = JOptionPane.showInputDialog("Do you reallly want to return to main menu? Your progress will likely be saved. Please type 'Yes' or 'No'");
            if(yes == null )
            {
               return;
            }
            if(yes.equalsIgnoreCase("yes"))
            {
               cl.show(switcher, "1");                                                             //takes the user back to the main screen
               update( display.getXval() , display.getYval() );
            }
            else if(yes.equalsIgnoreCase("no"))
            {
               JOptionPane.showMessageDialog(null, "Okay, your game will resume.");
            }
         
         }
      }
      private class GuessButtonListener implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            JButton source = null;
            if(e.getSource()instanceof JButton)
            {
               source = (JButton)e.getSource();
            
            }
            if(source.getText().equals(levels[level].getName()))         //if the guess is right
            {
               guess = true;
               display.resetImage();                                          //shows the entire picture
            }
            else
            {
               guess = false;
            }                                        //call display methods and scoreboard methods
            for(int k = 0; k < acs.length; k ++)
            {
               acs[k].setEnabled(false);    //setting the appropriate text of the JButtons
            }
            scoreboard.update(guess, p1);
            next.setEnabled(true); 
            update( display.getXval() , display.getYval() );
         }
      }
      private class CheckListener implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {                                                      
            String presumed = key.getText();
            if(presumed.equalsIgnoreCase(levels[level].getName()))
            {
               guess = true;
               next.setEnabled(true);
               check.setEnabled(false);
               display.resetImage();
            }
            else
            {
               guess = false;
               if(numOfG == 0)
               {
                  String loser = "Oops. You have exceeded your number of guesses";
                  loser+= "\n You can no longer play";
                  loser+= "\n If you wish to play again, please reset and start over";
                  check.setEnabled(false);
                  JOptionPane.showMessageDialog(null, loser);
               }
            }
            numOfG--;
            scoreboard.update(guess, p1, numOfG);
            update( display.getXval() , display.getYval() );
         }
      }
      private class NextButtonListener implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            level++;
            if(level == 20)
            {
               String winner = "Thank you for playing.";
               winner+= "\n You have reached level 20 and completed the game!";
               winner+= "\n Please hit \"Reset\" to reset the gameboard if you want to play again,";
               winner+= "\n then hit Return to Main Menu.";
               winner+= "\n From there may play again, or check the leaderboard to see if you made the top 3!";
               winner+= "\n Don't forget the spread to word to friends about this amazing new game! The Missing Piece";
               JOptionPane.showMessageDialog(null, winner);
               check.setEnabled(false);
            }
            else
            {
               if(level < 10)
               {
                  for(int k = 0; k < 4; k ++)
                  {
                     acs[k].setText(levels[level].getAnswerChoice(k));    //setting the appropriate text of the JButtons
                     acs[k].setEnabled(true);
                  }
               }
               display.setImage(level);
               for(int k = 0; k < 2; k ++)
               {
                  display.blackPixels(levels[level].getPixelCall(k,0), levels[level].getPixelCall(k,1), levels[level].getPixelCall(k,2), levels[level].getPixelCall(k,3));   //hide the pixels at these locations
               }
               if(level == 10)
               {
                  clt.show(subpanel, "2");
                  JOptionPane.showMessageDialog(null, "From now on, please type your answer in the textfield and hit the \"Check\" button to check your answer. \n No spaces. You will have 5 guesses.");
               }
               key.setText("");                                              //when it gets to level 11, make sure to take out the buttons, and in place, add a textfield for the answerChoice and a button to check whether or not that answer is correct
               numOfG = 5; 
               check.setEnabled(true);                                    //reset number of guesses
            }
            next.setEnabled(false);
            scoreboard.update(numOfG, level);
            update( display.getXval() , display.getYval() );
         }
      }
      private void levelData()      //do it in terms of the picture length (i.e. length/2, instead of rote numbers....
      {
         Scanner ac = null; Scanner ph = null;
         try
         {
            ac = new Scanner(new File("answerC.txt"));
            ph = new Scanner(new File("pixelH.txt"));
         }
         catch(Exception e)
         {
            System.out.println("Sorry. Files could not be read in");
         }
         for(int k = 0; k < 20; k++)                           //reads in all the pixel data
         {
            String name = ph.next(); 
            int[][] pixels = new int[2][4];
            for(int r = 0; r < pixels.length; r++)
            {
               for(int c = 0; c < pixels[0].length; c++)
               {
                  pixels[r][c] = ph.nextInt();            //reads in the next 8 integers
               }
            }
            levels[k] = new MPLevels(name, pixels);
         }
         ph.close();
         for(int k = 0; k < 10; k++)
         {
            String[] answerC = new String[4];
            for(int a = 0; a < 4; a++)
            {
               answerC[a] = ac.nextLine().trim();
            }
            levels[k].addAnswerChoices(answerC);
            for(int p = 0; p < 4; p++)
            {
               int a = getRN();              //sorting the answer choices
               int b = getRN();
               if(a == 0)
               {
                  levels[k].setRightAnswer(b);
               }
               if(b == 0)
               {
                  levels[k].setRightAnswer(a);
               }
               String temp = levels[k].getAnswerChoice(a);
               levels[k].setAnswerChoice(levels[k].getAnswerChoice(b), a);
               levels[k].setAnswerChoice(temp, b);   
            }
         }
         ac.close();
      }
      public int getRN()
      {
         return (int)(Math.random() * 4);
      }
   }
}