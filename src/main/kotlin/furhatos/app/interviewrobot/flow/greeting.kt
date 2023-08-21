package furhatos.app.interviewrobot.flow

import furhatos.app.interviewrobot.*
import furhatos.app.interviewrobot.language.greet
import furhatos.app.interviewrobot.language.randomizeClarificationRequest
import furhatos.app.interviewrobot.nlu.ChooseTopicIntent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No

val Greeting: State = state(Interaction) {
    onEntry {
        dialogLogger.startSession() // logs dialog and records user speech
        furhat.ask(greet)
    }

    onResponse<ChooseTopicIntent> {
        randomizeClarificationRequest()
        furhat.say("Alright!")
        users.current.topic.adjoin(it.intent)
        goto(AnalyzeInterest)
    }

    onResponse<No> {
        randomizeClarificationRequest()
        furhat.say("Okay.")
        goto(Idle)
    }
}

