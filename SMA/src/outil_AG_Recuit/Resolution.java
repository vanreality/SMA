package outil_AG_Recuit;


import comportement.Algo_Recuit;

import java.io.IOException;
import java.util.ArrayList;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

/* C'est dans cette classe que se trouve la fonction main qui va r�soudre tout le probl�me.
 * Cette classe fait appel aux classes Algo_Recuit, Parametres et LectureBaseDeDonnees.
 * */
public class Resolution extends OneShotBehaviour {
	
	public void action() {
		String nom_fichier = "C:\\Users\\Camille\\Documents\\Centrale 2018-2019\\ICO\\Projet\\Base de donn�es obtenue avec le recuit.xlsx"; // Ceci est le fichier qui contiendra la base de donn�es.
		ArrayList<Parametres> matriceDesParametres; // On initialise la liste des param�tres qui seront utilis�s pour faire tourner le recuit.
		double[][] matriceDesResultats; // On initialise la matrice qui va contenir les r�sultats de recuit obtenus avec les diff�rents param�tres.

		LectureBaseDeDonnees lectureBaseDeDonnees = new LectureBaseDeDonnees(nom_fichier); // On va r�cup�rer la matrice des param�tres avec lesquels on va faire tourner le recuit.
		matriceDesParametres = lectureBaseDeDonnees.r�cup�rerParam�tres();
		matriceDesResultats = new double[matriceDesParametres.size()][4]; // On cr�e la matrice qui va contenir les r�sultats � �crire dans la base de donn�es excel.

		/*
		for (int ligne = 0; ligne < matriceDesParametres.size(); ligne++) {
			Algo_Recuit recuit = new Algo_Recuit(matriceDesParametres.get(ligne).nbVilles, matriceDesParametres.get(ligne).param1,
					matriceDesParametres.get(ligne).param2, matriceDesParametres.get(ligne).param3); // On fait tourner le recuit avec les quatre param�tres d�finis dans la ligne ligne de la matrice des param�tres.
		*/			
					
			Algo_Recuit recuit = new Algo_Recuit(500, 50, 100, 0.5);
		
			/*
			 * Ci-dessous, on remplit le fichier excel qui nous servira de base de donn�es pour la partie apprentissage.
			 
			
			matriceDesResultats[ligne][0] = recuit.getMin_fitness(); 
			matriceDesResultats[ligne][1] = recuit.getEcart();
			matriceDesResultats[ligne][2] = recuit.getMoyenne();
			matriceDesResultats[ligne][3] = recuit.getTemps_execution();
		}
		
		
		lectureBaseDeDonnees.ecrireResultats(matriceDesResultats); // Ecrit les r�sultats dans le fichier excel de base de donn�es
		*/
		
		
	}
	

}
// Cette classe va devenir le comportement de l'agent Recuit