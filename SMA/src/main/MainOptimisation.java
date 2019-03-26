package main;

import jade.core.ProfileImpl;
import jade.wrapper.*;

public class MainOptimisation {
	public static void main(String[] args) {
		jade.core.Runtime rt = jade.core.Runtime.instance();
		ProfileImpl pMain = new ProfileImpl(null, 2222, "Platform");
		AgentContainer mc = rt.createMainContainer(pMain);
		AgentController rma;
		try {
			rma = mc.createNewAgent("rma", "jade.tools.rma.rma", null);
			rma.start();
			
//			mc.createNewAgent("A", "myAgent.AgentA", null).start();
//			mc.createNewAgent("B", "myAgent.AgentB", null).start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
}
