package furhatos.app.interviewrobot.nlu

import furhatos.nlu.TextGenerator
import furhatos.nlu.*
import furhatos.nlu.common.Number
import furhatos.util.Language

class ChooseTopicIntent : Intent() {
    var currentTopic: Topic? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@currentTopic",
            "about @currentTopic",
            "I would like to talk about @currentTopic",
            "I want to talk about @currentTopic",
            "I want to talk about my @currentTopic",
            "I'd like to know more about @currentTopic",
            "Can you help me with @currentTopic",
            "let's talk about @currentTopic",
            "can you advice me on @currentTopic",
            "I want advice on @currentTopic",
            "Can you help me with my @currentTopic",
            "I would like to have some advice on @currentTopic",
            "I need help with @currentTopic",
            "I need help with my @currentTopic"
        )
    }
}

class RequestTopicOptionsIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "What can we talk about?",
            "What are my options?",
            "What can you talk about?",
            "What do you know about?",
            "What topics do you have?",
            "Which topics can we talk about?",
            "Which topics can you talk about?",
            "Which topics do you know about?",
            "Which topics can you help me with?",
            "What can you give me advice on?"
        )
    }
}

open class TellCVIntent : Intent(), TextGenerator {
    var degree: Degree? = null
    var formerPositions: Number? = null
    var yrsOfExperience: Number? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@degree",
            "@formerPositions jobs",
            "@formerPositions positions",
            "@formerPositions companies",
            "@yrsOfExperience years",
            "I have a @degree",
            "I have a @degree degree",
            "I have had @formerPositions jobs",
            "I have held @formerPositions positions",
            "I have worked for @formerPositions companies",
            "I have worked in @formerPositions companies",
            "I have worked for @yrsOfExperience years",
            "I have @yrsOfExperience years of experience",
            "I have @yrsOfExperience years of work experience",
            "I have @yrsOfExperience years of experience in that field",
            "I have a @degree and have worked for @formerPositions companies",
            "I have a @degree and have worked for @yrsOfExperience years",
            "I have worked for @formerPositions companies over @yrsOfExperience years",
            "I have worked for @yrsOfExperience years in @formerPositions companies",
            "I have worked for @yrsOfExperience years in @formerPositions positions",
            "I have a @degree, have worked for @yrsOfExperience years, and have had @formerPositions jobs",
            "I have a @degree, have worked for @yrsOfExperience years, and have held @formerPositions positions",
            "I have worked for @yrsOfExperience years in @formerPositions companies and have a @degree"
        )
    }

    override fun toText(lang: Language): String {
        return generate(
            lang,
            "[a $degree degree][have worked in $formerPositions roles][and have $yrsOfExperience years of working experience]"
        )
    }

    override fun toString(): String {
        return toText()
    }
}


class TellPositionsIntent : Intent() {
    var formerPositions: Number? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@formerPositions",
            "I have had @formerPositions jobs",
            "I have held @formerPositions positions",
            "I have worked for @formerPositions companies",
            "I have worked in @formerPositions companies"
        )
    }

}

class TellExperienceIntent : Intent() {
    var yrsOfExperience: Number? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@yrsOfExperience",
            "@yrsOfExperience years",
            "I have worked for @yrsOfExperience years",
            "I have @yrsOfExperience years of experience",
            "I have @yrsOfExperience years of work experience",
            "I have @yrsOfExperience years of experience in that field")
    }
}

class InterviewIntent : Intent(), TextGenerator {
    var talkedPreparation: Boolean? = false
    var talkedContent: Boolean? = false
    var talkedTest: Boolean? = false

    override fun toText(lang: Language): String {
        val topics = mutableListOf("")
        topics.removeAt(0)
        if(talkedPreparation!!) topics.add("how to prepare")
        if(talkedContent!!) topics.add("interview questions")
        if(talkedTest!!) topics.add("technical test")
        if(topics.size==0) return generate(lang,"[Oh, I guess you didn't need advice after all]")
        return generate(lang, "[I hope I was of some help regarding ${topics.joinToString ( separator = " and "){it -> it }}.]")
    }

    override fun toString(): String {
        return toText()
    }
}

class DoneWithInterviewAdvice : Intent(){

    override fun getExamples(lang: Language): List<String> {
        return listOf("No",
            "Nope",
            "I am done",
            "I think I am done",
            "I don't want any more advice",
            "I think we are done",
            "That is all",
            "No, that is all",
            "No, I am good")
    }
}

class RequestInterviewPreparationAdvice : Intent(), TextGenerator {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Preparation",
            "How do I prepare for an interview?",
            "What do you mean by preparation?",
            "Let's talk about preparation.",
            "How do I prepare?",
            "What do I do before?",
            "What do I do before an interview?"
        )
    }

    override fun toText(lang: Language): String {
        return generate(
            lang,
            "[Before the interview, you should take some time to rehearse and think about what is necessary " +
                    "for the position you are interviewing for. Be able to relate relevant information on your CV, " +
                    "as you will likely be discussing it with the interviewer.]"
        )
    }

    override fun toString(): String {
        return toText()
    }
}

class TellDegreeIntent : Intent() {
    var degree: Degree? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@degree",
            "I have a @degree",
            "I have a @degree degree"
        )
    }

}

class RequestInterviewContentAdvice : Intent(), TextGenerator {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Interview questions",
            "tell me about the questions",
            "What questions should I ask?",
            "What should I talk about?",
            "What is your advice on what to talk about?",
            "Explain more about interview questions.",
            "What do you mean interview questions?",
            "Let's talk about the questions.",
            "Let's talk about the interview questions."
        )
    }

    override fun toText(lang: Language): String {
        return generate(
            lang,
            "[You can relax a bit there, since the interviewer will probably lead the conversation. " +
                    "Still, you should keep in mind that it is a good idea to show interest by asking questions " +
                    "more related to the position or company.]"
        )
    }

    override fun toString(): String {
        return toText()
    }
}


class RequestInterviewTestAdvice : Intent(), TextGenerator {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Test",
            "Technical test",
            "Technical skills test",
            "How do I prepare for a technical test?",
            "How do I prepare for a technical skills test?",
            "How should I prepare for the technical test?",
            "How should I prepare for the technical skills test?",
            "Can you advise me on the technical test?",
            "Can you advise me on the technical skills test?",
            "Could you advise me on the technical test?",
            "Could you advise me on the technical skills test?",
            "What should I do about a technical test?",
            "What should I do during a technical test?",
            "What should I do regarding a technical test?",
            "Let's talk about the technical test.",
            "I'd like to talk about the technical skills test."
        )
    }

    override fun toText(lang: Language): String {
        return generate(
            lang,
            "[Regarding technical test, the point is more to show how you approach a problem, rather than " +
                    "solving it completely. With that in mind, you should explain your reasoning while you tackle " +
                    "the test. It is also okay to pay less attention to the interviewer, since your main focus " +
                    "should be on the task at hand.]"
        )
    }

    override fun toString(): String {
        return toText()
    }
}

class RequestInterviewAdviceOptions : Intent(), TextGenerator {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Options",
            "What were my options?",
            "What are my options?",
            "What can you give me advice me on?",
            "Could you repeat the options?",
            "Can you repeat the options?",
            "Please repeat the options"
        )
    }

    override fun toText(lang: Language): String {
        return generate(lang, "[We can go over interview preparation, the interview questions and the technical test.]")
    }

    override fun toString(): String {
        return toText()
    }
}

class RequestCVAdvice : Intent() {
    var cvAdviceNeed: CVAdviceNeed? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@cvAdviceNeed",
            "I need advice on @cvAdviceNeed",
            "I want to know about @cvAdviceNeed",
            "I need help with @cvAdviceNeed"
        )
    }
}