package app.bmc.com.BHOOMI_MRTC.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Master Data.
 */
@Entity
public class MST_VLM {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "VLM_ID")
    private int VLM_ID;
    @ColumnInfo(name = "VLM_DST_ID")
    private int VLM_DST_ID;
    @ColumnInfo(name = "VLM_TLK_ID")
    private int VLM_TLK_ID;
    @ColumnInfo(name = "VLM_HBL_ID")
    private int VLM_HBL_ID;
    @ColumnInfo(name = "VLM_CIR_ID")
    private int VLM_CIR_ID;
    @ColumnInfo(name = "VLM_VLG_ID")
    private int VLM_VLG_ID;
    @ColumnInfo(name = "VLM_DKN_NM")
    private String VLM_DKN_NM;
    @ColumnInfo(name = "VLM_DST_NM")
    private String VLM_DST_NM;
    @ColumnInfo(name = "VLM_TLK_NM")
    private String VLM_TLK_NM;
    @ColumnInfo(name = "VLM_TKN_NM")
    private String VLM_TKN_NM;
    @ColumnInfo(name = "VLM_HKN_NM")
    private String VLM_HKN_NM;
    @ColumnInfo(name = "VLM_HBL_NM")
    private String VLM_HBL_NM;
    @ColumnInfo(name = "VLM_CIR_NM")
    private String VLM_CIR_NM;
    @ColumnInfo(name = "VLM_CKN_NM")
    private String VLM_CKN_NM;
    @ColumnInfo(name = "VLM_VKN_NM")
    private String VLM_VKN_NM;
    @ColumnInfo(name = "VLM_VLG_NM")
    private String VLM_VLG_NM;
    @ColumnInfo(name = "VLM_TUNIT")
    private String VLM_TUNIT;
    @ColumnInfo(name = "VLM_FURBAN")
    private String VLM_FURBAN;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVLM_ID() {
        return VLM_ID;
    }

    public void setVLM_ID(int VLM_ID) {
        this.VLM_ID = VLM_ID;
    }

    public int getVLM_DST_ID() {
        return VLM_DST_ID;
    }

    public void setVLM_DST_ID(int VLM_DST_ID) {
        this.VLM_DST_ID = VLM_DST_ID;
    }

    public int getVLM_TLK_ID() {
        return VLM_TLK_ID;
    }

    public void setVLM_TLK_ID(int VLM_TLK_ID) {
        this.VLM_TLK_ID = VLM_TLK_ID;
    }

    public int getVLM_HBL_ID() {
        return VLM_HBL_ID;
    }

    public void setVLM_HBL_ID(int VLM_HBL_ID) {
        this.VLM_HBL_ID = VLM_HBL_ID;
    }

    public int getVLM_CIR_ID() {
        return VLM_CIR_ID;
    }

    public void setVLM_CIR_ID(int VLM_CIR_ID) {
        this.VLM_CIR_ID = VLM_CIR_ID;
    }

    public int getVLM_VLG_ID() {
        return VLM_VLG_ID;
    }

    public void setVLM_VLG_ID(int VLM_VLG_ID) {
        this.VLM_VLG_ID = VLM_VLG_ID;
    }

    public String getVLM_DKN_NM() {
        return VLM_DKN_NM;
    }

    public void setVLM_DKN_NM(String VLM_DKN_NM) {
        this.VLM_DKN_NM = VLM_DKN_NM;
    }

    public String getVLM_DST_NM() {
        return VLM_DST_NM;
    }

    public void setVLM_DST_NM(String VLM_DST_NM) {
        this.VLM_DST_NM = VLM_DST_NM;
    }

    public String getVLM_TLK_NM() {
        return VLM_TLK_NM;
    }

    public void setVLM_TLK_NM(String VLM_TLK_NM) {
        this.VLM_TLK_NM = VLM_TLK_NM;
    }

    public String getVLM_TKN_NM() {
        return VLM_TKN_NM;
    }

    public void setVLM_TKN_NM(String VLM_TKN_NM) {
        this.VLM_TKN_NM = VLM_TKN_NM;
    }

    public String getVLM_HKN_NM() {
        return VLM_HKN_NM;
    }

    public void setVLM_HKN_NM(String VLM_HKN_NM) {
        this.VLM_HKN_NM = VLM_HKN_NM;
    }

    public String getVLM_HBL_NM() {
        return VLM_HBL_NM;
    }

    public void setVLM_HBL_NM(String VLM_HBL_NM) {
        this.VLM_HBL_NM = VLM_HBL_NM;
    }

    public String getVLM_CIR_NM() {
        return VLM_CIR_NM;
    }

    public void setVLM_CIR_NM(String VLM_CIR_NM) {
        this.VLM_CIR_NM = VLM_CIR_NM;
    }

    public String getVLM_CKN_NM() {
        return VLM_CKN_NM;
    }

    public void setVLM_CKN_NM(String VLM_CKN_NM) {
        this.VLM_CKN_NM = VLM_CKN_NM;
    }

    public String getVLM_VKN_NM() {
        return VLM_VKN_NM;
    }

    public void setVLM_VKN_NM(String VLM_VKN_NM) {
        this.VLM_VKN_NM = VLM_VKN_NM;
    }

    public String getVLM_VLG_NM() {
        return VLM_VLG_NM;
    }

    public void setVLM_VLG_NM(String VLM_VLG_NM) {
        this.VLM_VLG_NM = VLM_VLG_NM;
    }

    public String getVLM_TUNIT() {
        return VLM_TUNIT;
    }

    public void setVLM_TUNIT(String VLM_TUNIT) {
        this.VLM_TUNIT = VLM_TUNIT;
    }

    public String getVLM_FURBAN() {
        return VLM_FURBAN;
    }

    public void setVLM_FURBAN(String VLM_FURBAN) {
        this.VLM_FURBAN = VLM_FURBAN;
    }


    @Override
    public String toString() {
        return this.VLM_DKN_NM;
    }
}
