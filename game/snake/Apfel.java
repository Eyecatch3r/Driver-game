package game.snake;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import game.Akteur;
import game.Bedienelemente;
import game.Welt;

import abiturklassen.datenstrukturklassen.linear.Queue;


/**
 * Beschreiben Sie hier die Klasse Apfel.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Apfel extends Akteur
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int x;

    /**
     * Konstruktor für Objekte der Klasse Apfel
     */
    public Apfel(Welt pWelt, double pX, double pY, double pRichtung, double pDrehGeschwindigkeit, double pBewegungsGeschwindigkeit, Kantenverhalten pKantenverhalten)
    {
        super(pWelt, pX, pY, pRichtung, pDrehGeschwindigkeit, pBewegungsGeschwindigkeit, "res/snake/img/apfel.png", pKantenverhalten);
    }

     public void agiere() 
    {
        
    }
    
    
    
}
