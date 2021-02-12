package app.bmc.com.BHOOMI_MRTC.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_ViewMutationStatusResult {

    @SerializedName("ViewMutationStatus")
    @Expose
    private String ViewMutationStatusResult;

    public String getViewMutationStatusResult() {
        return ViewMutationStatusResult;
    }

    public void setViewMutationStatusResult(String viewMutationStatusResult) {
        ViewMutationStatusResult = viewMutationStatusResult;
    }
}
