package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    int FPS = 60;

    final int originalTileSize = 16;
    final int scale = 3;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenWidth = tileSize * maxScreenCol;
    public final int maxScreenHeight = tileSize * maxScreenRow;

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;


    public Entity[] objects = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monsters = new Entity[20];
    ArrayList<Entity> entities = new ArrayList<>();

    public AssetSetter assetSetter = new AssetSetter(this);
    public UserInterface userInterface = new UserInterface(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public Player player = new Player(this, keyHandler);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public EventHandler eventHandler = new EventHandler(this);

    TileManager tileManager = new TileManager(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    Thread gameThread;

    public GamePanel() {

        this.setPreferredSize(new Dimension(maxScreenWidth, maxScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setupGame() {

        assetSetter.setObject();
        assetSetter.setNpc();
        assetSetter.setMonster();
//        playMusic(0);
        gameState = titleState;
    }

    @Override
    public void run() {

        double drawInterval = ( double ) 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while ( gameThread.isAlive() ) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if ( delta >= 1 ) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {

        if ( gameState == playState ) {
            player.update();

            Arrays.stream(npc).forEach(object -> {
                if ( object != null ) {
                    object.update();
                }
            });

            for ( int i = 0; i < monsters.length; i++ ) {
                if ( monsters[i] != null ) {
                    if ( monsters[i].alive && !monsters[i].dying ) {
                        monsters[i].update();
                    }
                    if ( !monsters[i].alive ) {
                        monsters[i] = null;
                    }
                }
            }
        }
    }

    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        Graphics2D graphics2D = ( Graphics2D ) graphics;

        if ( gameState == titleState ) {
            userInterface.draw(graphics2D);
        } else {
            tileManager.draw(graphics2D);

            entities.add(player);

            for ( int i = 0; i < npc.length; i++ ) {
                if ( npc[i] != null ) {
                    entities.add(npc[i]);
                }
            }

            for ( int i = 0; i < objects.length; i++ ) {
                if ( objects[i] != null ) {
                    entities.add(objects[i]);
                }
            }

            for ( int i = 0; i < monsters.length; i++ ) {
                if ( monsters[i] != null ) {
                    entities.add(monsters[i]);
                }
            }

            entities.sort(new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {

                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            for ( Entity entity : entities ) {
                entity.draw(graphics2D);
            }

            entities.clear();

            userInterface.draw(graphics2D);
        }

        graphics2D.dispose();
    }

    public void playMusic(int index) {

        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopMusic() {

        music.stop();
    }

    public void playSoundEffect(int index) {

        soundEffect.setFile(index);
        soundEffect.play();
    }
}
