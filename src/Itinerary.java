/*itineraries are the start and end location and times */
public class Itinerary {
	private Location startLoc;
	private Location endLoc;
	private int startTime;
	private int timeFlexibility;

	private int endTime; // Calculated by startTime + minTravelTime +
							// flexibility
	/**
	 * generates an itinerary
	 * @param startLoc Location that agent wants to start at
	 * @param endLoc location that agent wants to end at
	 * @param startTime time at which itinerary starts
	 * @param timeFlexibility time in addition to the time it takes to travel from start to end
	 */
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
		return "start(x,y)@t ->\tend(x,y)@t \t:travel time \t:timeflex";
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

	
	public Location getStartLoc() {
		return startLoc;
	}
	
	public void setStartLoc(Location startLoc) {
		this.startLoc = startLoc;
	}
	
	public Location getEndLoc() {
		return endLoc;
	}
	
	public void setEndLoc(Location endLoc) {
		this.endLoc = endLoc;
	}

	public int getStartTime() {
		return startTime;
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public int getTimeFlexibility() {
		return timeFlexibility;
	}
	
	public void setTimeFlexibility(int timeFlexibility) {
		this.timeFlexibility = timeFlexibility;
	}	
}
