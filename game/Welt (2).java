package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import abiturklassen.datenstrukturklassen.linear.List;

/**
 * Ein Objekt der Klasse Welt verwaltet eine Liste von Akteur-Objekten. 
 * 
 * @author Gerd Heischkamp
 */
public class Welt extends JComponent implements ComponentListener
{
    private Image offscreenBild = null;
    private Graphics offscreenZeichenflaeche = null;
    private List<Akteur> akteure = null;
    private BufferedImage hintergrundBild = null;
    private BufferedImage vordergrundBild = null;    
    private Color hintergrundFarbe = Color.WHITE;
    private int breite, hoehe;

    /**
     * Konstruktor der Klasse Welt.
     * 
     * @param pBreite Breite der Welt
     * @param pHoehe Hoehe der Welt
     */
    public Welt(int pBreite, int pHoehe)
    {
        super();
        breite = pBreite;
        hoehe = pHoehe;
        this.setPreferredSize(new Dimension(breite, hoehe));
        this.addComponentListener(this);
        akteure = new List<Akteur>();
        vordergrundBild = new BufferedImage((int)this.getPreferredSize().getWidth(), (int)this.getPreferredSize().getHeight(), BufferedImage.TYPE_INT_ARGB);
        initialisiereWelt();
    }

    /**
     * Konstruktor der Klasse Welt.
     * 
     * @param pBreite Breite der Welt
     * @param pHoehe Hoehe der Welt
     * @param pPfadZurBilddatei Pfad zur Bilddatei
     */
    public Welt(int pBreite, int pHoehe, String pPfadZurBilddatei)
    {
        this(pBreite, pHoehe);
        try {
            hintergrundBild = ImageIO.read(new File(pPfadZurBilddatei));
        } catch (IOException e) {}
    }

    /**
     * Konstruktor der Klasse Welt.
     * 
     * @param pBreite Breite der Welt
     * @param pHoehe Hoehe der Welt
     * @param pBgColor Farbe des Hintergrundes
     */
    
    public Welt(int pBreite, int pHoehe, Color pBgColor)
    {
        this(pBreite, pHoehe);
        hintergrundFarbe = pBgColor;
    }

    /**
     * Die Methode wird automatisch aufgrufen, wenn ein Welt-Objekt erzeugt wird. Hier können 
     * Initialisierungen vorgenommen werden.
     */
    public void initialisiereWelt(){}

    /**
     * Die Methode wird bei jedem Zyklus der Handlungsschleife aufgerufen, bevor die entsprechende
     * agiere-Methode der Akteur-Objekte aufgerufen wird.
     */
    public void agiere(){}
          
    /**
     * Die Methode wird in einer Schleife aufgerufen, wodurch vom Welt-Objekt und den Akteur-Objekte
     * die agiere-Methoden aufgerufen werden.
     */
    public void loop()
    {
        // Welt-Handlungsschritt durchführen lassen
        this.agiere();
        
        // Akteur-Handlungsschritte durchführen lassen
        akteure.toFirst();
        while(akteure.hasAccess())
        {
            akteure.getContent().agiere();
            akteure.next();
        }
    }
    
    /**
     * Liefert die Breite der Welt.
     * 
     * @return Breite der Welt
     */
    public int gibBreite()
    {
        if(this.isValid())
        {
            return (int)this.getSize().getWidth();
        }
        else
        {
            return (int)this.getPreferredSize().getWidth();
        }
    }
    
    /**
     * Liefert die Hoehe der Welt.
     * 
     * @return Hoehe der Welt
     */
    public int gibHoehe()
    {
        if(this.isValid())
        {
            return (int)this.getSize().getHeight();
        }
        else
        {
            return (int)this.getPreferredSize().getHeight();
        }
    }
    
    /**
     * Setzt die Hintergrundfarbe der Welt. Nur sichtbar, wenn kein Hintergrundbild gewaehlt ist.
     * 
     * @param pHintergrundfarbe Farbe des Hintergrundes
     */
    public void setzeHintergrundFarbe(Color pHintergrundfarbe)
    {
        this.hintergrundFarbe = pHintergrundfarbe;
    }
    
    /**
     * Liefert die Hintergrundfarbe der Welt. Nur sichtbar, wenn kein Hintergrundbild gewaehlt ist.
     */
    public Color gibHintergrundFarbe()
    {
        return this.hintergrundFarbe;
    }
    
    /**
     * Liefert das Vordergrundbild der Welt.
     * 
     * @return Vordergrundbild der Welt
     */
    public BufferedImage gibVordergrundBild()
    {
        return this.vordergrundBild;
    }
    
    /**
     * Setzt das Vordergrundbild der Welt. Dieses kann für Einblendungen genutzt werden.
     * 
     * @param pVordergrundBild Vordergrundbild der Welt
     */
    public void setzeVordergrundBild(BufferedImage pVordergrundBild)
    {
        this.vordergrundBild = pVordergrundBild;
    }
    
    /**
     * Liefert das Hintergrundbild der Welt.
     * 
     * @return Hintergrund der Welt
     */
    public BufferedImage gibHintergrundBild()
    {
        return this.hintergrundBild;
    }
   
    /**
     * Setzt das Hintergrundbild der Welt.
     * 
     * @param pHintergrundBild Hintergrundbild der Welt
     */
    public void setzeHintergrundBild(BufferedImage pHintergrundBild)
    {
        this.hintergrundBild = pHintergrundBild;
    }
    
    /**
     * Setzt das Hintergrundbild der Welt.
     * 
     * @param pPfadZurDatei Pfad zum Hintergrundbild der Welt
     */
    public void setzeHintergrundBild(String pPfadZurDatei) {
        try {
            this.hintergrundBild = ImageIO.read(new File(pPfadZurDatei));
        } catch (IOException e) {}
    }

    /**
     * Liefert eine Liste (Kopie) der Akteure, die in der Welt existieren, ggf. nach Klassen gefiltert.
     * 
     * @param pVarargsKlassenfilter Klassen, nach denen gefiltert die Liste ausgegeben werden soll
     */
    public List<Akteur> gibAkteure(Class... pVarargsKlassenfilter) 
    {
        List<Akteur> gefilterteAkteure = new List<Akteur>();
        Akteur current = akteure.getContent();
        if(current == null) akteure.toFirst();
        while(akteure.hasAccess())
        {
            if(pVarargsKlassenfilter != null)
            {
                for(Class c: pVarargsKlassenfilter)
                {
                    if(akteure.getContent().getClass().isAssignableFrom(c))
                    {
                        gefilterteAkteure.append(akteure.getContent());
                    }
                }
            }
            else
            {
                gefilterteAkteure.append(akteure.getContent());
            }
            akteure.next();
        }
        akteure.toFirst();
        while(akteure.hasAccess() && (!akteure.getContent().equals(current)))
        {
            if(pVarargsKlassenfilter != null)
            {
                for(Class c: pVarargsKlassenfilter)
                {
                    if(akteure.getContent().getClass().isAssignableFrom(c))
                    {
                        gefilterteAkteure.append(akteure.getContent());
                    }
                }
            }
            else
            {
                gefilterteAkteure.append(akteure.getContent());
            }
            akteure.next();
        }
        return gefilterteAkteure;
    }
    
    /**
     * Fuegt einen Akteuer in die Liste der Akteure ein, die in der Welt existieren.
     * 
     * @param pAkteur Einzufuegender Akteur
     */
    public void akteurEinfuegen(Akteur pAkteur)
    {
        akteure.append(pAkteur);
    }
    
    /**
     * Loescht einen Akteur aus der Liste der Akteure, die in der Welt existieren.
     * 
     * @param pAkteur Zu loeschender Akteur
     */
    public void akteurEntfernen(Akteur pAkteur)
    {
        Akteur current = akteure.getContent();
        if(current == null) akteure.toFirst();
        while(akteure.hasAccess())
        {
            if(akteure.getContent().equals(pAkteur))
            {
                akteure.remove();
            }
            else
            {
                akteure.next();
            }
        }
        akteure.toFirst();
        while(akteure.hasAccess() && (!akteure.getContent().equals(current)))
        {
            if(akteure.getContent().equals(pAkteur))
            {
                akteure.remove();
            }
            else
            {
                akteure.next();
            }
        }
    }

    /**
     * Ueberschreibt die Methoder der Oberklasse. Das Bild wird neu gezeichnet.
     */
    @Override
    public void update(Graphics g)
    {
        paint(g);
    }

    /**
     * Ueberschreibt die Methode der Oberklasse. Das Bild wird neu gezeichnet.
     */
    @Override
    public void paint(Graphics g)
    {
        zeichneWelt(g);
    }
    
    /**
     * Zeichnet die Welt und alle Akteuer, die in der Welt existieren.
     */
    public void zeichneWelt(Graphics g)
    {
        // Double-Buffer initialisieren
        if (offscreenBild == null) 
        {
            offscreenBild = createImage(
                this.getSize().width,
                this.getSize().height
            );
            offscreenZeichenflaeche = offscreenBild.getGraphics();
        }

        Graphics2D g2d = (Graphics2D)offscreenZeichenflaeche;
        if(hintergrundBild != null)
        {
            AffineTransform lAffineTransformation = new AffineTransform();
            lAffineTransformation.scale(((double)this.getSize().width)/
                ((double)hintergrundBild.getWidth()), 
                ((double)this.getSize().height)/
                ((double)(hintergrundBild.getHeight())));
            g2d.drawImage(hintergrundBild, lAffineTransformation, null);
        }
        else
        {
            g2d.setColor(hintergrundFarbe);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            //g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
        }
        akteure.toFirst();
        while(akteure.hasAccess())
        {
            akteure.getContent().zeichneAkteur(g2d);
            akteure.next();
        }
        if(vordergrundBild != null)
        {
            AffineTransform lAffineTransformation = new AffineTransform();
            lAffineTransformation.scale(((double)this.getSize().width)/
                ((double)vordergrundBild.getWidth()), 
                ((double)this.getSize().height)/
                ((double)(vordergrundBild.getHeight())));
            g2d.drawImage(vordergrundBild, lAffineTransformation, null);
        }
        g.drawImage(offscreenBild, 0, 0, null);
    }
    
    
    /**
     * ComponentListener-Interface-Methode: leer.
     */
    @Override
    public void componentHidden(ComponentEvent arg0) {}
    
    /**
     * ComponentListener-Interface-Methode: leer.
     */
    @Override
    public void componentMoved(ComponentEvent arg0) {}

    /**
     * ComponentListener-Interface-Methode: Die Zeichenflaeche wird an die neue Weltgroesse angepasst.
     */
    @Override
    public void componentResized(ComponentEvent arg0) {
        offscreenBild = createImage(this.getSize().width, this.getSize().height);
        offscreenZeichenflaeche = offscreenBild.getGraphics();
    }

    /**
     * ComponentListener-Interface-Methode: leer.
     */
    @Override
    public void componentShown(ComponentEvent arg0) {}
}
