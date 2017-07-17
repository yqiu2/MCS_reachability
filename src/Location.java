/**
 * Location class for storing x and y coordinates
 * @author Amy Qiu
 *
 */
public class Location {
	private int x;
	private int y;

	/**
	 * generates Location object
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}


	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	public static int L1Dist(Location loc1, Location loc2)	{		
		return Math.abs(loc1.x - loc2.x)+ Math.abs(loc1.y - loc2.y);
	}
	
	public static String toStringFormat(){
		return "location = (x,y)";
	}
	
	public String toString() {
		return "("+x+","+y+")";
	} 
	
}
