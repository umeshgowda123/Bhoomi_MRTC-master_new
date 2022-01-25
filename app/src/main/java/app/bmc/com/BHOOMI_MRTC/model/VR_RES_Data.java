package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;

import app.bmc.com.BHOOMI_MRTC.interfaces.VR_RES_Interface;

public class VR_RES_Data implements VR_RES_Interface {

    @ColumnInfo(name = "VR_LAND_OWNER_RES")
    private String VR_LAND_OWNER_RES;
    @ColumnInfo(name = "VR_CULT_RES")
    private String VR_CULT_RES;


    @Override
    public String getVR_LAND_OWNER_RES() {
        return VR_LAND_OWNER_RES;
    }

    public void setVR_LAND_OWNER_RES(String VR_LAND_OWNER_RES) {
        this.VR_LAND_OWNER_RES = VR_LAND_OWNER_RES;
    }

    public void setVR_CULT_RES(String VR_CULT_RES) {
        this.VR_CULT_RES = VR_CULT_RES;
    }

    @Override
    public String getVR_CULT_RES() {
        return VR_CULT_RES;
    }


}
