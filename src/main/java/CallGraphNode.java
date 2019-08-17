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

    String subTreeToString(int dept) {
        StringBuilder builder = new StringBuilder();

        addSelfToStringBuilder(dept, builder);
        addChildrenToStringBuilder(dept, builder);

        return builder.toString();
    }

    private void addSelfToStringBuilder(int dept, StringBuilder builder) {
        for (int i = 0; i < dept; i++) {
            builder.append("\t");
        }
        builder.append(methodName).append("\n");
    }

    private void addChildrenToStringBuilder(int dept, StringBuilder builder) {
        for (CallGraphNode child : children) {
            builder.append(child.subTreeToString(dept + 1));
        }
    }

    @Override
    public String toString() {
        return methodName;
    }
}
