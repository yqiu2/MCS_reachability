/*A task*/
public class Task {
	private Location location;
	private Valuation valuation; // change this to a valuation function that gives a value at a certain time
	private boolean assigned = false;

	public Task(Location loc, Valuation valuation){
		if (location != null && valuation != null) { 
			location = loc;
			this.valuation = valuation;
			assigned = false;
		}
	}
	public Task(Location loc, int time, int val) {
		if (loc != null && time >= 0 && val > 0) { 
			location = loc;
			valuation = new Valuation(val, time );
			assigned = false;
		} else {
			System.out.println("this failed: (loc != null && time >= 0 && val >= 0)");
		
		}
	}
	
	public Location getLocation() {
		return location;
	}
	public Valuation getValuation() {
		return valuation;
	}
	public boolean isAssigned() {
		return assigned;
	}
	
	public static String toStringFormat() {
		return "location\t:valuation\t\t:assigned";
	}
	
	public String toString(){
		return location.toString()+"\t:"+valuation.toString()+"\t:"+assigned+"\n";
	}

	public Task MakeACopy() {
		Task theCopy = new Task(location, valuation);
		return theCopy;
	}

}
