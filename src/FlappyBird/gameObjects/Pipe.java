package FlappyBird.gameObjects;

import FlappyBird.Drawable;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pipe implements Drawable {

    private BufferedImage img = null;
    private double[] rect;

    public Pipe(int direction, double[] rect) {
        this.rect = rect;

        if (direction==0)
            try {
                img = ImageIO.read(new File("images/pipeUp.png"));
            } catch(IOException e) {
                e.printStackTrace();
            }
        else if(direction==1)
            try {
                img = ImageIO.read(new File("images/pipeDown.png"));
            } catch(IOException e) {
                e.printStackTrace();
            }

        img = rescaleImage((int)(img.getWidth()/3) ,(int)(img.getHeight()/3));
    }

    private BufferedImage rescaleImage(int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public void draw(@NotNull Graphics2D g) {
        g.drawImage(img, (int)this.rect[0], (int)this.rect[1], img.getWidth(), img.getHeight(),null);
    }
}
