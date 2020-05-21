package koalaexample;

import koalaexample.Moveable;

import java.awt.image.BufferedImage;

public abstract class Sawblade extends Moveable {

    private int init,v, max;
    private boolean dir;
    public Sawblade(int x, int y, int init, int v, int maxX, boolean dir, boolean visible, BufferedImage Image) {
        super(x, y, visible, Image);
        this.init = init;
        this.v = v;
        this.max = maxX;
        this.dir = dir;
        //this.hitBox.setLocation(x, y);
    }
    protected int getInit() {
        return init;
    }

    protected int getV() {
        return v;
    }

    protected int getMax() {
        return max;
    }

    protected boolean getDir() {
        return dir;
    }

    public void setDir(boolean dir) {
        this.dir = dir;
    }

    @Override
    public void update() {
        super.update();
    }
}
