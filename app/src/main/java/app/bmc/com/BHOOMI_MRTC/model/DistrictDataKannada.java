package app.bmc.com.BHOOMI_MRTC.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import app.bmc.com.BHOOMI_MRTC.interfaces.DistrictModelInterface;

public class DistrictDataKannada implements DistrictModelInterface {
    @ColumnInfo(name = "VLM_DST_ID")
    private int VLM_DST_ID;
    @ColumnInfo(name = "VLM_DKN_NM")
    private String VLM_DKN_NM;
    @ColumnInfo(name = "VLM_DST_NM")
    private String VLM_DST_NM;

    public int getVLM_DST_ID() {
        return VLM_DST_ID;
    }

    public void setVLM_DST_ID(int VLM_DST_ID) {
        this.VLM_DST_ID = VLM_DST_ID;
    }

    public String getVLM_DKN_NM() {
        return VLM_DKN_NM;
    }

    public void setVLM_DKN_NM(String VLM_DKN_NM) {
        this.VLM_DKN_NM = VLM_DKN_NM;
    }

    public String getVLM_DST_NM() {
        return VLM_DST_NM;
    }

    public void setVLM_DST_NM(String VLM_DST_NM) {
        this.VLM_DST_NM = VLM_DST_NM;
    }


    @NonNull
    @Override
    public String toString() {
        return this.VLM_DKN_NM;
    }
}
