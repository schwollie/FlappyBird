package FlappyBird.gameObjects;

import FlappyBird.graphics.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteImage extends Sprite {

    private BufferedImage img;
    private double[] rect;
    private boolean visible = true;

    public SpriteImage(MainPanel panel, double[] rect, String filename) {
        super(panel);
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = rescaleImage((int) rect[2], (int) rect[3]);
        this.rect = rect;
    }

    private BufferedImage rescaleImage(int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public void draw(Graphics g) {
        g.drawImage(img, (int) rect[0], (int) rect[1], img.getWidth(), img.getHeight(), null);
    }

    public double[] getRect() {
        return rect;
    }

    public void updateRect(double[] rect) {
        if ((int) this.rect[2] != (int) rect[2] || (int) this.rect[3] != (int) rect[3]) {
            rescaleImage((int) rect[2], (int) rect[3]);
        }

        this.rect = rect;

    }


}
