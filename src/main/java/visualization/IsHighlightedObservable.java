package visualization;

import java.util.ArrayList;

public class IsHighlightedObservable {

    private boolean isHighlighted;
    private ArrayList<IsHighlightedListener> listeners = new ArrayList<>();

    public IsHighlightedObservable(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    void set(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        for (IsHighlightedListener listener : listeners) {
            listener.isHighlightedUpdated(isHighlighted);
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
