package outil_Recuit;

import outil_AG_Recuit.LectureBaseDeDonnees;
import outil_AG_Recuit.LigneTableau;
import outil_AG_Recuit.Parametres;
import outil_AG_Recuit.Resolution;
import outil_AG_Recuit.TextToTableConverter;
import outil_AG_Recuit.Trajet2;
import outil_AG_Recuit.Ville2;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Algo_Recuit{
	int nbVilles;
	int param1;
	double param2;
	double param3;
	private double min_fitness;
	private double ecart;
	private double moyenne;
	private double temps_execution;
	public String trajet_optimal_id;
	public Ville2[] carte;
	public Trajet2 T;
	
	
	
	
	// Constructeurs
	
	
	public Algo_Recuit(int nbVilles, int param1, double param2, double param3) { // Ce constructeur est celui qu'on utilise lorsque l'on souhaite remplir le fichier qui contient nos résultats.
		this.nbVilles = nbVilles;
		this.param1 = param1; // palier
		this.param2 = param2; // t0
		this.param3 = param3; // coefficient de refroidissement
		
		/*
		 * Ci-dessous, on récupère les informations de la base de données (à savoir les paramètres qui permettront de faire tourner le recuit simulé). Puis on les met dans tableau_distances,
		 * qui dans chaque ligne contient le nom de la ville, puis la liste des distances aux autres villes. Cela nous permet de créer la carte qui contient nbVilles Villes, qui est ensuite
		 * utilisée pour initialiser les trajets nécessaires dans le Recuit.
		 */

		TextToTableConverter constructeur_du_tableau = new TextToTableConverter("../data.csv",nbVilles);
		ArrayList<LigneTableau> tableau_distances = constructeur_du_tableau.getMatriceDistances();
		
		this.carte = this.createCarte(tableau_distances);
		this.T = new Trajet2(carte);
				
		try {
			this.trajet_optimal_id = this.execution();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* 
	 * Le constructeur suivant est celui qu'on utilise lorsque l'on veut faire communiquer les agents entre eux. En effet, l'argument carteString correspond à ce que l'agent précédent 
	 * va donner à l'agent Recuit pour initialiser son fonctionnement.
	 */
		

	
	
	
	public Algo_Recuit(int nbVilles, int param1, double param2, double param3, String carteString) {
		this.nbVilles = nbVilles;
		this.param1 = param1; // palier
		this.param2 = param2; // t0
		this.param3 = param3; // coefficient de refroidissement
		

		TextToTableConverter constructeur_du_tableau = new TextToTableConverter("../data.csv",nbVilles);
		ArrayList<LigneTableau> tableau_distances = constructeur_du_tableau.getMatriceDistances();
		
		this.carte = this.createCarte(tableau_distances);
		this.T = new Trajet2(carteString, tableau_distances);
		
		try {
			this.trajet_optimal_id = this.execution();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
		

	//Getters
	
	
	public double getMin_fitness() {
		return min_fitness;
	}

	public double getEcart() {
		return ecart;
	}

	public double getMoyenne() {
		return moyenne;
	}

	public double getTemps_execution() {
		return temps_execution;
	}
	
	
	
	//Méthodes
	
	
	/*
	 * La méthode createCarte sert à créer la carte contenant les nbVilles premières villes de la base de données. Cela permet de sélectionner le nombre de villes que l'on veut et donc de faire
	 * un PVC à taille variable.
	 */
	
	private Ville2[] createCarte(ArrayList<LigneTableau> tableau_distances) {
		
		Ville2[] carte = new Ville2[this.nbVilles];
		int indice = 0;
		for(int i = 0; i < tableau_distances.size(); i++) {
			carte[indice] = new Ville2(tableau_distances.get(i).ville,tableau_distances.get(i).distances, i);
			indice+=1;
		}
		return carte;	
	}
	
	
	
	
	
	
	
	
	private String execution () throws IOException {
				
		Ville2[] carte = this.carte.clone();
		
		double FIT=0; // Somme des distances 
		int iter = 1000; // A faire varier, nombre de fois qu'on fait tourner l'algo
		Trajet2 [] LISTE_TRAJETS = new Trajet2[iter+1];   //la liste des différents trajets qu'on va effectuer, on met iter+1, parce que la boucle tournera iter+1 fois
		long [] liste_durées = new long[iter+1];   //la liste des différentes durées qu'on va récuperer, correspondant à la durée des trajets de la liste LISTE_TRAJETS
		long somme_durées=0;
		long v=0;
		
		
		//Initialisation des fichiers pour écrire dedans
		/*
		File DUREES = new File("C:/Users/Camille/Documents/Centrale 2018-2019/ICO/Projet/Durées.txt"); // DUREES est le fichier qui va contenir les durées de parcours
		DUREES.createNewFile();
		File DISTANCES = new File("C:/Users/Camille/Documents/Centrale 2018-2019/ICO/Projet/Distances.txt");
		FileWriter writer_DUREES = new FileWriter(DUREES);
		FileWriter writer_DISTANCES = new FileWriter(DISTANCES);
		*/
		
		
		for (int n=0; n<=iter; n++) {
		
			Trajet2 T = new Trajet2(this.T);
			Trajet2 T0 = T;
			int k = 0;
			int nbiter_cycle = this.param1; 
			double temp = this.param2;
			boolean nouveau_cycle = true;
			long debut = System.nanoTime();
			
			while (nouveau_cycle==true) {
				int nbiter = 0;
				nouveau_cycle = false;
				while(nbiter<=nbiter_cycle && k<=1000000) {
					k+=1;
					nbiter+=1;
					Trajet2 T1 = new Trajet2(carte); //Trajet le plus court
					double delta = T1.getDistanceTotale()-T.getDistanceTotale(); //différence de distance entre les deux itinéraires
					if (delta < 0) { //Si T est plus long que T1, alors on actualise le chemin le plus court 
						T=T1;
						nouveau_cycle = true;
					}
					else {
						double probabilite = Math.exp(-delta/temp);
						double q = Math.random();
						if (q < probabilite) {
							T = T1;
							nouveau_cycle = true;
							if (T.getDistanceTotale() < T0.getDistanceTotale()) {
								T0 = T;
							}
						}
					
					}
				}
				temp *= this.param3;
			
			}
			//System.out.println(T);
			double fit = T.getDistanceTotale();
			//liste_FIT[n]=fit;
			LISTE_TRAJETS[n]=T;
			FIT=FIT+fit;
			String fit_string = Double.toString(fit);
			long fin = System.nanoTime();
			long durée = fin-debut;
			String duree = Long.toString(durée); // On convertit durée en string pour pouvoir l'écrire dans le fichier texte
			//System.out.println(duree);
			/*
			writer_DUREES.write(duree);
			writer_DUREES.write("\n");
			writer_DISTANCES.write(fit_string);
			writer_DISTANCES.write("\n");
			*/
			//BufferedWriter.newLine();
			liste_durées[n]=durée;
			somme_durées += durée;
			//System.out.println("durée d'exécution = " + durée );
			
		}
		/*
		writer_DUREES.flush();
		writer_DUREES.close();	
		writer_DISTANCES.flush();
		writer_DISTANCES.close(); 
		
		
		/*
		 * Maintenant que notre algorithme a tourné plusieurs fois, on va pouvoir calculer la moyenne du temps d'exécution, la moyenne de la distance parcourue,
		 */
		
		long moyenne_durée_exécution = somme_durées/iter;
		
		
		for (int m=0; m<=iter; m++) {
			v+=((liste_durées[m]-moyenne_durée_exécution)*(liste_durées[m]-moyenne_durée_exécution))/iter;
				
				
		}
			double écart_type = Math.sqrt(v);
			double moyenne_longueur_trajet = FIT/1000;
			//System.out.println("moyenne de la durée d'exécution en nanosecondes = " + moyenne_durée_exécution);
			//System.out.println("moyenne de la durée d'exécution en millisecondes = " + moyenne_durée_exécution*0.0000001);
			//System.out.println("écart_type de la durée d'exécution = " + écart_type*0.0000001);
			//System.out.println("moyenne de la longueur du trajet =" + moyenne_longueur_trajet);
			
			Trajet2 TRAJET_MIN = LISTE_TRAJETS[0]; // On initialise la variable qui contiendra le trajet minimum après la boucle suivante :
			double FIT_MIN=LISTE_TRAJETS[0].getDistanceTotale();
			for (int m=0; m<=iter; m++) {
				if(LISTE_TRAJETS[m].getDistanceTotale()<=FIT_MIN) {
					FIT_MIN=LISTE_TRAJETS[m].getDistanceTotale();
					TRAJET_MIN=LISTE_TRAJETS[m];
				}
			}

		
			Trajet2 TRAJET_MAX = LISTE_TRAJETS[0]; // On initialise la variable qui contiendra le trajet maximum après la boucle suivante :
			double FIT_MAX=LISTE_TRAJETS[0].getDistanceTotale();
			for (int m=0; m<=iter; m++) {
				if(LISTE_TRAJETS[m].getDistanceTotale()>=FIT_MAX) {
					FIT_MIN=LISTE_TRAJETS[m].getDistanceTotale();
					TRAJET_MAX=LISTE_TRAJETS[m];
				}
			}
			double ecart_max_min = TRAJET_MAX.getDistanceTotale()-TRAJET_MIN.getDistanceTotale();
			
			
			List<Integer> trajet_optimal = TRAJET_MIN.getTrajetId(); // Ceci nous permet de récupérer le trajet optimal sous forme d'Id.
			
			double distance_trajet_optimal = TRAJET_MIN.getDistanceTotale();
			System.out.println(TRAJET_MIN);
			
			this.ecart=ecart_max_min;
			this.moyenne = moyenne_longueur_trajet;
			this.temps_execution = moyenne_durée_exécution*0.0000001;
			this.min_fitness = distance_trajet_optimal;
			
			
			
			System.out.println(trajet_optimal);
			
			/* Ci-dessous, on transforme le résultats en un String de notre liste, afin que les agents puissent communiquer. */
			
			String resultat = "[";
			ListIterator<Integer> it = trajet_optimal.listIterator();
			while (it.hasNext()) {
				Integer i = it.next();
				resultat = resultat + i + ",";
			}
			resultat = resultat.substring(0, resultat.length()-1);
			resultat += "]";
			
			System.out.println(resultat);
			return resultat; // Retourne le trajet optimal avec les villes sous forme d'un String de la liste. A mettre dans le done de l'agent.
	}
}


