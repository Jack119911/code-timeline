package tutorial;

import resources.Images;
import resources.TutorialText;

class TutorialPageContent {

    private TutorialPageContent() {}

    static final TutorialPageContentEntry[] content = {
            new TutorialPageContentEntry(TutorialText.INTRO, Images.TUTORIAL_INTRODUCTION),
            new TutorialPageContentEntry("2", Images.TUTORIAL_INTRODUCTION),
            new TutorialPageContentEntry("3", Images.TUTORIAL_INTRODUCTION),
            new TutorialPageContentEntry("4", Images.TUTORIAL_INTRODUCTION),
    };

}
