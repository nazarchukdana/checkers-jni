import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class CheckerButton extends JButton {
    private Color checkerColor;
    private boolean hasChecker;
    private boolean isSelected = false;
    private boolean isClicked = false;
    private final int row;
    private final int col;
    private final Border originalBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
    private final Border hoveredBorder = BorderFactory.createLineBorder(Color.ORANGE, 2);

    public CheckerButton(Color backgroundColor, Color checkerColor, boolean hasChecker, int row, int col) {
        setBackground(backgroundColor);
        this.checkerColor = checkerColor;
        this.hasChecker = hasChecker;
        this.row = row;
        this.col = col;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(originalBorder);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        repaint();
    }
    public void setClicked(boolean clicked){
        isClicked = clicked;
        repaint();
    }
    public int getRow(){
        return row;
    }
    public int getColumn(){
        return col;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBorder(isSelected? hoveredBorder : originalBorder);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        if (hasChecker) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int diameter = Math.min(getWidth(), getHeight()) - 20;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            g2d.setColor(checkerColor);
            g2d.fillOval(x, y, diameter, diameter);
            g2d.setColor(isClicked ? Color.ORANGE : Color.GRAY);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(x, y, diameter, diameter);
        }
    }
}
