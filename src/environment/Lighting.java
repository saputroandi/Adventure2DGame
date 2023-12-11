package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gamePanel;
    BufferedImage darknessFilter;
    public int dayCounter;
    public float filterAlpha = 0f;

    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;

    public Lighting(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        setLightSource();
    }

    public void setLightSource() {

        darknessFilter = new BufferedImage(gamePanel.screenWidth, gamePanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = ( Graphics2D ) darknessFilter.getGraphics();

        if ( gamePanel.player.currentLight == null ) {
            graphics2D.setColor(new Color(0, 0, 0.1f, 0.9f));
        } else {
            int centerX = gamePanel.player.screenX + (gamePanel.tileSize) / 2;
            int centerY = gamePanel.player.screenY + (gamePanel.tileSize) / 2;

            Color[] color = new Color[10];
            float[] fraction = new float[10];

            color[0] = new Color(0, 0, 0.1f, 0.1f);
            color[1] = new Color(0, 0, 0.1f, 0.42f);
            color[2] = new Color(0, 0, 0.1f, 0.52f);
            color[3] = new Color(0, 0, 0.1f, 0.61f);
            color[4] = new Color(0, 0, 0.1f, 0.69f);
            color[5] = new Color(0, 0, 0.1f, 0.76f);
            color[6] = new Color(0, 0, 0.1f, 0.82f);
            color[7] = new Color(0, 0, 0.1f, 0.87f);
            color[8] = new Color(0, 0, 0.1f, 0.91f);
            color[9] = new Color(0, 0, 0.1f, 0.94f);

            fraction[0] = 0f;
            fraction[1] = 0.4f;
            fraction[2] = 0.5f;
            fraction[3] = 0.6f;
            fraction[4] = 0.65f;
            fraction[5] = 0.7f;
            fraction[6] = 0.75f;
            fraction[7] = 0.8f;
            fraction[8] = 0.85f;
            fraction[9] = 0.9f;

            RadialGradientPaint radialGradientPaint = new RadialGradientPaint(centerX, centerY, (( float ) gamePanel.player.currentLight.lightRadius / 2), fraction, color);

            graphics2D.setPaint(radialGradientPaint);
        }

        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        graphics2D.dispose();
    }

    public void update() {

        if ( gamePanel.player.lightUpdated ) {
            setLightSource();
            gamePanel.player.lightUpdated = false;
        }

        if ( dayState == day ) {

            dayCounter++;

            if ( dayCounter > 600 ) {
                dayState = dusk;
                dayCounter = 0;
            }
        }

        if ( dayState == dusk ) {

            filterAlpha += 0.001f;
            if ( filterAlpha > 1f ) {
                filterAlpha = 1f;
                dayState = night;
            }
        }

        if ( dayState == night ) {

            dayCounter++;
            if ( dayCounter > 600 ) {
                dayState = dawn;
                dayCounter = 0;
            }
        }

        if ( dayState == dawn ) {

            filterAlpha -= 0.001f;

            if ( filterAlpha < 0 ) {
                filterAlpha = 0;
                dayState = day;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        graphics2D.drawImage(darknessFilter, 0, 0, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        String situation = "";

        switch ( dayState ) {
            case day:
                situation = "Day";
                break;
            case dusk:
                situation = "Dusk";
                break;
            case night:
                situation = "Night";
                break;
            case dawn:
                situation = "Dawn";
                break;
        }

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(50F));
        graphics2D.drawString(situation, 800, 500);
    }
}
