package Json;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import MegasusBOT.MegasusBOT;
import MegasusBOT.commands;
import net.dv8tion.jda.api.EmbedBuilder;

public class LolAccountSaved {
	@SuppressWarnings("unchecked")
	public static void AccountSave(long id, String platform, String name) {
		if(exist(id)){
			EmbedBuilder e = new EmbedBuilder();
			e.setTitle(":anger: Your account is allready in our database");
			e.setDescription("If you want to unload it use "+MegasusBOT.prefix+"lol remove");
			commands.e.getChannel().sendMessage(e.build()).queue();
			return;
		}
		JSONObject list = new JSONObject();
		JSONArray a = new JSONArray();
		list.put("id", id);
		list.put("platform", platform);
		list.put("name", name);
		JSONArray e = existedAccontns();
		try {
			for(int i=0;i<e.size();i++){
				a.add(e.get(i));
			}
		} catch (NullPointerException e2) {
		}
		a.add(list);
		try (FileWriter f = new FileWriter("LolAccountSaved.json")) {
			f.write(a.toString());
			f.flush();
		} catch (IOException b) {
			b.printStackTrace();
		}
	}
	public static JSONArray existedAccontns(){
		JSONParser p = new JSONParser();
		Object o;
		try {
			o = p.parse(new FileReader("LolAccountSaved.json"));
			JSONArray a = (JSONArray) o;
			return a;
		} catch (IOException | ParseException e) {
			return null;
		}
	}
	public static boolean exist (long id){
		JSONArray a = existedAccontns();
		try {
			a.size();
		} catch (NullPointerException e) {
			return false;
		}
		for(int i = 0;i<a.size();i++){
			JSONObject e = (JSONObject) a.get(i);
			String key = e.get("id").toString();
			 if(key.equals(id+"")){
				 return true;
			    }
		}
		return false;
	}
}