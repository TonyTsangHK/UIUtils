package utils.ui;

public enum SelectionState {
    SELECTED("selected"), UNSELECTED("unselected");
    
    public final String desc;
    
    private SelectionState(String desc) {
        this.desc = desc;
    }
    
    public SelectionState oppositeState() {
        if (this == SELECTED) {
            return UNSELECTED;
        } else {
            return SELECTED;
        }
    }
    
    @Override
    public String toString() {
        return desc;
    }
}
