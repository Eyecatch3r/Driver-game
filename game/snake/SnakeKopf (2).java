package game.snake;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.geom.Point2D;

import game.Akteur;
import game.Bedienelemente;
import game.Welt;

import abiturklassen.datenstrukturklassen.linear.List;

public class SnakeKopf extends Akteur
{   
    // Attribute
    private boolean linksGedrueckt, rechtsGedrueckt;
    private List<SnakeGlied> glieder;
    
    public SnakeKopf(Welt pWelt, double pX, double pY, double pRichtung, double pDrehGeschwindigkeit, double pBewegungsGeschwindigkeit, Kantenverhalten pKantenverhalten)
    {
        super(pWelt, pX, pY, pRichtung, pDrehGeschwindigkeit, pBewegungsGeschwindigkeit, "res/snake/img/kopf.png", pKantenverhalten);
        this.setzeZeichneForm(false);
        
        erzeugeGlieder(7);
    }
    
    /**
     * Erzeugt eine Anzahl von Gliedern und 
     */
    private void erzeugeGlieder(int pAnzahl)
    {
        glieder = new List<SnakeGlied>();
        for(int i = 1; i < pAnzahl+1; i++)
        {
            SnakeGlied g = new SnakeGlied(this.gibWelt(), 
                this.gibX(), this.gibY(),
                this.gibRichtung(), this.gibDrehGeschwindigkeit(), 
                this.gibBewegungsGeschwindigkeit(), 
                this.gibKantenverhalten());
            int c = (int)(16.0/this.gibBewegungsGeschwindigkeit()); // Anzahl der Verschiebungen, die in eine Bildbreite passen
            g.vorwaerts(-i*c*this.gibBewegungsGeschwindigkeit()); // SnakeGlied-Objekt entspechend der Position nach hinten verschieben
            for(int j = 0; j < i*c; j++)
            {
                double x1, x2, y1, y2;
                x1 = g.gibX();
                y1 = g.gibY();
                g.vorwaerts(this.gibBewegungsGeschwindigkeit()); // SnakeGlie-Objekt schrittweise auf die Position des SnakeKopf-Objektes vorschieben
                x2 = g.gibX();
                y2 = g.gibY();
                g.bewegungAnhaengen(new Bewegung(x2-x1,y2-y1));
            }
            g.vorwaerts(-i*c*gibBewegungsGeschwindigkeit()); // SnakeGlied-Objekt wieder entsprechend der Position nach hinten verschieben
           
            glieder.append(g);
            this.gibWelt().akteurEinfuegen(g);
        }
    }
    
    public void agiere() 
    {
        double x1, y1, x2, y2;
        // Position vor der Bewegung merken
        x1 = gibX();
        y1 = gibY();
        if(Bedienelemente.isKeyDown(KeyEvent.VK_LEFT) && !linksGedrueckt)
        {
            this.rotiere(90);
            linksGedrueckt = true;
        }
        else if(!Bedienelemente.isKeyDown(KeyEvent.VK_LEFT))
        {
            linksGedrueckt = false;
        }
        if(Bedienelemente.isKeyDown(KeyEvent.VK_RIGHT) && !rechtsGedrueckt)
        {
            this.rotiere(-90);
            rechtsGedrueckt = true;
        }
        else if(!Bedienelemente.isKeyDown(KeyEvent.VK_RIGHT))
        {
            rechtsGedrueckt = false;
        }
        this.vorwaerts(this.gibBewegungsGeschwindigkeit());
        // Position nach der Bewegung merken
        x2 = gibX();
        y2 = gibY();
        
        // Bewegungsobjekt erzeugen und an die Queues der SnakeGlied-Objekte anhängen
        // to do
    }
}
