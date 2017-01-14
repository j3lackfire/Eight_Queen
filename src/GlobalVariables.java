/**
 * Created by Le Pham Minh Duc on 1/11/2017.
 */
public class GlobalVariables {

    public static int boardSize = 8;
    public static int mutationChance = 15; //15% chance there will be a mutation in every evolution.
    public static int maxFinessEvaluation = 10000;
    public static float percentOfParentPicked = 0.625f; //5 out of 8

    //get number of best parent in a case. For example, have 8 queens, and we pick 5 best one.
    public static int getNumberOfParentPicked() {
        return (int)(percentOfParentPicked * boardSize);
    }

    //each queen, at maximum, can see (board size - 1) queen.
    //So inverse penalty at max will be number of queen * number of maximum value the queen can see
    public static  int getMaxInversePenaltyValue() {
        return boardSize * (boardSize - 1);
    }
}
