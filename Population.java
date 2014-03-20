import java.util.*;

public class Population {

    private ArrayList<Waypoint> Paths;
    
    public Population (int popSize, boolean creates) { //Constructor for Gene Pool/Random Paths
        Paths = new ArrayList <Waypoint> ();
        if (creates){
        	for (int i = 0; i < popSize; i++){
        	    Paths.add(i, new Waypoint(Salesman.Town_Names.length));
       		}
       	}
    }

    public Population (Waypoint PathA, Waypoint PathB, Waypoint PathC, Waypoint PathD, Waypoint PathE) { //Select Pond for evolution
        Paths = new ArrayList<Waypoint>(); //Fittest come first
        Paths.add(PathA); Paths.add(PathB); Paths.add(PathC); Paths.add(PathD); Paths.add(PathE);
    }

	public Waypoint[] getShortestNPaths(int num) {
		Collections.sort(Paths);
		Waypoint[] shortest = new Waypoint[num];
		for (int i = 0; i < num; i++) {
			shortest[i] = getPath(i);
		}
		return shortest;
	}
	
    public Population createPool() { //Create a pool for evolution. Paths are unique and removed from population
        Population subset = new Population(5, false);
        TreeSet<Integer> choices = new TreeSet<Integer>(); //Use set to ensure same Path is not chosen
        
        while (choices.size() != 5) {
            choices.add((int)(Math.random()*Salesman.popSize));
        }
        
        Iterator<Integer> iterator = choices.descendingIterator();
        for (int i = 0; i < 5; i++) {
            subset.addPath(getPath(iterator.next()));
        }
        
        return subset;
    }

    public ArrayList<Waypoint> getWaypoints () {return Paths;}

    public void addPaths(Population arrivals) { //Add new Paths and shuffle
        Waypoint[] inputs = arrivals.getWaypoints().toArray(new Waypoint[arrivals.getSize()]);
        for (int i = 0; i < arrivals.getSize(); i++) {
            addPath(inputs[i]);
        }
        Collections.shuffle(Paths);
    }

    public Waypoint getFittest() {
        Waypoint fittest = Paths.get(0);
        for (int i = 1; i< Paths.size(); i++) {
            if (fittest.getCost() > Paths.get(i).getCost()){
                fittest = Paths.get(i);
            }
        }
        return fittest;
    }

    public void setPath(int index, Waypoint path) {Paths.add(index, path);}

    public void removePath(Waypoint path) {Paths.remove(path);}

    public void addPath(Waypoint path) {Paths.add(path);}

    public Waypoint getPath(int index) {return Paths.get(index);}

    public int getSize() {return Paths.size();}
}
