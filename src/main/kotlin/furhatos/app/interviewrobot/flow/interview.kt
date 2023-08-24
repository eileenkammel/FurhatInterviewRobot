package furhatos.app.interviewrobot.flow

import furhatos.app.interviewrobot.*
import furhatos.app.interviewrobot.language.*
import furhatos.app.interviewrobot.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

// TOPIC HANDLER
val AnalyzeInterest: State = state(Interaction) {
    onEntry {
        when (users.current.topic.currentTopic!!.value) {
            "cv" -> goto(AskAboutCV)
            "job interview" -> goto(AskAboutInterview)
            else -> {
                furhat.say(topicNotFound)
                goto(RequestTopic)
            }
        }
    }
}

val RequestTopic: State = state(Interaction) {
    onEntry {
        furhat.ask(requestTopic)
    }

    onResponse<ChooseTopicIntent> {
        users.current.topic.currentTopic = it.intent.currentTopic //overwrite current topic
        randomizeClarificationRequest()
        goto(AnalyzeInterest)
    }

    onResponse<RequestTopicOptionsIntent> {
        randomizeClarificationRequest()
        furhat.ask(giveTopicOptions)
    }

    onResponse<Yes> {
        randomizeClarificationRequest()
        furhat.ask(requestTopic)
    }

    onResponse<No> {
        randomizeClarificationRequest()
        goto(End)
    }
}

// TOPIC 1
val AskAboutCV: State = state(Interaction) {
    onEntry {
        furhat.ask(requestCV)
    }

    onResponse<TellCVIntent> {
        users.current.cv.adjoin(it.intent)
        randomizeClarificationRequest()
        goto(CheckCVProfile)
    }
}


val CheckCVProfile : State = state(Interaction) {
    onEntry {
        when { // if slot is empty, specifically targets slot
            users.current.cv.degree == null -> goto(RequestDegree)
            users.current.cv.formerPositions == null -> goto(RequestPositions)
            users.current.cv.yrsOfExperience == null -> goto(RequestExperience)
            else -> {
                furhat.say("So you have ${users.current.cv}")
                goto(RandomCVTalk)
            }
        }
    }
}

val RequestDegree: State = state(Interaction){
    onEntry {
        furhat.ask(requestDegree)
    }
    onResponse<TellDegreeIntent> {
        users.current.cv.degree = it.intent.degree
        randomizeClarificationRequest()
        goto(CheckCVProfile)
    }
}

val RequestExperience: State = state(Interaction){
    onEntry {
        furhat.ask(requestYrsOfExperience)
    }
    onResponse<TellExperienceIntent> {
        users.current.cv.yrsOfExperience = it.intent.yrsOfExperience
        randomizeClarificationRequest()
        goto(CheckCVProfile)
    }
}

val RequestPositions: State = state(Interaction){
    onEntry {
        furhat.ask(requestFormerPositions)
    }
    onResponse<TellPositionsIntent> {
        users.current.cv.formerPositions = it.intent.formerPositions
        randomizeClarificationRequest()
        goto(CheckCVProfile)
    }
}

val RandomCVTalk : State = state(Interaction) {
    onEntry {
        furhat.ask(askCVQuestion)
    }
    onResponse {
        furhat.say("Interesting!")
        goto(GiveCVAdvice)
    }
}

val GiveCVAdvice: State = state(Interaction) {
    onEntry {
        furhat.ask(cvAdviceIntro)
    }

    onResponse<RequestCVAdvice> {
        users.current.cvAdviceNeed.adjoin(it.intent)
        when (users.current.cvAdviceNeed.cvAdviceNeed!!.value) {
            "contents" -> furhat.say(giveCVContentAdvice)
            "cv with no experience" -> furhat.say(giveFirstCVAdvice)
            "structure" -> furhat.say(giveCVStructureAdvice)
            "personal interests" -> furhat.say(givePersonalInterestAdvice)
        }

        goto(AskIfMoreAdvice)
    }
    onResponse<RequestInterviewAdviceOptions> {
        furhat.say(cvAdviceOptions)
        reentry()
    }
}

// TOPIC 2
val AskAboutInterview: State = state(Interaction) {
    onEntry {
        if( users.current.interview.talkedTest!!) furhat.ask("Anything else you want to ask about interviews?")
        else if( users.current.interview.talkedContent!!) furhat.ask("Anything else you want to ask about interviews?")
        else if( users.current.interview.talkedPreparation!!) furhat.ask("Anything else you want to ask about interviews?")
        else furhat.ask(interviewAdviceIntro)
    }

    onReentry {
        furhat.ask("Anything else you want to ask about interviews?")
    }

    onResponse<RequestInterviewPreparationAdvice> {
        randomizeClarificationRequest()
        goto(askInterviewPreparation)
    }

    onResponse<RequestInterviewContentAdvice> {
        randomizeClarificationRequest()
        goto(askInterviewContent)
    }

    onResponse<RequestInterviewTestAdvice> {
        randomizeClarificationRequest()
        goto(askInterviewTest)
    }

    onResponse<Yes> {
        furhat.say("[What next? We can go over interview preparation, the interview questions and technical tests.]")
        reentry()
    }

    onResponse<RequestInterviewAdviceOptions> {
        randomizeClarificationRequest()
        furhat.say("${it.intent}")
        reentry()
    }

    onResponse<DoneWithInterviewAdvice> {
        furhat.say("Okay, I hope you found that useful.")
        goto(ChooseMoreOrEnd)
    }

    onResponse<No> {
        furhat.say("Okay, I hope you found that useful.")
        goto(ChooseMoreOrEnd)
    }

}

val askInterviewPreparation: State = state(Interaction) {
    onEntry {
        if (users.current.interview.talkedPreparation!!) furhat.say(repeat)
        furhat.ask(askInterviewPreparationQuestion)
    }
    onResponse {
        furhat.say("Oh I see.")
        furhat.say("[Before the interview, you should take some time to rehearse and think about what is necessary " +
                "for the position you are interviewing for. Be able to relate relevant information on your CV, " +
                "as you will likely be discussing it with the interviewer.]")
        users.current.interview.talkedPreparation = true
        furhat.say("${users.current.interview}")
        goto(AskAboutInterview)
    }
}

val askInterviewContent: State = state(Interaction) {
    onEntry {
        if (users.current.interview.talkedContent!!) furhat.say(repeat)
        furhat.ask(askInterviewContentQuestion)
    }
    onResponse {
        furhat.say("Oh I see.")
        furhat.say("[You can relax a bit there, since the interviewer will probably lead the conversation. " +
                "Still, you should keep in mind that it is a good idea to show interest by asking questions " +
                "more related to the position or company.]")
        users.current.interview.talkedContent = true
        furhat.say("${users.current.interview}")
        goto(AskAboutInterview)
    }
}

val askInterviewTest: State = state(Interaction) {
    onEntry {
        if (users.current.interview.talkedTest!!) furhat.say(repeat)
        furhat.ask(askInterviewTestQuestion)
    }
    onResponse {
        furhat.say("Oh I see.")
        furhat.say("[Regarding technical test, the point is more to show how you approach a problem, rather than " +
                "solving it completely. With that in mind, you should explain your reasoning while you tackle " +
                "the test. It is also okay to pay less attention to the interviewer, since your main focus " +
                "should be on the task at hand.]")
        users.current.interview.talkedTest = true
        furhat.say("${users.current.interview}")
        goto(AskAboutInterview)
    }
}

// ADVICE HANDLER
val AskIfMoreAdvice: State = state(Interaction) {
    onEntry {
        furhat.ask("Do you want more advice on this topic?")
    }

    onResponse<Yes> {
        when (users.current.topic.currentTopic!!.value) {
            "cv" -> goto(GiveCVAdvice)
            "job interview" -> goto(AskAboutInterview)
            "interviews" -> goto(AskAboutInterview)
            else -> goto(RequestTopic)
        }
    }

    onResponse<No> {
        goto(ChooseMoreOrEnd)
    }
}

// NEW TOPIC HANDLER
val ChooseMoreOrEnd: State = state(Interaction) {
    onEntry {
        furhat.ask(additionalTopic)
    }

    onResponse<Yes> {
        goto(RequestTopic)
    }

    onResponse<No> {
        goto(End)
    }
}

// USER SESSION END STATE
val End: State = state(Interaction) {
    onEntry {
        furhat.say(farewell)
        dialogLogger.endSession() // stops logging for user's session
        goto(Idle)
    }
}
