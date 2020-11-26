package uet.oop.bomberman.entities;

import javax.swing.*;
import java.awt.*;
import javafx.scene.image.ImageView;
import uet.oop.bomberman.graphics.*;
import static uet.oop.bomberman.entities.Map.SIZE;

public class Bomb {
    private int x;
    private int y;
    public int checkBoom;
    private Image image;
    private int imageIndex=0;
    private int lenghBoom;
    public final Image[] IMAGE_BOOM={
            new ImageIcon(getClass().getResource("/res/sprites/bomb.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/bomb2.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/bomb3.png")).getImage(),
    };

    public Bomb(int x, int y,int lenghBoom) {
        this.x = x-20;
        this.y = y;
        this.lenghBoom=lenghBoom;
        this.checkBoom=1;
        this.image=IMAGE_BOOM[0];
        boomBang();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int isCheckBoom() {
        return checkBoom;
    }

    public void setCheckBoom(int checkBoom) {
        this.checkBoom = checkBoom;
    }

    public void draw(Graphics2D g2d){
        imageIndex++;
        image = IMAGE_BOOM[imageIndex/5 %IMAGE_BOOM.length];
        g2d.drawImage(image,x,y,SIZE,SIZE,null);
    }

    public Rectangle getRect(){
        Rectangle rectangle= new Rectangle(x+15,y+15,SIZE-10,SIZE-10);
        return  rectangle;
    }

    public BombBang boomBang(){
        int xRaw= x-10;
        int yRaw= y-10;
        int lenghWave=this.lenghBoom;
        BombBang boomBang = new BombBang(xRaw,yRaw,lenghWave);
        return boomBang;
    }
}


