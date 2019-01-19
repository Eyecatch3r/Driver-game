package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * Die Klasse Bedienelemente fungiert als KeyListener. In einer HashMap werden die gedrueckten Tasten abgespeichert.
 * 
 * @author Gerd Heischkamp
 */
public class Bedienelemente implements KeyListener
{
    // Attribute
    private static HashMap<Integer, Boolean> tastenStatus = new HashMap<Integer, Boolean>();
    
    /**
     * Konstruktor der Klasse Bedienelemente
     */
    public Bedienelemente() {}

    /**
     * Interface Keylistener: Die gedrückte Taste wird in der HashMap markiert.
     */
    public void keyPressed(KeyEvent arg0) {
        tastenStatus.put(new Integer(arg0.getKeyCode()), new Boolean(true));
    }
    
    /**
     * Interface Keylistener: Die gedrückte Taste wird in der HashMap ent-markiert.
     */
    public void keyReleased(KeyEvent arg0) {
        tastenStatus.put(new Integer(arg0.getKeyCode()), new Boolean(false));
    }

    /**
     * Interface Keylistener: leer.
     */
    public void keyTyped(KeyEvent arg0) {}

    /**
     * Liefert, ob die Taste pVirtualKey gedrueckt ist oder nicht.
     * 
     * @param pVirtualKey Taste, deren Zustand geprueft werden soll
     * @return true, wenn die Taste pVirtualKey gedrueckt ist, sonst false
     */
    public static boolean isKeyDown(int pVirtualKey)
    {
        Boolean b = tastenStatus.get(pVirtualKey);
        return (b!=null) ? b.booleanValue() : false;
    }
}
