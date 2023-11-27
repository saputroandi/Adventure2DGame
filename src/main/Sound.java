package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;

    URL[] soundUrl = new URL[30];

    FloatControl floatControl;

    int volumeScale = 3;

    float volume;

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
        soundUrl[8] = getClass().getResource("/sounds/fanfare.wav");
        soundUrl[9] = getClass().getResource("/sounds/cursor.wav");
        soundUrl[10] = getClass().getResource("/sounds/burning.wav");
        soundUrl[11] = getClass().getResource("/sounds/cuttree.wav");
        soundUrl[12] = getClass().getResource("/sounds/gameover.wav");

    }

    public void setFile(int index) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl[index]);

            clip = AudioSystem.getClip();

            clip.open(audioInputStream);
            floatControl = ( FloatControl ) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
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

    public void checkVolume() {


        switch ( volumeScale ) {
            case 0:
                volume = -80f;
                break;
            case 1:
                volume = -20f;
                break;
            case 2:
                volume = -12f;
                break;
            case 3:
                volume = -5f;
                break;
            case 4:
                volume = 1f;
                break;
            case 5:
                volume = 6f;
                break;
        }

        floatControl.setValue(volume);
    }
}
