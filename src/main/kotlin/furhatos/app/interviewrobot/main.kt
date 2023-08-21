package furhatos.app.interviewrobot

import furhatos.app.interviewrobot.flow.Init
import furhatos.demo.audiofeed.FurhatAudioFeedRecorder
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
    val recorder = FurhatAudioFeedRecorder(streamer)

    /** Choose one of them to record audio in, out or both */
    //recorder.startRecordAll(File("recording.wav"))
    recorder.startRecordSeparate(audioInFile = File("audioIn.wav"), audioOutFile = File("audioOut.wav"))
    Skill.main(args)


    //streamer.stop()
}
