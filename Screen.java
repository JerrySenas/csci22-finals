import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The abstract class for a distinct game state. Three are implemented
 *  in this project: the MainMenuScreen, the CharacterSelectScreen, and the GameScreen.
 * <p>
 * Each Screen is responsible for updating, rendering, and handling user input. Screens 
 * are dependent on another class that is a JComponent 
 * (which, for this project, is the GameCanvas).
 */
public abstract class Screen {
    ActionMap actionMap;
    InputMap inputMap;

    /**
     * Constructs the Screen, then populates the ActionMap and InputMap of the GameCanvas.
     * @param am The ActionMap of the GameCanvas.
     * @param im The InputMap of the GameCanvas.
     */
    public Screen(ActionMap am, InputMap im) {
        actionMap = am;
        inputMap = im;
        setupActionMap();
        setupInputMap();
    }

    /**
     * Updates the state of this Screen. Runs every frame.
     */
    public abstract void update();
    /**
     * Defines what must be drawn for the current frame. Runs every frame.
     * 
     * @param g2d The Graphics2D object that is passed into the paintComponent method of the GameCanvas.
     */
    public abstract void draw(Graphics2D g2d);

    /**
     * Defines how this Screen should handle an up arrow key press.
     */
    public abstract void upAction();
    /**
     * Defines how this Screen should handle a down arrow key press.
     */
    public abstract void downAction();
    /**
     * Defines how this Screen should handle a left arrow key press.
     */
    public abstract void leftAction();
    /**
     * Defines how this Screen should handle a right arrow key press.
     */
    public abstract void rightAction();
    /**
     * Defines how this Screen should handle a z key press.
     */
    public abstract void confirmAction();
    /**
     * Defines how this Screen should handle an x key press.
     */
    public abstract void cancelAction();

    /**
     * Populates actionMap with the AbstractActions associated with, by default, 
     * the up, down, left and right arrow keys, the z key, and the x key.
     * <p>
     * This method assumes that upAction, downAction, leftAction, rightAction, 
     * confirmAction (for the z key), and cancelAction (for the x key) are all 
     * already implemented.
     */
    public void setupActionMap() {
        actionMap.put("up", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { upAction(); }
        });
        actionMap.put("down", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { downAction(); }
        });
        actionMap.put("left", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { leftAction(); }
        });
        actionMap.put("right", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { rightAction(); }
        });
        actionMap.put("confirm", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { confirmAction(); }
        });
        actionMap.put("cancel", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { cancelAction(); }
        });
    }

    /**
     * Maps key bindings to the corresponding AbstractAction in actionMap.
     * Confirm is mapped to the z key and cancel is mapped to the x key.
     * <p>
     * This method assumes that setupActionMap has already been run.
     * @see #setupActionMap()
     */
    public void setupInputMap() {
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, false), "confirm");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, false), "cancel");
    }
}
