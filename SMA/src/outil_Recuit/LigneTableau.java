package outil_AG_Recuit;



/*
 * On cr�e cette classe car il n'existe pas par d�faut dans Java de tableau qui peut contenir des objets diff�rents. Or on veut cr�er un tableau qui contient, dans sa premi�re colonne,
 * le nom des villes, et dans la deuxi�me colonne les distances associ�es. Cette classe nous permet de cr�er une � une les lignes de notre future matrice des distances.
 */

public class LigneTableau {
	public String ville;
	public double[] distances;
	
	public LigneTableau(String nomVille, double[] distances) {
		this.ville = nomVille;
		this.distances = distances;
	}
}
