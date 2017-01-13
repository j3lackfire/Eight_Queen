import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Le Pham Minh Duc on 1/11/2017.
 */
public class Board {
    private int boardSize = 8;
    private int totalPenalty;
    private ArrayList<Queen> queensList = new ArrayList<Queen>();

    //use this method for the combine board method
    //create an empty board
    public Board() { }


    public Board(int _boardSize) {
        boardSize = _boardSize;
        //create a pool for y position of the queen
        ArrayList<Integer> yPosPool = new ArrayList<>();
        for (int i = 0; i < boardSize; i ++) {
            yPosPool.add(i);
        }
        //generate the board so that no queen share the same row or column
        Random random = new Random();
        for (int i = 0; i < boardSize; i ++) {
            //get a random position from the pool. Remove it afterwards to make sure no duplicate
            int index = random.nextInt(yPosPool.size());
            queensList.add(new Queen(i, yPosPool.get(index)));
            yPosPool.remove(index);
        }

        //Finish step 1, which is to generate the board.
        calculatePenalty();
    }

    //-------------------------
    //Board method, for altering the value of the board
    //-------------------------
    public void setBoardSize(int size) { boardSize = size; }

    public void addQueen(Queen queen) {
        queensList.add(queen);
    }

    public int getQueenListSize() {return queensList.size();}

    //check if the board already have a queen placed in a similar X or Y position
    public boolean isConflictWith(Queen queen) {
        if (queensList.size() == 0) {
            return false;
        }

        for (int i = 0; i < queensList.size(); i ++) {
            if (queen.getXPos() == queensList.get(i).getXPos() || queen.getYPos() == queensList.get(i).getYPos()) {
                return true;
            }
        }
        return false;
    }

    //------------------------
    //Evolution Algorithm method.
    //------------------------
    //mutation method.
    //randomly swap two queens in any position.
    public void swapQueens() {
        Random rand = new Random();
        int queenOneIndex = rand.nextInt(boardSize);
        int queenTwoIndex = rand.nextInt(boardSize);
        //just to make sure
        while (queenOneIndex == queenTwoIndex) {
            queenTwoIndex = rand.nextInt(boardSize);
        }
        swapQueens(queenOneIndex, queenTwoIndex);
    }

    public void swapQueens(Queen queenOne, Queen queenTwo) {
        swapQueens(queenOne.getXPos(), queenTwo.getXPos());
    }

    public void swapQueens(int queenOneIndex, int queenTwoIndex) {
        Queen queenOne = queensList.get(queenOneIndex);
        Queen queenTwo = queensList.get(queenTwoIndex);

        int tempY = queenOne.getYPos();
        queenOne.resetPosition(queenOne.getXPos(), queenTwo.getYPos());
        queenTwo.resetPosition(queenTwo.getXPos(), tempY);
    }

    //selection method - get the best queens, queen with lowest penalty
    public ArrayList<Queen> getBestQueen(int selectionSize) {
        if (selectionSize > boardSize) {
            throw new InvalidParameterException("Number of best queen Can not be bigger than the board size !!!!");
        }
        ArrayList<Queen> returnList = new ArrayList<>();
        //having a temp list so that we keep the main list untouched
        ArrayList<Queen> tempList = getSortedQueenList();

        for (int i = 0; i < selectionSize; i ++) {
            returnList.add(tempList.get(i));
        }
        return returnList;
    }

    //Cross over method. Combine two boards into one.
    public Board combineBoard(Board otherBoard) {
        Random random = new Random();
        //The number of parents picked. In this case, 5 queens with the lowest penalty of each board is selected
        int numberOfParent = GlobalVariables.getNumberOfParentPicked();
        ArrayList<Queen> thisBestQueens = getBestQueen(numberOfParent);
        ArrayList<Queen> otherBestQueens = otherBoard.getBestQueen(numberOfParent);

        int thisIndex = 0;
        int otherIndex = 0;

        Board newBoard = new Board();
        newBoard.setBoardSize(GlobalVariables.boardSize);
        //if there are still things to add to the list.
        while ((thisIndex < numberOfParent || otherIndex < numberOfParent) && //if there are still something in the best queen lists
                (newBoard.getQueenListSize() <= numberOfParent)) { //and the board isn't already full
            //first, check if the queen of the current index is already exist (X or Y already exist)
            //If it is already exist, skip that queen.
            boolean isSkipQueen = false;
            if (thisIndex < numberOfParent) { //avoid the array out of range exception
                if (newBoard.isConflictWith(thisBestQueens.get(thisIndex))) {
                    thisIndex ++;
                    isSkipQueen = true;
                }
            }
            if (otherIndex < numberOfParent) {
                if (newBoard.isConflictWith(otherBestQueens.get(otherIndex))) {
                    otherIndex ++;
                    isSkipQueen = true;
                }
            }
            //skip this loop so it's safer to do everything, I think :/
            if (isSkipQueen) {
                continue;
            }
            //second step, let's add the best queen to the board.
            //If one list is full then we just have to add the other list.
            if (thisIndex >= numberOfParent) {
                newBoard.addQueen(otherBestQueens.get(otherIndex));
                otherIndex ++;
                continue;
            }
            if (otherIndex >= numberOfParent) {
                newBoard.addQueen(thisBestQueens.get(thisIndex));
                thisIndex ++;
                continue;
            }
            //both list have queen here. Let see which has the better queen
            if (thisBestQueens.get(thisIndex).getPenalty() > otherBestQueens.get(otherIndex).getPenalty()) {
                newBoard.addQueen(otherBestQueens.get(otherIndex));
                otherIndex ++;
                continue;
            } else {
                newBoard.addQueen(thisBestQueens.get(thisIndex));
                thisIndex ++;
                continue;
            }
        }
        //in the case of all value from the two parent list does not fit into the new board because of conflict,
        //we have to fill the rest of the board by placing random queen.
        if (newBoard.getQueenListSize() != GlobalVariables.boardSize) {
            //First, we have to find the available X and Y position
            ArrayList<Integer> availableXPos = new ArrayList<>();
            ArrayList<Integer> availableYPos = new ArrayList<>();
            for (int i = 0; i < boardSize; i ++) {
                availableXPos.add(i);
                availableYPos.add(i);
            }
            //the remove int from array list is need because for some reason, in JAVA, they do not
            //have the different between remove(Object) and removeAt(index)
            //all share the same function remove, which is fine in most case, unless you have a list of Integer
            for (int i = 0; i < newBoard.getQueenListSize(); i ++) {
                availableXPos = removeIntFromArrayList(availableXPos, newBoard.queensList.get(i).getXPos());
                availableYPos = removeIntFromArrayList(availableYPos, newBoard.queensList.get(i).getYPos());
            }

            for (int i = 0; i < availableXPos.size(); i ++) {
                int index = random.nextInt(availableYPos.size());
                newBoard.addQueen(new Queen(availableXPos.get(i), availableYPos.get(index)));
                availableYPos.remove(index);
            }
        }

        //sort the list by x so I can see it easier.
        Board returnBoard = new Board();
        returnBoard.setBoardSize(GlobalVariables.boardSize);
        for (int i = 0; i < GlobalVariables.boardSize; i ++) {
            for (int j = 0; j < newBoard.getQueenListSize(); j ++) {
                if (i == newBoard.queensList.get(j).getXPos()) {
                    returnBoard.addQueen(newBoard.queensList.get(j));
                }
            }
        }
        returnBoard.calculatePenalty();
        //mutation chance = 80%
        if (random.nextInt(100) < GlobalVariables.mutationChance) {
            returnBoard.swapQueens(); //randomly swap two queens.
        }
        return returnBoard;
    }

    //use this method to get the sorted list so we don't alter the original list.
    public ArrayList<Queen> getSortedQueenList() {
        ArrayList<Queen> returnList = new ArrayList<>();
        for (int i = 0; i < queensList.size(); i ++) {
            returnList.add(queensList.get(i));
        }
        Collections.sort(returnList);
        return  returnList;
    }

    public int calculatePenalty() {
        //calculate the penalty of each queen.
        totalPenalty = 0;
        for (int i = 0; i < boardSize; i ++) {
            totalPenalty += queensList.get(i).getPenalty(queensList);
        }
        return totalPenalty;
    }

    public int getInversePenalty() {
        return GlobalVariables.getMaxInversePenaltyValue() - totalPenalty;
    }

    public void printBoard() {
        String printString = "Board - penalty: " + totalPenalty + " - Inverse penalty: " + getInversePenalty() + "\n";
        for (int i = 0; i < queensList.size(); i ++) {
            printString += queensList.get(i).getYPos() + " ,";
            if (i % 40 == 0 && i > 0) {
                printString += "\n";
            }
        }
        System.out.println(printString);
    }

    //remove an interger from an array list. If I parse it into the list, it will remove by index.
    //Since java does not have an distinction between REMOVE AT and REMOVE Object, if I pass an integer in, bug will happen
    private ArrayList<Integer> removeIntFromArrayList(ArrayList<Integer> array, int value) {
        ArrayList<Integer> returnList = new ArrayList<>();
        for (int i = 0; i < array.size(); i ++) {
            if (array.get(i) != value) {
                returnList.add(array.get(i));
            }
        }
        return returnList;
    }
}
