package FlappyBird.gameObjects;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class Bird implements Drawable, PhysicsObject {

    private BufferedImage img = null;
    private double[] rect = new double[4];
    private double v_vertical = 0.0;
    private static final double gravity = 0.0007;

    public Bird(double[] rect) {
        try {
            img = ImageIO.read(new File("images/bird.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        this.rect = rect;
        img = rescaleImage(85 ,85);
    }

    private BufferedImage rescaleImage(int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    @Override
    public void doPhysics(double deltaTime, double velocity) {
        this.v_vertical += gravity*deltaTime;
        this.rect[1] += v_vertical*deltaTime;
    }

    @Override
    public void draw(@NotNull Graphics2D g) {
        g.drawImage(img, (int)rect[0], (int)rect[1], img.getWidth(), img.getHeight(),null);
        //g.drawRect((int)rect[0], (int)rect[1], img.getWidth(), img.getHeight());
    }
}
