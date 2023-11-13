package main;

import entity.OldManNpc;
import monster.GreenSlime;
import object.Boots;
import object.Chest;
import object.Door;
import object.Key;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    public void setObject() {

    }

    public void setNpc() {

        gamePanel.npc[0] = new OldManNpc(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 21;
    }

    public void setMonster() {

        gamePanel.monsters[0] = new GreenSlime(gamePanel);
        gamePanel.monsters[0].worldX = gamePanel.tileSize * 23;
        gamePanel.monsters[0].worldY = gamePanel.tileSize * 36;

        gamePanel.monsters[1] = new GreenSlime(gamePanel);
        gamePanel.monsters[1].worldX = gamePanel.tileSize * 23;
        gamePanel.monsters[1].worldY = gamePanel.tileSize * 37;
    }
}
