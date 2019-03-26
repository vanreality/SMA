package agents;

import jade.core.Agent;
import comportement.*;

public class Agent_Recuit extends Agent{
	protected void setup() {
		Algo_Recuit A = new Algo_Recuit();
		addBehaviour(A);
	}
}
