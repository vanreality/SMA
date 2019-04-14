package outil_Tabou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Donnees {
    
	public int [][] matriceDonnees;
	public Ville [] villesDonneesOrdonneesParId;
	
	public Donnees() {
		File file= new File ("./donnees/data.csv");
						
		this.villesDonneesOrdonneesParId=this.constructionListeVilles(file);

		this.matriceDonnees=this.constructionMatriceDonnees(file);
	}
			
	public Ville[] constructionListeVilles(File file) {

	        String[] nomsVillesData = null;
	        String[] nomsVilles=null;
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
			
			nomsVilles= new String [nomsVillesData.length];
	        for (int i=1; i<nomsVillesData.length;i++) {
	        	nomsVilles[i-1]=nomsVillesData[i];
	  
	        }
	        
	        Ville[] listeVilles=new Ville[nomsVilles.length];
	        
			for (int i=0;i<nomsVilles.length;i++) {
				listeVilles[i]=new Ville(nomsVilles[i]);
			}
	        
			return listeVilles;
	    }
	
	public int[][] constructionMatriceDonnees(File file) {


	        String sep = new Character(',').toString();
	        FileReader fr;
	        
	        
			try {
				fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
				int len=br.readLine().split(sep).length-1; // On enl�ve la premi�re ligne qui contient les noms des villes
				
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
    
	public static void main(String[] args) {
    	
    	Donnees donnees=new Donnees();
    	
    	System.out.println(donnees.villesDonneesOrdonneesParId[300]);
    
    	System.out.println(donnees.matriceDonnees[499][498]);
    	
    }
}
