package koalaexample;

import koalaexample.GameObject;

import java.awt.image.BufferedImage;

public abstract class Stationary extends GameObject {
    public Stationary(int x, int y, boolean vis, BufferedImage image) {
        super(x, y, vis,image);
    }
}
