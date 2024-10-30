import javax.swing.*;
import java.awt.*;

public class Checker extends JPanel {
    private Color fillColor;
    private Color borderColor;
    private boolean isSelected = false;

    public Checker(Color fillColor, Color borderColor) {
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        this.setOpaque(false);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the size of the panel
        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - 10;  // Set the diameter with some padding
        Color currentBorderColor = isSelected ? Color.ORANGE : borderColor;
        // Draw the border
        g2d.setColor(currentBorderColor);
        g2d.fillOval(5, 5, diameter, diameter);

        // Draw the checker piece inside the border
        g2d.setColor(fillColor);
        g2d.fillOval(8, 8, diameter - 6, diameter - 6);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(80, 80);  // Set the preferred size for the pieces
    }
    public void setSelected(boolean selected) {

        this.isSelected = selected;
        repaint();
    }
    public Color getFillColor(){
        return fillColor;
    }
}

