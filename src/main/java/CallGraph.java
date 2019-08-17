import com.intellij.ui.components.JBPanel;

public class CallGraph {

    private CallGraphNode rootNode;

    CallGraph(CallGraphNode rootNode) {
        this.rootNode = rootNode;
    }

    void initVisualization(JBPanel panel) {
        addNodeVisualizationsToPanel(panel, rootNode);
    }

    private void addNodeVisualizationsToPanel(JBPanel panel, CallGraphNode currentNode) {
        panel.add(currentNode.getVisualization().getComponent());
        for (CallGraphNode child : currentNode.getChildren()) {
            addNodeVisualizationsToPanel(panel, child);
        }
    }

    int getGraphDepth() {
        return rootNode.getSubTreeDepth();
    }

    @Override
    public String toString() {
        return rootNode.subTreeToString(0);
    }
}
