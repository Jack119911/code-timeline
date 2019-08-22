import com.intellij.ui.JBColor;

import java.awt.*;
import java.util.NoSuchElementException;

abstract class NodeVisualization {

    final String methodName;
    final JBColor color;

    NodeVisualization(String methodName) {
        this.methodName = methodName;
        this.color = determineColor();
        createComponent();
    }

    private JBColor determineColor() {
        try {
            return ColorService.getDistinctColorForMethodName(methodName);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ColorService.getRandomColorForMethodName(methodName);
        }
    }

    protected abstract void createComponent();

    abstract Component getComponent();
}
