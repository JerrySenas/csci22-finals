import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;

public class CharacterSelectScreen extends Screen {
    class Portrait extends Sprite {
        public Portrait(int x, int y, String filepath) {
            super(x, y, filepath);
        }

        @Override
        public void draw(Graphics2D g2d) {
            g2d.drawImage(getImage(), (int) getX(), (int) getY(), null);
        }
    }

    class Cursor extends Sprite {
        private int extraSize;
        private double bobTime;
        private boolean isSelected;

        private BufferedImage playerCursor, playerCursorSelected;

        public Cursor(double x, double y, boolean isP1) {
            super(x, y, "assets/portraits/cursor1p.png");

            bobTime = 0;
            setTranslationSpeed(0.5);
            extraSize = 0;
            isSelected = false;

            playerCursor = null;
            playerCursorSelected = null;
            try {
                playerCursor = ImageIO.read(new File(isP1 ? "assets/portraits/cursor1p.png" : "assets/portraits/cursor2p.png"));
                playerCursorSelected = ImageIO.read(new File(isP1 ? "assets/portraits/cursor1pd.png" : "assets/portraits/cursor2pd.png"));
            } catch (Exception e) {
            }
            setImage(playerCursor);
        }

        public void select() {
            isSelected = true;
            extraSize = 250;
            setImage(playerCursorSelected);
        }

        public void deselect() {
            isSelected = false;
            setImage(playerCursor);
        }

        @Override
        public void update() {
            super.update();
            if (!isSelected) {
                bobTime += 0.05;
                setAngle(-5 + (Math.sin(bobTime) + 1) / 2 * (10));
            } else if (extraSize > 0) {
                setAngle(0);
                extraSize -= 40;
                if (extraSize < 0) {
                    extraSize = 0;
                }
            }
        }

        @Override
        public void draw(Graphics2D g2d) {
            AffineTransform reset = g2d.getTransform();
            g2d.rotate(
                Math.toRadians(getAngle()),
                getX() + getImage().getWidth() / 2,
                getY() + getImage().getHeight() / 2
            );
            g2d.drawImage(
                getImage(),
                (int) getX() - 3 - extraSize / 2, (int) getY() - 7 - extraSize / 2,
                getImage().getWidth() + extraSize,
                getImage().getHeight() + extraSize,
                null
            );
            g2d.setTransform(reset);
        }
    }

    class FullBody extends Sprite {
        double scale;
        int height;
        int width;
        boolean isReversed;

        public FullBody(boolean reversed) {
            super(0, 0, "assets/portraits/fullbody01.png");
            scale = 1;
            height = 0;
            width = 0;
            isReversed = reversed;
            setImage(0);
        }

        @Override
        public void draw(Graphics2D g2d) {
            g2d.drawImage(getImage(),
                (int) getX(), (int) getY(),
                width, height,
                null);
        }

        public void setImage(int charaNum) {
            BufferedImage rawImage = loadImage(String.format("assets/portraits/fullbody%02d.png", charaNum));
            int startX = 0;

            for (int i = 0; i < rawImage.getWidth(); i++) {
                int pixel = rawImage.getRGB(i, 0);
                if ((pixel & 0x00FFFFFF) != 0xFFFFFF) {
                    startX = i;
                    break;
                }
            }

            setImage(rawImage.getSubimage(startX, 0, rawImage.getWidth() - startX, rawImage.getHeight()));
            scale = 768.0 / getImage().getHeight();
            height = 768;
            width = (int) (getImage().getWidth() * scale);
            if (isReversed) {
                setTargetX(1024);
                setX(1024);
                width *= -1;
            }
            setY(30);
            setX(-30);
        }
    }

    private Portrait[] portraits;

    private int selectedPortrait;
    private int numCharas;

    private Cursor p1Cursor;
    private Cursor p2Cursor;

    private FullBody p1FullBody;
    private FullBody p2FullBody;

    public CharacterSelectScreen() {
        selectedPortrait = 0;
        numCharas = 10;

        p1FullBody = new FullBody(false);
        p2FullBody = new FullBody(true);
        addSprite(p1FullBody);
        addSprite(p2FullBody);

        portraits = new Portrait[numCharas];

        for (int i = 0; i < portraits.length; i++) {
            int offset = 64;
            int row = i / 2;
            if (row % 2 == 1) {
                offset = 0;
            }
            int xCoord = 350 + offset + (i%2)*125;
            int yCoord = 100 + 110 * row;

            portraits[i] = new Portrait(xCoord, yCoord, String.format("assets/portraits/chara%02d.png", i));
            addSprite(portraits[i]);
        }

        p1Cursor = new Cursor(portraits[0].getX(), portraits[0].getY(), true);
        p2Cursor = new Cursor(portraits[1].getX(), portraits[1].getY(), false);
        addSprite(p2Cursor);
        addSprite(p1Cursor);
    }


    @Override
    public void upAction() {
        if (!p1Cursor.isSelected) {
            selectedPortrait -= 2;
            if (selectedPortrait < 0) {
                selectedPortrait += numCharas;
            }

            p1Cursor.setTargetX(portraits[selectedPortrait].getX());
            p1Cursor.setTargetY(portraits[selectedPortrait].getY());
            p1FullBody.setImage(selectedPortrait);
        }
    }
    @Override
    public void downAction() {
        if (!p1Cursor.isSelected) {
            selectedPortrait += 2;
            if (selectedPortrait > numCharas - 1) {
                selectedPortrait -= numCharas;
            }

            p1Cursor.setTargetX(portraits[selectedPortrait].getX());
            p1Cursor.setTargetY(portraits[selectedPortrait].getY());
            p1FullBody.setImage(selectedPortrait);
        };
    }
    @Override
    public void leftAction() {
        if (!p1Cursor.isSelected) {
            selectedPortrait--;
            if (selectedPortrait < 0) {
                selectedPortrait = numCharas - 1;
            }

            p1Cursor.setTargetX(portraits[selectedPortrait].getX());
            p1Cursor.setTargetY(portraits[selectedPortrait].getY());
            p1FullBody.setImage(selectedPortrait);
        }
    }
    @Override
    public void rightAction() {
        
        if (!p1Cursor.isSelected) {
            selectedPortrait++;
            if (selectedPortrait > numCharas - 1) {
                selectedPortrait = 0;
            }

            p1Cursor.setTargetX(portraits[selectedPortrait].getX());
            p1Cursor.setTargetY(portraits[selectedPortrait].getY());
            p1FullBody.setImage(selectedPortrait);
        }
    }
    @Override
    public void confirmAction() {
        if (!p1Cursor.isSelected) {
            p1Cursor.select();
            p2Cursor.select();
        }
    }
    @Override
    public void cancelAction() {
        if (p1Cursor.isSelected) { p1Cursor.deselect(); }
    }
    
}
