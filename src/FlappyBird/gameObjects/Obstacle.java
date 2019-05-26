package FlappyBird.gameObjects;

import FlappyBird.graphics.MainPanel;

import java.util.Random;

public class Obstacle implements Drawable, PhysicsObject {

    private Pipe top;
    private Pipe bottom;
    private double[] pos;
    private static final int distance = 100;
    private static final int height_bounds = 300;
    private int offset = 150;

    public Obstacle(double[] pos, MainPanel panel) {
        this.pos = pos;

        top = new Pipe(1, pos, panel);
        bottom = new Pipe(0, pos, panel);

        double[] rectTop = top.getRect();
        top.setRect(new double[] {this.pos[0], this.pos[1]-offset-distance,
                rectTop[2], rectTop[3]});

        double[] rectBottom = bottom.getRect();
        bottom.setRect(new double[] {this.pos[0], this.pos[1]+rectBottom[3]-offset+distance,
                rectBottom[2], rectBottom[3]});

    }

    public boolean collides_with(double[] rect) {
        return false;
    }

    private void updatePipes() {
        top.setRect(new double[]{pos[0], pos[1] - offset - distance, top.getRect()[2], top.getRect()[3]});
        bottom.setRect(new double[]{pos[0], pos[1] + bottom.getRect()[3] - offset + distance,
                bottom.getRect()[2], bottom.getRect()[3]});
    }

    private void changeHeight() {
        Random rnd = new Random();

        int heightDelta = rnd.nextInt(height_bounds) - height_bounds / 2;

        this.pos[1] = heightDelta;

        /*double[] rectTop = top.getRect();
        top.setRect(new double[] {this.pos[0], this.pos[1]-offset-distance,
                rectTop[2]+heightDelta, rectTop[3]});

        double[] rectBottom = bottom.getRect();
        bottom.setRect(new double[] {this.pos[0], this.pos[1]+rectBottom[3]-offset+distance+heightDelta,
                rectBottom[2], rectBottom[3]});*/
    }

    private void checkOutOfBounds() {
        if (this.pos[0] < -100) {
            this.pos[0] = 1300;
            changeHeight();
        }
    }

    @Override
    public void doPhysics(double deltaTime, double velocity, double gravity) {
        checkOutOfBounds();
        pos[0] -= deltaTime*velocity;
        updatePipes();
    }

    @Override
    public void updateSprite() {
        updatePipes();
        top.updateSprite();
        bottom.updateSprite();
    }
}
