package outil_AG;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Circuit {

	private GestionnaireCircuit gestionnaireCircuit;
	private ArrayList<Ville> circuit = new ArrayList<Ville>();
	private double fitness;
	private double distance;
	private int tailleCircuit;
	
	/**
	 * Constructeur qui génère un circuit aléatoire
	 * @param gestionnaireCircuit
	 */
	public Circuit(GestionnaireCircuit gestionnaireCircuit) {
		this.gestionnaireCircuit = gestionnaireCircuit;
		this.circuit = (ArrayList<Ville>) gestionnaireCircuit.getVillesDestinations().clone();
		ArrayList<Ville> c1 = new ArrayList<Ville>();
		c1.add(gestionnaireCircuit.getVille(0));
		ArrayList<Ville> c2 = new ArrayList<Ville>();
		c2 = new ArrayList<Ville>(this.circuit.subList(1, this.circuit.size()));
		Collections.shuffle(c2);
		c1.addAll(c2);
		this.circuit = c1;
		this.tailleCircuit = this.circuit.size();
	}
	
	/**
	 * Constructeur par duplication d'une ArrayList<Ville>
	 * @param gestionnaireCircuit
	 * @param c
	 */
	public Circuit(GestionnaireCircuit gestionnaireCircuit, ArrayList<Ville> ac) {
		this.gestionnaireCircuit = gestionnaireCircuit;
		this.circuit = ac;
		this.tailleCircuit = this.circuit.size();
	}
	
	public double getDistance() {
		double d = 0;
		// Calcul de la distance entre chaque ville de 0 à n-1
		for (int i=0; i<this.tailleCircuit-1; i++) {
			d += this.circuit.get(i).distance(this.circuit.get(i+1));
		}
		// Calcul de la distance entre n-1 et 0
		d += this.circuit.get(this.tailleCircuit-1).distance(this.circuit.get(0));
		return d;
		
	}
	
	/**
	 * @return the tailleCircuit
	 */
	public int getTailleCircuit() {
		return tailleCircuit;
	}

	public double getFitness() {
		if (this.fitness == 0) {
			this.fitness = 1/this.getDistance();
		}
		return this.fitness;
	}
	
	public void setVille(int index, Ville v) {
		this.circuit.set(index, v);
		this.fitness = 0;
		this.distance = 0;
	}
	
	public Ville getVille(int index) {
		return this.circuit.get(index);
	}
	
	public boolean contientVille(Ville v) {
		Iterator<Ville> iterator = this.circuit.iterator();
		Ville i;
		while (iterator.hasNext()) {
			i=iterator.next();
			if (i!=null) {
				if (i.equals(v))
					return true;
			}
		}
		return false;
	}

	/**
	 * @return the circuit
	 */
	public List<Ville> getCircuit() {
		return circuit;
	}

	@Override
	public String toString() {
		return "Circuit [circuit=" + circuit + "]";
	}
	
	
	
	
	
	
}
