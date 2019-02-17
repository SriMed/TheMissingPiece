
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreboardPic extends JPanel
{
   private JLabel run, high, g, level;
        
   public ScoreboardPic()
   {
      setLayout(new FlowLayout());
   		
      setBackground(Color.white);
   
      run = new JLabel("Your Run: 0");
      add(run);
   		
      high = new JLabel("Your High: 0");
      add(high); 
   	
      level = new JLabel("Level: 1");
      add(level);
      
      g = new JLabel("Num of Guesses: 5");
   }
   public void update(boolean gana, Player p)//levels 1-10
   {
      if(gana == true)                                   //if you won
      {
         p.winnerHigh();                            //add 10 points
         p.updateRun();
         System.out.println("You have gained 10 points.");
      }
      else                                         //if you lost
      {
         p.loserHigh();
         p.setRun(0);                           //setting current to zero
         System.out.println("You have lost 5 points.");
      }
      run.setText("Your Run: " + p.getRun());
      high.setText("Your High: " + p.getHigh());
   }
   public void update(boolean gana, Player p, int guess)    //levels 11-20
   {
      if(gana == true)                                   //if you won
      {
         p.updateHigh(10 * guess);                            //depending on the level, update points accordingly
         p.updateRun();
         System.out.println("You have gained " + (10 * guess) + " points.");
      }
      else                                         //if you lost
      {
         p.updateHigh(-10 * (5-guess));
         p.setRun(0);                           //setting current to zero if you lost
         System.out.println("You have lost " + (10 * guess) + " points.");
      }
      run.setText("Your Run: " + p.getRun());
      high.setText("Your High: " + p.getHigh());
      g.setText("Num of Guesses: " + guess);           //update the number of guesses
   }
   public void update(int guess, int lev)
   {
      if(lev == 9)
      {
         add(g);
      }
      g.setText("Num of Guesses: " + guess);
      level.setText("Level: " + (lev + 1));
   }
   public void removeGuessLabel()
   {
      remove(g);
   }
   public void set(int running, double hi, int lev)
   {
      run.setText("Your Run: " + running);
      high.setText("Your High: " + hi);
      level.setText("Level: " + lev);
   }
}