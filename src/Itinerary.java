/*itineraries are the start and end location and times */
public class Itinerary {
	public Location startLoc;
	public Location endLoc;
	public int startTime;
	public int timeFlexibility;

	private int endTime; // Calculated by startTime + minTravelTime +
							// flexibility

	public Itinerary(Location startLoc, Location endLoc, int startTime, int timeFlexibility) {
		if (startLoc != null && endLoc != null && startTime >= 0 && timeFlexibility >= 0) {
			this.startLoc = startLoc;
			this.endLoc = endLoc;
			this.startTime = startTime;
			this.timeFlexibility = timeFlexibility;
			
			this.endTime = startTime + Location.L1Dist(startLoc, endLoc) + timeFlexibility;
		} else {
			// throw exception ???
		}
	}

	public static String toStringFormat() {
		return "itineraries:\n start(x,y)@time ->\tend(x,y)@time \t:travel time \t:time flexibility";
	}

	public String toString() {
		String str = "";
		str += startLoc.toString() + "@" + startTime;
		str += " ->\t";
		str += endLoc.toString() + "@" + endTime;
		str += "\t:" + Location.L1Dist(startLoc, endLoc);
		str += "\t:" + timeFlexibility;
		str += "\n";
		return str;
	}

}
