package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;


import app.bmc.com.BHOOMI_MRTC.interfaces.RTCV_RES_Interface;

public class RTCV_Data implements RTCV_RES_Interface {
    @ColumnInfo(name = "REFF_RES")
    private String REFF_RES;

    @Override
    public String  getREFF_RES() {
        return REFF_RES;
    }

    public void setREFF_RES(String REFF_RES) {
        this.REFF_RES = REFF_RES;
    }
}
