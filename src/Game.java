import javax.swing.*;
import java.awt.*;

public class Game {
    private JFrame frame;
    private JPanel boardPanel;
    private JPanel checkersPanel;
    private JLabel scoreLabel;
    private final int BOARD_SIZE = getBoardSize();
    private final int WHITE_CHECKER = 1;
    private final int BLACK_CHECKER = 2;

    public Game() {
        frame = new JFrame("Checkers Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        scoreLabel = new JLabel("White: 0 | Black: 0", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(scoreLabel, BorderLayout.NORTH);
        Dimension boardDimension = new Dimension(80*BOARD_SIZE, 80*BOARD_SIZE);
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        populateBoard();
        frame.add(boardPanel);
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

        // Add the layered pane to the frame
        frame.add(layeredPane, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private native int[][] getBoardState();
    private native int getBoardSize();
    private void populateBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel square = new JPanel();
                // Checkerboard pattern: alternate between black and white squares
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);  // White square
                } else {
                    square.setBackground(new Color(102,102,102));  // Black square
                }

                // Add the square to the board panel
                boardPanel.add(square);
            }
        }
    }
    private void populateCheckers() {
        int [][] boardState = getBoardState();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel checker = new JPanel();  // Placeholder for each piece slot
                checker.setOpaque(false);  // Set transparent by default (for empty spaces)

                if (boardState[row][col] == WHITE_CHECKER) {
                    checker = new Checker(Color.WHITE, Color.GRAY);  // Add a white piece with gray border
                } else if (boardState[row][col] == BLACK_CHECKER) {
                    checker = new Checker(Color.BLACK, Color.GRAY);  // Add a black piece with gray border
                }

                // Add the piece (or empty transparent slot) to the piecesPanel
                checkersPanel.add(checker);
            }
        }
    }
}
