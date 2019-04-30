package outil_AG;


import java.util.ArrayList;
import java.util.Collections;

public class GA {

	private GestionnaireCircuit gestionnaireCircuit;
	private Double tauxMutation = 0.015;
	private int tailleTournoi = 5;
	private boolean elitisme = true;
	
	public GA(GestionnaireCircuit gestionnaireCircuit) {
		super();
		this.gestionnaireCircuit = gestionnaireCircuit;
	}

	/**
	 * Renvoie le circuit le plus court parmi tailleTournoi circuits pris au hasard
	 * @param popu
	 * @return
	 */
	public Circuit selectionTournoi(Population popu) {
		Population tournoi = new Population(this.gestionnaireCircuit, this.tailleTournoi);
		//On prend tailleTournoi circuits aléatoirement parmi popu
		for (int i=0; i<this.tailleTournoi; i++) {
			int randomId = (int) Math.floor(Math.random() * popu.getTaillePopulation());
			tournoi.sauvegarderCircuit(i, popu.getCircuit(randomId));
		}
		// On retourne le trajet le plus court parmi les tailleTournoi pris au hasard
		return tournoi.getFittest();
	}
	      
	public Circuit crossover(Circuit parent1, Circuit parent2) {
		// On crée des circuits sans paris (la 1ère ville de chaque circuit)
		parent1 = new Circuit(this.gestionnaireCircuit, new ArrayList<Ville>(parent1.getCircuit().subList(1, parent1.getTailleCircuit())));
		parent2 = new Circuit(this.gestionnaireCircuit, new ArrayList<Ville>(parent2.getCircuit().subList(1, parent2.getTailleCircuit())));
		Circuit enfant = new Circuit(this.gestionnaireCircuit);
		enfant = new Circuit(this.gestionnaireCircuit, new ArrayList<Ville>(enfant.getCircuit().subList(1, enfant.getTailleCircuit())));
		
		int startPos = (int) Math.floor(Math.random()*parent1.getTailleCircuit());
		int endPos = (int) Math.floor(Math.random()*parent1.getTailleCircuit());
		if (startPos > endPos) {
			int i = 1*startPos;
			startPos = 1*endPos;
			endPos = 1*i;
		}
		
		// On remplit de villes de parent1 entre les deux noeuds
		for (int i=0; i<enfant.getTailleCircuit(); i++) {
			if (i>startPos && i<endPos) {
				enfant.setVille(i, parent1.getVille(i));
			}
		}
		
		//On remplit de villes de parent 2 ailleurs
		for (int i=0; i<=startPos; i++) {
			enfant.setVille(i, null);
		}
		for (int i=endPos; i<enfant.getTailleCircuit(); i++) {
			enfant.setVille(i, null);
		}
		
		for (int i=0; i<parent2.getTailleCircuit(); i++) {
			//System.out.println("i="+i);
			if (enfant.contientVille(parent2.getVille(i))==false) {
				for (int j = 0; j<enfant.getTailleCircuit(); j++) {
					if (enfant.getVille(j) == null) {
						enfant.setVille(j, parent2.getVille(i));
						break;
					}
				}
			}
		}
		
		// On ajoute paris en 1er dans le circuit
		ArrayList<Ville> a = new ArrayList<Ville>();
		a.add(gestionnaireCircuit.getVille(0));
		a.addAll(enfant.getCircuit());
		enfant = new Circuit(this.gestionnaireCircuit, a);
		return enfant;
	}
	
	public Circuit muter(Circuit c) {
		
		// On crée deux arraylist ac1 et ac2 pour conserver Paris en première position du circuit
		// ac1 = [Paris]
		// ac2 = [toutes les autres villes]
		// on fait les modifications que sur ac2 et on concatène ac1 et ac2 ensuite
		ArrayList<Ville> ac1 = new ArrayList<Ville>();
		ac1.add(gestionnaireCircuit.getVille(0));
		ArrayList<Ville> ac2 = new ArrayList<Ville>();
		ac2 = new ArrayList<Ville>(c.getCircuit().subList(1, c.getTailleCircuit()));
		Circuit c2 = new Circuit(this.gestionnaireCircuit, ac2);
		
		
		for (int cPos1 = 0; cPos1<c2.getTailleCircuit(); cPos1++) {
			if (Math.random()<this.tauxMutation) {
				int cPos2 = (int) (c2.getTailleCircuit()*Math.random());
				Ville v1 = c2.getVille(cPos1);
				Ville v2 = c2.getVille(cPos2);
				c2.setVille(cPos2, v1);
				c2.setVille(cPos1, v2);
			}
		}
		ac1.addAll(c2.getCircuit());
		Circuit c1 = new Circuit(this.gestionnaireCircuit, ac1);
		return c1;
	}
	
	public Population evoluerPopulation(Population popu) {
		Population newpop = new Population(this.gestionnaireCircuit, popu.getTaillePopulation());
		int elitismeOffset = 0;
		if (this.elitisme) {
			newpop.sauvegarderCircuit(0, popu.getFittest());
			elitismeOffset = 1;
		}
		//On réalise un crossover pour chaque nouveau circuit de l'enfant
		for (int i=elitismeOffset; i<newpop.getTaillePopulation(); i++) {
			Circuit parent1 = this.selectionTournoi(popu);
			Circuit parent2 = this.selectionTournoi(popu);
			Circuit enfant = this.crossover(parent1, parent2);
			newpop.sauvegarderCircuit(i, enfant);
		}
		for (int i=elitismeOffset; i<newpop.getTaillePopulation(); i++) {
			newpop.sauvegarderCircuit(i, this.muter(newpop.getCircuit(i)));
		}

		System.out.println("Distance actuelle : " + newpop.getFittest().getDistance());
		
		return newpop;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GestionnaireCircuit gc = new GestionnaireCircuit();
		
		// Variable définissant la longueur de circuit à utiliser
		int longueurcircuit = 5;
		
		int len = Math.min(gc.villesDonneesOrdonneesParId.length-1, longueurcircuit);
		for (int i=0; i<len; i++) {
			Ville v = new Ville(gc.villesDonneesOrdonneesParId[i], i, gc.matriceDonnees);
			gc.ajouterVille(v);
		}
		
	   // On initialise la population avec 50 circuits (taille de la population)
	   Population pop = new Population(gc, 50);
	   System.out.println("Distance initiale : " + pop.getFittest().getDistance());
	   System.out.println("Circuit initial : " + pop.getFittest().getCircuit());
	   
	   // On fait evoluer notre population sur 100 generations
	   GA ga = new GA(gc);
	   pop = ga.evoluerPopulation(pop);
	   for (int i=0; i<100; i++) {
	      pop = ga.evoluerPopulation(pop);
	   }
	   
	   Circuit meilleurCircuit = pop.getFittest();
	   System.out.println("Distance finale : " + meilleurCircuit.getDistance());
	   System.out.println("Meilleur "+meilleurCircuit.toString());
	   System.out.println("Longueur de circuit = "+meilleurCircuit.getCircuit().size());
	}

}
