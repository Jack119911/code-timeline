import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;

import java.awt.*;

public class DefaultNodeVisualization extends NodeVisualization {

    private JBPanel panel;
    private static final int HEIGHT = 40;

    DefaultNodeVisualization(String methodName) {
        super(methodName);
    }

    @Override
    protected void createComponent() {
        panel = new JBPanel();
        panel.withBackground(color);
        panel.withPreferredHeight(HEIGHT);

        JBLabel label = new JBLabel(methodName);
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        panel.add(label);
    }

    @Override
    Component getComponent() {
        return panel;
    }

}
