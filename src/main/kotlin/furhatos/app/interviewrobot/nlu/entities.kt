package furhatos.app.interviewrobot.nlu

import furhatos.nlu.EnumEntity
import furhatos.util.Language

class Topic : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("cv", "job interview", "job interviews", "interviews")
    }
}

class Degree : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("Associates", "Bachelors", "Masters", "Doctorate", "Ph.D.")
    }
}

class Skill : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("Python", "Java", "HTML")
    }
}

class InterviewConfidence : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("confident:confident", "not confident:not confident,insecure")
    }
}

class CVAdviceNeed : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "contents:cv contents,contents of a cv,what to put in a cv,how to write a cv",
            "cv with no experience:how to write a cv if I have no experience,how to write a cv for the first job",
<<<<<<< HEAD
            "structure:how to structure a cv,what's a good structure for a cv, structure",
            "personal interests:what are good personal interest to mention in a cv, should I mention personal interests")
=======
            "structure:how to structure a cv,what's a good structure for a cv",
            "personal interests:what are good personal interest to mention in a cv, should I mention personal interests"
        )
    }
}

class InterviewAdviceNeed : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "nervous:nervous in interview, how to stay calm, how to come off as confident",
            "preparation:how to prepare, how do I prepare, what should I prepare",
            "clothes:how should I dress, what's a good way to dress, what is an appropriate outfit",
            "questions:what kind of questions should I ask, what should I ask"
        )
    }
}

class SkillsAdviceNeed : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "format:how should I format my skills, how should I talk about my skills",
            "language:should I list this language, should I put this language"
        )
>>>>>>> origin/main
    }
}