package LeagueOfLegends;

import MegasusBOT.MegasusBOT;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class lolhelp {
	public lolhelp(GuildMessageReceivedEvent event) {
		EmbedBuilder help = new EmbedBuilder();
		help.setTitle(":sos: (!!!BETA!!!) League of Legends commands:");
		help.setDescription(MegasusBOT.prefix + "lol (profile,add,remove)\n Baza de date este neterminata, se lucreaza la baza de date la iteme si comenzile lol champ,live");
		event.getChannel().sendMessage(help.build()).queue();
	}
}
