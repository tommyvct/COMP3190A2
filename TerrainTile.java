// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

// A subclass of Tile 
/*
- [ ] new art - 5 p
	- [ ] Requires PSD edits
- [ ] more levels
= [ ] discrete states
 	[ ] height
	[ ] move cost
	[ ] value
	= [ ] Things
		- [ ] Water Level - int
		- [ ] Vegetation
*/

public class TerrainTile extends Tile{

	// max settings - [0-max] inclusive
	protected int maxHeight = 5; // 0-5 to start
	protected int maxWater = 5; // 0-5 to start
	protected int maxVeg = 5; // 0-5 to start, 3+ is trees 0 is seeds, 1-2 is grass
	protected int maxSoil = 5; // soil quality 0-5, affects vegetation, improves from water and rock to 1, then veg can grow and improves slowly over time based on the vegetation level
	protected int maxLava = 3;

	// current settings
	protected int currentHeight = 1; // 0-5 to start
	protected int currentWater = 1; // 0-5 to start
	protected int currentVeg = 1; // 0-5 to start, 3+ is trees 0 is seeds, 1-2 is grass
	protected int currentSoil = 1; // soil quality 0-5, affects vegetation, improves from water and rock to 1, then veg can grow and improves slowly over time based on the vegetation level
	protected int currentLava = 0;

	
	// **** PRESETS ****
	// height, water, veg, soil, lava - flat settings
	public int[] preset_primordial = {4,3,0,0,1}; // sets the values to this
	// height, water, veg, soil, lava - probabilistic settings
	public double[] preset_probability_primordial = { 0.7, 0.5, 0.0, 0.0, 0.2 }; // accepts probabilities

	// height, water, veg, soil, lava - flat settings
	public int[] preset_jungle = {4,3,0,0,1}; // sets the values to this
	// height, water, veg, soil, lava - probabilistic settings
	public double[] preset_probability_jungle = { 0.7, 0.5, 0.0, 0.0, 0.2 }; // accepts probabilities


	// **** DEBUGGING ****
	public boolean debug_mode = true; // print additional debugging messages 

	public TerrainTile(){
		super(); // call the parent class Constructor
	}

	// [ ] Complete the Overloaded Constuctor
	// parameterized with initial settings.
	// height, water, veg, soil, lava - flat settings
	// // how should we handle overflow? - Choose 1 
		// print a warning message if the value is < 0 or > max for each
		// - a) Wrap - Good for testing as you can't break it and can increase randomly
			// simply mod by the number of options. - may be confusing if the numbers are offset
				// will wrap around
		// - b) Negative numbers? - Use negative values as absolute - adds some symmetry if using random noise distributions - create a bell curve centered on 0
		// - c) Floor to 0 - robust and don't have to worry about using ints as array index 
			// ? - what is max? 
	public TerrainTile( int height, int water, int vegetation, int soil, int lava){

		// print a warning message if the value is < 0 or > max for each

		super(); // refers to the Tile constructor that accepts no parameters

		// Initialize the tile to the appropriate settings
		currentHeight = height; // 0-5 to start
		currentWater = water; // 0-5 to start
		currentVeg = vegetation; // 0-5 to start, 3+ is trees 0 is seeds, 1-2 is grass
		currentSoil = 1; // soil quality 0-5, affects vegetation, improves from water and rock to 1, then veg can grow and improves slowly over time based on the vegetation level
		currentLava = 0;
	}

	// -------------------------------
	// Phase 3
	// Store the next state of this tile
	@Override // Letting the compiler know this is overriding its parent method
	public void setNext(boolean next){
		// parent
		// nextState = next;
		// this should update the next state
		// [ ] 
	}
	// Update this state to the next state
	@Override // Letting the compiler know this is overriding its parent method
	public void updateToNext(){
		// parent version
		// isAlive = nextState;
		// update the next step
		// [ ] isAlive should be set to true if currentVeg > 0
	}

	// Phase 2
	// Set the Tile to a random state with a given probability(0-1.0)
	// 0.0 should never set it to alive
	// 0.5 should set it to a alive 50% of the time
	// 1.0 should always set it to a alive
	@Override // Letting the compiler know this is overriding its parent method
	// so far just minimal water prob
	public void setRandom(double randomProb){

		super.setRandom(randomProb); // call parent method, sets alive or dead
		
		if(isAlive()){
			currentVeg = 1;
		}

		// [ ] is alive is true if veg is > 0
	}

// - not sure about this, much more complicated
// - how does the probability distribution work? Maybe exponential? Gaussian? 

	// Overloaded method
	// Overloading the setRandom with only randomProb 
	
	// should be normalized based on probability
	// for example, a prob of .5 would mean a 50% chance of the tile having a given attribute
		// if this is true, a second roll would then select from the levels with a linear spread (ie a dice roll against the index) [0,max]
	// height, water, veg, soil, lava - probability settings [0.0, 1.0] inclusive

	// Extra stuff
	// [f] square the probability and normalize it between 0 and max of each var
	public void setRandom(double heightProb, double waterProb, double vegetationProb, double soilProbability, double lava){
		// [ ] How do you call setRandom with random prob first
		// [ ] veg prob is only on alive tiles
	}

	// -----  Accessor methods -----
	// Return the cell state, number of neighbors and number of neighbors still alive
	// override - same name same params, different class
	// Can disable some of the output with a debug flag if it is being annoying
		// note: debug flag var: debug_mode
	public String toString(){
		
		String st = "Tile: "; // [ ] Type ? What does this mean? 

		// [ ] Optional debugging output - with flag var "debug_mode"
		// [ ] Optional in that you can disable it, not optional as in you don't have to implement it
		if( isAlive ){
			st += " is alive with "; // [f] Add Species Name of Vegetation 
										// Make ArrayList
										// Requires Vegetation
		}else{
			st += " is dead with "; // [f][ ]List of previous types	
										// Requires Vegetation
		}

		// how many tiles are still alive
		st += countNeighbors(); // max neighbors
		st += " with " + countNeighborsAlive() + " alive";

		// [ ] Additional stats on vegetation, height, water etc. 
		// [ ] Everytime you add a new feature, add a debug comment
		// * Never Ending Task

		return st;
	}

	// Provided
	// [ ] overload the art
	// Tiles states (for art)
	// override - same name same params, different class
	public String getImageName(){
		String newName = super.getImageName();
		// [ ] Replace with new Image names
			// [ ] ? Which Images? Establish a png art budget
			// File Spec: png
				// [ ] Grass - Vegetation
				// [ ] Tree - Vegetation 3 or more
				// [ ] Beach - borders water wider then 2 if height is equal to the water level
					// [ ] Unless there are cliffs
				// Condition: water is 1 depth
				// [ ] ShallowWater
				// Condition: water is 2 depth or more
				// [ ] DeepWater

			// Bonus elements
				// [ ] FlowingLava
				// [ ] HardLava
				// [ ] LavaRock
				// .. etc

		// Image Features:
			// [f] [ ] Add Iterators (ie Grass01.png, Grass02.png..) - Hold an arbitary number of Unique png files with the previous naming scheme in a data structure. 
				// [ ] Upgrade single Art asset with a range
				// [ ] This also means there needs to be an ArrayList to hold them

		/* Old version for comparison
		if(isAlive){
			return aliveTileArt;
		}else{
			return deadTileArt; // if using different costing tiles
		}
		*/
		return newName;
	}

}