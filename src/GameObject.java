import java.awt.image.BufferedImage;
import java.awt.*;

public abstract class GameObject{
    protected int x;
    protected int y;
    protected boolean visible = true;
    protected BufferedImage image;
    protected Rectangle hitBox;

    public GameObject(int x, int y, boolean visible, BufferedImage image)
    {
        this.x = x;
        this.y = y;
        this.visible = visible;
        this.image = image;
        this.hitBox = new Rectangle(x,y,this.image.getWidth(),this.image.getHeight());
        this.hitBox.setLocation(x, y);
    }

    protected int getX() {
        return x;
    }
    protected int getY() {
        return y;
    }
    protected void setVisible(boolean vis) {
        visible = vis;
    }
    protected boolean getVisible() {
        return visible;
    }
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(getVisible()) {
            g2d.drawImage(this.image,x,y, null);
            //g2d.setColor(Color.CYAN);
            //g2d.drawRect(x,y,this.image.getWidth(),this.image.getHeight());
            //this.health.drawImage(g2d);
        }
    }
    public Rectangle getHitBox()
    {
        return hitBox.getBounds();
    }
    public void update()
    {
        hitBox.setLocation(x,y);
    }
}
