package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;

import app.bmc.com.BHOOMI_MRTC.interfaces.RLR_RES_Interface;

public class RLR_RES_Data implements RLR_RES_Interface {

    @ColumnInfo(name = "RLR_RES")
    private String RLR_RES;

    @Override
    public String getRLR_RES() {
        return RLR_RES;
    }

    public void setRLR_RES(String RLR_RES) {
        this.RLR_RES = RLR_RES;
    }
}
