package agents;

import jade.core.Agent;
import comportement.Algo_Main;

public class Agent_Main extends Agent {

	protected void setup()
	{
		
		Algo_Main main = new Algo_Main();
		
		
		
		addBehaviour(main);
		
	}
	
}
