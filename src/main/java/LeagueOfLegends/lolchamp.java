package LeagueOfLegends;

import Json.LolChampion;
import MegasusBOT.commands;
import net.dv8tion.jda.api.EmbedBuilder;

public class lolchamp {
	lolchamp(String[] args){
		String name=commands.e.getMessage().getContentRaw().substring((args[0]+args[1]).length()+2);
		name=LolChampion.ObjectByName("name", name);
		String t = name;
		if(name.contains(" ")){
			String[]x = name.split(" ");
			name = x[0]+x[1];
		}else
		if(name.contains("\'")){
			String[]x=name.split("\'");
			name = x[0]+x[1];
		}
		EmbedBuilder c = new EmbedBuilder();
		if(LolChampion.ObjectByName("emote", name)!=null)
			c.setTitle("<:"+LolChampion.ObjectByName("id", t)+":"+LolChampion.ObjectByName("emote", t)+">"+LolChampion.ObjectByName("name", t)+", "+LolChampion.ObjectByName("title", t)+" (patch 9.18.1)");
		else
		c.setTitle(name+", "+LolChampion.ObjectByNamePatch("title", name)+" (patch 9.18.1)");
		try{
		c.setThumbnail(LolChampion.ObjectByName("icon", t));
		}catch(IllegalArgumentException e){
			EmbedBuilder x = new EmbedBuilder();
			x.setTitle("This champ not exist");
			commands.e.getChannel().sendMessage(x.build()).queue();
			return;
		}
		c.appendDescription("Champ: "+ LolChampion.ObjectByName("name", t) +"\n");
		c.setFooter(LolChampion.ObjectByName("description", t));
		c.appendDescription("Tags : "+LolChampion.TagsbyName(name)+"\n");
		c.addField("StartStatus", "HP:\n"
				+ "DMG:\n"
				+ "Mana\n"
				+ "Armor:\n"
				+ "MagicR.:\n"
				+ "Range:\n"
				+ "HPRegen:\n"
				+ "ManaRegen:\n", true);
		c.addField("", LolChampion.StatusByName("hp", name)+"\n"
				+ LolChampion.StatusByName("attackdamage", name)+"\n"
				+ LolChampion.StatusByName("mp", name)+"\n"
				+ LolChampion.StatusByName("armor", name)+"\n"
				+ LolChampion.StatusByName("spellblock", name)+"\n"
				+ LolChampion.StatusByName("attackrange", name)+"\n"
				+ LolChampion.StatusByName("hpregen", name)+"\n"
				+ LolChampion.StatusByName("mpregen", name)+"\n", true);				
		String[] x;
		if(LolChampion.ObjectByName("ao", t)!=null){
			x = LolChampion.ObjectByName("ao", t).split(" ");
		}
		else{
			x = "q w e q q r q e q e r e e w w r w w".split(" ");
		}
		c.appendDescription("Ability Order:\n");
			for(int i=0;i<x.length;i++){
				x[i]=":regional_indicator_"+x[i]+":";
				if(i!=x.length-1){
					x[i]=x[i]+">>";
				}else{
					x[i]=x[i]+"\n";
				}
				c.appendDescription(x[i]);
			}
		commands.e.getChannel().sendMessage(c.build()).queue();
	}
}
