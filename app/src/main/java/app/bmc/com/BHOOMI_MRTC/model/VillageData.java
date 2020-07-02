package app.bmc.com.BHOOMI_MRTC.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import app.bmc.com.BHOOMI_MRTC.interfaces.VillageModelInterface;

public class VillageData implements VillageModelInterface {
    @ColumnInfo(name = "VLM_VLG_ID")
    private int VLM_VLG_ID;
    @ColumnInfo(name = "VLM_VKN_NM")
    private String VLM_VKN_NM;
    @ColumnInfo(name = "VLM_VLG_NM")
    private String VLM_VLG_NM;

    public int getVLM_VLG_ID() {
        return VLM_VLG_ID;
    }

    public void setVLM_VLG_ID(int VLM_VLG_ID) {
        this.VLM_VLG_ID = VLM_VLG_ID;
    }

    public String getVLM_VKN_NM() {
        return VLM_VKN_NM;
    }

    public void setVLM_VKN_NM(String VLM_VKN_NM) {
        this.VLM_VKN_NM = VLM_VKN_NM;
    }

    public String getVLM_VLG_NM() {
        return VLM_VLG_NM;
    }

    public void setVLM_VLG_NM(String VLM_VLG_NM) {
        this.VLM_VLG_NM = VLM_VLG_NM;
    }


    @NonNull
    @Override
    public String toString() {
        return this.VLM_VLG_NM;
    }
}
