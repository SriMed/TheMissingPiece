//Medarametla
//Layout to store all the Level objects and their private fields such as PixelLocations and AnswerChoices
public class MPLevels implements Level
{
   private String myName;
   private String[] answerChoices; private int[][] hidePixels = new int[2][4]; int rightAnswer = 0;
   public MPLevels(String _name, int[][] _hide)                                  //constructor for all levels
   {
      myName = _name;
      for(int r = 0; r < hidePixels.length; r++)
      {
         for(int c = 0; c < hidePixels[0].length; c++)
         {
            hidePixels[r][c] = _hide[r][c];
         }
      }
   }
   public String getName()
   {
      return myName;
   }
   public void addAnswerChoices(String[] _answerC)                               //for levels 1-10, add the answerChoices
   {
      answerChoices = new String[4];
      for(int k = 0; k < answerChoices.length; k++)
      {
         answerChoices[k] = _answerC[k];
      }
   }
   public void setAnswerChoice(String temp, int k)
   {
      answerChoices[k] = temp;
   }
   public String[] getAnswerChoice()
   {
      return answerChoices;
   }
   public String getAnswerChoice(int index)
   {
      return answerChoices[index];
   }
   public int[][] getPixelLocations()
   {
      return hidePixels;
   }
   public int getPixelCall(int r, int c)
   {
      return hidePixels[r][c];
   }
   public int getNumOfLevels()
   {
   return 20;
   }
   public void setRightAnswer(int k)
   {
   rightAnswer = k;
   }
   public int getRightAnswer()
   {
   return rightAnswer;
   }
}