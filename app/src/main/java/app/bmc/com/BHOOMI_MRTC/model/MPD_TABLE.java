package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MPD_TABLE {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "MPD_DST_ID")
    private int MPD_DST_ID;
    @ColumnInfo(name = "MPD_TLK_ID")
    private int MPD_TLK_ID;
    @ColumnInfo(name = "MPD_HBL_ID")
    private int MPD_HBL_ID;
    @ColumnInfo(name = "MPD_VLG_ID")
    private int MPD_VLG_ID;
    @ColumnInfo(name = "MPD_RES")
    private String MPD_RES;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMPD_DST_ID() {
        return MPD_DST_ID;
    }

    public void setMPD_DST_ID(int MPD_DST_ID) {
        this.MPD_DST_ID = MPD_DST_ID;
    }

    public int getMPD_TLK_ID() {
        return MPD_TLK_ID;
    }

    public void setMPD_TLK_ID(int MPD_TLK_ID) {
        this.MPD_TLK_ID = MPD_TLK_ID;
    }

    public int getMPD_HBL_ID() {
        return MPD_HBL_ID;
    }

    public void setMPD_HBL_ID(int MPD_HBL_ID) {
        this.MPD_HBL_ID = MPD_HBL_ID;
    }

    public int getMPD_VLG_ID() {
        return MPD_VLG_ID;
    }

    public void setMPD_VLG_ID(int MPD_VLG_ID) {
        this.MPD_VLG_ID = MPD_VLG_ID;
    }

    public String getMPD_RES() {
        return MPD_RES;
    }

    public void setMPD_RES(String MPD_RES) {
        this.MPD_RES = MPD_RES;
    }

}
