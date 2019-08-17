public class CallGraph {

    private CallGraphNode rootNode;

    CallGraph(CallGraphNode rootNode) {
        this.rootNode = rootNode;
    }

    @Override
    public String toString() {
        return rootNode.subTreeToString(0);
    }
}
