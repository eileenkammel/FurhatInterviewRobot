package furhatos.app.interviewrobot.flow

import furhatos.app.interviewrobot.setting.DISTANCE_TO_ENGAGE
import furhatos.app.interviewrobot.setting.MAX_NUMBER_OF_USERS
import furhatos.flow.kotlin.*

val Init: State = state {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(DISTANCE_TO_ENGAGE, MAX_NUMBER_OF_USERS)
    }

    onEntry {
        /** start interaction */
        when {
            users.hasAny() -> {
                furhat.attend(users.random)
                goto(Greeting)
            }

            else -> goto(Idle)
        }
    }
}
