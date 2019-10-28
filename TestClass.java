
// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

public class TestClass{
	// Some example tests for demo purposes. 
	// no warrenty included. 
	public static void main(String[] args){
			// Just testing some of the copying methods
			basicCopyTests();
			// To give an idea of performance. 
			// How could we improve performance? 
			performanceTests();
	}

	private static void performanceTests(){

		// Test 1 - 10000 iterations just cloning a 10x10 board
		int iterations = 10000;

		GameBoard board =  new GameBoard();
		board.initializeBoard(10,10);

		long startTime = System.currentTimeMillis();

		for(int i = 0; i < iterations; i++){
			GameBoard copy = board.deepCopy();
		}

		// from https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java/14323134
		long elapsedTime = System.currentTimeMillis() - startTime;
		long elapsedSeconds = elapsedTime / 1000;

		System.out.println("MS To Calc: Test1: " + elapsedTime);
		System.out.println("Seconds To Calc: Test1: " + elapsedSeconds);

		// Test 2 - 10000 iterations just cloning a 50x50 board
		board.initializeBoard(50,50);
		MapGenerator mg = new MapGenerator(board);
		mg.addAgentsToMap(true, 10, 0);

		startTime = System.currentTimeMillis();

		for(int i = 0; i < iterations; i++){
			GameBoard copy = board.deepCopy();
		}

		// from https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java/14323134
		elapsedTime = System.currentTimeMillis() - startTime;
		elapsedSeconds = elapsedTime / 1000;

		System.out.println("MS To Calc: Test2: " + elapsedTime);
		System.out.println("Seconds To Calc: Test2: " + elapsedSeconds);

		// Test 2 - 10000 iterations just cloning and creating agents on a 100x100 board
		board.initializeBoard(100,100);
		mg = new MapGenerator(board);

		startTime = System.currentTimeMillis();

		for(int i = 0; i < iterations; i++){
			mg.createMap(100, 100, 0.3);
			mg.addAgentsToMap(true, 10, 0);
		}

		// from https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java/14323134
		elapsedTime = System.currentTimeMillis() - startTime;
		elapsedSeconds = elapsedTime / 1000;

		System.out.println("MS To Calc: Test3: " + elapsedTime);
		System.out.println("Seconds To Calc: Test3: " + elapsedSeconds);

	}

	private static void basicCopyTests(){
		// Tile Clones
		Tile t = new Tile();
		Tile t2 = t.deepCopy();
		t2.setNext(true);
		t2.updateToNext();

		System.out.println(t.toString());
		System.out.println(t2.toString());

		// Clone the board
		GameBoard board = new GameBoard();
		board.initializeBoard(3,5);

		board.addAgent(new Player("Dylan"),0,0);
		board.addAgent(new AIAgent("AI"),2,4);
		board.addThing(new Goal("Goal"), 1,1);
		board.addThing(new Item("Item"), 2,2);

		GameBoard board2 = board.deepCopy();

		if(board.getPlayer() == board2.getPlayer()){
			System.out.println("! it is the same player!");
		}

		// Testing the board -> player ->Tiles
		System.out.println("Player Tiles: " + board.getPlayer().getTile() + "\n" +  board2.getPlayer().getTile());

		if(board.getPlayer().getTile() == board2.getPlayer().getTile()){
			System.out.println("! it is the same player!");
		}

		// This just seems silly
		System.out.println("board.getPlayer().getTile().getAgent(): " + board.getPlayer().getTile().getAgent() + "\n" + board2.getPlayer().getTile().getAgent());

		if(board.getPlayer().getTile().getAgent() == board2.getPlayer().getTile().getAgent()){
			System.out.println("! it is the same player on the tile referenced by the player on the board!");
		} 
		System.out.println(" --- Board Printouts --- ");
		System.out.println(board.toString());
		System.out.println(board2.toString());


		board2.initializeBoard(3,3);

		System.out.println(board2.toString());

	}

}