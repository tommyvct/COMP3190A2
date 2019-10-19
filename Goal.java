// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

// A Goal is a particular type of Thing. 
public class Goal extends Thing{
	
	// [ ] Change From Trap
	private String image = "Trap.png";

	public Goal(String name){
		super(name);
	}	
	// Copy constructor for cloning
	protected Goal(Goal other){
		super(other);
	}

	@Override
	public Goal deepCopy(){
		return new Goal(this);
	}

	// For the Art
	@Override
	public String getImageName(){
		return image;
	}
	
}