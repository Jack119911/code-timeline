package visualization.interaction;

import call_graph.Method;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.psi.JavaCodeFragment;
import com.intellij.psi.JavaCodeFragmentFactory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiMethod;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MethodContentPopupController implements MouseListener {

    private JComponent componentForPopup;
    private Method methodToShow;
    private JBPopup currentPopup;

    public MethodContentPopupController(JComponent componentForPopup, Method methodToShow) {
        this.componentForPopup = componentForPopup;
        this.methodToShow = methodToShow;
    }

    private JComponent createPopupContent() {
        PsiMethod psiMethod = methodToShow.getPsiMethod();
        Project project = psiMethod.getProject();
        JavaCodeFragmentFactory fragmentFactory = JavaCodeFragmentFactory.getInstance(project);
        JavaCodeFragment codeFragment = fragmentFactory.createMemberCodeFragment("  " + psiMethod.getText(), psiMethod.getContext(), psiMethod.isPhysical());
        Document document = PsiDocumentManager.getInstance(project).getDocument(codeFragment);
        return new EditorTextField(document, project, psiMethod.getContainingFile().getFileType(), true, false);
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
        ComponentPopupBuilder builder = JBPopupFactory.getInstance().createComponentPopupBuilder(createPopupContent(), componentForPopup);
        builder.setShowBorder(true);
        currentPopup = builder.createPopup();
        currentPopup.showUnderneathOf(componentForPopup);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        currentPopup.cancel();
    }
}
