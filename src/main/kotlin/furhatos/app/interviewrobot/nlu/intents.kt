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
            "I would like to talk about @currentTopic",
            "I want to talk about @currentTopic",
            "I want to talk about my @currentTopic",
            "I'd like to know more about @currentTopic",
            "Can you help me with my @currentTopic",
            "I would like to have some advice on @currentTopic"
        )
    }
}

class RequestTopicOptionsIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "What can we talk about?",
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

class TellInterviewIntent : Intent(), TextGenerator {
    var confidence: InterviewConfidence? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@confidence",
            "I am @confidence",
            "I am @confidence during job interviews",
            "I feel @confidence",
            "I feel @confidence during job interviews"
        )
    }

    override fun toText(lang: Language): String {
        return generate(lang, "[I see, you are $confidence during job interviews.]")
    }

    override fun toString(): String {
        return toText()
    }
}

class TellInterviewConfidenceIntent : Intent() {
    var confidence: InterviewConfidence? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf("@confidence",
            "I am @confidence",
            "I am @confidence during job interviews",
            "I feel @confidence",
            "I feel @confidence during job interviews")
    }
}
class TellSkillIntent : Intent(), TextGenerator {
    var skill: Skill? = null
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I know @skill",
            "I am proficient in @skill",
            "I have experience with @skill"
        )
    }

    override fun toText(lang: Language): String {
        return generate(lang, "[So you are proficient in $skill]")
    }

    override fun toString(): String {
        return toText()
    }
}

class RequestCVAdvice : Intent() {
    var cvAdviceNeed: CVAdviceNeed? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf("I need advice on @cvAdviceNeed", "@cvAdviceNeed",
            "I want to know @CvAdviceNeed")
    }
}

class RequestInterviewAdvice : Intent() {
    var interviewAdviceNeed: InterviewAdviceNeed? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf("I need advice on @interviewAdviceNeed", "interviewAdviceNeed")
    }
}

class RequestSkillsAdvice : Intent() {
    var skillsAdviceNeed: SkillsAdviceNeed? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf("I need advice on @skillsAdviceNeed", "skillsAdviceNeed")
    }
}