// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

// An item is collected by the Player from the Map
public class Item extends Thing{

	public Item(String name){
		super( name);
	}

	// Copy Constructor
	protected Item(Item other){
		super(other); // Call the Thing copy constructor
	}
	
	@Override
	public Item deepCopy(){
		return new Item(this); // Call and return the copy constructor
	}

}