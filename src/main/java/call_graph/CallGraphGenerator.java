package call_graph;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.java.PsiMethodCallExpressionImpl;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class CallGraphGenerator {

    private static ArrayList<Method> createdMethods = new ArrayList<>();

    private CallGraphGenerator() {}

    public static CallGraph generateCallGraph(PsiMethod rootPsiMethod) {
        createdMethods.clear();
        CallGraphNode rootNode = createNodeFromPsiMethod(rootPsiMethod, new PsiMethodCallExpressionImpl());
        PsiClass containingClass = rootPsiMethod.getContainingClass();
        buildTreeForCallHierarchy(rootPsiMethod, rootNode, containingClass);
        return new CallGraph(rootNode, createdMethods);
    }

    private static CallGraphNode createNodeFromPsiMethod(PsiMethod psiMethod, PsiMethodCallExpression methodCall) {
        Method method = getMethodFromPsiMethod(psiMethod);
        return new CallGraphNode(method, checkIfMethodCallIsOptional(methodCall), checkIfMethodIsCalledMultipleTimes(methodCall));
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
            if (childMethodCall.resolveMethod() != rootPsiMethod) {
                processChildMethodCall(childMethodCall, rootNode, classToBuildTreeFor);
            }
        }
    }

    private static void processChildMethodCall(PsiMethodCallExpression childMethodCall, CallGraphNode parentNode, PsiClass classToBuildTreeFor) {
        PsiMethod childPsiMethod = childMethodCall.resolveMethod();
        if (childPsiMethod != null && childPsiMethod.getContainingClass() == classToBuildTreeFor) {
            CallGraphNode childNode = createNodeFromPsiMethod(childPsiMethod, childMethodCall);
            parentNode.addChild(childNode);
            buildTreeForCallHierarchy(childPsiMethod, childNode, classToBuildTreeFor);
        }
    }

    private static boolean checkIfMethodCallIsOptional(PsiMethodCallExpression methodCall) {
        return checkIfSurroundedByForLoop(methodCall) || checkIfSurroundedBySwitchBlock(methodCall) || checkIfSurroundedByConditionalExpression(methodCall);
    }

    private static boolean checkIfSurroundedByForLoop(PsiMethodCallExpression methodCall) {
        PsiIfStatement surroundingIf = PsiTreeUtil.getParentOfType(methodCall, PsiIfStatement.class);
        if (surroundingIf == null) {
            return false;
        } else {
            return methodCall != surroundingIf.getCondition();
        }
    }

    private static boolean checkIfSurroundedBySwitchBlock(PsiMethodCallExpression methodCall) {
        PsiSwitchBlock surroundingSwitch = PsiTreeUtil.getParentOfType(methodCall, PsiSwitchBlock.class);
        if (surroundingSwitch == null) {
            return false;
        } else {
            return surroundingSwitch.getExpression() != methodCall;
        }
    }

    private static boolean checkIfSurroundedByConditionalExpression(PsiMethodCallExpression methodCall) {
        PsiConditionalExpression surroundingConditionalExpression = PsiTreeUtil.getParentOfType(methodCall, PsiConditionalExpression.class);
        if (surroundingConditionalExpression == null) {
            return false;
        } else {
            return methodCall != surroundingConditionalExpression.getCondition();
        }
    }

    private static boolean checkIfMethodIsCalledMultipleTimes(PsiMethodCallExpression methodCall) {
        PsiLoopStatement surroundingLoop = PsiTreeUtil.getParentOfType(methodCall, PsiLoopStatement.class);
        if (surroundingLoop == null) {
            return false;
        } else {
            return PsiTreeUtil.isAncestor(surroundingLoop.getBody(), methodCall, false);
        }
    }

}
