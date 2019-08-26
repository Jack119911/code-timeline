import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

class CallGraphGenerator {

    private static ArrayList<Method> createdMethods = new ArrayList<>();

    private CallGraphGenerator() {}

    static CallGraph generateCallGraph(PsiMethod rootPsiMethod) {
        createdMethods.clear();
        CallGraphNode rootNode = createNodeFromPsiMethod(rootPsiMethod);
        PsiClass containingClass = rootPsiMethod.getContainingClass();
        buildTreeForCallHierarchy(rootPsiMethod, rootNode, containingClass);
        return new CallGraph(rootNode, createdMethods);
    }

    private static CallGraphNode createNodeFromPsiMethod(PsiMethod psiMethod) {
        Method method = getMethodFromName(psiMethod.getName());
        return new CallGraphNode(method);
    }

    private static Method getMethodFromName(String methodName) {
        for (Method existingMethod : createdMethods) {
            if (methodName.equals(existingMethod.getName())) {
                return existingMethod;
            }
        }
        return createMethod(methodName);
    }

    @NotNull
    private static Method createMethod(String methodName) {
        Method newMethod = new Method(methodName);
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
