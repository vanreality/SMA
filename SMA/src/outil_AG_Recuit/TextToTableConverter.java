package outil_AG_Recuit;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Cette classe permet de récupérer les données du fichier csv (qui contient les villes, leurs Id et la matrice des distances) et de créer une matrice_distances plus simple d'utilisation.
 * En effet, une fois la matrice_distances créée, on n'a plus besoin de faire appel au fichier csv.
 */

public class TextToTableConverter {
	int nbVilles;
	String nom_fichier_data;
	public static ArrayList<LigneTableau> matrice_distances;
	String[] liste_nom_villes;

	public TextToTableConverter(String chemin_fichier_data, int nbVilles) {
		this.nom_fichier_data = chemin_fichier_data; // On récupère le chemin du fichier duquel on veut récupérer les données pour faire tourner le Recuit.
		this.nbVilles = nbVilles;
		this.matrice_distances = new ArrayList<LigneTableau>();
	}

	public ArrayList<LigneTableau> getMatriceDistances() {
		String ville;
		
		try {
			// FileReader reads text files in the default encoding.
			FileReader fichier = new FileReader(this.nom_fichier_data);

			// Always wrap FileReader in BufferedReader.
			BufferedReader buffer = new BufferedReader(fichier);

			this.liste_nom_villes = Arrays.copyOfRange(buffer.readLine().split(","), 1, this.nbVilles +1); //On recupere les nbVille èmes premieres villes
			
			//on récupère les distances correspondantes
			String[] liste_distances;

			for (int n = 0; n < this.nbVilles; n++) {
				ville = this.liste_nom_villes[n]; // on récupère le nom de la n ième ville

				String ligne = buffer.readLine();
				liste_distances = Arrays.copyOfRange(ligne.split(","), 1, this.nbVilles + 1);

				double[] distances = new double[this.nbVilles];
				
				for (int i = 0; i < distances.length; i++) {
					distances[i] = Double.parseDouble(liste_distances[i]);
				}

				// ajouter l'element distance dans Matrice_distances associé à la clé ville
				this.matrice_distances.add(new LigneTableau(ville, distances));
				

			}
			

			// Always close files.
			buffer.close();
			
		} catch (FileNotFoundException ex) {
			System.out.println("Fichier non trouvé");
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return this.matrice_distances;
	}
}
