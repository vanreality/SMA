package outil_Recuit;


/*
 * Cette classe nous permet de créer l'objet Parametres, qui contient les 4 paramètres de convergence à donner au Recuit pour qu'il puisse tourner.
 */
public class Parametres {
	int nbVilles;
	int param1;
	double param2;
	double param3;
	
	public Parametres(int nbVilles, int param1, double param2, double param3) {
		this.nbVilles = nbVilles;
		this.param1 = param1; // palier
		this.param2 = param2; // t0
		this.param3 = param3; // coefficient de refroidissement
	}
}
