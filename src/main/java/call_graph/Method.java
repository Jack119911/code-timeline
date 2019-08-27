package call_graph;

import com.intellij.psi.PsiMethod;
import visualization.IsHighlightedObservable;

public class Method {

    private final PsiMethod psiMethod;
    private final IsHighlightedObservable isHighlighted = new IsHighlightedObservable(false);

    Method(PsiMethod psiMethod){
        this.psiMethod = psiMethod;
    }

    public String getName() {
        return psiMethod.getName();
    }

    public IsHighlightedObservable getIsHighlighted() {
        return isHighlighted;
    }

    public void jumpToDeclaration() {
        psiMethod.navigate(true);
    }

}
