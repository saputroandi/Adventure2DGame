package main;

import entity.Entity;
import object.CoinBronze;
import object.Heart;
import object.ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UserInterface {

    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font maruMonica;

    boolean messageOn = false;
    public String currentDialogue = "";
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messagesCounter = new ArrayList<>();

    int commandNum = 0;

    BufferedImage heartFull, heartHalf, heartBlank, crystalFull, crystalBlank, coin;

    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;

    int subState = 0;
    int counter = 0;

    public Entity npc;

    public UserInterface(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        init();

    }

    public void init() {

        try {
            InputStream inputStream = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch ( IOException | FontFormatException e ) {
            throw new RuntimeException(e);
        }

        Entity heart = new Heart(gamePanel);
        Entity crystal = new ManaCrystal(gamePanel);
        Entity coinBronze = new CoinBronze(gamePanel);

        heartFull = heart.image;
        heartHalf = heart.image2;
        heartBlank = heart.image3;

        crystalFull = crystal.image;
        crystalBlank = crystal.image2;

        coin = coinBronze.down1;
    }

    public void addMessage(String text) {

        messages.add(text);
        messagesCounter.add(0);
    }

    public void draw(Graphics2D graphics2D) {

        this.graphics2D = graphics2D;

        graphics2D.setFont(maruMonica);
        graphics2D.setColor(Color.WHITE);

        if ( gamePanel.gameState == gamePanel.titleState ) {
            drawTitleScreen();
        } else if ( gamePanel.gameState == gamePanel.playState ) {
            drawPlayerLife();
            drawMessages();
        } else if ( gamePanel.gameState == gamePanel.pauseState ) {
            drawPlayerLife();
            drawPauseScreen();
        } else if ( gamePanel.gameState == gamePanel.dialogueState ) {
//            drawPlayerLife();
            drawDialogueScreen();
        } else if ( gamePanel.gameState == gamePanel.characterState ) {
            drawCharacterScreen();
            drawInventory(gamePanel.player, true);
        } else if ( gamePanel.gameState == gamePanel.optionsState ) {
            drawOptionsScreen();
        } else if ( gamePanel.gameState == gamePanel.gameOverState ) {
            drawGameOverScreen();
        } else if ( gamePanel.gameState == gamePanel.transitionState ) {
            drawTransition();
        } else if ( gamePanel.gameState == gamePanel.tradeState ) {
            drawTradeScreen();
        }
    }

    public void drawPlayerLife() {

        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i = 0;

        while ( i < gamePanel.player.maxLife / 2 ) {
            graphics2D.drawImage(heartBlank, x, y, null);
            i++;
            x += gamePanel.tileSize;
        }

        x = gamePanel.tileSize / 2;
        i = 0;

        while ( i < gamePanel.player.life ) {
            graphics2D.drawImage(heartHalf, x, y, null);
            i++;
            if ( i < gamePanel.player.life ) {
                graphics2D.drawImage(heartFull, x, y, null);
            }
            i++;
            x += gamePanel.tileSize;
        }

        x = (gamePanel.tileSize / 2) - 5;
        y = ( int ) (gamePanel.tileSize * 1.5);
        i = 0;

        while ( i < gamePanel.player.maxMana ) {
            graphics2D.drawImage(crystalBlank, x, y, null);
            i++;
            x += 35;
        }

        x = (gamePanel.tileSize / 2) - 5;
        y = ( int ) (gamePanel.tileSize * 1.5);
        i = 0;

        while ( i < gamePanel.player.mana ) {
            graphics2D.drawImage(crystalFull, x, y, null);
            i++;
            x += 35;
        }
    }

    public void drawMessages() {

        int messageX = gamePanel.tileSize;
        int messageY = gamePanel.tileSize * 4;

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32F));

        for ( int i = 0; i < messages.size(); i++ ) {

            if ( messages.get(i) != null ) {
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString(messages.get(i), messageX, messageY);

                int counter = messagesCounter.get(i) + 1;
                messagesCounter.set(i, counter);
                messageY += 50;

                if ( messagesCounter.get(i) > 180 ) {
                    messages.remove(i);
                    messagesCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen() {

        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        String text = "Blue Boy Adventure";
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 92F));

        int x = getCenterOfText(text);
        int y = gamePanel.tileSize * 2;

        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(text, x + 3, y + 3);

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(text, x, y);

        x = gamePanel.screenWidth / 2 - ((gamePanel.tileSize * 2) / 2);
        y += gamePanel.tileSize * 2;
        graphics2D.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

//        menu
        text = "NEW GAME";
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));

        x = getCenterOfText(text);
        y += ( int ) (gamePanel.tileSize * 3.5);
        graphics2D.drawString(text, x, y);
        if ( commandNum == 0 ) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "LOAD GAME";
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));

        x = getCenterOfText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);
        if ( commandNum == 1 ) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "QUIT";
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));

        x = getCenterOfText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);
        if ( commandNum == 2 ) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }
    }

    public void drawPauseScreen() {

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getCenterOfText(text);
        int y = gamePanel.screenHeight / 2;

        graphics2D.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        int x = gamePanel.tileSize * 3;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 6);
        int height = gamePanel.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 28F));

        for ( String line : currentDialogue.split("\n") ) {
            graphics2D.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen() {

        final int frameX = gamePanel.tileSize * 2;
        final int frameY = gamePanel.tileSize;
        final int frameWidth = gamePanel.tileSize * 5;
        final int frameHeight = gamePanel.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.tileSize;
        final int lineHeight = 35;

        graphics2D.drawString("Level", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Life", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Mana", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Strength", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Attack", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Defense", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Exp", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Next Level", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        graphics2D.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        graphics2D.drawString("Shield", textX, textY);

        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gamePanel.tileSize;
        String value;

        value = String.valueOf(gamePanel.player.level);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.life + "/" + gamePanel.player.maxLife);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.mana + "/" + gamePanel.player.maxMana);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.defense);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.nextLevelExp);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        graphics2D.drawImage(gamePanel.player.currentWeapon.down1, tailX - gamePanel.tileSize, textY - 24, null);
        textY += gamePanel.tileSize;
        graphics2D.drawImage(gamePanel.player.currentShield.down1, tailX - gamePanel.tileSize, textY - 24, null);
//        value = String.valueOf(gamePanel.player.currentWeapon.name);
//        textX = getXForAlignToRightText(value, tailX);
//        graphics2D.drawString(value, textX, textY);
//        textY += lineHeight;
//
//        value = String.valueOf(gamePanel.player.currentShield.name);
//        textX = getXForAlignToRightText(value, tailX);
//        graphics2D.drawString(value, textX, textY);
    }

    public void drawInventory(Entity entity, boolean cursor) {

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if ( entity == gamePanel.player ) {
            frameX = gamePanel.tileSize * 12;
            frameY = gamePanel.tileSize;
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            frameX = gamePanel.tileSize * 2;
            frameY = gamePanel.tileSize;
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gamePanel.tileSize + 3;

        for ( int i = 0; i < entity.inventory.size(); i++ ) {
            if ( entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield ) {
                graphics2D.setColor(new Color(240, 190, 90));
                graphics2D.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);
            }

            graphics2D.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            slotX += slotSize;
            if ( i == 4 || i == 9 || i == 14 ) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        if ( cursor ) {
            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = gamePanel.tileSize;
            int cursorHeight = gamePanel.tileSize;

            graphics2D.setColor(Color.WHITE);
            graphics2D.setStroke(new BasicStroke(3));
            graphics2D.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gamePanel.tileSize * 3;


            int textX = dFrameX + 20;
            int textY = dFrameY + gamePanel.tileSize;

            graphics2D.setFont(graphics2D.getFont().deriveFont(28F));

            int itemIndex = getItemIndexOnInventory(slotCol, slotRow);
            if ( itemIndex < entity.inventory.size() ) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

                String itemDesc = entity.inventory.get(itemIndex).description;

                for ( String line : itemDesc.split("\n") ) {
                    graphics2D.drawString(line, textX, textY);
                    textY += 32;
                }

            }
        }
    }

    public void drawOptionsScreen() {

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));

        int frameX = gamePanel.tileSize * 6;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 8;
        int frameHeight = gamePanel.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch ( subState ) {
            case 0:
                optionsTop(frameX, frameY);
                break;
            case 1:
                optionsFullScreenNotification(frameX, frameY);
                break;
            case 2:
                optionsControl(frameX, frameY);
                break;
            case 3:
                optionsEndGameConfirmation(frameX, frameY);
                break;
        }

        gamePanel.keyHandler.enterPressed = false;
    }

    public void optionsTop(int frameX, int frameY) {

        int textX;
        int textY;

        String text = "Options";
        textX = getCenterOfText(text);
        textY = frameY + gamePanel.tileSize;
        graphics2D.drawString(text, textX, textY);

        text = "Full Screen";
        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize * 2;
        graphics2D.drawString(text, textX, textY);
        if ( commandNum == 0 ) {
            graphics2D.drawString(">", textX - 25, textY);
            if ( gamePanel.keyHandler.enterPressed ) {
                if ( !gamePanel.fullScreen ) {
                    gamePanel.fullScreen = true;
                } else if ( gamePanel.fullScreen ) {
                    gamePanel.fullScreen = false;
                }
                subState = 1;
            }
        }

        text = "Music";
        textY += gamePanel.tileSize;
        graphics2D.drawString(text, textX, textY);
        if ( commandNum == 1 ) {
            graphics2D.drawString(">", textX - 25, textY);
        }

        text = "Sound Effect";
        textY += gamePanel.tileSize;
        graphics2D.drawString(text, textX, textY);
        if ( commandNum == 2 ) {
            graphics2D.drawString(">", textX - 25, textY);
        }

        text = "Control";
        textY += gamePanel.tileSize;
        graphics2D.drawString(text, textX, textY);
        if ( commandNum == 3 ) {
            graphics2D.drawString(">", textX - 25, textY);
            if ( gamePanel.keyHandler.enterPressed ) {
                subState = 2;
                commandNum = 0;
            }
        }

        text = "End Game";
        textY += gamePanel.tileSize;
        graphics2D.drawString(text, textX, textY);
        if ( commandNum == 4 ) {
            graphics2D.drawString(">", textX - 25, textY);
            if ( gamePanel.keyHandler.enterPressed ) {
                subState = 3;
                commandNum = 0;
            }
        }

        text = "Back";
        textY += gamePanel.tileSize * 2;
        graphics2D.drawString(text, textX, textY);
        if ( commandNum == 5 ) {
            graphics2D.drawString(">", textX - 25, textY);
            if ( gamePanel.keyHandler.enterPressed ) {
                gamePanel.gameState = gamePanel.playState;
                commandNum = 0;
            }
        }

        textX = frameX + ( int ) (gamePanel.tileSize * 4.5);
        textY = frameY + gamePanel.tileSize * 2 + 24;
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRect(textX, textY, 24, 24);
        if ( gamePanel.fullScreen ) {
            graphics2D.fillRect(textX, textY, 24, 24);
        }

        textY += gamePanel.tileSize;
        graphics2D.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gamePanel.music.volumeScale;
        graphics2D.fillRect(textX, textY, volumeWidth, 24);

        textY += gamePanel.tileSize;
        graphics2D.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gamePanel.soundEffect.volumeScale;
        graphics2D.fillRect(textX, textY, volumeWidth, 24);

        gamePanel.config.saveConfig();

    }

    public void optionsFullScreenNotification(int frameX, int frameY) {

        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize * 3;

        currentDialogue = "The change will take\neffect after restarting\nthe game";

        for ( String line : currentDialogue.split("\n") ) {
            graphics2D.drawString(line, textX, textY);
            textY += 40;
        }

        textY = frameY + gamePanel.tileSize * 9;
        graphics2D.drawString("Back", textX, textY);

        if ( commandNum == 0 ) {
            graphics2D.drawString(">", textX - 25, textY);
            if ( gamePanel.keyHandler.enterPressed ) {
                subState = 0;
            }
        }
    }

    public void optionsControl(int frameX, int frameY) {

        int textX;
        int textY;

        String text = "Control";
        textX = getCenterOfText(text);
        textY = frameY + gamePanel.tileSize;
        graphics2D.drawString(text, textX, textY);

        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize;
        graphics2D.drawString("Move", textX, textY += gamePanel.tileSize);
        graphics2D.drawString("Confirm/Attack", textX, textY += gamePanel.tileSize);
        graphics2D.drawString("Shoot/Cast", textX, textY += gamePanel.tileSize);
        graphics2D.drawString("Character Screen", textX, textY += gamePanel.tileSize);
        graphics2D.drawString("Pause", textX, textY += gamePanel.tileSize);
        graphics2D.drawString("Options", textX, textY += gamePanel.tileSize);

        textX = frameX + gamePanel.tileSize * 6;
        textY = frameY + gamePanel.tileSize * 2;
        graphics2D.drawString("WASD", textX, textY += gamePanel.tileSize);
        graphics2D.drawString("Enter", textX, textY += gamePanel.tileSize);
        graphics2D.drawString("F", textX, textY += gamePanel.tileSize);
        graphics2D.drawString("C", textX, textY += gamePanel.tileSize);
        graphics2D.drawString("P", textX, textY += gamePanel.tileSize);
        graphics2D.drawString("ESC", textX, textY += gamePanel.tileSize);

        textX = frameX + gamePanel.tileSize;
        textY = frameY + gamePanel.tileSize * 9;
        graphics2D.drawString("Back", textX, textY);

        if ( commandNum == 0 ) {
            graphics2D.drawString(">", textX - 25, textY);
            if ( gamePanel.keyHandler.enterPressed ) {
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void optionsEndGameConfirmation(int frameX, int frameY) {

        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize * 3;

        currentDialogue = "Quit the game and\nreturn to the title screen";

        for ( String line : currentDialogue.split("\n") ) {
            graphics2D.drawString(line, textX, textY);
            textY += 40;
        }

        String text = "Yes";
        textX = getCenterOfText(text);
        textY += gamePanel.tileSize * 3;
        graphics2D.drawString(text, textX, textY);
        if ( commandNum == 0 ) {
            graphics2D.drawString(">", textX - 25, textY);
            if ( gamePanel.keyHandler.enterPressed ) {
                subState = 0;
                gamePanel.gameState = gamePanel.titleState;
            }
        }

        text = "No";
        textX = getCenterOfText(text);
        textY += gamePanel.tileSize;
        graphics2D.drawString(text, textX, textY);
        if ( commandNum == 1 ) {
            graphics2D.drawString(">", textX - 25, textY);
            if ( gamePanel.keyHandler.enterPressed ) {
                subState = 0;
                commandNum = 4;
            }
        }
    }

    public void drawGameOverScreen() {

        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        int x;
        int y;
        String text;
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 110F));

        text = "Game Over";
        graphics2D.setColor(Color.BLACK);
        x = getCenterOfText(text);
        y = gamePanel.tileSize * 4;
        graphics2D.drawString(text, x, y);

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(text, x - 4, y - 4);

        graphics2D.setFont(graphics2D.getFont().deriveFont(50F));
        text = "Retry";
        x = getCenterOfText(text);
        y += gamePanel.tileSize * 4;
        graphics2D.drawString(text, x, y);
        if ( commandNum == 0 ) {
            graphics2D.drawString(">", x - 40, y);
        }

        text = "Quit";
        x = getCenterOfText(text);
        y += 55;
        graphics2D.drawString(text, x, y);
        if ( commandNum == 1 ) {
            graphics2D.drawString(">", x - 40, y);
        }
    }

    public void drawTransition() {

        counter++;
        graphics2D.setColor(new Color(0, 0, 0, counter * 5));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        if ( counter == 50 ) {
            counter = 0;
            gamePanel.gameState = gamePanel.playState;
            gamePanel.currentMap = gamePanel.eventHandler.tempMap;
            gamePanel.player.worldX = gamePanel.tileSize * gamePanel.eventHandler.tempCol;
            gamePanel.player.worldY = gamePanel.tileSize * gamePanel.eventHandler.tempRow;
            gamePanel.eventHandler.previousEventX = gamePanel.player.worldX;
            gamePanel.eventHandler.previousEventY = gamePanel.player.worldY;
        }
    }

    public void drawTradeScreen() {

        switch ( subState ) {
            case 0:
                tradeSelect();
                break;
            case 1:
                tradeBuy();
                break;
            case 2:
                tradeSell();
                break;
        }

        gamePanel.keyHandler.enterPressed = false;
    }

    public void tradeSelect() {

        drawDialogueScreen();

        int x = gamePanel.tileSize * 15;
        int y = gamePanel.tileSize * 4;
        int width = gamePanel.tileSize * 3;
        int height = ( int ) (gamePanel.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        graphics2D.drawString("Buy", x, y);
        if ( commandNum == 0 ) {
            graphics2D.drawString(">", x - 24, y);
            if ( gamePanel.keyHandler.enterPressed ) {
                subState = 1;
            }
        }
        y += gamePanel.tileSize;
        graphics2D.drawString("Sell", x, y);
        if ( commandNum == 1 ) {
            graphics2D.drawString(">", x - 24, y);
            if ( gamePanel.keyHandler.enterPressed ) {
                subState = 2;
            }
        }
        y += gamePanel.tileSize;
        graphics2D.drawString("Leave", x, y);
        if ( commandNum == 2 ) {
            graphics2D.drawString(">", x - 24, y);
            if ( gamePanel.keyHandler.enterPressed ) {
//                subState = 0;
                commandNum = 0;
                currentDialogue = "Come again, haha.";
                gamePanel.gameState = gamePanel.dialogueState;
            }
        }
        y += gamePanel.tileSize;

    }

    public void tradeBuy() {

        drawInventory(gamePanel.player, false);
        drawInventory(npc, true);

        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize * 9;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        graphics2D.drawString("[ESC] Back", x + 24, y + 60);

        x = gamePanel.tileSize * 12;
        drawSubWindow(x, y, width, height);
        graphics2D.drawString("Ur coin: " + gamePanel.player.coin, x + 24, y + 60);

        int indexItemNpc = getItemIndexOnInventory(npcSlotCol, npcSlotRow);
        if ( indexItemNpc < npc.inventory.size() ) {
            x = ( int ) (gamePanel.tileSize * 5.5);
            y = ( int ) (gamePanel.tileSize * 5.5);
            width = ( int ) (gamePanel.tileSize * 2.5);
            height = gamePanel.tileSize;

            drawSubWindow(x, y, width, height);
            graphics2D.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(indexItemNpc).price;
            String text = "" + price;
            x = getXForAlignToRightText(text, gamePanel.tileSize * 8 - 20);
            graphics2D.drawString(text, x, y + 34);

            if ( gamePanel.keyHandler.enterPressed ) {
                if ( npc.inventory.get(indexItemNpc).price > gamePanel.player.coin ) {
                    subState = 0;
                    currentDialogue = "You need more coin to buy that!";
                    gamePanel.gameState = gamePanel.dialogueState;
                    drawDialogueScreen();
                } else if ( gamePanel.player.inventory.size() == gamePanel.player.maxInventorySize ){
                    subState = 0;
                    currentDialogue = "You cannot carry anymore.";
                    gamePanel.gameState = gamePanel.dialogueState;
                } else {
                    gamePanel.player.coin -= npc.inventory.get(indexItemNpc).price;
                    gamePanel.player.inventory.add(npc.inventory.get(indexItemNpc));
                }
            }
        }
    }

    public void tradeSell() {

        drawInventory(gamePanel.player, true);

        int x;
        int y;
        int width;
        int height;

        x = gamePanel.tileSize * 2;
        y = gamePanel.tileSize * 9;
        width = gamePanel.tileSize * 6;
        height = gamePanel.tileSize * 2;

        drawSubWindow(x, y, width, height);
        graphics2D.drawString("[ESC] Back", x + 24, y + 60);

        x = gamePanel.tileSize * 12;
        drawSubWindow(x, y, width, height);
        graphics2D.drawString("Ur coin: " + gamePanel.player.coin, x + 24, y + 60);

        int indexItemPlayer = getItemIndexOnInventory(playerSlotCol, playerSlotRow);
        if ( indexItemPlayer < gamePanel.player.inventory.size() ) {
            x = ( int ) (gamePanel.tileSize * 15.5);
            y = ( int ) (gamePanel.tileSize * 5.5);
            width = ( int ) (gamePanel.tileSize * 2.5);
            height = gamePanel.tileSize;

            drawSubWindow(x, y, width, height);
            graphics2D.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gamePanel.player.inventory.get(indexItemPlayer).price / 2;
            String text = "" + price;
            x = getXForAlignToRightText(text, gamePanel.tileSize * 18 - 20);
            graphics2D.drawString(text, x, y + 34);

            if ( gamePanel.keyHandler.enterPressed ) {
                if ( gamePanel.player.inventory.get(indexItemPlayer) == gamePanel.player.currentWeapon || gamePanel.player.inventory.get(indexItemPlayer) == gamePanel.player.currentShield){
                    commandNum = 0;
                    subState = 0;
                    currentDialogue = "You cannot sell an equipped item!.";
                    gamePanel.gameState = gamePanel.dialogueState;
                } else {
                    gamePanel.player.inventory.remove(indexItemPlayer);
                    gamePanel.player.coin += price;
                }
            }
        }
    }

    public int getItemIndexOnInventory(int slotCol, int slotRow) {

        return slotCol + (slotRow * 5);
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color color = new Color(0, 0, 0, 240);

        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(4));
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getCenterOfText(String text) {

        int length = ( int ) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gamePanel.screenWidth / 2 - length / 2;
    }

    public int getXForAlignToRightText(String text, int tailX) {

        int length = ( int ) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return tailX - length;
    }
}
