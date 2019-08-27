package call_graph;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class CallGraphGenerator {

    private static ArrayList<Method> createdMethods = new ArrayList<>();

    private CallGraphGenerator() {}

    public static CallGraph generateCallGraph(PsiMethod rootPsiMethod) {
        createdMethods.clear();
        CallGraphNode rootNode = createNodeFromPsiMethod(rootPsiMethod);
        PsiClass containingClass = rootPsiMethod.getContainingClass();
        buildTreeForCallHierarchy(rootPsiMethod, rootNode, containingClass);
        return new CallGraph(rootNode, createdMethods);
    }

    private static CallGraphNode createNodeFromPsiMethod(PsiMethod psiMethod) {
        Method method = getMethodFromPsiMethod(psiMethod);
        return new CallGraphNode(method);
    }

    private static Method getMethodFromPsiMethod(PsiMethod psiMethod) {
        for (Method existingMethod : createdMethods) {
            if (psiMethod.getName().equals(existingMethod.getName())) {
                return existingMethod;
            }
        }
        return createMethod(psiMethod);
    }

    @NotNull
    private static Method createMethod(PsiMethod psiMethod) {
        Method newMethod = new Method(psiMethod);
        createdMethods.add(newMethod);
        return newMethod;
    }

    private static void buildTreeForCallHierarchy(PsiMethod rootPsiMethod, CallGraphNode rootNode, PsiClass classToBuildTreeFor) {
        Collection<PsiMethodCallExpression> children = PsiTreeUtil.findChildrenOfAnyType(rootPsiMethod, true, PsiMethodCallExpression.class);
        for (PsiMethodCallExpression childMethodCall : children) {
            processChildMethodCall(childMethodCall, rootNode, classToBuildTreeFor);
        }
    }

    private static void processChildMethodCall(PsiMethodCallExpression childMethodCall, CallGraphNode parentNode, PsiClass classToBuildTreeFor) {
        PsiMethod childPsiMethod = childMethodCall.resolveMethod();
        if (childPsiMethod != null && childPsiMethod.getContainingClass() == classToBuildTreeFor) {
            CallGraphNode childNode = createNodeFromPsiMethod(childPsiMethod);
            parentNode.addChild(childNode);
            buildTreeForCallHierarchy(childPsiMethod, childNode, classToBuildTreeFor);
        }
    }

}
