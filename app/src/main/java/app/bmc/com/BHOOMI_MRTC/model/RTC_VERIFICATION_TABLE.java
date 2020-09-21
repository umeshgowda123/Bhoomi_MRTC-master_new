package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RTC_VERIFICATION_TABLE {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "REFF_NO")
    private String REF_NO;
    @ColumnInfo(name = "REFF_RES")
    private String REFF_RES;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getREF_NO() {
        return REF_NO;
    }

    public void setREF_NO(String REF_NO) {
        this.REF_NO = REF_NO;
    }

    public String getREFF_RES() {
        return REFF_RES;
    }

    public void setREFF_RES(String REFF_RES) {
        this.REFF_RES = REFF_RES;
    }
}
