package outil_AG_Recuit;


public class Ville2 {
	private String name;
	private double[] distance;
	public int id;
	
	
	//Constructors
	
	/*
	 * Le constructeur ci-dessous permet de construire une ville à partir d'un nom de ville, et de la liste des distances par rapport aux autres villes (par ordre du fichier txt).
	 * Il nous permet d'adapter le problème à un nombre de villes quelconque.
	 */
	public Ville2(String name, double[] distance, int id) { 
		this.name = name;
		this.distance = distance;
		this.id = id;
	}
	public Ville2(Ville2 v) { //On réalise une copie d'une ville
		this.name = v.getName();
		this.distance = v.getDistance();
		this.id = v.id; 
	}
	
	
	//Getters
	public String getName() {
		return this.name;
	}
	public double[] getDistance() {
		return this.distance;
	}
	
	
	//Setters
	public void setName(String name) {
		this.name = name;
	}
	
	
	//Methods
	public double findDistance(int indiceArrivée) { //On renvoie la distance de l'objet this par rapport à l'objet ville d'indice i
		return distance[indiceArrivée];
	}
}