package Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class scheduler extends AudioEventAdapter {

    public final AudioPlayer audioPlayer;
    public final BlockingQueue<AudioTrack> queue;

    public scheduler(AudioPlayer audioPlayer) {

        this.audioPlayer = audioPlayer;
        this.queue = new LinkedBlockingQueue<>();

    }

    public void queue(AudioTrack track) {

        if (!this.audioPlayer.startTrack(track, true)) {

            this.queue.offer(track);

        }

    }

    public void nextTrack() {

        this.audioPlayer.startTrack(this.queue.poll(), false);

    }

    public void stopPlayback() {

        this.audioPlayer.stopTrack();

    }

    @Override
    public void onTrackEnd(AudioPlayer audioPlayer, AudioTrack track, AudioTrackEndReason endReason) {

        if (endReason.mayStartNext) {

            nextTrack();

        }

    }

}