//to keep track of all the players of the game

public class Player 
{
   private String myName; private double myHigh; private int myRun;
   public Player()
   {
      myName = null;                               //to deal with instantiation errors later on
      myHigh = 0;
      myRun = 0;
   }
   public Player(String n)
   {
      myName = n;
      myHigh = 0;
      myRun = 0;
   }
   public Player(String n, double h, int r)
   {
      myName = n;
      myHigh = h;
      myRun = r;
   }
   public String getName()
   {
      return myName;
   }
   public double getHigh()
   {
      return myHigh;
   }
   public int getRun()
   {
      return myRun;
   }
   public void setName(String p)
   {
      myName = p;
   }
   public void setHigh(double h)
   {
      myHigh = h;
   }
   public void updateHigh(double u) //useful when updating the score, trying to subtract or add to current score
   {
      myHigh = myHigh + u;
   }
   public void winnerHigh()         //useful when simply adding 1 to the score
   {
      myHigh+=10;
   }
   public void loserHigh()
   {
      myHigh-=5;
   }
   public void setRun(int r)
   {
      myRun = r;
   }
   public void updateRun() //useful when updating the score, trying to add to total runs
   {
      myRun = myRun + 1;
   }
   public String toString()
   {
      return myName + ":" + myHigh+ ";" + myRun;
   }
   public void reset()
   {
   myRun = 0;
   myHigh = 0;
   }
}