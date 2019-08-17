import com.intellij.psi.PsiElement;

public class CallGraphGenerator {

    private CallGraphGenerator() {}

    public static CallGraph generateCallGraph(PsiElement rootPsiElement) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    static CallGraph generateMockCallGraph() {
        CallGraphNode rootNode = new CallGraphNode("rootMethod");

        rootNode.addChild(new CallGraphNode("firstChild"));
        rootNode.addChild(new CallGraphNode("secondChild"));

        CallGraphNode childWithChildren = new CallGraphNode("thirdChildWithChildren");
        childWithChildren.addChild(new CallGraphNode("firstNestedChild"));
        childWithChildren.addChild(new CallGraphNode("secondNestedChild"));
        rootNode.addChild(childWithChildren);

        return new CallGraph(rootNode);
    }

}
