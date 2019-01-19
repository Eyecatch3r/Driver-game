package game.Pong;

import game.Akteur;
import game.Welt;
import java.util.Random;
import game.Bedienelemente;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;
import abiturklassen.datenstrukturklassen.linear.List;
import game.Pong.driverWorld;
import game.Welt.Status;
import game.Bild;
import game.Imageloader;
/**
 * Beschreiben Sie hier die Klasse Driver.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Driver extends Akteur
{
    private int lane,timer,imageTimer, t2 = 0;
    private boolean leftKeyPressed, rightKeyPressed;
    private double moveTimerRight, moveTimerLeft;
    private boolean KeyPressed, isAnimated = false;
    public enum Image
    {
        NORMAL,EXPLODE;
    }
    /**
     * Konstruktor für Objekte der Klasse Driver
     */
    public Driver(Welt pWelt, double pX, double pY, Bild pBild, Kantenverhalten pKantenverhalten, int pLane)
    {
        super(pWelt,pX,pY,pBild,pKantenverhalten, pLane);
        Bild pic = null;
        KeyPressed = false;
        this.setzeZeichneForm(false);
        setzeZeichenEbene(1);
    }

    public void agiere()
    {
        switch(gibWelt().getStatus())
        {
            case GAME:
            //Anfahranimation am anfang des Spiels
            if(!isAnimated)
            {
                timer++;
                setzeY(gibY()-1);
                if(timer == 128)
                {
                    timer = 0;
                    isAnimated = true;
                }
            }
            control();
            collision();
            break;
            case TRANSITION:
            //Animiert eine Crash Situation durch das ändern und Rotieren des Bildes
            Random r = new Random();
            imageTimer++;
            if(imageTimer <= 10)
            {
                if(r.nextInt(10) < 5)
                {
                    this.rotiere(this.gibRichtung()+0.1);
                }
                else
                {
                    this.rotiere(this.gibRichtung()-0.1);
                }
            }
            if(imageTimer >= 10)
            {
                this.setzeBild("res/Driver/Img/crash.png");
                t2++;
                if(t2 >= 30)
                {
                    gibWelt().setStatus(Status.GAME_OVER);
                }
            }
            break;
        }
    }

    /**
     * Diese Methode prüft, ob sich ein kollidierendes Objekt der klasse Enemydriver auf dem 
     * DriverObjekt befindet
     */
    public void collision()
    {
        boolean cl = this.gibKollidierendeObjekte(EnemyDriver.class).isEmpty() ? false : true;

        if(cl == true)
        {

            //System.out.print("Game Over!"+"\n"+"your Score: "+((driverWorld)gibWelt()).getScore()+"\n");
            ((driverWorld)gibWelt()).setStatus(Status.TRANSITION);

        }
    }

    /**
     * Diese Methode ist fuer die Steuerung des Driver Objekts zuständig, durch Globale Variablen eine sanfte Bewegung zu ermöglichen
     */
    public void control()
    {
        if(Bedienelemente.isKeyDown(KeyEvent.VK_LEFT) && lane > 0 && !leftKeyPressed || moveTimerLeft >= 1 && !leftKeyPressed )
        {

            leftKeyPressed = true;

            this.setzeX(this.gibX()-10);
            moveTimerLeft++;
            setzeRichtung(gibRichtung()+1);
            if(moveTimerLeft == this.gibWelt().getWidth()*0.25/10)
            {
                lane--;
                moveTimerLeft = 0;
                setzeRichtung(0);

            }   
        }
        else if(!Bedienelemente.isKeyDown(KeyEvent.VK_LEFT))
        {
            leftKeyPressed = false;
        }
        if(Bedienelemente.isKeyDown(KeyEvent.VK_RIGHT) && lane < 3 && !rightKeyPressed || moveTimerRight >= 1 && !rightKeyPressed )
        {
            rightKeyPressed = true;

            this.setzeX(this.gibX()+10);
            setzeRichtung(gibRichtung()-1);
            moveTimerRight++;
            if(moveTimerRight == this.gibWelt().getWidth()*0.25/10)
            {
                lane++;
                moveTimerRight = 0;
                setzeRichtung(0);
            }   
        }
        else if(!Bedienelemente.isKeyDown(KeyEvent.VK_RIGHT))
        {
            rightKeyPressed = false;
        }
    }

}
