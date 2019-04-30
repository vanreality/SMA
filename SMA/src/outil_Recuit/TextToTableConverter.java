package outil_Recuit;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Cette classe permet de r�cup�rer les donn�es du fichier csv (qui contient les villes, leurs Id et la matrice des distances) et de cr�er une matrice_distances plus simple d'utilisation.
 * En effet, une fois la matrice_distances cr��e, on n'a plus besoin de faire appel au fichier csv.
 */

public class TextToTableConverter {
	int nbVilles;
	String nom_fichier_data;
	public static ArrayList<LigneTableau> matrice_distances;
	String[] liste_nom_villes;

	public TextToTableConverter(String chemin_fichier_data, int nbVilles) {
		this.nom_fichier_data = chemin_fichier_data; // On r�cup�re le chemin du fichier duquel on veut r�cup�rer les donn�es pour faire tourner le Recuit.
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

			this.liste_nom_villes = Arrays.copyOfRange(buffer.readLine().split(","), 1, this.nbVilles +1); //On recupere les nbVille �mes premieres villes
			
			//on r�cup�re les distances correspondantes
			String[] liste_distances;

			for (int n = 0; n < this.nbVilles; n++) {
				ville = this.liste_nom_villes[n]; // on r�cup�re le nom de la n i�me ville

				String ligne = buffer.readLine();
				liste_distances = Arrays.copyOfRange(ligne.split(","), 1, this.nbVilles + 1);

				double[] distances = new double[this.nbVilles];
				
				for (int i = 0; i < distances.length; i++) {
					distances[i] = Double.parseDouble(liste_distances[i]);
				}

				// ajouter l'element distance dans Matrice_distances associ� � la cl� ville
				this.matrice_distances.add(new LigneTableau(ville, distances));
				

			}
			

			// Always close files.
			buffer.close();
			
		} catch (FileNotFoundException ex) {
			System.out.println("Fichier non trouv�");
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return this.matrice_distances;
	}
}
