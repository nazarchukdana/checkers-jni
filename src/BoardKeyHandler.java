import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoardKeyHandler implements KeyListener, Handler {
    private Game game;
    public BoardKeyHandler(Game game) {
        this.game = game;
        highlightSelectedCell();
    }
    private void highlightSelectedCell() {
        int index = game.getSelectedRow() * game.getBoardSize() + game.getSelectedColumn();
        Component component = game.getCheckersPanel().getComponent(index);
        highlightCell(component);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP -> game.changeSelectedCell(game.getSelectedRow()-1, game.getSelectedColumn()); // Moving up (row - 1, col + 0)
            case KeyEvent.VK_DOWN -> game.changeSelectedCell(game.getSelectedRow()+1, game.getSelectedColumn()); // Moving down (row + 1, col + 0)
            case KeyEvent.VK_LEFT -> game.changeSelectedCell(game.getSelectedRow(), game.getSelectedColumn()-1); // Moving left (row + 0, col - 1)
            case KeyEvent.VK_RIGHT -> game.changeSelectedCell(game.getSelectedRow(), game.getSelectedColumn()+1); // Moving right (row + 0, col + 1)
            case KeyEvent.VK_ENTER -> handleEnterKey(); // Handle Enter key for selection/movement
        }

        highlightSelectedCell();
    }
    private void handleEnterKey() {
        handleCellClick(game.getSelectedRow(), game.getSelectedColumn());
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
