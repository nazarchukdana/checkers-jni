import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoardKeyHandler implements KeyListener, Handler {
    private Game game;
    public BoardKeyHandler(Game game) {
        this.game = game;
        highlightSelectedCell(game.getSelectedRow(), game.getSelectedColumn()); // Highlight the starting cell
    }
    private void highlightSelectedCell(int row, int col) {
        int index = row * game.getBoardSize() + col;
        Component component = game.getCheckersPanel().getComponent(index);
        highlightCell(component);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int BOARD_SIZE = game.getBoardSize();
        // Update selected row/column based on arrow keys
        switch (keyCode) {
            case KeyEvent.VK_UP -> game.changeSelectedRow((game.getSelectedRow() > 0) ? game.getSelectedRow() - 1 : game.getSelectedRow());
            case KeyEvent.VK_DOWN -> game.changeSelectedRow((game.getSelectedRow() < BOARD_SIZE - 1) ? game.getSelectedRow() + 1 : game.getSelectedRow());
            case KeyEvent.VK_LEFT -> game.changeSelectedColumn((game.getSelectedColumn() > 0)? game.getSelectedColumn() - 1 : game.getSelectedColumn());
            case KeyEvent.VK_RIGHT -> game.changeSelectedColumn((game.getSelectedColumn() < BOARD_SIZE - 1) ? game.getSelectedColumn() + 1 : game.getSelectedColumn());
            case KeyEvent.VK_ENTER -> handleEnterKey(); // Handle Enter key for selection/movement
        }

        // Highlight the new cell based on updated selectedRow and selectedCol
        highlightSelectedCell(game.getSelectedRow(), game.getSelectedColumn());
    }
    private void handleEnterKey() {
        int index = game.getSelectedRow() * game.getBoardSize() + game.getSelectedColumn();
        Component component = game.getCheckersPanel().getComponent(index);
        handleCellClick(component);
    }
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public Game getGame() {
        return game;
    }
}
