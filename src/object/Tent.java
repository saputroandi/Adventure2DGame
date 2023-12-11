package object;

import entity.Entity;
import main.GamePanel;

public class Tent extends Entity {
    public Tent(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init(){

        type = typeConsumable;
        name = "Tent";
        down1 = getScaledImage("/objects/tent", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nIt can recover ur body and\nbring u to next morning.";
        price = 100;
        stackable = true;
    }

    public boolean use(Entity entity){

        gamePanel.gameState = gamePanel.sleepState;
        gamePanel.player.getPlayerSleepImage(down1);
        gamePanel.playSoundEffect(14);
        gamePanel.player.life = gamePanel.player.maxLife;
        gamePanel.player.mana = gamePanel.player.maxMana;

        return true;
    }
}
