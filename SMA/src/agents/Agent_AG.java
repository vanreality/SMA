import jade.core.Agent;

import comportement.Algo_AG;


public class Agent_AG extends Agent {


	protected void setup()
	{
		addBehaviour(new Algo_AG());
	}
	
}
