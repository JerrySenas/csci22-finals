import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameCanvas extends JComponent {
    private int width;
    private int height;

    private Screen currentScreen;
    private CharacterSelectScreen charaScreen;

    private Timer animTimer;
    
    public GameCanvas(int w, int h) {
        width = w;
        height = h;

        charaScreen = new CharacterSelectScreen();
        currentScreen = charaScreen;
        animTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                currentScreen.update();
                repaint();
            }
        });
        animTimer.start();
    }

    public void setupControls(ActionMap am, InputMap im) {
        charaScreen.setupControls(am, im);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        currentScreen.draw(g2d);
    }
}
