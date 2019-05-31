package FlappyBird.gameObjects;

import FlappyBird.AI.Matrix;
import FlappyBird.AI.NeuronalNetwork;
import FlappyBird.graphics.MainPanel;

import java.util.ArrayList;

public class AIBird implements Drawable, PhysicsObject, Comparable<AIBird> {

    private Sprite sprite;
    private double[] rect;
    private double v_vertical = 0.0;
    private NeuronalNetwork nn;
    private float fitness;

    public AIBird(double[] rect, MainPanel panel) {
        this.rect = rect;
        this.sprite = new Sprite(panel, rect, "images/bird.png");
        this.nn = new NeuronalNetwork(new int[]{5, 3, 1});
    }

    public void jump() {
        this.v_vertical = -0.7;
    }

    private boolean collisionLogic(double[] rect) {
        // Cond1. If A's left edge is to the right of the B's right edge, - then A is Totally to right Of B
        //Cond2. If A's right edge is to the left of the B's left edge, - then A is Totally to left Of B
        //Cond3. If A's top edge is below B's bottom edge, - then A is Totally below B
        //Cond4. If A's bottom edge is above B's top edge, - then A is Totally above B
        return !(this.rect[0] > rect[0] + rect[2]) && !(this.rect[0] + this.rect[2] < rect[0])
                && !(this.rect[1] > rect[1] + rect[3]) && !(this.rect[1] + this.rect[3] < rect[1]);

    }

    public void evaluate(Obstacle next, int screenHeight) {
        double posTop = next.getPipes()[0].getRect()[1] + next.getPipes()[0].getRect()[3];
        double posBottom = next.getPipes()[1].getRect()[1];
        //System.out.println(pos[1]);
        double[][] input_val = {{this.v_vertical, this.rect[1], posBottom, posTop, screenHeight}};
        Matrix input = new Matrix(input_val);

        Matrix output = this.nn.forward(input);

        if (output.getData()[0][0] > 0.5) {
            jump();
        } // else {do nothing}

    }

    public NeuronalNetwork getNn() {
        return this.nn;
    }

    public void setNn(NeuronalNetwork nn) {
        this.nn = nn;
    }

    public void setYPos(double y) {
        this.rect[1] = y;
    }

    public boolean doesCollide(ArrayList<Obstacle> obstacles) {
        for (Obstacle o : obstacles) {
            if (collisionLogic(o.getTop().getRect())) {
                return true;
            }
            if (collisionLogic(o.getBottom().getRect())) {
                return true;
            }
        }

        return this.rect[1] < 0 || this.rect[1] + this.rect[3] > 800;

    }

    public float getFitness() {
        return this.fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public void setVisibility(boolean visibility) {
        sprite.setVisibility(visibility);
    }

    @Override
    public int compareTo(AIBird b) {
        if (b.getFitness() > this.getFitness()) {
            return -1;
        } else if (b.getFitness() < this.getFitness()) {
            return +1;
        } else {
            return 0;
        }

    }


    @Override
    public void doPhysics(double deltaTime, double velocity, double gravity) {
        this.v_vertical += gravity * deltaTime;
        this.rect[1] += v_vertical * deltaTime;
        this.fitness += deltaTime;
    }

    @Override
    public void updateSprite() {
        sprite.updateRect(this.rect);
    }
}
