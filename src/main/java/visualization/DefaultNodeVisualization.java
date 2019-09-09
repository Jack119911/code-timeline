package visualization;

import call_graph.Method;
import com.intellij.openapi.editor.ex.util.EditorUtil;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import resources.Icons;
import visualization.interaction.MethodContentPopupController;
import visualization.interaction.MethodIsHighlightedController;
import visualization.interaction.MethodNavigationController;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class DefaultNodeVisualization extends NodeVisualization implements IsHighlightedListener, ComponentListener {

    private static final int HEIGHT = 40;
    private static final int FONT_SIZE = 17;
    private static final int TEXT_VERTICAL_OFFSET = (HEIGHT - FONT_SIZE) / 2 + 2;
    private static final int MAXIMAL_WIDTH_PER_ONE_LABEL = 1500;
    private static final String CONDITION_TOOL_TIP_TEXT = "A condition controls whether the method is called or not";
    private static final String LOOP_TOOL_TIP_TEXT = "The method may be called multiple times, because it is surrounded by a loop";
    private static final String RECURSION_TOOL_TIP_TEXT = "Recursion: This method calls itself directly or indirectly";
    private JBPanel panel;
    private JBPanel labelWrapper;
    private int widthForSingleLabel;
    private boolean adjustedNumberOfLabels = false;

    public DefaultNodeVisualization(Method method, boolean callIsOptional, boolean calledMultipleTimes) {
        super(method, callIsOptional, calledMultipleTimes);
    }

    @Override
    protected void createComponent() {
        createPanel();
        createMethodNameLabels(1, 0);
        createIcons();
        initListeners();
    }

    private void createPanel() {
        panel = new JBPanel(new BorderLayout());
        panel.withBackground(color);
        panel.withPreferredHeight(HEIGHT);
        addInteraction(panel, true, true, false);
    }

    private void createMethodNameLabels(int numOfLabels, int gapBetweenLabels) {
        if (labelWrapper != null) {
            panel.remove(labelWrapper);
        }
        labelWrapper = new JBPanel(new FlowLayout(FlowLayout.CENTER, gapBetweenLabels, TEXT_VERTICAL_OFFSET)).andTransparent();

        for (int i = 1; i <= numOfLabels; i++) {
            JBLabel methodNameLabel = new JBLabel(method.getName() + "()");
            methodNameLabel.setFont(new Font(EditorUtil.getEditorFont().getName(), Font.PLAIN, FONT_SIZE));
            addInteraction(methodNameLabel, true, true, true);
            labelWrapper.add(methodNameLabel);
            widthForSingleLabel = methodNameLabel.getPreferredSize().width;
        }

        panel.add(labelWrapper, BorderLayout.CENTER);
    }

    private void createIcons() {
        JBPanel wrapper = new JBPanel().andTransparent();
        JBPanel spaceRight = new JBPanel().andTransparent();

        if (callIsOptional) {
            addIcon(Icons.CONDITION, wrapper, spaceRight, CONDITION_TOOL_TIP_TEXT);
        }
        if (calledMultipleTimes) {
            addIcon(Icons.LOOP, wrapper, spaceRight, LOOP_TOOL_TIP_TEXT);
        }
        if (method.isRecursive()) {
            addIcon(Icons.RECURSION, wrapper, spaceRight, RECURSION_TOOL_TIP_TEXT);
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
        panel.addComponentListener(this);
    }

    @SuppressWarnings("SameParameterValue")
    private void addInteraction(JComponent component, boolean highlightMethodBlock, boolean navigateImplementation, boolean showContentPopup) {
        if (highlightMethodBlock) { component.addMouseListener(new MethodIsHighlightedController(method)); }
        if (navigateImplementation) { component.addMouseListener(new MethodNavigationController(method, panel)); }
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

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        if (!adjustedNumberOfLabels) {
            adjustNumberOfLabels();
            adjustedNumberOfLabels = true;
        }
    }

    private void adjustNumberOfLabels() {
        int width = panel.getWidth();
        int numOfLabels = (width / MAXIMAL_WIDTH_PER_ONE_LABEL) + 1;
        int widthForAllLabels = numOfLabels * widthForSingleLabel;
        int spaceToDistribute = width - widthForAllLabels;
        int spaceBetweenLabels = spaceToDistribute / (numOfLabels + 1);
        createMethodNameLabels(numOfLabels, spaceBetweenLabels);
    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {
        // Do nothing
    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {
        // Do nothing
    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {
        // Do nothing
    }
}
