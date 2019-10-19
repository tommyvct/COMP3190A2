
// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

// the Tile class represents a single Tile on the GameBoard. 
// This is where the other objects in the game are stored. 
// Used for Cellular Automata
public class Tile{

	// [ ] private and accessors
	protected Agent currentAgent; // Player and AI
	protected Thing currentThing; // Items, Goals, and other inanimate objects

	// Phase 3
	// The next state to change to. 
	protected boolean nextState = false;
	// is it passable or not
	protected boolean isAlive = false;
	
	// Phase 2
	// the neighbors of this tile
	private TileList neighbors;

	// Provided
	// - Tile Art - 
	private String deadTileArt = "Dead.png";
	private String aliveTileArt = "Alive.png";

	// Phase 1
	// Basic Tile
	public Tile(){
		// basic tile constructor
		isAlive = false;
		neighbors = new TileList();
	}

	// State Updating 
	// ----------------------------------
	// --- Basic state accessors ---
	// returns true if this Tile is a alive or false if it is not
	public boolean isAlive(){
		return isAlive;
	}
	// Store the next state of this tile
	public void setNext(boolean next){
		nextState = next;
	}
	// Update this state to the next state
	public void updateToNext(){
		isAlive = nextState;
	}


	// Agent methods
	// ----------------------------------
	public void setAgent(Agent newAgent){
		currentAgent = newAgent;
	}
	public Agent getAgent(){
		return currentAgent;
	}
	public boolean hasAgent(){
		return (currentAgent != null);
	}

	// Thing methods
	// ----------------------------------
	public void setThing(Thing newThing){
		currentThing = newThing;
	}
	public Thing getThing(){
		return currentThing;
	}
	public boolean hasThing(){
		return (currentThing != null);
	}

	// Phase 2
	// Set the Tile to a random state with a given probability(0-1.0)
	// 0.0 should never set it to alive
	// 0.5 should set it to a alive 50% of the time
	// 1.0 should always set it to a alive
	public void setRandom(double randomProb){
		if( Math.random() < randomProb){
			isAlive = true;
		}else{
			isAlive = false;
		}
	}

	// ***** Neighbors ***** 
	// Access all the neighboring tiles.
	public TileList getNeighbors(){
		return neighbors;
	}
	// Add a neighboring tile to the TileList
	public void addNeighbor(Tile neighbor){
		this.neighbors.add(neighbor);
	}
	// number of neighbors (assigned)
	public int countNeighbors(){
		return neighbors.size();
	}

	// Number of neighbors that are alive
	public int countNeighborsAlive(){

		int count = 0;

		for(int i =0 ; i < neighbors.size(); i++){
			if( neighbors.get(i).isAlive()){
				count++;
			}
		}

		return count;
	}

	// Phase 1


	// -----  Accessor methods -----
	// Return the cell state, number of neighbors and number of neighbors still alive
	public String toString(){
		String st = "Tile: ";
		if( isAlive ){
			st += " is alive with ";
		}else{
			st += " is dead with ";
		}

		st += neighbors.size();

		st += " with " + countNeighborsAlive() + " alive";

		return st;
	}

	// Provided
	// Tiles states (for art)
	public String getImageName(){
		
		if(isAlive){
			return aliveTileArt;
		}else{
			return deadTileArt; // if using different costing tiles
		}
	}
	
	// _-------------------------------- Needed for simulation ---

	// Note the neighbors are not configured yet
	public Tile deepCopy(){
		// Cloning
		Tile newTile = new Tile();
		newTile.isAlive = this.isAlive;
		newTile.neighbors = new TileList();

		// Asign the Agent and the Tile to each other. 
		if( currentAgent != null){
			Agent clone = currentAgent.deepCopy();
			newTile.currentAgent = clone;
			clone.setTile(newTile);
		}

		return newTile;
	}


}