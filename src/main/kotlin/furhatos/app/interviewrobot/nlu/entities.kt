package furhatos.app.interviewrobot.nlu

import furhatos.nlu.EnumEntity
import furhatos.util.Language

class Topic : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "cv: resume",
            "job interview: job interview, job interviews, interview, interviews"
        )
    }
}

class Degree : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("Associates", "Bachelors", "Masters", "Doctorate", "Ph.D.", "High School Diploma")
    }
}

class CVAdviceNeed : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "contents: contents, contents of a cv, what to put in a cv, how to write a cv",
            "cv with no experience: how to write a cv if I have no experience, how to write a cv for the first job, first cv",
            "structure: structure, how to structure a cv, what's a good structure for a cv",
            "personal interests: personal interests, what are good personal interest to mention in a cv, should I mention personal interests"
        )
    }
}