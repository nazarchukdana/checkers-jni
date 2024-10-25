import javax.swing.*;
import java.awt.*;

public interface Handler {
    default void handleCellClick(Component clickedComponent) {
        int row = getRow(clickedComponent);
        int col = getCol(clickedComponent);
        if (clickedComponent instanceof Checker) {
            Checker clickedChecker = (Checker) clickedComponent;
            if (isCurrentPlayerChecker(clickedChecker)) {
                if (getGame().getClickedRow() == -1 && getGame().getClickedColumn() == -1) {
                    getGame().changeClickedRow(row);
                    getGame().changeClickedColumn(col);
                    clickedChecker.setSelected(true);
                } else if (getGame().getClickedRow() == row && getGame().getClickedColumn() == col) {
                    clickedChecker.setSelected(false);
                    getGame().changeClickedRow(-1);
                    getGame().changeClickedColumn(-1);
                }
            } else {
                System.out.println("Cannot select opponent's checker.");
            }
        } else if (isAnyClicked() && getGame().moveAndUpdateBoard(getGame().getClickedRow(), getGame().getClickedColumn(), row, col)) {
            getGame().changeClickedRow(-1);
            getGame().changeClickedColumn(-1);
        }
        getGame().getCheckersPanel().repaint();
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
    private boolean isCurrentPlayerChecker(Checker checker) {
        int currentPlayer = getGame().getCurrentPlayer(); // Get the current player
        Color checkerColor = checker.getFillColor(); // Assuming Checker has a method to get fill color

        // Check if the checker color matches the current player
        return (currentPlayer == getGame().getWHITE_CHECKER() && checkerColor.equals(Color.WHITE)) ||
                (currentPlayer == getGame().getBLACK_CHECKER() && checkerColor.equals(Color.BLACK));
    }
    private boolean isAnyClicked(){
        System.out.println(getGame().getClickedRow() != -1 && getGame().getClickedColumn() != -1);
        return getGame().getClickedRow() != -1 && getGame().getClickedColumn() != -1;
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
