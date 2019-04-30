package outil_Recuit;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Trajet2 {
	private List<Ville2> etapes = new ArrayList<Ville2>();
	private double distanceTotale;
	private int nbVilles;
	
	public Trajet2(Ville2[] carte) {      //create new random trip
		this.nbVilles = carte.length;
		for(int i=0;i<nbVilles;i++) {
			etapes.add(new Ville2(carte[i]));
		}
		//Collections.shuffle(etapes); //On a une liste d'étapes de villes aléatoires

		Ville2 temp; //La première étape est toujours la première étape du fichier texte
		for(int j = 0;j<etapes.size();j++) {
			if(etapes.get(j).getName()==carte[0].getName()) {
				temp = etapes.get(j);
				etapes.remove(j);
				etapes.add(0, temp);
			}
		}
		this.calculateDistanceTotale();
	}
	
	public Trajet2(Trajet2 T) {
		this.etapes = new ArrayList<Ville2>(T.getEtapes());
		this.distanceTotale = T.getDistanceTotale();
		this.nbVilles = T.getnbVilles();
	}
	
	public Trajet2(String string_id, ArrayList<LigneTableau> matriceDesVilles) {
		List<String> liste_id_ville_string = Arrays.asList(string_id.substring(1, string_id.length()-1).split(","));
		List<Integer> liste_id_ville = new ArrayList<Integer>();
		List<Ville2> liste_objet_ville = new ArrayList<Ville2>();
		
		this.nbVilles=liste_id_ville_string.size()-1;
		
		for(int i = 0; i<liste_id_ville_string.size();i++) {
			liste_id_ville.add(Integer.parseInt(liste_id_ville_string.get(i)));
		}
		
		
		for(int f=0;f<liste_id_ville.size()-1;f++) {
			Ville2 villetemp = new Ville2(matriceDesVilles.get(liste_id_ville.get(f)).ville,matriceDesVilles.get(liste_id_ville.get(f)).distances,liste_id_ville.get(f));
			liste_objet_ville.add(villetemp);
		}
		this.etapes = new ArrayList<>(liste_objet_ville);
		this.calculateDistanceTotale();
	}
	
	private void calculateDistanceTotale() {
		this.distanceTotale = 0;

		for(int i=0;i<nbVilles-1;i++) {
			this.distanceTotale=this.distanceTotale + etapes.get(i).findDistance(etapes.get(i+1).id);
		}

		this.distanceTotale+=etapes.get(0).findDistance(etapes.get(nbVilles-1).id);	

	}
	
	//Getters
	public double getDistanceTotale() {
		return this.distanceTotale;
	}
	public int getnbVilles() {
		return this.nbVilles;
	}
	public List<Ville2> getEtapes() {
		return this.etapes;
	}
	
	
	public String toString1() {
		String s="";
		for(Ville2 v : etapes) {
			s+= v.getName()+", ";
		}
		return "Le chemin est : "+s+etapes.get(0).getName()+"      La distance totale est : "+this.distanceTotale +" km";
	}
	
	public List<Ville2> getTrajet2() {
		List<Ville2> trajet = new ArrayList<Ville2>();
		for (int i = 0; i < this.etapes.size(); i++) {
			trajet.add(this.etapes.get(i));
		}
		trajet.add(this.etapes.get(0));
		return trajet;
	}
	public List<Integer> getTrajet2Id(){
		List<Integer> trajet = new ArrayList<Integer>();
		for (int i = 0; i < this.etapes.size(); i++) {
			trajet.add(this.etapes.get(i).id);
		}
		trajet.add(this.etapes.get(0).id);
		return trajet;
	}


	public String toString() {
		String s="";
		for(Ville2 v : etapes) {
			s+= v.getName()+", ";
		}
		return "Le chemin est : "+s+etapes.get(0).getName()+"      La distance totale est : "+this.distanceTotale;
	}
	
	public List<Ville2> getTrajet() {
		List<Ville2> trajet = new ArrayList<Ville2>();
		for (int i = 0; i < this.etapes.size(); i++) {
			trajet.add(this.etapes.get(i));
		}
		trajet.add(this.etapes.get(0));
		return trajet;
	}
	public List<Integer> getTrajetId(){
		List<Integer> trajet = new ArrayList<Integer>();
		for (int i = 0; i < this.etapes.size(); i++) {
			trajet.add(this.etapes.get(i).id);
		}
		trajet.add(this.etapes.get(0).id);
		return trajet;
	}
	


}