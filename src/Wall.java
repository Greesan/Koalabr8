import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends Stationary {
    protected Rectangle hitBox;
    public int state = 2;
    public Wall(int x, int y,  boolean vis, BufferedImage image)
    {
        super(x,y,vis,image);
        hitBox = new Rectangle(x,y,image.getWidth(),image.getHeight());
        this.hitBox.setLocation(x, y);
    }

    public Rectangle getHitBox()
    {
        return hitBox.getBounds();
    }

    public int getState() {
        return state;
    }
}
