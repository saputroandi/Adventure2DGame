package main;

import entity.OldManNpc;
import interactive.DryTree;
import interactive.Tile;
import monster.GreenSlime;
import object.*;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    public void setObject() {

        int i = 0;
        gamePanel.objects[i] = new CoinBronze(gamePanel);
        gamePanel.objects[i].worldX = gamePanel.tileSize * 25;
        gamePanel.objects[i].worldY = gamePanel.tileSize * 19;
        i++;

        gamePanel.objects[i] = new Axe(gamePanel);
        gamePanel.objects[i].worldX = gamePanel.tileSize * 26;
        gamePanel.objects[i].worldY = gamePanel.tileSize * 22;
        i++;

//        gamePanel.objects[i] = new ManaCrystal(gamePanel);
//        gamePanel.objects[i].worldX = gamePanel.tileSize * 22;
//        gamePanel.objects[i].worldY = gamePanel.tileSize * 32;
//        i++;
    }

    public void setNpc() {

        gamePanel.npc[0] = new OldManNpc(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 21;
    }

    public void setMonster() {

        int i = 0;
        gamePanel.monsters[i] = new GreenSlime(gamePanel);
        gamePanel.monsters[i].worldX = gamePanel.tileSize * 23;
        gamePanel.monsters[i].worldY = gamePanel.tileSize * 36;
        i++;

        gamePanel.monsters[i] = new GreenSlime(gamePanel);
        gamePanel.monsters[i].worldX = gamePanel.tileSize * 23;
        gamePanel.monsters[i].worldY = gamePanel.tileSize * 37;
        i++;

        gamePanel.monsters[i] = new GreenSlime(gamePanel);
        gamePanel.monsters[i].worldX = gamePanel.tileSize * 36;
        gamePanel.monsters[i].worldY = gamePanel.tileSize * 37;
        i++;

        gamePanel.monsters[i] = new GreenSlime(gamePanel);
        gamePanel.monsters[i].worldX = gamePanel.tileSize * 35;
        gamePanel.monsters[i].worldY = gamePanel.tileSize * 37;
    }

    public void setInteractiveTiles() {

        int i = 0;
        gamePanel.interactiveTiles[i] = new DryTree(gamePanel, 27, 12);
        i++;

        gamePanel.interactiveTiles[i] = new DryTree(gamePanel, 28, 12);
        i++;

        gamePanel.interactiveTiles[i] = new DryTree(gamePanel, 29, 12);
        i++;

        gamePanel.interactiveTiles[i] = new DryTree(gamePanel, 30, 12);
        i++;

        gamePanel.interactiveTiles[i] = new DryTree(gamePanel, 31, 12);
        i++;

        gamePanel.interactiveTiles[i] = new DryTree(gamePanel, 32, 12);
        i++;

        gamePanel.interactiveTiles[i] = new DryTree(gamePanel, 33, 12);
        i++;
    }
}
