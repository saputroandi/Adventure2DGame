package main;

import entity.Player;
import object.Object;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GamePanel extends JPanel implements Runnable {

    public final int maxScreenCol = 16;

    public final int maxScreenRow = 12;

    public final int maxWorldCol = 50;

    public final int maxWorldRow = 50;

    public final int playState = 1;

    public final int pauseState = 2;

    final int originalTileSize = 16;

    final int scale = 3;

    public final int tileSize = originalTileSize * scale;

    public final int maxScreenWidth = tileSize * maxScreenCol;

    public final int maxScreenHeight = tileSize * maxScreenRow;

    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public Object[] objects = new Object[10];

    public AssetSetter assetSetter = new AssetSetter(this);

    public UserInterface userInterface = new UserInterface(this);

    public int gameState;

    TileManager tileManager = new TileManager(this);

    KeyHandler keyHandler = new KeyHandler(this);

    public Player player = new Player(this, keyHandler);

    Sound music = new Sound();

    Sound soundEffect = new Sound();

    Thread gameThread;

    int FPS = 60;

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
        playMusic(0);
        gameState = playState;
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
        }

//        if ( gameState == pauseState ) {
//            System.out.println("paused");
//        }
    }

    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        Graphics2D graphics2D = ( Graphics2D ) graphics;

        tileManager.draw(graphics2D);
        Arrays.stream(objects).forEach(object -> {
            if ( object != null ) {
                object.draw(graphics2D, this);
            }
        });
        player.draw(graphics2D);

        userInterface.draw(graphics2D);

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
