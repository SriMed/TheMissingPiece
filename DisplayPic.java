//Medarametla
//with all the images, shall have to read in the players' scores from the outputted data.txt
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

public class DisplayPic extends JPanel
{
   private PixelOperationsPic pix = new PixelOperationsPic();                             //PixelOperations, which hides the necessary part of the pixel

   private ImageIcon i = new ImageIcon("guess-the-picture.jpeg"); File images = new File("images");
   
   private File[] picturesList = images.listFiles();                                      //the list of images in the images for each level
   
   private BufferedImage img= new BufferedImage(1600,1200,BufferedImage.TYPE_INT_RGB);

   private Graphics buf = img.getGraphics();

   private int x, y;
   
   public DisplayPic()
   {
      int w = img.getWidth();
      int h = img.getHeight();
      buf.drawImage( i.getImage() , 0 , 0 , w , h , null );
   } 
   public int getXval()                                                                   // not getX !
   {
      return x;
   } 
   public int getYval()                                                                   // not getY !
   {
      return y;
   }  
   public int getRGB(int x, int y)
   {
      int xpos = x * img.getWidth( ) / getWidth() ;
      int ypos = y * img.getHeight() / getHeight();
      return img.getRGB(xpos,ypos);
   }      
   public void update(int xval, int yval)
   {
      x       = xval;
      y       = yval;
   } 
/**********************************************************************/
   public void blackPixels(int horiStart, int horiStop, int vertiStart, int vertiStop)                      //hides the appropriate pixels for each picture
   {
      Color[][] tmp = pix.getArray( img );
      pix.hidePixels( tmp , horiStart, horiStop, vertiStart, vertiStop);
      pix.setImage( img , tmp );
   }
/**********************************************************************/ 
   public void resetImage()                                                                                 //to be used to reveal pictures after guesses
   {
      Color[][] tmp = pix.getArray( img );      
      int w = img.getWidth();
      int h = img.getHeight();
   
      buf.drawImage( i.getImage() , 0 , 0 , w , h , null );                                                 //resets the image to the original
   }  
   public void setImage(int index)                                                                         //open the file at the specified index of the array
   {
   
      int w = img.getWidth();
      int h = img.getHeight();
   
      try
      {
         i = new ImageIcon("black.jpg");
      }
      catch(Exception e)
      {
         System.out.println("Sorry. Image could not be opened."); 
      }
      buf.drawImage( i.getImage() , 0 , 0 , w , h , null );       //to make sure white background
      try {
         i = new ImageIcon("" + picturesList[index]);
      }
      catch(Exception e)
      {
         System.out.println("Sorry. Image could not be opened."); 
      }
      buf.drawImage( i.getImage() , 0 , 0 , w , h , null );
   }    
   public void paintComponent(Graphics g)
   {
      g.drawImage( img , 0 , 0 , getWidth() , getHeight() , null );
   
   }
}