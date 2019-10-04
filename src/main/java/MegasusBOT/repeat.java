package MegasusBOT;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class repeat {
	// vector de aparent
	public static Guild[] guild={null};
	public boolean problem;

	public repeat(String[] args, GuildMessageReceivedEvent event) {
		boolean repeat=false;
		try{
		for(int i = 0;i<guild.length;i++){
			if(guild[i]==event.getGuild()){
				repeat = true;
				break;
			}
		}
		}catch(NullPointerException e){}
		if (repeat) {
			repeat = false;
			for (int i = 0; i < guild.length; i++)
				if (event.getGuild() == guild[i])
					guild[i]=null;
		} else {
			repeat = true;
			try{
			guild[guild.length]=event.getGuild();
			}catch(ArrayIndexOutOfBoundsException e){
				guild[0]=event.getGuild();
			}
		}
		TextChannel tc = event.getChannel();
		EmbedBuilder r = new EmbedBuilder();
		r.setTitle(":repeat: Repeat: " + (repeat ? "ON" : "OFF"));
		r.setDescription("For remove traks from queue use skip comand");
		tc.sendMessage(r.build()).queue();
	}
}
