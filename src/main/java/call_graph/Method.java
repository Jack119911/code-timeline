package call_graph;

import visualization.IsHighlightedObservable;

public class Method {

    private final String name;
    private final IsHighlightedObservable isHighlighted = new IsHighlightedObservable(false);

    Method(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public IsHighlightedObservable getIsHighlighted() {
        return isHighlighted;
    }

}
