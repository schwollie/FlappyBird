package FlappyBird;

import FlappyBird.gameObjects.*;
import FlappyBird.graphics.*;

import java.awt.*;
import java.util.ArrayList;

public class Game {

    private Bird b;
    public ArrayList<Drawable> drawables = new ArrayList<>();
    public ArrayList<PhysicsObject> physicsObjects = new ArrayList<>();
    private int width, height;
    private static final int v = 1;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;


        add_Bird();
        addPipes();


        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Display(Game.this, width, height);
            }
        });

        gameLoop();
    }

    private void add_Bird() {
        b = new Bird(new double[] {100, 100, 100, 100});
        drawables.add(b);
        physicsObjects.add(b);
    }

    private void addPipes() {
        Obstacle o = new Obstacle(new double[] {100, 0});
        drawables.add(o);
        physicsObjects.add(o);
    }

    private void gameLoop() {
        double last_time = System.currentTimeMillis();

        while(true) {
            double time = System.currentTimeMillis();
            double delta_time = time-last_time;
            last_time = time;

            for (PhysicsObject o : physicsObjects) {
                o.doPhysics(delta_time, v);
            }

            System.out.println(delta_time);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
