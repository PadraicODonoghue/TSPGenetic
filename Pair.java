public class Pair {
	//Used to return two objects from inputParser method
    private final Double[][] a;
    private final String[] b;

    public Pair(Double[][] a, String[] b) {
        this.a = a;
        this.b = b;
    }
    
    public Double[][] getFirst() {
        return this.a;
    }

    public String[] getSecond() {
        return this.b;
    }

}
