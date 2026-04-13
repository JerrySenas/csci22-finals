import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;

public class CharacterSelectScreen extends Screen {
    private Sprite[] portraits;

    private int p1Selected;
    private int p2Selected;
    public static final int numCharas = 10;

    private Cursor p1Cursor;
    private Cursor p2Cursor;

    private FullBody p1FullBody;
    private FullBody p2FullBody;
    private BufferedImage[] fullBodyImages;

    private ReadFromServer readFromServer;
    private DataOutputStream writeToServer;

    public CharacterSelectScreen() {
        p1Selected = 0;
        p2Selected = 0;


        fullBodyImages = new BufferedImage[numCharas];


        portraits = new Sprite[numCharas];

        p1FullBody = new FullBody(false);
        p2FullBody = new FullBody(true);

        addSprite(p1FullBody);
        addSprite(p2FullBody);

        for (int i = 0; i < portraits.length; i++) {
            int offset = 64;
            int row = i / 2;
            if (row % 2 == 1) {
                offset = 0;
            }
            int xCoord = 350 + offset + (i%2)*125;
            int yCoord = 100 + 110 * row;

            portraits[i] = new Sprite(xCoord, yCoord, String.format("assets/portraits/chara%02d.png", i));
            addSprite(portraits[i]);
            fullBodyImages[i] = loadImage(String.format("assets/portraits/fullbody%02d.png", i));
        }


        p1Cursor = new Cursor(portraits[0].getX(), portraits[0].getY(), true);
        p2Cursor = new Cursor(portraits[1].getX(), portraits[1].getY(), false);
        addSprite(p2Cursor);
        addSprite(p1Cursor);


        connectToServer();
    }

    public void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 9999);
            readFromServer = new ReadFromServer(new DataInputStream(socket.getInputStream()));
            new Thread(readFromServer).start();
            writeToServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error in establishing connection with server: " + e);
        }
    }

    @Override
    public void update() {
        super.update();
        p1Cursor.setTargetX(portraits[p1Selected].getX());
        p1Cursor.setTargetY(portraits[p1Selected].getY());
        p1FullBody.setImage(p1Selected);

        p2Cursor.setTargetX(portraits[p2Selected].getX());
        p2Cursor.setTargetY(portraits[p2Selected].getY());
        p2FullBody.setImage(p2Selected);
    }


    @Override
    public void upAction() {
        try {
            writeToServer.writeInt(KeyEvent.VK_UP);
            writeToServer.flush();
        } catch (IOException e) { }
    }
    @Override
    public void downAction() {
        try {
            writeToServer.writeInt(KeyEvent.VK_DOWN);
            writeToServer.flush();
        } catch (IOException e) { }
    }
    @Override
    public void leftAction() {
        try {
            writeToServer.writeInt(KeyEvent.VK_LEFT);
            writeToServer.flush();
        } catch (IOException e) { }
    }
    @Override
    public void rightAction() {
        try {
            writeToServer.writeInt(KeyEvent.VK_RIGHT);
            writeToServer.flush();
        } catch (IOException e) { }
    }
    @Override
    public void confirmAction() {
        try {
            writeToServer.write(KeyEvent.VK_Z);
        } catch (IOException e) { }
        if (!p1Cursor.isSelected) {
            p1Cursor.select();
            p2Cursor.select();
        }
    }
    @Override
    public void cancelAction() {
        try {
            writeToServer.write(KeyEvent.VK_X);
        } catch (IOException e) { }
        if (p1Cursor.isSelected) { p1Cursor.deselect(); }
    }
    
    private class Cursor extends Sprite {
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

    private class FullBody extends Sprite {
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
        }

        @Override
        public void draw(Graphics2D g2d) {
            g2d.drawImage(getImage(),
                (int) getX(), (int) getY(),
                width, height,
                null);
        }

        public void setImage(int charaNum) {
            BufferedImage rawImage = fullBodyImages[charaNum];
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
            setY(30);
            setX(-30);

            if (isReversed) {
                setTargetX(1024);
                setX(1024);
                width *= -1;
            }
        }
    }
    private class ReadFromServer implements Runnable {
        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in) {
            dataIn = in;
        }

        public void run() {
            try {
                while (true) {
                    p1Selected = dataIn.readInt();
                    p2Selected = dataIn.readInt();
                    Thread.sleep(10);
                }
            } catch (Exception e) { }
        }
    }

    private class WriteToServer implements Runnable {
        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out) {
            dataOut = out;
        }

        public void run() {

        }
    }
}
