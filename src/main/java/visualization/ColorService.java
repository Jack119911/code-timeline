package visualization;

import com.intellij.ui.JBColor;
import kotlin.random.Random;

import java.awt.*;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ColorService {

    private static final float SATURATION = (float) 0.5;
    private static final float BRIGHTNESS = (float) 0.8;

    private static final HashMap<String, JBColor> distinctColorMapping = new HashMap<>();
    private static final HashMap<String, JBColor> randomColorMapping = new HashMap<>();

    private ColorService(){}

    static JBColor getDistinctColorForMethodName(String methodName) throws NoSuchElementException {
        if (distinctColorMapping.containsKey(methodName)) {
            return distinctColorMapping.get(methodName);
        } else {
            throw new NoSuchElementException("No Color for this method. Use iniColors() first");
        }
    }

    private static void addDistinctColor(int numOfColors, String methodName) {
        float step = (float) 1 / numOfColors;
        float hue = (step * distinctColorMapping.size());
        int distinctColorRgb = Color.HSBtoRGB(hue, SATURATION, BRIGHTNESS);
        JBColor distinctColor = new JBColor(distinctColorRgb, distinctColorRgb);
        distinctColorMapping.put(methodName, distinctColor);
    }

    public static void initColors(String[] methodNames) {
        distinctColorMapping.clear();
        randomColorMapping.clear();
        for (String methodName : methodNames) {
            addDistinctColor(methodNames.length, methodName);
        }
    }

    static JBColor getRandomColorForMethodName(String methodName) {
        if (randomColorMapping.containsKey(methodName)) {
            return randomColorMapping.get(methodName);
        } else {
            JBColor randomColor = getRandomColor();
            randomColorMapping.put(methodName, randomColor);
            return randomColor;
        }
    }

    private static JBColor getRandomColor() {
        Random rand = kotlin.random.Random.Default;
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new JBColor(new Color(r, g, b), new Color(r, g, b));
    }

}
