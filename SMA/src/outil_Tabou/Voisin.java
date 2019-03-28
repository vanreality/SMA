package outil_Tabou;

public class Voisin extends Ordonnancement{

	int [] mouvement;
	boolean estDansTabou;

	public Voisin(Ville[] villes, Voyage voyage) {
		super(villes, voyage);
		this.mouvement=new int [2];
		this.estDansTabou=false;
	}
	
	public Voisin(Ordonnancement ordonnancement) {
		super(ordonnancement);
		this.mouvement=new int [2];
		this.estDansTabou=false;
	}
	
	
	public int[] getMouvement() {
		return this.mouvement;
	}

	public void setMouvement(int a, int b) {
		this.mouvement = new int [] {a,b};
	}
	
	public void setEstDansTabou(boolean bool) {
		this.estDansTabou=bool;
	}

	public boolean MvtDsTabou(int a, int b, int [][] listeTabou) {
		for (int i=0;i<listeTabou.length;i++) {
			if (listeTabou[i][0]==a && listeTabou[i][1]==b) {
				this.estDansTabou=true;
				return true;
			}
			if (listeTabou[i][0]==b && listeTabou[i][1]==a) {
				this.estDansTabou=true;

				return true;
			}	
		}
		
		return false;
	}

	
	
	 public static void main(String[] args) {
		 
		 Ville B= new Ville("Bordeaux");
		 Ville L= new Ville("Lyon");
		 Ville N=new Ville("Nantes");
		 Ville P= new Ville("Paris");
		 Ville M=new Ville("Marseille");
		 Ville D= new Ville("Dijon"); 
		 
		 Donnees donnees=new Donnees();
		 
		 Voyage Voyage1=new Voyage(donnees,6);
		 
		 Voisin voisin=new Voisin(new Ville [] {B, L, N, P, M, D},Voyage1);
		 
		 System.out.println(voisin);
		 
		 int [][] tabou= new int [][] {{2,3},{4,5}};
		 
		 System.out.println(!voisin.MvtDsTabou(5, 3, tabou));
		 
	 }

	
}

