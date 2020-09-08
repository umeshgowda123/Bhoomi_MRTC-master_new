package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VR_INFO {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "VR_DST_ID")
    private int VR_DST_ID;
    @ColumnInfo(name = "VR_TLK_ID")
    private int VR_TLK_ID;
    @ColumnInfo(name = "VR_HBL_ID")
    private int VR_HBL_ID;
    @ColumnInfo(name = "VR_VLG_ID")
    private int VR_VLG_ID;
    @ColumnInfo(name = "VR_LAND_NO")
    private int VR_LAND_NO;
    @ColumnInfo(name = "VR_SNO")
    private String VR_SNO;
    @ColumnInfo(name = "VR_HISSA_NM")
    private String VR_HISSA_NM;
    @ColumnInfo(name = "VR_LAND_OWNER_RES")
    private String VR_LAND_OWNER_RES;
    @ColumnInfo(name = "VR_CULT_RES")
    private String VR_CULT_RES;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVR_DST_ID() {
        return VR_DST_ID;
    }

    public void setVR_DST_ID(int VR_DST_ID) {
        this.VR_DST_ID = VR_DST_ID;
    }

    public int getVR_TLK_ID() {
        return VR_TLK_ID;
    }

    public void setVR_TLK_ID(int VR_TLK_ID) {
        this.VR_TLK_ID = VR_TLK_ID;
    }

    public int getVR_HBL_ID() {
        return VR_HBL_ID;
    }

    public void setVR_HBL_ID(int VR_HBL_ID) {
        this.VR_HBL_ID = VR_HBL_ID;
    }

    public int getVR_VLG_ID() {
        return VR_VLG_ID;
    }

    public void setVR_VLG_ID(int VR_VLG_ID) {
        this.VR_VLG_ID = VR_VLG_ID;
    }

    public int getVR_LAND_NO() {
        return VR_LAND_NO;
    }

    public void setVR_LAND_NO(int VR_LAND_NO) {
        this.VR_LAND_NO = VR_LAND_NO;
    }

    public String getVR_SNO() {
        return VR_SNO;
    }

    public void setVR_SNO(String VR_SNO) {
        this.VR_SNO = VR_SNO;
    }

    public String getVR_HISSA_NM() {
        return VR_HISSA_NM;
    }

    public void setVR_HISSA_NM(String VR_HISSA_NM) {
        this.VR_HISSA_NM = VR_HISSA_NM;
    }

    public String getVR_LAND_OWNER_RES() {
        return VR_LAND_OWNER_RES;
    }

    public void setVR_LAND_OWNER_RES(String VR_LAND_OWNER_RES) {
        this.VR_LAND_OWNER_RES = VR_LAND_OWNER_RES;
    }

    public String getVR_CULT_RES() {
        return VR_CULT_RES;
    }

    public void setVR_CULT_RES(String VR_CULT_RES) {
        this.VR_CULT_RES = VR_CULT_RES;
    }
}
