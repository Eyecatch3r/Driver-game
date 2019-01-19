package game.Pong;
import game.Akteur;
import game.Welt;
import game.Pong.driverWorld;
import game.Bild;
import game.Imageloader;
/**
 * Write a description of class LaneLine here.
 *
 * @author Aldo Costa
 * @version 1.01
 */
public class LaneLine extends Akteur
{
    public int maxLanes;
    public Bild pic;
    /**
     * Constructor for objects of class LaneLine
     */
    public LaneLine(Welt pWelt, double pX, double pY, Bild pBild,Kantenverhalten pKantenverhalten)
    {
        super(pWelt,pX,pY,pBild,pKantenverhalten);
        pic = pBild;
        maxLanes = 1;
        this.setzeZeichneForm(false);
    }
    
    public void agiere()
    {
        switch(gibWelt().getStatus())
        {
            case START:
            this.setzeY(gibY()+2+((driverWorld)gibWelt()).getDifficulty());
            // if(gibY() == this.gibWelt().gibHoehe()*0.3 && maxLanes <= 4)
            // {
                // pic.setzeBufferedImage(Imageloader.loadImage("res/Driver/Img/LaneLine.png"));
                // LaneLine ll5 = new LaneLine(gibWelt(), this.gibX(),-128,pic, Kantenverhalten.EBENE);
                // gibWelt().akteurEinfuegen(ll5);
                // maxLanes++;
                // ll5.setMaxLanes(maxLanes+1);
            // }

            if(gibY() >= this.gibWelt().gibHoehe()+128)
            {
                this.setzeY(0);
            }
            
            break;
            case GAME:
            this.setzeY(gibY()+2+((driverWorld)gibWelt()).getDifficulty());
            // if(gibY() == this.gibWelt().gibHoehe()*0.3 && maxLanes <= 4)
            // {
                // pic.setzeBufferedImage(Imageloader.loadImage("res/Driver/Img/LaneLine.png"));
                // LaneLine ll5 = new LaneLine(gibWelt(), this.gibX(), 128,pic,  Kantenverhalten.EBENE);
                // gibWelt().akteurEinfuegen(ll5);
                // maxLanes++;
                // ll5.setMaxLanes(maxLanes+1);
            // }

            if(gibY() >= this.gibWelt().gibHoehe()+128)
            {
                this.setzeY(0);
            }
            break;
        }
        
    }
    
    public int getMaxLanes()
    {
        return maxLanes;
    }

    public void setMaxLanes(int pMaxLanes)
    {
        maxLanes = pMaxLanes;
    }

}
