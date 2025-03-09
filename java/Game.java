import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Game {
    static {
        System.loadLibrary("libproj01_lib");
    }
    private JFrame frame;
    private JPanel boardPanel;
    private JLabel scoreLabel;
    private int selectedRow;
    private int selectedColumn;
    private CheckerButton[][] boardButtons;
    private CheckerButton lastSelectedButton;
    private final int BOARD_SIZE;
    private final int WHITE_CHECKER;
    private final int BLACK_CHECKER;
    private final int WHITE_KING;
    private final int BLACK_KING;

    public Game() {
        initGame();
        selectedRow = 0;
        selectedColumn = 0;
        BOARD_SIZE=getBoardSize();
        WHITE_CHECKER = getWHITE_CHECKER();
        BLACK_CHECKER = getBLACK_CHECKER();
        WHITE_KING = getWHITE_KING();
        BLACK_KING = getBLACK_KING();
        boardButtons = new CheckerButton[BOARD_SIZE][BOARD_SIZE];
        setGUI();
    }
    public native void initGame();
    private void setGUI(){
        frame = new JFrame("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        scoreLabel = new JLabel("White: "+getWhiteScore()+" | Black: "+getBlackScore(), JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(scoreLabel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        boardPanel.setPreferredSize(new Dimension(80 * BOARD_SIZE, 80 * BOARD_SIZE));
        populateBoard();
        centerPanel.add(boardPanel);
        frame.add(centerPanel, BorderLayout.CENTER);
        setKeyListener();
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void populateBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Color squareColor = (row + col) % 2 == 0 ? Color.WHITE : new Color(102, 102, 102);
                Color checkerColor = null;
                boolean hasChecker = false;
                boolean isKing = false;
                int checker = getBoardCell(row, col);
                if (checker == WHITE_CHECKER) {
                    checkerColor = Color.WHITE;
                    hasChecker = true;
                } else if (checker == BLACK_CHECKER) {
                    checkerColor = Color.BLACK;
                    hasChecker = true;
                } else if (checker == WHITE_KING) {
                    checkerColor = Color.WHITE;
                    hasChecker = true;
                    isKing = true;
                } else if (checker == BLACK_KING) {
                    checkerColor = Color.BLACK;
                    hasChecker = true;
                    isKing = true;
                }
                CheckerButton checkerButton = new CheckerButton(squareColor, checkerColor, hasChecker, isKing, row, col);
                boardButtons[row][col] = checkerButton;
                checkerButton.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleClick(checkerButton.getRow(), checkerButton.getColumn());
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        checkerButton.setSelected(true);
                        changeSelectedCell(checkerButton.getRow(), checkerButton.getColumn());
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        checkerButton.setSelected(false);
                    }
                });
                boardPanel.add(checkerButton);
            }
        }
    }
    private void setKeyListener(){
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP -> changeSelectedCell(selectedRow-1, selectedColumn);
                    case KeyEvent.VK_DOWN -> changeSelectedCell(selectedRow+1, selectedColumn);
                    case KeyEvent.VK_LEFT -> changeSelectedCell(selectedRow, selectedColumn-1);
                    case KeyEvent.VK_RIGHT -> changeSelectedCell(selectedRow, selectedColumn+1);
                    case KeyEvent.VK_ENTER -> handleClick(selectedRow, selectedColumn);
                }
                highlightSelectedCell();
            }
        });
    }
    private void changeSelectedCell(int row, int col){
        if((row >= 0 && row < 8) || (col >= 0 && col < 8)) {
            selectedRow = row;
            selectedColumn = col;
        }
    }
    private void handleClick(int row, int col){
        int result = processClick(row, col);
        CheckerButton clickedButton = getBoardButtonAt(row, col);
        if (clickedButton != null) {
            if (result == 0) {
                clickedButton.setClicked(false);
            } else if (result == 1) {
                clickedButton.setClicked(true);
            } else if (result == 2) {
                updateGame();
            }
        }
    }
    private void highlightSelectedCell(){
        if (lastSelectedButton != null) {
            lastSelectedButton.setSelected(false);
        }
        CheckerButton selectedButton = getBoardButtonAt(selectedRow, selectedColumn);

        if (selectedButton != null) {
            selectedButton.setSelected(true);
            lastSelectedButton = selectedButton;
        }
    }
    private CheckerButton getBoardButtonAt(int row, int col){
        return boardButtons[row][col];
    }
    private void updateGame() {
        updateBoard();
        updateScoreLabel();
        if(isEndGame()) endGame();
    }
    private void updateBoard() {
        boardPanel.removeAll();
        populateBoard();
        boardPanel.revalidate();
        boardPanel.repaint();
    }
    private void updateScoreLabel(){
        scoreLabel.setText("White: "+getWhiteScore()+" | Black: "+getBlackScore());
    }
    private void endGame(){
        frame.dispose();
        new EndGame(getWinner());
    }
    public native int getBoardCell(int row, int col);
    public native int getBoardSize();
    public native int getWHITE_CHECKER();
    public native int getBLACK_CHECKER();
    public native int getWHITE_KING();
    public native int getBLACK_KING();
    public native int getWhiteScore();
    public native int getBlackScore();
    public native boolean isEndGame();
    public native int getWinner();
    public native int processClick(int row, int col);
}
