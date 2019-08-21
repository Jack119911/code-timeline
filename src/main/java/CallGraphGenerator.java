import com.intellij.openapi.application.ReadAction;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

class CallGraphGenerator {

    private CallGraphGenerator() {}

    static CallGraph generateCallGraph(PsiMethod rootMethod) {
        CallGraphNode rootNode = createNodeFromMethod(rootMethod);
        PsiClass containingClass = rootMethod.getContainingClass();
        buildTreeForCallHierarchy(rootMethod, rootNode, containingClass);
        return new CallGraph(rootNode);
    }

    private static CallGraphNode createNodeFromMethod(PsiMethod method) {
        return new CallGraphNode(method.getName());
    }

    private static void buildTreeForCallHierarchy(PsiMethod rootMethod, CallGraphNode rootNode, PsiClass classToBuildTreeFor) {
        Collection<PsiMethodCallExpression> children = PsiTreeUtil.findChildrenOfAnyType(rootMethod, true, PsiMethodCallExpression.class);
        for (PsiMethodCallExpression childMethodCall : children) {
            processChildMethodCall(childMethodCall, rootNode, classToBuildTreeFor);
        }
    }

    private static void processChildMethodCall(PsiMethodCallExpression childMethodCall, CallGraphNode parentNode, PsiClass classToBuildTreeFor) {
        PsiMethod childMethod = childMethodCall.resolveMethod();
        if (childMethod != null && childMethod.getContainingClass() == classToBuildTreeFor) {
            CallGraphNode childNode = createNodeFromMethod(childMethod);
            parentNode.addChild(childNode);
            buildTreeForCallHierarchy(childMethod, childNode, classToBuildTreeFor);
        }
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
