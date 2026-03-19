import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JComponent {
    private int width;
    private int height;

    private Screen currentScreen;
    
    public GameCanvas(int w, int h) {
        width = w;
        height = h;
    }

    public void setupControls(InputMap im, ActionMap am) {
        
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
    }
}
