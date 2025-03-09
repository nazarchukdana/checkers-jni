#include <vector>
#include <cstdlib>
#include "Game.h"
const int EMPTY = 0;
const int WHITE_CHECKER = 1;
const int BLACK_CHECKER = 2;
const int WHITE_KING = 3;
const int BLACK_KING = 4;
const int BOARD_SIZE = 8;
class Game{
private:
    int whiteScore;
    int blackScore;
    int currentPlayer;
    int clickedRow;
    int clickedColumn;
    std::vector<std::vector<int>> boardState;
    auto initBoard() -> void {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 == 0) {
                    boardState[row][col] = EMPTY;
                } else {
                    if (row < 3) {
                        boardState[row][col] = BLACK_CHECKER;
                    } else if (row > 4) {
                        boardState[row][col] = WHITE_CHECKER;
                    } else {
                        boardState[row][col] = EMPTY;
                    }
                }
            }
        }
    }
    auto isOpponentCheckerOnPlace(int row, int col) -> bool{
        int opponent = currentPlayer == WHITE_CHECKER ? BLACK_CHECKER : WHITE_CHECKER;
        return boardState[row][col] == opponent || boardState[row][col] == opponent + 2;
    }
    auto calcOpponentPos(int fromRow, int fromCol, int toRow, int toCol) -> std::vector<int>{
        int opponentRow = (toRow + fromRow) / 2;
        int opponentCol = (toCol + fromCol) / 2;
        return {opponentRow, opponentCol};
    }
    auto isOpponentOnTheWay(int fromRow, int fromCol, int toRow, int toCol) -> bool{
        auto opponentPosition = calcOpponentPos(fromRow, fromCol, toRow, toCol);
        return isOpponentCheckerOnPlace(opponentPosition[0], opponentPosition[1]);
    }
    auto capturingMove(int fromRow, int fromCol, int toRow, int toCol){
        auto opponentPosition = calcOpponentPos(fromRow, fromCol, toRow, toCol);
        boardState[opponentPosition[0]][opponentPosition[1]] = EMPTY;
        currentPlayer == WHITE_CHECKER ? whiteScore++ : blackScore++;
    }
    auto isMoveValid(int fromRow, int fromCol, int toRow, int toCol) -> bool {
        if (toRow < 0 || toCol < 0) return false;
        int checker = boardState[fromRow][fromCol];
        if(checker == WHITE_CHECKER && fromRow - toRow == 1 && abs(fromCol - toCol) == 1) return true;
        else if(checker == BLACK_CHECKER && fromRow - toRow == -1 && abs(fromCol - toCol) == 1) return true;
        else if ((checker == WHITE_KING || checker == BLACK_KING) && abs(fromRow - toRow) == 1 && abs(fromCol - toCol) == 1) return true;
        else if (abs(fromRow - toRow) == 2 && abs(fromCol - toCol) == 2 && isOpponentOnTheWay(fromRow, fromCol, toRow, toCol)) return true;
        return false;
    }
    auto moveChecker(int fromRow, int fromCol, int toRow, int toCol)-> void{
        int checker = boardState[fromRow][fromCol];
        boardState[fromRow][fromCol] = EMPTY;
        if (checker == WHITE_CHECKER && toRow == 0) {
            boardState[toRow][toCol] = WHITE_KING;
        } else if (checker == BLACK_CHECKER && toRow == BOARD_SIZE - 1) {
            boardState[toRow][toCol] = BLACK_KING;
        } else {
            boardState[toRow][toCol] = checker;
        }
        if (abs(fromRow - toRow) == 2 && abs(fromCol - toCol) == 2){
            capturingMove(fromRow, fromCol, toRow, toCol);
        }
    }
    auto changeCurrentPlayer() -> void{
        currentPlayer = currentPlayer == WHITE_CHECKER? BLACK_CHECKER : WHITE_CHECKER;
    }
    auto isChecker(int row, int col) -> bool {return boardState[row][col] == WHITE_CHECKER || boardState[row][col] == BLACK_CHECKER
                                                     || boardState[row][col] == WHITE_KING || boardState[row][col] == BLACK_KING ;}
    auto isPlayersChecker(int row, int col) -> bool{
        return boardState[row][col] == currentPlayer || boardState[row][col] == currentPlayer + 2;
    }
    auto isAnyClicked() -> bool{
        return clickedRow != -1 || clickedColumn != -1;
    }
    auto clickChecker(int row, int col) -> void{
        clickedRow = row;
        clickedColumn = col;
    }
    auto isCheckerClickedBefore(int row, int col) -> bool{
        return clickedRow == row && clickedColumn == col;
    }
    auto setClickedPosToInitial()-> void{
        clickedRow = -1;
        clickedColumn = -1;
    }
public:
    Game(){
        currentPlayer = WHITE_CHECKER;
        whiteScore = 0;
        blackScore = 0;
        clickedRow = -1;
        clickedColumn = -1;
        boardState = std::vector<std::vector<int>>(BOARD_SIZE, std::vector<int>(BOARD_SIZE, 0));
        initBoard();
    }
    auto getBoardCell(int row, int col) -> int{
        return boardState[row][col];
    }
    auto processClick(int row, int col) -> int {
        if (isChecker(row, col)) {
            if(isPlayersChecker(row, col)){
                if (isCheckerClickedBefore(row, col)) {
                    setClickedPosToInitial();
                    return 0;
                } else if (!isAnyClicked()){
                    clickChecker(row, col);
                    return 1;
                }
            }
        } else if (isAnyClicked()) {
            if (isMoveValid(clickedRow, clickedColumn, row, col)) {
                moveChecker(clickedRow, clickedColumn, row, col);
                changeCurrentPlayer();
                setClickedPosToInitial();
                return 2;
            }
        }
        return -1;
    }
    auto getWhiteScore()-> int{
        return whiteScore;
    }
    auto getBlackScore()->int{
        return blackScore;
    }
};

Game game;
JNIEXPORT void JNICALL Java_Game_initGame
        (JNIEnv * env, jobject jobj){
    game = Game();
}
JNIEXPORT jint JNICALL Java_Game_getBoardSize
        (JNIEnv * env, jobject jobj){
    return BOARD_SIZE;
}
JNIEXPORT jint JNICALL Java_Game_getBoardCell
        (JNIEnv * env, jobject jobj, jint row, jint col){
    return game.getBoardCell(row, col);
}
JNIEXPORT jint JNICALL Java_Game_getWHITE_1CHECKER
        (JNIEnv * env, jobject jobj){
    return WHITE_CHECKER;
}
JNIEXPORT jint JNICALL Java_Game_getBLACK_1CHECKER
        (JNIEnv * env, jobject jobj){
    return BLACK_CHECKER;
}
JNIEXPORT jint JNICALL Java_Game_getWHITE_1KING
        (JNIEnv *, jobject){
    return WHITE_KING;
}
JNIEXPORT jint JNICALL Java_Game_getBLACK_1KING
        (JNIEnv *, jobject){
    return BLACK_KING;
}
JNIEXPORT jint JNICALL Java_Game_getWhiteScore
        (JNIEnv * env, jobject jobj){
    return game.getWhiteScore();
}
JNIEXPORT jint JNICALL Java_Game_getBlackScore
        (JNIEnv * env, jobject jobj){
    return game.getBlackScore();
}
JNIEXPORT jboolean JNICALL Java_Game_isEndGame
        (JNIEnv * env, jobject jobj){
    return game.getWhiteScore() == 12 || game.getBlackScore() == 12;
}
JNIEXPORT jint JNICALL Java_Game_getWinner
        (JNIEnv * env, jobject jobj){
    return  game.getWhiteScore() == 12? WHITE_CHECKER : BLACK_CHECKER;
}
JNIEXPORT jint JNICALL Java_Game_processClick
        (JNIEnv *, jobject, jint row, jint col){
    return game.processClick(row, col);
}



