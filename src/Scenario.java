import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Scenario {
	// perhaps a different data structure for itineraries and tasks?   
	// how about a min heap based on time?
	// what about being indexed by location?
	private ArrayList<Itinerary> itineraries;   
	private ArrayList<Task> tasks;

	private Location maxLocation;
	private Field graph; 
	
	// whether accessibility of tasks has been calculated with 
	// k number of optimal paths
	private int isCalculatedAccessibilities;

	private Random rand = new Random();

	public Scenario() {
		itineraries = new ArrayList<Itinerary>();
		tasks = new ArrayList<Task>();
		maxLocation = new Location(20, 30);
		graph = new Field(maxLocation);
		isCalculatedAccessibilities = 0;
	}

	public Scenario(int maxX, int maxY) {
		this();
		maxLocation = new Location(maxX, maxY);
		graph = new Field(maxLocation);
	}

	public String toString() {
		String str = "Scenario:\n";
		str += "# itineraries = " + itineraries.size() + "\n";
		str += Itinerary.toStringFormat() + "\n";
		for (Itinerary itin : itineraries) {
			str += itin.toString();
		}
		str += "# tasks = " + tasks.size() + "\n";
		str += Task.toStringFormat() + ":\n";
		for (Task task : tasks) {
			str += task.toString();
		}
		return str;
	}

	private String printItinerariesParameters(int numItineraries, Distributions startLocDistro,
			Distributions endLocDistro, Distributions startTimeDistro, Distributions timeFlexDistro, int startTimeMax,
			int timeFlexMax, int startLocStdDev, int endLocStdDev, int startTimeStdDev, int timeFlexStdDev,
			ArrayList<Location> startLocCenters, ArrayList<Location> endLocCenters, ArrayList<Integer> startTimeCenters,
			ArrayList<Integer> timeFlexCenters) {
		return "Generating " + numItineraries + " Itineraries:\n" + "startLocDistro:" + startLocDistro + " endLocDistro:"
				+ endLocDistro + " startTimeDistro:" + startTimeDistro + " timeFlexDistro:" + timeFlexDistro
				+ " startTimeMax:" + startTimeMax + " timeFlexMax:" + timeFlexMax + " startLocStdDev:" + startLocStdDev
				+ " endLocStdDev:" + endLocStdDev + " startTimeStdDev:" + startTimeStdDev + " timeFlexStdDev:"
				+ timeFlexStdDev + " startLocCenters:" + startLocCenters + " endLocCenters:" + endLocCenters
				+ " startTimeCenters:" + startTimeCenters + " timeFlexCenters:" + timeFlexCenters;

	}

	public void addItineraries(int numItineraries, Distributions startLocDistro, Distributions endLocDistro,
			Distributions startTimeDistro, Distributions timeFlexDistro, int startTimeMax, int timeFlexMax,
			int startLocStdDev, int endLocStdDev, int startTimeStdDev, int timeFlexStdDev,
			ArrayList<Location> startLocCenters, ArrayList<Location> endLocCenters, ArrayList<Integer> startTimeCenters,
			ArrayList<Integer> timeFlexCenters) {
		
		System.out.println(printItinerariesParameters(numItineraries, startLocDistro, endLocDistro, startTimeDistro,
				timeFlexDistro, startTimeMax, timeFlexMax, startLocStdDev, endLocStdDev, startTimeStdDev,
				timeFlexStdDev, startLocCenters, endLocCenters, startTimeCenters, timeFlexCenters));

		Location startLocMax = maxLocation;
		Location endLocMax = maxLocation;

		for (int i = 0; i < numItineraries; i++) {
			/* START LOCATION DISTRIBUTIONS */
			Location startLoc = null;
			if (startLocDistro == Distributions.Random) {
				int x = rand.nextInt(startLocMax.getX());
				int y = rand.nextInt(startLocMax.getY());
				startLoc = new Location(x, y);
			} else if (startLocDistro == Distributions.Gaussian) {
				Location startLocCenter = startLocCenters.get(rand.nextInt(startLocCenters.size() - 1));
				int x = (int) (startLocStdDev * rand.nextGaussian()) + startLocCenter.getX();
				int y = (int) (startLocStdDev * rand.nextGaussian()) + startLocCenter.getY();
				startLoc = new Location(x, y);
			} else {
				System.out.println(
						"startLoc uniform random distribution = 0 \nnormal distribution = 1 and provide list of centers");
			}

			/* START LOCATION DISTRIBUTIONS */
			Location endLoc = null;
			if (endLocDistro == Distributions.Random) {
				int x = rand.nextInt(endLocMax.getX());
				int y = rand.nextInt(endLocMax.getY());
				endLoc = new Location(x, y);
			} else if (endLocDistro == Distributions.Gaussian) {
				Location endLocCenter = endLocCenters.get(rand.nextInt(endLocCenters.size() - 1));
				int x = (int) (startLocStdDev * rand.nextGaussian()) + endLocCenter.getX();
				x = (x > startLocMax.getX()) ? startLocMax.getY() : x;
				x = (x < 0) ? 0 : x;
				int y = (int) (startLocStdDev * rand.nextGaussian()) + endLocCenter.getY();
				y = (y > startLocMax.getY()) ? startLocMax.getY() : y;
				y = (y < 0) ? 0 : y;
				endLoc = new Location(x, y);
			} else {
				System.out.println(
						"endLoc uniform random distribution = 0 \nnormal distribution = 1 and provide list of centers");
			}

			/* START TIME DISTRIBUTIONS */
			int startTime = -1;
			if (startTimeDistro == Distributions.Random) {
				startTime = (int) (startTimeMax * Math.random());
			} else if (startTimeDistro == Distributions.Gaussian) {
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
			if (timeFlexDistro == Distributions.Random) {
				timeFlex = (int) (timeFlexMax * Math.random());
			} else if (timeFlexDistro == Distributions.Gaussian) {
				int timeFlexCenter = timeFlexCenters.get(rand.nextInt(timeFlexCenters.size() - 1));
				timeFlex = (int) (timeFlexStdDev * rand.nextGaussian()) + timeFlexCenter;
				timeFlex = (timeFlex > timeFlexMax) ? timeFlexMax : timeFlex;
				timeFlex = (timeFlex < 0) ? 0 : timeFlex;
			} else if (timeFlexDistro == Distributions.Exponential) {

			} else {
				System.out.println(
						"timeFlex uniform random distribution = 0 \nnormal distribution = 1 and provide list of centers");
			}

			Itinerary itinerary = new Itinerary(startLoc, endLoc, startTime, timeFlex);
			this.itineraries.add(itinerary);
		}
	}
	
	public void addItinerary(int startLocX, int startLocY, int endLocX, int endLocY, int startTime, int timeFlex){
		Itinerary itinerary = new Itinerary(new Location(startLocX, startLocY), new Location(endLocX, endLocY), startTime, timeFlex);
		this.itineraries.add(itinerary);		
	}
	

	private String printTasksParameters(int numTasks, Distributions locDistro, Distributions endLocDistro, Distributions timeDistro,
			Distributions timeFlexDistro, int maxPayout, int timeMax, int timeFlexMax, int locStdDev, int endLocStdDev,
			int timeStdDev, int timeFlexStdDev, ArrayList<Location> locCenters, ArrayList<Location> endLocCenters,
			ArrayList<Integer> timeCenters, ArrayList<Integer> timeFlexCenters) {
		return "Generating "+ numTasks + " tasks:\n" + " locDistro:" + locDistro + " endLocDistro:" + endLocDistro + " timeDistro:" + timeDistro +
				" timeFlexDistro:" + timeFlexDistro + " maxPayout:" + maxPayout + " timeMax:" + timeMax + " timeFlexMax: " + timeFlexMax + " locStdDev:" + locStdDev + " endLocStdDev: "+ endLocStdDev + 
				" timeStdDev:" + timeStdDev + " timeFlexStdDev:" + timeFlexStdDev + " locCenters:" + locCenters + " endLocCenters:" + endLocCenters +
				" timeCenters:" + timeCenters+ " timeFlexCenters:" + timeFlexCenters;
	}
	public void addTasks(int numTasks, Distributions locDistro, Distributions endLocDistro, Distributions timeDistro,
			Distributions timeFlexDistro, int maxPayout, int timeMax, int timeFlexMax, int locStdDev, int endLocStdDev,
			int timeStdDev, int timeFlexStdDev, ArrayList<Location> locCenters, ArrayList<Location> endLocCenters,
			ArrayList<Integer> timeCenters, ArrayList<Integer> timeFlexCenters) {

		System.out.println(printTasksParameters (numTasks,  locDistro,  endLocDistro,  timeDistro,
			 timeFlexDistro,  maxPayout,  timeMax,  timeFlexMax,  locStdDev,  endLocStdDev,
			 timeStdDev,  timeFlexStdDev, locCenters, endLocCenters,
			 timeCenters,  timeFlexCenters));
		
		Location locMax = maxLocation;

		for (int i = 0; i < numTasks; i++) {
			/* LOCATION DISTRIBUTIONS */
			Location loc = null;
			if (locDistro == Distributions.Random) {
				int x = rand.nextInt(locMax.getX());
				int y = rand.nextInt(locMax.getY());
				loc = new Location(x, y);
			} else if (locDistro == Distributions.Gaussian) {
				Location startLocCenter = locCenters.get(rand.nextInt(locCenters.size() - 1));
				int x = (int) (locStdDev * rand.nextGaussian()) + startLocCenter.getX();
				int y = (int) (locStdDev * rand.nextGaussian()) + startLocCenter.getY();
				loc = new Location(x, y);
			} else {
				System.out.println(
						"task location uniform random distribution = 0 \nnormal distribution = 1 and provide list of centers");
			}

			/* TASK TIME DISTRIBUTIONS */
			int time = -1;
			if (timeDistro == Distributions.Random) {
				time = (int) (timeMax * Math.random());
			} else if (timeDistro == Distributions.Gaussian) {
				int startTimeCenter = timeCenters.get(rand.nextInt(timeCenters.size() - 1));
				time = (int) (timeStdDev * rand.nextGaussian()) + startTimeCenter;
				time = (time > timeMax) ? timeMax : time;
				time = (time < 1) ? 1 : time;
			} else {
				System.out.println(
						"startTime uniform random distribution = 0 \nnormal distribution = 1 and provide list of centers");
			};
			
			int payout = (maxPayout > 0) ? maxPayout : 1;
			Task task = new Task(loc, time, payout);
			this.tasks.add(task);
			graph.addTask(task);
			
		}
	}	
	
	public void addTask(int locX, int locY, int time, int payout){
		Task task = new Task(new Location(locX, locY), time, payout);
		this.tasks.add(task);
		graph.addTask(task);	
	}
	
	
	public HashMap<Task, Integer> returnPathAccessbilities(int k) {
		if (isCalculatedAccessibilities != k){
			this.graph.calculateAccessibility(this.itineraries, k);
			isCalculatedAccessibilities = k;
		}
		return this.graph.returnPathAccessbilities();	
	}
	
	public String AccessibilitiesToString(HashMap<Task, Integer> agentAccessibilityHashMap, HashMap<Task, Integer> pathAccessibilityHashMap) {
		String str = "task: \tagentAccess: \tpathAccess:\n";
		for (Task task: tasks) {
			str += task.toString()+"\t"+agentAccessibilityHashMap.get(task)+"\t"+pathAccessibilityHashMap.get(task)+"\n";
		}
		return str;
	}
	public HashMap<Task, Integer> returnAgentAccessbilities(int k) {
		if (isCalculatedAccessibilities != k){
			this.graph.calculateAccessibility(this.itineraries, k);
			isCalculatedAccessibilities = k;
		}
		return this.graph.returnAgentAccessbilities();		
	}
	
	
	
	public int findNumberTasksAssigned(){
		int numAssigned = 0; 
		for (Task t : tasks){
			if (t.isAssigned()) { 
				numAssigned++;
			}
		}
		return numAssigned;
	}	
	

	public ArrayList<Itinerary> getItineraries() {
		return itineraries;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public Location getMaxLocation() {
		return maxLocation;
	}

	public void setMaxLocation(Location maxLocation) {
		this.maxLocation = maxLocation;
	}

}
