import java.util.*;

public class Parser{

	public static Pair inputParser(String inputs){
	
		int num_towns = 100;
		String[] Town_Names = new String[num_towns];
		Double[] Lat = new Double[num_towns];
		Double[] Long = new Double[num_towns];
		Double[][] distances = new Double[num_towns][num_towns];
		
		//instantiate an instance of FileIO
        FileIO reader = new FileIO();

        //the code below shows how to load a file
        String[] raw_inputs = reader.load(inputs);

        //now you can process the input file as a String array
        for (int i = 0; i < num_towns; i++){
			String[] processing_string = raw_inputs[i].split(","); //Split string at spaces
			Town_Names[i] = processing_string[1];
			Lat[i] = Double.parseDouble(processing_string[2]);
			Long[i] = Double.parseDouble(processing_string[3]);
			//System.out.println(Town_Names[i] + " Lat: " + Lat[i] + " Long: " + Long[i]);
        }

		//Populate Distances Array
		for (int i = 0; i < num_towns; i++){ //For each town
			for (int j = 0; j < num_towns; j++){ //For each destination
				distances[i][j] = Calculator.distFrom(Lat[i], Long[i], Lat[j], Long[j]); //Add cost to Array
			}
		}
	
		Pair output = new Pair(distances, Town_Names);
		return output;
	}
}
