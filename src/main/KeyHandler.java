package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean upPressed, downPressed, rightPressed, leftPressed, enterPressed, spacePressed;

    public KeyHandler(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if ( gamePanel.gameState == gamePanel.titleState ) {
            titleState(code);
        } else if ( gamePanel.gameState == gamePanel.playState ) {
            playState(code);
        } else if ( gamePanel.gameState == gamePanel.pauseState ) {
            pauseState(code);
        } else if ( gamePanel.gameState == gamePanel.dialogueState ) {
            dialogueState(code);
        } else if ( gamePanel.gameState == gamePanel.characterState ) {
            characterState(code);
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

        if ( code == KeyEvent.VK_SPACE ) {
            spacePressed = false;
        }

//        if ( code == KeyEvent.VK_ENTER ) {
//            enterPressed = false;
//        }
    }

    public void titleState(int code){
        if ( code == KeyEvent.VK_W || code == KeyEvent.VK_UP ) {
            gamePanel.userInterface.commandNum--;
            if ( gamePanel.userInterface.commandNum < 0 ) {
                gamePanel.userInterface.commandNum = 2;
            }
        } else if ( code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN ) {
            gamePanel.userInterface.commandNum++;
            if ( gamePanel.userInterface.commandNum > 2 ) {
                gamePanel.userInterface.commandNum = 0;
            }
        } else if ( code == KeyEvent.VK_ENTER ) {
            if ( gamePanel.userInterface.commandNum == 0 ) {
                gamePanel.gameState = gamePanel.playState;
//                    gamePanel.playMusic(0);
            } else if ( gamePanel.userInterface.commandNum == 1 ) {
//
            } else if ( gamePanel.userInterface.commandNum == 2 ) {
                System.exit(0);
            }
        }
    }

    public void playState(int code){
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

        if ( code == KeyEvent.VK_SPACE ) {
            spacePressed = true;
        }

        if ( code == KeyEvent.VK_C ){
            gamePanel.gameState = gamePanel.characterState;
        }
    }

    public void pauseState(int code){
        if ( code == KeyEvent.VK_ESCAPE ) {
            gamePanel.gameState = gamePanel.playState;
        }
    }

    public void dialogueState(int code){
        if ( code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE ) {
            gamePanel.gameState = gamePanel.playState;
        }
    }

    public void characterState(int code){
        if ( code == KeyEvent.VK_C ){
            gamePanel.gameState = gamePanel.playState;
        }
    }
}
