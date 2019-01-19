package game.Pong;

import game.Akteur;
import game.Welt;
import game.Pong.driverWorld;
import game.Bedienelemente;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;
import game.Bild;
import game.Welt.Status;
/**
 * Write a description of class EnemyDriver here.
 *
 * @author Aldo Costa
 * @version 1.01
 */
public class EnemyDriver extends Akteur
{
    private int Lane;
    /**
     * Constructor for objects of class EnemyDriver
     */
    public EnemyDriver(Welt pWelt, double pX, double pY, Bild pBild, Kantenverhalten pKantenverhalten, int pLane)
    {
        super(pWelt,pX,pY,pBild,pKantenverhalten, pLane);
        Lane = pLane;
        this.setzeZeichneForm(false);
    }

    public void agiere()
    {
        switch(gibWelt().getStatus())
        {
            case GAME:
            this.setzeY(gibY()+4+((driverWorld)gibWelt()).getDifficulty());
            if(gibY() >= this.gibWelt().gibHoehe()+100)
            {
                this.gibWelt().akteurEntfernen(this);
                switch(gibWelt().getStatus())
                {
                    case GAME:

                    ((driverWorld)gibWelt()).setScore(((driverWorld)gibWelt()).getScore()+1);
                    break;
                }

            }
            break;
            
            case START:
            this.setzeY(gibY()+4+((driverWorld)gibWelt()).getDifficulty());
            if(gibY() >= this.gibWelt().gibHoehe()+100)
            {
                this.gibWelt().akteurEntfernen(this);
                switch(gibWelt().getStatus())
                {
                    case GAME:

                    ((driverWorld)gibWelt()).setScore(((driverWorld)gibWelt()).getScore()+1);
                    break;
                }

            }
            break;
            
            case TRANSITION:
            
            break;
        }
    }

    public int getLane() {
        return this.Lane;
    }

    public void setLane(int pLane) {
        this.Lane = pLane;
    }
}
