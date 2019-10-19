// The AI agent is a Creature trying to stop 
// the player from reaching the Goal .
public class AIAgent extends Agent{

	// Art Settings
	private final String image = "Creature.png";
	
	// --- Constructors ---
	public AIAgent(String name){
		super(name);
	}

	// --- Art ---
	@Override
	public String getImageName(){
		return image;
	}
}