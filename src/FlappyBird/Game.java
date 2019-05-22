package FlappyBird;

import FlappyBird.gameObjects.Bird;
import FlappyBird.graphics.Display;

import java.awt.*;
import java.util.ArrayList;

public class Game {

    public static Game gameInst;
    private Bird b;
    public ArrayList<Drawable> drawables = new ArrayList<>();

    public Game(int width, int height) {
        b = new Bird();
        drawables.add(b);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Display(width, height);
            }
        });

        gameLoop();
    }

    private void gameLoop() {
        gameInst = this;
    }
}
