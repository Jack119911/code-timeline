package visualization;

import com.intellij.ui.components.JBPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TutorialNavigationListener implements ActionListener {

    private JBPanel pages;
    private boolean next;

    public TutorialNavigationListener(JBPanel pages, boolean next) {
        this.pages = pages;
        this.next = next;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        CardLayout cardLayout = (CardLayout) pages.getLayout();
        if (next) {
            cardLayout.next(pages);
        } else {
            cardLayout.previous(pages);
        }
    }
}
