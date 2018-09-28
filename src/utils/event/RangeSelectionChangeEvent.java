package utils.event;

import utils.math.IntegerRange;
import utils.ui.component.RangeSelectionPanel;
import utils.ui.component.RangeSelectionPanel.RangeComponent;

import java.util.EventObject;

@SuppressWarnings("serial")
public class RangeSelectionChangeEvent extends EventObject {
    public enum Type {
        RANGE_SELECT("rangeSelect"), RANGE_MODIFY("rangeModify"), RANGE_ADD_MODIFY("rangeAddModify"),
        RANGE_DELETE_MODIFY("rangeDeleteModify");
        
        public final String desc;
        
        private Type(String desc) {
            this.desc = desc;
        }
        
        @Override
        public String toString() {
            return this.desc;
        }
    }
    
    private Type type;
    
    private RangeSelectionPanel sourceRangeSelectionPanel;
    
    private RangeComponent selectedRangeComponent = null, originalSelectedRangeComponent = null,
        affectedRangeComponent = null;
    
    private IntegerRange shrinkingRange, expandingRange, rangeBeforeShrinking, rangeBeforeExpanding;
    
    public RangeSelectionChangeEvent(
            RangeSelectionPanel srcPanel,             
            RangeComponent      originalSelectedRangeComponent,
            RangeComponent      selectedRangeComponent
    ) {
        super(srcPanel);
        
        this.type = Type.RANGE_SELECT;
        
        this.sourceRangeSelectionPanel = srcPanel;
        
        this.originalSelectedRangeComponent = originalSelectedRangeComponent;
        this.selectedRangeComponent         = selectedRangeComponent;
    }
    
    public RangeSelectionChangeEvent(
            RangeSelectionPanel srcPanel,
            Type                type,
            RangeComponent      affectedRangeComponent,
            IntegerRange        shrinkRange,
            IntegerRange        expandingRange,
            IntegerRange        rangeBeforeShrinking,
            IntegerRange        rangeBeforeExpanding
    ) {
        super(srcPanel);
        
        this.type = type;
        
        this.affectedRangeComponent = affectedRangeComponent;
        
        this.sourceRangeSelectionPanel = srcPanel;
        
        this.shrinkingRange            = shrinkRange;
        this.expandingRange            = expandingRange;
        this.rangeBeforeShrinking      = rangeBeforeShrinking;
        this.rangeBeforeExpanding      = rangeBeforeExpanding;
    }
    
    public Type getType() {
        return type;
    }
    
    public RangeComponent getAffectedRangeComponent() {
        return affectedRangeComponent;
    }
    
    public RangeSelectionPanel getSourceRangeSelectionPanel() {
        return sourceRangeSelectionPanel;
    }
    
    public IntegerRange getShrinkingRange() {
        return shrinkingRange;
    }
    
    public IntegerRange getExpandingRange() {
        return expandingRange;
    }
    
    public IntegerRange getRangeBeforeShrinking() {
        return rangeBeforeShrinking;
    }
    
    public IntegerRange getRangeBeforeExpanding() {
        return rangeBeforeExpanding;
    }
    
    public RangeComponent getOriginalSelectedRangeComponent() {
        return originalSelectedRangeComponent;
    }
    
    public RangeComponent getSelectedRagneComponent() {
        return selectedRangeComponent;
    }
}
