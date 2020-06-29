package app.bmc.com.BHOOMI_MRTC.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import app.bmc.com.BHOOMI_MRTC.interfaces.BranchNameModelInterface;

public class BranchNameWithCodeData implements BranchNameModelInterface {


    @ColumnInfo(name = "BNK_NME_EN")
    private String BNK_NME_EN;


    @ColumnInfo(name = "BNK_BRNCH_CDE")
    private int BNK_BRNCH_CDE;


    @ColumnInfo(name = "Bnk_Brnch_Nme_Eng")
    private String Bnk_Brnch_Nme_Eng;


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

    public String getBnk_Brnch_Nme_Eng() {
        return Bnk_Brnch_Nme_Eng;
    }

    public void setBnk_Brnch_Nme_Eng(String bnk_Brnch_Nme_Eng) {
        Bnk_Brnch_Nme_Eng = bnk_Brnch_Nme_Eng;
    }

    @NonNull
    @Override
    public String toString() {
        return this.Bnk_Brnch_Nme_Eng;
    }
}
