
public class Experiment {

	public static void main(String[] args) {

		/**
		 * Itineraries 
		 * 
		 */
		Scenario sc1 = new Scenario(10,2);
		sc1.addItineraries(3, Distributions.Random, Distributions.Random, Distributions.Random, Distributions.Random, 
				250, 38, 0, 0, 0, 0, null, null, null, null);
		sc1.addTasks(6, Distributions.Random, Distributions.Random, Distributions.Random, Distributions.Random,
				1, 250, 38, 0, 0, 0, 0, null, null, null, null);
		System.out.println(sc1.toString());
		
		
	}

}
