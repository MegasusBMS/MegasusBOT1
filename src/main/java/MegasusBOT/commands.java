package MegasusBOT;

import java.util.ArrayList;
import java.util.List;

import LeagueOfLegends.LeagueOfLegends;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class commands extends ListenerAdapter{
	boolean mentenance;
	public String arg;
	public static GuildMessageReceivedEvent e;
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		e = event;
		String [] args = event.getMessage().getContentRaw().split("\\s+");
		List<String> msg = new ArrayList<String>();
		for(int i = 0; i<args.length; i++){
			msg.add(args[i]);
		}
		if(event.getGuild().getSelfMember()!=event.getMember())
		if(args[0].startsWith(MegasusBOT.prefix)){
			String c = args[0];
			String[] i = "clear status ban leave join volume queue stop play skip kick repeat np nowplay support next profile lol spam shutdown mentenance".split(" ");
			int command = 100;
			for(int j=0;j<i.length;j++){
				if((MegasusBOT.prefix+i[j]).equalsIgnoreCase(c)){
					command=j;
					break;
				}
			}
			if(mentenance&&!event.getMember().isOwner()){
				event.getChannel().sendMessage("i'm in mentenance");
				return;
			}
			switch (command){
			case 0:
				new clear(args,event);
				break;
			case 1:
				new status(event);
				break;
			case 2:
				new ban(msg,event);
				break;
			case 3:
				new leave(event);
				break;
			case 4:
				new join(event);
				break;
			case 5:
				new volume(args,event);
				break;
			case 6:
				new queue(args,event);
				break;
			case 7:
				new stop(event);
				break;
			case 8:
				new Play(args,event,true);
				break;
			case 9:
				new skip(msg,event);
				break;
			case 10:
				new kick(msg,event);
				break;
			case 11:
				new repeat(args,event);
				break;
			case 12:
				new NowPlay(msg,event);
				break;
			case 13:
				new NowPlay(msg,event);
				break;
			case 14:
				EmbedBuilder support = new EmbedBuilder();
				support.setTitle(":hugging: Support");
				event.getChannel().sendMessage(support.build()).queue();
				event.getChannel().sendMessage("https://discord.gg/B76kauM").queue();
				break;
			case 15:
				new next(msg,event);
				break;
			case 16:
				new profile(msg,event);
				break;
			case 17:
				new LeagueOfLegends(args,event);
				break;
			case 18:
				new spam(args,event);
				break;
			case 19:
				if(event.getMember().isOwner()){
					event.getJDA().shutdown();
					System.out.println("SutingDown...");
					System.exit(10);
				}else
					new help(event);
				break;
			case 20:
				mentenance=!mentenance;
				event.getChannel().sendMessage("MentenanceStatus: "+mentenance).queue();
				break;
			default :
				new help(event);
				break;
			}
		}else{
			//if(event.getMember().getId()!="603476324195237908")
			//new spamevent();
		}
	}
}