import java.util.ArrayList;

/**
 * Created by Le Pham Minh Duc on 1/11/2017.
 */
public class Queen implements Comparable<Queen> {
    private int xPos; //the x position
    private int yPos; //the y position
    private int penalty; //the number of queens this queen can check.

    public int getXPos () {return xPos;}
    public int getYPos () {return yPos;}
    public int getPenalty() {return penalty;}

    public Queen (int x, int y) {
        xPos = x;
        yPos = y;
    }

    public void resetPosition(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public int getPenalty(ArrayList<Queen> queenList) {
        penalty = 0;
        for (int i = 0; i < queenList.size(); i ++) {
            if (queenList.get(i) == this) {
                continue;
            }
            if (isChecking(queenList.get(i))) {
                penalty ++;
            }
        }
        return penalty;
    }

    public boolean isChecking(Queen another) {
        //two queens lie in the same line, x or y
        if (xPos == another.getXPos()
            || yPos == another.getYPos()
            || xPos - yPos == another.getXPos() -  another.getYPos() //if two queens lie in the same diagonal line.
            || xPos + yPos == another.getXPos() + another.getYPos()) { //diagonal but the other way
            return true;
        }
        return false;
    }

    public String toString() {
        return "Queen: " + xPos + " , " + yPos + " - penalty: " + penalty + "\n";
    }

    //doing this so I don't have to write a sorting function
    @Override
    public int compareTo(Queen otherQueen) {
        return getPenalty() - otherQueen.getPenalty();
    }
}
