package main;

import jade.core.ProfileImpl;
import jade.wrapper.*;

public class MainOptimisation {
	public static void main(String[] args) {
		jade.core.Runtime rt = jade.core.Runtime.instance();
		ProfileImpl pMain = new ProfileImpl(null, 2222, "Platform");
		AgentContainer mc = rt.createMainContainer(pMain);
		AgentController rma, main, tabou, ag;
		try {
			
			rma = mc.createNewAgent("rma", "jade.tools.rma.rma", null);
			rma.start();
			
			main = mc.createNewAgent("main", "agents.Agent_Main", null);
			main.start();
			
			tabou = mc.createNewAgent("tabou", "agents.Agent_Tabou", null);
			tabou.start();
			
			ag = mc.createNewAgent("ag", "agents.Agent_AG", null);
			ag.start();

		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
}
