package game;

import java.awt.image.BufferedImage;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * Die Klasse Bild kapselt den Zugriff auf ein Objekt der Klasse BufferedImage.
 * 
 * @author Gerd Heischkamp
 */
public class Bild
{
    // Attribute
    private BufferedImage bild;
    private double skalierungsfaktorX = 1.0, skalierungsfaktorY = 1.0, rotation = 0.0;
    private Shape form;

    /**
     * Konstruktor der Klasse Bild. Es wird ein Objekt der Klasse BufferedImage erzeugt.
     * 
     * @param pPfadZurDatei Pfad zur Bilddatei
     */
    public Bild(String pPfadZurDatei)
    {
        setzeBild(pPfadZurDatei);
    }
    
    /**
     * Dem Attribut bild vom Datentyp BufferedImage wird ein neues Bildobjekt zugewiesen.
     * Ein Shape-Objekt mit den Maßen des Bildes wird angelegt.
     * 
     * @param pPfadZurDatei Pfad zur Bilddatei
     */
    public void setzeBild(String pPfadZurDatei) {
        if(pPfadZurDatei != null)
        {
            try {
                this.bild = ImageIO.read(new File(pPfadZurDatei));
            } catch (IOException e) {
                try {
                    this.bild = ImageIO.read(new File("res/img/questionmark.png"));
                } catch (IOException e1) {
                    this.bild = new BufferedImage(64,64, BufferedImage.TYPE_INT_ARGB);
                }
            }
        }
        else
        {
            try {
                this.bild = ImageIO.read(new File("res/img/questionmark.png"));
            } catch (IOException e1) {
                this.bild = new BufferedImage(64,64, BufferedImage.TYPE_INT_ARGB);
            }
        }
        if(bild != null)
        {
            int[] x = new int[4];
            int[] y = new int[4];
            x[0] = 0;
            y[0] = 0;
            x[1] = 0;
            y[1] = bild.getHeight();
            x[2] = bild.getWidth();
            y[2] = bild.getHeight();
            x[3] = bild.getWidth();
            y[3] = 0;
            form = new Polygon(x,y,x.length);
        }
    }

    /**
     * Liefert das Shape-Objekt, welches das Bild umgibt.
     * 
     * @return Shape-Objekt, welches das Bild umgibt
     */
    public Shape gibForm()
    {
        return form;
    }

    /**
     * Liefert die Breite des Bildes
     * 
     * @return Breite des Bildes
     */
    public int gibBreite()
    {
        return bild.getWidth();
    }

    /**
     * Liefert die Hoehe des Bildes
     * 
     * @return Hoehe des Bildes
     */
    public int gibHoehe()
    {
        return bild.getHeight();
    }

    /**
     * Liefert das BufferedImage-Objekt, welches im Bild-Objekt gekapselt ist.
     * 
     * @return BufferedImage-Objekt, das im Bild gekapselt ist
     */
    public BufferedImage gibBufferedImage()
    {
        return bild;
    }
    
    /**
     * Liefert den Skalierungsfaktor in x-Richtung, mit dem das Bild gezeichnet wird.
     * 
     * @return x-Skalierungsfaktor, mit dem das Bild gezeichnet wird
     */
    public double gibSkalierungsfaktorX()
    {
        return this.skalierungsfaktorX;
    }

    /**
     * Setzt den Skalierungsfaktor in x-Richtung, mit dem das Bild gezeichnet werden soll.
     * 
     * @param pSkalierungsfaktor x-Skalierungsfaktor, mit dem das Bild gezeichnet werden soll
     */
    public void setzeSkalierungsfaktorX(double pSkalierungsfaktorX)
    {
        this.skalierungsfaktorX = pSkalierungsfaktorX;
    }

    /**
     * Liefert den Skalierungsfaktor in y-Richtung, mit dem das Bild gezeichnet wird.
     * 
     * @return y-Skalierungsfaktor, mit dem das Bild gezeichnet wird
     */
    public double gibSkalierungsfaktorY()
    {
        return this.skalierungsfaktorY;
    }

    /**
     * Setzt den Skalierungsfaktor in y-Richtung, mit dem das Bild gezeichnet werden soll.
     * 
     * @param pSkalierungsfaktor y-Skalierungsfaktor, mit dem das Bild gezeichnet werden soll
     */
    public void setzeSkalierungsfaktorY(double pSkalierungsfaktorY)
    {
        this.skalierungsfaktorY = pSkalierungsfaktorY;
    }

    /**
     * Setzt die Skalierungsfaktoren in x- und y-Richtung, mit denen das Bild gezeichnet werden soll.
     * 
     * @param pSkalierungsfaktorX x-Skalierungsfaktor, mit dem das Bild gezeichnet werden soll
     * @param pSkalierungsfaktorY y-Skalierungsfaktor, mit dem das Bild gezeichnet werden soll
     */
    public void setzeSkalierung(double pSkalierungsfaktorX, double pSkalierungsfaktorY)
    {
        this.skalierungsfaktorX = pSkalierungsfaktorX;
        this.skalierungsfaktorY = pSkalierungsfaktorY;        
    }
    
    /**
     * Setzt denselben Skalierungsfaktoren in x- und y-Richtung, mit dem das Bild gezeichnet werden soll.
     * 
     * @param pSkalierungsfaktor x- und y-Skalierungsfaktor, mit dem das Bild gezeichnet werden soll
     */
    public void setzeSkalierung(double pSkalierungsfaktor)
    {
        this.skalierungsfaktorX = pSkalierungsfaktor;
        this.skalierungsfaktorY = pSkalierungsfaktor;
    }

    /**
     * Liefert die Rotation, mit der das Bild gezeichnet wird.
     * 
     * @return Rotation, mit der das Bild gezeichnet wird
     */
    public double gibRotation()
    {
        return this.rotation;
    }

    /**
     * Setzt die Rotation, mit der das Bild gezeichnet werden soll.
     * 
     * @param pRotation, mit der das Bild gezeichnet werden soll
     */
     public void setzeRotation(double pRotation)
    {
        this.rotation = pRotation;
    }
}
