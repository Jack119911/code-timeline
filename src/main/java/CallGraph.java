import com.intellij.ui.components.JBPanel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

class CallGraph {

    private final CallGraphNode rootNode;

    CallGraph(CallGraphNode rootNode) {
        this.rootNode = rootNode;
    }

    void initVisualization(JBPanel panel) {
        rootNode.updateNecessarySpaceInSubTree();
        initAllNodeVisualizations(panel, rootNode, 0, 0);
    }

    private void initAllNodeVisualizations(JBPanel panel, CallGraphNode currentNode, int currentDepthLevel, int xPosition) {
        initVisualizationOfCurrentNode(panel, currentNode, currentDepthLevel, xPosition);
        initVisualizationOfChildren(panel, currentNode, currentDepthLevel, xPosition);
    }

    private void initVisualizationOfCurrentNode(JBPanel panel, CallGraphNode node, int nodeDepthLevel, int xPosition) {
        Component component = node.getVisualization().getComponent();
        GridBagConstraints constraints = createConstraints(node, nodeDepthLevel, xPosition);
        panel.add(component, constraints);
    }

    @NotNull
    private GridBagConstraints createConstraints(CallGraphNode node, int nodeDepthLevel, int xPosition) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = nodeDepthLevel;
        constraints.gridx = xPosition;
        constraints.gridwidth = node.getNecessarySpace();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        //constraints.weightx = 0.5;
        return constraints;
    }

    private void initVisualizationOfChildren(JBPanel panel, CallGraphNode currentNode, int currentDepthLevel, int xPosition) {
        int xPositionForChild = xPosition;
        for (CallGraphNode child : currentNode.getChildren()) {
            initAllNodeVisualizations(panel, child, currentDepthLevel + 1, xPositionForChild);
            xPositionForChild += child.getNecessarySpace();
        }
    }

    int getGraphDepth() {
        return rootNode.getSubTreeDepth();
    }

    @Override
    public String toString() {
        rootNode.updateNecessarySpaceInSubTree();
        return rootNode.subTreeToString(0);
    }
}
