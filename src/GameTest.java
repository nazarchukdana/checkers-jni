
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();// Initialize a new game instance before each test
    }
    @Test
    void testGetBoardSize() {
        assertEquals(8, game.getBoardSize());
    }
    @Test
    public void testInitialBoardSetup() {
        int WHITE_CHECKER = game.getWHITE_CHECKER();
        int BLACK_CHECKER = game.getBLACK_CHECKER();
        int EMPTY = 0;
        int BOARD_SIZE = game.getBoardSize();
        // Check the initial setup for each row and column
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int piece = game.getBoardCell(row, col);

                // Top three rows should contain black checkers in specific positions
                if (row < 3 && (row + col) % 2 == 1) {
                    assertEquals(BLACK_CHECKER, piece, "Black checker should be at row " + row + ", col " + col);
                }
                // Middle two rows should be empty
                else if (row >= 3 && row <= 4) {
                    assertEquals(EMPTY, piece, "Cell should be empty at row " + row + ", col " + col);
                }
                // Bottom three rows should contain white checkers in specific positions
                else if (row > 4 && (row + col) % 2 == 1) {
                    assertEquals(WHITE_CHECKER, piece, "White checker should be at row " + row + ", col " + col);
                }
                // Other cells should be empty
                else {
                    assertEquals(EMPTY, piece, "Cell should be empty at row " + row + ", col " + col);
                }
            }
        }
    }
    @Test
    void testInitialScores() {
        assertEquals(0, game.getWhiteScore());
        assertEquals(0, game.getBlackScore());
    }
    @Test
    public void testInvalidMove(){
        assertEquals(1, game.handleCellClick(5, 0), "selecting the checker");//selecting checker
        assertEquals(-1, game.handleCellClick(4, 0), "invalid move to cell above");//up
        assertEquals(-1, game.handleCellClick(6, 0), "invalid move to cell below");//down
        assertEquals(-1, game.handleCellClick(6, 1), "invalid move to the cell where is another checker");//another checker
        assertEquals(-1, game.handleCellClick(5, 1), "invalid move to the right cell");//right
        assertEquals(-1, game.handleCellClick(4, -1), "invalid move to the cell which is out of bounds");//out of bounds
    }
    @Test
    public void testValidMove(){
        assertEquals(1, game.handleCellClick(5, 2));
        assertEquals(2, game.handleCellClick(4, 3));
        assertEquals(1, game.handleCellClick(2, 1));
        assertEquals(2, game.handleCellClick(3, 0));
        assertEquals(1, game.handleCellClick(4, 3));
        assertEquals(2, game.handleCellClick(3, 2));
        assertEquals(1, game.handleCellClick(3, 0));
        assertEquals(2, game.handleCellClick(4, 1));
    }
    @Test
    public void testValidCapturing(){
        game.handleCellClick(5, 2);
        game.handleCellClick(4, 3);
        game.handleCellClick(2, 1);
        game.handleCellClick(3, 2);
        game.handleCellClick(4, 3);
        game.handleCellClick(2, 1);
        assertEquals(0, game.getBoardCell(3, 2), "Capturing should reset the state of captured black checker to 0");//checking the captured black checker
        game. handleCellClick(1, 0);
        game.handleCellClick(3, 2);
        assertEquals(0, game.getBoardCell(2, 1), "Capturing should reset the state of captured  white checker to 0");
    }
    @Test
    public void testInvalidCapturing(){
        game.handleCellClick(5, 2);
        game.handleCellClick(4, 3);
        game.handleCellClick(2, 1);
        game.handleCellClick(3, 0);
        game.handleCellClick(5, 4);
        assertEquals(-1, game.handleCellClick(3, 2), "Capturing of your own checker should be not allowed");//capturing your checker
        game.handleCellClick(4, 3);
        game.handleCellClick(3, 2);
        game.handleCellClick(3, 0);
        game.handleCellClick(4, 1);
        game.handleCellClick(3, 2);
        assertEquals(-1, game.handleCellClick(5, 0), "Capturing when you have at this pos another checker should be not allowed");
    }
    @Test
    public void testChangingScoreAfterCapturing(){
        game.handleCellClick(5, 2);
        game.handleCellClick(4, 3);
        game.handleCellClick(2, 1);
        game.handleCellClick(3, 2);
        game.handleCellClick(4, 3);
        game.handleCellClick(2, 1);
        assertEquals(1, game.getWhiteScore(), "White score should be changed after capturing");
        game. handleCellClick(1, 0);
        game.handleCellClick(3, 2);
        assertEquals(1, game.getBlackScore(), "Black score should be changed after capturing");
    }
    @Test
    public void testWinning(){
        game.handleCellClick(5, 2);
        game.handleCellClick(4, 3);
        game.handleCellClick(2, 1);
        game.handleCellClick(3, 2);
        game.handleCellClick(4, 3);
        game.handleCellClick(2, 1);
        game.handleCellClick(1, 2);
        game.handleCellClick(3, 0);
        game.handleCellClick(5, 0);
        game.handleCellClick(4, 1);
        game.handleCellClick(3, 0);
        game.handleCellClick(5, 2);
        game.handleCellClick(5, 4);
        game.handleCellClick(4, 3);
        game.handleCellClick(5, 2);
        game.handleCellClick(3, 4);
        game.handleCellClick(5, 6);
        game.handleCellClick(4, 5);
        game.handleCellClick(3, 4);
        game.handleCellClick(5, 6);
        game.handleCellClick(6, 5);
        game.handleCellClick(5, 4);
        game.handleCellClick(2, 3);
        game.handleCellClick(3, 4);
        game.handleCellClick(5, 4);
        game.handleCellClick(4, 3);
        game.handleCellClick(3, 4);
        game.handleCellClick(5, 2);
        game.handleCellClick(7, 4);
        game.handleCellClick(6, 5);
        game.handleCellClick(5, 6);
        game.handleCellClick(7, 4);
        game.handleCellClick(7, 6);
        game.handleCellClick(6, 5);
        game.handleCellClick(7, 4);
        game.handleCellClick(5, 6);
        game.handleCellClick(6, 1);
        game.handleCellClick(5, 0);
        game.handleCellClick(5, 2);
        game.handleCellClick(7, 4);
        game.handleCellClick(7, 2);
        game.handleCellClick(6, 3);
        game.handleCellClick(7, 4);
        game.handleCellClick(5, 2);
        game.handleCellClick(7, 0);
        game.handleCellClick(6, 1);
        game.handleCellClick(5, 2);
        game.handleCellClick(7, 0);
        game.handleCellClick(5, 0);
        game.handleCellClick(6, 1);
        game.handleCellClick(7, 0);
        game.handleCellClick(5, 2);
        game.handleCellClick(6, 7);
        game.handleCellClick(7, 6);
        game.handleCellClick(2, 5);
        game.handleCellClick(3, 4);
        game.handleCellClick(7, 6);
        game.handleCellClick(6, 5);
        game.handleCellClick(5, 6);
        game.handleCellClick(7, 4);
        assertTrue(game.isEndGame());
        assertEquals(game.getBLACK_CHECKER(), game.getWinner());
    }

}
