package LeagueOfLegends;

import java.util.List;
import java.util.NoSuchElementException;

import Json.LolAccount;
import Json.LolAccountSaved;
import Json.LolChampion;
import MegasusBOT.MegasusBOT;
import MegasusBOT.commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import net.rithms.riot.api.endpoints.spectator.dto.BannedChampion;
import net.rithms.riot.api.endpoints.spectator.dto.CurrentGameInfo;
import net.rithms.riot.api.endpoints.spectator.dto.CurrentGameParticipant;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

public class lollive {
	String name;
	Summoner summoner=null;
	String platform;
	boolean ready = false;

	public lollive(String[] args) throws RiotApiException {
		EmbedBuilder lol = new EmbedBuilder();
		if (args.length == 2) {
			if (LolAccountSaved.exist(commands.e.getMember().getIdLong())) {
				platform = LolAccount.ObjectByID("platform", commands.e.getMember().getIdLong());
				name = LolAccount.ObjectByID("name", commands.e.getMember().getIdLong());
				ready = true;
			}
		}
		if (args.length < 4 && !ready) {
			lol.setTitle(":upside_down: Please provide some arguments");
			lol.setDescription(MegasusBOT.prefix + "lol profile (Region) (SummonerName)");
			commands.e.getChannel().sendMessage(lol.build()).queue();
			return;
		}
		String[] Platforms = "eune euw jp kr na oce ru br tr".split(" ");
		boolean found = false;
		if(!ready)
		for (int i = 0; i < Platforms.length; i++) {
			if (args[2].equalsIgnoreCase(Platforms[i])) {
				args[2] = Platforms[i];
				found = true;
				break;
			}
		}
		if (found == false && !ready) {
			lol.setTitle(":upside_down: Unknown platform name: " + args[2]);
			lol.setDescription(MegasusBOT.prefix + "lol profile (eune/euw/jp/kr/na/oce/ru/br/tr) (SummonerName)");
			commands.e.getChannel().sendMessage(lol.build()).queue();
			return;
		}
		ApiConfig config = new ApiConfig().setKey(LeagueOfLegends.apikey);
		RiotApi api = new RiotApi(config);
		if (!ready)
			name = commands.e.getMessage().getContentRaw().substring((args[0] + args[1] + args[2]).length() + 3);
		try {
			try {
				summoner = api.getSummonerByName(Platform.getPlatformByName(ready ? platform : args[2]), name);
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
			lol.setTitle(":upside_down: Unknown platform name: " + (ready ? platform : args[2]));
			lol.setDescription(MegasusBOT.prefix + "lol profile (Region) (SummonerName)");
			commands.e.getChannel().sendMessage(lol.build()).queue();
			return;
		}
		try {
			CurrentGameInfo game = api.getActiveGameBySummoner(Platform.getPlatformByName(ready ? platform : args[2]),
					summoner.getId());
			List<BannedChampion> bc = game.getBannedChampions();
			List<CurrentGameParticipant> p = game.getParticipants();
			String type = game.getGameType();
			lol.setTitle(":red_circle: " + summoner.getName() + " is playing right now!");
			lol.appendDescription("GameType: " + type + "\n\n");
			lol.appendDescription("BannedChamps:\n");
			for (int i = 0; i < bc.size(); i++) {
				lol.appendDescription("<:" + LolChampion.ObjectByID("id", bc.get(i).getChampionId()) + ":"
						+ LolChampion.ObjectByID("emote", bc.get(i).getChampionId()) + ">");
				if (i == 4) {
					lol.appendDescription("      ");
				}
			}
			lol.addField("Blue Team",participant(p.get(0),args)+"\n"
					+ participant(p.get(1),args)+"\n"
					+ participant(p.get(2),args)+"\n"
					+ participant(p.get(3),args)+"\n"
					+ participant(p.get(4),args)+"\n",true);
			lol.addField("Red Team",participant(p.get(5),args)+"\n"
					+ participant(p.get(6),args)+"\n"
					+ participant(p.get(7),args)+"\n"
					+ participant(p.get(8),args)+"\n"
					+ participant(p.get(9),args)+"\n",true);
		} catch (RiotApiException e) {
			lol.setTitle(":anger: This summoner isn't playing right now");
		}
		commands.e.getChannel().sendMessage(lol.build()).queue();
	}
	public String participant(CurrentGameParticipant p, String[]args){
		ApiConfig config = new ApiConfig().setKey(LeagueOfLegends.apikey);
		RiotApi api = new RiotApi(config);
		String participant="";
		List<ChampionMastery> cm = null ;
		try {
			cm= api.getChampionMasteriesBySummoner(Platform.getPlatformByName(ready ? platform : args[2]),p.getSummonerId());
		} catch (RiotApiException e) {
			e.printStackTrace();
		}
		participant ="<:a:"+LolChampion.ObjectByID("emote", p.getChampionId())+">";
		participant +=" "+p.getSummonerName();
		String[] b = "621679927757897738 621679202542026763 621679868559753258 621678629432066078 621679751664369665 621677151296421888 621676671929417789"
				.split(" ");
		for(int i=0;i<cm.size();i++)
			if(p.getChampionId()==cm.get(i).getChampionId())
				participant +="<:a:"+b[cm.get(i).getChampionLevel()]+">"+pointsConvertor(cm.get(i).getChampionPoints());
		return participant;	
	}
	private String pointsConvertor(long points) {
		String pts = "";
		while (points != 0) {
			if (pts.length() == 3 || pts.length() == 7) {
				pts = "," + pts;
			}
			pts = points % 10 + pts;
			points = points / 10;
		}
		return pts;
	}
}
