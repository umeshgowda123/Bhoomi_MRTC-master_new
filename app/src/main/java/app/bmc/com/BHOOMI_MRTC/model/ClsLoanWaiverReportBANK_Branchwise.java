package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "cls_branchwise")
public class ClsLoanWaiverReportBANK_Branchwise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="dist_code")
    @SerializedName("DISTRICT_CODE")
    private int DISTRICT_CODE;


    @ColumnInfo(name="bank_name")
    @SerializedName("BANK_NAME")
    private String BANK_NAME;

    @ColumnInfo(name="branch_code")
    @SerializedName("BRANCH_CODE")
    private String BRANCH_CODE;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDISTRICT_CODE() {
        return DISTRICT_CODE;
    }

    public void setDISTRICT_CODE(int DISTRICT_CODE) {
        this.DISTRICT_CODE = DISTRICT_CODE;
    }

    public String getBANK_NAME() {
        return BANK_NAME;
    }

    public void setBANK_NAME(String BANK_NAME) {
        this.BANK_NAME = BANK_NAME;
    }

    public String getBRANCH_CODE() {
        return BRANCH_CODE;
    }

    public void setBRANCH_CODE(String BRANCH_CODE) {
        this.BRANCH_CODE = BRANCH_CODE;
    }
}
