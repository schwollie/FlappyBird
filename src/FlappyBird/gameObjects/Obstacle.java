package FlappyBird.gameObjects;

import java.awt.*;
import java.util.Random;

public class Obstacle implements Drawable, PhysicsObject {

    private Pipe top;
    private Pipe bottom;
    private double[] pos;
    private static final int distance = 75;
    private static final int height_bound = 300;
    private int offset = 150;

    public Obstacle(double[] pos) {
        this.pos = pos;

        top = new Pipe(1, pos);
        bottom = new Pipe(0, pos);

        double[] rectTop = top.getRect();
        top.setRect(new double[] {this.pos[0], this.pos[1]-offset-distance,
                rectTop[2], rectTop[3]});

        double[] rectBottom = bottom.getRect();
        System.out.println(rectBottom[3]);
        bottom.setRect(new double[] {this.pos[0], this.pos[1]+rectBottom[3]-offset+distance,
                rectBottom[2], rectBottom[3]});

    }

    public void changeHeight() {
        Random rnd = new Random();
    }


    public boolean collides_with(double[] rect) {
        return false;
    }

    private void updatePipes() {
        top.setRect(new double[] {pos[0], top.getRect()[1], top.getRect()[2], top.getRect()[3]});
        bottom.setRect(new double[] {pos[0], bottom.getRect()[1], bottom.getRect()[2], bottom.getRect()[3]});
    }

    @Override
    public void doPhysics(double deltaTime, double velocity) {
        pos[0] -= deltaTime*velocity;
        updatePipes();
    }

    @Override
    public void draw(Graphics2D g) {
        top.draw(g);
        bottom.draw(g);
    }
}
