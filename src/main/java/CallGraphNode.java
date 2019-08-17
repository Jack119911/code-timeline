import java.util.ArrayList;

public class CallGraphNode {

    private ArrayList<CallGraphNode> children = new ArrayList<>();
    private String methodName;

    CallGraphNode(String methodName) {
        this.methodName = methodName;
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
        builder.append(methodName).append("\n");
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
