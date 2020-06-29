package app.bmc.com.BHOOMI_MRTC.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import app.bmc.com.BHOOMI_MRTC.interfaces.HobliModelInterface;

public class HobliDataKannada implements HobliModelInterface {
    @ColumnInfo(name = "VLM_HBL_ID")
    private int VLM_HBL_ID;

    @ColumnInfo(name = "VLM_HKN_NM")
    private String VLM_HKN_NM;
    @ColumnInfo(name = "VLM_HBL_NM")
    private String VLM_HBL_NM;

    public int getVLM_HBL_ID() {
        return VLM_HBL_ID;
    }

    public void setVLM_HBL_ID(int VLM_HBL_ID) {
        this.VLM_HBL_ID = VLM_HBL_ID;
    }

    public String getVLM_HKN_NM() {
        return VLM_HKN_NM;
    }

    public void setVLM_HKN_NM(String VLM_HKN_NM) {
        this.VLM_HKN_NM = VLM_HKN_NM;
    }

    public String getVLM_HBL_NM() {
        return VLM_HBL_NM;
    }

    public void setVLM_HBL_NM(String VLM_HBL_NM) {
        this.VLM_HBL_NM = VLM_HBL_NM;
    }

    @NonNull
    @Override
    public String toString() {
        return this.VLM_HKN_NM;
    }
}
