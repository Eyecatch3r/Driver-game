package game.asteroidgame;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import game.Akteur;
import game.Bedienelemente;
import game.Welt;

public class Asteroid extends Akteur
{
    private boolean kollidiert;

    public Asteroid(Welt pWelt, double pX, double pY, double pRichtung, double pDrehGeschwindigkeit, double pBewegungsGeschwindigkeit, Kantenverhalten pKantenverhalten)
    {
        super(pWelt, pX, pY, pRichtung, pDrehGeschwindigkeit, pBewegungsGeschwindigkeit, "res/asteroidgame/img/stone.png", pKantenverhalten);
        this.gibBild().setzeSkalierung(0.1 + 0.5 * (new Random().nextDouble()));
    }

    public void agiere() 
    {
        this.vorwaerts(this.gibBewegungsGeschwindigkeit());
        this.gibBild().setzeRotation(this.gibBild().gibRotation()+this.gibDrehGeschwindigkeit());
    }

    public void zeichneAkteur(Graphics2D g)
    {
        super.zeichneAkteur(g);
    }
}
