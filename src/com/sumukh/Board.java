package com.sumukh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Board {
	
	static Node initialState;
	static Node finalState;
	static int nodesGenerated = 0;
	static int nodesExpanded = 1;
	
	static PriorityQueue<Node> openList;
	static ArrayList<Node> closedList;
	
	//A Comparator for ordering the priority queue
	public static Comparator<Node> manhattanComparator = new Comparator<Node>() {

		@Override
		public int compare(Node o1, Node o2) {
			return (int) (o1.evaluationFunction() - o2.evaluationFunction());
		}
		
	};
	
	//Program starts here
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		openList = new PriorityQueue<>(manhattanComparator);
		closedList = new ArrayList<Node>();
		Scanner scanner = new Scanner(System.in);
		int[][] first = new int[3][3];
		int[][] last = new int[3][3];
		System.out.println("Enter the initial state: \n"); //Accept initial and goal state from user
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				first[i][j] = scanner.nextInt();
			}
		}
		
		System.out.println("Enter the goal state: \n");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				last[i][j] = scanner.nextInt();
			}
		}
		scanner.close();
		finalState = new Node(last);
		initialState = new Node(first, finalState, null);
		openList.add(initialState); //Add first state/node into openList
		checking(); //begin checking for further operations
		

	}
	
	//Checks which nodes to be selected/expanded
	public static void checking() {
		while (!openList.isEmpty()) {
			Node node = openList.remove();
			if (node.isGoalStateReached()) {
				System.out.println("\nSolution: \n");
				printStates(node);
				System.out.println("No. of nodes generated: " + nodesGenerated);
				System.out.println("No. of nodes expanded: " + nodesExpanded);
				return;
			} else {
				if (isNodeAlreadyVisited(node)) {
					continue;
				} else {
					nodesExpanded++;
					closedList.add(node);
					startOperation(node);
				}
			}
		}
	}
	
	//Checks if state is already present in the closedList
	private static boolean isNodeAlreadyVisited(Node node) {
		// TODO Auto-generated method stub
		boolean areNodesEqual = false;
		for (Node temp : closedList) {
			if (node.manhattanDistance == temp.manhattanDistance) {
				if (areArraysSame(temp.stateSpace, node.stateSpace)) {
					return true;
				}
			}
		}
		return areNodesEqual;
	}
	
	//Compares 2 2-D arrays and determines if they are same or not
	private static boolean areArraysSame(int[][] array1, int[][] array2) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (array1[i][j] != array2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	//prints the sequence of states that lead to the goal state
	private static void printStates(Node node) {
		// TODO Auto-generated method stub
		ArrayList<Node> finalList = new ArrayList<>();
		do {
			finalList.add(node);
			node = node.parentNode;
		} while (node.parentNode != null);
		node.printBoard();
		Collections.reverse(finalList);
		for (Node temp : finalList) {
			temp.printBoard();
		}
		System.out.println("Path cost: " + finalList.size());
	}

	//Given a node, this function determines the next possible states achievable from this node
	public static void startOperation(Node current) {
		if (current != null) {
			moveUp(current);
			moveDown(current);
			moveLeft(current);
			moveRight(current);
		}
	}
	
	//Moves the blank tile (0 in this case) up
	public static void moveUp(Node parent) {
		int[][] array1 = new int[3][3];
		
		int zeroPositionRow = parent.getZeroPositionRow();
		int zeroPositionCol = parent.getZeroPositionCol();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				array1[i][j] = parent.stateSpace[i][j];
			}
		}
		
		if (zeroPositionRow != 0) {
			int temp = array1[zeroPositionRow - 1][zeroPositionCol];
			array1[zeroPositionRow - 1][zeroPositionCol] = 0;
			array1[zeroPositionRow][zeroPositionCol] = temp;
			Node node = new Node(array1, finalState, parent);
			openList.add(node);
			nodesGenerated++;
			
		}
	}
	
	//Moves the blank tile (0 in this case) down
	public static void moveDown(Node parent) {
		int[][] array1 = new int[3][3];
		
		int zeroPositionRow = parent.getZeroPositionRow();
		int zeroPositionCol = parent.getZeroPositionCol();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				array1[i][j] = parent.stateSpace[i][j];
			}
		}
		if (zeroPositionRow != 2) {
			int temp = array1[zeroPositionRow + 1][zeroPositionCol];
			array1[zeroPositionRow + 1][zeroPositionCol] = 0;
			array1[zeroPositionRow][zeroPositionCol] = temp;
			Node node = new Node(array1, finalState, parent);
			openList.add(node);
			nodesGenerated++;
		}
	}
	
	//Moves the blank tile (0 in this case) left
	public static void moveLeft(Node parent) {
		int[][] array1 = new int[3][3];
		
		int zeroPositionRow = parent.getZeroPositionRow();
		int zeroPositionCol = parent.getZeroPositionCol();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				array1[i][j] = parent.stateSpace[i][j];
			}
		}
		if (zeroPositionCol != 0) {
			int temp = array1[zeroPositionRow][zeroPositionCol - 1];
			array1[zeroPositionRow][zeroPositionCol - 1] = 0;
			array1[zeroPositionRow][zeroPositionCol] = temp;
			Node node = new Node(array1, finalState, parent);
			openList.add(node);
			nodesGenerated++;
		}
	}
	
	//Moves the blank tile (0 in this case) right
	public static void moveRight(Node parent) {
		int[][] array1 = new int[3][3];
		
		int zeroPositionRow = parent.getZeroPositionRow();
		int zeroPositionCol = parent.getZeroPositionCol();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				array1[i][j] = parent.stateSpace[i][j];
			}
		}
		if (zeroPositionCol != 2) {
			int temp = array1[zeroPositionRow][zeroPositionCol + 1];
			array1[zeroPositionRow][zeroPositionCol + 1] = 0;
			array1[zeroPositionRow][zeroPositionCol] = temp;
			Node node = new Node(array1, finalState, parent);
			openList.add(node);
			nodesGenerated++;
		}
	}
}
