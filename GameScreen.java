import java.awt.event.*;
import javax.swing.*;

public class GameScreen extends Screen {
    private Player p1;
    
    public GameScreen() {
        p1 = new Player(0, 0, "assets/portraits/cursor1p.png");
        addSprite(p1);
    }

    @Override
    public void upAction() {
        p1.setIsUpPressed(true);
    }

    public void upReleasedAction() {
        p1.setIsUpPressed(false);
    }

    @Override
    public void downAction() {
        p1.setIsDownPressed(true);
    }

    public void downReleasedAction() {
        p1.setIsDownPressed(false);
    }

    @Override
    public void leftAction() {
        p1.setIsLeftPressed(true);
    }

    public void leftReleasedAction() {
        p1.setIsLeftPressed(false);
    }

    @Override
    public void rightAction() {
        p1.setIsRightPressed(true);
    }

    public void rightReleasedAction() {
        p1.setIsRightPressed(false);
    }

    @Override
    public void confirmAction() {

    }

    @Override
    public void cancelAction() {

    }

    @Override
    public void setupActionMap(ActionMap am) {
        super.setupActionMap(am);
        am.put("up release", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { upReleasedAction(); }
        });
        am.put("down release", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { downReleasedAction(); }
        });
        am.put("left release", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { leftReleasedAction(); }
        });
        am.put("right release", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { rightReleasedAction(); }
        });
    }

    @Override
    public void setupInputMap(InputMap im) {
        super.setupInputMap(im);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "up release");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "down release");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left release");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right release");
    }

}
