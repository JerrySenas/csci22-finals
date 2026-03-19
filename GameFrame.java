import javax.swing.*;
import java.awt.*;

public class GameFrame {
    private JFrame frame;
    private int width;
    private int height;

    private JPanel cp;

    private GameCanvas gameCanvas;

    public GameFrame() {
        frame = new JFrame();
        width = 1024;
        height = 768;
    }

    public void setupGUI() {
        cp = (JPanel) frame.getContentPane();
        frame.setSize(width, height);
        frame.setTitle("Final Project - Senas - Soriano");

        gameCanvas = new GameCanvas(width, height);
        gameCanvas.setupControls(cp.getInputMap(), cp.getActionMap());
        cp.add(gameCanvas);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
