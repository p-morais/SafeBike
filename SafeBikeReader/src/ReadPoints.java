import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadPoints {
    public static void main(String[] args) throws FileNotFoundException,
            IOException, ParseException {
    	
    	ArrayList<Accident> oops = new ArrayList<>();
    	FileReader file = new FileReader("bike-data.json");
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(file);
        
        for (Object o : jsonArray) {
            JSONObject accident = (JSONObject) o;
            JSONObject fields = (JSONObject)accident.get("fields");
            JSONArray location = (JSONArray)fields.get("location");
            double l1 = (double) location.get(0);
            double l2 = (double) location.get(1);
            String description = (String)fields.get("crash_type");
            
             oops.add(new Accident(l1, l2, description));
        }
        for (Accident a:oops)
        {
        	System.out.println(a);
        }

    }
  
}
