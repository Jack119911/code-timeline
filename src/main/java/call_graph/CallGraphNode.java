package call_graph;

import visualization.DefaultNodeVisualization;
import visualization.NodeVisualization;

import java.util.ArrayList;

class CallGraphNode {

    private final ArrayList<CallGraphNode> children = new ArrayList<>();
    private final Method method;
    private final boolean callIsOptional;
    private final boolean calledMultipleTimes;
    private NodeVisualization visualization;
    private int necessarySpace;
    private int paddingUnitsLeft;
    private int paddingUnitsRight;

    CallGraphNode(Method method, boolean callIsOptional, boolean calledMultipleTimes) {
        this.method = method;
        this.callIsOptional = callIsOptional;
        this.calledMultipleTimes = calledMultipleTimes;
    }

    ArrayList<CallGraphNode> getChildren() {
        return children;
    }

    void addChildren(ArrayList<CallGraphNode> childrenToAdd) {
        children.addAll(childrenToAdd);
    }

    void addChild(CallGraphNode childToAdd) {
        this.children.add(childToAdd);
    }

    NodeVisualization getVisualization() {
        if (visualization == null) {
            visualization = new DefaultNodeVisualization(method, callIsOptional, calledMultipleTimes);
        }
        return visualization;
    }

    int getNecessarySpace() {
        return necessarySpace;
    }

    void updateNecessarySpaceInSubTree() {
        if (children.isEmpty()) necessarySpace = 1;
        else {
            necessarySpace = 0;
            for (CallGraphNode child : children) {
                child.updateNecessarySpaceInSubTree();
                necessarySpace += child.getNecessarySpace();
            }
        }
    }

    int getPaddingUnitsLeft() {
        return paddingUnitsLeft;
    }

    int getPaddingUnitsRight() {
        return paddingUnitsRight;
    }

    void updatePaddingUnitsInSubTree(int ownPaddingUnitsLeft, int ownPaddingUnitsRight) {
        updateOwnPadding(ownPaddingUnitsLeft, ownPaddingUnitsRight);
        if (children.size() == 1) {
            children.get(0).updatePaddingUnitsInSubTree(ownPaddingUnitsLeft + 1, ownPaddingUnitsRight + 1);
        } else if (children.size() >= 2) {
            children.get(0).updatePaddingUnitsInSubTree(ownPaddingUnitsLeft + 1, 0);
            children.get(children.size() - 1).updatePaddingUnitsInSubTree(1, ownPaddingUnitsRight + 1);
            for (int i = 1; i < children.size() - 1; i++) {
                children.get(i).updatePaddingUnitsInSubTree(1, 0);
            }
        }

    }

    private void updateOwnPadding(int ownPaddingUnitsLeft, int ownPaddingUnitsRight) {
        paddingUnitsLeft = ownPaddingUnitsLeft;
        paddingUnitsRight = ownPaddingUnitsRight;
    }

    int getSubTreeDepth() {
        int subTreeDepth = 1;

        int maxChildSubTreeDepth = 0;
        for (CallGraphNode child : children) {
            int childTreeDepth = child.getSubTreeDepth();
            if (childTreeDepth > maxChildSubTreeDepth) maxChildSubTreeDepth = childTreeDepth;
        }
        subTreeDepth += maxChildSubTreeDepth;

        return subTreeDepth;
    }

    String subTreeToString(int depth) {
        StringBuilder builder = new StringBuilder();

        addSelfToStringBuilder(depth, builder);
        addChildrenToStringBuilder(depth, builder);

        return builder.toString();
    }

    private void addSelfToStringBuilder(int depth, StringBuilder builder) {
        for (int i = 0; i < depth; i++) {
            builder.append("\t");
        }
        builder.append(method.getName()).append(" (").append(necessarySpace).append(")").append("\n");
    }

    private void addChildrenToStringBuilder(int depth, StringBuilder builder) {
        for (CallGraphNode child : children) {
            builder.append(child.subTreeToString(depth + 1));
        }
    }

    @Override
    public String toString() {
        return method.getName();
    }
}
