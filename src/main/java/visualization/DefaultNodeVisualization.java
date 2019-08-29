package visualization;

import call_graph.Method;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import resources.Icons;
import visualization.interaction.MethodContentPopupController;
import visualization.interaction.MethodIsHighlightedController;
import visualization.interaction.MethodNavigationController;


import javax.swing.*;
import java.awt.*;

public class DefaultNodeVisualization extends NodeVisualization implements IsHighlightedListener {

    private static final int HEIGHT = 40;
    private static final String FONT_STYLE = "Serif";
    private static final int FONT_SIZE = 20;
    private static final String CONDITION_TOOL_TIP_TEXT = "A condition controls whether the method is called or not";
    private static final String LOOP_TOOL_TIP_TEXT = "The method may be called multiple times, because it is surrounded by a loop";
    private JBPanel panel;

    public DefaultNodeVisualization(Method method, boolean callIsOptional, boolean calledMultipleTimes) {
        super(method, callIsOptional, calledMultipleTimes);
    }

    @Override
    protected void createComponent() {
        createPanel();
        createMethodNameLabel();
        createIcons();
        initListeners();
    }

    private void createPanel() {
        panel = new JBPanel(new BorderLayout());
        panel.withBackground(color);
        panel.withPreferredHeight(HEIGHT);
        addInteraction(panel, true, true, false);
    }

    private void createMethodNameLabel() {
        JBPanel wrapper = new JBPanel().andTransparent();

        JBLabel methodNameLabel = new JBLabel(method.getName() + "()");
        methodNameLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, FONT_SIZE));
        addInteraction(methodNameLabel, true, true, true);
        wrapper.add(methodNameLabel);

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
        addInteraction(iconLabel, true, false, false);
        panelForIcon.add(iconLabel);
        panelForSpace.add(Box.createRigidArea(new Dimension(icon.getIconWidth(), 1)));
    }

    private void initListeners() {
        method.getIsHighlighted().addListener(this);
    }

    @SuppressWarnings("SameParameterValue")
    private void addInteraction(JComponent component, boolean highlightMethodBlock, boolean navigateImplementation, boolean showContentPopup) {
        if (highlightMethodBlock) { component.addMouseListener(new MethodIsHighlightedController(method)); }
        if (navigateImplementation) { component.addMouseListener(new MethodNavigationController(method)); }
        if (showContentPopup) { component.addMouseListener(new MethodContentPopupController(component, method)); }
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

}
