import java.util.HashMap;

public class Experiment {

	public static void main(String[] args) {
/*		Scenario sc1 = new Scenario(5,8);
		sc1.addItineraries(5, Distributions.Random, Distributions.Random, Distributions.Random, Distributions.Random, 
				3, 18, 0, 0, 0, 0, null, null, null, null);
		sc1.addTasks(30, Distributions.Random, Distributions.Random, Distributions.Random, Distributions.Random,
				1, 8, 10, 0, 0, 0, 0, null, null, null, null);
		System.out.println(sc1.toString());
		HashMap<Task, Integer> sc1agentAccessibilities = sc1.returnAgentAccessbilities(5);
		System.out.println("agentAccessibilities: "+ sc1agentAccessibilities);
		HashMap<Task, Integer> sc1pathAccessibilities = sc1.returnPathAccessbilities(5);
		System.out.println("pathAccessibilities: "+ sc1pathAccessibilities);
		*/
		
		Scenario sc2 = new Scenario(4,4);
		sc2.addItineraries(5, Distributions.Random, Distributions.Random, Distributions.Random, Distributions.Random, 
				2, 5, 0, 0, 0, 0, null, null, null, null);
		sc2.addTasks(50, Distributions.Random, Distributions.Random, Distributions.Random, Distributions.Random,
				1, 4, 4, 0, 0, 0, 0, null, null, null, null);
		System.out.println(sc2.toString());
		HashMap<Task, Integer> sc2agentAccessibilities = sc2.returnAgentAccessbilities(5);
		HashMap<Task, Integer> sc2pathAccessibilities = sc2.returnPathAccessbilities(5);
		System.out.println(sc2.AccessibilitiesToString(sc2agentAccessibilities, sc2pathAccessibilities));
	
		

	}

}
