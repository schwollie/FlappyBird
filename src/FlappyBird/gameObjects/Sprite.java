package FlappyBird.gameObjects;

import FlappyBird.graphics.MainPanel;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {

    private BufferedImage img;
    private double[] rect;

    public Sprite(MainPanel panel, double[] rect, String filename) {
        super();
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = rescaleImage((int) rect[2], (int) rect[3]);
        this.rect = rect;

        panel.addSprite(this);
    }

    private BufferedImage rescaleImage(int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public void paintComponent(@NotNull Graphics g) {
        g.drawImage(img, (int) rect[0], (int) rect[1], img.getWidth(), img.getHeight(), null);
    }

    public double[] getRect() {
        return rect;
    }

    public void updateRect(@NotNull double[] rect) {
        if ((int) this.rect[2] != (int) rect[2] || (int) this.rect[3] != (int) rect[3]) {
            rescaleImage((int) rect[2], (int) rect[3]);
        }

        this.rect = rect;

    }

}
