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
import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.ParticipantStats;
import net.rithms.riot.api.endpoints.match.dto.TeamStats;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

public class lolprofile {
	boolean ready = false;
	String name;
	String platform;

	public lolprofile(String[] args) throws RiotApiException {
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
		if (!ready)
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
		Summoner summoner = null;
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
					lol.setDescription(MegasusBOT.prefix + "lol profile (Region) (SummonerName) \n" + "or "
							+ MegasusBOT.prefix + "add (Region) (SummonerName)");
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
		MatchList a = null;
		try {
			a = api.getMatchListByAccountId(Platform.getPlatformByName(ready ? platform : args[2]),
					summoner.getAccountId());
		} catch (RiotApiException e) {
			if (e.getErrorCode() == 404) {
				lol.setTitle(":upside_down: Unknown summoner name: " + name);
				lol.setDescription(MegasusBOT.prefix + "lol profile (Region) (SummonerName) \n" + "or "
						+ MegasusBOT.prefix + "add (Region) (SummonerName)");
				commands.e.getChannel().sendMessage(lol.build()).queue();
				return;
			}
		}
		long mid = a.getMatches().get(0).getGameId();
		Match last = api.getMatch(Platform.getPlatformByName(ready ? platform : args[2]), mid);
		lol.setTitle(":sparkles: Summoner: " + summoner.getName());
		lol.appendDescription("Level: " + summoner.getSummonerLevel() + "\n");
		lol.appendDescription("Region: " + Platform.getPlatformByName(ready ? platform : args[2]).getName());
		lol.setThumbnail("https://ddragon.leagueoflegends.com/cdn/9.16.1/img/profileicon/" + summoner.getProfileIconId()
				+ ".png");
		List<ChampionMastery> cm = api.getChampionMasteriesBySummoner(
				Platform.getPlatformByName(ready ? platform : args[2]), summoner.getId());
		String c = "";
		if (cm.size() >= 3) {
			String[] b = "621679927757897738 621679202542026763 621679868559753258 621678629432066078 621679751664369665 621677151296421888 621676671929417789"
					.split(" ");
			for (int i = 0; i < 3; i++) {
				c = c/* champ */ + "<:" + LolChampion.ObjectByID("id", cm.get(i).getChampionId()) + ":"
						+ LolChampion.ObjectByID("emote", cm.get(i).getChampionId()) + ">"
						/* champ_name */ + LolChampion.NameByID(cm.get(i).getChampionId()) + " "
						/* mastery */ + "<:mastery:" + b[cm.get(i).getChampionLevel() - 1] + ">" + " "
						/* points */ + pointsConvertor(cm.get(i).getChampionPoints()) + " pts\n";

			}
		}
		lol.addField("Mains:", c, true);
		long l;
		@SuppressWarnings("unused")
		int ratio;
		int s = 0;
		float k = 0, d = 0, as = 0;
		try{
		for (int i = 0; i < 10; i++) {
			l = a.getMatches().get(i).getGameId();
			TeamStats ts = api.getMatch(Platform.getPlatformByName(ready ? platform : args[2]), l)
					.getTeamByTeamId(api.getMatch(Platform.getPlatformByName(ready ? platform : args[2]), l)
							.getParticipantBySummonerName(name).getTeamId());
			if (ts.getWin().getBytes().length == 3)
				s++;
			ParticipantStats stats = api.getMatch(Platform.getPlatformByName(ready ? platform : args[2]), l)
					.getParticipantBySummonerName(name).getStats();
			k = k + stats.getKills();
			d = d + stats.getDeaths();
			as = as + stats.getAssists();
		}
		}catch(NullPointerException e){
			
		}
		int lg = s * 100 / 10;
		String score = k / 10 + "/" + d / 10 + "/" + as / 10;
		lol.addField("Ratio/KDR(last 10 matches)", lg + "%" + " / " + score, true);
		ParticipantStats ls = api
				.getMatch(Platform.getPlatformByName(ready ? platform : args[2]), a.getMatches().get(0).getGameId())
				.getParticipantBySummonerName(name).getStats();
		int ki = ls.getKills(), dr = ls.getDeaths(), asd = ls.getAssists();
		score = ki + "/" + dr + "/" + asd;
		lol.addField("LastGame:", "Lane:\n" + "Champion:\n" + "Score\n" + "Mode:\n" + "Type:\n", true);
		lol.addField("",
				a.getMatches().get(0).getLane() + "\n"
						+ LolChampion.ObjectByID("name", a.getMatches().get(0).getChampion()) + "\n" + score + "\n"
						+ last.getGameMode() + "\n" + last.getGameType() + "\n",
				true);
		// last.getParticipantBySummonerName(summoner.getName()).getStats().get
		lol.appendDescription("\n Last game: " + a.getMatches().get(0).getLane()
				+ LolChampion.ObjectByID("name", a.getMatches().get(0).getChampion()));
		commands.e.getChannel().sendMessage(lol.build()).queue();
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
