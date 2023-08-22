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
            "interviews" -> goto(AskAboutInterview)
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
}

// TOPIC 2
val AskAboutInterview: State = state(Interaction) {
    onEntry {
        furhat.ask(interviewAdviceIntro)
    }

    onReentry {
        furhat.say("${users.current.interview}")
        furhat.ask("Anything else you want to ask about interviews?")
    }

    onResponse<RequestInterviewPreparationAdvice> {
        randomizeClarificationRequest()
        if (users.current.interview.talkedPreparation!!) furhat.say(repeat)
        furhat.say("${it.intent}")
        users.current.interview.talkedPreparation = true
        reentry()
    }

    onResponse<RequestInterviewContentAdvice> {
        randomizeClarificationRequest()
        if (users.current.interview.talkedContent!!) furhat.say(repeat)
        furhat.say("${it.intent}")
        users.current.interview.talkedContent = true
        reentry()
    }

    onResponse<RequestInterviewTestAdvice> {
        randomizeClarificationRequest()
        if (users.current.interview.talkedPreparation!!) furhat.say(repeat)
        furhat.say("${it.intent}")
        users.current.interview.talkedTest = true
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
