import java.util.ArrayList;

// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

// This class stores a single Map instance 
public class GameBoard{
	
	// Life Rules - Index of array is number of living neighbors. 
	// if a Tile is alive, apply life rule based on the number of neighbors == index 
	// (in this version, 2 or 3 neighbors alive, return true/turn the Tile to alive, otherwise return false. 
	private boolean[] defaultLifeRules = {false,false,true,true,false,false,false,false,false}; // number of neighbors to come alive on
	// This rule applies to a Tile that is not alive. 
	private boolean[] defaultdeathRules = {false,false,true,false,false,false,false,false,false}; // turn alive in these conditions

	// --- Important Local Variables. --- 
	// Object references of the Map. 
	private ArrayList<Agent> agents;
	private ArrayList<Thing> things;

	// Reference to the actual Tile array
	private Tile[][] map;

	// Debugging
	private boolean DEBUG_MODE = false;

	// These are required for the BoardWindow
	public int getHeight(){
		if( map == null){
			return 0;
		}
		return map.length;
	}
	public int getWidth(){
		if( map[0] == null){
			return 0;
		}
		return map[0].length;
	}

	// Phase 1
	// Create a new game board
	public GameBoard(){

		map = new Tile[1][1]; // needs to be 1x1 at least or clone will crash

		map[0][0] = new Tile();

		agents = new ArrayList<Agent>();
		things = new ArrayList<Thing>();

	}

	// A string representation of the board for testing. 
	public String toString(){
		String stMap = "";
		for(int i = 0 ; i < map.length; i++){
			stMap += "\n";
			for(int j =0; j < map[i].length; j++){

				// Note if a Tile has both a Thing and an Agent it will print both, which will be odd. 
				Tile currentTile = map[i][j];
				if( currentTile.hasAgent() ){
					Agent a = currentTile.getAgent();
					if( a instanceof Player){
						stMap += "P"; // Player Here
					}else if (a instanceof AIAgent){
						stMap += "A"; // AI Here
					}
				}
				if(currentTile.hasThing()){
					Thing t = currentTile.getThing();
					if( t instanceof Item){
						stMap += "I";
					}else if(t instanceof Goal){
						stMap += "G";
					}
				}

				if(!currentTile.hasThing() && !currentTile.hasAgent()){
					if(map[i][j].isAlive()){
						stMap += "X";
					}else{
						stMap += ".";
					}
				}
			}
		}
		// Print out Agents & Things
		for(int i =0 ; i < agents.size(); i++){
			stMap += "\n" + agents.toString();
		}
		for(int i =0 ; i < things.size(); i++){
			stMap += "\n" + things.toString();
		}

		return stMap;
	}

	// Adding and returning Agents
	//----------------------------------
	// add an agent to the given location (Adds it to the Tile))
	// Note this DOES NOT check whether the tile is already full
	// You should do this yourself BEFORE adding the agent.
	public void addAgent(Agent newAgent, int row, int col){
		agents.add(newAgent); // assign agent to list of agents in gameboard
		map[row][col].setAgent(newAgent); // assign agent to tile
		newAgent.setTile( map[row][col] );

		if(DEBUG_MODE) { System.out.println("Agent: " + newAgent.toString() + " added to board at " + row +" "+ col); }
	}
	// return the list of Agents
	public ArrayList<Agent> getAgents(){
		return agents;
	}

	// Access only the Player ( support for only one player)
	public Player getPlayer(){
		for(int i = 0; i < agents.size(); i++){
			if(agents.get(i) instanceof Player){
				return (Player)agents.get(i);
			}
		}
		
		System.out.println("NO player found!");
		return null;
	}

	// Access only the AI agents. 
	// There are probably more efficient ways to do this but the agents list is short. 
	public ArrayList<AIAgent> getAIAgents(){
		// Get all agents on board
		ArrayList<AIAgent> aiList = new ArrayList<AIAgent>();
		// iterate and return only AIAgents
		for(int i = 0; i < agents.size(); i++){
			if(agents.get(i) instanceof AIAgent){
				aiList.add((AIAgent) agents.get(i));
			}
		}
		return aiList;
	}

	// Adding and returning Things
	//----------------------------------
	// add an agent to the given location (Adds it to the Tile))
	// Note this DOES NOT check whether the tile is already full
	// You should do this yourself BEFORE adding the agent.
	public void addThing(Thing t, int row, int col){
		things.add(t); // assign agent to list of agents in gameboard
		map[row][col].setThing(t); // assign agent to tile
		if(DEBUG_MODE) { System.out.println("Thing: " + t.toString() + " added to board at " + row +" "+ col);}
	}

	// Return the list of things
	public ArrayList<Thing> getThings(){
		return things;
	}

	// --- Board Accessor ---
	// Needed by Board Window
	public Tile getTile(int row, int col){
		//System.out.println("Map set to :" + map.length + " " + map[0].length + " accessing "+ row + " "+ col);
		return map[row][col];
	}

	// --- Movement --- 
	// Move directly to a new tile, do not pass go, do not test for errors
	// [ ] Add error testing
	public void applyMove(Agent theAgent, Tile newTile){
		// update the Tiles
		Tile oldTile = theAgent.getTile();
		// If tile isn't null Or Full
		theAgent.setTile(newTile); // give the agent a reference to the Tile
		oldTile.setAgent(null); // remove agent from old tile location
		newTile.setAgent(theAgent); // move agent to new tile location

	}

	// --- Board Initialization ---
	// Note we need to keep a Board reference often so this 
	// Will act like a constructor but not create a new object. 
	// Create a new 2d array of type Tile
	// It should have int rows and int cols parameters
	public void initializeBoard(int rows, int cols){
		// Erase all agents
		agents = new ArrayList<Agent>();
		things = new ArrayList<Thing>();

		map = new Tile[rows][cols];
		for(int row = 0; row < map.length; row++){
			for(int col = 0; col < map[row].length; col++){
				map[row][col] = new Tile();
			}
		}

		// Sets up the neighbor references. 
		// Needs to be done whenever the map Tiles are edited
		setNeighbors();
	}

	// copies an existing array of Tiles and resets all agents. 
	// Used for loading maps. 
	public void initializeBoard(Tile[][] newMap){
		// Erase all agents
		agents = new ArrayList<Agent>();
		things = new ArrayList<Thing>();

		map = newMap;
		setNeighbors();
	}

	// Simply pass in values that were pre calculated. 
	public void initializeBoard(Tile[][] newMap, ArrayList<Agent> agents, ArrayList<Thing> things){
		map = newMap;
		this.agents = agents;
		this.things = things;
		setNeighbors();
	}

	// Resets the board by overwriting all the tiles. Should not change the overall size
	// Resets agents as well
	// Doesn't delete the tiles or references to neighbors but erases all Agents
	public void resetBoard(double randomProb){
		
		for(int row = 0; row < map.length; row++){
			for(int col = 0; col < map[row].length; col++){
				map[row][col].setRandom(randomProb);
			}
		}

		clearBoard();
	}

	// Resets the Agents and things, leaving the tiles untouched
	// Resets agents as well
	// Doesn't delete the tiles or references to neighbors but erases all Agents
	public void clearBoard(){

		// set everything to null
		for(int row = 0; row < map.length; row++){
			for(int col = 0; col < map[row].length; col++){
				map[row][col].setAgent(null); // clear each agent
				map[row][col].setThing(null);
			}
		}

		// Erase all agents
		agents = new ArrayList<Agent>();
		things = new ArrayList<Thing>();
	}

	// Bad Form allowing this to be directly accessed like this
	public Tile[][] getMap(){
		return map;
	}

	// Set each and every neighbor of every Tile on the board. 
	// The board should NOT wrap around the other side. This means some tiles
	// will have fewer neighbors then others
	private void setNeighbors(){
		for(int row = 0; row < map.length; row++){
			for(int col = 0; col < map[row].length; col++){
				setOneTileNeighbor(row, col);
			}
		}
	}

	// Set the neighbor of one tile
	// Should skip themself and also any illigal Tiles (off the board)
	private void setOneTileNeighbor(int currentRow, int currentCol){
		for(int i = -1; i <=1 ; i++){
			for(int j = -1; j <= 1; j++){
				// currently examined neighbors
				int neighborRow = currentRow + i;
				int neighborCol = currentCol + j;
				if(i == 0 && j == 0){
					// Skip self
				// on the board?
				}else if(neighborRow >= 0 && neighborRow < map.length 
					&& neighborCol >= 0 && neighborCol < map[neighborRow].length){

					// Add the current neighbor to the current Tile
					map[currentRow][currentCol].addNeighbor(map[neighborRow][neighborCol]);
				}
			}
		}
	}

	// Go to the next step of the CA
	// Needs to be done in two steps so the first tile updating doesn't throw off the neighbors. 
	public void nextStep(){
		calcNextStep();
		updateAllTiles();
	}

	// Overloaded version selects a custom set of rules. 
	public void nextStep(boolean[] lifeRule, boolean[] deathRule){
		calcNextStep(lifeRule, deathRule);
		updateAllTiles();
	}

	// Calculates each Tiles next state but doesn't apply it yet
	private void calcNextStep(){
		calcNextStep( defaultLifeRules, defaultdeathRules);
	}

	// Overloaded version selects a custom set of rules. 
	// Calculates each Tiles next state but doesn't apply it yet
	 // Can be called to apply a different CA rule to the board. 
    // Be careful you have the correct array size (9, so index (0-8) inclusive)
    // If you pass 
	private void calcNextStep(boolean[] lifeRule, boolean[] deathRule){
		for(int row = 0; row < map.length; row++){
			for(int col = 0; col < map[row].length; col++){
				calcOneTileNextStep(row, col, lifeRule, deathRule);	
			}
		}
	}

	// base version that calls the overloaded method
	private void calcOneTileNextStep(int row, int col){
		calcOneTileNextStep(row, col, defaultLifeRules, defaultdeathRules);
	}

	// Calculate the next step for a single tile (don't apply it yet!)
	// This version accepts a custom list of rules
	private void calcOneTileNextStep(int row, int col, boolean[] lifeRule, boolean[] deathRule){
		// neighbor count (total cells alive)
		int neighborAlive = map[row][col].countNeighborsAlive();
		
		//System.out.println("Neighbors: "+  map[row][col].countNeighbors() + " Neighbors alive" + neighborAlive);
		
		// apply the rules. 
		if(map[row][col].isAlive() && lifeRule != null ){
			map[row][col].setNext(lifeRule[neighborAlive]); // life rule
		}else if( deathRule != null){
			map[row][col].setNext(deathRule[neighborAlive]); // death rule
		}
	}

	// Updates all the Tiles to the next state that is queued
	private void updateAllTiles(){
		for(int row = 0; row < map.length; row++){
			for(int col = 0; col < map[row].length; col++){
				map[row][col].updateToNext();
			}
		}
	}

	// Creates a deep copy of the GameBoard. 
	public GameBoard deepCopy(){

		GameBoard newBoard = new GameBoard();

		// X [ ] will crash on size [n][]
		Tile[][] newMap = new Tile[map.length][map[0].length];

		// Duplicate all tiles
		for(int r =0; r < map.length; r++){
			for(int c = 0; c < map[r].length; c++){
				newMap[r][c] = map[r][c].deepCopy();
			}
		}

		// assign copy of map
		newBoard.map = newMap;

		// Deep copy of agents
		for(int i = 0 ; i < this.agents.size(); i++){
			newBoard.agents.add(0,this.agents.get(0).deepCopy());
		}

		// Deep copy of things
		for(int i = 0 ; i < this.things.size(); i++){
			newBoard.things.add(0, this.things.get(0).deepCopy());
		}

		// Reset neighbors
		newBoard.setNeighbors();
		return newBoard;

	}
}