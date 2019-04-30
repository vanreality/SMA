package outil_AG;


public class Ville {
	private String nom;
	private int id;
	private int[][] distMat;
	
	public Ville(String nom, int id, int[][] distMat) {
		this.nom = nom;
		this.id = id;
		this.distMat = distMat;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public double distance(Ville other) {
		//System.out.println("this : "+this.id+" other : "+other.id + "test : "+distMat[this.id][other.id]);
//		System.out.println("This : "+this.id);
//		System.out.println("Other : "+other);
//		System.out.println("matrice");
		return distMat[this.id][other.id];
	}
	
	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Ville other = (Ville) obj;
		if (nom == null) {
			if (other.nom != null) {
				return false;
			}
		} else if (!nom.equals(other.nom)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return nom;
	}

	
	
	
}
