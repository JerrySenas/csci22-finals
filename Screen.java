import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The abstract class for a distinct game state. Three are implemented
 *  in this project: the MainMenuScreen, the CharacterSelectScreen, and the GameScreen.
 * <p>
 * Each Screen is responsible for updating, rendering, and handling user input. Screens 
 * are dependent on a JPanel (which, for this project, is the found in GameFrame).
 */
public abstract class Screen {
    /**
     * Clears then populates both the ActionMap and the InputMap of the GameFrame.
     * @param am The ActionMap of the GameFrame
     * @param im The InputMap of the GameFrame
     */
    public void setupControls(ActionMap am, InputMap im) {
        am.clear();
        setupActionMap(am);
        im.clear();
        setupInputMap(im);
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
     * Populates the ActionMap with the AbstractActions associated with, by default, 
     * the up, down, left and right arrow keys, the z key, and the x key.
     * <p>
     * This method assumes that upAction, downAction, leftAction, rightAction, 
     * confirmAction (for the z key), and cancelAction (for the x key) are all 
     * already implemented.
     * 
     * @param am The ActionMap to be populated.
     */
    public void setupActionMap(ActionMap am) {
        am.put("up", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { upAction(); }
        });
        am.put("down", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { downAction(); }
        });
        am.put("left", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { leftAction(); }
        });
        am.put("right", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { rightAction(); }
        });
        am.put("confirm", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { confirmAction(); }
        });
        am.put("cancel", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) { cancelAction(); }
        });
    }

    /**
     * Maps key bindings to the corresponding method for an InputMap.
     * Confirm is mapped to the z key and cancel is mapped to the x key.
     * <p>
     * This method assumes that setupActionMap has already been run.
     * 
     * @param im The InputMap to be populated with key bindings.
     */
    public void setupInputMap(InputMap im) {
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, false), "confirm");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, false), "cancel");
    }
}
