package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class Fireball extends Projectile {
    public Fireball(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        name = "Fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {

        up1 = getScaledImage("/projectiles/fireball_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = getScaledImage("/projectiles/fireball_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = getScaledImage("/projectiles/fireball_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = getScaledImage("/projectiles/fireball_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = getScaledImage("/projectiles/fireball_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = getScaledImage("/projectiles/fireball_right_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = getScaledImage("/projectiles/fireball_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = getScaledImage("/projectiles/fireball_left_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public boolean haveResource(Entity user){

        return user.mana >= useCost;
    }

    @Override
    public void subtractResource(Entity user){
        user.mana -= useCost;
    }
}
