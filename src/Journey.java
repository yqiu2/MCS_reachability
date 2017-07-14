import java.util.ArrayList;

public class Journey {
	private ArrayList<Location> path;
	
	public Journey () {
		path = new ArrayList<Location>();
	}
	
	public Journey(Location start) {
		this();
		this.add(start);
	}
	
	public void add(Location loc) {
		this.add(loc);
	}

	public ArrayList<Location> getPath() {
		return path;
	}

	public void setPaths(ArrayList<Location> path) {
		this.path = path;
	}
	
	
}
