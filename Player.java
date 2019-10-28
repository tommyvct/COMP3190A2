import java.util.ArrayList;

// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

// A Player type of Agent
// Player class is actually still controlled by AI
// Is trying to reach the goal, collect items and is being stopped by Agents
public class Player extends Agent{
	
	// Items collected by the Player
	public ArrayList<Item> inventory;

	// Player Constructor 
	// accepts a name
	public Player(String name){
		super(name);
		inventory = new ArrayList<Item>();
	}

	// Player Copy Constructor
	public Player(Player other){
		// Call the parent constructor
		super(other); // copy constrcutor

		// --- Deep Copy of inventory ---
		this.inventory = new ArrayList<Item>();
		// Deep Copy of inventory
		for( int i = 0 ; i < other.inventory.size(); i++ ){
			this.inventory.add( other.inventory.get(i).deepCopy() );
		}
	}

	// --- Inventory ---
	public void addItem(Item theItem){
		inventory.add(theItem);
	}
	public ArrayList<Item> getItems(){
		return inventory;
	}

	// Make a deep clone of this Player. 
	@Override
	public Player deepCopy(){
		return new Player(this);
	}
}