package comportement;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import outil_Tabou.*;

public class Algo_Tabou extends CyclicBehaviour{
	
	private Donnees mesDonnees = new Donnees();
	private Voyage monVoyage;
	private Ordonnancement monOrdo;

	@Override
	public void action() 
	{
		ACLMessage msg = myAgent.receive();
		if(msg != null)
		{
			String message = msg.getContent();
			ArrayList<Integer> listeId = recupId(message);
		//	System.out.println("Ordo recu");
		//	System.out.println(listeId);
			monVoyage = new Voyage(mesDonnees, listeId);
			monOrdo = new Ordonnancement(monVoyage.villesOrdonneesParId, monVoyage);
		//	System.out.println("Ordonnancement initial:");
		//	System.out.println(monOrdo);
			monOrdo = monOrdo.Tabou2(10, 2, 10);
			System.out.println(monOrdo);
			
		//	System.out.println("Ordo retournée");
		//	System.out.println(monOrdo.genereId().toString());
		//	System.out.println("");
			
			ACLMessage ret = msg.createReply();
			ret.setContent(monOrdo.calculCout() + "/" + monOrdo.genereId().toString());
			myAgent.send(ret);
			
			
		}
		else
		{
			block();
		}
	}
	
	private ArrayList<Integer> recupId(String message)
	{
		message = message.replace("[", ""); 
		message = message.replace("]", ""); 
		message = message.replace(" ", ""); 
		String[] parts = message.split(",");
		int len = parts.length;
		ArrayList<Integer> listeId = new ArrayList<Integer>();
		for(int k = 0 ; k < len ; k++)
		{
			listeId.add(Integer.parseInt(parts[k]));
		}
		return listeId;
	}
	
	
}