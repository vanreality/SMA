package comportement;
import jade.core.behaviours.Behaviour;
import jade.core.Agent; 

public class Algo_Recuit extends Behaviour {

	
	Ville[] carte = new Ville[] {new Ville("Bordeaux", new int[] {0,780,320,580,480,660}),
			new Ville("Lyon", new int[] {780,0,700,460,300,200}),
			new Ville("Nantes", new int[] {320,700,0,380,820,630}),
			new Ville("Paris", new int[] {580,460,380,0,750,310}),
			new Ville("Marseille", new int[] {480,300,820,750,0,500}),
			new Ville("Dijon", new int[] {660,200,630,310,500,0}),
			};


	
	
	public void action() {
		Ville[] carte = new Ville[] {new Ville("Bordeaux", new int[] {0,780,320,580,480,660}),
				new Ville("Lyon", new int[] {780,0,700,460,300,200}),
				new Ville("Nantes", new int[] {320,700,0,380,820,630}),
				new Ville("Paris", new int[] {580,460,380,0,750,310}),
				new Ville("Marseille", new int[] {480,300,820,750,0,500}),
				new Ville("Dijon", new int[] {660,200,630,310,500,0}),
				};
		int palier = 10;
		double t0=10000;
		double lambda = 0.8;
		
		Trajet T = new Trajet(carte);
		Trajet T0 = T;
		int k = 0;
		int nbiter_cycle = palier; 
		double temp = t0;
		boolean nouveau_cycle = true;
		long debut = System.currentTimeMillis();
		while (nouveau_cycle==true) {
			int nbiter = 0;
			nouveau_cycle = false;
			while(nbiter<nbiter_cycle && k<=1000000) {
				k+=1;
				nbiter+=1;
				Trajet T1 = new Trajet(carte);
				int delta = T1.getFitness()-T.getFitness();
				if (delta < 0) {
					T=T1;
					nouveau_cycle = true;
				}
				else {
					double probabilite = Math.exp(-delta/temp);
					double q = Math.random();
					if (q < probabilite) {
						T = T1;
						nouveau_cycle = true;
					if (T.getFitness() < T0.getFitness()) {
						T0 = T;
					}
					}
					
				}
			}
			temp *= lambda;
			
		}
		System.out.println(T);
		long fin = System.currentTimeMillis();
		long durée = fin-debut;
		System.out.println("durée d'exécution = " + durée );
	}
	
	public boolean done(){
		return true ;
	}

}
