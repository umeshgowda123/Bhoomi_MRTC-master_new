package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class V_MUTATION_STATUS_TABLE {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "VMS_DST_ID")
    private int VMS_DST_ID;
    @ColumnInfo(name = "VMS_TLK_ID")
    private int VMS_TLK_ID;
    @ColumnInfo(name = "VMS_HBL_ID")
    private int VMS_HBL_ID;
    @ColumnInfo(name = "VMS_VLG_ID")
    private int VMS_VLG_ID;
    @ColumnInfo(name = "VMS_LAND_NO")
    private int VMS_LAND_NO;
    @ColumnInfo(name = "VMS_RES")
    private String VMS_RES;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVMS_DST_ID() {
        return VMS_DST_ID;
    }

    public void setVMS_DST_ID(int VMS_DST_ID) {
        this.VMS_DST_ID = VMS_DST_ID;
    }

    public int getVMS_TLK_ID() {
        return VMS_TLK_ID;
    }

    public void setVMS_TLK_ID(int VMS_TLK_ID) {
        this.VMS_TLK_ID = VMS_TLK_ID;
    }

    public int getVMS_HBL_ID() {
        return VMS_HBL_ID;
    }

    public void setVMS_HBL_ID(int VMS_HBL_ID) {
        this.VMS_HBL_ID = VMS_HBL_ID;
    }

    public int getVMS_VLG_ID() {
        return VMS_VLG_ID;
    }

    public void setVMS_VLG_ID(int VMS_VLG_ID) {
        this.VMS_VLG_ID = VMS_VLG_ID;
    }

    public int getVMS_LAND_NO() {
        return VMS_LAND_NO;
    }

    public void setVMS_LAND_NO(int VMS_LAND_NO) {
        this.VMS_LAND_NO = VMS_LAND_NO;
    }

    public String getVMS_RES() {
        return VMS_RES;
    }

    public void setVMS_RES(String VMS_RES) {
        this.VMS_RES = VMS_RES;
    }
}
