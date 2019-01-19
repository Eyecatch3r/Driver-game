package com.rob.engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
    private int w, h; // width, height
    private int[] p; // pixel
    
    public Image(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Image.class.getResourceAsStream(path));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        w = image.getWidth();
        h = image.getHeight();
        p = image.getRGB(0, 0, w, h, null, 0, w);
        
        image.flush();
    }
    
    //
    //  Getter | Setter
    //
    
    public int getWidth() {
        return w;
    }
    public void setWidth(int w) {
        this.w = w;
    }
    
    public int getHeight() {
        return h;
    }
    public void setHeight(int h) {
        this.h = h;
    }
    
    public int[] getPixel() {
        return p;
    }
    public void setPixel(int[] p) {
        this.p = p;
    }
}
