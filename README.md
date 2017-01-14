# Eight_Queen
A solution to the eight queen problem using evolutional algorithm

# STEPS

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
	

BENCHMARK

