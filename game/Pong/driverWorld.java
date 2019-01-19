package game.Pong;

import game.Welt;
import game.Akteur;
import java.awt.Color;
import game.Akteur.Kantenverhalten;
import game.Bedienelemente;
import java.awt.event.KeyEvent;

import java.awt.Graphics;
import java.util.Random;
import java.lang.Math;
import java.util.Timer;
import abiturklassen.datenstrukturklassen.linear.List;
import java.lang.Class;
import java.awt.Image;
import java.awt.image.BufferedImage;
import game.Imageloader;
import game.Bild;
import game.Musik;
/**
 * Beschreiben Sie hier die Klasse PongWorld.
 * 
 * @author Aldo Costa 
 * @version 1.01
 */
public class driverWorld extends Welt
{
    private boolean isInitialized,isPlaying;
    private Status aktuellerZustand;
    private int timer,timer2, difficulty,freq;
    private int prevLane;
    private boolean gameOver;
    private BufferedImage pic1 = Imageloader.loadImage("res/Driver/Img/Black_viper.png"),
    pic2 = Imageloader.loadImage("res/Driver/Img/Car.png"),pic3 = Imageloader.loadImage("res/Driver/Img/Mini_Truck.png"),
    pic4 = Imageloader.loadImage("res/Driver/Img/Mini_Van.png"), pic5 = Imageloader.loadImage("res/Driver/Img/LaneLine.png"),
    pic6 = Imageloader.loadImage("res/Driver/Img/road.jpg");
    public driverWorld(int pWidth,int pHeight,Color pColor)
    {
        super(pWidth,pHeight,pColor);
        setStatus(Status.START);
        aktuellerZustand = getStatus();
        isPlaying = false;
        initialisiereWelt();
        isInitialized = false;
        
        difficulty = 0;
        prevLane = 0;
        gameOver = false;
        freq = 1;
        Musik.geraeuschHinzufuegen("gameover", "res/Driver/sounds/gameover.wav");
        Musik.geraeuschHinzufuegen("theme", "res/Driver/sounds/theme.wav");
        Musik.geraeuschHinzufuegen("gameover2", "res/Driver/sounds/gm.wav");
    }

    @Override
    public void agiere()
    {
        switch(getStatus())
        {
            case START:
            Musik.geraeuschStoppen("gameover2");
            if(!Musik.isRunning("theme"))
            {
                
                Musik.geraeuschAbspielen("theme");
                
            }
            timer++;
            timer2++;
            if(timer >= 50 && !gameOver)
            {
                Spawncars();
                timer = 0;
            }   

            break;
            case GAME:
            if(!Musik.isRunning("theme"))
            {
                Musik.geraeuschAbspielen("theme");
            }
            timer++;
            timer2++;
            if(timer >= 150/freq && !gameOver)
            {
                Spawncars();
                timer = 0;
            }   

            if(timer2 >= 500 && difficulty <= 4)
            {
                difficulty =  difficulty+1;
                if(freq <= 5)
                {
                    freq = freq+1;
                }
                timer2 = 0;
            }

            if(gameOver == true)
            {
                setStatus(Status.GAME_OVER);
            }
            break;
            case TRANSITION:
            Musik.geraeuschStoppen("theme");
            if(!Musik.isRunning("gameover"))
            {
                
                Musik.geraeuschAbspielen("gameover");
                
            }
            
            break;
            case GAME_OVER:
            if(!Musik.isRunning("gameover2"))
            {
                
                Musik.geraeuschAbspielen("gameover2");
                Musik.setVolume(0.5f, Musik.getClip("gameover2"));
                isPlaying = true;
            }
            
            difficulty = 0;
            timer = 0;
            timer2 = 0;
            this.setGameOver(false);
            break;

        }

    }

    /**
     * Diese Methode Ezeugt gegnerische Autos die nach Zufallsprinzip
     * auf den verschiedenen Lanes erzeugt werden. Ausserdem kontrolliert diese Methode, ob ein Auto auf der Lane gespawnt ist, ist 
     * dies der Fall wird das Spawnen weiterer Autos in dieser Lane verhindert
     */    public void Spawncars()
    {
        Random R = new Random();

        if(Math.random() < 0.5){

            int lane = R.nextInt(4);

            int picR = R.nextInt(4);
            Bild pic = new Bild("res/Driver/Img/Audi.png");

            switch(picR)
            {
                case 0:

                pic.setzeBufferedImage(pic1);
                break;

                case 1:
                pic.setzeBufferedImage(pic2);
                break;

                case 2:
                pic.setzeBufferedImage(pic3);
                break;

                case 3:
                pic.setzeBufferedImage(pic4);
                break;
            }

            while(lane == 0)
            {
                lane = R.nextInt(5);
            }

            EnemyDriver E = new EnemyDriver(this, 0, -100,pic, Kantenverhalten.EBENE, lane);

            switch(lane)
            {

                case 1:
                E.setzeX(this.getWidth()*0.125);
                break;
                case 2:
                E.setzeX(this.getWidth()*0.3750);
                break;
                case 3:
                E.setzeX(this.getWidth()*0.625);
                break;
                case 4:
                E.setzeX(this.getWidth()*0.875);
                break;

            }

            while(prevLane != lane)
            {
                this.akteurEinfuegen(E);
                prevLane = lane;
            } 
        }

    }

    /**
     * diese Methode iteriert eine (kopierte) Liste der Akteure durch,und sammelt all diejenigen EnemyDriver Objekte, die
     * sich in der pLane befinden (ineffizient)
     */
    public List<EnemyDriver> gibAutosAufLane(int pLane)
    {

        List<EnemyDriver> neueListe = new List<EnemyDriver>();
        List<Akteur> A = gibAkteure(EnemyDriver.class);
        if(!A.isEmpty())
        {
            A.toFirst();
            Akteur current = A.getContent();

            while(A.hasAccess())
            {
                if(((EnemyDriver)current).getLane() == pLane)
                {
                    neueListe.append(((EnemyDriver)current));
                    A.next();
                }
                else
                {
                    A.next();
                }
            }
        }
        else
        {
            return neueListe;
        }
        return neueListe;

    }

    public int getDifficulty()
    {
        return difficulty;
    }

    public void setdifficulty(int pdifficulty)
    {
        difficulty = pdifficulty;
    }

    public boolean getGameOver()
    {
        return gameOver;
    }

    public void setGameOver(boolean pGameOver)
    {
        gameOver = pGameOver;
    }

    public void initialisiereWelt()
    {
        if(this.getStatus() != null)
        {
            switch(this.getStatus())
            {

                case START:
                Bild p1 = new Bild("res/Driver/Img/Audi.png");
                p1.setzeBufferedImage(pic5);

                LaneLine ll1 = new LaneLine(this, 0, 0,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll1);
                LaneLine ll2 = new LaneLine(this, this.gibBreite()*0.25, 0,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll2);
                LaneLine ll3 = new LaneLine(this, this.gibBreite()*0.5, 0,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll3);
                LaneLine ll4 = new LaneLine(this, this.gibBreite()*0.75, 0,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll4);
                LaneLine ll5 = new LaneLine(this, this.gibBreite(), 0,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll5);

                LaneLine ll6 = new LaneLine(this, 0, 0-this.getHeight()*0.3,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll6);
                LaneLine ll7 = new LaneLine(this, this.gibBreite()*0.25, 0-this.getHeight()*0.3,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll7);
                LaneLine ll8 = new LaneLine(this, this.gibBreite()*0.5, 0-this.getHeight()*0.3,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll8);
                LaneLine ll9 = new LaneLine(this, this.gibBreite()*0.75, 0-this.getHeight()*0.3,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll9);
                LaneLine ll10 = new LaneLine(this, this.gibBreite(), 0-this.getHeight()*0.3,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll10);

                LaneLine ll11 = new LaneLine(this, 0, 0-this.getHeight()*0.6,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll11);
                LaneLine ll12 = new LaneLine(this, this.gibBreite()*0.25, 0-this.getHeight()*0.6,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll12);
                LaneLine ll13 = new LaneLine(this, this.gibBreite()*0.5, 0-this.getHeight()*0.6,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll13);
                LaneLine ll14 = new LaneLine(this, this.gibBreite()*0.75, 0-this.getHeight()*0.6,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll14);
                LaneLine ll15 = new LaneLine(this, this.gibBreite(), 0-this.getHeight()*0.6,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll15);

                LaneLine ll16 = new LaneLine(this, 0, 0-this.getHeight()*0.9,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll16);
                LaneLine ll17 = new LaneLine(this, this.gibBreite()*0.25, 0-this.getHeight()*0.9,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll17);
                LaneLine ll18 = new LaneLine(this, this.gibBreite()*0.5, 0-this.getHeight()*0.9,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll18);
                LaneLine ll19 = new LaneLine(this, this.gibBreite()*0.75, 0-this.getHeight()*0.9,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll19);
                LaneLine ll20 = new LaneLine(this, this.gibBreite(), 0-this.getHeight()*0.9,p1,  Kantenverhalten.EBENE);
                this.akteurEinfuegen(ll20);

                break;
                case GAME:
                Driver player;

                Bild p2 = new Bild("res/Driver/Img/Audi.png");
                p2.setzeBufferedImage(Imageloader.loadImage("res/Driver/Img/Audi.png"));
                player = new Driver(this, this.gibBreite()*0.125, this.gibHoehe()+64,p2,Kantenverhalten.EBENE, 0);
                this.akteurEinfuegen(player);
                break;
            }
        }
        else
        {
            this.setStatus(Status.START);
        }
    }

}
