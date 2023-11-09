package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, rightPressed, leftPressed, enterPressed;

    GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if ( gamePanel.gameState == gamePanel.playState ) {
            if ( code == KeyEvent.VK_W || code == KeyEvent.VK_UP ) {
                upPressed = true;
            }

            if ( code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN ) {
                downPressed = true;
            }

            if ( code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT ) {
                rightPressed = true;
            }

            if ( code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT ) {
                leftPressed = true;
            }

            if ( code == KeyEvent.VK_ESCAPE ) {
                gamePanel.gameState = gamePanel.pauseState;
            }

            if ( code == KeyEvent.VK_ENTER ) {
                enterPressed = true;
            }
        } else if ( gamePanel.gameState == gamePanel.pauseState ) {
            if ( code == KeyEvent.VK_ESCAPE ) {
                gamePanel.gameState = gamePanel.playState;
            }
        } else if ( gamePanel.gameState == gamePanel.dialogueState ) {
            if ( code == KeyEvent.VK_ENTER ) {
                gamePanel.gameState = gamePanel.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if ( code == KeyEvent.VK_W || code == KeyEvent.VK_UP ) {
            upPressed = false;
        }

        if ( code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN ) {
            downPressed = false;
        }

        if ( code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT ) {
            rightPressed = false;
        }

        if ( code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT ) {
            leftPressed = false;
        }

//        if ( code == KeyEvent.VK_ENTER ) {
//            enterPressed = false;
//        }
    }
}
