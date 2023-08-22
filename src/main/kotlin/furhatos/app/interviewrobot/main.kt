package furhatos.app.interviewrobot

import furhatos.app.interviewrobot.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill
import java.io.File

class InterviewrobotSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}