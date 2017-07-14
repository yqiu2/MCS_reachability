// location is a struct that defines a location with x and y
public class Location {
	public int x;
	public int y;
	// public boolean hasTask;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
		// this.hasTask = task;
	}

	public static int L1Dist(Location loc1, Location loc2)	{		
		return Math.abs(loc1.x - loc2.x)+ Math.abs(loc1.y - loc2.y);
	}
	
	public String toString() {
		return "("+x+","+y+")";
	} 
	
}
