package main;

import entity.MerchantNpc;
import entity.OldManNpc;
import interactive.DryTree;
import interactive.Tile;
import monster.GreenSlime;
import monster.Orc;
import object.*;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    public void setObject() {

        int mapNum = 0;
        int i = 0;
        gamePanel.objects[mapNum][i] = new Door(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 14;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 28;
        i++;

        gamePanel.objects[mapNum][i] = new Lantern(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 18;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 20;
        i++;

        gamePanel.objects[mapNum][i] = new Tent(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 19;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 20;
        i++;

        gamePanel.objects[mapNum][i] = new Door(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 12;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 12;
        i++;

        gamePanel.objects[mapNum][i] = new PotionRed(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 25;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 12;
        i++;
        gamePanel.objects[mapNum][i] = new PotionRed(gamePanel);
        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 26;
        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 12;
        i++;

//        gamePanel.objects[mapNum][i] = new Chest(gamePanel, new Key(gamePanel));
//        gamePanel.objects[mapNum][i].worldX = gamePanel.tileSize * 25;
//        gamePanel.objects[mapNum][i].worldY = gamePanel.tileSize * 11;
//        i++;
    }

    public void setNpc() {

        int mapNum = 0;
        int i = 0;
        gamePanel.npc[mapNum][i] = new OldManNpc(gamePanel);
        gamePanel.npc[mapNum][i].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[mapNum][i].worldY = gamePanel.tileSize * 21;

        mapNum = 1;
        i = 0;
        gamePanel.npc[mapNum][i] = new MerchantNpc(gamePanel);
        gamePanel.npc[mapNum][i].worldX = gamePanel.tileSize * 12;
        gamePanel.npc[mapNum][i].worldY = gamePanel.tileSize * 7;
    }

    public void setMonster() {

        int mapNum = 0;
        int i = 0;
        gamePanel.monsters[mapNum][i] = new GreenSlime(gamePanel);
        gamePanel.monsters[mapNum][i].worldX = gamePanel.tileSize * 23;
        gamePanel.monsters[mapNum][i].worldY = gamePanel.tileSize * 36;
        i++;

        gamePanel.monsters[mapNum][i] = new GreenSlime(gamePanel);
        gamePanel.monsters[mapNum][i].worldX = gamePanel.tileSize * 23;
        gamePanel.monsters[mapNum][i].worldY = gamePanel.tileSize * 37;
        i++;

        gamePanel.monsters[mapNum][i] = new GreenSlime(gamePanel);
        gamePanel.monsters[mapNum][i].worldX = gamePanel.tileSize * 36;
        gamePanel.monsters[mapNum][i].worldY = gamePanel.tileSize * 37;
        i++;

        gamePanel.monsters[mapNum][i] = new GreenSlime(gamePanel);
        gamePanel.monsters[mapNum][i].worldX = gamePanel.tileSize * 35;
        gamePanel.monsters[mapNum][i].worldY = gamePanel.tileSize * 37;
        i++;

        gamePanel.monsters[mapNum][i] = new Orc(gamePanel);
        gamePanel.monsters[mapNum][i].worldX = gamePanel.tileSize * 12;
        gamePanel.monsters[mapNum][i].worldY = gamePanel.tileSize * 33;
    }

    public void setInteractiveTiles() {

        int mapNum = 0;
        int i = 0;
        gamePanel.interactiveTiles[mapNum][i] = new DryTree(gamePanel, 27, 12);
        i++;

        gamePanel.interactiveTiles[mapNum][i] = new DryTree(gamePanel, 28, 12);
        i++;

        gamePanel.interactiveTiles[mapNum][i] = new DryTree(gamePanel, 29, 12);
        i++;

        gamePanel.interactiveTiles[mapNum][i] = new DryTree(gamePanel, 30, 12);
        i++;

        gamePanel.interactiveTiles[mapNum][i] = new DryTree(gamePanel, 31, 12);
        i++;

        gamePanel.interactiveTiles[mapNum][i] = new DryTree(gamePanel, 32, 12);
        i++;

        gamePanel.interactiveTiles[mapNum][i] = new DryTree(gamePanel, 33, 12);
        i++;
    }
}
