   //Medarametla
   //compare Players by their high score

   import java.util.Comparator;
    public class ByHigh implements Comparator<Player>
   {
       public int compare(Player arg1, Player arg2)
      {
         return (int)(arg1.getHigh() - arg2.getHigh());  //sorts by the player's high score
      }
   }