import com.intellij.psi.PsiElement;
import org.apache.commons.lang.NotImplementedException;

public class CallGraphGenerator {

    private CallGraphGenerator() {}

    public static CallGraph generateCallGraph(PsiElement rootPsiElement) {
        throw new NotImplementedException();
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
