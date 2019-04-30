package agents;

import jade.core.Agent;

import comportement.Algo_Tabou;


public class Agent_Tabou extends Agent {


	protected void setup()
	{
		addBehaviour(new Algo_Tabou());
	}
	
}
