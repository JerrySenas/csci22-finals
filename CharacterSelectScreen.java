import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class CharacterSelectScreen extends Screen {

    public CharacterSelectScreen() {
        
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, 100, 100);
    }
    
    @Override
    public void upAction() {
        System.out.println("Up pressed");
    }
    @Override
    public void downAction() {
        System.out.println("Down pressed");
    }
    @Override
    public void leftAction() {
        System.out.println("Left pressed");
    }
    @Override
    public void rightAction() {
        System.out.println("Right pressed");
    }
    @Override
    public void confirmAction() {
        System.out.println("Z pressed");
    }
    @Override
    public void cancelAction() {
        System.out.println("X pressed");
    }
    
}
