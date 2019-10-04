package MegasusBOT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import Music.GuildMusicManager;
import Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class next {

	public next(List<String> args, GuildMessageReceivedEvent event) {
		TextChannel channel = event.getChannel();
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer player = musicManager.player;
		List<AudioTrack>queue =new ArrayList<AudioTrack>(musicManager.scheduler.getQueue());
		
		if (player.getPlayingTrack() == null) {
			channel.sendMessage("The player is not playing any song.").queue();

			return;
		}
		
		if(queue.size()==0){
			EmbedBuilder e = new EmbedBuilder();
			e.setTitle("Nothing next , queue is clear");
			channel.sendMessage(e.build()).queue();
			return;
		}

		AudioTrackInfo info = queue.get(0).getInfo();
		EmbedBuilder n = new EmbedBuilder();
		n.setTitle(":thinking: Next play :");
		n.setDescription(String.format("**Playing** [%s](%s)\n%s %s", info.title, info.uri,"Duration: ",
				formatTime(queue.get(0).getDuration())));
		channel.sendMessage(n.build()).queue();
	}

	private String formatTime(long timeInMillis) {
		final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
		final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
		final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}
