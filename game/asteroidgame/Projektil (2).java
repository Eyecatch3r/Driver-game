package game.asteroidgame;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import game.Akteur;
import game.Musik;
import game.Welt;

import abiturklassen.datenstrukturklassen.linear.List;

public class Projektil extends Akteur
{
    private boolean kollidiert;

    public Projektil(Welt pWelt, double pX, double pY, double pRichtung, double pDrehGeschwindigkeit, double pBewegungsGeschwindigkeit, Kantenverhalten pKantenverhalten) 
    {
        super(pWelt, pX, pY, pRichtung, pDrehGeschwindigkeit, pBewegungsGeschwindigkeit, "res/asteroidgame/img/bullet.png", pKantenverhalten);
        this.gibBild().setzeSkalierung(0.1);
        this.gibBild().setzeRotation(90);
        Musik.geraeuschHinzufuegen("explosion", "res/asteroidgame/sound/Explosion.wav");
    }

    public void agiere() 
    {
        List<Akteur> temp = this.gibKollidierendeObjekte(Asteroid.class);
        temp.toFirst();
        while(temp.hasAccess())
        {
            this.gibWelt().akteurEntfernen(temp.getContent());
            temp.next();
        }
        kollidiert = temp.isEmpty() ? false : true;
        //if(kollidiert) Musik.geraeuschAbspielen("explosion");*/
        this.vorwaerts(this.gibBewegungsGeschwindigkeit());
        if(kollidiert || this.istAusserhalbDerWelt()) this.gibWelt().akteurEntfernen(this);
    }

    public void zeichneAkteur(Graphics2D g)
    {
        super.zeichneAkteur(g);
    }
}
