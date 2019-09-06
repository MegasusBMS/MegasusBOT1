package MegasusBOT;

import java.util.ArrayList;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Music.GuildMusicManager;
import Music.PlayerManager;
import Music.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class repeat {
	public static boolean repeatsong = false;
	public static boolean repeatqueue = false;
	public boolean problem;
	public static String[] song;

	public repeat(String[] args, GuildMessageReceivedEvent event) {
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		TrackScheduler scheduler = musicManager.scheduler;
		AudioPlayer player = musicManager.player;
		EmbedBuilder r = new EmbedBuilder();
		if (args.length == 1) {
			r.setTitle(":upside_down: Please provide some arguments");
			r.setDescription(MegasusBOT.prefix + "repeat [queue/song] (on/off)");
			event.getChannel().sendMessage(r.build()).queue();
			return;
		}
		if(!args[1].equalsIgnoreCase("queue")&&!args[1].equalsIgnoreCase("song")){
			r.setTitle(":upside_down:Wrong arguments");
			r.setDescription(MegasusBOT.prefix + "repeat [queue/song] (on/off)");
			event.getChannel().sendMessage(r.build()).queue();
			return;
		}
		if (player.getPlayingTrack() == null) {
			commands.e.getChannel().sendMessage("The player is not playing any song.").queue();

			return;
		}
		if (args[1].equalsIgnoreCase("queue")) {
			repeatsong = false;
			if(args.length==2){
				repeatqueue = repeatqueue ? false : true;
			}else{
				if(args.length==3){
					repeatqueue = args[2].equalsIgnoreCase("on") ? true
							: args[2].equalsIgnoreCase("off") ? false : wrong(event, repeatqueue);
					if (problem) {
						problem = false;
						return;
					}
				}
			}
			if(repeatqueue)
			r.setTitle(":repeat_one: Repeat queue : ON");
		else
			r.setTitle(":repeat_one: Repeat queue : OFF");
			song = (MegasusBOT.prefix + "play " + player.getPlayingTrack().getInfo().title).split(" ");
			new Play(repeat.song, commands.e, false);
			List<AudioTrack> list = new ArrayList<AudioTrack>(musicManager.scheduler.getQueue());
			song = (MegasusBOT.prefix + "play " +list.get(0).getInfo().title).split(" ");
		event.getChannel().sendMessage(r.build()).queue();
			return;
		}
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("song")) {
				repeatqueue = false;
				repeatsong = repeatsong ? false : true;
				if (repeatsong)
					r.setTitle(":repeat_one: Repeat song : ON");
				else
					r.setTitle(":repeat_one: Repeat song : OFF");
				event.getChannel().sendMessage(r.build()).queue();
			}
		}
		if (args.length > 2) {
			if (args[1].equalsIgnoreCase("song")) {
				repeatqueue = false;
				if (player.getPlayingTrack() == null) {
					commands.e.getChannel().sendMessage("The player is not playing any song.").queue();

					return;
				}
				repeatsong = args[2].equalsIgnoreCase("on") ? true
						: args[2].equalsIgnoreCase("off") ? false : wrong(event, repeatsong);
				if (problem) {
					problem = false;
					return;
				}
			}

			if (repeatsong)
				r.setTitle(":repeat_one: Repeat song : ON");
			else
				r.setTitle(":repeat_one: Repeat song : OFF");
			event.getChannel().sendMessage(r.build()).queue();
		}
		if (repeatsong) {
			scheduler.queueclear();
			song = (MegasusBOT.prefix + "play " + player.getPlayingTrack().getInfo().title).split(" ");
			new Play(song, commands.e, false);
		} else {
			scheduler.reloadqueue();
		}
	}

	private boolean wrong(GuildMessageReceivedEvent event, boolean returned) {
		EmbedBuilder r = new EmbedBuilder();
		r.setTitle(":rolling_eyes: Pls insert ON or OFF");
		r.setDescription(MegasusBOT.prefix + "repeat [queue/song] (on/off)");
		event.getChannel().sendMessage(r.build()).queue();
		problem = true;
		return returned;
	}

}
