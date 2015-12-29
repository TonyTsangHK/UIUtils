package utils.ui.mvc;


public class ModelViewControllerPair {
    private DataModel dataModel;
    private Synchronizer synchronizer;
    private ViewUI viewUI;
    
    public ModelViewControllerPair(DataModel dataModel, Synchronizer synchronizer, ViewUI viewUI) {
        setDataModel(dataModel);
        setSynchronizer(synchronizer);
        setViewUI(viewUI);
    }
    
    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }
    
    public DataModel getDataModel() {
        return dataModel;
    }
    
    public void setSynchronizer(Synchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }
    
    public Synchronizer getSynchronizer() {
        return synchronizer;
    }
    
    public void setViewUI(ViewUI viewUI) {
        this.viewUI = viewUI;
    }
    
    public ViewUI getViewUI() {
        return viewUI;
    }
}
