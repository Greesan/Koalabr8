import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Moveable extends GameObject {
    public Moveable(int x, int y,boolean visible, BufferedImage image) {
        super(x,y,visible,image);
    }

}
