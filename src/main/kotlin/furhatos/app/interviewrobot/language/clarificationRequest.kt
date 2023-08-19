package furhatos.app.interviewrobot.language

import furhatos.flow.kotlin.*
import kotlin.random.Random

val requestClarification: State = state {
    onEntry {
        furhat.ask {
            random {
                +"Sorry?"
                +"Pardon?"
                +"Pardon me?"
                +"Excuse me?"
                +"Sorry, what was that?"
                +"Sorry, could you repeat that?"
            }
        }
    }

    onResponse {
        furhat.say {
            random {
                +"Oh, okay!"
                +"Ah, okay!"
                +"Uh huh"
                +"I see"
            }
        }

        terminate()
    }
}

fun FlowControlRunner.randomizeClarificationRequest() {
    val probability = 0.15
    val randomValue: Double = Random.nextDouble()
    if (randomValue <= probability) {
        call(requestClarification)
    }
}