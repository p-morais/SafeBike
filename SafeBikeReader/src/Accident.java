import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.*;

import javax.net.ssl.HttpsURLConnection;

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
	  public String print() throws Exception {
	        String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + myLatitude + "," + myLongitude + "&sensor=true";
	        
	        URL obj = new URL(url);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        
	        // optional default is GET
	        con.setRequestMethod("GET");
	        
	        //add request header
	        con.setRequestProperty("User-Agent", "Mozilla/5.0");
	        
	        int responseCode = con.getResponseCode();
	        //System.out.println("\nSending 'GET' request to URL : " + url);
	        //System.out.println(url);
	        //System.out.println("Response Code : " + responseCode);
	        
	        BufferedReader in = new BufferedReader(
	                                               new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        
	        //print result
	        //System.out.println(response.toString());
	        
	        String responseStr = response.toString();
	        JSONObject json = new JSONObject(responseStr).getJSONArray("results").getJSONObject(0);
	        String toReturn = json.getString("formatted_address");
	        return toReturn;
	    }
}
