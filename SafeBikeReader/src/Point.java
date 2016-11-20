
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
	
	public String toString()
	{
		return ": [" + latitude + ", " +longitude + "]";
	}

	public double getDistance(Point p){
		double lat = p.getLatitude() - this.getLatitude();
		double longitude = p.getLongitude() - this.getLongitude();
		double squ = Math.pow(longitude*longitude, lat*lat);
		return squ;
	}
}
