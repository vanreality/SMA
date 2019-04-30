package outil_Tabou;

import java.io.IOException;

import autre.BaseDeDonneesIA;

public class Voyage {
	
	public int [][] matriceVoyage;
	public Ville [] villesOrdonneesParId;
	public int tailleVoyage;
	
	// CREATION D'UN VOYAGE DE TAILLE N A PARTIR DES DONNEES
	
	public Voyage(Donnees donnees, int tailleVoyage) {
		this.tailleVoyage=tailleVoyage;
		
		// CREATION DU TABLEAU DES VILLES A PARTIR DES VILLES DES DONNEES ET DE LA TAILLE DU VOYAGE
		
		this.villesOrdonneesParId=new Ville [tailleVoyage];
		
		for (int i=0; i<tailleVoyage;i++) {
			this.villesOrdonneesParId[i]=donnees.villesDonneesOrdonneesParId[i];
		}
				
		// CREATION DE LA MATRICE DE VOYAGE A PARTIR DE LA MATRICE DES DONNEES ET DE LA TAILLE DU VOYAGE

		this.matriceVoyage=new int [tailleVoyage][tailleVoyage];

		for (int i=0; i<tailleVoyage;i++) {
			for (int j=i;j<tailleVoyage;j++) {
				int a = donnees.matriceDonnees[i][j];
				this.matriceVoyage[i][j]=a;
				this.matriceVoyage[j][i]=a;
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		

		 Donnees donnees=new Donnees();
		 
		 Voyage Voyage1=new Voyage(donnees,5);
		 
		 System.out.println(Voyage1.villesOrdonneesParId[4]);

		 
		 System.out.println(Voyage1.matriceVoyage[4][3]);


		 System.out.println(Voyage1.matriceVoyage[3][2]);

		 
		 System.out.println(Voyage1.matriceVoyage[4][2]);
		
	}
}




	



