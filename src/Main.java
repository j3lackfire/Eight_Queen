/**
 * Created by Le Pham Minh Duc on 1/11/2017.
 */
public class Main {
    public static  void main(String args[]) {
        Board board = new Board(GlobalVariables.boardSize);
        Board otherBoard = new Board(GlobalVariables.boardSize);
        boolean combineToBoardOne = true;
        int inversePenalty = 0;
        int loopCount = 0;
        String printString = "";
        while (loopCount < GlobalVariables.maxFinessEvaluation && inversePenalty != GlobalVariables.getMaxInversePenaltyValue()) {
            loopCount ++;
            if (combineToBoardOne) { //so we always have to parent, we combine from this one to another one, back and ford
                board = board.combineBoard(otherBoard);
                inversePenalty = board.getInversePenalty();
                combineToBoardOne = false;
            } else {
                otherBoard = otherBoard.combineBoard(board);
                inversePenalty = otherBoard.getInversePenalty();
                combineToBoardOne = true;
            }
            printString += inversePenalty + ", ";
            if (loopCount % 40 == 0) {
                System.out.println(printString);
                printString = "";
            }
        }
        System.out.println(printString);
        System.out.println("--------------FINAL BOARD------------");
        System.out.println("Number of try: " + loopCount);
        if (combineToBoardOne) {
            otherBoard.printBoard();
        } else {
            board.printBoard();
        }

    }

}
