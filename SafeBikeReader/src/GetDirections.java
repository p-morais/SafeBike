import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Stack;
import java.util.*;
import org.json.*;

import javax.net.ssl.HttpsURLConnection;


public class GetDirections {

	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		GetDirections d = new GetDirections();
		ArrayList<Point> myPoints = d.sendGet("Duke University Golf Club, 3001 Cameron Boulevard, Durham, NC 27705", "Durham Bulls, 409 Blackwell Street, Durham, NC 27701");
		ReadPoints r = new ReadPoints();
		ArrayList<Accident> myAccidents = r.read("bike-data.json");
		System.out.println(myPoints);
		HashSet<Accident> allAccidents = d.accidentsOnWay(myPoints, myAccidents);
		for (Accident oopsies: allAccidents)
		{
			System.out.println(oopsies);
		}
	}

	private HashSet<Accident> accidentsOnWay(ArrayList<Point> points, ArrayList<Accident> accidents){
		HashSet<Accident> dangers = new HashSet<Accident>();
		for(int i=0; i<points.size() - 1; i++){
			Point first = points.get(i);
			Point second = points.get(i+1);
			double distance = first.getDistance(second);
			double a = first.getLatitude() - second.getLatitude();
			double b = first.getLongitude() - second.getLongitude();
			double j =100;
			while (j>0)		//how many points to check
			{
				Point p = new Point(first.getLatitude() + (a/j), first.getLongitude() + (b/j));
				j--;
				for (Accident incident: accidents)
				{
					if (incident.inRadius(p))
					{
						dangers.add(incident);
					}
				}
			}
		}
		return dangers;
	}
	
	private ArrayList<Point> sendGet(String start, String destination) throws Exception {

		start = start.replace(" ", "+");
		destination = destination.replace(" ", "+");
		
		String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + start + "&destination=" + destination + "&mode=biking&key=AIzaSyD7YNcgMTNrVTnrduaYUKCCIyKn3GW3VO4";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

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
		JSONObject json = new JSONObject(responseStr);
		
		JSONObject routes = json.getJSONArray("routes").getJSONObject(0);
		JSONObject legs = routes.getJSONArray("legs").getJSONObject(0);
		JSONArray steps = legs.getJSONArray("steps");
		
		ArrayList<Point> points = new ArrayList<>();
		
		for(int i=0; i<steps.length(); i++) {
			JSONObject step = steps.getJSONObject(i);
			//System.out.println(step);
			JSONObject st = step.getJSONObject("start_location");
			
			
			Point p = new Point(st.getDouble("lat"), st.getDouble("lng"));
			points.add(p);
			if(i == steps.length() - 1) {
				JSONObject end = step.getJSONObject("end_location");
				Point p1 = new Point(end.getDouble("lat"), end.getDouble("lng"));
				points.add(p1);
			}
		}
		return points;
		
		
	}
	
}
