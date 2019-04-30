package comportement;

import java.util.ArrayList;

import outil_AG.*;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Algo_AG extends CyclicBehaviour{

	@Override
	public void action() 
	{
		ACLMessage msg = myAgent.receive();
		if(msg != null)
		{
			String message = msg.getContent();
			ArrayList<Integer> listeId = recupId(message);
			
			// TODO Auto-generated method stub
			// Variable définissant la longueur de circuit à utiliser
			int longueurcircuit = listeId.size();
			// Taille population
			int taillePopulation = 50;
			// Nombre de generations
			int nbGenerations = 100;
			
			GestionnaireCircuit gc = new GestionnaireCircuit();
			int len = Math.min(gc.villesDonneesOrdonneesParId.length-1, longueurcircuit);
			for (int i=0; i<len; i++) {
				Ville v = new Ville(gc.villesDonneesOrdonneesParId[i], i, gc.matriceDonnees);
				gc.ajouterVille(v);
			}

			// Initialisation avec agents
			ArrayList<Ville> ac = new ArrayList<Ville>(); // ArrayList du circuit envoye a l'algo AG
			for (int i: listeId) {
				ac.add(gc.getVille(i));
			}
			Population pop = new Population(gc, taillePopulation, ac);
			// System.out.println("Distance initiale : " + pop.getFittest().getDistance());
			// System.out.println("Circuit initial : " + pop.getFittest().getCircuit());
		   
			// On fait evoluer notre population sur 100 generations
			GA ga = new GA(gc);
			pop = ga.evoluerPopulation(pop);
			for (int i=0; i<nbGenerations; i++) {
				pop = ga.evoluerPopulation(pop);
			}
		   
			Circuit meilleurCircuit = pop.getFittest();
			ArrayList<Integer> cId = new ArrayList<Integer>();
			for (Ville v: meilleurCircuit.getCircuit()) {
				cId.add(v.getId());
			}
			ACLMessage ret = msg.createReply();
			ret.setContent(cId.toString());
			myAgent.send(ret);
//			System.out.println("Distance finale : " + meilleurCircuit.getDistance());
//			System.out.println("Meilleur "+meilleurCircuit.toString());
//			System.out.println("Longueur de circuit = "+meilleurCircuit.getCircuit().size());	
			
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