package FlappyBird.graphics;

import FlappyBird.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.*;

public class Display extends JFrame implements Runnable{

    private Thread repaintThread;

    public Display(int w, int h) {
        super("Flappy Bird");
        setSize(w, h);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        this.repaintThread = new Thread(this);
        this.repaintThread.start();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Game game = Game.gameInst;
        ArrayList<Drawable> drawables = game.drawables;

        for (Drawable d : drawables) {
            d.draw(g2);
        }

    }

    public void run() {
        while(this.isVisible()) {
            this.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
