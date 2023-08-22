package furhatos.app.interviewrobot

import furhatos.app.interviewrobot.nlu.*
import furhatos.flow.kotlin.NullSafeUserDataDelegate
import furhatos.records.User

//Current topic user wants to talk about
val User.topic by NullSafeUserDataDelegate {
    ChooseTopicIntent()
}

//User profile

val User.cv by NullSafeUserDataDelegate {
    TellCVIntent()
}

val User.cvAdviceNeed by NullSafeUserDataDelegate{
    RequestCVAdvice()
}

val User.interview by NullSafeUserDataDelegate {
    InterviewIntent()
}