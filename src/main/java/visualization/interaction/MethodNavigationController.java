package visualization.interaction;

import call_graph.Method;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MethodNavigationController implements MouseListener {

    private Method methodToNavigateTo;
    private JComponent origin;

    public MethodNavigationController(Method methodToNavigateTo, JComponent origin) {
        this.methodToNavigateTo = methodToNavigateTo;
        this.origin = origin;
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        methodToNavigateTo.jumpToDeclaration();
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
        origin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        // Do nothing
    }
}
