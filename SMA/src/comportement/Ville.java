package comportement;

import java.util.HashMap;
import java.util.Map;

public class Ville {
	private String name;
	private int[] distance;
	private Map<String, Integer> ref = new HashMap<String, Integer>();
	
	public Ville(String name, int[] distance) {
		this.name = name;
		this.distance = distance;
	}
	public Ville(Ville v) {
		this.name = v.name;
		this.distance = v.distance;
		ref.put("Bordeaux", 0);
		ref.put("Lyon", 1);
		ref.put("Nantes", 2);
		ref.put("Paris", 3);
		ref.put("Marseille", 4);
		ref.put("Dijon", 5);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDistance(String ville) {
		return distance[ref.get(ville)];
	}
	public int getID() {
		return(ref.get(this.name));
	}

}