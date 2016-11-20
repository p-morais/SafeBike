public class Point{
	Double longitude, latitude;
	
	public Point(double one, double two){
		latitude = one;
		longitude = two;
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public double getLongitude(){
		return longitude;
	}
	

	public double getDistance(Point p){
		double lat = p.getLatitude() - this.getLatitude();
		double longitude = p.getLongitude() - this.getLongitude();
		double squ = Math.pow(longitude*longitude + lat*lat, .5);
		return squ;
	}
    
    public boolean isWithin(Point other) {
        double radius = 0.0;    //this is changed when we decide how far/close two points need to be
        if(getDistance(other) < radius) return true;
        return false;
    }
}
