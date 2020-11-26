package uet.oop.bomberman.view;

import javax.swing.*;

public class Gui extends JFrame {
    public static final int W_FARME = 770;
    public static final int H_FARME = 710;

    public Gui(){
        initGui();
    }

    private void initGui() {
        setTitle("Boom");
        setSize(W_FARME,H_FARME);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Container());
    }
}