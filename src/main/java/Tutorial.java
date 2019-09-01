import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.util.ui.JBImageIcon;
import org.jetbrains.annotations.NotNull;
import resources.Images;
import resources.TutorialText;
import visualization.StyleSettings;

import javax.swing.*;
import java.awt.*;

class Tutorial {

    private static final int FONT_SIZE = 15;
    private static final int MARGIN_LEFT = 5;
    private static final int MARGIN_TOP = 5;

    private Tutorial() {}

    static JComponent getTutorialComponent() {
        JBPanel panel = new JBPanel(new GridBagLayout());
        panel.add(createText(TutorialText.INTRO), getTextConstraints());
        panel.add(createPic(Images.TUTORIAL_INTRODUCTION), getPicConstraints());
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
        textArea.setFont(new Font(StyleSettings.getInstance().getMonoSpaceFontName(), Font.PLAIN, FONT_SIZE));
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

    private static JComponent createPic(Image image) {
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

}
