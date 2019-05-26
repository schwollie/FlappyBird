package FlappyBird.graphics;

import FlappyBird.gameObjects.Sprite;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private ArrayList<Sprite> sprites = new ArrayList<>();

    @Override
    public void paintComponent(@NotNull Graphics g) {
        for (Sprite s : sprites) {
            s.paintComponent(g);
        }
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }
}
