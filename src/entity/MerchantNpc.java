package entity;

import main.GamePanel;
import object.*;

public class MerchantNpc extends Entity {

    public MerchantNpc(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
        setItem();
    }

    public void getImage() {

        up1 = getScaledImage("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = getScaledImage("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = getScaledImage("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = getScaledImage("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = getScaledImage("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = getScaledImage("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = getScaledImage("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = getScaledImage("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {

        dialogues[0] = "He he, so you found me.\nI have some good stuff.\nDo you want to trade?";
    }

    @Override
    public void speak() {

        super.speak();
        gamePanel.gameState = gamePanel.dialogueState;
//        gamePanel.userInterface.npc = this;
    }

    public void setItem() {

        inventory.add(new PotionRed(gamePanel));
        inventory.add(new Key(gamePanel));
        inventory.add(new SwordNormal(gamePanel));
        inventory.add(new Axe(gamePanel));
        inventory.add(new ShieldWood(gamePanel));
        inventory.add(new ShieldBlue(gamePanel));
    }
}
