package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import javax.swing.JFrame;


/**
 * Die Klasse Programmfenster zeigt ein Objekt vom Datentyp Welt an.
 * Sie steuert die FPS-Rate und ruft die loop-Methode des Welt-Objektes kontinuierlich auf.
 * 
 * @autor Gerd Heischkamp
 */
public class Programmfenster extends JFrame implements Runnable
{
    // Attribute
    private Bedienelemente bedienelemente;
    private Welt aktuelleWelt;
    private double lastTime = 0.0, fps = 0;
    private int sleepTime = 40;
    private final int MIN_SLEEP_TIME = 10, MAX_SLEEP_TIME = 200, TARGET_FPS = 900;
    private long time;

    /**
     * Erzeugt eine Instanz der Klasse Programmfenster.
     */
    public static void main(String[] args)
    {
        Programmfenster frame = new Programmfenster();
    }

    /**
     * Konstruktor der Klasse Programmfenster. Das Fensterlayout wird aufgebaut und die Loop-Schleife des Welt-Objektes wird gestartet.
     */
    public Programmfenster()
    {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(String.format("FPS: %4.2f SleepTime %2d",fps, sleepTime));
        bedienelemente = new Bedienelemente();
        this.addKeyListener(bedienelemente);
        this.setLayout(new BorderLayout());
        //aktuelleWelt = new game.asteroidgame.AsteroidWelt();
        aktuelleWelt = new game.Pong.driverWorld(800,800,Color.GRAY);
        this.add(aktuelleWelt, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);  
        this.startAnimation();
    }
    
    /**
     * Ersetzt das aktuelle Welt-Objekt des Programmfensters durch das uebergebene Welt-Objekt.
     * 
     * @param pWelt Neues Welt-Objekt
     */
    public void setzeWelt(Welt pWelt)
    {
        this.remove(aktuelleWelt);
        aktuelleWelt = pWelt;
        this.add(aktuelleWelt, BorderLayout.CENTER);
        this.revalidate();
        this.pack();
    }
    
    /**
     * Startet die Animation, die loop-Schleife beginnt.
     */
    private void startAnimation()
    {
        lastTime = System.nanoTime();
        time = System.currentTimeMillis();
        Thread th = new Thread(this);
        th.start();
    }

    /**
     * Die loop-Schleife wird nebenlaeufig ausgefuehrt.
     */
    public void run()
    {
        while (true) {
            controlFPS();

            aktuelleWelt.loop();
            aktuelleWelt.repaint();

            delay(sleepTime);
        }
    }

    
    /**
     * Verzoegert die loop-Schleife des Programmfensters. Nebenlaeufige Threads bekommen Rechenzeit.
     * 
     * @param pSecInMillis Zeit in Millisekunden
     */
    private void delay(long pSecInMillis)
    {
        try {
            Thread.sleep(pSecInMillis);
        } catch (InterruptedException e) {
            //nichts
        }
    }

    /**
     * Reguliert die Verzoegerungszeit innerhalb der loop-Schleife, so dass die FPS-Rate optimal ist.
     */
    private void controlFPS()
    {
        fps = -1000000000.0 / (lastTime - (lastTime = System.nanoTime()));
        if(fps > TARGET_FPS) sleepTime++; else sleepTime--;
        sleepTime = (sleepTime < MIN_SLEEP_TIME) ? MIN_SLEEP_TIME : (sleepTime > MAX_SLEEP_TIME) ? MAX_SLEEP_TIME : sleepTime;
        if(System.currentTimeMillis() - time > 1000)
        {
            this.setTitle(String.format("FPS: %4.2f SleepTime %2d",fps, sleepTime));
            time = System.currentTimeMillis();
        }
    }
}