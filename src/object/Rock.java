package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class Rock extends Projectile {

    public Rock(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {

        up1 = getScaledImage("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = getScaledImage("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down1 = getScaledImage("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = getScaledImage("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = getScaledImage("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = getScaledImage("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left1 = getScaledImage("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = getScaledImage("/projectiles/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public boolean haveResource(Entity user) {

        return user.ammo >= useCost;
    }

    @Override
    public void subtractResource(Entity user) {

        user.ammo -= useCost;
    }

    @Override
    public Color getParticleColor() {

        return new Color(40, 50, 0);
    }

    @Override
    public int getParticleSize() {

        return 10;
    }

    @Override
    public int getParticleSpeed() {

        return 1;
    }

    @Override
    public int getParticleMaxLife() {

        return 20;
    }
}
