package outil_AG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireCircuit {

	private ArrayList<Ville> villesDestinations = new ArrayList<Ville>();
	public int [][] matriceDonnees;
	public String [] villesDonneesOrdonneesParId;
	
	public GestionnaireCircuit() {
		super();
		File file= new File ("./donnees/data.csv");
		this.villesDonneesOrdonneesParId=this.constructionListeVilles(file);
		this.matriceDonnees=this.constructionMatriceDonnees(file);
	}

	public void ajouterVille (Ville v){
		this.villesDestinations.add(v);
	}
	
	public Ville getVille(int index) {
		return this.villesDestinations.get(index);
	}
	
	public int nombreVilles() {
		return this.villesDestinations.size();
	}

	/**
	 * @return the villesDestinations
	 */
	public ArrayList<Ville> getVillesDestinations() {
		return villesDestinations;
	}
	
	/*
	 * Fonction qui g�n�re la liste de nom des villes avec comme ordonnancement :
	 * la premi�re ville de la liste a l'id 0, la deuxieme l'id 1 etc.
	 */
	public String[] constructionListeVilles(File file) {

        String[] nomsVillesData = null;
        String[] nomsVilles=null;
        @SuppressWarnings("deprecation")
		String sep = new Character(',').toString();
        FileReader fr;
        
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
	        nomsVillesData=br.readLine().split(sep);
	        
	        
	        br.close();
	        fr.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int len = nomsVillesData.length;
		nomsVilles= new String [len];
        for (int i=1; i<len;i++) {
        	nomsVilles[i-1]=nomsVillesData[i];
  
        }
        
        
		return nomsVilles;
    }

	/*
	 * Fonction qui g�n�re la matrice de donn�es des distances entre les villes avec leur id
	 */
	public int[][] constructionMatriceDonnees(File file) {
	
	
	        @SuppressWarnings("deprecation")
			String sep = new Character(',').toString();
	        FileReader fr;
	        
	        
			try {
				fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
				int len = br.readLine().split(sep).length-1; // On enl�ve la premi�re ligne qui contient les noms des villes
				
		        int[][] matriceDonnees=new int[len][len];
				
		        
				for (int i=0;i<len;i++) { // On va parcourir chaque ligne du fichier scv contenant des donnees
					String[] tableauTemp=br.readLine().split(sep); // on cree un tableau temporaire contenant la liste des donnees sous forme de string
					for (int k=0;k<len;k++) {
						matriceDonnees[i][k]=(int)Float.parseFloat(tableauTemp[k+1]); // on convertit les elements de tableauTemp en int qu'on ajoute � la matrice
					}
				}
			
		        br.close();
		        fr.close();
				return matriceDonnees;
	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return matriceDonnees;
	       
	}
}
