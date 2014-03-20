public class EvolutionChamber {
	private Population newPop;
	
	public EvolutionChamber() {
		newPop = new Population(Salesman.popSize, false); 	//New empty population for next generation
		newPop.addPath(Salesman.population.getFittest()); 	//Preserve Fittest of previous generation
	}
	
	public static Population evolve (Population oldPop) {
		Population newPop = new Population(Salesman.popSize, false);
		newPop.addPath(oldPop.getFittest());
		for (int i = 0; i < oldPop.getSize()-1; i++) {
            // Select parents
            Waypoint father = oldPop.createPool().getFittest();
			Waypoint mother = oldPop.createPool().getFittest();
            // Crossover parents
            Waypoint child = crossover(father, mother);
            // Add child to new population
            //System.out.println(child.getCost());
            newPop.addPath(child);
        }
        
        return newPop;
	}
	
	public static Waypoint crossover (Waypoint father, Waypoint mother) {
		boolean[] visited = new boolean[Salesman.Town_Names.length];
		int[] route = new int[Salesman.Town_Names.length];
		
		 
        if (father.getCost() == mother.getCost()) {
        	mother = new Waypoint(Salesman.Town_Names.length);
        }
        
		for (int i = 0; i < Salesman.Town_Names.length; i++) {
			visited[i]=false;
			route[i] = -1;
		}
		
		int lowerBound = (int)(Math.random()*Salesman.Town_Names.length);
		int upperBound = (int)(Math.random()*Salesman.Town_Names.length);
		
		if (upperBound < lowerBound) {
			int temp = upperBound;
			upperBound = lowerBound;
			lowerBound = temp;
		}
		
		for (int i = 0; i < Salesman.Town_Names.length; i++) {
			if (i > lowerBound && i < upperBound) {
		        route[i] = father.getRouteID().get(i);
		        visited[father.getRouteID().get(i)] = true;
		    }
		}
		
		for (int i = 0; i < Salesman.Town_Names.length; i++) {
            if (visited[mother.getRouteID().get(i)] == false) { 		// If child doesn't have the city add it
                for (int j = 0; j < Salesman.Town_Names.length; j++) {	// Loop to find a spare position in the child's tour
                    if (route[j] == -1) {								// Spare position found, add city
                        route[j] = mother.getRouteID().get(i);
                        visited[mother.getRouteID().get(i)] = true;
                        break;
                    }
                }
            }
        }
        
        Waypoint child = mutate(route);
	    child.calcCost();
        
        return child;
	}
            
	public static Waypoint mutate (int[] route) {
		for (int i = 0; i < route.length; i++) {
			if (Math.random() < Salesman.mutationRate) {
				int j = (int)(Math.random()*route.length);
				int k = route[i];
				route[i] = route[j];
				route[j] = k;
			}
		}
		return new Waypoint(route);
	}
}
