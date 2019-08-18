import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

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
        CallGraph callGraph = ProgressManager.getInstance().computePrioritized(
                (ThrowableComputable<CallGraph, ProcessCanceledException>) () -> CallGraphGenerator.generateCallGraph((rootMethod))
        );
        callGraph.initVisualization(rootView);
    }

}