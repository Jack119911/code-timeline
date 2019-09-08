package tutorial;

import resources.Images;
import resources.TutorialText;

class TutorialPageContent {

    private TutorialPageContent() {}

    static final TutorialPageContentEntry[] content = {
            new TutorialPageContentEntry(TutorialText.INTRO, Images.TUTORIAL_INTRODUCTION),
            new TutorialPageContentEntry(TutorialText.BLOCKS, Images.TUTORIAL_BLOCKS),
            new TutorialPageContentEntry(TutorialText.CONTROL_FLOW, Images.TUTORIAL_CONTROL_FLOW),
            new TutorialPageContentEntry(TutorialText.DEPENDENCY, Images.TUTORIAL_DEPENDENCY),
            new TutorialPageContentEntry(TutorialText.CONTROL_STRUCTURES, Images.TUTORIAL_CONTROL_STRUCTURES),
            new TutorialPageContentEntry(TutorialText.NAVIGATION, Images.TUTORIAL_HOVER),
            new TutorialPageContentEntry(TutorialText.ROOT_METHOD, Images.TUTORIAL_CONTEXT_MENU),
    };

}
