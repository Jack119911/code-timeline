import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;

import java.awt.*;

public class DefaultNodeVisualization extends NodeVisualization {

    private JBPanel panel;
    private static final int HEIGHT = 40;

    DefaultNodeVisualization(Method method) {
        super(method);
    }

    @Override
    protected void createComponent() {
        panel = new JBPanel();
        panel.withBackground(color);
        panel.withPreferredHeight(HEIGHT);

        JBLabel label = new JBLabel(method.getName() + "()");
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        panel.add(label);
    }

    @Override
    Component getComponent() {
        return panel;
    }

}
