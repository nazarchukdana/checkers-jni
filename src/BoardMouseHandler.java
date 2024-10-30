import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardMouseHandler extends MouseAdapter implements Handler {
    private Game game;

    public BoardMouseHandler(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component clickedComponent = e.getComponent().getComponentAt(e.getPoint());
        int row = getRow(clickedComponent);
        int col = getCol(clickedComponent);
        handleCellClick(row, col);
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Component enteredComponent = e.getComponent().getComponentAt(e.getPoint());
        int row = getRow(enteredComponent);
        int col = getCol(enteredComponent);
        game.changeSelectedCell(row , col);
        highlightCell(enteredComponent);
    }
    @Override
    public void mouseExited(MouseEvent e) {
        resetLastHighlightedCell();
    }

}
