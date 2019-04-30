package outil_Recuit;


import comportement.Algo_Recuit;

import java.io.IOException;
import java.util.ArrayList;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

/* C'est dans cette classe que se trouve la fonction main qui va résoudre tout le problème.
 * Cette classe fait appel aux classes Algo_Recuit, Parametres et LectureBaseDeDonnees.
 * */
public class Resolution extends OneShotBehaviour {
	
	public void action() {
		String nom_fichier = "C:\\Users\\Camille\\Documents\\Centrale 2018-2019\\ICO\\Projet\\Base de données obtenue avec le recuit.xlsx"; // Ceci est le fichier qui contiendra la base de données.
		ArrayList<Parametres> matriceDesParametres; // On initialise la liste des paramètres qui seront utilisés pour faire tourner le recuit.
		double[][] matriceDesResultats; // On initialise la matrice qui va contenir les résultats de recuit obtenus avec les différents paramètres.

		LectureBaseDeDonnees lectureBaseDeDonnees = new LectureBaseDeDonnees(nom_fichier); // On va récupérer la matrice des paramètres avec lesquels on va faire tourner le recuit.
		matriceDesParametres = lectureBaseDeDonnees.récupérerParamètres();
		matriceDesResultats = new double[matriceDesParametres.size()][4]; // On crée la matrice qui va contenir les résultats à écrire dans la base de données excel.

		/*
		for (int ligne = 0; ligne < matriceDesParametres.size(); ligne++) {
			Algo_Recuit recuit = new Algo_Recuit(matriceDesParametres.get(ligne).nbVilles, matriceDesParametres.get(ligne).param1,
					matriceDesParametres.get(ligne).param2, matriceDesParametres.get(ligne).param3); // On fait tourner le recuit avec les quatre paramètres définis dans la ligne ligne de la matrice des paramètres.
		*/			
					
			Algo_Recuit recuit = new Algo_Recuit(500, 50, 100, 0.5);
		
			/*
			 * Ci-dessous, on remplit le fichier excel qui nous servira de base de données pour la partie apprentissage.
			 
			
			matriceDesResultats[ligne][0] = recuit.getMin_fitness(); 
			matriceDesResultats[ligne][1] = recuit.getEcart();
			matriceDesResultats[ligne][2] = recuit.getMoyenne();
			matriceDesResultats[ligne][3] = recuit.getTemps_execution();
		}
		
		
		lectureBaseDeDonnees.ecrireResultats(matriceDesResultats); // Ecrit les résultats dans le fichier excel de base de données
		*/
		
		
	}
	

}
// Cette classe va devenir le comportement de l'agent Recuit