
   //to run the game, "The Missing Piece"
   import javax.swing.JFrame;
   public class DriverPic
   {
      public static void main(String[] args)
      {
         JFrame f = new JFrame("The Missing Piece!");
         f.setSize(1000,800);
         f.setLocation(100,50);
         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         f.setContentPane(new PanelPic());
         f.setVisible(true);
      }
   }