package app.bmc.com.bhoomi.model;

import android.arch.persistence.room.ColumnInfo;

import app.bmc.com.bhoomi.interfaces.TalukModelInterface;

public class TalukData implements TalukModelInterface {
    @ColumnInfo(name = "VLM_TLK_ID")
    private int VLM_TLK_ID;
    @ColumnInfo(name = "VLM_TLK_NM")
    private String VLM_TLK_NM;
    @ColumnInfo(name = "VLM_TKN_NM")
    private String VLM_TKN_NM;

    public int getVLM_TLK_ID() {
        return VLM_TLK_ID;
    }

    public void setVLM_TLK_ID(int VLM_TLK_ID) {
        this.VLM_TLK_ID = VLM_TLK_ID;
    }

    public String getVLM_TLK_NM() {
        return VLM_TLK_NM;
    }

    public void setVLM_TLK_NM(String VLM_TLK_NM) {
        this.VLM_TLK_NM = VLM_TLK_NM;
    }

    public String getVLM_TKN_NM() {
        return VLM_TKN_NM;
    }

    public void setVLM_TKN_NM(String VLM_TKN_NM) {
        this.VLM_TKN_NM = VLM_TKN_NM;
    }


    @Override
    public String toString() {
        return this.VLM_TLK_NM;
    }
}
