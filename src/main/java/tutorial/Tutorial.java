package tutorial;

import com.intellij.openapi.editor.ex.util.EditorUtil;
import com.intellij.ui.components.*;
import com.intellij.util.ui.JBImageIcon;
import org.jetbrains.annotations.NotNull;
import resources.Icons;


import javax.swing.*;
import java.awt.*;

public class Tutorial {

    private static final int FONT_SIZE = 15;
    private static final int MARGIN_LEFT = 5;
    private static final int MARGIN_TOP = 5;

    private Tutorial() {}

    public static JComponent getTutorialComponent() {
        JBPanel pages = new JBPanel(new CardLayout());
        addContent(pages);
        return pages;
    }

    private static void addContent(JBPanel pages) {
        for (TutorialPageContentEntry entry : TutorialPageContent.content) {
            pages.add(createTutorialPage(entry.getText(), entry.getImage(), pages));
        }
    }

    @NotNull
    private static JComponent createTutorialPage(String text, Image image, JBPanel parent) {
        JBPanel panel = new JBPanel(new GridBagLayout());
        panel.add(createText(text), getTextConstraints());
        panel.add(createPic(image), getPicConstraints());
        panel.add(createNavigation(parent), getNavigationConstraints());
        JBPanel wrapper = new JBPanel(new FlowLayout(FlowLayout.LEADING, MARGIN_LEFT, MARGIN_TOP));
        wrapper.add(panel);
        return new JBScrollPane(wrapper);
    }

    private static JComponent createText(String text) {
        JBTextArea textArea = createTextArea(text);
        return createScrollPane(textArea);
    }

    @NotNull
    private static JBScrollPane createScrollPane(JBTextArea textArea) {
        JBScrollPane scrollPane = new JBScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 200));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }

    @NotNull
    private static JBTextArea createTextArea(String text) {
        JBTextArea textArea = new JBTextArea(text);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setBorder(BorderFactory.createEmptyBorder());
        textArea.setFont(new Font(EditorUtil.getEditorFont().getName(), Font.PLAIN, FONT_SIZE));
        return textArea;
    }

    private static GridBagConstraints getTextConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.5;
        constraints.fill = GridBagConstraints.VERTICAL;
        return constraints;
    }

    private static JComponent createPic(@SuppressWarnings("SameParameterValue") Image image) {
        JBLabel label = new JBLabel(new JBImageIcon(image));
        label.setBorder(BorderFactory.createEtchedBorder());
        return label;
    }

    private static GridBagConstraints getPicConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.5;
        return constraints;
    }

    private static JComponent createNavigation(JBPanel parent) {
        JBPanel wrapper = new JBPanel();
        JButton left = new JButton(Icons.NAVIGATION_LEFT);
        JButton right = new JButton(Icons.NAVIGATION_RIGHT);
        left.addActionListener(new TutorialNavigationListener(parent, false));
        right.addActionListener(new TutorialNavigationListener(parent, true));
        wrapper.add(left);
        wrapper.add(right);
        return wrapper;
    }

    private static GridBagConstraints getNavigationConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        return constraints;
    }

}
