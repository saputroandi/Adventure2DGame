package main;

import java.io.*;
import java.util.Objects;

public class Config {

    GamePanel gamePanel;

    public Config(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    public void saveConfig() {

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("config.txt"));

            if ( gamePanel.fullScreen ) {
                bufferedWriter.write("on");
            }

            if ( !gamePanel.fullScreen ) {
                bufferedWriter.write("off");
            }

            bufferedWriter.newLine();

            bufferedWriter.write(String.valueOf(gamePanel.music.volumeScale));
            bufferedWriter.newLine();

            bufferedWriter.write(String.valueOf(gamePanel.soundEffect.volumeScale));
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("config.txt"));

            String line = bufferedReader.readLine();

            if ( line.equals("on") ) {
                gamePanel.fullScreen = true;
            }

            if ( line.equals("off") ) {
                gamePanel.fullScreen = false;
            }

            line = bufferedReader.readLine();
            gamePanel.music.volumeScale = Integer.parseInt(line);

            line = bufferedReader.readLine();
            gamePanel.soundEffect.volumeScale = Integer.parseInt(line);

            bufferedReader.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
