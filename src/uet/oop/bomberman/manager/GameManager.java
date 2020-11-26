package uet.oop.bomberman.manager;

import uet.oop.bomberman.entities.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.*;
import static uet.oop.bomberman.entities.Map.SIZE;
import static uet.oop.bomberman.view.Gui.H_FARME;
import static uet.oop.bomberman.view.Gui.W_FARME;

public class GameManager {
    private Bomber player;
    private ArrayList<Map> arrMapItem;
    private ArrayList<Bomb> arrBomb;
    private ArrayList<Boss> arrBoss;
    private ArrayList<BombBang> arrBombBang;
    private ArrayList<Item> arrItem;
    public static final int TIME_BANG=120;
    public static final int TIME_WAVE=15;
    private int timeDie;
    private boolean checkWin;
    private Random random=new Random();
    //private Clip clip1;
    private ArrayList<Integer> timeBoom;
    private ArrayList<Integer> timeWave;
    public final Image[] MY_IMAGE= new Image[]{
            new ImageIcon(getClass().getResource("res/sprites/grass.png")).getImage()
    };

    public boolean isCheckWin() {
        return checkWin;
    }

    public void setCheckWin(boolean checkWin) {
        this.checkWin = checkWin;
    }

    public void initGame(){

        timeBoom=new ArrayList<>();
        timeWave=new ArrayList<>();
        arrBomb=new ArrayList<>();
        arrBoss= new ArrayList<>();
        arrBombBang = new ArrayList<>();
        arrItem=new ArrayList<>();
        player=new Bomber(W_FARME/2,H_FARME-90-SIZE,Bomber.DOWN,1);
        arrMapItem =new ArrayList<>();
        readMap();
        initBoss();
        initItem();
    }

    public void initBoss(){
        for (int i=0;i<5;i++){
            int orient= random.nextInt(4);
            Map point= arrMapItem.get(random.nextInt(255));
            while (point.bit!=0) {
                point= arrMapItem.get(random.nextInt(255));
            }
            int xRaw=point.getX();
            int yRaw=point.getY();
            Boss boss=new Boss(xRaw,yRaw,orient);
            arrBoss.add(boss);
        }
    }

    public void initItem(){
        for (int i = 0; i< arrMapItem.size(); i++) {
            int show = random.nextInt(100) + 1;
            if (show > 80 && (arrMapItem.get(i).bit == 2
                    || arrMapItem.get(i).bit == 4 || arrMapItem.get(i).bit == 5)) {
                int xRaw = arrMapItem.get(i).getX();
                int yRaw = arrMapItem.get(i).getY();
                Item item = new Item(xRaw, yRaw);
                arrItem.add(item);
            }
        }
    }

    public void movePlayer(int newOrient){
        player.changeOrient(newOrient);
        player.move(arrMapItem,arrBomb,1);
        player.moveItem(arrItem);
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(MY_IMAGE[0],0,0,W_FARME,H_FARME,null);
        try {
            for (Bomb boom : arrBomb) {
                boom.draw(g2d);
            }
            for (BombBang boomBang : arrBombBang) {
                boomBang.draw(g2d, arrMapItem);
            }
            for (Item item:arrItem){
                item.draw(g2d);
            }
            for (Map mapItem : arrMapItem) {
                mapItem.draw(g2d);
            }

            for (Boss boss : arrBoss) {
                boss.drawBoss(g2d);
            }
            player.draw(g2d);
        }catch (ConcurrentModificationException e){

        }
    }

    public void myPlayerBoom(int t){
        Bomb boom= player.DatBoom(arrBomb);
        if (arrBomb.size()<player.getSoBoom()){
            arrBomb.add(boom);
            timeBoom.add(t);
        }
    }

    public boolean AI(int t){
        for (int i=arrBoss.size()-1;i>=0;i--){
            arrBoss.get(i).moveBoss(arrMapItem,arrBomb,t);
        }
        for (int i=0;i<arrBomb.size();i++){
            if (t-timeBoom.get(i) >=TIME_BANG){
                BombBang boomBang = arrBomb.get(i).boomBang();
                arrBomb.remove(i);

                arrBombBang.add(boomBang);
                timeBoom.remove(i);
                try {
                    boomBang.checkBoomToBoom(arrBomb, timeBoom);
                }catch (IndexOutOfBoundsException e){
                }
                timeWave.add(t);
            }
        }
        for (int i = 0; i< arrBombBang.size(); i++){
            arrBombBang.get(i).checkBoomToBoss(arrBoss);
            if (t-timeWave.get(i)>=TIME_WAVE){
                arrBombBang.remove(i);
                timeWave.remove(i);
            }
        }
        if (player.checkDieToBoss(arrBoss) == true){
;
            setCheckWin(false);
            return false;
        }
        for (int i = 0; i< arrBombBang.size(); i++){
            if(arrBombBang.get(i).checkBoomToPlayer(arrBombBang,player)==true){
                timeDie=t;
                setCheckWin(false);
                return false;
            }
        }
        if (arrBoss.isEmpty()){
            setCheckWin(true);
            return false;
        }
        return true;
    }

    public void readMap(){

        int intLine=0;
        try {
            String path = getClass().getResource("/res/level/map1.txt").getPath();
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line= br.readLine();
            while (line!=null){
                for (int i=0;i<line.length();i++){
                    arrMapItem.add(new Map(i*SIZE,intLine*SIZE,Integer.parseInt(String.valueOf(line.charAt(i)))));
                }
                line= br.readLine();
                intLine++;
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


