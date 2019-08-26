import com.intellij.ui.components.JBPanel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

class CallGraph {

    private final CallGraphNode rootNode;
    private final ArrayList<Method> processedMethods;

    CallGraph(CallGraphNode rootNode, ArrayList<Method> processedMethods) {
        this.rootNode = rootNode;
        this.processedMethods = processedMethods;
    }

    void initVisualization(JBPanel panel) {
        rootNode.updateNecessarySpaceInSubTree();
        rootNode.updatePaddingUnitsInSubTree(0, 0);
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
        // constraints.weightx = 0.5;
        addPaddingToConstraints(constraints, node);
        return constraints;
    }

    private void addPaddingToConstraints(GridBagConstraints constraints, CallGraphNode node) {
        constraints.insets.top = 1;
        final int paddingStep = 4;
        constraints.insets.left = node.getPaddingUnitsLeft() * paddingStep;
        constraints.insets.right = node.getPaddingUnitsRight() * paddingStep;
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
