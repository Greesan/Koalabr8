import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

public class Koala extends Moveable {
    private int v;
    protected BufferedImage koalaStandImage;
    protected BufferedImage koalaLeftImage;
    protected BufferedImage koalaRightImage;
    protected BufferedImage koalaUpImage;
    protected BufferedImage koalaDownImage;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    Date lasttime;
    Date projtime;
    Koala(int x, int y, boolean vis, BufferedImage koalaStandImage, BufferedImage koalaLeftImage,
          BufferedImage koalaRightImage, BufferedImage koalaUpImage, BufferedImage koalaDownImage) {
        super(x,y,vis,koalaStandImage);
        lasttime = new Date();
        this.x = x;
        this.y = y;
        this.v = 3;
        this.koalaStandImage = koalaStandImage;
        this.koalaLeftImage = koalaLeftImage;
        this.koalaRightImage = koalaRightImage;
        this.koalaUpImage = koalaUpImage;
        this.koalaDownImage = koalaDownImage;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }



    public void drawImage() {
        
    }

    public void update() {
        if(getVisible())
        {
            if (this.UpPressed) {
                this.moveUp();
            }
            else if (this.DownPressed) {
                this.moveDown();
            }

            else if (this.LeftPressed) {
                this.moveLeft();
            }
            else if (this.RightPressed) {
                this.moveRight();
            }
        }
    }
    public void collideCheck(Object obj)
    {
        if (obj instanceof ArrayList<?> && (!((ArrayList<?>) obj).isEmpty()) && ((ArrayList<?>) obj).get(0) instanceof Wall) {
            for(Object wall : (ArrayList<?>)obj) {
                if (((Wall)wall).getVisible() && ((Wall) wall).getHitBox().intersects(hitBox)) {
                    setX(getX() + (isLeftPressed() ? 1 : 0) * getV() - (isRightPressed() ? 1 : 0) * getV());
                    setY(getY() + (isUpPressed() ? 1 : 0) * getV() - (isDownPressed() ? 1 : 0) * getV());
                }
            }
        }
        else if (obj instanceof ArrayList<?> && (!((ArrayList<?>) obj).isEmpty()) && ((ArrayList<?>) obj).get(0) instanceof TNT) {
            {
                for (Object tnt : (ArrayList<?>) obj) {
                    if (((TNT)tnt).getVisible() && ((TNT) tnt).getHitBox().intersects(hitBox)) {
                        this.visible = false;
                        ((TNT) tnt).visible = false;
                    }
                }
            }
        }
        else if (obj instanceof ArrayList<?> && (!((ArrayList<?>) obj).isEmpty()) && ((ArrayList<?>) obj).get(0) instanceof Sawblade) {
            //System.out.println("PING");
            for (Object saw : (ArrayList<?>) obj) {
                if (((Sawblade)saw).getVisible() && ((Sawblade) saw).getHitBox().intersects(hitBox))
                    this.visible = false;
            }
        }
        else if (obj instanceof ArrayList<?> && (!((ArrayList<?>) obj).isEmpty()) && ((ArrayList<?>) obj).get(0) instanceof Exit) {
            for (Object exit : (ArrayList<?>) obj) {
                if (((Exit) exit).getVisible() && ((Exit) exit).getHitBox().intersects(hitBox)) {
                    this.visible = false;
                    ((Exit) exit).visible = false;
                }
            }
        }
        else if (obj instanceof Koala) {
            if (((Koala) obj).getHitBox().intersects(hitBox) && (isDownPressed() || isUpPressed() || isLeftPressed() || isRightPressed())
                    && ((Koala) obj).getVisible()) {
                setX(getX() + (isLeftPressed() ? 1 : 0) * getV() - (isRightPressed() ? 1 : 0) * getV());
                setY(getY() + (isUpPressed() ? 1 : 0) * getV() - (isDownPressed() ? 1 : 0) * getV());
            }
        }
    }
    public void collideCheck(Object obj, ArrayList<TNT> tnts) {
        if (obj instanceof Switch) {
            if (((Switch) obj).getHitBox().intersects(hitBox) && (isDownPressed() || isUpPressed() || isLeftPressed() || isRightPressed())
                    && ((Switch) obj).getVisible()) {
                tnts.forEach(tnt->tnt.setVisible(false));
            }
        }
    }
    private void moveLeft() {
        koalaStandImage = koalaLeftImage;
        if(x>0){
            this.x -= v;
            hitBox.setLocation(x,y);
        }
    }

    private void moveRight() {
        koalaStandImage = koalaRightImage;
        if(x<1485) {
            this.x += v;
            hitBox.setLocation(x, y);
        }
    }

    private void moveUp() {
        koalaStandImage = koalaUpImage;
        if(y>0) {
            this.y -= v;
            hitBox.setLocation(x,y);
        }
    }

    private void moveDown() {
        koalaStandImage = koalaDownImage;
        if(y<790) {
            this.y += v;
            hitBox.setLocation(x,y);
        }
    }

    public int getV() {
        return v;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isUpPressed() {
        return UpPressed;
    }

    public boolean isDownPressed() {
        return DownPressed;
    }

    public boolean isLeftPressed() {
        return LeftPressed;
    }

    public boolean isRightPressed() {
        return RightPressed;
    }
    @Override
    public String toString() {
        return "x=" + x + ", y=" + y;
    }

    /*
    public void tankCollision(ArrayList<TNT> projs)
    {
        for(TNT i:projs)
        {
            if(i.getX() > x && i.getX() < x + 50 && i.getY() > y && i.getY() < y + 50)
                vis = false;
        }
    }
    */
}
