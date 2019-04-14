package outil_Tabou;

public class Ville {


	public int id;
	public String nom;
	private static int idInc=0;
	
	public Ville() {
		this.id=idInc++;
		this.nom="";
	}
	
	public Ville(String nom) {
		this();
		this.nom = nom;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public String toString() {
    	return "Ville "+this.id+" : "+this.nom+"\n";
    }

	 public static void main(String[] args) {
		 
		 
		 Ville Bordeaux= new Ville("B");
		 Ville Lyon= new Ville("L");
		 Ville Nantes=new Ville("N");
		 Ville Paris= new Ville("P");
		 Ville Marseille=new Ville("M");
		 Ville Dijon= new Ville("D");
		 
		 System.out.print(Bordeaux);
		 System.out.print(Lyon);
		 System.out.print(Nantes);
		 System.out.print(Paris);
		 System.out.print(Marseille);
		 System.out.print(Dijon);		 
		 
		 
	 }
	 
}


