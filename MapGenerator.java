// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

import java.util.ArrayList;

// Generates a new GameBoard
public class MapGenerator{

	// Current board the MapGenerator is working on
	private GameBoard currentBoard;

	// Basically just a class for holding a tile on the board
	// Not really needed at the moment exactly. 
	private class Node{

		private int row;
		private int col;

		public Node(int row, int col){
			this.row = row;
			this.col = col;
		}
	}

	// Create a MapGenerator focused on a specific Board
	public MapGenerator(GameBoard board){
		currentBoard = board;
	}

	// accepts and resets a current map
	public void createMap( int rows, int cols, double density){
		
		Tile[][] map = new Tile[rows][cols];
		// generate map
		for(int r = 0; r < rows; r++){
			for(int c = 0; c < cols; c++){
				// create new random tile
				Tile tempTile = new Tile();
				tempTile.setRandom(density);
				map[r][c] = tempTile;
			}
		}

		// Determine if board is valid
			// Should be within 5% +/- the given density
			// All tiles with 0 or 1 neighbors removed
			// should have some CA iterations on it to smooth the walls
				// (this will help with the next step)
			// etc

		// [ ] Your work goes here....

		currentBoard.initializeBoard(map);

	}

	// Clears all old agents as well
	// Initializes the board with the Player, Goal Itenms and AIAgents
	// This needs completing
	public void addAgentsToMap(boolean addPlayer, int items, int aiAgents){

		// --- Debugging -- Remove or internalize later (handle properly and log)
		// Bail out if map is missing
		if(currentBoard == null){
			System.out.println("ERROR Board is not initlaized Agents can't be added to map, map is invalid.");
			return;
		}

		// --- Debugging -- Remove or internalize later (handle properly and log)
		// Bail out if map is missing
		if(currentBoard.getMap() == null || currentBoard.getMap().length < 1){
			System.out.println("ERROR Agents can't be added to map, map is invalid.");
			return;
		}

		currentBoard.clearBoard(); // clear all Agents and Things

		// Adding a new player
		if( addPlayer){
	
			// Add random location
			Node newNode = generateNode() ; // get a random valid location

			// [ ] Need a better method of evalutating the board states
			// Created and add player
			Player newPlayer = new Player("New Player");
			currentBoard.addAgent( newPlayer,  newNode.row , newNode.col );

			// [ ] Add a Goal - Add Conditions to the Goal and the Item
			// The goal should be close to the farthest possible point (optimal not path)
			// Add random location
			newNode = generateNode() ; // get a random valid location
			// Created and add player
			Goal newGoal = new Goal("Level Goal");
			currentBoard.addThing( newGoal,  newNode.row , newNode.col );

		}

		// [ ] Generates some random Items (for demo purposes, you should improve this)
		if( items > 0){

			// Add random nodes. 
			for(int i = 0; i < items; i++){

				Node n = generateNode();

				int r = n.row;
				int c = n.col;
				Item t = new Item("New Item");

				currentBoard.addThing(t, r , c );
			}
		} 

		// For the Nodes:

		// (Max Distance) = The length of the longest side of the board, so 10x20, max would be 20

		// Mark a Player location and a Goal location
		// These should have a shortest path that is equal to or longer then the (Max Distance)
		// This means you will need some obstacles in your map (cannot be wide open)

		// Add Items up to the items param count, each should be at least 25% of the (Max Distance)
		// from both the Player and the Goal and as far as reasonable from other Items. 

		// Add an AIagent. It should be close to halfway between the Player and the Goal and between 
		// 10%-25% of the max distance from at least one Item (Optional)

		// The AIAgent, Player, Goal and Item must all be accessible to each other (no passing through walls)

		// Adjust as necessary with adjustments/Respawn of goals until you have a map that fulfills 
		// these requirements
	}

	// This is a simple generator that creates a Node. It basically (for now)
	// is just an object holding a board position. It will loop until the node 
	// is not a. in a wall or b. on a Tile with an Agent or a Thing already
	// this is one way to handle it but not hte only way and certainly not the best way
	private Node generateNode(){
		int boardHeight = currentBoard.getHeight();
		int boardWidth = currentBoard.getWidth();
		boolean validLocation = false;

		Node location = null;

		// Loop until we have a valid location
		while(!validLocation){

			int randomRow = (int)(Math.random() * boardHeight); 
			int randomCol = (int)(Math.random() * boardWidth);

			Tile selected = currentBoard.getTile(randomRow, randomCol);
			// IF the tile is valid (not alive / a wall) AND is empty
			if( !selected.isAlive() && !selected.hasThing() && !selected.hasAgent()){
				location = new Node(randomRow, randomCol);
				validLocation = true;
			}
		}

		return location;
	}
	
}