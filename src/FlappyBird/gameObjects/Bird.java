package FlappyBird.gameObjects;

import FlappyBird.graphics.MainPanel;

import java.awt.event.KeyEvent;

public class Bird implements Drawable, PhysicsObject, KeyEventHandling {

    private Sprite sprite;
    private double[] rect;
    private double v_vertical = 0.0;

    public Bird(double[] rect, MainPanel panel) {
        this.rect = rect;
        sprite = new Sprite(panel, rect, "images/bird.png");
    }

    public void jump() {
        this.v_vertical = -0.7;
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
        this.v_vertical += gravity*deltaTime;
        this.rect[1] += v_vertical*deltaTime;
    }

    @Override
    public void updateSprite() {
        sprite.updateRect(this.rect);
    }
}
