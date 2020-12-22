package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MS_REPORT_TABLE {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "MSR_DST_ID")
    private int MSR_DST_ID;
    @ColumnInfo(name = "MSR_TLK_ID")
    private int MSR_TLK_ID;
    @ColumnInfo(name = "MSR_HBL_ID")
    private int MSR_HBL_ID;
    @ColumnInfo(name = "MSR_VLG_ID")
    private int MSR_VLG_ID;
    @ColumnInfo(name = "MSR_SNO")
    private String MSR_SNO;
    @ColumnInfo(name = "MSR_RES")
    private String MSR_RES;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMSR_DST_ID() {
        return MSR_DST_ID;
    }

    public void setMSR_DST_ID(int MSR_DST_ID) {
        this.MSR_DST_ID = MSR_DST_ID;
    }

    public int getMSR_TLK_ID() {
        return MSR_TLK_ID;
    }

    public void setMSR_TLK_ID(int MSR_TLK_ID) {
        this.MSR_TLK_ID = MSR_TLK_ID;
    }

    public int getMSR_HBL_ID() {
        return MSR_HBL_ID;
    }

    public void setMSR_HBL_ID(int MSR_HBL_ID) {
        this.MSR_HBL_ID = MSR_HBL_ID;
    }

    public int getMSR_VLG_ID() {
        return MSR_VLG_ID;
    }

    public void setMSR_VLG_ID(int MSR_VLG_ID) {
        this.MSR_VLG_ID = MSR_VLG_ID;
    }

    public String getMSR_SNO() {
        return MSR_SNO;
    }

    public void setMSR_SNO(String MSR_SNO) {
        this.MSR_SNO = MSR_SNO;
    }

    public String getMSR_RES() {
        return MSR_RES;
    }

    public void setMSR_RES(String MSR_RES) {
        this.MSR_RES = MSR_RES;
    }
}
