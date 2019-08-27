package visualization;

import call_graph.Method;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DefaultNodeVisualization extends NodeVisualization implements IsHighlightedListener, MouseListener {

    private JBPanel panel;
    private static final int HEIGHT = 40;

    public DefaultNodeVisualization(Method method, boolean callIsOptional, boolean calledMultipleTimes) {
        super(method, callIsOptional, calledMultipleTimes);
    }

    @Override
    protected void createComponent() {
        createPanel();
        createLabel();
        initListeners();
    }

    private void createLabel() {
        JBLabel label = new JBLabel(method.getName() + "()");
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        panel.add(label);
    }

    private void createPanel() {
        panel = new JBPanel();
        panel.withBackground(color);
        panel.withPreferredHeight(HEIGHT);
    }

    private void initListeners() {
        method.getIsHighlighted().addListener(this);
        panel.addMouseListener(this);
    }

    @Override
    public void isHighlightedUpdated(boolean isHighlighted) {
        if (isHighlighted) {
            panel.withBackground(color.brighter());
        } else {
            panel.withBackground(color);
        }
    }

    @Override
    public Component getComponent() {
        return panel;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        method.jumpToDeclaration();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        // Do nothing
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        // Do nothing
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        method.getIsHighlighted().set(true);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        method.getIsHighlighted().set(false);
    }
}
