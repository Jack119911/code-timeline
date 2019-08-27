import java.util.ArrayList;

class IsHighlightedObservable {

    private boolean isHighlighted;
    private ArrayList<IsHighlightedListener> listeners = new ArrayList<>();

    IsHighlightedObservable(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    void set(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        for (IsHighlightedListener listener : listeners) {
            listener.highlightUpdated(isHighlighted);
        }
    }

    boolean get() {
        return isHighlighted;
    }

    void addListener(IsHighlightedListener isHighlightedListener) {
        listeners.add(isHighlightedListener);
    }

    void removeListener(IsHighlightedListener isHighlightedListener) {
        listeners.remove(isHighlightedListener);
    }
}
