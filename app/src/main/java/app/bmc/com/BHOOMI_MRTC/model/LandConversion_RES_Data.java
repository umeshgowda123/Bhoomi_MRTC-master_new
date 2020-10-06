package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;

import app.bmc.com.BHOOMI_MRTC.interfaces.LandConversion_Interface;

public class LandConversion_RES_Data implements LandConversion_Interface {

    @ColumnInfo(name = "AFFIDAVIT_RES")
    private String AFFIDAVIT_RES;
    @ColumnInfo(name = "USER_RES")
    private String USER_RES;


    @Override
    public String getAFFIDAVIT_RES() {
        return AFFIDAVIT_RES;
    }

    @Override
    public String getUSER_RES() {
        return USER_RES;
    }

    public void setAFFIDAVIT_RES(String AFFIDAVIT_RES) {
        this.AFFIDAVIT_RES = AFFIDAVIT_RES;
    }

    public void setUSER_RES(String USER_RES) {
        this.USER_RES = USER_RES;
    }
}
