package visualization.interaction;

import call_graph.Method;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MethodNavigationController implements MouseListener {

    private Method methodToNavigateTo;

    public MethodNavigationController(Method methodToNavigateTo) {
        this.methodToNavigateTo = methodToNavigateTo;
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
        // Do nothing
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        // Do nothing
    }
}
