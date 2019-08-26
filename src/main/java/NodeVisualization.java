import com.intellij.ui.JBColor;

import java.awt.*;
import java.util.NoSuchElementException;

abstract class NodeVisualization {

    final Method method;
    final JBColor color;

    NodeVisualization(Method method) {
        this.method = method;
        this.color = determineColor();
        createComponent();
    }

    private JBColor determineColor() {
        try {
            return ColorService.getDistinctColorForMethodName(method.getName());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ColorService.getRandomColorForMethodName(method.getName());
        }
    }

    protected abstract void createComponent();

    abstract Component getComponent();
}
