
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
    public void testInitialPlayerIsWhite() {
        assertEquals(game.getWHITE_CHECKER(), game.getCurrentPlayer(), "Initial player should be White.");
    }

    @Test
    public void testCheckersInitialization() {
        // Verify board size
        assertEquals(8, game.getBoardSize(), "Board size should be 8x8.");

        // Verify initial board state
        int[][] initialState = game.getBoardState();
        assertNotNull(initialState, "Initial board state should not be null.");
        assertEquals(8, initialState.length, "Board should have 8 rows.");

        // Check initial position of WHITE and BLACK checkers
        assertEquals(game.getWHITE_CHECKER(), initialState[7][0], "White checker should be at initial position (7,0).");
        assertEquals(game.getBLACK_CHECKER(), initialState[0][1], "Black checker should be at initial position (0,1).");
    }

    @Test
    public void testValidMove() {
        // Attempt a valid move for WHITE
        boolean isValid = game.moveAndUpdateBoard(5, 0, 4, 1);
        assertTrue(isValid, "Valid move should return true.");
    }

    @Test
    public void testInvalidMove() {
        // Attempt an invalid move (not diagonal, or out of bounds)
        boolean isInvalid = game.moveAndUpdateBoard(5, 0, 5, 1);
        assertFalse(isInvalid, "Invalid move should return false.");
    }

    @Test
    public void testScoreUpdate() {
        game.moveAndUpdateBoard(5, 2, 4, 3);
        game.moveAndUpdateBoard(2, 5, 3, 4);
        game.moveAndUpdateBoard(4, 3, 2, 5);
        // Simulate capturing mov
        assertEquals(1, game.getWhiteScore(), "White's score should be updated to 1 after capture.");
    }

    @Test
    public void testTurnSwitching() {
        // Simulate a series of moves
        game.moveAndUpdateBoard(5, 0, 4, 1);  // White moves
        assertEquals(game.getBLACK_CHECKER(), game.getCurrentPlayer(), "After White moves, it should be Black's turn.");

        game.moveAndUpdateBoard(2, 1, 3, 2);  // Black moves
        assertEquals(game.getWHITE_CHECKER(), game.getCurrentPlayer(), "After Black moves, it should be White's turn.");
    }

    @Test
    void testEndGame() {
        // Simulate an end game state by reaching max score
        for (int i = 0; i < 12; i++) {
            game.changeWhiteScore(i+1); // Set score directly for testing
        }
        assertTrue(game.isEndGame(), "Game should end when a player reaches 12.");
        assertEquals(game.getWHITE_CHECKER(), game.getWinner(), "White should be the winner with max score.");
    }
    @Test
    public void testUpdateScoreLabel() {
        // Simulate score changes
        game.changeWhiteScore(3);  // Assuming you have implemented this to add to the score
        game.changeBlackScore(5);   // Assuming you have implemented this to add to the score

        // Update the label after changing the scores
        game.updateScoreLabel(); // Assuming this method refreshes the label based on current scores

        // Retrieve the current score label text
        String expectedLabelText = "White: " + game.getWhiteScore() + " | Black: " + game.getBlackScore();

        // Verify that the label text has been updated correctly
        assertEquals(expectedLabelText, game.getScoreLabel().getText(), "Score label should display the correct score.");
    }
}
