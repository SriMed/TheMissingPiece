//Medarametla
//stores all the players of the game, utilizes the methods of ArrayList
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Comparator;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.*;
public class Leaderboard
{
   private static ArrayList<Player> list = new ArrayList<Player>();
   public static void add(Player p)
   {
      list.add(p);
   }
   public static void remove(Player p)
   {
      list.remove(p);
   }
   public static Player[] toArray()                                           //return an array of Players
   {
      return ((Player[])list.toArray());
   }
   public static void sort(Comparator c)                                      //when calling this method, pass in the comparator  that you want to sort by, either byHigh or byName
   {
      int maxPos;
      for(int k = 0; k < list.size(); k++)
      {
         maxPos = findMax(k, c);
         swap(maxPos, k);
      }
   }
   private static int findMax(int position, Comparator c)
   { 
      int max = position;
      for(int k = position; k < list.size(); k++)                            //so less than upper
      {
         if(c.compare(list.get(max), list.get(k)) < 0)                      //if min is greater, change the min
         {
            max = k;
         }
      }
      return max;
   }
   private static void swap(int a, int b)
   {
      Player temp = list.get(a);
      list.set(a, list.get(b));
      list.set(b, temp);
   
   }
   public static Player[] getTop3()
   {
      sort(new ByHigh());
      
      Object[] list2 = list.toArray();
      Player[] top3 = new Player[3];
      
      for(int k = 0; k < 3; k ++)
      {
         if(list2[k] instanceof Player)
            top3[k] = (Player)list2[k];
      }
      return top3;
   }
   public static void displayTop3(String filename) throws Exception                 //used to save
   {
      Object[] list2 = list.toArray();
      Player[] top3 = new Player[3];
      
      for(int k = 0; k < 3; k ++)
      {
         if(list2[k] instanceof Player)
            top3[k] = (Player)list2[k];
      }
      
      sort(new ByHigh());                                                           //sorts the list byHigh
      PrintStream outfile = new PrintStream(new FileOutputStream(filename));        //prints out the top 3 into the file top3.txt
      for(int k = 0; k < 3; k ++)
      {
         outfile.println((k + 1)+ ". " + top3[k].toString());
      }
      
   }
   public static void displayAllLeaderboard(String filename) throws Exception
   {
      Object[] list2 = list.toArray();
      Player[] all = new Player[list2.length];
      
      for(int k = 0; k < list2.length; k ++)
      {
         if(list2[k] instanceof Player)
            all[k] = (Player)list2[k];
      }
      
      sort(new ByHigh());                                                           //sorts the list byHigh
      PrintStream outfile = new PrintStream(new FileOutputStream(filename));        //prints out the top 3 into the file top3.txt
      outfile.println(all.length);
      for(int k = 0; k < all.length; k ++)
      {
         outfile.println(all[k].toString());
      }
      
   }
   public static void readAllLeaderboard(String filename)                         //reads players in, utilizing the file printed
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File(filename));
      }
      catch(Exception e)
      {
         System.out.println("Could not read from savedLider");
      }
      int numOfPlayers =  Integer.parseInt(infile.nextLine().trim());
      Player[] allPlayers = new Player[numOfPlayers];
      for(int k = 0; k < allPlayers.length; k ++)
      {
         String toBeParsed = infile.nextLine();
         String name = toBeParsed.substring(0, toBeParsed.indexOf(":"));
         double high = Double.parseDouble(toBeParsed.substring(toBeParsed.indexOf(":") + 1, toBeParsed.indexOf(";")));
         int run = Integer.parseInt(toBeParsed.substring(toBeParsed.indexOf(";") + 1, toBeParsed.length()));
         allPlayers[k] = new Player(name, high, run);
      }
      for(int k = 0; k < allPlayers.length; k++)
      {
         list.add(allPlayers[k]);
      }
   }
   public static int inList(String p)          //retuns true if a player with this name is in the list
   {
      Object[] list2 = list.toArray();
      Player[] all = new Player[list2.length];
      
      for(int k = 0; k < list2.length; k ++)
      {
         if(list2[k] instanceof Player)
            all[k] = (Player)list2[k];
      }
      
      for(int k = 0; k < all.length; k++)
      {
         if(all[k].getName().equalsIgnoreCase(p))
            return k;
      }
      return -1;
   
   }
   public static Player getPlayerAt(int index)
   {
      Object[] list2 = list.toArray();
      Player[] all = new Player[list2.length];
      
      for(int k = 0; k < list2.length; k ++)
      {
         if(list2[k] instanceof Player)
            all[k] = (Player)list2[k];
      }
      return all[index];
   
   }
}

