package Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LolChampion {
	public static String NameByID(int id) {
		JSONParser p = new JSONParser();

		try {
			Object o = p.parse(new FileReader("champions.json"));
			JSONArray a = (JSONArray) o;
			for (int i = 0; i < a.size(); i++) {
				JSONObject obj = (JSONObject) a.get(i);
				String key = (String) obj.get("key");
				if (key.equals(id + "")) {
					String name = (String) obj.get("name");
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

	public static String ObjectByName(String ob ,String x) {
		JSONParser p = new JSONParser();

		try {
			Object o = p.parse(new FileReader("champions.json"));
			JSONArray a = (JSONArray) o;
			for (int i = 0; i < a.size(); i++) {
				JSONObject obj = (JSONObject) a.get(i);
				String key = (String) obj.get("name");
				if (key.equalsIgnoreCase(x)) {
					String name = (String) obj.get(ob);
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
	
	public static String ObjectByID(String s, int id) {
		JSONParser p = new JSONParser();

		try {
			Object o= p.parse(new FileReader("champions.json"));
			JSONArray a = (JSONArray) o;
			for (int i = 0; i < a.size(); i++) {
				JSONObject obj = (JSONObject) a.get(i);
				String key = (String) obj.get("key");
				if (key.equals(id + "")) {
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

	public static String ObjectByNamePatch(String ob, String name) {
		JSONParser p = new JSONParser();

		try {
				Object o = p.parse(new FileReader("championsNEW.json"));
				JSONObject a = (JSONObject) o;
				JSONObject ab = (JSONObject) a.get("data");
				JSONObject abc = (JSONObject) ab.get(name);
				return (String) abc.get(ob);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object StatusByName(String ob, String name) {
		JSONParser p = new JSONParser();

		try {
				Object o = p.parse(new FileReader("championsNEW.json"));
				JSONObject a = (JSONObject) o;
				JSONObject ab = (JSONObject) a.get("data");
				JSONObject abc = (JSONObject) ab.get(name);
				JSONObject abcd = (JSONObject) abc.get("stats");
				Object x = abcd.get(ob);
				return x;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String TagsbyName(String name) {
		JSONParser p = new JSONParser();

		try {
			Object o;
			o = p.parse(new FileReader("championsNEW.json"));
			JSONObject a = (JSONObject) o;
			JSONObject ab = (JSONObject) a.get("data");
			JSONObject abc = (JSONObject) ab.get(name);
			JSONArray x = (JSONArray) abc.get("tags");
			String s="";
			for(int i=0;i<x.size();i++){
				s=s+x.get(i)+" ";
			}			
			return s;
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