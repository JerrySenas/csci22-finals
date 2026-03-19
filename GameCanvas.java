import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JComponent {
    private int width;
    private int height;

    private Screen currentScreen;
    private CharacterSelectScreen charaScreen;
    
    public GameCanvas(int w, int h) {
        width = w;
        height = h;

        charaScreen = new CharacterSelectScreen();
    }

    public void setupControls(ActionMap am, InputMap im) {
        charaScreen.setupControls(am, im);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        currentScreen.draw(g2d);
    }
}
