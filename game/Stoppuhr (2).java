package game;

/**
 * Die Klasse Stoppuhr dient der Steuerung zeitabhaengiger Vorgaenge.
 * 
 * @author Gerd Heischkamp
 */
public class Stoppuhr
{
    // Attribute
    private long startzeitpunkt;
    
    /**
     * Konstruktor der Klasse Stoppuhr. Die Zeitmessung wird gestartet.
     */
    public Stoppuhr()
    {
        this.starteZeitmessung();
    }
    
    /**
     * Startet die Zeitmessung (neu).
     */
    public void starteZeitmessung()
    {
        startzeitpunkt = System.currentTimeMillis();
    }
    
    /**
     * Liefert den Zeitpunkt des Beginns der Zeitmessung in Millisekunden.
     * 
     * @return Zeitpunkt des Beginns der Zeitmessung
     */
    public long gibStartzeitpunkt()
    {
        return this.startzeitpunkt;
    }
    
    /**
     * Liefert die Dauer seit Beginn der Zeitmessung in Millisekunden.
     * 
     * @return Dauer seit Beginn der Zeitmessung
     */
    public long gibVergangeneZeit()
    {
        return System.currentTimeMillis()-this.startzeitpunkt;
    }
}
