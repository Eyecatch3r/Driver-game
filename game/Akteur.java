package game;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import abiturklassen.datenstrukturklassen.linear.List;

/**
 * Ein Objekt der Klasse Akteur existiert in einem Objekt der Klasse Welt. Die agiere-Methode wird in einer Schleife aufgerufen.
 */
public class Akteur 
{
    // Attribute
    private Bild bild;
    private double x, y, richtung, drehGeschwindigkeit, bewegungsGeschwindigkeit;
    private AffineTransform affineTransformation;
    private Welt kWelt;
    private Shape form, transformierteForm;
    private Kantenverhalten kantenverhalten = Kantenverhalten.FLAECHE;
    private int zeichenEbene = 0;
    private boolean zeichneForm = true;
    private boolean fuelleForm = false;

    /**
     * Enum für das Positionsverhalten an den Grenzen der Welt.
     */
    public enum Kantenverhalten 
    {
        /** 
         * EBENE - Der Akteur kann sich auch ausserhalb des sichtbaren Bereichs der Welt bewegen.
         */
        EBENE,
        /** 
         * FLAECHE - Der Akteur kann sich nur innerhalb des sichtbaren Bereichs der Welt bewegen. 
         */
        FLAECHE,
        /** 
         * KUGEL - Der Akteur kommt beim Verlassen der Welt an der einen Kante auf der gegenueberliegenden Kante wieder rein. 
         */
        KUGEL 
    }

    /**
     * Konstruktor der Klasse Akteur
     * 
     * @param pWelt Welt-Objekt, in dem der Akteur existiert
     * @param pX x-Koordinate des Akteurs in der Welt
     * @param pY y-Koordinate des Akteurs in der Welt
     * @param pRichtung Blickrichtung des Akteurs in der Welt
     * @param pDrehGeschwindigkeit Drehgeschwindigkeit des Akteurs in der Welt
     * @param pBewegungsGeschwindigkeit Bewegungsgeschwindigkeit des Akteurs in der Welt
     * @param pPfadZumBild Pfad zur Bilddatei des Akteurs
     * @param Kantenverhalten Kantenverhalten des Akteurs (EBENE, FLAECHE, KUGEL)
     */
    public Akteur(Welt pWelt, double pX, double pY, double pRichtung, double pDrehGeschwindigkeit, double pBewegungsGeschwindigkeit, Bild pBild, Kantenverhalten pKantenverhalten) 
    {
        kWelt = pWelt;
        x = pX;
        y = pY;
        richtung = pRichtung;
        drehGeschwindigkeit = pDrehGeschwindigkeit;
        bewegungsGeschwindigkeit = pBewegungsGeschwindigkeit;
        bild = pBild;;
        kantenverhalten = pKantenverhalten;
    }
    
     public Akteur(Welt pWelt,double pX, double pY, Bild pBild, Kantenverhalten pKantenverhalten, int pLane)
    {
        kWelt = pWelt;
        x = pX;
        y = pY;
        bild = pBild;
        kantenverhalten = pKantenverhalten;
    }
    
    public Akteur(Welt pWelt,double pX, double pY, Bild pBild)
    {
        kWelt = pWelt;
        x = pX;
        y = pY;
        bild = pBild;
    }
    
    public Akteur(Welt pWelt,double pX, double pY, Bild pBild, Kantenverhalten pKantenverhalten)
    {
        kWelt = pWelt;
        x = pX;
        y = pY;
        kantenverhalten = pKantenverhalten;
        bild = pBild;
    }
    
    /**
     * Die Methode wird in einer Schleife aufgerufen. Hier wird das Verhalten des AKteurs bei einem Handlungsschritt festgelegt.
     */
    public void agiere() {}

    /**
     * Liefert das Welt-Objekt, in dem der Akteur existiert.
     * 
     * @return Welt-Objekt, in dem der Akteur existiert
     */
    public Welt gibWelt() {
        return kWelt;
    }

    /**
     * Setzt das Bild des Akteurs.
     * 
     * @param pPfadZurDatei Pfad zur Datei des Bildes
     */
    public void setzeBild(String pPfadZurDatei) 
    {
        bild = new Bild(pPfadZurDatei);
        if(bild != null)
        {
            this.setzeForm(bild.gibForm());
            transformierteForm = this.gibAffineTransformation().createTransformedShape(form);
        }
    }

    /**
     * Liefert das Bild des Akteurs.
     * 
     * @return Bild des Akteurs
     */
    public Bild gibBild()
    {
        return this.bild;
    }
    
    /**
     * Setzt die Form des Akteur-Objekte. Standardmaessig ergibt sich die Form aus den Massen des Bildes, jedoch kann man die Form auch genauer eingrenzen.
     * 
     * @param pForm Form des Akteurs
     */
    public void setzeForm(Shape pForm)
    {
        this.form = pForm;
        this.transformierteForm = this.gibAffineTransformation().createTransformedShape(form);
    }
    
    /**
     * Liefert die Form des Akteur-Objektes.
     */
    public Shape gibForm()
    {
        return this.form;
    }

    /**
     * Liefert true, wenn die Form des Akteurs zusaetzlich zum Bild des Akteurs gezeichnet werden soll, sonst false.
     * 
     * @return true, wenn die Form des Akteurs zusaetzlich zum Bild des Akteurs gezeichnet werden soll, sonst false
     */
    public boolean gibZeichneForm()
    {
        return this.zeichneForm;
    }

    /**
     * Bestimmt, ob die Form des Akteurs zusaetzlich zum Bild des Akteurs gezeichnet werden soll.
     */
    public void setzeZeichneForm(boolean pZeichneForm)
    {
        this.zeichneForm = pZeichneForm;
    }
    
    /**
     * Liefert true, wenn die Form des Akteurs ausgeuellt gezeichnet werden soll, sonst false.
     * 
     * @return true, wenn die Form des Akteurs ausgefuelltgezeichnet werden soll, sonst false
     */
    
    public boolean gibFuelleForm()
    {
        return this.fuelleForm;
    }

    /**
     * Bestimmt, ob die Form des Akteurs ausgefuellt gezeichnet werden soll.
     */
    public void setzeFuelleForm(boolean pFuelleForm)
    {
        this.fuelleForm = pFuelleForm;
    }

    /**
     * Liefert die Form des Akteurs nach der Transformation, die sich durch Translation an die aktuelle Position und Rotation in die aktuelle Blickrichtung ergibt.
     * 
     * @return transformierte Form des Akteurs
     */
    public Shape gibTransformierteForm()
    {
        return this.transformierteForm;
    }

    /**
     * Liefert die Drehgeschwindigkeit des Akteurs.
     * 
     * @return Dregeschwindigkeit des Akteurs
     */
    public double gibDrehGeschwindigkeit()
    {
        return this.drehGeschwindigkeit;
    }

    /**
     * Setzt die Drehgeschwindigkeit des Akteurs.
     * 
     * @param pDrehGeschwindigkeit Drehgeschwindigkeit des Akteurs
     */
    public void setzeDrehGeschwindigkeit(double pDrehGeschwindigkeit)
    {
        this.drehGeschwindigkeit = pDrehGeschwindigkeit;
    }

    /**
     * Liefert die Bewegungsgeschwindigkeit des Akteurs.
     * 
     * @return Bewegungsgeschwindigkeit des Akteurs
     */
    public double gibBewegungsGeschwindigkeit()
    {
        return this.bewegungsGeschwindigkeit;
    }

    /**
     * Setzt die Bewegungsgeschwindigkeit des Akteurs.
     * 
     * @param pBewegungsgeschwindigkeit Bewegungsgeschwindigkeit des Akteurs
     */
    public void setzeBewegungsGeschwindigkeit(double pBewegungsGeschwindigkeit)
    {
        this.bewegungsGeschwindigkeit = pBewegungsGeschwindigkeit;
    }

    /**
     * Liefert die x-Koordinate des Akteurs in der Welt.
     * 
     * @return x-Koordinate des Ateurs in der Welt
     */
    public double gibX() {
        return this.x;
    }

    /**
     * Setzt die x-Koordinate des Akteurs in der Welt.
     * 
     * @param pX x-Koordinate des Akteurs in der Welt
     */
    public void setzeX(double pX) {
        switch(kantenverhalten)
        {
            case EBENE:
            this.x = pX;
            break;
            case FLAECHE:
            this.x = Math.max(0, Math.min(pX, kWelt.gibBreite()));
            break;
            case KUGEL:
            default:
            this.x = ((pX % kWelt.gibBreite()) + kWelt.gibBreite()) % Math.max(1.0,kWelt.gibBreite());
            break;
        }
    }

    /**
     * Liefert die y-Koordinate des Akteurs in der Welt.
     * 
     * @return y-Koordinate des Ateurs in der Welt
     */
    public double gibY() {
        return this.y;
    }

    /**
     * Setzt die y-Koordinate des Akteurs in der Welt.
     * 
     * @param pY y-Koordinate des Akteurs in der Welt
     */
    public void setzeY(double pY) {
        switch(kantenverhalten)
        {
            case EBENE:
            this.y = pY;
            break;
            case FLAECHE:
            this.y = Math.max(0, Math.min(pY, kWelt.gibHoehe()));
            break;
            case KUGEL:
            default:
            this.y = ((pY % kWelt.gibHoehe()) + kWelt.gibHoehe()) % Math.max(1.0,kWelt.gibHoehe());
            break;
        }
    }

    /**
     * Liefert die Richtung des Akteurs in der Welt.
     * 
     * @return Richtung des Akteurs in der Welt
     */
    public double gibRichtung() {
        return this.richtung;
    }

    /**
     * Setzt die Richtung des Akteurs in der Welt.
     * 
     * @param pRichtung Richtung des Akteurs in der Welt
     */
    public void setzeRichtung(double pRichtung) {
        this.richtung = pRichtung;
    }

    /**
     * Liefert die Distanz des Akteurs zu einem anderen Akteur
     * 
     * @param pAndererAkteur Anderer Akteur, zu dem der Abstand bestimmt wird
     * 
     * @return Distanz des AKteurs zu einem anderen Akteur
     */
    public double gibDistanz(Akteur pAndererAkteur)
    {
        return Math.sqrt(
            Math.pow(this.gibX()-pAndererAkteur.gibX(), 2)+
            Math.pow(this.gibY()-pAndererAkteur.gibY(), 2));
    }

    /**
     * Liefert den Umkreisradius um die Grafik des Akteurs
     * 
     * @return Umkreisradius um die Grafik des Akteurs
     */
    public double gibUmkreisradius()
    {
        return Math.sqrt(Math.pow(this.gibBild().gibSkalierungsfaktorX()*((double)bild.gibBreite())/2d,2) + Math.pow(this.gibBild().gibSkalierungsfaktorY()*((double)bild.gibHoehe())/2d,2));
    }

    /**
     * Liefert true, wenn der Akteur ausserhalb des sichtbaren Bereichs der Welt liegt, sonst false.
     * 
     * @return true, wenn der Akteur ausserhalb des sichtbaren Bereichs der Welt liegt, sonst false
     */
    public boolean istAusserhalbDerWelt()
    {
        if((this.x < 0) || (this.x > this.gibWelt().gibBreite()) || (this.y < 0) || (this.y > this.gibWelt().gibHoehe())) return true; else return false; 
    }

    /**
     * Liefert true, wenn der Akteur mit einem anderen Akteur kollidiert, sonst false.
     * 
     * @return true, wenn der Akteur mit einem anderen Akteur kollidiert, sonst false
     */
    public boolean kollidiert(Akteur pAndererAkteur) {
        // Schnell-Test
        /*if(this.gibDistanz(pAndererAkteur) >
        this.gibUmkreisradius()+pAndererAkteur.gibUmkreisradius())
        {
            return false;
        }*/
        
        if(this.gibDistanz(pAndererAkteur) > 90)
        {
            return false;
        }
        
        // Detail-Test
        /*Area intersectionArea = new Area(this.transformierteForm);
        intersectionArea.intersect(new Area(pAndererAkteur.transformierteForm));
        return !intersectionArea.isEmpty();*/
        return true;
    }

    /**
     * Liefert eine Liste (Kopie) der Akteure in der Welt, mit denen der Aktuer kollidiert, ggf. gefiltert nach Klassen.
     * 
     * @param Klassen, nach denen gefiltert wird
     * 
     * @return Liste (Kopie) der Akteure in der Welt, mit denen der Aktuer kollidiert, ggf. gefiltert nach Klassen
     */
    public List<Akteur> gibKollidierendeObjekte(Class... pVarargsKlassen)
    {
        List<Akteur> kollidierendeAkteure = this.gibWelt().gibAkteure(pVarargsKlassen);
        kollidierendeAkteure.toFirst();
        while(kollidierendeAkteure.hasAccess())
        {
            if(kollidierendeAkteure.getContent().equals(this)) // keine Überlappung mit sich selbst
            {
                kollidierendeAkteure.remove();
            }
            else if(!kollidierendeAkteure.getContent().kollidiert(this))
            {
                kollidierendeAkteure.remove();
            }
            else
            {
                kollidierendeAkteure.next();
            }
        }

        return kollidierendeAkteure;
    }
   
    /**
     * Bewirkt, dass sich der Akteur in die Blickrichtung bewegt.
     * 
     * @param pDistanz Distanz, um die der Akteur bewegt wird
     */
    public void vorwaerts(double pDistanz)
    {
        this.setzeX(this.gibX()+pDistanz*Math.cos(Math.toRadians(this.gibRichtung())));
        this.setzeY(this.gibY()-pDistanz*Math.sin(Math.toRadians(this.gibRichtung())));
    }

    /**
     * Bewirkt, dass sich der Akteur um den angegebenen Winkel dreht.
     * 
     * @param pWinkel Winkel, um den der Akteur gedreht wird
     */
    public void rotiere(double pWinkel)
    {
        this.setzeRichtung(this.gibRichtung()+pWinkel);
    }

    /**
     * Setzt das Kantenverhalten auf EBENE, FLAECHE bzw. KUGEL
     * 
     * @param pKantenverhalten EBENE, FLAECHE oder KUGEL
     */
    public void setzeKantenverhalten(Kantenverhalten pKantenverhalten)
    {
        this.kantenverhalten = pKantenverhalten;
    }

    /**
     * Liefert das Kantenverhalten des Akteurs
     * 
     * @return Kantenverhalten (EBENE, FLAECHE, KUGEL)
     */
    public Kantenverhalten gibKantenverhalten()
    {
        return this.kantenverhalten;
    }

    /**
     * Liefert die Zeichenebene für diesen Akteur.
     * 
     * @return Zeichenebene für diesen Akteur
     */
    public int gibZeichenEbene()
    {
        return this.zeichenEbene;
    }

    /**
     * Setzt die Zeichenebene für diesen Akteur.
     * 
     * @param pZeichenEbene Zeichenebene für diesen Akteur
     */
    public void setzeZeichenEbene(int pZeichenEbene)
    {
        this.zeichenEbene = pZeichenEbene;
    }
    
    /**
     * Zeichnet den Akteur.
     * 
     * @param g2d Leinwand, auf die gezeichnet wird
     */
    public void zeichneAkteur(Graphics2D g2d)
    {
        if(bild != null)
        {
            if(this.zeichneForm && this.fuelleForm)
            {
                g2d.setColor(Color.YELLOW);
                g2d.fill(transformierteForm);
            }
            affineTransformation = gibAffineTransformation();
            g2d.drawImage(bild.gibBufferedImage(), affineTransformation, null);
            transformierteForm = affineTransformation.createTransformedShape(form);
            if(this.zeichneForm)
            {
                g2d.setColor(Color.RED);
                g2d.draw(transformierteForm);
            }
        }  
    }
    
    /**
     * Der Akteur wird gezeichnet.
     * 
     * @param g2d Leinwand, auf die gezeichnet wird
     */
    public void paint(Graphics2D g2d) 
    {   
        zeichneAkteur(g2d);
    }

    /**
     * Liefert ein AffineTransform-Objekt, das zur Zeichnung des Akteurs (an der Position, mit der Blickrichtung) dient.
     * 
     * @return AffineTransform-Objekt, das zur Zeichnung des Akteurs (an der Position, mit der Blickrichtung) dient
     */
    public AffineTransform gibAffineTransformation()
    {
        AffineTransform lAffineTransformation = new AffineTransform();
        lAffineTransformation.setToTranslation(x - bild.gibSkalierungsfaktorX()*((double)bild.gibBreite()) / 2d, y - bild.gibSkalierungsfaktorY()*((double)bild.gibHoehe()) / 2d);
        lAffineTransformation.rotate(-Math.toRadians(richtung - bild.gibRotation()),bild.gibSkalierungsfaktorX()*((double)bild.gibBreite()) / 2d, bild.gibSkalierungsfaktorY()*((double)bild.gibHoehe()) / 2d);
        lAffineTransformation.scale(bild.gibSkalierungsfaktorX(), bild.gibSkalierungsfaktorY());
        
        return lAffineTransformation;
    }
}