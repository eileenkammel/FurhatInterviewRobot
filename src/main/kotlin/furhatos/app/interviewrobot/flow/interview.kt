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
            "cv" -> {
                goto(AskAboutCV)
            }

            "job interview" -> {
                goto(AskAboutInterview)
            }
            "interviews" -> {
                goto(AskAboutInterview)
            }

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
        goto(CheckCvProfile)
    }
}


val CheckCvProfile : State = state(Interaction) {
    onEntry {
        when { // if slot is empty, specifically targets slot
            users.current.cv.degree == null -> goto(RequestDegree)
            users.current.cv.formerPositions == null -> goto(RequestPositions)
            users.current.cv.yrsOfExperience == null -> goto(RequestExperience)
            else -> {
                furhat.say("So you have ${users.current.cv}")
                goto(RandomCvTalk)
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
        goto(CheckCvProfile)
    }
}

val RequestExperience: State = state(Interaction){
    onEntry {
        furhat.ask(requestYrsOfExperience)
    }
    onResponse<TellExperienceIntent> {
        users.current.cv.yrsOfExperience = it.intent.yrsOfExperience
        randomizeClarificationRequest()
        goto(CheckCvProfile)
    }
}

val RequestPositions: State = state(Interaction){
    onEntry {
        furhat.ask(requestFormerPositions)
    }
    onResponse<TellPositionsIntent> {
        users.current.cv.formerPositions = it.intent.formerPositions
        randomizeClarificationRequest()
        goto(CheckCvProfile)
    }
}

val RandomCvTalk : State = state(Interaction) {
    onEntry {
        furhat.ask {random {+"What are your concerns when it comes to writing a CV?"
            +"How many CVs have you written so far?"}}
    }
    onResponse {
        furhat.say("Interesting!")
        goto(GiveCVAdvice)
    }
}

val GiveCVAdvice: State = state(Interaction) {
    onEntry {
        furhat.ask("What kind of cv advice do you need?")
    }

    onResponse<RequestCVAdvice> {
        users.current.cvAdviceNeed.adjoin(it.intent)
        when (users.current.cvAdviceNeed.cvAdviceNeed!!.value) {
            "contents" -> furhat.say(giveCvContentAdvice)
            "cv with no experience" -> furhat.say(giveFirstCvAdvice)
            "structure" -> furhat.say(giveCvStructureAdvice)
            "personal interests" -> furhat.say(givePersonalInterestAdvice)
        }

        goto(AskIfMoreAdvice)
    }
}

// TOPIC 2
val AskAboutInterview: State = state(Interaction) {
    onEntry {
        furhat.say("I can give you advice on some topics regarding interviews in general")
        furhat.say("I had in mind a couple topics")

        furhat.ask("preparation, interview questions and technical tests.")
        //interview: preparation, interview questions and technical test
    }


    onReentry {
        furhat.say("${users.current.interview}")
        furhat.ask("Anything else you want to ask about interviews?")
    }

    onResponse<requestInterviewPreparationAdvice> {
        randomizeClarificationRequest()
        if (users.current.interview.talked_preparation!!) furhat.say("Ah,, we were already over this")
        furhat.say("${it.intent}")
        users.current.interview.talked_preparation= true
        reentry()
    }

    onResponse<requestInterviewContentAdvice> {
        if (users.current.interview.talked_content!!) furhat.say("Ah we were already over this")
        randomizeClarificationRequest()
        furhat.say("${it.intent}")
        users.current.interview.talked_content= true
        reentry()
    }

    onResponse<requestInterviewTestAdvice> {
        if (users.current.interview.talked_preparation!!) furhat.say("Ah,, we were already over this")
        randomizeClarificationRequest()
        furhat.say("${it.intent}")
        users.current.interview.talked_test= true
        reentry()
    }

    onResponse<requestInterviewOptionsAdvice> {
        randomizeClarificationRequest()
        furhat.say("${it.intent}")
        reentry()
    }

    onResponse<doneWithInterviewAdvice> {
        furhat.say("Ah, I hope I was of some use.")
        goto(AnalyzeInterest)
    }


}



// TOPIC 3


// ADVICE HANDLER
val AskIfMoreAdvice: State = state(Interaction) {
    onEntry {
        furhat.ask("Do you want more advice on this topic?")
    }

    onResponse<Yes> {
        when (users.current.topic.currentTopic!!.value) {
            "cv" -> {
                goto(GiveCVAdvice)
            }
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
