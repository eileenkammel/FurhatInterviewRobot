package furhatos.app.interviewrobot.flow

import furhatos.app.interviewrobot.*
import furhatos.app.interviewrobot.language.giveTopicOptions
import furhatos.app.interviewrobot.language.greet
import furhatos.app.interviewrobot.language.randomizeClarificationRequest
import furhatos.app.interviewrobot.nlu.ChooseTopicIntent
import furhatos.app.interviewrobot.nlu.RequestTopicOptionsIntent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No

val Greeting: State = state(Interaction) {
    onEntry {
        dialogLogger.startSession(cloudToken = "f8b663ad-e9bb-44f8-8e57-1a146ad336a2") // logs dialog and records user speech
        furhat.ask(greet)
    }

    onResponse<ChooseTopicIntent> {
        randomizeClarificationRequest()
        furhat.say("Alright!")
        users.current.topic.adjoin(it.intent)
        goto(AnalyzeInterest)
    }

    onResponse<RequestTopicOptionsIntent> {
        randomizeClarificationRequest()
        furhat.ask(giveTopicOptions)
    }

    onResponse<No> {
        randomizeClarificationRequest()
        furhat.say("Okay.")
        goto(Idle)
    }
}

