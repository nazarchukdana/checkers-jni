import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class CheckerButton extends JButton {
    private Color pieceColor;
    private boolean hasPiece;
    private boolean isSelected = false;
    private boolean isClicked = false;
    private int row;
    private int col;
    Border originalBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
    Border hoveredBorder = BorderFactory.createLineBorder(Color.ORANGE, 2);

    public CheckerButton(Color backgroundColor, Color pieceColor, boolean hasPiece, int row, int col) {
        setBackground(backgroundColor);
        this.pieceColor = pieceColor;
        this.hasPiece = hasPiece;
        this.row = row;
        this.col = col;
        setContentAreaFilled(false); // Disable default button background
        setFocusPainted(false);      // Remove focus border
        setBorder(originalBorder);// Initial border

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
        // Draw the square button background
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the checker piece if present
        if (hasPiece) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int diameter = Math.min(getWidth(), getHeight()) - 20;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            // Draw checker piece (filled circle)
            g2d.setColor(pieceColor);
            g2d.fillOval(x, y, diameter, diameter);

            // Draw border around the checker piece
            g2d.setColor(isClicked ? Color.ORANGE : Color.GRAY);
            g2d.setStroke(new BasicStroke(3)); // Adjust thickness of the border here
            g2d.drawOval(x, y, diameter, diameter); // Draws a border around the circle
        }
    }
}
