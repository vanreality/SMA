package comportement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Trajet implements Comparable {
	private List<Ville> etapes = new ArrayList<Ville>();
	private int fitness;
	private int nbVilles;
	
	
	public Trajet(Ville[] carte) {      //create new random trip
		this.nbVilles = carte.length;
		Ville temp;
		for(int i=0;i<nbVilles;i++) {
			etapes.add(new Ville(carte[i]));
		}
		Collections.shuffle(etapes);
		for(int j = 0;j<etapes.size();j++) {
			if(etapes.get(j).getName()=="Bordeaux") {
				temp = etapes.get(j);
				etapes.remove(j);
				etapes.add(0, temp);
			}
		}
		this.calculateFitness();
	}
	
	
	public Trajet(Trajet t1, Trajet t2) {
		List<Ville> inter = new ArrayList<Ville>();		
		for(int i = 0; i<t1.getnbVilles();i++) {
			inter.add(new Ville(t1.getEtapes().get(i)));
			inter.add(new Ville(t2.getEtapes().get(i)));
		}

		boolean present=false;
		int j;
		for(int i =0; i<inter.size();i++) {
			for(j =0; j<this.etapes.size();j++) {
				if(this.etapes.get(j).getName()==inter.get(i).getName())
					present=true;
			}
			if(present == false)
				this.etapes.add(inter.get(i));
			present=false;
			
		}
		
		this.nbVilles = this.etapes.size();
		this.calculateFitness();
	}
	public void calculateFitness() {
		this.fitness = 0;
		for(int i=0;i<nbVilles-1;i++) {
			this.fitness+=etapes.get(i).getDistance(etapes.get(i+1).getName());
		}
		this.fitness+=etapes.get(0).getDistance(etapes.get(etapes.size()-1).getName());
	}
	public int getFitness() {
		return this.fitness;
	}
	public int getnbVilles() {
		return this.nbVilles;
	}
	public List<Ville> getEtapes() {
		return this.etapes;
	}
	public String toString() {
		String s="";
		for(Ville v : etapes) {
			s+= v.getName()+", ";
		}
			
		return s+etapes.get(0).getName()+"  "+this.fitness;

	}
	@Override
	public int compareTo(Object arg0) {
		
		return ((Trajet)arg0).getFitness()-this.fitness;
	}
	public Trajet Reproduction(Trajet tr) {
		Trajet enfant = new Trajet(this, tr);
		
		return enfant;
	}
	public void echange(int nb1, int nb2) {
		Collections.swap(this.etapes, nb1, nb2);
		this.calculateFitness();
	}
}