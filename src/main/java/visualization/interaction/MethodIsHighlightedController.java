package visualization.interaction;

import call_graph.Method;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MethodIsHighlightedController implements MouseListener {

    private Method methodToHighlight;

    public MethodIsHighlightedController(Method methodToHighlight) {
        this.methodToHighlight = methodToHighlight;
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        // Do nothing
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
        methodToHighlight.getIsHighlighted().set(true);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        methodToHighlight.getIsHighlighted().set(false);
    }
}
