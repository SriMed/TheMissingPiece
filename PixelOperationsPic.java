//
// Torbert, 24 July 2013
//
import java.awt.Color;
import java.awt.image.BufferedImage;
//
public class PixelOperationsPic
{
   public Color[][] getArray(BufferedImage img)    //defalt, regular image
   {
      Color[][] arr;
      
      int numcols = img.getWidth();
      int numrows = img.getHeight();
   	
      arr = new Color[numrows][numcols];
   	
      for(int j = 0; j < arr.length; j++)
      {
         for(int k = 0; k < arr[0].length; k++)
         {
            int rgb = img.getRGB(k,j);
            
            arr[j][k] = new Color(rgb);
         }
      }
   	//
      return arr;
   }
   public void setImage(BufferedImage img, Color[][] arr)      //resetting to original
   {
      for(int j = 0; j < arr.length; j++)
      {
         for(int k = 0; k < arr[0].length; k++)
         {
            Color tmp = arr[j][k];
            //
            int rgb = tmp.getRGB();
            //
            img.setRGB(k,j,rgb);
         }
      }
   }
	/**********************************************************************/

   public void hidePixels(Color[][] arr, int horiStart, int horiStop, int vertiStart, int vertiStop)     //hides at certain location, transferring from a user-assumed 400 by 400 grid
   {
      for(int j = (int)((arr.length*horiStart)/400); j < (int)((arr.length*horiStop)/400); j++)
      {
         for(int k = (int)((arr[0].length*vertiStart)/400); k < (int)((arr[0].length*vertiStop)/400); k++)
         {
            arr[j][k] = Color.BLACK;
         }
      }
   
   }
}