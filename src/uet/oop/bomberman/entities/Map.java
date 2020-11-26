package uet.oop.bomberman.entities;

import javax.swing.*;
import java.awt.*;

public class Map {
    private int x;
    private int y;
    public int bit;
    public static final int SIZE=32;
    public final Image[] MY_IMAGE={
            new ImageIcon(getClass().getResource("/res/sprites/grass.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/wall.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/brick.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/balloom_right1.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/doll_right1.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/player_right_1.png")).getImage(),
    };


    public Map(int x, int y, int bit) {
        this.x = x;
        this.y = y;
        this.bit = bit;
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

    public Rectangle getRect(){
        Rectangle rectangle=new Rectangle(x,y+15,SIZE-10,SIZE-10);
        return rectangle;
    }
    public void draw(Graphics2D g2d){
        if (bit!=0) {
            g2d.drawImage(MY_IMAGE[bit-1], x,y,SIZE+2,SIZE+2,null);
        }
        if (x== 0 || y==0 || x==13 || y==31){
            g2d.drawImage(MY_IMAGE[0],x,y,null);
        }
    }

}

