package uet.oop.bomberman.entities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import static uet.oop.bomberman.entities.Map.SIZE;

public class Boss extends Bomber {
    private int x;
    private int y;
    private int orient;
    private Image image;
    private Random random= new Random();
    private int imageIndex=0;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    public final Image[] BOSSLEFT ={
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_left1.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_left2.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_left3.png")).getImage(),

    };
    public final Image[] BOSSRIGHT={
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_right1.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_right2.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_right3.png")).getImage(),
    };

    public final Image[] BOSSUP={
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_up1.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_up2.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_up3.png")).getImage(),
    };
    public final Image[] BOSSDOWN={
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_down1.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_down2.png")).getImage(),
            new ImageIcon(getClass().getResource("/res/sprites/kondoria_down3.png")).getImage(),
    };


    public Boss(int x, int y, int orient) {
        super();
        this.x = x;
        this.y = y;
        this.orient = orient;
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

    public void changeOrient(int newOrient){
        orient=newOrient;
    }

    public void creatOrient(){
        int percent= random.nextInt(100);
        if (percent>95){
            int newOrient=random.nextInt(4);
            changeOrient(newOrient);
            switch (orient){
                case LEFT:{
                    if (!isIrun()){
                        image=BOSSLEFT[0];
                    }
                    else {
                        imageIndex++;
                        image=BOSSLEFT[imageIndex/3%BOSSLEFT.length];
                    }
                    break;
                }
                case RIGHT:{
                    if (!isIrun()){
                        image=BOSSRIGHT[0];
                    }
                    else {
                        imageIndex++;
                        image=BOSSRIGHT[imageIndex/3%BOSSRIGHT.length];
                    }
                    break;
                }
                case UP:{
                    if (!isIrun()){
                        image=BOSSUP[0];
                    }
                    else {
                        imageIndex++;
                        image=BOSSUP[imageIndex/3%BOSSUP.length];
                    }
                    break;
                }
                case DOWN:{
                    if (!isIrun()){
                        image=BOSSDOWN[0];
                    }
                    else {
                        imageIndex++;
                        image=BOSSDOWN[imageIndex/3%BOSSDOWN.length];
                    }
                }
                break;
            }
            imageIndex++;
        }
    }

    public void drawBoss(Graphics2D g2d){

        g2d.drawImage(image,x,y,SIZE,SIZE,null);

    }


    public boolean checkMoveBoom(ArrayList<Bomb> arrBoom){
        for (int i=0;i<arrBoom.size();i++){
            Rectangle rectangle= getRect().intersection(arrBoom.get(i).getRect());
            if (rectangle.isEmpty()==false && arrBoom.get(i).isCheckBoom()==0){
                return false;
            }
        }
        return true;
    }

    public void moveBoss(ArrayList<Map> arrMapItem, ArrayList<Bomb> arrBoom, int t) {
        int speed = 2;
        int xRaw = x;
        int yRaw = y;
        switch (orient) {
            case LEFT:
                xRaw -= speed;
                break;
            case RIGHT:
                xRaw += speed;
                break;
            case UP:
                yRaw -= speed;
                break;
            case DOWN:
                yRaw += speed;
            default:
        }
        int xRaw1 = x;
        int yRaw1 = y;
        x = xRaw;
        y = yRaw;
        boolean checkMoveBoss = checkMove(arrMapItem);
        boolean checkMoveBossBoom= checkMoveBoom(arrBoom);
        if (checkMoveBoss==true){
            x=xRaw1;
            y=yRaw1;
        }
        if (checkMoveBossBoom==false){
            x=xRaw1;
            y=yRaw1;
        }
        creatOrient();
    }

    public boolean checkMove(ArrayList<Map> arrMapItem) {
        for (Map mapItem : arrMapItem){
            if (mapItem.bit == 5 || mapItem.bit == 1 || mapItem.bit == 2 || mapItem.bit == 3 ||
                    mapItem.bit == 4 || mapItem.bit== 6 ||  mapItem.bit== 7 || mapItem.bit== 8
                    || mapItem.bit== 9) {
                Rectangle rectangle = getRect().intersection(mapItem.getRect());
                if (rectangle.isEmpty() == false) {
                    return true;
                }
            }
        }
        return  false;
    }


    public Rectangle getRect() {
        Rectangle rectangle= new Rectangle(x,y+15,SIZE-10,SIZE-5);
        return rectangle;
    }
}

