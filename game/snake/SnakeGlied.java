package game.snake;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import game.Akteur;
import game.Bedienelemente;
import game.Welt;

import abiturklassen.datenstrukturklassen.linear.Queue;


public class SnakeGlied extends Akteur
{   
    // Attribute
    private Queue<Bewegung> bewegungen;
    
    public SnakeGlied(Welt pWelt, double pX, double pY, double pRichtung, double pDrehGeschwindigkeit, double pBewegungsGeschwindigkeit, Kantenverhalten pKantenverhalten)
    {
        super(pWelt, pX, pY, pRichtung, pDrehGeschwindigkeit, pBewegungsGeschwindigkeit, "res/snake/img/glied.png", pKantenverhalten);
        this.setzeZeichneForm(false);
        bewegungen = new Queue<Bewegung>();
    }

    public void agiere() 
    {
        // Nächstes Bewegungsobjekt in der Queue abarbeiten
        // to do
    }
    
    public void bewegungAnhaengen(Bewegung pBewegung)
    {
        bewegungen.enqueue(pBewegung);
    }
}
