package LeagueOfLegends;

import java.util.NoSuchElementException;

import Json.LolAccountSaved;
import MegasusBOT.MegasusBOT;
import MegasusBOT.commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

public class loladd {
	loladd(String[] args){
		EmbedBuilder lol = new EmbedBuilder();
		if (args.length < 4) {
			lol.setTitle(":upside_down: Please provide some arguments");
			lol.setDescription(MegasusBOT.prefix + "lol add (Region) (SummonerName)");
			commands.e.getChannel().sendMessage(lol.build()).queue();
			return;
		}
		String[] Platforms = "eune euw jp kr na oce ru br tr".split(" ");
		boolean found = false;
		for (int i = 0; i < Platforms.length; i++) {
			if (args[2].equalsIgnoreCase(Platforms[i])) {
				args[2] = Platforms[i];
				found = true;
				break;
			}
		}
		if (found == false) {
			lol.setTitle(":upside_down: Unknown platform name: " + args[2]);
			lol.setDescription(MegasusBOT.prefix + "lol add (eune/euw/jp/kr/na/oce/ru/br/tr) (SummonerName)");
			commands.e.getChannel().sendMessage(lol.build()).queue();
			return;
		}
		Summoner summoner = null;
		ApiConfig config = new ApiConfig().setKey(LeagueOfLegends.apikey);
		RiotApi api = new RiotApi(config);
		String name=commands.e.getMessage().getContentRaw().substring((args[0]+args[1]+args[2]).length()+3);
		try {
			try {
				summoner = api.getSummonerByName(Platform.getPlatformByName(args[2]),name);
			} catch (RiotApiException e) {
				if (e.getErrorCode() == 404) {
					lol.setTitle(":upside_down: Unknown summoner name: " + name);
					lol.setDescription(MegasusBOT.prefix + "lol profile (Region) (SummonerName)");
					commands.e.getChannel().sendMessage(lol.build()).queue();
					return;
				}
				e.printStackTrace();
			}
		} catch (NoSuchElementException e) {
			lol.setTitle(":upside_down: Unknown platform name: " + args[2]);
			lol.setDescription(MegasusBOT.prefix + "lol profile (Region) (SummonerName)");
			commands.e.getChannel().sendMessage(lol.build()).queue();
			return;
		}
		if(summoner== null){
			return;
		}
		LolAccountSaved.AccountSave(commands.e.getMember().getIdLong(), args[2], args[3]);
		commands.e.getChannel().sendMessage("Your account is saved on your discord id").queue();
	}
}
