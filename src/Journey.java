import java.util.ArrayList;

public class Journey {
	private Itinerary itinerary;
	private int totalPayout;
	private ArrayList<Task> tasksPath;
	private ArrayList<Integer> taskArrivalTimes;
	private int currentTime;
	
	public Journey(Itinerary it) {
		itinerary = it;
		totalPayout = 0;
		tasksPath = new ArrayList<Task>();
		taskArrivalTimes = new ArrayList<Integer>();
		currentTime = it.getStartTime();
	}
	
	public Journey makeCopy() {
		Journey copy = new Journey(itinerary);
		copy.totalPayout = this.totalPayout;
		copy.tasksPath = new ArrayList<Task>();
		for (int i=0;i < this.tasksPath.size(); i++) {
			copy.tasksPath.add(i, this.tasksPath.get(i));
		}
		copy.taskArrivalTimes = new ArrayList<Integer>();
		for (int i=0;i < this.tasksPath.size(); i++) {
			copy.taskArrivalTimes.add(i, this.taskArrivalTimes.get(i));
		}
		copy.currentTime = this.currentTime;
		return copy;
	}
	
	/**
	 * get the current location that this journey is at 
	 * @return
	 */
	public Location getCurrLocation() {
		if (tasksPath.size() == 0) {
			return itinerary.getStartLoc();
		} else {
			return tasksPath.get(tasksPath.size()-1).getLocation();
		}
	}
	
	public Task getCurrTask() {
		if (tasksPath.size() == 0) {
			return null;
		} else {
			return tasksPath.get(tasksPath.size()-1);
		}
	}
	
	public int getCurrentTime() {
	
		return currentTime;
		/*		if (taskArrivalTimes.isEmpty()) {
			return itinerary.getStartTime();
		} else {
			return taskArrivalTimes.get(taskArrivalTimes.size()-1);	
		}
*/	
		}
	
	public int getItineraryEndTime() {
		return itinerary.getEndTime();		
	}
	
	public int getTravelTime(int taskArrivalTime) {
		return taskArrivalTime - this.getCurrentTime();
	}
	
	public void add(Task task, int taskPayout,  int taskArrivalTime) {
		totalPayout += taskPayout;
		tasksPath.add(task);
		taskArrivalTimes.add(taskArrivalTime);
		assert(taskArrivalTime > currentTime);
		currentTime = taskArrivalTime;
	}
	
	public int getTotalPayout() {
		return totalPayout;
	}

	public Location getEndLocation() {
		return itinerary.getEndLoc();
	}
	
	public Itinerary getItinerary() {
		return itinerary;
	}

	public ArrayList<Task> getTasksPath() {
		return tasksPath;
	}

	public ArrayList<Integer> getTaskArrivalTimes() {
		return taskArrivalTimes;
	}
	
	// for comparing to?
	public int compareTo(Journey other) {
		return other.getTotalPayout() - this.totalPayout;
	}

	public String toString(){
		String res = "Journey.toString()";
		res += itinerary.shortToString();
		res += " time=" + this.getCurrentTime();
		res += " revenue= " + this.totalPayout;
		res += " Tasks= [ ";
		for (Task task : tasksPath) {
			res += task.toString() + ", ";
		}
		res += "]";
		
		return res;
	}

}
