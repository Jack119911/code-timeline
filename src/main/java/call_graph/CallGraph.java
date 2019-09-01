package call_graph;

import com.intellij.ui.components.JBPanel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class CallGraph {

    private static final int PADDING_STEP = 10;
    private static final int GAP_BETWEEN_LINES = 1;
    private static final boolean USE_PYRAMID_LAYOUT = false;
    private final CallGraphNode rootNode;
    private final ArrayList<Method> processedMethods;

    CallGraph(CallGraphNode rootNode, ArrayList<Method> processedMethods) {
        this.rootNode = rootNode;
        this.processedMethods = processedMethods;
    }

    public void initVisualization(JBPanel panel) {
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
        if (USE_PYRAMID_LAYOUT) {
            addPyramidPaddingToConstraints(constraints, node);
        } else {
            addUniformPaddingToConstraints(constraints, node);
        }
        return constraints;
    }

    private void addUniformPaddingToConstraints(GridBagConstraints constraints, CallGraphNode node) {
        constraints.insets.top = GAP_BETWEEN_LINES;
        constraints.insets.left = GAP_BETWEEN_LINES;
    }

    private void addPyramidPaddingToConstraints(GridBagConstraints constraints, CallGraphNode node) {
        constraints.insets.top = GAP_BETWEEN_LINES;
        constraints.insets.left = node.getPaddingUnitsLeft() * PADDING_STEP;
        constraints.insets.right = node.getPaddingUnitsRight() * PADDING_STEP;
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
