import java.util.ArrayList;

// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

// The controller class is responsible for handling Input, creating and managing all other
// Classes and contains the main method. It also counts on the timer thread to update the game loop
public class Controller{
	
	// Preset sizes of the maps
	private int[][] mapSizes = {{1,1},{3,3},{5,10},{10,20},{20,35},{30,55}, {55,80 }};
	private int[] currentSize = {3,3}; // current size of the map {row, col}
	
	// spawn density (0-1) (Proportion of tiles that are alive/walls of total tiles)
	private double spawnDensity = 0.3;

	// --- Controller references to the Board and Window ---
	private GameBoard board; // The Playing surface
	private BoardWindow window; // Draws Graphics

	// --- Controller references for Map Generation and AI ---
	private MapGenerator generator; // Generates maps based on rules and conditions
	private AIController ai; // Manages the AI for player and opponents

	// Enable this boolean to get extra debugging info from the Controller. 
	private boolean DEBUG_MODE = true;
	private boolean isPaused = true;

	// Can use this to update the speed without messing with the Timer
	// Less steps per turn is faster, more is slower
	private int stepsThisTurn = 0;  
	private final int STEPS_PER_TURN = 10;

	// Start the game
    public static void main(String[] args){
    	Controller runtime = new Controller();
    }

    // Main constructor that sets up the reference to the BoardWindow and kicks everything else off
	public Controller(){
		// We will not change this because the BoardWindow is subscribing to it. 
		board = new GameBoard();
		generator = new MapGenerator(board); // make a map generator for this board
		ai = new AIController(board);

		// Generate a random starting board
		randomBoard();
		randomAgents();
    	
    	// Loading the Window after the Board is loaded helps 
		window = new BoardWindow(board, this);
	}

    // Set the existing board to random Tile values
    private void randomBoard(){
    	//board.resetBoard(spawnDensity); // random with probability
    	generator.createMap( currentSize[0],currentSize[1], spawnDensity ); // 3 row x 5 col with .3 density
    }

    // Don't change the map but generate random agents on it
    private void randomAgents(){
    	generator.addAgentsToMap( true, 1 , 0); // a player, 1 item and no monster/creature
    }

    // Update the Cellular Automata to the next step
    private void nextStep(){
    	board.nextStep();
    }

    // An example of using the custom next step method call. 
    // Note you can pass in a null array to only apply life or death, however
    // if you pass the wrong sized boolean array it may crash (needs 9 elements, for index (0-8) inclusive)
    private void customNextStep(){

    	// if alive, apply life rule based on the number of neighbors == index 
    	// (ie index is number of neighbors, returns whether it will be alive next step or not
    	// Life rule is applied to cells already alive (walls)
		// boolean[] refineLifeRules = {false,false,true,true,true,false,false,false,false}; // number of neighbors to come alive on
		// If dead, apply death rules based on number of neighbors (come alive if 3 neighbors only)
		// boolean[] refinedeathRules = {false,false,false,true,true,false,false,false,false}; // turn alive in these conditions

		// board.nextStep(refineLifeRules, refinedeathRules);


		boolean[] lifeRule =  {false, false,  true,  true,  true,  true,  true,  true,  true};
		boolean[] deathRule = {true,  true, false, false, false, false, false, false, false};
		// lifeRule, deathRule
		// currentBoard.nextStep(lifeRule, null);
		board.nextStep(lifeRule, deathRule);


    }

    // Next step of the Game, Applys the players move, then the AIs move, then checks end conditions. 
    private void nextTurn(){
    	
    	ai.playerTurn(); // Give the current board state to the AI
    	
    	ai.aiTurn(); // Move the AI 

    	// Check for end state conditions
    	if(ai.isGameOver()){
    		System.out.println("Game is Over");

    		// [ ] Output end of game info here
    		if(board.getPlayer() != null){
    			System.out.println("Game Ended for the Player: " + board.getPlayer().toString());
    		}else{
    			System.out.println("Game Ended With No Player Found");
    		}
    		
    		isPaused = true;
    	}
    }
	
	// Non arrow key pressed
	public void keyPressed(char key){

		// optional next step
		// [ ] arrow input for manual player control

		if( DEBUG_MODE) { System.out.println("Key Pressed: " + key);}
		
		// if this is a number
        if(Character.isDigit(key)){
            if( DEBUG_MODE ) { System.out.println("Load Map Size:"  + key);}
            // convert to an int
            int selected = Character.digit(key,10);

			if(selected >=0 && selected < mapSizes.length ){
				currentSize = mapSizes[selected];
				// Reload map
				randomBoard();
			}
		}

		// P also means pause timer
        if(key =='p'){
           if(isPaused = !isPaused){   
               System.out.println(" Execution Started ");  
           }else{
               System.out.println(" Execution Paused ");
           }
         // R key sets things to random
        }else if( key == 'm'){
        	// random map
        	randomBoard();

        	// Reset agents
        }else if( key == 'r'){

        	randomAgents();
			
		// N key goes to the next state of the board. 
		}else if( key == 'n'){
			nextStep();
		}else if( key == 's' || key == 't'){ // couldn't decide which one was better
			nextTurn();
		}else if(key == 'c'){
			// Apply custom rule to board
			customNextStep();
		}
	}

	// This is called automatically on a Timer from the BoardWindow class. 
	public void stepUpdate(){
		// This gets called all the time if active
		
		if( !isPaused){
			stepsThisTurn++;
			if(stepsThisTurn > STEPS_PER_TURN){
				stepsThisTurn = 0;
				
				nextTurn();
				
				//if( DEBUG_MODE){ System.out.println("StepUpdate"); }
			}
		}
	}
}