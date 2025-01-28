package core;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.plaf.ScrollBarUI;

public class CustomScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
	@Override
    protected void configureScrollBarColors() {
        this.thumbColor = Color.decode("#202634");
        this.trackColor = Color.decode("#2F343F");
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
}
