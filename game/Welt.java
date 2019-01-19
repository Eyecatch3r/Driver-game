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
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import abiturklassen.datenstrukturklassen.linear.List;
import java.awt.Font;
import game.Pong.*;
import game.Imageloader;
import java.io.*;
import java.util.Scanner;
import java.awt.AlphaComposite;
import javax.swing.Timer;

/**
 * Ein Objekt der Klasse Welt verwaltet eine Liste von Akteur-Objekten.
 *
 * @author Gerd Heischkamp(edited by Aldo Costa)
 */
public class Welt extends JComponent implements ComponentListener
{
    private Status aktuellerStatus;
    private Image offscreenBild = null;
    private Graphics offscreenZeichenflaeche = null;
    private List<Akteur> akteure = null;
    private List<Integer> scorel;
    private BufferedImage hintergrundBild = null;
    private BufferedImage vordergrundBild = null;
    private Color hintergrundFarbe = Color.WHITE;
    private int breite, hoehe, score;
    public static final long RUNNING_TIME = 2000;
    private float alpha = 0f;
    private long startTime = -1;
    private File newTextFile = new File("game/score.txt");
    public static final int ALPHA_RGB = BufferedImage.TYPE_INT_ARGB;
    private boolean isInitialized = false, isCleared = false, isScored = false, isWritten = false,Scored = false;
    private BufferedImage startScreen = Imageloader.loadImage("res/Driver/Img/Startscreen.png");
    private BufferedImage Gameover = Imageloader.loadImage("res/Driver/Img/Gameover.png");
    private BufferedImage pressX = Imageloader.loadImage("res/Driver/Img/pressX.png");

    public enum Status
    {
        START,GAME,TRANSITION,GAME_OVER;
    }

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
        scorel = new List<Integer>();
        vordergrundBild = new BufferedImage((int)this.getPreferredSize().getWidth(), (int)this.getPreferredSize().getHeight(), BufferedImage.TYPE_INT_ARGB);
        initialisiereWelt();
        aktuellerStatus = Status.START;
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

    public Status getStatus()
    {
        return aktuellerStatus;
    }

    public void setStatus(Status newStatus)
    {
        aktuellerStatus = newStatus;
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

    public int getScore()
    {
        return score;
    }

    public void setScore(int pScore)
    {
        score = pScore;
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

        switch(aktuellerStatus)
        {

            case START:
            if(isInitialized == false)
            {
                initialisiereWelt();
                isInitialized = true;
                Scored = false;
            }
            akteure.toFirst();
            while(akteure.hasAccess()){

                akteure.getContent().zeichneAkteur(g2d);
                akteure.next();

            }

            if(Bedienelemente.isKeyDown(KeyEvent.getExtendedKeyCodeForChar(88)))
            {
                aktuellerStatus = Status.GAME;

            }
            //ändert die Alphavalue der beiden Bilder, um einen Javascript ähnlichen Fadein-effekt darzustellen
            if (startTime < 0) {
                startTime = System.currentTimeMillis();
            } else {

                long time = System.currentTimeMillis();
                long duration = time - startTime;
                if (duration >= RUNNING_TIME) {
                    startTime = -1;

                    alpha = 0f;
                } else {
                    alpha = 1f - ((float) duration / (float) RUNNING_TIME);
                }

            }

            g2d.drawImage(fadeImage(pressX,0,0, pressX.getWidth(), pressX.getHeight(),ALPHA_RGB, alpha), this.gibBreite()/6,this.gibHoehe()/2, null);
            g2d.drawImage(fadeImage(startScreen,0,0, startScreen.getWidth(), startScreen.getHeight(),ALPHA_RGB, alpha), this.gibBreite()/3,this.gibHoehe()/4, null);

            break;
            case GAME:
            if(isCleared == false)
            {
                List<Akteur> enemy = gibAkteure(EnemyDriver.class);
                enemy.toFirst();
                while(enemy.hasAccess()){
                    akteurEntfernen(enemy.getContent());
                    enemy.next();

                }
                initialisiereWelt();
                isCleared = true;
            }

            akteure.toFirst();

            while(akteure.hasAccess()){
                if(akteure.getContent().gibZeichenEbene() == 0)
                {

                    akteure.getContent().zeichneAkteur(g2d);
                    akteure.next();
                }
                else
                {
                    akteure.next();
                }
            }
            akteure.toFirst();
            while(akteure.hasAccess()){
                if(akteure.getContent().gibZeichenEbene() == 1)
                {

                    akteure.getContent().zeichneAkteur(g2d);
                    akteure.next();
                }
                else
                {
                    akteure.next();
                }
            }

            //Zeichnet einen String zum anzeigen des Scores
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            g2d.setColor(Color.RED);

            g2d.drawString("Score:"+getScore() , this.getHeight()-200, 80);

            break;
            case TRANSITION:
            akteure.toFirst();

            while(akteure.hasAccess()){
                if(akteure.getContent().gibZeichenEbene() == 0)
                {

                    akteure.getContent().zeichneAkteur(g2d);
                    akteure.next();
                }
                else
                {
                    akteure.next();
                }
            }
            akteure.toFirst();
            while(akteure.hasAccess()){
                if(akteure.getContent().gibZeichenEbene() == 1)
                {

                    akteure.getContent().zeichneAkteur(g2d);
                    akteure.next();
                }
                else
                {
                    akteure.next();
                }
            }
            break;

            case GAME_OVER:
            //speichert den aktuellen Score in eine Textdatei ein mithhilfe einer Score list
            if(isScored == false)
            {

                writeScore();
                isScored = true;
            }

            // Score Anzeige
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            g2d.setColor(Color.RED);
            g2d.drawString("your Score:"+String.valueOf(getScore())  , this.getWidth()/5, this.getHeight()/3);
            akteure.toFirst();

            while(akteure.hasAccess()){

                akteure.remove();
                akteure.next();

            }
            isInitialized = false;
            if (startTime < 0) {
                startTime = System.currentTimeMillis();
            } else {

                long time = System.currentTimeMillis();
                long duration = time - startTime;
                if (duration >= RUNNING_TIME) {
                    
                    long time2 = System.currentTimeMillis();
                    long duration2 = time - startTime;
                    if (duration2 >= RUNNING_TIME)
                    {
                        startTime = -1;
                        
                        alpha = 1f;
                    }
                    else
                    {
                        alpha = 1f - ((float) duration2/ (float) RUNNING_TIME);
                    }
                } else {
                    alpha = 1f - ((float) duration / (float) RUNNING_TIME);
                }

            }
            g2d.drawImage(fadeImage(Gameover, 0, 0, Gameover.getWidth(), Gameover.getHeight(), ALPHA_RGB, alpha), this.getWidth()/6, this.getHeight()/3, null);
            //anzeigen des Scoreboards

            try
            {
                g2d.setFont(new Font("TimesRoman", Font.PLAIN, 60));
                int offset = 0;
                int scorerank = 0;
                BufferedReader br = new BufferedReader(new FileReader(newTextFile));
                String line;
                while((line = br.readLine()) != null && line != null && scorerank < 5)
                {
                    g2d.drawString("score:"+line, getWidth()/3, getHeight()-300+offset);
                    offset = offset + 55;
                    scorerank++;
                }
            }
            catch(IOException iox)
            {
                iox.printStackTrace();
            }
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            g2d.drawString("press Y to replay", 10, this.getHeight()-700);

            //übergangsbedingung um ein neues Spiel zu starten
            if(Bedienelemente.isKeyDown(KeyEvent.getExtendedKeyCodeForChar(89)))
            {
                aktuellerStatus = Status.START;
                isCleared = false;
                score = 0;
                isScored = false;
            }
            break;
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

    public static BufferedImage fadeImage(BufferedImage OI,int x, int y ,int width, int height,int type,float rate)
    {
        BufferedImage nI = new BufferedImage(width,height,type);
        Graphics2D g2 = nI.createGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, rate));
        g2.drawImage(OI, x, y, null);
        g2.dispose();
        return nI;
    }

    /**
     * hier sortieren wir die Score liste und speichern sie in einer textdatei ab
     */
    public void writeScore()
    {
        try {
            List<Integer> scores = new List();
            Scanner sc = new Scanner(new FileReader(newTextFile));
            FileWriter fw = new FileWriter(newTextFile, true);
            Integer actscore = score;

            while (sc.hasNextInt()) {
                int nextint;
                sc.nextLine();
                nextint = sc.nextInt();
                scorel.append(nextint);

            }
            sc.close();
            
            
            if(!Scored)scorel.append(score); Scored = true;
            
            scorel.toFirst();
            while(scorel.hasAccess())
            {
                if(scores.isEmpty())
                {
                    scores.append(scorel.getContent());

                } 
                else
                {
                    scores.toFirst();
                    while(scores.hasAccess() && (scores.getContent() > scorel.getContent()))
                    {
                        scores.next();
                    }
                    if(scores.hasAccess())
                    {
                        scores.insert(scorel.getContent());
                    }
                    else
                    {
                        scores.append(scorel.getContent());
                    }
                    scorel.next();
                }
            }

            PrintWriter writer = new PrintWriter(newTextFile);
            writer.print("");
            writer.flush();
            writer.close();

            scores.toFirst();
            while(scores.hasAccess())
            {
                Integer str = scores.getContent();

                fw.write(str.toString());
                fw.write(System.getProperty("line.separator"));

                scores.next();
            }
            fw.close();
        } catch (IOException iox) {

            iox.printStackTrace();
        }
    }

    /**
     * diese Methode geht Pixelweise durch ein BufferedImage und ändert den Alpha wert
     */
    public static void changeAlpha(BufferedImage modMe, double modAmount) {
        //
        for (int x = 0; x < modMe.getWidth(); x++) {          
            for (int y = 0; y < modMe.getHeight(); y++) {
                //
                int argb = modMe.getRGB(x, y); //always returns TYPE_INT_ARGB
                int alpha = (argb >> 24) & 0xff;  //isolate alpha

                alpha *= modAmount; //similar distortion to tape saturation (has scrunching effect, eliminates clipping)
                alpha &= 0xff;      //keeps alpha in 0-255 range

                argb &= 0x00ffffff; //remove old alpha info
                argb |= (alpha << 24);  //add new alpha info
                modMe.setRGB(x, y, argb);            
            }
        }
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
