import java.util.*;

class Waypoint implements Comparable<Waypoint> {
	private String route;					//Route so far
	private double cost = 0.0;				//Cost/distance so far
	private boolean[] visited;				//Towns visited so far
	private ArrayList<Integer> routeID;		//Route so far using ID
	
	public Waypoint (int num_towns) { //Generate Random Path
		routeID = new ArrayList<Integer>();
		visited = new boolean[Salesman.Town_Names.length];
		
		for (int k = 0; k < num_towns; k++) {routeID.add(k); visited[k] = true;}
		
		Collections.shuffle(routeID);
		
		calcCost();
	}
	
	public Waypoint (int[] route) {
		routeID = new ArrayList<Integer>();
		visited = new boolean[route.length];
		for (int i = 0; i < route.length; i++) {
       		addNext(route[i]);
        }
	}
	
	public void calcCost () { //Calculate complete cost of Path
		double tally = 0;
		int limit = routeID.size() - 1;
		for (int i = 0; i < limit; i++) {
			tally = tally + Salesman.Distances[routeID.get(i)][routeID.get(i+1)];
		}
		tally = tally + Salesman.Distances[routeID.get(limit)][routeID.get(0)];
		this.cost = tally;
	}
	
	public void printRoute () {
		System.out.println("Path is:");
		Iterator iterator = routeID.iterator();
	 	while (iterator.hasNext()) {
	 		System.out.print("." + ((int)iterator.next() + 1));
	 	}
	 }
	
	public double getCost () {
		if (cost == 0.0) {calcCost();}
		return cost;
	}
	
	public ArrayList<Integer> getRouteID () {return this.routeID;}
	
	public boolean isVisited (int point) {return this.visited[point];}
	
	public double twoopt (int index1, int index2){
		//System.out.println("\nBefore");
		//printRoute();
		int backup = getRouteID().get(index1);
		routeID.set(index1, routeID.get(index2));
		routeID.set(index2, backup);
		//System.out.println("\nAfter");
		//printRoute();
		calcCost();
		System.out.println("Cost after 2-opt: " + getCost());
		return getCost();
	}
	
	public void addNext (int point) {
		this.visited[point] = true;
		this.routeID.add(point);
	}
	
	public void addNth (int point, int index) {
		this.visited[point] = true;
		this.routeID.add(point, index);
	}
	
	public int getSize() {return this.routeID.size();}
	
	public int compareTo(Waypoint object){ //
       if(cost - object.getCost() > 0){ //compare the costs of the Tours
           return 1;
        }else if(cost - object.getCost() < 0){
           return -1;   //return 1 or -1 depending on whether these costs are bigger or smaller
        }else{
            return 0;   //return 0 if they're the same
        }
   }
	
		
}
