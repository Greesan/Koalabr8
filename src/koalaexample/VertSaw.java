package koalaexample;

import java.awt.image.BufferedImage;

public class VertSaw extends Sawblade {
    public VertSaw(int x, int y, int v, int maxY,boolean dir, boolean visible, BufferedImage Image) {
        super(x, y, y, v, maxY, dir, visible, Image);
    }


    @Override
    public void update() {
        if(getY() > getInit() + getMax() || getY() < getInit())
            setDir(!getDir());
        y += getV()*(getDir()?1:-1);
        super.update();
    }
}
