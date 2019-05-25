package FlappyBird.graphics;

import FlappyBird.*;
import FlappyBird.gameObjects.Drawable;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Display extends JFrame implements Runnable{

    private Thread repaintThread;
    private Game game;

    public Display(Game game, int w, int h) {
        super("Flappy Bird");

        this.game = game;

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

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);

        ArrayList<Drawable> drawables = game.drawables;

        for (Drawable d : drawables) {
            d.draw(g2);
        }


    }
    public void run() {
        while(this.isVisible()) {
            this.invalidate();
            this.validate();
            this.repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
