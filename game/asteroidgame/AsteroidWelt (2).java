package game.asteroidgame;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import game.Bedienelemente;
import game.Programmfenster;
import game.Welt;
import game.Akteur.Kantenverhalten;

/**
 * Die Klasse AsteroidWelt verwaltet alle Akteur-Objekte, die im Asteroid-Spiel vorkommen.
 * 
 * @author Gerd Heischkamp
 */
public class AsteroidWelt extends Welt 
{
    // Attribute
    
    /**
     * Konstruktor der Klasse AsteroidWelt. Die Welt wird mit schwarzem Hintergrund erzeugt.
     */
    public AsteroidWelt() 
    {
        this(800, 800, Color.BLACK);
    }

    /**
     * Konstruktor der Klasse AsteroidWelt.
     * 
     * @param pPF Programmfenster, in dem die Welt dargestellt wird.
     * @param pBreite Breite der Welt
     * @param pHoehe Hoehe der Welt
     */
    public AsteroidWelt(int pBreite, int pHoehe, Color pBgColor) 
    {
        super(pBreite, pHoehe, pBgColor);
    }

    /**
     * Überschreibt die Methode initialisiereWelt der Oberklasse. Die Akteuer des Spiels werden erzeugt und eingefuegt.
     */
    @Override
    public void initialisiereWelt() {
        this.akteurEinfuegen(new Raumschiff(this, this.gibBreite()/2, this.gibHoehe()/2, 0, 5d, 5d, Kantenverhalten.FLAECHE));
        Random lZufallsgenerator = new Random();
        for(int i = 0; i < 10; i++)
        {
            this.akteurEinfuegen(new Asteroid(this, 
                    lZufallsgenerator.nextInt(this.gibBreite()), 
                    lZufallsgenerator.nextInt(this.gibBreite()), 
                    lZufallsgenerator.nextInt(360),
                    5d,
                    5d,
                    Kantenverhalten.KUGEL));
        }
    }
    
    /**
     * Überschreibt die Methode agiere der Oberklasse.
     */
    @Override
    public void agiere()
    {
        
    }   
}
