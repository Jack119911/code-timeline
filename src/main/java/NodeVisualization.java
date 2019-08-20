import com.intellij.ui.JBColor;

import java.awt.*;

abstract class NodeVisualization {

    protected String methodName;
    protected JBColor color;

    NodeVisualization(String methodName) {
        this.methodName = methodName;
        this.color = determineColor();
        createComponent();
    }

    protected JBColor determineColor() {
        try {
            return ColorService.getDistinctColorForMethodName(methodName);
        } catch (Exception e) {
            e.printStackTrace();
            return ColorService.getRandomColorForMethodName(methodName);
        }
    }

    protected abstract void createComponent();

    abstract Component getComponent();
}
