public class Accident{
	
	double myLatitude;
	double myLongitude;
	String myDescription;
	long objectid;
	
	public Accident(double l1, double l2, String description, long num)
	{
		myLatitude = l1;
		myLongitude = l2;
		myDescription = description; 
		objectid = num;
	}
	
	public String toString()
	{
		return objectid + ": " + myDescription + ": [" + myLatitude + ", " + myLongitude + "]";
	}
	
	public boolean inRadius(Point p)
	{
		double lat = p.getLatitude() - myLatitude;
		double longitude = p.getLongitude() - myLongitude;
		double squ = Math.pow(longitude*longitude + lat*lat, .5);
		if (squ < .0009)
		{
			return true;
		}
		return false;
	}
}
