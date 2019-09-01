package visualization;

import java.awt.*;

public class StyleSettings {

    private static StyleSettings instance;

    private String monoSpaceFontName = Font.SERIF;

    private StyleSettings() {}

    public static StyleSettings getInstance() {
        if (instance == null) {
            instance = new StyleSettings();
        }
        return instance;
    }

    public String getMonoSpaceFontName() {
        return monoSpaceFontName;
    }

    public void setMonoSpaceFontName(String monoSpaceFontName) {
        this.monoSpaceFontName = monoSpaceFontName;
    }
}
