package app.bmc.com.BHOOMI_MRTC.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CalamityDetails {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "MSTCTYPE_ID")
    private int MSTCTYPE_ID;

    @ColumnInfo(name = "MSTCTYPE_VAL")
    private int MSTCTYPE_VAL;

    @ColumnInfo(name = "MSTCTYPE_DESC")
    private String MSTCTYPE_DESC;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMSTCTYPE_ID() {
        return MSTCTYPE_ID;
    }

    public void setMSTCTYPE_ID(int MSTCTYPE_ID) {
        this.MSTCTYPE_ID = MSTCTYPE_ID;
    }

    public int getMSTCTYPE_VAL() {
        return MSTCTYPE_VAL;
    }

    public void setMSTCTYPE_VAL(int MSTCTYPE_VAL) {
        this.MSTCTYPE_VAL = MSTCTYPE_VAL;
    }

    public String getMSTCTYPE_DESC() {
        return MSTCTYPE_DESC;
    }

    public void setMSTCTYPE_DESC(String MSTCTYPE_DESC) {
        this.MSTCTYPE_DESC = MSTCTYPE_DESC;
    }
}
