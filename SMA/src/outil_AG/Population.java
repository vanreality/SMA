package outil_AG;

import java.util.ArrayList;
import java.util.List;

public class Population {

	private ArrayList<Circuit> circuits = new ArrayList<Circuit>();
	private int taillePopulation;

	public Population(GestionnaireCircuit gestionnaireCircuit, int taillePopulation) {
		this.taillePopulation = taillePopulation;
		for (int i=0; i<taillePopulation; i++) {
			this.circuits.add(new Circuit(gestionnaireCircuit));
		}
	}
	
	public Population(GestionnaireCircuit gestionnaireCircuit, int taillePopulation, ArrayList<Ville> c) {
		this.taillePopulation = taillePopulation;
		for (int i=0; i<taillePopulation; i++) {
			this.circuits.add(new Circuit(gestionnaireCircuit, c));
		}
	}
	
	/**
	 * Permet de placer le circuit c à l'emplacement index de la liste circuits
	 * @param index
	 * @param c
	 */
	public void sauvegarderCircuit(int index, Circuit c) {
		this.circuits.set(index, c);
	}
	
	public Circuit getCircuit(int index) {
		return this.circuits.get(index);
	}
	
	/**
	 * Determine le circuit le plus court parmi cette population
	 * @return
	 */
	public Circuit getFittest() {
		Circuit fittest = this.circuits.get(0);
		for (int i=0; i<this.taillePopulation; i++) {
			if (fittest.getFitness() <= this.getCircuit(i).getFitness()) {
				fittest = this.getCircuit(i);
			}
		}
		return fittest;
	}
	
	/**
	 * @return the circuits
	 */
	public List<Circuit> getCircuits() {
		return circuits;
	}

	/**
	 * @return the taillePopulation
	 */
	public int getTaillePopulation() {
		return taillePopulation;
	}
	
	
	
	
}
