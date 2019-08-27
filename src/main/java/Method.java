public class Method {

    private final String name;
    private final IsHighlightedObservable isHighlighted = new IsHighlightedObservable(false);

    Method(String name){
        this.name = name;
    }

    String getName() {
        return name;
    }

    IsHighlightedObservable getIsHighlighted() {
        return isHighlighted;
    }

}
