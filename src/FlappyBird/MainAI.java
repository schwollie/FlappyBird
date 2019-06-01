package FlappyBird;

import FlappyBird.AI.NeuronalNetwork;
import FlappyBird.gameObjects.AIBird;
import FlappyBird.gameObjects.Drawable;
import FlappyBird.gameObjects.Obstacle;
import FlappyBird.gameObjects.PhysicsObject;
import FlappyBird.graphics.DisplayTest;
import FlappyBird.graphics.MainPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MainAI {

    private static final int width = 1300;
    private static final int height = 800;
    private static final int birdX = 100;
    private static final int birdY = 200;

    private static final double simulationSpeed = 1.1;
    private static final double velocity = 0.35;
    private static final double gravity = 0.0026;
    private DisplayTest frame;
    private ArrayList<AIBird> birds = new ArrayList<>();
    private ArrayList<AIBird> birds_all = new ArrayList<>();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private ArrayList<Drawable> drawables = new ArrayList<>();
    private ArrayList<PhysicsObject> physicsObjects = new ArrayList<>();

    public MainAI() {
        frame = new DisplayTest(width, height);

        MainPanel panel = new MainPanel();

        addBird(panel);
        addPipes(panel);

        frame.add(panel);
        frame.setVisible(true);
        frame.repaint();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MainAI main = new MainAI();
        Thread t = new Thread(main::gameLoop);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addBird(MainPanel panel) {
        for (int i = 0; i < 300; i++) {
            AIBird b = new AIBird(new double[]{birdX, birdY, 65, 46}, panel);
            birds.add(b);
            birds_all.add(b);
        }

    }

    private void addPipes(MainPanel panel) {
        Random rnd = new Random();
        for (int i = 0; i < 3; i++) {
            Obstacle o = new Obstacle(new double[]{1000 + i * 433, rnd.nextInt(50) - 25}, panel);
            drawables.add(o);
            physicsObjects.add(o);
            obstacles.add(o);
        }
    }

    private void resetPipes() {
        Random rnd = new Random();
        int i = 0;
        for (Obstacle o : obstacles) {
            o.setPos(new double[]{1000 + i * 433, rnd.nextInt(100) - 50});
            i++;
        }
    }

    private void gameLoop() {
        double last_time = System.currentTimeMillis();

        while (true) {
            while (birds.size() > 0) {
                double time = System.currentTimeMillis();
                double delta_time = (time - last_time) * simulationSpeed;
                last_time = time;

                // Pipes
                for (Drawable i : drawables) {
                    i.updateSprite();
                }

                for (PhysicsObject p : physicsObjects) {
                    p.doPhysics(delta_time, velocity, gravity);
                }

                //Birds
                birdAction(); // ai move

                // Birds (draw/Physics/Collision)
                ArrayList<AIBird> birds2remove = new ArrayList<>();
                for (AIBird bird : birds) {
                    bird.updateSprite();
                    bird.doPhysics(delta_time, velocity, gravity);
                    bird.setVisibility(true);

                    if (bird.doesCollide(obstacles)) {
                        birds2remove.add(bird);
                        bird.setVisibility(false);
                    }
                }

                birds.removeAll(birds2remove);

                // Repaint
                EventQueue.invokeLater(frame::repaint);
                try {
                    Thread.sleep(1000 / 200);
                } catch (InterruptedException e) {

                }


            }
            naturalSelection();
        }
    }

    private void birdAction() {
        Obstacle o = getNextObstacle();
        for (AIBird b : birds) {
            b.evaluate(o, height);
        }
    }

    private Obstacle getNextObstacle() {
        Obstacle next = obstacles.get(0);
        for (Obstacle o : obstacles) {
            if (o.getPos()[0] + o.getWidth() >= birdX && o.getPos()[0] - birdX < next.getPos()[0] - birdX) {
                //System.out.println(o.getWidth());
                next = o;
            }
        }
        return next;
    }


    private void naturalSelection() {
        // natural Selection + Evolution
        birds_all.sort(((o1, o2) -> o1.compareTo(o2))); // best bird at end of list

        ArrayList<AIBird> bestBirds = new ArrayList<>();
        bestBirds.add(birds_all.get(birds_all.size() - 1));
        bestBirds.add(birds_all.get(birds_all.size() - 2));

        Random rnd = new Random();
        for (int i = 0; i < birds_all.size() - 2; i++) {
            AIBird bird = birds_all.get(i);
            int rand = rnd.nextInt(100);
            if (rand < 30) {
                bird.setNn(NeuronalNetwork.breed(birds_all.get(rnd.nextInt(birds_all.size())).getNn(),
                        bestBirds.get(rnd.nextInt(bestBirds.size())).getNn()));
            } else if (rand < 80) {
                bird.setNn(bestBirds.get(rnd.nextInt(bestBirds.size())).getNn());
                bird.setNn(NeuronalNetwork.mutate(bird.getNn()));
            } else if (rand < 90) {
                bird.setNn(bestBirds.get(rnd.nextInt(bestBirds.size())).getNn());
            } else {
                bird.getNn().randomWeights();
            }
        }


        //restart scenario
        for (AIBird b : birds_all) {
            birds.add(b);
        }

        for (AIBird b : birds_all) {
            b.setYPos(birdY);
            b.setFitness(0);
            b.setVisibility(true);
        }

        resetPipes();
    }
}

