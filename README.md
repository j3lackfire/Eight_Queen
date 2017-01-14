# Eight_Queen
A solution to the eight queen problem using evolutional algorithm

## STEPS

1. Generate two boards with randomly placed queen such that each queen have different X and Y coordinate.

2. Calculate the penalty score of each board. The penalty score is the total penalty of every queen in the board. The penalty of each queen is the number of queen she can check.

3. Find the top 5 queen with the least penalty for each board.

4. Combine two board using cross-over method. 
	- The main idea is to create a new board by combining the top 5 best queen from board one and other 5 from board 2.
	- To do this, we loop throught the list best queen list of each board and add the queen from that list into this new board. The queen is sorted by penalty so that the queen with the least penalty will be add first and so on.
	- For every queen placed, we check if that queen is conflict with the board or not. A conflict is define as if the X or Y cordinate of the queen is already been placed on the board. If the queen is conflict, ignore that queen, if not, place the queen with that position in the board. 
	- The combination stop when the board is full or the two list of queen is empty. If the two lists is empty and the board is still not full, we place queen at random avaiable place in the board.

5. After combination, the board has 80% chance of mutation, in which two random queens on the board switch Y position for each other.
	
6. Keep combining the board until the board created has zero penalty or we run at maximum fitness which is 10,000 in this case. The order or looping is:
	- Board 1
	- Board 2
	-> Combine board 1 and board 2 into Board 1 (Board 1 = combine (board1, board2))
	-> Combine the new board 1 and board 2 into board 2
	-> combine again into board 1 and board 2 until the condition is reached.
	

Example in picture:
![Example](https://github.com/j3lackfire/Eight_Queen/blob/master/8-Queens.png)

## BENCHMARK

Eachs solution is run 10,000 times to find the average loop count it need to find the solution for the problem.

Generally, the evolution method run at 3x to 5x faster than the RANDOM method. However, this is only for the 8-queen problem, when the number of queen go higher, for example, 100- queens or 1000 queens, both solution will not yield the correct result in a resonable time frame. For the 1000s queen or more problem, we need to create a specific solution.

As an observation, for my implementation of the evolution algorithm, the best parameter would be 50% of parent picked and 0% percent mutation. However, for general case, 15% mutation chance would yield the best result. I think that is because, in most case, having a mutation chance too high will mess too much with the solution, thus slow everything down. However, not having a mutation will cause the board stuck at one near solution state and never get out of state which make the board loop forever.

Below is the detail benchmark.

------------

Random method: a board is created randomly until the solution is found:
Average loop count: 437

------------

+ Evolution method: mutation chance = 15%, percent of parent picked: 87.5% (7/8 queens).
* Average loop count: 3027 -> Worst solution.

+ Evolution method: mutation chance = 80%, percent of parent picked: 62.5% (5/8 queens).
* Average loop count: 194

+ Evolution method: mutation chance = 20%, percent of parent picked: 62.5% (5/8 queens).
* Average loop count: 114

+ Evolution method: mutation chance = 0%, percent of parent picked: 62.5% (5/8 queens).
* Average loop count: infinity -> __can not find the correct solution.__

+ Evolution method: mutation chance = 70%, percent of parent picked: 50% (4/8 queens).
* Average loop count: 169

+ Evolution method: mutation chance = 15%, percent of parent picked: 50% (4/8 queens).
* Average loop count: 99

+ Evolution method: mutation chance = 0%, percent of parent picked: 50% (4/8 queens).
* Average loop count: 87-> __best solution__

+ Evolution method: mutation chance = 15%, percent of parent picked: 37.5% (3/8 queens).
* Average loop count: 166
