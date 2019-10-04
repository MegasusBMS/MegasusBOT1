package Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LolAccount {
	public static String ObjectByID(String s,long id) {
		JSONParser p = new JSONParser();
		
		try {
			Object o = p.parse(new FileReader("LolAccountSaved.json"));
			JSONArray a = (JSONArray) o;
			for(int i=0;i<a.size();i++){
			JSONObject obj = (JSONObject)a.get(i);
		    long key =(long)obj.get("id");
		    if(key==id){
		    	String name = (String) obj.get(s);
		    	return name;
		    }
			}
			return "N/A";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	return null;
	}
}