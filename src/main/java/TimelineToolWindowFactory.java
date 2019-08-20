import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMirrorElement;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class TimelineToolWindowFactory implements ToolWindowFactory {

    private JBPanel rootView;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        initVisualization(toolWindow);
    }

    private void initVisualization(ToolWindow toolWindow) {
        rootView = new JBPanel(new GridBagLayout());
        JBScrollPane scrollPane = new JBScrollPane(rootView);

        ContentManager contentManager = toolWindow.getContentManager();
        Content content = contentManager.getFactory().createContent(scrollPane, "Visualization", false);
        contentManager.removeAllContents(true);
        contentManager.addContent(content);
        toolWindow.show(null);
    }

    void visualizeCallHierarchy(PsiMethod rootMethod) {
        ColorService.initColors(getAllMethodNamesOfClass(Objects.requireNonNull(rootMethod.getContainingClass())));
        CallGraph callGraph = ProgressManager.getInstance().computePrioritized(
                (ThrowableComputable<CallGraph, ProcessCanceledException>) () -> CallGraphGenerator.generateCallGraph((rootMethod))
        );
        callGraph.initVisualization(rootView);
    }

    private String[] getAllMethodNamesOfClass(PsiClass containingClass) {
        ArrayList<String> methodNames = new ArrayList<>();
        for (PsiMethod method : containingClass.getMethods()) {
            methodNames.add(method.getName());
        }
        System.out.println(methodNames);
        return methodNames.toArray(new String[0]);
    }

}