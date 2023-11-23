package object;

import entity.Entity;
import main.GamePanel;

public class CoinBronze extends Entity {
    public CoinBronze(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typePickupOnly;
        name = "Bronze coin";
        value = 1;
        down1 = getScaledImage("/objects/coin_bronze", gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public void use(Entity entity) {

        gamePanel.playSoundEffect(1);
        gamePanel.userInterface.addMessage("Coin + " + value);
        entity.coin += value;
    }
}
