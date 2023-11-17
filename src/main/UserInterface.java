package main;

import entity.Entity;
import object.Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UserInterface {

    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font maruMonica;

    boolean messageOn = false;
    public String message = "";
    public String currentDialogue = "";

    int commandNum = 0;

    BufferedImage heartFull, heartHalf, heartBlank;

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

        heartFull = heart.image;
        heartHalf = heart.image2;
        heartBlank = heart.image3;
    }

    public void drawMessage(String text) {

        message = text;

        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {

        this.graphics2D = graphics2D;

        graphics2D.setFont(maruMonica);
        graphics2D.setColor(Color.WHITE);

        if ( gamePanel.gameState == gamePanel.titleState ) {
            drawTitleScreen();
        } else if ( gamePanel.gameState == gamePanel.playState ) {
            drawPlayerLife();
        } else if ( gamePanel.gameState == gamePanel.pauseState ) {
            drawPlayerLife();
            drawPauseScreen();
        } else if ( gamePanel.gameState == gamePanel.dialogueState ) {
            drawPlayerLife();
            drawDialogueScreen();
        } else if ( gamePanel.gameState == gamePanel.characterState ) {
            drawCharacterScreen();
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
    }

    public void drawTitleScreen() {

        String text = "Blue Boy Adventure";
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 92F));

        int x = getCenterOfText(text);
        int y = gamePanel.tileSize * 2;

        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(text, x + 3, y + 3);

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(text, x, y);

        x = gamePanel.maxScreenWidth / 2 - ((gamePanel.tileSize * 2) / 2);
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
        int y = gamePanel.maxScreenHeight / 2;

        graphics2D.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.maxScreenWidth - (gamePanel.tileSize * 4);
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

    public void drawCharacterScreen(){

        final int frameX = gamePanel.tileSize;
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
        textY += lineHeight + 20;
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

        graphics2D.drawImage(gamePanel.player.currentWeapon.down1, tailX - gamePanel.tileSize, textY - 13, null);
        textY += gamePanel.tileSize;
        graphics2D.drawImage(gamePanel.player.currentShield.down1, tailX - gamePanel.tileSize, textY - 13, null);
//        value = String.valueOf(gamePanel.player.currentWeapon.name);
//        textX = getXForAlignToRightText(value, tailX);
//        graphics2D.drawString(value, textX, textY);
//        textY += lineHeight;
//
//        value = String.valueOf(gamePanel.player.currentShield.name);
//        textX = getXForAlignToRightText(value, tailX);
//        graphics2D.drawString(value, textX, textY);
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color color = new Color(0, 0, 0, 200);

        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(4));
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getCenterOfText(String text) {

        int length = ( int ) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gamePanel.maxScreenWidth / 2 - length / 2;
    }

    public int getXForAlignToRightText(String text, int tailX){

        int length = ( int ) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return tailX - length;
    }
}
