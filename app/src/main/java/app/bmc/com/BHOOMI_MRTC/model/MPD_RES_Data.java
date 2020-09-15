package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;

import app.bmc.com.BHOOMI_MRTC.interfaces.MPD_RES_Interface;

public class MPD_RES_Data implements MPD_RES_Interface {

    @ColumnInfo(name = "MPD_RES")
    private String MPD_RES;

    @Override
    public String getMDP_RES() {
        return MPD_RES;
    }

    public void setMPD_RES(String MPD_RES) {
        this.MPD_RES = MPD_RES;
    }
}
