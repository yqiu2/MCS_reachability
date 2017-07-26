import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Field {
	private int maxX;
	private int maxY;

	private ArrayList<Task> tasks;

	private ArrayList<Task>[][] field;
	// for each task and time there are itineraries that can reach it with n
	// paths
	private HashMap<Task, HashMap<Integer, HashMap<Itinerary, Integer>>> accessibility;
	private HashMap<Task, HashMap<Integer, ArrayList<Task>>> taskDistances;

	public Field(Location maxLocation) {
		maxX = maxLocation.getX();
		maxY = maxLocation.getY();
		tasks = new ArrayList<Task>();
		field = new ArrayList[maxX][maxY];
		for (int i = 0; i < maxX; i++) {
			for (int j = 0; j < maxY; j++) {
				field[i][j] = new ArrayList<Task>();
			}
		}
		taskDistances = new HashMap<Task, HashMap<Integer, ArrayList<Task>>>();
		accessibility = new HashMap<Task, HashMap<Integer, HashMap<Itinerary, Integer>>>();

	}

	public void addTask(Task task) {
		tasks.add(task);
		Location taskLoc = task.getLocation();
		int locX = taskLoc.getX();
		int locY = taskLoc.getY();
		field[locX][locY].add(task);
	}

	public static int L1Dist(Location loc1, Location loc2) {
		return Math.abs(loc1.getX() - loc2.getX()) + Math.abs(loc1.getY() - loc2.getY());
	}

	/**
	 * 
	 * @param closeby
	 *            a hashmap that contains all
	 * @param journey
	 *            contains current location, current time, end time and end
	 *            location
	 * @return hashmap of task and arrival time at task for all tasks and times
	 *         that can be feasibly added to your journey as it now stands
	 */

	/*
	 * private HashMap<Task, Integer> feasibleTasks(HashMap<Integer,
	 * ArrayList<Task>> closeby, Journey journey) { HashMap<Task, Integer>
	 * reachable = new HashMap<Task, Integer>(); int timeLeft =
	 * journey.getItineraryEndTime() - journey.getCurrentTime(); for (int i = 0;
	 * i <= timeLeft; i++) { if (closeby.containsKey(i)) { for (Task task :
	 * closeby.get(i)) { // ******************** here int distFromCurr =
	 * Field.L1Dist(journey.getCurrLocation(), task.getLocation()); assert (i !=
	 * distFromCurr); int distFromEnd = Field.L1Dist(journey.getEndLocation(),
	 * task.getLocation()); int maxValidArrivalTime =
	 * journey.getItineraryEndTime() - distFromEnd; int minValidArrivalTime =
	 * journey.getCurrentTime() + distFromCurr;
	 * 
	 * int beginPayTime = task.getValuation().getBeginPayTime(); int endPayTime
	 * = task.getValuation().getEndPayTime(); assert (beginPayTime ==
	 * endPayTime); assert (task.getValueAt(beginPayTime)> 0);
	 * 
	 * if (beginPayTime <= maxValidArrivalTime && endPayTime >=
	 * minValidArrivalTime) { // then this task is a task you can add to your
	 * journey int tryArrivalTimeStart = (beginPayTime < minValidArrivalTime) ?
	 * minValidArrivalTime : beginPayTime; int tryArrivalTimeEnd = (endPayTime <
	 * maxValidArrivalTime) ? endPayTime : maxValidArrivalTime; // you can add
	 * this task to your journey at multiple // points for (int j =
	 * tryArrivalTimeStart; j <= tryArrivalTimeEnd; j++) {
	 * System.out.println("trying arrivalTimes between " + tryArrivalTimeStart
	 * +" and " + tryArrivalTimeEnd); if (task.getValueAt(j) > 0 &&
	 * !journey.getTasksPath().contains(task)) { reachable.put(task, j); } } }
	 * 
	 * } } } return reachable; }
	 */
	/**
	 * 
	 * @param journey
	 *            contains information about what the current location and maybe
	 *            what the current task is
	 * @param atStart
	 *            lets you know if the current location is a task?
	 * @return list of tasks and the arrival times at the task (in case the
	 *         tasks don't have single point valuation functions) also fills out
	 *         Field's taskDistances distances between tasks
	 */
	public HashMap<Task, Integer> findFeasibleTasks(Journey journey, boolean atStart) {

		//System.out.println(
		//		"in findFeasibleTasks finding tasks for " + journey + "with current time " + journey.getCurrentTime());
		HashMap<Task, Integer> reachable = new HashMap<Task, Integer>();

		for (Task task : tasks) {
			// ******************** here
			int distFromCurr = Field.L1Dist(journey.getCurrLocation(), task.getLocation());
			int distFromEnd = Field.L1Dist(journey.getEndLocation(), task.getLocation());
			int maxValidArrivalTime = journey.getItineraryEndTime() - distFromEnd;
			int minValidArrivalTime = journey.getCurrentTime() + distFromCurr;

			int PayTime = task.getValuation().getFirstTime();
			assert (task.getValueAt(PayTime) > 0);
			if (PayTime > minValidArrivalTime && PayTime < maxValidArrivalTime && task.getValueAt(PayTime) > 0
					&& !journey.getTasksPath().contains(task)) {
				assert (PayTime > journey.getCurrentTime());
				reachable.put(task, PayTime);
			}

		}
		return reachable;

		/*
		 * Task currTask = journey.getCurrTask(); if (currTask != null) { if
		 * (this.taskDistances.get(currTask) != null) { // you have distances
		 * from your current location HashMap<Integer, ArrayList<Task>> closeby
		 * = this.taskDistances.get(currTask);
		 * 
		 * return feasibleTasks(closeby, journey);
		 * 
		 * } else { // lacking distances from current task location so find them
		 * HashMap<Integer, ArrayList<Task>> closeby = new HashMap<Integer,
		 * ArrayList<Task>>(); for (Task task : tasks) { int dist =
		 * Field.L1Dist(task.getLocation(), currTask.getLocation()); if
		 * (!closeby.containsKey(dist)) { ArrayList<Task> tasks = new
		 * ArrayList<Task>(); tasks.add(task); closeby.put(dist, tasks); } else
		 * { ArrayList<Task> tasks = closeby.get(dist); tasks.add(task);
		 * closeby.put(dist, tasks); } } taskDistances.put(currTask, closeby);
		 * 
		 * return feasibleTasks(closeby, journey);
		 * 
		 * } } else { // if currTask is null then currLocation is not a
		 * taskLocation HashMap<Integer, ArrayList<Task>> closeby = new
		 * HashMap<Integer, ArrayList<Task>>(); for (Task task : tasks) { int
		 * dist = Field.L1Dist(task.getLocation(), currLocation); if
		 * (!closeby.containsKey(dist)) { ArrayList<Task> tasks = new
		 * ArrayList<Task>(); tasks.add(task); closeby.put(dist, tasks); } else
		 * { ArrayList<Task> tasks = closeby.get(dist); tasks.add(task);
		 * closeby.put(dist, tasks); } } return feasibleTasks(closeby, journey);
		 * }
		 */ }

	// FINDING THE K BEST JOURNEYS
	private ArrayList<Journey> topKJourneys(ArrayList<Journey> bestPaths, int k) {
		bestPaths.sort(Comparator.comparing(Journey::getTotalPayout).reversed());
	
		
		for (int i = k; i < bestPaths.size(); i++) {
			bestPaths.remove(i);
		}
		return bestPaths;
	}

	private ArrayList<Journey> addPotentialJourney(ArrayList<Journey> bestPaths, Journey toAdd, int k) {
		bestPaths.add(toAdd);
		// System.out.println("size of bestPaths " +bestPaths.size()+ " adding
		// to journey " + toAdd + " k = " + k);
		if (bestPaths.size() > 2 * k) {
			bestPaths = topKJourneys(bestPaths, k);
		}
		return bestPaths;
	}

	public ArrayList<Journey> findKBestJourneys(Itinerary it, int k) {
		ArrayList<Journey> bestPaths = new ArrayList<Journey>();
		Journey journeyBegin = new Journey(it);
		HashMap<Task, Integer> feasibleTasks = findFeasibleTasks(journeyBegin, true);

		if (feasibleTasks.isEmpty()) {
			bestPaths = addPotentialJourney(bestPaths, journeyBegin, k);
			return bestPaths;
		} else {
			for (Task t : feasibleTasks.keySet()) {
				int arrivalTime = feasibleTasks.get(t);

				int taskPayout = t.getValueAt(arrivalTime);
				Journey newJourney = journeyBegin.makeCopy();
				newJourney.add(t, taskPayout, arrivalTime);
				bestPaths = findJourney(bestPaths, newJourney, k);
			}
		}
		bestPaths = topKJourneys(bestPaths, k);
		return bestPaths;
	}

	private ArrayList<Journey> findJourney(ArrayList<Journey> bestPaths, Journey it, int k) {
		HashMap<Task, Integer> feasibleTasks = findFeasibleTasks(it, false);

		if (feasibleTasks.isEmpty()) {
			bestPaths = addPotentialJourney(bestPaths, it, k);
		} else {
			for (Task t : feasibleTasks.keySet()) {
				int arrivalTime = feasibleTasks.get(t);
				int taskPayout = t.getValueAt(arrivalTime);
				Journey newJourney = it.makeCopy();
				newJourney.add(t, taskPayout, arrivalTime);
				bestPaths = findJourney(bestPaths, newJourney, k);
			}
		}
		return bestPaths;
	}

	// FINDING THE ACCESSIBILTIES
	public void calculateAccessibility(ArrayList<Itinerary> itineraries, int k) {
		for (Itinerary it : itineraries) {
			ArrayList<Journey> bestPaths = findKBestJourneys(it, k);
			System.out.println("best Paths for itinerary " + it.shortToString() +"\n"+ bestPaths);
			for (Journey path : bestPaths) {
				for (int i = 0; i < path.getTasksPath().size(); i++) {
					Task task = path.getTasksPath().get(i);
					int arrivalTime = path.getTaskArrivalTimes().get(i);
					
					if (!accessibility.containsKey(task)) {
						//System.out.println("no accessibility info on this task");
						HashMap<Itinerary, Integer> newItineraryHM = new HashMap<Itinerary, Integer>();
						newItineraryHM.put(it, 1);
						HashMap<Integer, HashMap<Itinerary, Integer>> arrivalHM = new HashMap<Integer, HashMap<Itinerary, Integer>>();
						arrivalHM.put(arrivalTime, newItineraryHM);
						accessibility.put(task, arrivalHM);
					} else {
						// System.out.println("accessibility info on this task");
						if (!accessibility.get(task).containsKey(arrivalTime)) {
							//System.out.println("no accessibility info on this task at this time");
							HashMap<Itinerary, Integer> newItineraryHM = new HashMap<Itinerary, Integer>();
							newItineraryHM.put(it, 1);
							accessibility.get(task).put(arrivalTime, newItineraryHM);
						} else {
							//System.out.println("accessibility info on this task at this time");
							if (!accessibility.get(task).get(arrivalTime).containsKey(it)) {
								accessibility.get(task).get(arrivalTime).put(it, 1);
							} else {
								int numPathsFromUser = accessibility.get(task).get(arrivalTime).get(it);
								accessibility.get(task).get(arrivalTime).put(it, numPathsFromUser + 1);
							}
						}
					}
				}
			}
		}
		System.out.println("Accessibility: " + accessibility);
	}

	// RETURNING ACCESSIBILITY SCORE
	public HashMap<Task, Integer> returnPathAccessbilities() {
		HashMap<Task, Integer> pathAccessibilities = new HashMap<Task, Integer>();
		for (Task task : tasks) {
			int numPaths = 0;
			if (accessibility.containsKey(task)) {
				for (int arrivalTime : accessibility.get(task).keySet()) {
					for (Itinerary it : accessibility.get(task).get(arrivalTime).keySet()) {
						numPaths += accessibility.get(task).get(arrivalTime).get(it);
					}
				}
			}
			pathAccessibilities.put(task, numPaths);
		}
		return pathAccessibilities;
	}

	public HashMap<Task, Integer> returnAgentAccessbilities() {
		HashMap<Task, Integer> pathAccessibilities = new HashMap<Task, Integer>();
		for (Task task : tasks) {
			int numAgents = 0;
			if (accessibility.containsKey(task)) {
				for (int arrivalTime : accessibility.get(task).keySet()) {
					numAgents += accessibility.get(task).get(arrivalTime).keySet().size();
				}
			}
			pathAccessibilities.put(task, numAgents);
		}
		return pathAccessibilities;
	}

}
