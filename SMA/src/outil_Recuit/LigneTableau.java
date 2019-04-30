package outil_Recuit;



/*
 * On crée cette classe car il n'existe pas par défaut dans Java de tableau qui peut contenir des objets différents. Or on veut créer un tableau qui contient, dans sa première colonne,
 * le nom des villes, et dans la deuxième colonne les distances associées. Cette classe nous permet de créer une à une les lignes de notre future matrice des distances.
 */

public class LigneTableau {
	public String ville;
	public double[] distances;
	
	public LigneTableau(String nomVille, double[] distances) {
		this.ville = nomVille;
		this.distances = distances;
	}
}
