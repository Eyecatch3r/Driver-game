package game.snake;

import java.awt.*;
import java.awt.image.*;
import java.util.Random;

import game.Bedienelemente;
import game.Programmfenster;
import game.Welt;
import game.Akteur.Kantenverhalten;

/**
 * Die Klasse SnakeWelt verwaltet alle Akteur-Objekte, die im Snake-Spiel vorkommen.
 * 
 * @author Gerd Heischkamp
 */
public class SnakeWelt extends Welt 
{
    // Attribute
    
    /**
     * Konstruktor der Klasse AsteroidWelt. Die Welt wird mit schwarzem Hintergrund erzeugt.
     */
    public SnakeWelt() 
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
    public SnakeWelt(int pBreite, int pHoehe, Color pBgColor) 
    {
        super(pBreite, pHoehe, pBgColor);
    }

    /**
     * Überschreibt die Methode initialisiereWelt der Oberklasse. Die Akteuer des Spiels werden erzeugt und eingefuegt.
     */
    @Override
    public void initialisiereWelt() {
        this.akteurEinfuegen(new SnakeKopf(this,200,100,0,90,5,Kantenverhalten.KUGEL));
    }
    
    /**
     * Überschreibt die Methode agiere der Oberklasse.
     */
    @Override
    public void agiere()
    {
        
    }   
}
