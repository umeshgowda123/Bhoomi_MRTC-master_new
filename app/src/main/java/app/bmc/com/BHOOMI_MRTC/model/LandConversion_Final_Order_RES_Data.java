package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;

import app.bmc.com.BHOOMI_MRTC.interfaces.LandConversion_Final_Order_Interface;

public class LandConversion_Final_Order_RES_Data implements LandConversion_Final_Order_Interface {

    @ColumnInfo(name = "REQUEST_ID_RES")
    private String REQUEST_ID_RES;
    @ColumnInfo(name = "SNO_RES")
    private String SNO_RES;

    @Override
    public String getREQUEST_ID_RES() {
        return REQUEST_ID_RES;
    }

    @Override
    public String getSNO_RES() {
        return SNO_RES;
    }

    public void setREQUEST_ID_RES(String REQUEST_ID_RES) {
        this.REQUEST_ID_RES = REQUEST_ID_RES;
    }

    public void setSNO_RES(String SNO_RES) {
        this.SNO_RES = SNO_RES;
    }
}
