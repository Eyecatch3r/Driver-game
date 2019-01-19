package game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import game.Bild;
/**
 * Write a description of class Imageloader here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Imageloader
{
    public static BufferedImage loadImage(String path)
    {
        try
        {
            
            return ImageIO.read(new File(path));
        }
        catch (IOException e)
        {
            
            return null;
        }
    }
}

