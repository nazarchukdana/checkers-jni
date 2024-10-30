import javax.swing.*;
import java.awt.*;

public interface Handler {
    default void handleCellClick(int row, int col) {
        Component clickedComponent = getComponentAtPos(row, col);
        int result = getGame().handleCellClick(row, col);
        if (result == 0) {
            ((Checker) clickedComponent).setSelected(false);
        } else if (result == 1) {
            ((Checker) clickedComponent).setSelected(true);
        } else if (result == 2) {
            getGame().updateGame();
            getGame().getCheckersPanel().repaint();
        }
    }
    private Component getComponentAtPos(int row, int col){
        int index = row * getGame().getBoardSize() + col;
        Component component = getGame().getCheckersPanel().getComponent(index);
        return component;
    }

    default int getRow(Component component) {
        Component[] components = getGame().getCheckersPanel().getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] == component) {
                return i / getGame().getBoardSize();  // Calculate row based on index and grid size
            }
        }
        return -1;
    }

    default int getCol(Component component) {
        Component[] components = getGame().getCheckersPanel().getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] == component) {
                return i % getGame().getBoardSize();  // Calculate column based on index and grid size
            }
        }
        return -1;
    }

    default void resetLastHighlightedCell() {
        if (getGame().getLastEnteredSquare() != null) {
            getGame().getLastEnteredSquare().setBorder(getGame().getOriginalBorder());
            getGame().changeLastEnteredSquare(null);
        }
    }
    default void highlightCell(Component component){
        if (component instanceof JPanel) {
            resetLastHighlightedCell();
            JPanel square = (JPanel) component;
            getGame().changeOriginalBorder(square.getBorder());
            square.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
            getGame().changeLastEnteredSquare(square);
        }
        getGame().getCheckersPanel().repaint();
    }
    // Retrieve the game instance (to be implemented in handlers)
    Game getGame();
}
