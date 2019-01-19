package game.engine;

import java.awt.image.DataBufferInt;

import com.rob.engine.gfx.Image;
import com.rob.engine.gfx.ImageTile;
import com.rob.engine.gfx.Font;

public class Renderer {
    private int pW, pH; //pWidth, pHeight
    private int[] p; //p = pixel
    
    private Font font = Font.STANDARD;
    
    public Renderer(GameContainer gc) {
        pW = gc.getWidth();
        pH = gc.getHeight();
        p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }
    
    public void clear() {
        for(int i = 0; i < p.length; i++) {
            // p[i] += i; --> cancer
            
            p[i] = 0;
        }
    }
    
    public void setPixel(int x, int y, int value) {
        if((x < 0 || x >= pW || y < 0 || y >= pH) || value == 0xffff00ff) {
            return;
        }
        
        p[x + y * pW] = value;
    }
    
    public void drawText(String text, int offX, int offY, int color) {
        text = text.toUpperCase();
        int offset = 0;
        
        for(int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i) - 32;
            
            for(int y = 0; y < font.getFontImage().getHeight(); y++) {
                for(int x = 0; x < font.getWidths()[unicode]; x++) {
                    if(font.getFontImage().getPixel()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getWidth()] == 0xffffffff) {
                        setPixel(x + offX + offset, y + offY, color);
                    }        
                }
            }
            offset += font.getWidths()[unicode];
        }
    }
    
    public void drawImage(Image image, int offX, int offY) {
        // Don't Render code
        if(offX < -image.getWidth()) return;
        if(offY < -image.getHeight()) return;
        if(offX >= pW) return;
        if(offY >= pH) return;
        
        int newX = 0;
        int newY = 0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();
        
        // Clipping code
        if(offX < 0) newX -= offX;
        if(offY < 0) newY -= offY;
        if(newWidth + offX > pW) newWidth -= newWidth + offX - pW;
        if(newHeight + offY > pH) newHeight -= newHeight + offY - pH;
        
        for(int y = newY; y < newHeight; y++) {
            for(int x = newX; x < newWidth; x++) {
                setPixel(x + offX, y + offY, image.getPixel()[x + y * image.getWidth()]);
            }
        }
    }
    
    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
        // Don't Render code
        if(offX < -image.getTileW()) return;
        if(offY < -image.getTileH()) return;
        if(offX >= pW) return;
        if(offY >= pH) return;
        
        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileW();
        int newHeight = image.getTileH();
        
        // Clipping code
        if(offX < 0) newX -= offX;
        if(offY < 0) newY -= offY;
        if(newWidth + offX > pW) newWidth -= newWidth + offX - pW;
        if(newHeight + offY > pH) newHeight -= newHeight + offY - pH;
        
        for(int y = newY; y < newHeight; y++) {
            for(int x = newX; x < newWidth; x++) {
                setPixel(x + offX, y + offY, image.getPixel()[(x + tileX * image.getTileW()) + 
                    (y +tileY * image.getTileH()) * image.getWidth()]);
            }
        }
    }
}