package tutorial;

import java.awt.*;

class TutorialPageContentEntry {
    private String text;
    private Image image;

    TutorialPageContentEntry(String text, Image image) {
        this.text = text;
        this.image = image;
    }

    String getText() {
        return text;
    }

    Image getImage() {
        return image;
    }
}