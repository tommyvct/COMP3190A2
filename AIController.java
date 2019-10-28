// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// For demo purposes in Comp 3190 Only

import java.util.ArrayList;

// This class will search the state space by fully duplicating and simulating the entire board. 
// It will alternate between player and AI turns (called by Controller class). 
public class AIController{
	
	// Limits: Limited number of states stored
	// Limited Depth will be required. 
	// Max 100 Moves per level
	private GameBoard board;
	// might want Things too

	public AIController(GameBoard boardReference){
		board = boardReference;
	}

	// Counters
	private int totalStatesExamined = 0;
	private int statesExaminedThisTurn = 0;
	private int totalTurns = 0;

	public void playerTurn(){

		Player currentPlayer = board.getPlayer();
		if( currentPlayer == null){
			System.out.println("No Player Found, returning");
			return;
		}

		GameBoard simulation = board.deepCopy();	
		// intermediate cache of current Agents and Things 
		ArrayList<Agent> allAgents = simulation.getAgents();
		ArrayList<Thing> allThings = simulation.getThings();

		// Calculate Player next best move

		// Calculate AI next best move. 

		//etc. 

		// select best legal move and make it
	}

	// Calculate value of a given board state
	private double calculatePlayer_F( GameBoard state ){
		
		// --- For player only ---
		// First iteration 
		// Go to Item, ignore AI
		// If no item, go to goal
		// One cost per tile

		// G is total cost so far

		// 1. Get Item, get to goal
		// First iteration 
		// Go to Item, ignore AI
		// If no item, go to goal

		// 2. Avoid Creature
		// Take into account creature (AIAgent). If it touches the PLayer, the Player "dies", GameOver

		// 3. Avoid Creature - Adversarial, need access to calc AI F

		// 4. Improve performance

		return 0.0;
	}

	// Init the beginning of the AI turn
	public void aiTurn(){

		ArrayList<Agent> agents = board.getAgents();
		if( agents.size() < 1){
			System.out.println("No Agents Found, returning");
			return;
		}

		GameBoard simulation = board.deepCopy();

		// ... 


	}

		// Calculate value of a given board state
	private double calculateAI_F(GameBoard state, Player thePlayer, ArrayList<AIAgent> aiList){
		
		return 0;
	}

	// End of round checks
	// [ ] Will currently crash if the turn is not called before this due to 
	// The player reference not being set. Should move player calls to GameBoard
	// to sort its list better. 
	// This one is for external use
	public boolean isGameOver(){
		return isGameOver(board); // Call it on this board
	}

	// In case you want to check the state of the simulation
	private boolean isGameOver(GameBoard currentBoard){

		// Patched: Should be currentBoard not board. 
		Player player = currentBoard.getPlayer(); // Might be null if no player found
		if( player == null){
			System.out.println("No player found, ending game");
			return true;
		}

		if(player.getTile() == null){
			System.out.println("Error: Player found but not on the board, ending game");
			return true;
		}

		// Conflict with the AI. If player and AI are neighbors?
		if(hasPlayerLost(player)){
			System.out.println("Player Lost - Game Over");
			return true;
		}

		// Got the Loot?
		if(collectItem(player)){
			// Collect any items on this Tile. 
			System.out.println("Player Collected Item");
		}

		// Player has reached the goal 
		if(hasPlayerWon(player)){

			// Gave Over

			System.out.println("Player Won - Game Over");
			return true;
		}

		// At least nothing terrible happened
		return false;

	}

	// --- Helper Methods ---
	// If the AI is next to the player
	// [ ] need a better system for this but avoids needing multiple agents 
	// and will work for flanking or multi units later. 
	private boolean hasPlayerLost(Player thePlayer){
		
		// Update to ArrayList (and in Tile). 
		TileList list = thePlayer.getTile().getNeighbors();

		// Check Neighboring Tiles to see if an AI agent is there
		for(int i = 0; i < list.size(); i++){
			// If the neighbor has an AI agent
			if( list.get(i).hasAgent() 
					&& list.get(i).getAgent() instanceof AIAgent){
				
				// Caught by AI
				return true;
			}
		}
		return false;
	}

	// If the player has reached the goal, they are done
	private boolean hasPlayerWon(Player thePlayer ){
		// is the player on the same tile as the goal
		Tile t = thePlayer.getTile();
		// The Tile the player is on also contains a Goal
		if(t.getThing() instanceof Goal){
			return true;
		}
		return false; // not found
	}

	// Collect the item from the map. s
	private boolean collectItem(Player thePlayer){
		// is the player on the same tile as the goal
		Tile t = thePlayer.getTile();
		// The Tile the player is on also contains a Goal
		if(t.getThing() instanceof Item){
			Item foundItem = (Item)t.getThing();
			System.out.println("Item Found: " + foundItem);
			thePlayer.addItem( foundItem );
			return true;
		}
		return false;
	}

}

/* AB Pruning
function minimax(position, depth, alpha, beta, maximizingPlayer)
	if depth == 0 or game over in position
		return static evaluation of position

	if maximizingPlayer
		maxEval = -infinity
		for each child of position
			eval = minimax(child, depth - 1, alpha, beta false)
			maxEval = max(maxEval, eval)
			alpha = max(alpha, eval)
			if beta <= alpha
				break
		return maxEval

	else
		minEval = +infinity
		for each child of position
			eval = minimax(child, depth - 1, alpha, beta true)
			minEval = min(minEval, eval)
			beta = min(beta, eval)
			if beta <= alpha
				break
		return minEval

*/