package com.sumukh;

public class Node {
	
	public int[][] goalState; //holds the goal state that has to be achieved
	
	Node parentNode; //points to the parent node of this node
	
	int[][] stateSpace; //holds the 2D array of the particular state space
	int manhattanDistance;
	int level; //g(n) for this algorithm
	int zeroPositionRow; //row position of the blank tile
	int zeroPositionCol; //column position of the blank tile
	
	//a constructor
	public Node(int[][] initialState, Node goalState, Node parent) {
		// TODO Auto-generated constructor stub
		this.stateSpace = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (initialState[i][j] == 0) {
					this.zeroPositionRow = i;
					this.zeroPositionCol = j;
				}
				stateSpace[i][j] = initialState[i][j];
			}
		}
		this.goalState = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.goalState[i][j] = goalState.stateSpace[i][j];
			}
		}
		if (parent != null) {
			setParent(parent);
			this.level = parent.level + 1;
		} else {
			this.parentNode = null;
			this.level = 0;
		}
		this.manhattanDistance = calculateHeuristicManhattan();
	}
	
	//checks if the goal state has been reached 
	public boolean isGoalStateReached() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (stateSpace[i][j] != goalState[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	//another constructor
	public Node(int[][] initialState) {
		this.stateSpace = new int[initialState.length][initialState.length];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				stateSpace[i][j] = initialState[i][j];
			}
		}
	}
	
	//prints the state of the board
	public void printBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(stateSpace[i][j] + " ");
			}
			System.out.print("\n");
		}
		System.out.println("\n");
	}
	
	//calculates the Manhattan distance - h(n)
	public int calculateHeuristicManhattan() {
		this.manhattanDistance = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int num = stateSpace[i][j];
				if (num != 0) {
					manhattanDistance += searchForNumber(num, i, j);
				}
			}
		}
		return manhattanDistance;
	}
	
	
	
	//the evaluation function f(n) = g(n) + h(n)
	public int evaluationFunction() {
		return (level + manhattanDistance); 
	}
	
	//calculates the distance of each tile from its goal
	private int searchForNumber(int num, int row, int col) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (goalState[i][j] == num) {
					return Math.abs(i - row) + Math.abs(j - col);
				}
			}
		}
		return 0;
	}
	
	//returns Manhattan value for this state
	public int getManhattan() {
		return this.manhattanDistance;
	}
	
	//sets the parent of this node
	public void setParent(Node parent) {
        //parent.addChild(this);
        this.parentNode = parent;
    }
    
	//returns the row number of the blank tile (0 here)
    public int getZeroPositionRow() {
    	return zeroPositionRow;
    }
    
    //returns the column number of the blank tile (0 here)
    public int getZeroPositionCol() {
    	return zeroPositionCol;
    }

}
