import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class Game {
    static {
        System.loadLibrary("libproj01_lib");
    }
    private JFrame frame;
    private JPanel boardPanel;
    private JPanel checkersPanel;
    private JLabel scoreLabel;
    private Border originalBorder;
    private JPanel lastEnteredSquare;
    private int selectedRow;
    private int selectedColumn;
    private BoardKeyHandler keyHandler;

    public Game() {
        initGame();
        selectedRow = 0;
        selectedColumn = 0;
        int BOARD_SIZE=getBoardSize();
        frame = new JFrame("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        scoreLabel = new JLabel("White: "+getWhiteScore()+" | Black: "+getBlackScore(), JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(scoreLabel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        Dimension boardDimension = new Dimension(80 * BOARD_SIZE, 80 * BOARD_SIZE);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(boardDimension);
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        boardPanel.setBounds(0, 0, boardDimension.width, boardDimension.height);  // Position of the board
        populateBoard();
        checkersPanel = new JPanel();
        checkersPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        checkersPanel.setBounds(0, 0, boardDimension.width, boardDimension.height);
        checkersPanel.setOpaque(false);
        populateCheckers();



        layeredPane.add(boardPanel, Integer.valueOf(0));  // Add the board at the bottom layer
        layeredPane.add(checkersPanel, Integer.valueOf(1));  // Add the pieces panel at the top layer
        centerPanel.add(layeredPane);
        // Add the layered pane to the frame
        frame.add(centerPanel, BorderLayout.CENTER);
        keyHandler = new BoardKeyHandler(this);
        frame.addKeyListener(keyHandler);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public native void initGame();

    public native int getBoardCell(int row, int col);

    public native int getBoardSize();

    private void populateBoard() {
        int BOARD_SIZE=getBoardSize();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel square = new JPanel();
                // Checkerboard pattern: alternate between black and white squares
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);  // White square
                } else {
                    square.setBackground(new Color(102, 102, 102));  // Black square
                }
                // Add the square to the board panel
                boardPanel.add(square);
            }
        }
    }

    private void populateCheckers() {
        int WHITE_CHECKER = getWHITE_CHECKER();
        int BLACK_CHECKER = getBLACK_CHECKER();
        int BOARD_SIZE=getBoardSize();
        BoardMouseHandler mouseHandler = new BoardMouseHandler(this);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel checker = new JPanel();
                checker.setOpaque(false);
                if (getBoardCell(row, col) == WHITE_CHECKER) {
                    checker = new Checker(Color.WHITE, Color.GRAY);
                } else if (getBoardCell(row, col) == BLACK_CHECKER) {
                    checker = new Checker(Color.BLACK, Color.GRAY);
                }
                checker.addMouseListener(mouseHandler);

                // Add the piece (or empty transparent slot) to the piecesPanel
                checkersPanel.add(checker);
            }
        }
    }

    private void updateBoard() {
        checkersPanel.removeAll();  // Clear current checkers
        populateCheckers();  // Re-populate checkers based on the updated state
        checkersPanel.revalidate();
        checkersPanel.repaint();
    }

    public void updateGame() {
            updateBoard();
            updateScoreLabel();
            if(isEndGame()) endGame();
    }

    public JPanel getCheckersPanel() {
        return checkersPanel;
    }
    public native int getCurrentPlayer();
    public native int getWHITE_CHECKER();
    public native int getBLACK_CHECKER();
    public native int getWhiteScore();
    public native int getBlackScore();
    public native void changeWhiteScore(int score);
    public native void changeBlackScore(int score);
    public void updateScoreLabel(){
        scoreLabel.setText("White: "+getWhiteScore()+" | Black: "+getBlackScore());
    }
    public JLabel getScoreLabel(){
        return scoreLabel;
    }
    public native boolean isEndGame();
    private void endGame(){
        frame.dispose();
        new EndGame(this);
    }
    public native int getWinner();
    public Border getOriginalBorder(){
        return originalBorder;
    }
    public void changeOriginalBorder(Border border){
        originalBorder = border;
    }
    public JPanel getLastEnteredSquare(){
        return lastEnteredSquare;
    }
    public void changeLastEnteredSquare(JPanel panel){
        lastEnteredSquare = panel;
    }
    public native int handleCellClick(int row, int col);
    public void changeSelectedCell(int row, int col){
        selectedRow = row;
        selectedColumn = col;
    }
    public int getSelectedRow(){
        return selectedRow;
    }
    public int getSelectedColumn(){
        return selectedColumn;
    }
}
