import java.util.*;

public class Salesman{

public static String[] Town_Names;
public static Double[][] Distances;
public static int popSize;
public static Population population;
public static int evolutions;
public static double mutationRate;

	public static void main(String[] args){
		evolutions = 1000;
		popSize = 10000;
		mutationRate = 0.3;

		//System.out.println("Preparing to parse input");
		Pair input = Parser.inputParser("Friday.csv");
		Distances = input.getFirst();
		Town_Names = input.getSecond();		
		
		population = new Population(popSize, true);
		Waypoint oldfittest	= population.getFittest();
		Population result = new Population(10, false);

		for (int m = 0; m < evolutions; m++) {
			population = EvolutionChamber.evolve(population);
		}
		
		for (int m = 0; m < popSize; m++) {
			System.out.println(population.getPath(m).getCost());
		}
		
		for (int m = 0; m < 10; m++) {
			Waypoint temp = population.getFittest();
			population.removePath(population.getFittest());
			result.addPath(twoOpt(temp));
		}
		
		System.out.println("\nPopulation's Fittest Before Evolution: " + oldfittest.getCost());
		oldfittest.printRoute();
		
		System.out.println("\nMega Population's Fittest After Evolution: " + population.getFittest().getCost());
		population.getFittest().printRoute();
				
		System.out.println("\nMega Population's Fittest After Evolution and 2-opt: " + result.getFittest().getCost());
		result.getFittest().printRoute();
		
		Population best = new Population(10, false);
		for (int m = 0; m < 10; m++) {
			Waypoint temp = result.getFittest();
			result.removePath(population.getFittest());
			best.addPath(reversePaths(temp));
		}
		
		System.out.println("\nMega Population's Fittest After Evolution and 2-opt and Reversal: " + best.getFittest().getCost());
		best.getFittest().printRoute();
	}
	
	public static Waypoint twoOpt (Waypoint twooptee){
		boolean changed = false;
		for (int i = 0; i < Town_Names.length; i++) {
			for (int j = i+1; j < Town_Names.length; j++) {
				ArrayList<Integer> routeAL = twooptee.getRouteID();
				Iterator<Integer> iterator = routeAL.iterator();
				int[] routeArray = new int[Town_Names.length];
				for (int k = 0; k < Town_Names.length; k++) {
				 	routeArray[k] = iterator.next();
				}
				Waypoint twoopted = operate(routeArray, i , j);
				//System.out.println("Trying " + i + " and " + j);
				//System.out.println(twoopted.getCost() + " vs " + twooptee.getCost());
				if (twoopted.getCost() < twooptee.getCost()) {
					//System.out.println("Changed");
					changed = true;
					twooptee = twoopted;
				}
			}
		}
		if (changed) { 
			//System.out.println("Changed");
			return twoOpt(twooptee);
		} else {
			return twooptee;
		}
	}
	
	public static Waypoint reversePaths (Waypoint optee) {
		boolean repeat = true;
		Population opted = new Population(Town_Names.length*Town_Names.length, false);
		opted.addPath(optee);
		for (int f = 0; f < 10; f++) {
			ArrayList<Integer> routeAL = opted.getFittest().getRouteID();
			Iterator<Integer> iterator = routeAL.iterator();
			int[] route = new int[Town_Names.length];
		
			for (int k = 0; k < Town_Names.length; k++) {
			 	route[k] = iterator.next();
			}
		
			for (int i = 0; i < route.length; i++) {
				for (int j = i; j < route.length; j++) {
					int[] newRoute = new int[route.length];
					for (int n = 0; n < route.length; n++) {
						newRoute[n] = route[n];
						if (n >= i && n <= j) {
							if (i != j) {
							//System.out.println("Reversing");
							newRoute[n] = route[j-(n-i)];
							}			
						}
					}
					Waypoint latest = new Waypoint(newRoute);
					if (latest.getCost() < optee.getCost()) {
						opted.addPath(latest);
					}
				}
			}	
		}
		return opted.getFittest();
	}
	
	public static Waypoint operate (int[] route , int i , int j) {
		int k = route[i];
		route[i] = route[j];
		route[j] = k;
		return new Waypoint(route);
	}
}
