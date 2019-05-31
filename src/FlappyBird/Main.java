package FlappyBird;

import FlappyBird.gameObjects.Bird;
import FlappyBird.gameObjects.Drawable;
import FlappyBird.gameObjects.Obstacle;
import FlappyBird.gameObjects.PhysicsObject;
import FlappyBird.graphics.DisplayTest;
import FlappyBird.graphics.MainPanel;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    private static final int width = 1300;
    private static final int height = 800;

    private static final double velocity = 0.35;
    private static final double gravity = 0.0024;
    private DisplayTest frame;
    private ArrayList<Bird> birds = new ArrayList<>();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private ArrayList<Drawable> drawables = new ArrayList<>();
    private ArrayList<PhysicsObject> physicsObjects = new ArrayList<>();

    public Main() {
        KeyEventListener k = new KeyEventListener();
        frame = new DisplayTest(width, height);
        frame.addKeyListener(k);

        MainPanel panel = new MainPanel();

        addBird(panel, k);
        addPipes(panel);

        frame.add(panel);
        frame.setVisible(true);
        frame.repaint();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        Thread t = new Thread(main::gameLoop);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addBird(MainPanel panel, KeyEventListener k) {

        Bird b = new Bird(new double[]{100, 200, 65, 46}, panel);
        k.addListener(b);
        drawables.add(b);
        physicsObjects.add(b);
        birds.add(b);
    }

    private void addPipes(MainPanel panel) {
        for (int i = 0; i < 3; i++) {
            Obstacle o = new Obstacle(new double[]{1000 + i * 433, 0}, panel);
            drawables.add(o);
            physicsObjects.add(o);
            obstacles.add(o);
        }
    }

    private void gameLoop() {
        double last_time = System.currentTimeMillis();

        boolean collision = false;

        while (!collision) {
            double time = System.currentTimeMillis();
            double delta_time = time - last_time;
            last_time = time;

            for (Drawable i : drawables) {
                i.updateSprite();
            }

            for (PhysicsObject p : physicsObjects) {
                p.doPhysics(delta_time, velocity, gravity);
            }

            for (Bird b : birds) {
                if (b.doesCollide(obstacles)) {
                    collision = true;
                }
            }

            EventQueue.invokeLater(frame::repaint);
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {

            }
        }
    }
}
