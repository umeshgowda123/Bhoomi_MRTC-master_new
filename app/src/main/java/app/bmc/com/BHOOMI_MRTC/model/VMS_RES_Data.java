package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;

import app.bmc.com.BHOOMI_MRTC.interfaces.VMS_RES_Interface;

public class VMS_RES_Data implements VMS_RES_Interface {
    @ColumnInfo(name = "VMS_RES")
    private String VMS_RES;

    @Override
    public String getVMS_RES() {
        return VMS_RES;
    }

    public void setVMS_RES(String VMS_RES) {
        this.VMS_RES = VMS_RES;
    }
}
