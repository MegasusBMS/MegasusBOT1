package Music;

import java.nio.ByteBuffer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

import MegasusBOT.MegasusBOT;
import MegasusBOT.Play;
import MegasusBOT.commands;
import MegasusBOT.repeat;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;

/**
 * This is a wrapper around AudioPlayer which makes it behave as an
 * AudioSendHandler for JDA. As JDA calls canProvide before every call to
 * provide20MsAudio(), we pull the frame in canProvide() and use the frame we
 * already pulled in provide20MsAudio().
 */
public class AudioPlayerSendHandler implements AudioSendHandler {
	private final AudioPlayer audioPlayer;
	private AudioFrame lastFrame;

	/**
	 * @param audioPlayer
	 *            Audio player to wrap.
	 */
	public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	public boolean canProvide() {
		if (lastFrame == null) {
			lastFrame = audioPlayer.provide();
		}

		return lastFrame != null;
	}

	public ByteBuffer provide20MsAudio() {
		if (lastFrame == null) {
			lastFrame = audioPlayer.provide();
		}
		PlayerManager playerManager = PlayerManager.getInstance();
		for (int i = 0; i < repeat.guild.length; i++) {
			Guild g = repeat.guild[i];
			if (g != null) {
				GuildMusicManager musicManager = playerManager.getGuildMusicManager(g);
				AudioPlayer player = musicManager.player;
				if ((player.getPlayingTrack().getDuration() - 3) < player.getPlayingTrack().getPosition()) {
					String[] song = (MegasusBOT.prefix + "play " + player.getPlayingTrack().getInfo().title).split(" ");
					new Play(song, commands.e, false);
					break;
				}
			}
		}
		byte[] data = lastFrame != null ? lastFrame.getData() : null;
		lastFrame = null;
		ByteBuffer d = ByteBuffer.wrap(data);
		return d;
	}

	public boolean isOpus() {
		return true;
	}
}
