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
        String printString = "The inverse penalty of each fitness loop is :\n";
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

        //A simple bench mark to see which one is better.
        System.out.println("--------------BENCH MARK------------");
        //Get the average loop count of the random method
        evaluateRandomMethod(2000);
        //Get the average loop count of the algorithm
        System.out.println("----------------");
        evaluateAlgorithmMethod(2000);
    }


    public static  void evaluateRandomMethod(int numberOfTry) {
        float startTime = System.currentTimeMillis();
        long totalLoopCount = 0;
        for (int i = 0; i < numberOfTry; i ++) {
            totalLoopCount  += benchmarkRandomMethod();
        }
        float endTime = System.currentTimeMillis();
        System.out.println("Average loop count of RANDOM method: " + totalLoopCount / numberOfTry);
        System.out.println("Execution time: " + (endTime - startTime));
    }

    public static  void evaluateAlgorithmMethod(int numberOfTry) {
        float startTime = System.currentTimeMillis();
        long totalLoopCount = 0;
        for (int i = 0; i < numberOfTry; i ++) {
            totalLoopCount  += benchmarkEvolutionAlgorithm();
        }
        float endTime = System.currentTimeMillis();
        System.out.println("Average loop count of ALGORITHM method: " + totalLoopCount / numberOfTry);
        System.out.println("Execution time: " + (endTime - startTime));
    }


    //Run one valuation of the problem and return the number of loop needed to find the solution
    //RANDOM method, The board is created randomly until we find the solution.
    public static  int benchmarkRandomMethod() {
        Board board = new Board(GlobalVariables.boardSize);
        int inversePenalty = board.getInversePenalty();
        int loopCount = 0;
        while (inversePenalty != GlobalVariables.getMaxInversePenaltyValue()) {
            board = new Board(GlobalVariables.boardSize);
            inversePenalty = board.getInversePenalty();
            loopCount ++;
        }
        return loopCount;
    }

    //Evolution Algorithm. The board is created and the solution is calculated using evolution method
    public static int benchmarkEvolutionAlgorithm() {
        Board board = new Board(GlobalVariables.boardSize);
        Board otherBoard = new Board(GlobalVariables.boardSize);
        boolean combineToBoardOne = true;
        int inversePenalty = 0;
        int loopCount = 0;
        String printString = "";
        while (inversePenalty != GlobalVariables.getMaxInversePenaltyValue()) {
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
        }
        return loopCount;
    }

}
