package LeagueOfLegends;

import Json.LolAccountRemove;
import Json.LolAccountSaved;
import MegasusBOT.commands;

public class lolremove {
	lolremove(){
		if(!LolAccountSaved.exist(commands.e.getMember().getIdLong())){
			commands.e.getChannel().sendMessage("you don't saved your account").queue();
			return;
		}
		LolAccountRemove.AccountRemove(commands.e.getMember().getIdLong());
		commands.e.getChannel().sendMessage("your account had been removed").queue();
	}
}
