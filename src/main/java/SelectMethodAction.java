import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

public class SelectMethodAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        ToolWindow timeline = ToolWindowManager.getInstance(e.getProject()).getToolWindow("Timeline");
        TimelineToolWindowFactory factory = new TimelineToolWindowFactory();
        factory.createToolWindowContent(e.getProject(), timeline);
        factory.visualizeCallHierarchy(psiElement);
    }

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        final boolean isMethod = e.getData(CommonDataKeys.PSI_ELEMENT) instanceof PsiMethod;
        e.getPresentation().setVisible(project != null && editor != null && isMethod);
    }
}
