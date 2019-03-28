package outil_AG_Recuit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Population {
	private List<Trajet> population = new ArrayList<Trajet>();
	private int i = 0;
	
	
	public Population(int n, Ville[] carte) {
		for (i = 0;i<n;i++) {
			population.add(new Trajet(carte));
		}
	}
	public void tri() {
		Collections.sort(this.population);
	}
	public void Selection() {
		this.tri();
		for(int i=0;i<this.population.size()/2;i++) {
			this.population.remove(0);
		}
	}
	public void Reproduction(int n) {
		Trajet a;
		Trajet b;
		while(population.size()<n) {
			a = population.get(new Random().nextInt(population.size()));
			b = population.get(new Random().nextInt(population.size()));
			population.add(new Trajet(a,b));
		}
	}
	public void Mutation(double p) { // remplacer par private Trajet
		int nb1, nb2;
		for(int i =0;i<this.population.size();i++) {
			if(Math.random()<p) {
				nb1 = ThreadLocalRandom.current().nextInt(1, 6);
				nb2 = ThreadLocalRandom.current().nextInt(1, 6);
				this.population.get(i).echange(nb1,nb2);
			}
		}

	}
	public String toString() {
		String s = "";
		for (Trajet t : population) {
			s+= t.toString() + "  \n";
		}
		return s;
	}
	public String best() {
		return population.get(population.size()-1).toString();
	}
}
