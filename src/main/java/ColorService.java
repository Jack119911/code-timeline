import com.intellij.ui.JBColor;
import kotlin.random.Random;

import java.awt.*;
import java.util.HashMap;

class ColorService {

    private static HashMap<String, JBColor> colorMapping = new HashMap<>();

    private ColorService(){}

    static JBColor getColorForMethodName(String methodName) {
        if (colorMapping.containsKey(methodName)) {
            return colorMapping.get(methodName);
        } else {
            JBColor randomColor = getRandomColor();
            colorMapping.put(methodName, randomColor);
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
