import java.util.ArrayList;

public class CallGraphNode {

    private ArrayList<CallGraphNode> children = new ArrayList<>();
    private String methodName;
    private NodeVisualization visualization;
    private int necessarySpace;

    CallGraphNode(String methodName) {
        this.methodName = methodName;
        this.visualization = new DefaultNodeVisualization(methodName);
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
        builder.append(methodName).append(" (").append(necessarySpace).append(")").append("\n");
    }

    private void addChildrenToStringBuilder(int depth, StringBuilder builder) {
        for (CallGraphNode child : children) {
            builder.append(child.subTreeToString(depth + 1));
        }
    }

    @Override
    public String toString() {
        return methodName;
    }
}
