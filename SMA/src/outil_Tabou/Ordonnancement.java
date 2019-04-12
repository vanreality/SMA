package outil_Tabou;

import java.util.ArrayList;
import java.util.Random;

public class Ordonnancement {

	public Ville [] ordo;
	public int cout;
	public Voyage voyage;	
	
	public Ordonnancement(Ville [] villes, Voyage voyage) {
		this.ordo=villes;
		this.voyage=voyage;
		this.cout=0;
	}
	
	public Ordonnancement(Ordonnancement ordo) {
		this.ordo=new Ville[ordo.ordo.length];
		for (int i=0; i<ordo.ordo.length;i++) {
			this.ordo[i]=ordo.ordo[i];
		}
		this.voyage=ordo.voyage;
		this.cout=ordo.cout;
	}

	public Ville[] getOrdo() {
		return ordo;
	}

	/******************************************************************************/
	/*                            CALCUL DU COUT                                  */
	/******************************************************************************/
		
	public int calculCout() {
		this.cout=0;
		for (int i=0; i<this.ordo.length-1;i++) {
			this.cout+=this.voyage.matriceVoyage[this.ordo[i].id][this.ordo[i+1].id];
			}
		cout+=this.voyage.matriceVoyage[this.ordo[this.ordo.length-1].id][this.ordo[0].id];
		return this.cout;
	}
	
	/******************************************************************************/
	/*                      FONCTION D'ECHANGE                                   */
	/******************************************************************************/
	
	public void echange(int pos1, int pos2)
	{
		Ville villeProvisoire = this.ordo[pos1];
		this.ordo[pos1] = this.ordo[pos2];
		this.ordo[pos2] = villeProvisoire;
	}
	
	/******************************************************************************/
	/*                      FONCTION DE MELANGE                                   */
	/******************************************************************************/
	
	public void melange(int nbEchange)
	{
		int len = this.ordo.length;
		Random random = new Random();
		
		for(int k = 0 ; k < nbEchange ; k++)
		{
			int ville1 = random.nextInt(len-1)+1;
			int ville2 = random.nextInt(len-1)+1;
			this.echange(ville1, ville2);
		}
	}

	/******************************************************************************/
	/*                      ETAPE 1 : ALGORITHME VOISINS                          */
	/******************************************************************************/
	
	public boolean voisinParDescenteSimple()
	{
		int coutDebut = this.calculCout();
		int coutInter;
		ArrayList<Integer> listeEchange = new ArrayList<Integer>();
		for(int VilleI = 1 ; VilleI < this.ordo.length - 1 ; VilleI++)
		{
			this.echange(VilleI, VilleI + 1);
			coutInter = this.calculCout();
			if( coutInter < coutDebut )
			{
				listeEchange.add(VilleI);
			}
			this.echange(VilleI, VilleI + 1);
		}
	//	System.out.println(listeEchange);
		if(listeEchange.isEmpty())
		{
			return false;
		}
		else 
		{
			int villeAuHasard = listeEchange.get( (int) (Math.random() * listeEchange.size()) );
		//	System.out.println("hasard = " + tacheAuHasard);
			this.echange(villeAuHasard, villeAuHasard + 1);
			return true;
		}
	}
	
	public boolean voisinParDescenteHasard()
	{
		int coutDebut = this.calculCout();
		int coutInter;
		ArrayList<int[]> listeEchange = new ArrayList<int[]>();
		int len = this.ordo.length;
		Random random = new Random();
		
		for(int k = 0 ; k < 50 ; k++) // this.ordonnancement.length - 1 
		{
			int ville1 = random.nextInt(len-1)+1;
			int ville2 = random.nextInt(len-1)+1;
			this.echange(ville1, ville2);
			coutInter = this.calculCout();
			if( coutInter < coutDebut )
			{
				listeEchange.add( new int [] {ville1, ville2} );
			}
			this.echange(ville1, ville2);
		}
		
		//for(int i = 0 ; i < listeEchange.size() ; i++ ) { System.out.print( "(" + listeEchange.get(i)[0] + ", " + listeEchange.get(i)[1] + "), " ) ; } System.out.println();
		
		if(listeEchange.isEmpty())
		{
			return false;
		}
		else 
		{
			int villeAuHasard1 = listeEchange.get( (int) (Math.random() * listeEchange.size()) )[0];
			int villeAuHasard2 = listeEchange.get( (int) (Math.random() * listeEchange.size()) )[1];
			//System.out.println("hasard = (" + villeAuHasard1 + ", " + villeAuHasard2 + ")\n");
			this.echange(villeAuHasard1, villeAuHasard2 );
			return true;
		}
	}
	
	public void meilleurOrdonnancementVoisinage(int rechercheMax, int methodeRecherche)
	{
		int n = 0;
		boolean voisinMeilleurTrouve = true;
		while( voisinMeilleurTrouve && n < rechercheMax )
		{
			switch(methodeRecherche)
			{
			case 1 : voisinMeilleurTrouve = this.voisinParDescenteSimple(); break;
			case 2 : voisinMeilleurTrouve = this.voisinParDescenteHasard(); break;
			default : break;
			}
			
			n++;
		}

	}
	
	/******************************************************************************/
	/*                      ETAPE 2 : ALGORITHMES TABOU                            */
	/******************************************************************************/
		
	public Ordonnancement Tabou1(int nbmax, int longueurTabou,int nbMaxVoisinsParIter) {
		
		/************************************************INITIALISATIONS ***************************************************/
		
		int len=this.ordo.length;
		
		int nbIter=0;

		Ordonnancement meilleureSol=new Ordonnancement(this);
		int meilleurIter=0;

		int [][] listeTabou=new int [longueurTabou][2];
		int listeTabouIncr=0;
		
		// ICI, INITIALISER LA FONCTION D'ASPIRATION A
		
		ArrayList<Voisin> listeVoisins = new ArrayList<Voisin>();
		
		Random random=new Random();
				
		/******************************************** PROCESSUS ITERATIF ****************************************************/
		
		while (nbIter-meilleurIter<nbmax) {
			nbIter+=1;
			
			/*
			System.out.println("début de la boucle "+nbIter);
			System.out.println("La meilleure solution au début de cette boucle a été obtenue à l'itération "+meilleurIter+" et est la suivante:");
			System.out.print(meilleureSol);
			
			//DEBUG
			System.out.print("L'ordonnancement courant est:");
			System.out.print(this);
			*/
			
			/********************************* GENERATION DE L'ENSEMBLE DES SOLUTIONS VOISINES********************************/
			
			for(int k = 0 ; k < nbMaxVoisinsParIter ; k++) // this.ordonnancement.length - 1 
			{
				
				int ville1 = random.nextInt(len-1)+1;
				int ville2= ville1;
				while (ville2==ville1) {
					ville2 = random.nextInt(len-1)+1;
				}
				Voisin voisin=new Voisin(this);
				
				// échange des deux villes et mémorisation de ce mouvement dans le voisin 
				
				voisin.echange(ville1, ville2);
				voisin.setMouvement(ville1, ville2);
				
				// le couple (ville1, ville2) est-il dans la liste tabou? si oui , on l'ajoute à la liste des voisins
				
				if (!voisin.MvtDsTabou(ville1,ville2,listeTabou)) {
					listeVoisins.add(voisin);
				}
												
			}
			
			/*
			System.out.println("--------------------------------------------------------------------------------");

			for (int i=0;i<listeVoisins.size();i++) {
				System.out.println(listeVoisins.get(i));
			}
			
			System.out.println("--------------------------------------------------------------------------------");
			*/
			
			/********************************* CHOIX DE LA MEILLEURE SOLUTION ********************************/
			
			// on calcule la meilleure solution des voisins pas dans tabou. 
			
			int meilleurVoisin=0;
			
			if (!listeVoisins.isEmpty()) {

				int meilleurCoutCourant= listeVoisins.get(meilleurVoisin).calculCout();  // VERIFIER SI LA LISTE N'EST PAS VIDE
				
				for (int i=1; i<listeVoisins.size();i++) {
					int coutTemp= listeVoisins.get(i).calculCout();
					if (coutTemp<meilleurCoutCourant) {
						meilleurCoutCourant=coutTemp;
						meilleurVoisin=i;
					}
				}
				
				/*
				//DEBUG
				System.out.print("Le meilleur voisin courant est:");
				System.out.print(listeVoisins.get(meilleurVoisin));
					*/
					
				/********************************* MISE A JOUR DE LA LISTE TABOU ********************************/
				
				listeTabou[listeTabouIncr]=listeVoisins.get(meilleurVoisin).getMouvement();
				
				/*
				// DEBUG
				
				String s=new String("Liste Tabou: ((");
				for (int i=0;i<listeTabou.length;i++) {s+=listeTabou[i][0]+" , "+listeTabou[i][1]+"), ";}
				s+=")";
				System.out.println(s);
				*/
				
				// Si on est à la fin de la liste tabou, on revient au début. Sinon, on écrit après.
				
				if (listeTabouIncr==longueurTabou-1){
					listeTabouIncr=0;
				}
				else listeTabouIncr++;

					
				/********************************* MISE A JOUR DE LA MEILLEURE SOLUTION ********************************/
				
				// L'ordonnancement courant devient le meilleur voisin courant
				
				this.echange(listeVoisins.get(meilleurVoisin).getMouvement()[0], listeVoisins.get(meilleurVoisin).getMouvement()[1]);

				// Si ce meilleur voisin courant est la meilleure solution jamais rencontrée, on la sauvegarde
				
				if (meilleurCoutCourant<meilleureSol.calculCout()) {
					for (int i=0; i<meilleureSol.ordo.length;i++) {
						meilleureSol.getOrdo()[i]=this.getOrdo()[i];
					}
					meilleurIter=nbIter;
					}
				
				listeVoisins.clear();
				
				/*
				System.out.print("La meilleure solution à la fin de cette boucle est:");
				System.out.print(meilleureSol);
				System.out.print("fin de la boucle\n\n");
				*/
				
			}
			
		}
		System.out.println("Meilleure solution trouvée lors de l'iteration "+meilleurIter+"");				
		return meilleureSol;
	}
	
	public Ordonnancement Tabou2(int nbmax, int longueurTabou,int nbMaxVoisinsParIter) {
		
		/************************************************INITIALISATIONS ***************************************************/
		
		int len=this.ordo.length;
		
		int nbIter=0;

		Ordonnancement meilleureSol=new Ordonnancement(this);
		int meilleurIter=0;

		int [][] listeTabou=new int [longueurTabou][2];
		int listeTabouIncr=0;
		
		// ICI, INITIALISER LA FONCTION D'ASPIRATION A
		
		ArrayList<Voisin> listeVoisinsPasTabou = new ArrayList<Voisin>();
		ArrayList<Voisin> listeVoisinsTabou= new ArrayList<Voisin>();
		
		
		Random random=new Random();
				
		/******************************************** PROCESSUS ITERATIF ****************************************************/
		
		while (nbIter-meilleurIter<nbmax) {
			nbIter+=1;
			
			/*
			System.out.println("début de la boucle "+nbIter);
			System.out.println("La meilleure solution au début de cette boucle a été obtenue à l'itération "+meilleurIter+" et est la suivante:");
			System.out.print(meilleureSol);
			
			//DEBUG
			System.out.print("L'ordonnancement courant est:");
			System.out.print(this);
			*/
			
			/********************************* GENERATION DE L'ENSEMBLE DES SOLUTIONS VOISINES********************************/
			
			for(int k = 0 ; k < nbMaxVoisinsParIter ; k++) // this.ordonnancement.length - 1 
			{
				
				int ville1 = random.nextInt(len-1)+1;
				int ville2= ville1;
				while (ville2==ville1) {
					ville2 = random.nextInt(len-1)+1;
				}
				Voisin voisin=new Voisin(this);
				
				// échange des deux villes et mémorisation de ce mouvement dans le voisin 
				
				voisin.echange(ville1, ville2);
				voisin.setMouvement(ville1, ville2);
				
				// le couple (ville1, ville2) est-il dans la liste tabou? on ajoute la voisin à la liste des voisins pas tabou ou à la liste des voisins tabou
				
				if (!voisin.MvtDsTabou(ville1,ville2,listeTabou)) {
					listeVoisinsPasTabou.add(voisin);
				}
				
				if (voisin.MvtDsTabou(ville1, ville2, listeTabou)) {
					listeVoisinsTabou.add(voisin);
				}
												
			}
			
			/*********************************  MEILLEURE SOLUTION PAS DANS TABOU ********************************/	
			
			int meilleurCoutCourant=0;
			
			// on calcule la meilleure solution des voisins pas dans tabou. 
			
			int meilleurVoisinPasTabou=0;
			
			if (!listeVoisinsPasTabou.isEmpty()) {
				
				int meilleurCoutCourantPasTabou= listeVoisinsPasTabou.get(meilleurVoisinPasTabou).calculCout();  // VERIFIER SI LA LISTE N'EST PAS VIDE
				
				for (int i=1; i<listeVoisinsPasTabou.size();i++) {
					int coutTemp= listeVoisinsPasTabou.get(i).calculCout();
					if (coutTemp<meilleurCoutCourantPasTabou) {
						meilleurCoutCourantPasTabou=coutTemp;
						meilleurVoisinPasTabou=i;
					}
				}
				
				meilleurCoutCourant=meilleurCoutCourantPasTabou;
				
				/*
				//DEBUG
				System.out.print("Le meilleur voisin courant pas dans Tabou  est:");
				System.out.print(listeVoisinsPasTabou.get(meilleurVoisinPasTabou));
				*/
					
				/*********************************  MEILLEURE SOLUTION DANS TABOU ********************************/

				int meilleurVoisinTabou=-1;
				
				// Si on n'a jamais rencontré de solution aussi bien que celle donnée par un voisin Tabou, on lève le Tabou
				
				for (int i=1; i<listeVoisinsTabou.size();i++) {
					int coutTemp= listeVoisinsTabou.get(i).calculCout();
					if (coutTemp<meilleureSol.calculCout()) {
						meilleurCoutCourant=coutTemp;
						meilleurVoisinTabou=i;
					}
				}
				
				/********************* MEILLEURE SOL COURANTE PAS TABOU: MISE A JOUR DE LA LISTE TABOU ET DE L'ORDO COURANTE********************************/

				if (meilleurVoisinTabou==-1) {
					
					// MISE A JOUR LISTE TABOU
					
					listeTabou[listeTabouIncr]=listeVoisinsPasTabou.get(meilleurVoisinPasTabou).getMouvement();

					/*
					// DEBUG
					
					String s=new String("Liste Tabou: ((");
					for (int i=0;i<listeTabou.length;i++) {s+=listeTabou[i][0]+" , "+listeTabou[i][1]+"), ";}
					s+=")";
					System.out.println(s);
					*/
					
					// MISE A JOUR LISTE TABOU INCR

					if (listeTabouIncr==longueurTabou-1){
						listeTabouIncr=0;
					}
					else listeTabouIncr++;
					
					//MISE A JOUR ORDO COURANT
					
					this.echange(listeVoisinsPasTabou.get(meilleurVoisinPasTabou).getMouvement()[0], listeVoisinsPasTabou.get(meilleurVoisinPasTabou).getMouvement()[1]);
					
				}
				
				/********************* MEILLEURE SOL COURANTE TABOU: MISE A JOUR DE L'ORDO COURANTE********************************/

				else{
					
					/*
					//DEBUG
					System.out.print("Le meilleur voisin courant dans Tabou  est:");
					System.out.print(listeVoisinsTabou.get(meilleurVoisinTabou));
					*/
					
					this.echange(listeVoisinsTabou.get(meilleurVoisinTabou).getMouvement()[0], listeVoisinsTabou.get(meilleurVoisinTabou).getMouvement()[1]);
					
				}
					
				/********************************* MISE A JOUR DE LA MEILLEURE SOLUTION ********************************/
				
				// Si le meilleur voisin courant (Tabou ou non) est la meilleure solution jamais rencontrée, on la sauvegarde
				
				if (meilleurCoutCourant<meilleureSol.calculCout()) {
					for (int i=0; i<meilleureSol.ordo.length;i++) {
						meilleureSol.getOrdo()[i]=this.getOrdo()[i];
					}
					meilleurIter=nbIter;
					}
				
				listeVoisinsTabou.clear();
				listeVoisinsPasTabou.clear();
				
				/*
				System.out.println(this);
				
				System.out.print("La meilleure solution à la fin de cette boucle est:");
				System.out.print(meilleureSol);
				System.out.print("fin de la boucle\n\n");
				*/
			}
		
		}
		System.out.println("Meilleure solution trouvée lors de l'iteration "+meilleurIter+"");						
		return meilleureSol;
	}
	

	/******************************************************************************/
	/*                             AFFICHAGE                                      */
	/******************************************************************************/
	
	
	@Override
	public String toString() {
    	String s=new String ("Cet ordonnancement est le suivant: {");
    	for (int i=0; i<this.ordo.length-1;i++) {
    		s+=this.ordo[i].nom+", ";
    	}
    	s+=this.ordo[this.ordo.length-1].nom+"} \n Le coût de cet ordonnancement est égal à "+this.calculCout()+"\n";
		return s;
    }
	
	
	/******************************************************************************/
	/*                             FONCTION MAIN                                  */
	/******************************************************************************/
		
	public static void main(String[] args) {
		
		 Donnees donnees=new Donnees();
		 
		 Voyage Voyage1=new Voyage(donnees,50);
		 
		 Ordonnancement ordo1= new Ordonnancement(Voyage1.villesOrdonneesParId, Voyage1);
		 
		 System.out.println("Ordonnancement initial:");
		 
		 System.out.println(ordo1);
		 
		 System.out.println("Utilisation de l'algorithme Tabou 1");
		 
		 System.out.println(ordo1.Tabou1(10, 3, 10));		 
		 		 
		 System.out.println("Utilisation de l'algorithme Tabou 1 amélioré");
		 
		 System.out.println(ordo1.Tabou2(10, 3, 10));
		 
		 // ALGORITHMES VOISINS
		 
		 /*Voyage1.setVilleDeDepart(B);

		 Ordonnancement ordo1= new Ordonnancement(Voyage1.villesOrdonneesParId, Voyage1);

	     System.out.print(ordo1);
	     
	     ordo1.melange(20);
	     
	     System.out.print(ordo1);

	     ordo1.meilleurOrdonnancementVoisinage(100, 2);

		 System.out.print(ordo1);
		 
		 */
		 
		 // ALGORITHME TABOU
		 
		 
	}
	
}


