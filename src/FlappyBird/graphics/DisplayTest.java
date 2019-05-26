package FlappyBird.graphics;

import javax.swing.*;

public class DisplayTest extends JFrame {

    public DisplayTest(int width, int height) {
        super();
        this.setTitle("Flappy Bird");
        this.setSize(width, height);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        //this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setResizable(false);
    }
}
