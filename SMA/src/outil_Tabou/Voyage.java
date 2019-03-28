package outil_Tabou;

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

		for (int i=0; i<tailleVoyage-1;i++) {
			for (int j=i;j<tailleVoyage;j++) {
				int a = donnees.matriceDonnees[i][j];
				this.matriceVoyage[i][j]=a;
				this.matriceVoyage[j][i]=a;
			}
		}
	}
	
}




	



