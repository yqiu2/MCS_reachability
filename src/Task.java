/*A task*/
public class Task {
	public Location location;
	public int time;
	public int valuation;
	public boolean completed = false;

	public Task(Location loc, int time, int val) {
		if (location != null && time >= 0 && val >= 0) { // can val = 0 ??
			location = loc;
			this.time = time;
			valuation = val;
			completed = false;
		}
	}
	
	public Task(Location loc, int time, int val, boolean completed) {
		this(loc, time, val);
		this.completed = completed;
	}
	
	public static String toStringFormat() {
		return "location(x,y)\t:time\t:valuation\t:completed";
		
	}
	
	public String toString(){
		
		return location.toString()+"\t:"+time+"\t:"+valuation+"\t:"+completed+"\n";
	}

	public Task MakeACopy() {
		Task theCopy = new Task(location, time, valuation);
		return theCopy;
	}

}
