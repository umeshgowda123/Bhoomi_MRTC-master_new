package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;

import app.bmc.com.BHOOMI_MRTC.interfaces.MSR_RES_Interface;

public class MSR_RES_Data implements MSR_RES_Interface {
    @ColumnInfo(name = "MSR_RES")
    private String MSR_RES;

    @Override
    public String getMSR_RES() {
        return MSR_RES;
    }

    public void setMSR_RES(String MSR_RES) {
        this.MSR_RES = MSR_RES;
    }
}
