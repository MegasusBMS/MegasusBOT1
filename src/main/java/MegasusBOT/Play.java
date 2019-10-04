package MegasusBOT;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.Nullable;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

import Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Play {
	private YouTube YouTube = null;
	public static String input;
	public static boolean b;

	public Play(String[] args, GuildMessageReceivedEvent event, boolean bo) {
		TextChannel channel = event.getChannel();
		AudioManager am = event.getGuild().getAudioManager();
		TextChannel tc = event.getChannel();
		PlayerManager mm = PlayerManager.getInstance();
		GuildVoiceState vs = event.getMember().getVoiceState();
		VoiceChannel vc = vs.getChannel();
		b = bo;
		if (b) {
			if (args.length < 2) {
				EmbedBuilder play = new EmbedBuilder();
				play.setTitle(":upside_down: Please provide some arguments");
				play.setDescription(MegasusBOT.prefix + "play [url/yt_id/title/thumbnails]");
				channel.sendMessage(play.build()).queue();

				return;
			}

			if (!vs.inVoiceChannel()) {
				EmbedBuilder j = new EmbedBuilder();
				j.setTitle(":sweat_smile: You are not connected to a voice channel");
				j.setDescription("First join to a voice channel");
				tc.sendMessage(j.build()).queue();
				return;
			}

			Member sm = event.getGuild().getSelfMember();
			if (!sm.hasPermission(vc, Permission.VOICE_CONNECT)) {
				EmbedBuilder j = new EmbedBuilder();
				j.setTitle(":cry: I dont have permision to connect to a voice channel");
				j.setDescription("say to owner or an administrator to give me this permision");
				tc.sendMessage(j.build()).queue();
				return;
			}
			if (!am.isConnected()) {
				mm.getGuildMusicManager(event.getGuild()).player.setVolume(50);
				am.openAudioConnection(vc);
			}
		}
		YouTube temp = null;
		try {
			temp = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(),
					JacksonFactory.getDefaultInstance(), null).setApplicationName("MegasusBOT JDA bodt").build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PlayerManager manager = PlayerManager.getInstance();
		YouTube = temp;
		if(args[1].startsWith("https://www.youtube.com/watch?v=")){
			manager.loadAndPlay(event.getChannel(), args[1]);
			return;
		}

		String imput = "";
		for (int i = 1; i < args.length; i++) {
			imput = imput + args[i] + " ";
		}
		input = imput;

		if (!isUrl(input)) {
			String ytSearched = searchYoutube(input);

			if (ytSearched == null) {
				EmbedBuilder play = new EmbedBuilder();
				play.setTitle(":thinking: I don't find this input");
				play.setDescription("Are you sure , this is what you want to play?");
				channel.sendMessage(play.build()).queue();

				return;
			}

			input = ytSearched;
		}
		manager.loadAndPlay(event.getChannel(), input);
	}

    private boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

	@Nullable
	private String searchYoutube(String input) {
		try {
			List<SearchResult> results;
			try {
				results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(1L).setType("video")
						.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
						.setKey("AIzaSyBXqUY-vhlfNBzKTsu9UTMH7QvX2ErNSmQ").execute().getItems();
			} catch (GoogleJsonResponseException e) {
				try {
					results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(1L).setType("video")
							.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
							.setKey("AIzaSyAA6kvAk52KNEjW1y_J0YZYmeWTE2jkI_c").execute().getItems();
				} catch (GoogleJsonResponseException q) {
					try {
						results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(1L).setType("video")
								.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
								.setKey("AIzaSyCutbHqKXSiQF-q7ATSG7ZxJ8i51Y0bapQ").execute().getItems();
					} catch (GoogleJsonResponseException w) {
						try {
							results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(1L).setType("video")
									.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
									.setKey("AIzaSyBh786UQseUgfqUv8vEXb3if4m_ydNNQPs").execute().getItems();
						} catch (GoogleJsonResponseException a) {
							results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(1L).setType("video")
									.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
									.setKey("AIzaSyDHZEaWZsE-FT7YBlIIoMbaRosOjzHpo6A").execute().getItems();

						}
					}
				}
			}
			if (!results.isEmpty()) {
				String videoId = results.get(0).getId().getVideoId();
				
				return "https://www.youtube.com/watch?v=" + videoId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}