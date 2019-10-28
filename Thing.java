// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

// Top level class for non Agent things in the game
public class Thing{
	
	private String name;

	private Tile currentTile; // Current tile the Thing is on

	private final String image = "Item.png"; // [ ]  "Thing.png";

	public Thing( String name){
		this.name = name;
	}

	// Copy constructor specifically for cloning
	// Full discussion here: 
	// Question of SO Here https://stackoverflow.com/questions/2326758/how-to-properly-override-clone-method
	// https://www.artima.com/intv/bloch13.html
	// Gets around the problem of returning a cloned object in the subclass
	// Implementing Clonable if problematic as well since it should be a shallow copy
	protected Thing (Thing toCopy){
		this(toCopy.name);
		this.currentTile = toCopy.currentTile;
	}

	// --- Assignment to Tile ---
	// Assign the given Thing its current Tile
	public void setTile(Tile current){
		currentTile = current;
	}
	// Return the current tile this Thing is on (Should always be on a tile)
	public Tile getTile(){
		return currentTile;
	}

	public Thing deepCopy(){
		return new Thing(this);
	}

	// For the Art
	public String getImageName(){
		return image;
	}
}