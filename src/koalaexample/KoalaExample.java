package koalaexample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.net.URL;
import static javax.imageio.ImageIO.read;



public class KoalaExample extends JPanel{
    public static final int WORLD_WIDTH = 1485;
    public static final int WORLD_HEIGHT = 790;
    public static final int SCREEN_WIDTH = 1485;
    public static final int SCREEN_HEIGHT = 790;
    int xleft,yleft,xright,yright;
    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jFrame;
    private ArrayList<Koala> koalas;
    private ArrayList<Wall> thewall;
    public ArrayList<TNT> tnts;
    private ArrayList<Sawblade> saws;
    private ArrayList<Exit> exits;
    private Detonator det;
    private Detonator blueswitchy;
    private Detonator yellowswitchy;
    private Background bg;
    private Message message1;
    private Message message2;
    private Message message3;
    private static Clip clip;
    AudioInputStream audioInputStream;

    public static void main(String[]args) throws InterruptedException {
        KoalaExample koalaExample = new KoalaExample();
        koalaExample.init();
        try {
            while (true) {
                koalaExample.koalas.forEach(Koala::update);
                koalaExample.saws.forEach(Sawblade::update);
                koalaExample.koalas.get(0).collideCheck(koalaExample.koalas.get(1));
                if(koalaExample.saws!=null) {
                    koalaExample.koalas.get(0).collideCheck(koalaExample.saws);
                    koalaExample.koalas.get(1).collideCheck(koalaExample.saws);
                }
                //koalaExample.koalas.get(1).collideCheck(koalaExample.koalas.get(0));
                if(koalaExample.thewall!=null) {
                    koalaExample.koalas.get(0).collideCheck(koalaExample.thewall);
                    koalaExample.koalas.get(1).collideCheck(koalaExample.thewall);
                }
                if(koalaExample.tnts!=null) {
                    koalaExample.koalas.get(0).collideCheck(koalaExample.tnts);
                    koalaExample.koalas.get(1).collideCheck(koalaExample.tnts);
                }
                if(koalaExample.exits!=null) {
                    koalaExample.koalas.get(0).collideCheck(koalaExample.exits);
                    koalaExample.koalas.get(1).collideCheck(koalaExample.exits);
                }
                koalaExample.koalas.get(0).collideCheck(koalaExample.det,koalaExample.tnts);
                koalaExample.koalas.get(1).collideCheck(koalaExample.det,koalaExample.tnts);
                if(!koalaExample.exits.get(0).getVisible() && !koalaExample.exits.get(1).getVisible())
                    koalaExample.message2.setVisible(true);
                else if(((!koalaExample.koalas.get(0).getVisible() || (!koalaExample.koalas.get(1).getVisible())) && (koalaExample.exits.get(0).getVisible() && koalaExample.exits.get(1).getVisible())
                )|| ((!koalaExample.koalas.get(0).getVisible() && (!koalaExample.koalas.get(1).getVisible())) && (koalaExample.exits.get(0).getVisible()||koalaExample.exits.get(1).getVisible())))
                    koalaExample.message1.setVisible(true);
                koalaExample.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch(InterruptedException ignored)
        {
            System.out.println(ignored);
        }
    }

    private void init() {
        koalas = new ArrayList<Koala>();
        thewall = new ArrayList<Wall>();
        saws = new ArrayList<Sawblade>();
        tnts = new ArrayList<TNT>();
        exits = new ArrayList<Exit>();
        this.jFrame = new JFrame("Koalaborate");
        this.world = new BufferedImage(KoalaExample.WORLD_WIDTH, KoalaExample.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage koalaImage = null;
        BufferedImage koalaLeftImage = null;
        BufferedImage koalaRightImage = null;
        BufferedImage koalaUpImage = null;
        BufferedImage koalaDownImage = null;
        BufferedImage backgroundImage = null;
        BufferedImage wall1Image = null;
        BufferedImage vertsawImage = null;
        BufferedImage horizsawImage = null;
        BufferedImage tntImage = null;
        BufferedImage exitImage = null;
        BufferedImage redswitchImage = null;
        BufferedImage winImage = null;
        BufferedImage loseImage = null;
        BufferedImage koal8Image = null;

        try{
            koalaImage = read(KoalaExample.class.getClassLoader().getResource("Koala_stand.png"));
            koalaLeftImage = read(KoalaExample.class.getClassLoader().getResource("Koala_left.png"));
            koalaRightImage = read(KoalaExample.class.getClassLoader().getResource("Koala_right.png"));
            koalaUpImage = read(KoalaExample.class.getClassLoader().getResource("Koala_up.png"));
            koalaDownImage = read(KoalaExample.class.getClassLoader().getResource("Koala_down.png"));
            backgroundImage = read(KoalaExample.class.getClassLoader().getResource("Background.jpg"));
            wall1Image = read(KoalaExample.class.getClassLoader().getResource("Wall1.jpg"));
            vertsawImage = read(KoalaExample.class.getClassLoader().getResource("Saw_vertical.png"));
            horizsawImage = read(KoalaExample.class.getClassLoader().getResource("Saw_horizontal.png"));
            tntImage = read(KoalaExample.class.getClassLoader().getResource("TNT.png"));
            exitImage = read(KoalaExample.class.getClassLoader().getResource("Exit1.png"));
            redswitchImage = read(KoalaExample.class.getClassLoader().getResource("Detonator.png"));
            winImage = read(KoalaExample.class.getClassLoader().getResource("Congratulation.gif"));
            loseImage = read(KoalaExample.class.getClassLoader().getResource("Restart.png"));
            koal8Image = read(KoalaExample.class.getClassLoader().getResource("Title.jpg"));



            InputStreamReader isr = new InputStreamReader(KoalaExample.class.getClassLoader().getResourceAsStream("maps/map2"));
            BufferedReader mapReader = new BufferedReader(isr);
            String row = mapReader.readLine();
            if(row == null){
                throw new IOException("No data in file");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);
            for(int i = 0;i < numRows; i++)
            {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int j = 0;j < numCols; j++)
                {
                    //System.out.println(i+j);
                    //2-> wall, 4->tnt, 6->horisaw, 7->vertsaw
                    switch(mapInfo[j]){
                        case "2":
                            thewall.add(new Wall(j*wall1Image.getWidth(),i*wall1Image.getHeight(),true,wall1Image));
                            //System.out.println(wall1Image.getHeight());
                            //System.out.println(wall1Image.getWidth());
                            break;
                        case "4":
                            tnts.add(new TNT(j*wall1Image.getWidth(),i*wall1Image.getHeight(),true, tntImage));
                            break;
                        case "5":
                            det = new Detonator(j*wall1Image.getWidth(),i*wall1Image.getHeight(),true, redswitchImage);
                            break;
                        case "6":
                            saws.add(new HorizSaw(j*wall1Image.getWidth()-30,i*wall1Image.getHeight(),3,1000-100*(i-5),true,true,horizsawImage));
                            break;
                        case "7":
                            saws.add(new VertSaw(j*wall1Image.getWidth(),i*wall1Image.getHeight(),3,600,true,true,vertsawImage));
                            break;
                        case "9":
                            exits.add(new Exit(j*wall1Image.getWidth(),i*wall1Image.getHeight(),true,exitImage));
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        bg = new Background(backgroundImage);
        message1 = new Message(SCREEN_WIDTH/2-loseImage.getWidth()/2,SCREEN_HEIGHT/2-loseImage.getHeight()/2,false,loseImage);
        message2 = new Message(SCREEN_WIDTH/2-winImage.getWidth()/2,SCREEN_HEIGHT/2-winImage.getHeight()/2,false,winImage);
        message3 = new Message(SCREEN_WIDTH-363,2,true,koal8Image);
        koalas.add(new Koala(100,100,true,koalaImage, koalaLeftImage, koalaRightImage, koalaUpImage, koalaDownImage));
        koalas.add(new Koala(1000,350,true,koalaImage, koalaLeftImage, koalaRightImage, koalaUpImage, koalaDownImage));
        KoalaControl koalaOneControl = new KoalaControl(koalas.get(0), KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT);
        KoalaControl koalaTwoControl = new KoalaControl(koalas.get(1), KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT);
        //System.out.println("hi");
        this.jFrame.addKeyListener(koalaOneControl);
        this.jFrame.addKeyListener(koalaTwoControl);
        this.jFrame.setLayout(new BorderLayout());
        this.jFrame.add(this);
        //this.jFrame.addKeyListener(tankOneControl);
        //this.jFrame.addKeyListener(tankTwoControl);
        this.jFrame.setSize(KoalaExample.SCREEN_WIDTH, KoalaExample.SCREEN_HEIGHT + 30);
        this.jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        buffer = world.createGraphics();
        bg.drawImage(buffer);
        det.drawImage(buffer);
        thewall.forEach(wall -> wall.drawImage(buffer));
        saws.forEach(saw -> saw.drawImage(buffer));
        tnts.forEach(tnt -> tnt.drawImage(buffer));
        exits.forEach(exit -> exit.drawImage(buffer));
        koalas.forEach(koala -> koala.drawImage(buffer));
        message1.drawImage(buffer);
        message2.drawImage(buffer);
        message3.drawImage(buffer);
        g2.drawImage(world,0,0,null);
    }
}
