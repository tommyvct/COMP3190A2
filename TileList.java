// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

// A partially full array of Tile objects
public class TileList{
	
	private static final int maxTiles = 8;
	
	// Instance Variables
	private int currentTiles = 0;
	private Tile[] tiles;

	// Basic constructor
	public TileList(){
		tiles = new Tile[maxTiles];
	}

	// Get the current size
	public int size(){
		return currentTiles;
	}

	// Add a tile to the list
	public void add(Tile newTile){
		if( currentTiles >= tiles.length){
			System.out.println("Error: TileList is full " + newTile.toString() + " index: " + currentTiles);
			return;
		}

		tiles[currentTiles] = newTile;
		currentTiles++;
	}

	// Return a tile at a given index
	public Tile get(int index){
		if(index < 0 || index >= currentTiles){
			System.out.println("Error: Invalid Tile accessed at index " + index);
			return null;
		}else{
			return tiles[index];
		}
	}
}