import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;
    @BeforeEach
    public void setUp() {
        game = new Game();
    }
    @Test
    void testGetBoardSize() {
        assertEquals(8, game.getBoardSize(), "Board size should be 8");
    }
    @Test
    public void testInitialBoardSetup() {
        int WHITE_CHECKER = game.getWHITE_CHECKER();
        int BLACK_CHECKER = game.getBLACK_CHECKER();
        int EMPTY = 0;
        int BOARD_SIZE = game.getBoardSize();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int checker = game.getBoardCell(row, col);
                if (row < 3 && (row + col) % 2 == 1) {
                    assertEquals(BLACK_CHECKER, checker, "Black checker should be at row " + row + ", col " + col);
                }
                else if (row >= 3 && row <= 4) {
                    assertEquals(EMPTY, checker, "Cell should be empty at row " + row + ", col " + col);
                }
                else if (row > 4 && (row + col) % 2 == 1) {
                    assertEquals(WHITE_CHECKER, checker, "White checker should be at row " + row + ", col " + col);
                }
                else {
                    assertEquals(EMPTY, checker, "Cell should be empty at row " + row + ", col " + col);
                }
            }
        }
    }
    @Test
    void testInitialScores() {
        assertEquals(0, game.getWhiteScore(), "White Score should be 0 initially");
        assertEquals(0, game.getBlackScore(), "WhiteScore should be 0 initially");
    }
    @Test
    public void testInValidMoveTryingToMoveUp(){
        game.processClick(5, 0);//selecting checker
        assertEquals(-1, game.processClick(4, 0), "Moving up should be invalid");
    }
    @Test
    public void testInValidMoveTryingToMoveDown(){
        game.processClick(5, 0);//selecting checker
        assertEquals(-1, game.processClick(6, 0), "Moving down should be invalid");
    }
    @Test
    public void testInValidMoveTryingToMoveToAnotherChecker(){
        game.processClick(5, 0);//selecting checker
        assertEquals(-1, game.processClick(6, 1), "Moving to another checker should be invalid");
    }
    @Test
    public void testInValidMoveTryingToMoveToRight(){
        game.processClick(5, 0);//selecting checker
        assertEquals(-1, game.processClick(5, 1), "Moving to the right should be invalid");
    }
    @Test
    public void testInValidMoveTryingToMoveOutOfBounds(){
        game.processClick(5, 0);//selecting checker
        assertEquals(-1, game.processClick(4, -1), "Moving out of bounds should be invalid");
    }
    @Test
    public void testInValidMoveTryingToMoveToOpponentChecker(){
        game.processClick(5, 2);//selecting
        game.processClick(4, 3);
        game.processClick(2, 1);//selecting
        game.processClick(3, 2);
        game.processClick(4, 3);
        assertEquals(-1, game.processClick(3, 2), "Moving to opponent checker should be invalid");
    }
    @Test
    public void testValidMove(){
        game.processClick(5, 2);//selecting
        assertEquals(2, game.processClick(4, 3), "Moving up-right should be valid");//up-right
        game.processClick(2, 1);//selecting
        assertEquals(2, game.processClick(3, 0), "Moving down-left should be valid");//down-left
        game.processClick(4, 3);//selecting
        assertEquals(2, game.processClick(3, 2), "Moving up-left should be valid");//up-left
        game.processClick(3, 0);//selecting
        assertEquals(2, game.processClick(4, 1), "Moving down-left should be valid");//down-right
    }
    @Test
    public void testValidCapturing(){
        game.processClick(5, 2);//selecting
        game.processClick(4, 3);
        game.processClick(2, 1);//selecting
        game.processClick(3, 2);
        game.processClick(4, 3);//selecting
        game.processClick(2, 1);
        assertEquals(0, game.getBoardCell(3, 2), "Capturing should reset the state of captured black checker to 0");//checking the captured black checker
        game.processClick(1, 0);//selecting
        game.processClick(3, 2);
        assertEquals(0, game.getBoardCell(2, 1), "Capturing should reset the state of captured  white checker to 0");
    }
    @Test
    public void testInvalidCapturing(){
        game.processClick(5, 2);//selecting
        game.processClick(4, 3);
        game.processClick(2, 1);//selecting
        game.processClick(3, 0);
        game.processClick(5, 4);//selecting
        assertEquals(-1, game.processClick(3, 2), "Capturing of your own checker should be not allowed");//capturing your checker
        game.processClick(4, 3);//selecting
        game.processClick(3, 2);
        game.processClick(3, 0);//selecting
        game.processClick(4, 1);
        game.processClick(3, 2);//selecting
        assertEquals(-1, game.processClick(5, 0), "Capturing when you have at this pos another checker should be not allowed");
    }
    @Test
    public void testChangingScoreAfterCapturing(){
        game.processClick(5, 2);
        game.processClick(4, 3);
        game.processClick(2, 1);
        game.processClick(3, 2);
        game.processClick(4, 3);
        game.processClick(2, 1);
        assertEquals(1, game.getWhiteScore(), "White score should be changed after capturing");
        game.processClick(1, 0);
        game.processClick(3, 2);
        assertEquals(1, game.getBlackScore(), "Black score should be changed after capturing");
    }
    @Test
    public void testWinning(){
        game.processClick(5, 2);
        game.processClick(4, 3);
        game.processClick(2, 1);
        game.processClick(3, 2);
        game.processClick(4, 3);
        game.processClick(2, 1);
        game.processClick(1, 2);
        game.processClick(3, 0);
        game.processClick(5, 0);
        game.processClick(4, 1);
        game.processClick(3, 0);
        game.processClick(5, 2);
        game.processClick(5, 4);
        game.processClick(4, 3);
        game.processClick(5, 2);
        game.processClick(3, 4);
        game.processClick(5, 6);
        game.processClick(4, 5);
        game.processClick(3, 4);
        game.processClick(5, 6);
        game.processClick(6, 5);
        game.processClick(5, 4);
        game.processClick(2, 3);
        game.processClick(3, 4);
        game.processClick(5, 4);
        game.processClick(4, 3);
        game.processClick(3, 4);
        game.processClick(5, 2);
        game.processClick(7, 4);
        game.processClick(6, 5);
        game.processClick(5, 6);
        game.processClick(7, 4);
        game.processClick(7, 6);
        game.processClick(6, 5);
        game.processClick(7, 4);
        game.processClick(5, 6);
        game.processClick(6, 1);
        game.processClick(5, 0);
        game.processClick(5, 2);
        game.processClick(7, 4);
        game.processClick(7, 2);
        game.processClick(6, 3);
        game.processClick(7, 4);
        game.processClick(5, 2);
        game.processClick(7, 0);
        game.processClick(6, 1);
        game.processClick(5, 2);
        game.processClick(7, 0);
        game.processClick(5, 0);
        game.processClick(6, 1);
        game.processClick(7, 0);
        game.processClick(5, 2);
        game.processClick(6, 7);
        game.processClick(7, 6);
        game.processClick(2, 5);
        game.processClick(3, 4);
        game.processClick(7, 6);
        game.processClick(6, 5);
        game.processClick(5, 6);
        game.processClick(7, 4);
        assertTrue(game.isEndGame());
        assertEquals(game.getBLACK_CHECKER(), game.getWinner());
    }

}
