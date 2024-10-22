import javax.swing.*;
import java.awt.*;

public class Game {
    private JFrame frame;
    private JPanel boardPanel;
    private JPanel checkersPanel;
    private int[][] boardState;
    private final int BOARD_SIZE = 8;
    private final int EMPTY = 0;
    private final int WHITE_PIECE = 1;
    private final int BLACK_PIECE = 2;

    public Game() {
        frame = new JFrame("Checkers Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        initializeBoardState();
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        populateBoard();
        frame.add(boardPanel);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 600));
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        boardPanel.setBounds(0, 0, 600, 600);  // Position of the board
        populateBoard();

        checkersPanel = new JPanel();
        checkersPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        checkersPanel.setBounds(0, 0, 600, 600);
        checkersPanel.setOpaque(false);
        populateCheckers();


        layeredPane.add(boardPanel, Integer.valueOf(0));  // Add the board at the bottom layer
        layeredPane.add(checkersPanel, Integer.valueOf(1));  // Add the pieces panel at the top layer

        // Add the layered pane to the frame
        frame.add(layeredPane);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void initializeBoardState() {
        boardState = new int[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 == 0) {
                    // White squares (these will remain empty)
                    boardState[row][col] = EMPTY;
                } else {
                    // Black squares
                    if (row < 3) {
                        boardState[row][col] = BLACK_PIECE;  // Black pieces in the top rows
                    } else if (row > 4) {
                        boardState[row][col] = WHITE_PIECE;  // White pieces in the bottom rows
                    } else {
                        boardState[row][col] = EMPTY;  // Empty middle rows
                    }
                }
            }
        }
    }

    private void populateBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel square = new JPanel();

                // Checkerboard pattern: alternate between black and white squares
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);  // White square
                } else {
                    square.setBackground(Color.BLACK);  // Black square
                }

                // Add the square to the board panel
                boardPanel.add(square);
            }
        }
    }
    private void populateCheckers() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel checker = new JPanel();  // Placeholder for each piece slot
                checker.setOpaque(false);  // Set transparent by default (for empty spaces)

                if (boardState[row][col] == WHITE_PIECE) {
                    checker = new Checker(Color.WHITE, Color.GRAY);  // Add a white piece with gray border
                } else if (boardState[row][col] == BLACK_PIECE) {
                    checker = new Checker(Color.BLACK, Color.GRAY);  // Add a black piece with gray border
                }

                // Add the piece (or empty transparent slot) to the piecesPanel
                checkersPanel.add(checker);
            }
        }
    }
}
