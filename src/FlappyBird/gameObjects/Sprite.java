package FlappyBird.gameObjects;

import FlappyBird.graphics.MainPanel;

import java.awt.*;

public abstract class Sprite {

    private boolean visible = true;

    public Sprite(MainPanel panel) {
        panel.addSprite(this);
    }

    public void paintComponent(Graphics g) {
        if (this.visible) {
            draw(g);
        }
    }

    public abstract void draw(Graphics g);

    public void setVisibility(boolean visibility) {
        this.visible = visibility;
    }
}
