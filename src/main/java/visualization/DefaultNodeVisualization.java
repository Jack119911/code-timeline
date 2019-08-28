package visualization;

import call_graph.Method;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import icons.Icons;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DefaultNodeVisualization extends NodeVisualization implements IsHighlightedListener, MouseListener {

    private static final String FONT_STYLE = "Serif";
    private static final int FONT_SIZE = 20;
    private static final String CONDITION_TOOL_TIP_TEXT = "A condition controls whether the method is called or not";
    private static final String LOOP_TOOL_TIP_TEXT = "The method may be called multiple times, because it is surrounded by a loop";
    private JBPanel panel;
    private static final int HEIGHT = 40;

    public DefaultNodeVisualization(Method method, boolean callIsOptional, boolean calledMultipleTimes) {
        super(method, callIsOptional, calledMultipleTimes);
    }

    @Override
    protected void createComponent() {
        createPanel();
        createLabel();
        createIcons();
        initListeners();
    }

    private void createPanel() {
        panel = new JBPanel(new BorderLayout());
        panel.withBackground(color);
        panel.withPreferredHeight(HEIGHT);
    }

    private void createLabel() {
        JBPanel wrapper = new JBPanel().andTransparent();

        JBLabel label = new JBLabel(method.getName() + "()");
        label.setFont(new Font(FONT_STYLE, Font.PLAIN, FONT_SIZE));
        wrapper.add(label);

        panel.add(wrapper, BorderLayout.CENTER);
    }

    private void createIcons() {
        JBPanel wrapper = new JBPanel().andTransparent();
        JBPanel spaceRight = new JBPanel().andTransparent();

        if (callIsOptional) {
            addIcon(Icons.CONDITION_ICON, wrapper, spaceRight, CONDITION_TOOL_TIP_TEXT);
        }
        if (calledMultipleTimes) {
            addIcon(Icons.LOOP_ICON, wrapper, spaceRight, LOOP_TOOL_TIP_TEXT);
        }

        panel.add(wrapper, BorderLayout.LINE_START);
        panel.add(spaceRight, BorderLayout.LINE_END);
    }

    private void addIcon(Icon icon, JBPanel panelForIcon, JPanel panelForSpace, String toolTipText) {
        JBLabel iconLabel = new JBLabel(icon);
        iconLabel.setToolTipText(toolTipText);
        panelForIcon.add(iconLabel);
        panelForSpace.add(Box.createRigidArea(new Dimension(icon.getIconWidth(), 1)));
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
