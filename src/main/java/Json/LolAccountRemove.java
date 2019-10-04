package Json;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LolAccountRemove {
	@SuppressWarnings("unchecked")
	public static void AccountRemove(long id) {
		JSONArray a = new JSONArray();
		JSONArray e = LolAccountSaved.existedAccontns();
		try {
			for(int i=0;i<e.size();i++){
				JSONObject o = (JSONObject) e.get(i);
				String member = o.get("id").toString();
				if(!member.equals(id+""))
				a.add(e.get(i));
			}
		} catch (NullPointerException e2) {
		}
		try (FileWriter f = new FileWriter("LolAccountSaved.json")) {
			f.write(a.toString());
			f.flush();
		} catch (IOException b) {
			b.printStackTrace();
		}
	}
}
