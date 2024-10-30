import javax.swing.*;
import javax.swing.border.Border;
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
    private JPanel checkersPanel;
    private JLabel scoreLabel;
    private Border originalBorder;
    private JPanel lastEnteredSquare;
    private int selectedRow;
    private int selectedColumn;
    private CheckerButton[][] boardButtons;
    private CheckerButton lastSelectedButton;

    public Game() {
        initGame();
        selectedRow = 0;
        selectedColumn = 0;
        int BOARD_SIZE=getBoardSize();
        boardButtons = new CheckerButton[BOARD_SIZE][BOARD_SIZE];
        frame = new JFrame("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        scoreLabel = new JLabel("White: "+getWhiteScore()+" | Black: "+getBlackScore(), JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(scoreLabel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        Dimension boardDimension = new Dimension(80 * BOARD_SIZE, 80 * BOARD_SIZE);
        /*JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(boardDimension);*/
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        boardPanel.setPreferredSize(boardDimension);
        //boardPanel.setBounds(0, 0, boardDimension.width, boardDimension.height);  // Position of the board
        populateBoard();
        /*
        checkersPanel = new JPanel();
        checkersPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        checkersPanel.setBounds(0, 0, boardDimension.width, boardDimension.height);
        checkersPanel.setOpaque(false);
        //populateCheckers();



        layeredPane.add(boardPanel, Integer.valueOf(0));  // Add the board at the bottom layer
        layeredPane.add(checkersPanel, Integer.valueOf(1));  // Add the pieces panel at the top layer*/
        centerPanel.add(boardPanel);
        // Add the layered pane to the frame
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP -> changeSelectedCell(selectedRow-1, selectedColumn); // Moving up (row - 1, col + 0)
                    case KeyEvent.VK_DOWN -> changeSelectedCell(selectedRow+1, selectedColumn); // Moving down (row + 1, col + 0)
                    case KeyEvent.VK_LEFT -> changeSelectedCell(selectedRow, selectedColumn-1); // Moving left (row + 0, col - 1)
                    case KeyEvent.VK_RIGHT -> changeSelectedCell(selectedRow, selectedColumn+1); // Moving right (row + 0, col + 1)
                    case KeyEvent.VK_ENTER -> handleClick(selectedRow, selectedColumn); // Handle Enter key for selection/movement
                }
                highlightSelectedCell();
            }
        });
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
        int BOARD_SIZE = getBoardSize();
        int WHITE_CHECKER = getWHITE_CHECKER();
        int BLACK_CHECKER = getBLACK_CHECKER();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Color squareColor = (row + col) % 2 == 0 ? Color.WHITE : new Color(102, 102, 102);
                Color pieceColor = null;
                boolean hasPiece = false;

                int piece = getBoardCell(row, col);
                if (piece == WHITE_CHECKER) {
                    pieceColor = Color.WHITE;
                    hasPiece = true;
                } else if (piece == BLACK_CHECKER) {
                    pieceColor = Color.BLACK;
                    hasPiece = true;
                }

                CheckerButton checkerButton = new CheckerButton(squareColor, pieceColor, hasPiece, row, col);
                boardButtons[row][col] = checkerButton;
                checkerButton.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleClick(checkerButton.getRow(), checkerButton.getColumn());
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

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
/*
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
    }*/
    public CheckerButton getBoardButtonAt(int row, int col){
        return boardButtons[row][col];
    }

    private void updateBoard() {
        boardPanel.removeAll(); // Clear the board
        populateBoard(); // Re-populate board based on updated state
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public void updateGame() {
            updateBoard();
            updateScoreLabel();
            if(isEndGame()) endGame();
    }

    public JPanel getCheckersPanel() {
        return checkersPanel;
    }
    public native int getWHITE_CHECKER();
    public native int getBLACK_CHECKER();
    public native int getWhiteScore();
    public native int getBlackScore();
    public void updateScoreLabel(){
        scoreLabel.setText("White: "+getWhiteScore()+" | Black: "+getBlackScore());
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
    public native int processClick(int row, int col);
    public void changeSelectedCell(int row, int col){
        if((selectedRow >=0 &&selectedRow < 8) || (selectedColumn >=0 &&selectedColumn < 8)) {
            selectedRow = row;
            selectedColumn = col;
        }
    }
    public int getSelectedRow(){
        return selectedRow;
    }
    public int getSelectedColumn(){
        return selectedColumn;
    }
    public void highlightSelectedCell(){
        if (lastSelectedButton != null) {
            lastSelectedButton.setSelected(false);
        }
        CheckerButton selectedButton = getBoardButtonAt(selectedRow, selectedColumn);

        if (selectedButton != null) {
            selectedButton.setSelected(true);
            lastSelectedButton = selectedButton;
        }
    }
    public void handleClick(int row, int col){
        int result = processClick(row, col);
        CheckerButton clickedButton = getBoardButtonAt(row, col);
        if (clickedButton != null) {
            if (result == 0) {
                clickedButton.setClicked(false); // Deselect if necessary
            } else if (result == 1) {
                clickedButton.setClicked(true);  // Highlight selection
            } else if (result == 2) {
                updateGame();                // Refresh game state
            }
        }
    }
}
