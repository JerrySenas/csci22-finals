import javax.swing.*;
import java.awt.*;

public class GameFrame {
    JFrame frame;
    int width;
    int height;

    Container cp;

    GameCanvas gameCanvas;

    public GameFrame() {
        frame = new JFrame();
        width = 1024;
        height = 768;
    }

    public void setupGUI() {
        cp = frame.getContentPane();
        frame.setSize(width, height);
        frame.setTitle("Final Project - Senas - Soriano");

        gameCanvas = new GameCanvas(width, height);
        cp.add(gameCanvas);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
