package app.bmc.com.BHOOMI_MRTC.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BankMasterData {

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
    private int BNK_BRNCH_CDE;

    @ColumnInfo(name = "BNK_BRNCH_NME")
    private String BNK_BRNCH_NME;

    @ColumnInfo(name = "BNK_IFSC_CDE")
    private String BNK_IFSC_CDE;

    @ColumnInfo(name = "BNK_VIFSC_CDE")
    private String BNK_VIFSC_CDE;


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

    public int getBNK_BRNCH_CDE() {
        return BNK_BRNCH_CDE;
    }

    public void setBNK_BRNCH_CDE(int BNK_BRNCH_CDE) {
        this.BNK_BRNCH_CDE = BNK_BRNCH_CDE;
    }

    public String getBNK_BRNCH_NME() {
        return BNK_BRNCH_NME;
    }

    public void setBNK_BRNCH_NME(String BNK_BRNCH_NME) {
        this.BNK_BRNCH_NME = BNK_BRNCH_NME;
    }


    public String getBNK_IFSC_CDE() {
        return BNK_IFSC_CDE;
    }

    public void setBNK_IFSC_CDE(String BNK_IFSC_CDE) {
        this.BNK_IFSC_CDE = BNK_IFSC_CDE;
    }

    public String getBNK_VIFSC_CDE() {
        return BNK_VIFSC_CDE;
    }

    public void setBNK_VIFSC_CDE(String BNK_VIFSC_CDE) {
        this.BNK_VIFSC_CDE = BNK_VIFSC_CDE;
    }
}
