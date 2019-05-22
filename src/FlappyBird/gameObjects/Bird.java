package FlappyBird.gameObjects;

import FlappyBird.Drawable;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class Bird implements Drawable {

    private BufferedImage img = null;
    private double[] rect = new double[4];

    public Bird() {
        try {
            img = ImageIO.read(new File("images/bird.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        img = rescaleImage(100 ,100);
    }

    private BufferedImage rescaleImage(int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public void draw(@NotNull Graphics2D g) {
        g.drawImage(img, 100, 100, img.getWidth(), img.getHeight(),null);
    }
}
