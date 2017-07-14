import java.util.ArrayList;
import java.util.Random;

public class Scenario {
	public ArrayList<Itinerary> itineraries;
	public ArrayList<Task> tasks;
	
	private Location maxLocation;
	

	private Random rand = new Random();

	public Scenario() {
		itineraries = new ArrayList<Itinerary>();
		tasks = new ArrayList<Task>();
	}
	public Scenario(int maxX, int maxY) {
		this();
		maxLocation = new Location(maxX, maxY);
	}

	public String toString(){
		String str = Itinerary.toStringFormat()+"\n";
		for (Itinerary itin : itineraries ) {
			str += itin.toString();
		}
		for (Task task : tasks ) {
			str += task.toString();
		}
		
		return str;
	}
	
	public Itinerary addItinerary(int startLocDistro, int endLocDistro, int startTimeDistro, int timeFlexDistro,
			Location startLocMax, Location endLocMax, int startTimeMax, int timeFlexMax, int startLocStdDev,
			int endLocStdDev, int startTimeStdDev, int timeFlexStdDev, ArrayList<Location> startLocCenters,
			ArrayList<Location> endLocCenters, ArrayList<Integer> startTimeCenters,
			ArrayList<Integer> timeFlexCenters) {

		/* START LOCATION DISTRIBUTIONS */
		Location startLoc = null;
		if (startLocDistro == 0) {
			int x = rand.nextInt(startLocMax.x);
			int y = rand.nextInt(startLocMax.y);
			startLoc = new Location(x, y);
		} else if (startLocDistro == 0) {
			Location startLocCenter = startLocCenters.get(rand.nextInt(startLocCenters.size() - 1));
			int x = (int) (startLocStdDev * rand.nextGaussian()) + startLocCenter.x;
			int y = (int) (startLocStdDev * rand.nextGaussian()) + startLocCenter.y;
			startLoc = new Location(x, y);
		} else {
			System.out.println(
					"startLoc uniform random distribution = 0 \nnormal distribution = 1 and provide list of centers");
		}

		/* START LOCATION DISTRIBUTIONS */
		Location endLoc = null;
		if (endLocDistro == 0) {
			int x = rand.nextInt(endLocMax.x);
			int y = rand.nextInt(endLocMax.y);
			endLoc = new Location(x, y);
		} else if (endLocDistro == 0) {
			Location endLocCenter = endLocCenters.get(rand.nextInt(endLocCenters.size() - 1));
			int x = (int) (startLocStdDev * rand.nextGaussian()) + endLocCenter.x;
			x = (x > startLocMax.x) ? startLocMax.x : x;
			x = (x < 0) ? 0 : x;
			int y = (int) (startLocStdDev * rand.nextGaussian()) + endLocCenter.y;
			y = (y > startLocMax.y) ? startLocMax.y : y;
			y = (y < 0) ? 0 : y;
			endLoc = new Location(x, y);
		} else {
			System.out.println(
					"endLoc uniform random distribution = 0 \nnormal distribution = 1 and provide list of centers");
		}

		/* START TIME DISTRIBUTIONS */
		int startTime = -1;
		if (startTimeDistro == 0) {
			startTime = (int) (startTimeMax * Math.random());
		} else if (startTimeDistro == 0) {
			int startTimeCenter = startTimeCenters.get(rand.nextInt(startTimeCenters.size() - 1));
			startTime = (int) (startTimeStdDev * rand.nextGaussian()) + startTimeCenter;
			startTime = (startTime > startTimeMax) ? startTimeMax : startTime;
			startTime = (startTime < 0) ? 0 : startTime;
		} else {
			System.out.println(
					"startTime uniform random distribution = 0 \nnormal distribution = 1 and provide list of centers");
		}

		/* TIME FLEXIBILITY DISTRIBUTIONS */
		int timeFlex = -1;
		if (timeFlexDistro == 0) {
			timeFlex = (int) (timeFlexMax * Math.random());
		} else if (timeFlexDistro == 0) {
			int timeFlexCenter = timeFlexCenters.get(rand.nextInt(timeFlexCenters.size() - 1));
			timeFlex = (int) (timeFlexStdDev * rand.nextGaussian()) + timeFlexCenter;
			timeFlex = (timeFlex > timeFlexMax) ? timeFlexMax : timeFlex;
			timeFlex = (timeFlex < 0) ? 0 : timeFlex;
		} else {
			System.out.println(
					"timeFlex uniform random distribution = 0 \nnormal distribution = 1 and provide list of centers");
		}

		Itinerary itinerary = new Itinerary(startLoc, endLoc, startTime, timeFlex);
		this.itineraries.add(itinerary);
		return itinerary;

	}

	public Task addTask(int locDistro, int endLocDistro, int timeDistro, int timeFlexDistro, Location locMax,
			Location endLocMax, int timeMax, int timeFlexMax, int locStdDev, int endLocStdDev, int timeStdDev,
			int timeFlexStdDev, ArrayList<Location> locCenters, ArrayList<Location> endLocCenters,
			ArrayList<Integer> timeCenters, ArrayList<Integer> timeFlexCenters) {

		/* LOCATION DISTRIBUTIONS */
		Location loc = null;
		if (locDistro == 0) {
			int x = rand.nextInt(locMax.x);
			int y = rand.nextInt(locMax.y);
			loc = new Location(x, y);
		} else if (locDistro == 0) {
			Location startLocCenter = locCenters.get(rand.nextInt(locCenters.size() - 1));
			int x = (int) (locStdDev * rand.nextGaussian()) + startLocCenter.x;
			int y = (int) (locStdDev * rand.nextGaussian()) + startLocCenter.y;
			loc = new Location(x, y);
		} else {
			System.out.println(
					"task location uniform random distribution = 0 \nnormal distribution = 1 and provide list of centers");
		}

		/* TASK TIME DISTRIBUTIONS */
		int time = -1;
		if (timeDistro == 0) {
			time = (int) (timeMax * Math.random());
		} else if (timeDistro == 0) {
			int startTimeCenter = timeCenters.get(rand.nextInt(timeCenters.size() - 1));
			time = (int) (timeStdDev * rand.nextGaussian()) + startTimeCenter;
			time = (time > timeMax) ? timeMax : time;
			time = (time < 0) ? 0 : time;
		} else {
			System.out.println(
					"startTime uniform random distribution = 0 \nnormal distribution = 1 and provide list of centers");
		}
		
		int payout = 1;
		Task task = new Task(loc, time, payout, false);
		this.tasks.add(task);
		return task;

	}

}
