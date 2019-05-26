package FlappyBird;

import FlappyBird.gameObjects.KeyEventHandling;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyEventListener implements KeyListener {

    public ArrayList<KeyEventHandling> keyEventListeners = new ArrayList<>();

    public void addListener(KeyEventHandling k) {
        keyEventListeners.add(k);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (KeyEventHandling k : keyEventListeners) {
            k.handleKeyEvent(e);
        }
    }
}
