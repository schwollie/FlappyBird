package FlappyBird.gameObjects;

import FlappyBird.graphics.MainPanel;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Bird implements Drawable, PhysicsObject, KeyEventHandling {

    private SpriteImage spriteImage;
    private double[] rect;
    private double v_vertical = 0.0;

    public Bird(double[] rect, MainPanel panel) {
        this.rect = rect;
        spriteImage = new SpriteImage(panel, rect, "images/bird.png");
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

    public boolean doesCollide(ArrayList<Obstacle> obstacles) {
        for (Obstacle o : obstacles) {
            if (collisionLogic(o.getTop().getRect())) {
                return true;
            }
            if (collisionLogic(o.getBottom().getRect())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void handleKeyEvent(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            jump();
        }
    }

    @Override
    public void doPhysics(double deltaTime, double velocity, double gravity) {
        this.v_vertical += gravity * deltaTime;
        this.rect[1] += v_vertical * deltaTime;
    }

    @Override
    public void updateSprite() {
        spriteImage.updateRect(this.rect);
    }
}
