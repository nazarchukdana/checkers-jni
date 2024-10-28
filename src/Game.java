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
    private BoardKeyHandler keyHandler;

    public Game() {
        initGame();
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

    public native int[][] getBoardState();

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
        int[][] boardState = getBoardState();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel checker = new JPanel();  // Placeholder for each piece slot
                checker.setOpaque(false);  // Set transparent by default (for empty spaces)
                if (boardState[row][col] == WHITE_CHECKER) {
                    checker = new Checker(Color.WHITE, Color.GRAY);  // Add a white piece with gray border
                } else if (boardState[row][col] == BLACK_CHECKER) {
                    checker = new Checker(Color.BLACK, Color.GRAY);  // Add a black piece with gray border
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

    public boolean moveAndUpdateBoard(int fromRow, int fromCol, int toRow, int toCol) {
        // Call the native method to move the checker
        if (moveCheckerJNI(fromRow, fromCol, toRow, toCol)) {
            updateBoard();
            updateScoreLabel();
            if(isEndGame()) endGame();
            return true;
        }

        return false;
    }

    private native boolean moveCheckerJNI(int fromRow, int fromCol, int toRow, int toCol);

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
    public native int getSelectedRow();
    public native int getSelectedColumn();
    public native void changeSelectedRow(int row);
    public native void changeSelectedColumn(int col);
    public native int getClickedRow();
    public native int getClickedColumn();
    public native void changeClickedRow(int row);
    public native void changeClickedColumn(int col);
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
}
