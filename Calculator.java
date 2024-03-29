class Calculator

{

	public static void main(String[] args){
		System.out.println(distFrom(53.382, -6.591, 40.689167, -74.044444));
	}


	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        	double earthRadius = 6371.009;
	        double dLat = Math.toRadians(lat2-lat1);
	        double dLng = Math.toRadians(lng2-lng1);
        	double sindLat = Math.sin(dLat / 2);
	        double sindLng = Math.sin(dLng / 2);
	        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	               * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
	        double dist = earthRadius * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return dist;
	}
}
