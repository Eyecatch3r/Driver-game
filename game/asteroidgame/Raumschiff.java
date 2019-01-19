package game.asteroidgame;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Shape;
import java.awt.Polygon;

import game.Akteur;
import game.Bedienelemente;
import game.Welt;
import game.Stoppuhr;

public class Raumschiff extends Akteur
{
    private boolean kollidiert;
    private Stoppuhr stoppuhr;

    public Raumschiff(Welt pWelt, double pX, double pY, double pRichtung, double pDrehGeschwindigkeit, double pBewegungsGeschwindigkeit, Kantenverhalten pKantenverhalten) 
    {
        super(pWelt, pX, pY, pRichtung, pDrehGeschwindigkeit, pBewegungsGeschwindigkeit, "res/asteroidgame/img/spaceship.png", pKantenverhalten);
        this.gibBild().setzeRotation(90d);
        this.gibBild().setzeSkalierung(0.75);
        int[] x = new int[8];
        int[] y = new int[8];
        x[0] = this.gibBild().gibBreite()/4;
        y[0] = 0;
        x[1] = this.gibBild().gibBreite()/4*3;
        y[1] = 0;
        x[2] = this.gibBild().gibBreite()/4*3;
        y[2] = this.gibBild().gibHoehe()/2;
        x[3] = this.gibBild().gibBreite();
        y[3] = this.gibBild().gibHoehe()/2;
        x[4] = this.gibBild().gibBreite();
        y[4] = this.gibBild().gibHoehe();
        x[5] = 0;
        y[5] = this.gibBild().gibHoehe();
        x[6] = 0;
        y[6] = this.gibBild().gibHoehe()/2;
        x[7] = this.gibBild().gibBreite()/4;
        y[7] = this.gibBild().gibHoehe()/2;
        Shape lForm = new Polygon(x,y,x.length);
        this.setzeForm(lForm);
        stoppuhr = new Stoppuhr();
    }

    public void agiere() 
    {
        kollidiert = this.gibKollidierendeObjekte(Asteroid.class).isEmpty() ? false : true;
        this.setzeFuelleForm(kollidiert);
        if(Bedienelemente.isKeyDown(KeyEvent.VK_LEFT))
        {
            this.rotiere(this.gibDrehGeschwindigkeit());
        }
        if(Bedienelemente.isKeyDown(KeyEvent.VK_RIGHT))
        {
            this.rotiere(-this.gibDrehGeschwindigkeit());
        }
        if(Bedienelemente.isKeyDown(KeyEvent.VK_UP))
        {
            this.vorwaerts(this.gibBewegungsGeschwindigkeit());
        }
        if(Bedienelemente.isKeyDown(KeyEvent.VK_SPACE) && (stoppuhr.gibVergangeneZeit() > 200))
        {
            this.gibWelt().akteurEinfuegen(new Projektil(this.gibWelt(), this.gibX(), this.gibY(), this.gibRichtung(), 0d, 3*this.gibBewegungsGeschwindigkeit(), Kantenverhalten.EBENE));
            stoppuhr.starteZeitmessung();
        }
    }

    public void zeichneAkteur(Graphics2D g)
    {
        super.zeichneAkteur(g);
    }
}
