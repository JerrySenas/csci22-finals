import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JComponent {
    int width;
    int height;
    
    public GameCanvas(int w, int h) {
        width = w;
        height = h;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
    }
}
