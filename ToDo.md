- ~~Let furhat answer anything when slot is filled(DONE), more slots per topic, loop conversation~~ DONE


- Define **more** slots/content for the user profile, especially for the skills and job interview topics
  - for every slot there must be a Request state, where furhat requests info for that slot and a Tell intent where the user tells that slot info
  - Request state only entered when user does not give info unprompted
  - For everything that can be used to fill a slot an entity must be created ore build-in entity used
  - Slot filling should feel as natural as possible, user should give information any way they want, furhat should not ask a lot of specific questions regarding any slots
    - Maybe can prompt the user to say a little more when too few slot have been filled without being too specific. Ideally the user will not notice there are slots to fill


- Add a counter and let furhat ask for two open elaborations if slots are unfilled. After that, request specific slot info
  - possible problem: what to do if human answer when elaborating contains no slot information?


- Implement "Talk" States, where furhat asks random questions about the current topic just to get the user to talk, e.g. "What is your worst job interview experience?"
  - During this state, user utterances are not saved in a meaningful way, so nothing can be done with them


- Clarification requests


- ~~Make change of topic possible at every state~~ DONE


- Make things like Skill in the user profile a list of things so that furhat can advise on learning new things if list is too short
  - Maybe here use a llm? when user chooses a new skill they are interested in, some advice on how to best acquire it can be given?



- ~~Implement logging~~ DONE



- Implement a give advice state, only entered if a certain amount of profile slots for a given topic have been filled. As long as not enough slots have been filled, reentry AskAboutX state
  - How to write a function that checks how many slots in the profile have been filled? Function must access users.kt file as var get reset to null on reentry of state. ~~Maybe in users.kt add different vars for each topic and not one for the whole profile? Maybe would make access easier...~~ DONE THAT PART 



- Other:
  - ~~Consent form~~ DONE
  - ~~Dialogue Evaluation Form~~ DONE


- For every states that can handle only certain answers as input, make sure the user can always ask for the options they have


- Add customized furhat utterances for no match and no answer for every state. It's a huge piece of work, but will enhance the natural feeling of the dialogue a lot and will leave the user less confused.


- Some "safety net" for when human is entirely confused and knows nothing to say. Furhat should take the initiative then to keep the conversation going.
