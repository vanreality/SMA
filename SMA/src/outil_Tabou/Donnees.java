package outil_Tabou;

public class Donnees {

	public int [][] matriceDonnees;
	public Ville [] villesDonneesOrdonneesParId;
	
	public Donnees() {
		/************************* ICI AJOUTER LES VILLES *****************/ // La ville de départ est la première ville ci-dessous.
		
		for (int i=0;i<500;i++) {
			villesDonneesOrdonneesParId[i]=new Ville("");
		}
		
		
		 
		Ville Bordeaux= new Ville("Bordeaux"); // VILLE DE DEPART
		 Ville Lyon= new Ville("Lyon");
		 Ville Nantes=new Ville("Nantes");
		 Ville Paris= new Ville("Paris");
		 Ville Marseille=new Ville("Marseille");
		 Ville Dijon= new Ville("Dijon");
		
		/******************************************************************/
		
		 this.villesDonneesOrdonneesParId= new Ville [] {Bordeaux, Lyon, Nantes,Paris, Marseille, Dijon};
		
		this.matriceDonnees= new int [][] {
	 		{0,780,320,580,480,660},
	 		{780,0,700,460,300,200},
	 		{320,700,0,380,820,630},
	 		{580,460,380,0,750,310},
	 		{480,300,820,750,0,500},
	 		{660,200,630,310,500,0},
		 };
	}
	
}

