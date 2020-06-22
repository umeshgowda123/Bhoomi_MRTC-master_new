package app.bmc.com.bhoomi.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import app.bmc.com.bhoomi.model.BankMasterData;
import app.bmc.com.bhoomi.model.BranchNameWithCodeData;
import app.bmc.com.bhoomi.model.CalamityData;
import app.bmc.com.bhoomi.model.CalamityDetails;
import app.bmc.com.bhoomi.model.DistrictData;
import app.bmc.com.bhoomi.model.DistrictDataKannada;
import app.bmc.com.bhoomi.model.HobliData;
import app.bmc.com.bhoomi.model.HobliDataKannada;
import app.bmc.com.bhoomi.model.MST_VLM;
import app.bmc.com.bhoomi.model.PacsBankMasterData;
import app.bmc.com.bhoomi.model.SeasonData;
import app.bmc.com.bhoomi.model.SeasonDetails;
import app.bmc.com.bhoomi.model.TalukData;
import app.bmc.com.bhoomi.model.TalukDataKannda;
import app.bmc.com.bhoomi.model.VillageData;
import app.bmc.com.bhoomi.model.VillageDataKannada;
import app.bmc.com.bhoomi.model.YearData;
import app.bmc.com.bhoomi.model.YearDetails;

@Dao
public interface DataBaseAccess {
    @Insert
    Long[] insertMasterData(List<MST_VLM> mst_vlmList);

    @Insert
    Long[] insertMasterCalamityData(List<CalamityDetails> calamity_list);


    @Insert
    Long[] insertMasterSeasonData(List<SeasonDetails> season_list);


    @Insert
    Long[] insertBankMasterData(List<BankMasterData> bankMasterList);

    @Insert
//    Long[] insertPacsBankMasterData(List<PacsBankMasterData> bankMasterList);
    Long[] insertPacsBankMasterData(List<PacsBankMasterData> bankMasterList1);//---SUSMITA


    @Insert
    Long[] insertYearSeasonData(List<YearDetails> year_list);

    @Query("SELECT COUNT(id) FROM MST_VLM ")
    int getNumberOfRows();


    @Query("SELECT COUNT(id) FROM CalamityDetails")
    int getNumberOfRowsFromCalamityMaster();

    @Query("SELECT COUNT(id) FROM BankMasterData")
    int getNumberOfROwsFromBankMaster();

//-----------------Susmita-------------------------
    @Query("SELECT COUNT(id) FROM PacsBankMasterData")
    int getNumberOfROwsFromPacsBankMaster();
//-------------------------------------------------

    @Query("SELECT VLM_DST_ID,VLM_DKN_NM,VLM_DST_NM FROM MST_VLM  group by VLM_DST_NM")
    List<DistrictData> getDistinctDistricts();


    @Query("SELECT DISTINCT BNK_NME_EN FROM BankMasterData  WHERE BNK_BHM_DC_CDE = :dis_id ORDER BY BNK_NME_EN ASC")
    List<String> getBankNames(int dis_id);


    @Query("SELECT DISTINCT BNK_NME_EN FROM PacsBankMasterData  WHERE BNK_BHM_DC_CDE = :dis_id ORDER BY BNK_NME_EN ASC")
    List<String> getPacsBankNames(int dis_id);

    @Query("SELECT DISTINCT Bnk_Brnch_Nme_Eng,BNK_NME_EN,BNK_BRNCH_CDE FROM PacsBankMasterData WHERE BNK_BHM_DC_CDE =:district_id and  BNK_NME_EN = :bank_name ORDER BY Bnk_Brnch_Nme_Eng ASC")
    List<BranchNameWithCodeData>  getPacsBranchNameList(int district_id, String bank_name);

    @Query("SELECT DISTINCT BNK_BRNCH_NME FROM BankMasterData WHERE BNK_BHM_DC_CDE = :district_id and BNK_NME_EN = :bank_name ORDER BY BNK_BRNCH_NME ASC")
    List<String>  getBranchNameList(int district_id, String bank_name);

    @Query("SELECT Year,Code FROM YearDetails")
    List<YearData> getDistinctYears();

    @Query("SELECT MSTSEASON_ID,MSTSEASON_VAL,MSTSEASON_DESC FROM SeasonDetails")
    List<SeasonData> getDistinctSeasons();

    @Query("SELECT MSTCTYPE_ID,MSTCTYPE_VAL,MSTCTYPE_DESC FROM CalamityDetails")
    List<CalamityData> getDistinctCalamity();

    @Query("SELECT VLM_DST_ID,VLM_DKN_NM,VLM_DST_NM FROM MST_VLM  group by VLM_DST_ID")
    List<DistrictDataKannada> getDistinctDistrictsKannada();

    @Query("SELECT VLM_TLK_NM,VLM_TLK_ID,VLM_TKN_NM FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID group by VLM_TLK_ID")
    public List<TalukData> getTalukByDistrictId(String VLM_DST_ID);

    @Query("SELECT VLM_TLK_NM,VLM_TLK_ID,VLM_TKN_NM FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID group by VLM_TLK_ID")
    public List<TalukDataKannda> getTalukByDistrictIdKannada(String VLM_DST_ID);


    @Query("SELECT VLM_HBL_NM,VLM_HBL_ID,VLM_HKN_NM FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID AND VLM_TLK_ID = :VLM_TLK_ID  group by VLM_HBL_ID")
    public List<HobliData> getHobliByTalukId_and_DistrictId(String VLM_TLK_ID, String VLM_DST_ID);


    @Query("SELECT VLM_HBL_NM,VLM_HBL_ID,VLM_HKN_NM FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID AND VLM_TLK_ID = :VLM_TLK_ID  group by VLM_HBL_ID")
    public List<HobliDataKannada> getHobliByTalukId_and_DistrictIdKannada(String VLM_TLK_ID, String VLM_DST_ID);


    @Query("SELECT VLM_VLG_NM,VLM_VLG_ID,VLM_VKN_NM FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID AND VLM_TLK_ID = :VLM_TLK_ID and VLM_HBL_ID = :VLM_HBL_ID  group by VLM_VLG_ID ORDER BY VLM_VLG_NM ASC")
    public List<VillageData> getVillageByHobliId_and_TalukId_and_DistrictId(String VLM_HBL_ID, String VLM_TLK_ID, String VLM_DST_ID);


    @Query("SELECT VLM_VLG_NM,VLM_VLG_ID,VLM_VKN_NM FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID AND VLM_TLK_ID = :VLM_TLK_ID and VLM_HBL_ID = :VLM_HBL_ID  group by VLM_VLG_ID")
    public List<VillageDataKannada> getVillageByHobliId_and_TalukId_and_DistrictIdKannada(String VLM_HBL_ID, String VLM_TLK_ID, String VLM_DST_ID);

}
