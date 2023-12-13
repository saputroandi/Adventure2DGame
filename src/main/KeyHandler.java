package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean upPressed, downPressed, rightPressed, leftPressed, enterPressed, spacePressed, shotKeyPressed;

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
        } else if ( gamePanel.gameState == gamePanel.optionsState ) {
            optionsState(code);
        } else if ( gamePanel.gameState == gamePanel.gameOverState ) {
            gameOverState(code);
        } else if ( gamePanel.gameState == gamePanel.tradeState ) {
            tradeState(code);
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

        if ( code == KeyEvent.VK_F ) {
            shotKeyPressed = false;
        }
    }

    public void titleState(int code) {

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
                gamePanel.restart();
            } else if ( gamePanel.userInterface.commandNum == 1 ) {
//
            } else if ( gamePanel.userInterface.commandNum == 2 ) {
                System.exit(0);
            }
        }
    }

    public void playState(int code) {

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
            gamePanel.gameState = gamePanel.optionsState;
        }

        if ( code == KeyEvent.VK_P ) {
            gamePanel.gameState = gamePanel.pauseState;
        }

        if ( code == KeyEvent.VK_ENTER ) {
            enterPressed = true;
        }

        if ( code == KeyEvent.VK_SPACE ) {
            spacePressed = true;
        }

        if ( code == KeyEvent.VK_C ) {
            gamePanel.gameState = gamePanel.characterState;
        }

        if ( code == KeyEvent.VK_F ) {
            shotKeyPressed = true;
        }

        if ( code == KeyEvent.VK_X ) {
            if ( !gamePanel.map.minimapOn ){
                gamePanel.map.minimapOn = true;
            } else {
                gamePanel.map.minimapOn = false;
            }
        }
    }

    public void pauseState(int code) {

        if ( code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_P ) {
            gamePanel.gameState = gamePanel.playState;
        }
    }

    public void dialogueState(int code) {

        if ( code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE ) {
            gamePanel.gameState = gamePanel.playState;
        }
    }

    public void characterState(int code) {

        if ( code == KeyEvent.VK_C ) {
            gamePanel.gameState = gamePanel.playState;
//            gamePanel.playSoundEffect(9);
        }

        if ( code == KeyEvent.VK_ENTER ) {
            gamePanel.player.selectItem();
        }

        playerInventory(code);
    }

    public void optionsState(int code) {

        int maxCommandNum = 0;

        switch ( gamePanel.userInterface.subState ) {
            case 0:
                maxCommandNum = 5;
                break;
            case 3:
                maxCommandNum = 1;
                break;
        }

        if ( code == KeyEvent.VK_ESCAPE ) {
            gamePanel.gameState = gamePanel.playState;
        }

        if ( code == KeyEvent.VK_ENTER ) {
            enterPressed = true;
        }

        if ( code == KeyEvent.VK_UP ) {
            gamePanel.userInterface.commandNum--;
            gamePanel.playSoundEffect(9);
            if ( gamePanel.userInterface.commandNum < 0 ) {
                gamePanel.userInterface.commandNum = maxCommandNum;
            }
        }

        if ( code == KeyEvent.VK_DOWN ) {
            gamePanel.userInterface.commandNum++;
            gamePanel.playSoundEffect(9);
            if ( gamePanel.userInterface.commandNum > maxCommandNum ) {
                gamePanel.userInterface.commandNum = 0;
            }
        }

        if ( code == KeyEvent.VK_LEFT ) {
            if ( gamePanel.userInterface.subState == 0 ) {
                if ( gamePanel.userInterface.commandNum == 1 && gamePanel.music.volumeScale > 0 ) {
                    gamePanel.music.volumeScale--;
                    gamePanel.music.checkVolume();
                    gamePanel.playSoundEffect(9);
                }
                if ( gamePanel.userInterface.commandNum == 2 && gamePanel.soundEffect.volumeScale > 0 ) {
                    gamePanel.soundEffect.volumeScale--;
                    gamePanel.playSoundEffect(9);
                }
            }
        }

        if ( code == KeyEvent.VK_RIGHT ) {
            if ( gamePanel.userInterface.subState == 0 ) {
                if ( gamePanel.userInterface.commandNum == 1 && gamePanel.music.volumeScale < 5 ) {
                    gamePanel.music.volumeScale++;
                    gamePanel.music.checkVolume();
                    gamePanel.playSoundEffect(9);
                }
                if ( gamePanel.userInterface.commandNum == 2 && gamePanel.soundEffect.volumeScale < 5 ) {
                    gamePanel.soundEffect.volumeScale++;
                    gamePanel.playSoundEffect(9);
                }
            }
        }
    }

    public void gameOverState(int code) {

        if ( code == KeyEvent.VK_UP ) {
            gamePanel.userInterface.commandNum--;
            gamePanel.stopMusic();
            gamePanel.playSoundEffect(9);
            if ( gamePanel.userInterface.commandNum < 0 ) {
                gamePanel.userInterface.commandNum = 1;
            }
        }

        if ( code == KeyEvent.VK_DOWN ) {
            gamePanel.userInterface.commandNum++;
            gamePanel.playSoundEffect(9);
            if ( gamePanel.userInterface.commandNum > 1 ) {
                gamePanel.userInterface.commandNum = 0;
            }
        }

        if ( code == KeyEvent.VK_ENTER ) {
            if ( gamePanel.userInterface.commandNum == 0 ) {
                gamePanel.gameState = gamePanel.playState;
                gamePanel.retry();
            }
            if ( gamePanel.userInterface.commandNum == 1 ) {
                gamePanel.userInterface.commandNum = 0;
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.restart();
            }
        }
    }

    public void tradeState(int code) {

        if ( code == KeyEvent.VK_ENTER ) {
            enterPressed = true;
        }

        if ( gamePanel.userInterface.subState == 0 ) {
            if ( code == KeyEvent.VK_UP ) {
                gamePanel.userInterface.commandNum--;
                if ( gamePanel.userInterface.commandNum < 0 ) {
                    gamePanel.userInterface.commandNum = 2;
                }
                gamePanel.playSoundEffect(9);
            }

            if ( code == KeyEvent.VK_DOWN ) {
                gamePanel.userInterface.commandNum++;
                if ( gamePanel.userInterface.commandNum > 2 ) {
                    gamePanel.userInterface.commandNum = 0;
                }
                gamePanel.playSoundEffect(9);
            }
        }

        if ( gamePanel.userInterface.subState == 1 ) {
            npcInventory(code);
            if ( code == KeyEvent.VK_ESCAPE ) {
                gamePanel.userInterface.subState = 0;
            }
        }

        if ( gamePanel.userInterface.subState == 2 ) {
            playerInventory(code);
            if ( code == KeyEvent.VK_ESCAPE ) {
                gamePanel.userInterface.subState = 0;
            }
        }
    }

    public void playerInventory(int code) {

        if ( code == KeyEvent.VK_UP ) {
            if ( gamePanel.userInterface.playerSlotRow != 0 ) {
                gamePanel.userInterface.playerSlotRow--;
                gamePanel.playSoundEffect(9);
            }
        }

        if ( code == KeyEvent.VK_DOWN ) {
            if ( gamePanel.userInterface.playerSlotRow != 3 ) {
                gamePanel.userInterface.playerSlotRow++;
                gamePanel.playSoundEffect(9);
            }
        }

        if ( code == KeyEvent.VK_RIGHT ) {
            if ( gamePanel.userInterface.playerSlotCol != 4 ) {
                gamePanel.userInterface.playerSlotCol++;
                gamePanel.playSoundEffect(9);
            }
        }

        if ( code == KeyEvent.VK_LEFT ) {
            if ( gamePanel.userInterface.playerSlotCol != 0 ) {
                gamePanel.userInterface.playerSlotCol--;
                gamePanel.playSoundEffect(9);
            }
        }
    }

    public void npcInventory(int code) {

        if ( code == KeyEvent.VK_UP ) {
            if ( gamePanel.userInterface.npcSlotRow != 0 ) {
                gamePanel.userInterface.npcSlotRow--;
                gamePanel.playSoundEffect(9);
            }
        }

        if ( code == KeyEvent.VK_DOWN ) {
            if ( gamePanel.userInterface.npcSlotRow != 3 ) {
                gamePanel.userInterface.npcSlotRow++;
                gamePanel.playSoundEffect(9);
            }
        }

        if ( code == KeyEvent.VK_RIGHT ) {
            if ( gamePanel.userInterface.npcSlotCol != 4 ) {
                gamePanel.userInterface.npcSlotCol++;
                gamePanel.playSoundEffect(9);
            }
        }

        if ( code == KeyEvent.VK_LEFT ) {
            if ( gamePanel.userInterface.npcSlotCol != 0 ) {
                gamePanel.userInterface.npcSlotCol--;
                gamePanel.playSoundEffect(9);
            }
        }
    }
}
