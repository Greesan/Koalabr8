import java.awt.*;
import java.awt.image.BufferedImage;

public class HorizSaw extends Sawblade {
    public HorizSaw(int x, int y, int v, int maxX, boolean dir, boolean visible, BufferedImage Image) {
        super(x, y, x, v, maxX, dir, visible, Image);
    }


    @Override
    public void update() {
        if(getX() > getInit() + getMax() || getX() < getInit())
            setDir(!getDir());
        x += getV()*(getDir()?1:-1);
        super.update();
    }
}
