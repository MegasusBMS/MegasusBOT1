package MegasusBOT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import Music.GuildMusicManager;
import Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class queue {
	public queue(String[] args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();
        
        if(args.length==2&&args[1].equalsIgnoreCase("clear")){
        	musicManager.scheduler.queueclear();
        	channel.sendMessage("Clear queue ...").queue();
        }
        
        if (queue.isEmpty()) {
            channel.sendMessage("The queue is empty").queue();

            return;
        }
        int trackCount;
        List<AudioTrack> list;
        	list = new ArrayList<AudioTrack>(queue);
        	trackCount = Math.min(queue.size(), 20);
        EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Current Queue (Total: " + list.size()+ ")");

        for (int i = 0; i < trackCount; i++) {
            AudioTrack track = list.get(i);
            AudioTrackInfo info = track.getInfo();

            builder.appendDescription(String.format("**"+(i+1)+")** [%s](%s)\n%s %s\n", info.title, info.uri,"Duration: ",
    				formatTime(track.getDuration())));   
        }
        long duration=0;
        for(int i = 0;i<list.size();i++){
        	AudioTrack track = list.get(i);
            duration+=track.getDuration();
        }
        String a ="";
        for (int i = 0; i < repeat.guild.length; i++) {
        	if(event.getGuild()==repeat.guild[i]){
        		a =":repeat: ";
        	}
        }
        builder.appendDescription(String.format("\n --------------------------------\n**Duration of the queue:** [%s] "+a,formatTime(duration)));
        
        channel.sendMessage(builder.build()).queue();
    }
	private String formatTime(long timeInMillis) {
		final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
		final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
		final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

		return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
	}
}