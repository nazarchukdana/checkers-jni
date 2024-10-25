import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;

public class BoardMouseHandler extends MouseAdapter implements Handler{
    private Game game;

    public BoardMouseHandler(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component clickedComponent = e.getComponent().getComponentAt(e.getPoint());
        handleCellClick(clickedComponent);
    }

    @Override
    public Game getGame() {
        return game;
    }

    // Helper methods to get the row and column of the clicked square
    @Override
    public void mouseEntered(MouseEvent e) {
        Component enteredComponent = e.getComponent().getComponentAt(e.getPoint());
        int row = getRow(enteredComponent);
        int col = getCol(enteredComponent);
        game.changeSelectedRow(row);
        game.changeSelectedColumn(col);
        highlightCell(enteredComponent);
    }
    @Override
    public void mouseExited(MouseEvent e) {
        resetLastHighlightedCell();
    }

}
