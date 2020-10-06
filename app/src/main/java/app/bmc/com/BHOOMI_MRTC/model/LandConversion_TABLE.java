package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LandConversion_TABLE {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "AFFIDAVIT_ID")
    private String AFFIDAVIT_ID;
    @ColumnInfo(name = "AFFIDAVIT_RES")
    private String AFFIDAVIT_RES;
    @ColumnInfo(name = "USER_ID")
    private String USER_ID;
    @ColumnInfo(name = "USER_RES")
    private String USER_RES;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAFFIDAVIT_ID() {
        return AFFIDAVIT_ID;
    }

    public void setAFFIDAVIT_ID(String AFFIDAVIT_ID) {
        this.AFFIDAVIT_ID = AFFIDAVIT_ID;
    }

    public String getAFFIDAVIT_RES() {
        return AFFIDAVIT_RES;
    }

    public void setAFFIDAVIT_RES(String AFFIDAVIT_RES) {
        this.AFFIDAVIT_RES = AFFIDAVIT_RES;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_RES() {
        return USER_RES;
    }

    public void setUSER_RES(String USER_RES) {
        this.USER_RES = USER_RES;
    }
}
