import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class CharacterSelectScreen extends Screen {
    class Portrait {
        private int x;
        private int y;
        private double centerX;
        private int centerY;
        private int radius;
        private Path2D frame;

        public Portrait(int x, int y) {
            this.x = x;
            this.y = y;
            radius = 50;
            centerX = x + radius * Math.sqrt(3) / 2;
            centerY = y + radius;

            frame = new Path2D.Double();
            frame.moveTo(centerX, y);
            for (int i = 1; i < 7; i++) {
                double angle = Math.toRadians(-90 + i*60);
                frame.lineTo(centerX + radius * Math.cos(angle), centerY + radius * Math.sin(angle));
            }

        }
    }

    private Portrait[] portraits;
    private int[] portraitXCoords;
    private int[] portraitYCoords;
    private int selected;

    public CharacterSelectScreen() {
        selected = 0;

        portraits = new Portrait[9];
        portraitXCoords = new int[9];
        portraitYCoords = new int[9];

        for (int i = 0; i < portraits.length; i++) {
            int offset = 200;
            int row = i / 3;
            if (row % 2 == 1) {
                offset = 150;
            }
            portraitXCoords[i] = offset + (i%3)*100;
            portraitYCoords[i] = 300 + 100 * row;

            portraits[i] = new Portrait(portraitXCoords[i], portraitYCoords[i]);
        }
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        for (Portrait portrait : portraits) {
            g2d.draw(portrait.frame);
        }
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(new Ellipse2D.Double(portraitXCoords[selected] - 7, portraitYCoords[selected], 100, 100));
    }

    @Override
    public void upAction() {
        if (selected > 2) {
            selected -= 3;
        }
    }
    @Override
    public void downAction() {
        if (selected < 6) {
            selected += 3;
        };
    }
    @Override
    public void leftAction() {
        if (selected > 0) {
            selected--;
        }
    }
    @Override
    public void rightAction() {
        
        if (selected < 8) {
            selected++;
        }
    }
    @Override
    public void confirmAction() {
        System.out.println("Z pressed");
    }
    @Override
    public void cancelAction() {
        System.out.println("X pressed");
    }
    
}
