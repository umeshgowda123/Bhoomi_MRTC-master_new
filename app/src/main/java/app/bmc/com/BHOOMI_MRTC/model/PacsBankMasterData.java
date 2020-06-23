package app.bmc.com.BHOOMI_MRTC.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PacsBankMasterData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "BNK_ID")
    private int BNK_ID;

    @ColumnInfo(name="BNK_BHM_DC_CDE")
    private int BNK_BHM_DC_CDE;

    @ColumnInfo(name = "BNK_BHM_DC_NME")
    private String BNK_BHM_DC_NME;

    @ColumnInfo(name = "BNK_BHM_TLK_CDE")
    private int BNK_BHM_TLK_CDE;

    @ColumnInfo(name = "BNK_BHM_TLK_NME")
    private String BNK_BHM_TLK_NME;

    @ColumnInfo(name = "BNK_NME_EN")
    private String BNK_NME_EN;

    @ColumnInfo(name = "BNK_BRNCH_CDE")
    private String BNK_BRNCH_CDE;

    @ColumnInfo(name = "Bnk_Brnch_Nme_Eng")
    private String Bnk_Brnch_Nme_Eng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBNK_ID() {
        return BNK_ID;
    }

    public void setBNK_ID(int BNK_ID) {
        this.BNK_ID = BNK_ID;
    }

    public int getBNK_BHM_DC_CDE() {
        return BNK_BHM_DC_CDE;
    }

    public void setBNK_BHM_DC_CDE(int BNK_BHM_DC_CDE) {
        this.BNK_BHM_DC_CDE = BNK_BHM_DC_CDE;
    }

    public String getBNK_BHM_DC_NME() {
        return BNK_BHM_DC_NME;
    }

    public void setBNK_BHM_DC_NME(String BNK_BHM_DC_NME) {
        this.BNK_BHM_DC_NME = BNK_BHM_DC_NME;
    }

    public int getBNK_BHM_TLK_CDE() {
        return BNK_BHM_TLK_CDE;
    }

    public void setBNK_BHM_TLK_CDE(int BNK_BHM_TLK_CDE) {
        this.BNK_BHM_TLK_CDE = BNK_BHM_TLK_CDE;
    }

    public String getBNK_BHM_TLK_NME() {
        return BNK_BHM_TLK_NME;
    }

    public void setBNK_BHM_TLK_NME(String BNK_BHM_TLK_NME) {
        this.BNK_BHM_TLK_NME = BNK_BHM_TLK_NME;
    }

    public String getBNK_NME_EN() {
        return BNK_NME_EN;
    }

    public void setBNK_NME_EN(String BNK_NME_EN) {
        this.BNK_NME_EN = BNK_NME_EN;
    }

    public String getBNK_BRNCH_CDE() {
        return BNK_BRNCH_CDE;
    }

    public void setBNK_BRNCH_CDE(String BNK_BRNCH_CDE) {
        this.BNK_BRNCH_CDE = BNK_BRNCH_CDE;
    }

    public String getBnk_Brnch_Nme_Eng() {
        return Bnk_Brnch_Nme_Eng;
    }

    public void setBnk_Brnch_Nme_Eng(String bnk_Brnch_Nme_Eng) {
        Bnk_Brnch_Nme_Eng = bnk_Brnch_Nme_Eng;
    }
}
