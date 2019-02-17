   //Medarametla
   //to use the comparator interface and compare player objects by name
   import java.util.Comparator;
    public class ByName implements Comparator<Player>
   {
       public int compare(Player arg1, Player arg2)
      {
         return arg2.getName().compareTo(arg1.getName());      //it think this is the right way, ensuring that names are sorted alphabetically
      }
   }