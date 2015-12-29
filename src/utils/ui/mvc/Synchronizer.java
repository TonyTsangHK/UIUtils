package utils.ui.mvc;

import utils.data.ValidateResult;

public interface Synchronizer {
    public void synchronizeData();
    public void synchronizeView();
    public DataModel getDataModel();
    public ViewUI getViewUI();
    public ValidateResult validateInputs();
}
