package FlappyBird;

import FlappyBird.gameObjects.*;
import FlappyBird.graphics.*;

import java.awt.*;
import java.util.ArrayList;

public class Game {

    private Bird b;
    public ArrayList<Drawable> drawables = new ArrayList<>();

    public Game(int width, int height) {
        b = new Bird();
        Pipe p = new Pipe(0, new double[] {0, 200, 100, 100});
        drawables.add(b);
        drawables.add(p);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Display(Game.this, width, height);
            }
        });

        gameLoop();
    }

    private void gameLoop() {

    }
}
