package MegasusBOT;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class spamevent {
	Member lastm = null;
	List<Member> a;
	int i;

	spamevent(){
		Member m = commands.e.getMember();
		if(m!=lastm){
		lastm = m;
		i=0;
		}
		else{
			if(a!=commands.e.getMessage().getMentionedMembers())
		a = commands.e.getMessage().getMentionedMembers();
		for(int i=0;i<a.size();i++)
			for(int j=0;j<commands.e.getMessage().getMentionedMembers().size();j++)
		if(commands.e.getMessage().getMentionedMembers().get(i)==a.get(j)){
			i++;
		}
		if(i == 3){
			List<Message> Messages = commands.e.getChannel().getHistory().retrievePast(3)
					.complete();
			commands.e.getChannel().deleteMessages(Messages).queue();
		}else{
			if(i>3){
				List<Message> Messages =commands.e.getChannel().getHistory().retrievePast(1)
						.complete();
				commands.e.getChannel().deleteMessages(Messages).queue();
			}
		}
		int x = Math.min(i, 4);
		for(int j=0;j<x;j++){
			commands.e.getChannel().sendMessage(m.getAsMention()+" NU MAI SPAMA COPILE");
		}
		}
	}
}
