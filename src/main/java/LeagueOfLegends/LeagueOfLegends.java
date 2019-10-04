package LeagueOfLegends;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.rithms.riot.api.RiotApiException;

public class LeagueOfLegends {
	public static String apikey = "RGAPI-5250efd5-402e-4e51-a25a-2b66654ae7ad";
	public LeagueOfLegends(String[] args, GuildMessageReceivedEvent event) {
		String[] i = "profile live add remove p champ champion c".split(" ");
		int command = 100;
		if(args.length==1){
			new lolhelp(event);
			return;
		}
		for (int j = 0; j < i.length; j++) {
			if (i[j].equalsIgnoreCase(args[1])) {
				command = j;
				break;
			}
		}
		switch (command) {
		case 0:
			try {
				new lolprofile(args);
			} catch (RiotApiException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				new lollive(args);
			} catch (RiotApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			new loladd(args);
			break;
		case 3:
			new lolremove();
			break;
		case 4:
			try {
				new lolprofile(args);
			} catch (RiotApiException e) {
				e.printStackTrace();
			}
			break;
		case 5:
		case 6:
		case 7:
			new lolchamp(args);
			break;
		default:
			new lolhelp(event);
			break;
		}
	}

}
