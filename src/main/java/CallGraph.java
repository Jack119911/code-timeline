public class CallGraph {

    private CallGraphNode rootNode;

    CallGraph(CallGraphNode rootNode) {
        this.rootNode = rootNode;
    }

    int getGraphDepth() {
        return rootNode.getSubTreeDepth();
    }

    @Override
    public String toString() {
        return rootNode.subTreeToString(0);
    }
}
