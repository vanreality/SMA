package comportement;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class Algo_Main extends Behaviour {

	int i = 0;
	int algo = 1;
	int n = 5;
	ArrayList<Integer> ordoCourant;
	int coutCourant = Integer.MAX_VALUE;
	
	final int ALGO_RECUIT = 1;
	final int ALGO_TABOU = 2;
	final int ALGO_AG = 3;
	
	public void onStart()
	{
		System.out.println("go");
		ordoCourant = new ArrayList<Integer>();
		for(int k = 0 ; k < 5 ; k++)
		{
			ordoCourant.add(k);
		}
	}
	public void action()
	{
		
		executeAlgo("ag");
		
		i++;
	}
	
	public boolean done()
	{
		return i==n;
	}
	
	public int onEnd()
	{
		System.out.println("... fin");
		System.out.println(ordoCourant);
		System.out.println(coutCourant);
		return 1;
	}
	
	private void executeAlgo(String algo)
	{
		ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
		msg.addReceiver(new AID(algo + "@Platform", AID.ISGUID));
		msg.setContent(ordoCourant.toString());
		myAgent.send(msg);
	//	System.out.println("Ordo envoyée");
	//	System.out.println(ordoCourant.toString());
		
		ACLMessage ret = myAgent.receive();
		while(ret == null)
		{
			ret = myAgent.receive();
		}
		if(ret != null) 
		{
			String reply = ret.getContent();
			System.out.println(reply);
			actualiseOrdo(reply);
		//	System.out.println(ordoCourant);	
		}
	}
	
	private void etapeAlgo(int num_algo)
	{
		if(num_algo == ALGO_RECUIT)
		{
			executeAlgo("recuit");
		}
		if(num_algo == ALGO_TABOU)
		{
			executeAlgo("tabou");
		}
		if(num_algo == ALGO_AG)
		{
			executeAlgo("ag");
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
	
	// message -> "cout/[0,1,5,1...]"
	private void actualiseOrdo(String message)
	{
		String[] parts = message.split("/");
		coutCourant = Integer.parseInt(parts[0]); // on recupere le cout courant sous forme de string
		
		ArrayList<Integer> listeId = recupId(parts[1]); // parts[1] -> "[0,8,1,5,9...]"
		for(int k = 0 ; k < ordoCourant.size() ; k++)
		{
			ordoCourant.set(k, listeId.get(k)); 
		}
	}
	
}
