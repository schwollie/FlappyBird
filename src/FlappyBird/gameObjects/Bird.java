package FlappyBird.gameObjects;

import FlappyBird.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bird implements Drawable {

    BufferedImage img = null;

    public Bird() {
        try {
            img = ImageIO.read(new File("images/bird.png"));
        } catch(IOException e) {
            System.out.println("File not Found");
        }


    }

    public void draw(Graphics2D g) {
        g.drawImage(img, 100, 100, 100, 100,null);
    }
}
