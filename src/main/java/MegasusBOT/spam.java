package MegasusBOT;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class spam {
	spam(String[] args, GuildMessageReceivedEvent event){
		Member m = event.getMember();
		if(!m.hasPermission(Permission.MANAGE_SERVER)&&!m.isOwner()){
			EmbedBuilder usage = new EmbedBuilder();
			usage.setTitle(":sweat_smile: You don't have permision to use this command");
			event.getChannel().sendMessage(usage.build()).queue();
			return;
		}
		if(args.length<2){
			EmbedBuilder usage = new EmbedBuilder();
			usage.setTitle(":sweat_smile: Specify amount to delete");
			usage.setDescription("Usage: " + MegasusBOT.prefix + "lol [tag] (number of tags)");
			event.getChannel().sendMessage(usage.build()).queue();
			return;
		}
		int nr = 0;
		try{
		nr = Integer.parseInt(args[args.length-1]);
		}catch(NumberFormatException e){
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle(":red_circle:  You used a malformed argument!");
			event.getChannel().sendMessage(error.build()).queue();
			return;
		}
		nr = Math.min(nr, 10);
		List<Member> tag = event.getMessage().getMentionedMembers();
		String x = "";
		for(int j=0; j<tag.size();j++){
			x=x+" "+tag.get(j).getAsMention();
		}
		for(int i=0;i<nr;i++){
			event.getChannel().sendMessage(x).queue();
		}
	}
}
