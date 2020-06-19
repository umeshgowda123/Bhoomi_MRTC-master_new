package app.bmc.com.bhoomi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_ViewMutationStatusResult {

    @SerializedName("ViewMutationStatusResult")
    @Expose
    private String ViewMutationStatusResult;

    public String getViewMutationStatusResult() {
        return ViewMutationStatusResult;
    }

    public void setViewMutationStatusResult(String viewMutationStatusResult) {
        ViewMutationStatusResult = viewMutationStatusResult;
    }
}
