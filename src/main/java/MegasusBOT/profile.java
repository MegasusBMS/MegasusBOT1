package MegasusBOT;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class profile {
	profile(List<String> msg, GuildMessageReceivedEvent event) {
		EmbedBuilder p = new EmbedBuilder();
		Member m;
		if (msg.size() == 2 && event.getMessage().getMentionedMembers().size() != 0) {
			m = event.getMessage().getMentionedMembers().get(0);
		} else {
			m = event.getMember();
		}
		p.setTitle(":performing_arts: Profile: Name: " + m.getUser().getAsTag());
		switch (m.getOnlineStatus()) {
		case DO_NOT_DISTURB:
			p.appendDescription("Status: Do not disturb :red_circle:");
			break;
		case IDLE:
			p.appendDescription("Status: Idle :clock11:");
			break;
		case INVISIBLE:
			p.appendDescription("Status: Invisible (\"Nice trye\") :wink:");
			break;
		case OFFLINE:
			p.appendDescription("Status: Offline :white_circle:");
			break;
		case ONLINE:
			p.appendDescription("Status: Online :white_check_mark:");
			break;
		default:
			p.appendDescription("Status: Unknow :shrug:");
			break;
		}
		p.appendDescription("\n\nID: " + m.getId() + "\n");
		p.setThumbnail(m.getUser().getAvatarUrl());
		p.appendDescription("Names: " + m.getEffectiveName() + "\n");
		if (m.getVoiceState().inVoiceChannel()) {
			if (event.getMember().hasPermission(m.getVoiceState().getChannel(), Permission.VOICE_CONNECT)) {
				p.appendDescription(
						"You can find him on this VoiceChannel: " + m.getVoiceState().getChannel().getName() + "");
			} else
				p.appendDescription("He is not connected to any VoiceChanel");
		} else
			p.appendDescription("He is not connected to any VoiceChanel");
		List<Role> r = m.getRoles();
		p.appendDescription("\nRoles:");
		for (int i = 0; i < Math.min(r.size(), 10); i++) {
			if (i % 2 == 0) {
				p.appendDescription("\n");
			}
			p.appendDescription(r.get(i).getName() + "; ");
		}
		event.getChannel().sendMessage(p.build()).queue();
	}
}
