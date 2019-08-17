import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TimelineToolWindow implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        CallGraph callGraph = CallGraphGenerator.generateMockCallGraph();
        System.out.println(callGraph);
        System.out.println(callGraph.getGraphDepth());

        JBPanel panel = new JBPanel(new GridBagLayout());
        JBScrollPane scrollPane = new JBScrollPane(panel);

        ContentManager contentManager = toolWindow.getContentManager();
        Content content = contentManager.getFactory().createContent(scrollPane, "Visualization", false);
        contentManager.addContent(content);

        callGraph.initVisualization(panel);
    }

}