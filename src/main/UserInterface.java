package main;

import java.awt.*;

public class UserInterface {

    public String message = "";

    public String currentDialogue = "";

    public boolean gameFinished = false;

    GamePanel gamePanel;

    Graphics2D graphics2D;

    Font font_40, font_80B;

    boolean messageOn = false;

    public UserInterface(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        this.font_40 = new Font("Arial", Font.PLAIN, 40);
        this.font_80B = new Font("Arial", Font.BOLD, 80);

    }

    public void drawMessage(String text) {

        message = text;

        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {

        this.graphics2D = graphics2D;

        graphics2D.setFont(font_40);
        graphics2D.setColor(Color.WHITE);

        if ( gamePanel.gameState == gamePanel.playState ) {

        } else if ( gamePanel.gameState == gamePanel.pauseState ) {
            drawPauseScreen();
        } else if ( gamePanel.gameState == gamePanel.dialogueState ) {
            drawDialogueScreen();
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
}
