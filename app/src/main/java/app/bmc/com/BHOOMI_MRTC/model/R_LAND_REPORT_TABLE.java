package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class R_LAND_REPORT_TABLE {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "RLR_DST_ID")
    private String RLR_DST_ID;
    @ColumnInfo(name = "RLR_TLK_ID")
    private String RLR_TLK_ID;
    @ColumnInfo(name = "RLR_HBL_ID")
    private String RLR_HBL_ID;
    @ColumnInfo(name = "RLR_VLG_ID")
    private String RLR_VLG_ID;
    @ColumnInfo(name = "RLR_SNO")
    private String RLR_SNO;
    @ColumnInfo(name = "RLR_SUROC")
    private String RLR_SUROC;
    @ColumnInfo(name = "RLR_HISSA")
    private String RLR_HISSA;
    @ColumnInfo(name = "RLR_RES")
    private String RLR_RES;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRLR_DST_ID() {
        return RLR_DST_ID;
    }

    public void setRLR_DST_ID(String RLR_DST_ID) {
        this.RLR_DST_ID = RLR_DST_ID;
    }

    public String getRLR_TLK_ID() {
        return RLR_TLK_ID;
    }

    public void setRLR_TLK_ID(String RLR_TLK_ID) {
        this.RLR_TLK_ID = RLR_TLK_ID;
    }

    public String getRLR_HBL_ID() {
        return RLR_HBL_ID;
    }

    public void setRLR_HBL_ID(String RLR_HBL_ID) {
        this.RLR_HBL_ID = RLR_HBL_ID;
    }

    public String getRLR_VLG_ID() {
        return RLR_VLG_ID;
    }

    public void setRLR_VLG_ID(String RLR_VLG_ID) {
        this.RLR_VLG_ID = RLR_VLG_ID;
    }

    public String getRLR_SNO() {
        return RLR_SNO;
    }

    public void setRLR_SNO(String RLR_SNO) {
        this.RLR_SNO = RLR_SNO;
    }

    public String getRLR_SUROC() {
        return RLR_SUROC;
    }

    public void setRLR_SUROC(String RLR_SUROC) {
        this.RLR_SUROC = RLR_SUROC;
    }

    public String getRLR_HISSA() {
        return RLR_HISSA;
    }

    public void setRLR_HISSA(String RLR_HISSA) {
        this.RLR_HISSA = RLR_HISSA;
    }

    public String getRLR_RES() {
        return RLR_RES;
    }

    public void setRLR_RES(String RLR_RES) {
        this.RLR_RES = RLR_RES;
    }
}
