package FlappyBird.gameObjects;

import FlappyBird.graphics.MainPanel;

public class Pipe implements Drawable {

    private SpriteImage spriteImage;
    private double[] rect;

    public Pipe(int direction, double[] pos, MainPanel panel) {
        this.rect = new double[4];
        this.rect[0] = pos[0]; this.rect[1] = pos[1];
        this.rect[2] = 81;
        this.rect[3] = 497; // right image dimensions

        String filename;
        if (direction == 0) {
            filename = "images/pipeUp.png";
        } else {
            filename = "images/pipeDown.png";
        }

        spriteImage = new SpriteImage(panel, this.rect, filename);
    }

    public double[] getRect() {
        return this.rect;
    }

    public void setRect(double[] rect) {
        this.rect = rect;
    }

    @Override
    public void updateSprite() {
        spriteImage.updateRect(this.rect);
    }
}
