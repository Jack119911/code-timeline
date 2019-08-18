import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.PsiElement;
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

    public void visualizeCallHierarchy(PsiElement rootElement) {
        //CallGraph callGraph = CallGraphGenerator.generateCallGraph(rootElement);
        System.out.println(rootElement);
        CallGraph callGraph = CallGraphGenerator.generateMockCallGraph();
        System.out.println(callGraph);
        System.out.println(callGraph.getGraphDepth());

        callGraph.initVisualization(rootView);
    }

}