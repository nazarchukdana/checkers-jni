import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class BoardMouseHandler extends MouseAdapter {
    private Game game;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public BoardMouseHandler(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Get the clicked component
        Component clickedComponent = e.getComponent().getComponentAt(e.getPoint());

        // Determine the row and column of the clicked square
        int row = getClickedRow(clickedComponent);
        int col = getClickedCol(clickedComponent);

        // Check if a checker is clicked
        if (clickedComponent instanceof Checker) {
            Checker clickedChecker = (Checker) clickedComponent;
            // Check if the clicked checker belongs to the current player
            if (isCurrentPlayerChecker(clickedChecker)) {
                if (selectedRow == -1 && selectedCol == -1) {
                    // Select the checker
                    selectedRow = row;
                    selectedCol = col;
                    clickedChecker.setSelected(true);
                } else if (selectedRow == row && selectedCol == col) {
                    // Deselect if the same checker is clicked again
                    clickedChecker.setSelected(false);
                    selectedRow = -1;
                    selectedCol = -1;
                }
            } else {
                // Attempt to select an opponent's checker (invalid move)
                System.out.println("Cannot select opponent's checker.");
            }
        } else if (isAnySelected() && game.moveAndUpdateBoard(selectedRow, selectedCol, row, col)){
                // Move successful, reset selection
                selectedRow = -1;
                selectedCol = -1;
            // Handle clicks on empty squares (if necessary)
            System.out.println("Clicked on empty square at (" + row + ", " + col + ")");
        }

        // Repaint to reflect changes
        game.getCheckersPanel().repaint();
    }
    private boolean isCurrentPlayerChecker(Checker checker) {
        int currentPlayer = game.getCurrentPlayer(); // Get the current player
        Color checkerColor = checker.getFillColor(); // Assuming Checker has a method to get fill color

        // Check if the checker color matches the current player
        return (currentPlayer == game.getWHITE_CHECKER() && checkerColor.equals(Color.WHITE)) ||
                (currentPlayer == game.getBLACK_CHECKER() && checkerColor.equals(Color.BLACK));
    }
    private boolean isAnySelected(){
        return selectedCol != -1 && selectedRow!= -1;
    }

    // Helper methods to get the row and column of the clicked square
    private int getClickedRow(Component component) {
        Component[] components = game.getCheckersPanel().getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] == component) {
                return i / game.getBOARD_SIZE();  // Calculate row based on index and grid size
            }
        }
        return -1;
    }

    private int getClickedCol(Component component) {
        Component[] components = game.getCheckersPanel().getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] == component) {
                return i % game.getBOARD_SIZE();  // Calculate column based on index and grid size
            }
        }
        return -1;
    }
}
