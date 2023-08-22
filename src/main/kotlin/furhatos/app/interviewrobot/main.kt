package furhatos.app.interviewrobot

import furhatos.app.interviewrobot.audiofeed.FurhatAudioFeedPlayback
import furhatos.app.interviewrobot.flow.Init
import furhatos.app.interviewrobot.audiofeed.FurhatAudioFeedRecorder
import furhatos.demo.audiofeed.FurhatAudioFeedStreamer
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill
import java.io.File

class InterviewrobotSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    val streamer = FurhatAudioFeedStreamer()
    streamer.start("192.168.0.9")
    recordAudio(streamer)
    /** Choose one of them to record audio in, out or both */
    //recorder.startRecordAll(File("recording.wav"))
    println("Starting streaming, press return to stop.")
    //readlnOrNull()
    Skill.main(args)
    streamer.stop()
}

fun recordAudio(streamer: FurhatAudioFeedStreamer) {
    val recorder = FurhatAudioFeedRecorder(streamer)

    /** Choose one of them to record audio in, out or both */
//    recorder.startRecordAll(File("recording.wav"))
    recorder.startRecordSeparate(audioInFile = File("audioIn.wav"), audioOutFile = File("audioOut.wav"))
}

fun playbackAudio(streamer: FurhatAudioFeedStreamer) {
    val playback = FurhatAudioFeedPlayback(streamer)
    playback.start(playSystem = true, playUser = true)
}
