package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;

    URL[] soundUrl = new URL[30];

    public Sound() {

        init();
    }

    public void init() {

        soundUrl[0] = getClass().getResource("/sounds/BlueBoyAdventure.wav");
        soundUrl[1] = getClass().getResource("/sounds/coin.wav");
        soundUrl[2] = getClass().getResource("/sounds/powerup.wav");
        soundUrl[3] = getClass().getResource("/sounds/unlock.wav");
        soundUrl[4] = getClass().getResource("/sounds/fanfare.wav");
        soundUrl[5] = getClass().getResource("/sounds/hitmonster.wav");
        soundUrl[6] = getClass().getResource("/sounds/receivedamage.wav");
        soundUrl[7] = getClass().getResource("/sounds/swingweapon.wav");

    }

    public void setFile(int index) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl[index]);

            clip = AudioSystem.getClip();

            clip.open(audioInputStream);
        } catch ( Exception exception ) {
            exception.printStackTrace();
        }
    }

    public void play() {

        clip.start();
    }

    public void stop() {

        clip.stop();
    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
