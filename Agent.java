// c Dylan Fries 2019
// Please do not distribute without permission (if you want permission just ask!)
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

// Agents are Human or AI that can move around the board
public class Agent{
	// Variables 
	private String name;
	private Tile currentTile; // Current tile the Agent is on

	// Art Settings
	private final String image = "Player.png";
	
	// --- Constructors ---
	public Agent(){
		name = "Default Agent";
	}

	public Agent(String name){
		this.name = name;
	}

	// --- Copying ---
	// Copy constructor for cloning purposes. 
	protected Agent(Agent other){
		Agent a = new Agent(other.name);
		a.currentTile = other.currentTile; // reference needs to be updated somewhere else
	}

	//Create a deep copy of the Agent
	public Agent deepCopy(){
		return new Agent();
	}

	// --- Assignment to Tile ---
	// Assign the given agent its current Tile
	public void setTile(Tile current){
		currentTile = current;
	}
	// Return the current tile this agent is on (Should always be on a tile)
	public Tile getTile(){
		return currentTile;
	}

	// --- Movement ---
	// is move valid
	// simple version just checks that the tile agent slot is null
	public boolean isMoveValid(Tile targetTile){
		return !targetTile.hasAgent(); 	
	}

	// --- Art ---
	public String getImageName(){
		return image;
	}
}