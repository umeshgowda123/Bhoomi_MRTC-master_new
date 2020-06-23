package app.bmc.com.BHOOMI_MRTC.model;

import android.arch.persistence.room.ColumnInfo;
import android.support.annotation.NonNull;

import app.bmc.com.BHOOMI_MRTC.interfaces.CalamityInterface;

public class CalamityData implements CalamityInterface {

    @ColumnInfo(name = "MSTCTYPE_ID")
    private int MSTCTYPE_ID;

    @ColumnInfo(name = "MSTCTYPE_VAL")
    private int MSTCTYPE_VAL;

    @ColumnInfo(name = "MSTCTYPE_DESC")
    private String MSTCTYPE_DESC;


    @Override
    public int getMSTCTYPE_ID() {
        return MSTCTYPE_ID;
    }

    public void setMSTCTYPE_ID(int MSTCTYPE_ID) {
        this.MSTCTYPE_ID = MSTCTYPE_ID;
    }

    @Override
    public int getMSTCTYPE_VAL() {
        return MSTCTYPE_VAL;
    }

    public void setMSTCTYPE_VAL(int MSTCTYPE_VAL) {
        this.MSTCTYPE_VAL = MSTCTYPE_VAL;
    }

    @Override
    public String getMSTCTYPE_DESC() {
        return MSTCTYPE_DESC;
    }

    public void setMSTCTYPE_DESC(String MSTCTYPE_DESC) {
        this.MSTCTYPE_DESC = MSTCTYPE_DESC;
    }

    @NonNull
    @Override
    public String toString() {
        return this.MSTCTYPE_DESC;
    }
}
