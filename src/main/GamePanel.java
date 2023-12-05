package main;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import entity.Projectile;
import interactive.Tile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    int FPS = 60;

    final int originalTileSize = 16;
    final int scale = 3;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;
    public final int tileSize = originalTileSize * scale;
    public final int screenWidth = tileSize * maxScreenCol;
    public int screenWidth2 = screenWidth;
    public final int screenHeight = tileSize * maxScreenRow;
    public int screenHeight2 = screenHeight;
    public boolean fullScreen = false;
    BufferedImage tempScreen;
    Graphics2D graphics2D;

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;

    public Entity[][] objects = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monsters = new Entity[maxMap][20];
    public Tile[][] interactiveTiles = new Tile[maxMap][50];
    public Entity[][] projectiles = new Entity[maxMap][20];
    public ArrayList<Entity> particles = new ArrayList<>();
    ArrayList<Entity> entities = new ArrayList<>();

    public AssetSetter assetSetter = new AssetSetter(this);
    public UserInterface userInterface = new UserInterface(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public Player player = new Player(this, keyHandler);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public EventHandler eventHandler = new EventHandler(this);
    Config config = new Config(this);

    public TileManager tileManager = new TileManager(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public PathFinder pathFinder = new PathFinder(this);
    Thread gameThread;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
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
        assetSetter.setInteractiveTiles();
        assetSetter.setNpc();
        assetSetter.setMonster();
        playMusic(0);
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        graphics2D = ( Graphics2D ) tempScreen.getGraphics();

        if ( fullScreen ) {
            setFullScreen();
        }
    }

    public void retry() {

        player.setDefaultPosition();
        player.restoreLifeAndMana();
        assetSetter.setMonster();
        assetSetter.setNpc();
    }

    public void restart() {

        player.setDefaultValues();
        player.setItems();
        assetSetter.setObject();
        assetSetter.setInteractiveTiles();
        assetSetter.setNpc();
        assetSetter.setMonster();
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
//                repaint();
                drawToTempScreen();
                drawToScreen();
                delta--;
            }
        }
    }

    public void update() {

        if ( gameState == playState ) {
            player.update();

            for ( int i = 0; i < npc[1].length; i++ ) {
                if ( npc[currentMap][i] != null ) {
                    npc[currentMap][i].update();
                }
            }


            for ( int i = 0; i < monsters[1].length; i++ ) {
                if ( monsters[currentMap][i] != null ) {
                    if ( monsters[currentMap][i].alive && !monsters[currentMap][i].dying ) {
                        monsters[currentMap][i].update();
                    }
                    if ( !monsters[currentMap][i].alive ) {
                        monsters[currentMap][i].checkDrop();
                        monsters[currentMap][i] = null;
                    }
                }
            }

//            for ( int i = 0; i < projectiles.size(); i++ ) {
//                if ( projectiles.get(i) != null ) {
//                    if ( projectiles.get(i).alive ) {
//                        projectiles.get(i).update();
//                    }
//                    if ( !projectiles.get(i).alive ) {
//                        projectiles.remove(i);
//                    }
//                }
//            }

            for ( int i = 0; i < projectiles[0].length; i++ ) {
                if ( projectiles[currentMap][i] != null ) {
                    if ( projectiles[currentMap][i].alive ) {
                        projectiles[currentMap][i].update();
                    }
                    if ( !projectiles[currentMap][i].alive ) {
                        projectiles[currentMap][i] = null;
                    }
                }
            }

            for ( int i = 0; i < particles.size(); i++ ) {
                if ( particles.get(i) != null ) {
                    if ( particles.get(i).alive ) {
                        particles.get(i).update();
                    }
                    if ( !particles.get(i).alive ) {
                        particles.remove(i);
                    }
                }
            }

            for ( int i = 0; i < interactiveTiles[1].length; i++ ) {
                if ( interactiveTiles[currentMap][i] != null ) {
                    interactiveTiles[currentMap][i].update();
                }
            }
        }
    }

    public void drawToTempScreen() {

        if ( gameState == titleState ) {
            userInterface.draw(graphics2D);
        } else {
            tileManager.draw(graphics2D);

            for ( int i = 0; i < interactiveTiles[1].length; i++ ) {
                if ( interactiveTiles[currentMap][i] != null ) {
                    interactiveTiles[currentMap][i].draw(graphics2D);
                }
            }

            entities.add(player);

            for ( int i = 0; i < npc[currentMap].length; i++ ) {
                if ( npc[currentMap][i] != null ) {
                    entities.add(npc[currentMap][i]);
                }
            }

            for ( int i = 0; i < objects[1].length; i++ ) {
                if ( objects[currentMap][i] != null ) {
                    entities.add(objects[currentMap][i]);
                }
            }

            for ( int i = 0; i < monsters[1].length; i++ ) {
                if ( monsters[currentMap][i] != null ) {
                    entities.add(monsters[currentMap][i]);
                }
            }

            for ( int i = 0; i < projectiles[0].length; i++ ) {
                if ( projectiles[currentMap][i] != null ) {
                    entities.add(projectiles[currentMap][i]);
                }
            }

            for ( int i = 0; i < particles.size(); i++ ) {
                if ( particles.get(i) != null ) {
                    entities.add(particles.get(i));
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
    }

//    public void paintComponent(Graphics graphics) {
//
//        super.paintComponent(graphics);
//
//        Graphics2D graphics2D = ( Graphics2D ) graphics;
//
//        if ( gameState == titleState ) {
//            userInterface.draw(graphics2D);
//        } else {
//            tileManager.draw(graphics2D);
//
//            for ( int i = 0; i < interactiveTiles.length; i++ ) {
//                if ( interactiveTiles[i] != null ) {
//                    interactiveTiles[i].draw(graphics2D);
//                }
//            }
//
//            entities.add(player);
//
//            for ( int i = 0; i < npc.length; i++ ) {
//                if ( npc[i] != null ) {
//                    entities.add(npc[i]);
//                }
//            }
//
//            for ( int i = 0; i < objects.length; i++ ) {
//                if ( objects[i] != null ) {
//                    entities.add(objects[i]);
//                }
//            }
//
//            for ( int i = 0; i < monsters.length; i++ ) {
//                if ( monsters[i] != null ) {
//                    entities.add(monsters[i]);
//                }
//            }
//
//            for ( int i = 0; i < projectiles.size(); i++ ) {
//                if ( projectiles.get(i) != null ) {
//                    entities.add(projectiles.get(i));
//                }
//            }
//
//            for ( int i = 0; i < particles.size(); i++ ) {
//                if ( particles.get(i) != null ) {
//                    entities.add(particles.get(i));
//                }
//            }
//
//            entities.sort(new Comparator<Entity>() {
//                @Override
//                public int compare(Entity e1, Entity e2) {
//
//                    return Integer.compare(e1.worldY, e2.worldY);
//                }
//            });
//
//            for ( Entity entity : entities ) {
//                entity.draw(graphics2D);
//            }
//
//            entities.clear();
//
//            userInterface.draw(graphics2D);
//        }
//
//        graphics2D.dispose();
//    }

    public void drawToScreen() {

        Graphics graphics = getGraphics();

        graphics.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        graphics.dispose();
    }

    public void setFullScreen() {

        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        graphicsDevice.setFullScreenWindow(Main.window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
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
