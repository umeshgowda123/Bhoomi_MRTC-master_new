package app.bmc.com.BHOOMI_MRTC.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.model.BankMasterData;
import app.bmc.com.BHOOMI_MRTC.model.BranchNameWithCodeData;
import app.bmc.com.BHOOMI_MRTC.model.CalamityData;
import app.bmc.com.BHOOMI_MRTC.model.CalamityDetails;
import app.bmc.com.BHOOMI_MRTC.model.DistrictData;
import app.bmc.com.BHOOMI_MRTC.model.DistrictDataKannada;
import app.bmc.com.BHOOMI_MRTC.model.HobliData;
import app.bmc.com.BHOOMI_MRTC.model.HobliDataKannada;
import app.bmc.com.BHOOMI_MRTC.model.MST_VLM;
import app.bmc.com.BHOOMI_MRTC.model.PacsBankMasterData;
import app.bmc.com.BHOOMI_MRTC.model.SeasonData;
import app.bmc.com.BHOOMI_MRTC.model.SeasonDetails;
import app.bmc.com.BHOOMI_MRTC.model.TalukData;
import app.bmc.com.BHOOMI_MRTC.model.TalukDataKannda;
import app.bmc.com.BHOOMI_MRTC.model.VR_RES_Data;
import app.bmc.com.BHOOMI_MRTC.model.VR_INFO;
import app.bmc.com.BHOOMI_MRTC.model.VillageData;
import app.bmc.com.BHOOMI_MRTC.model.VillageDataKannada;
import app.bmc.com.BHOOMI_MRTC.model.YearData;
import app.bmc.com.BHOOMI_MRTC.model.YearDetails;

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

    @Query("SELECT DISTINCT VLM_DST_NM, VLM_DST_ID FROM MST_VLM ORDER BY LOWER(VLM_DST_NM)")
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

    @Query("SELECT VLM_DST_ID,VLM_DKN_NM FROM MST_VLM  group by VLM_DKN_NM")
    List<DistrictDataKannada> getDistinctDistrictsKannada();

    @Query("SELECT VLM_TLK_NM,VLM_TLK_ID FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID group by LOWER(VLM_TLK_NM)")
    List<TalukData> getTalukByDistrictId(String VLM_DST_ID);

    @Query("SELECT VLM_TLK_ID,VLM_TKN_NM FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID group by VLM_TKN_NM")
    List<TalukDataKannda> getTalukByDistrictIdKannada(String VLM_DST_ID);


    @Query("SELECT VLM_HBL_NM,VLM_HBL_ID FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID AND VLM_TLK_ID = :VLM_TLK_ID  group by LOWER(VLM_HBL_NM)")
    List<HobliData> getHobliByTalukId_and_DistrictId(String VLM_TLK_ID, String VLM_DST_ID);


    @Query("SELECT VLM_HBL_ID,VLM_HKN_NM FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID AND VLM_TLK_ID = :VLM_TLK_ID  group by VLM_HKN_NM")
    List<HobliDataKannada> getHobliByTalukId_and_DistrictIdKannada(String VLM_TLK_ID, String VLM_DST_ID);


    @Query("SELECT VLM_VLG_NM,VLM_VLG_ID FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID AND VLM_TLK_ID = :VLM_TLK_ID and VLM_HBL_ID = :VLM_HBL_ID  group by VLM_VLG_ID ORDER BY LOWER(VLM_VLG_NM) ASC")
    List<VillageData> getVillageByHobliId_and_TalukId_and_DistrictId(String VLM_HBL_ID, String VLM_TLK_ID, String VLM_DST_ID);


    @Query("SELECT VLM_VLG_ID,VLM_VKN_NM FROM MST_VLM WHERE VLM_DST_ID = :VLM_DST_ID AND VLM_TLK_ID = :VLM_TLK_ID and VLM_HBL_ID = :VLM_HBL_ID  group by VLM_VKN_NM")
    List<VillageDataKannada> getVillageByHobliId_and_TalukId_and_DistrictIdKannada(String VLM_HBL_ID, String VLM_TLK_ID, String VLM_DST_ID);

    //----------------------------------------DB Fun for View RTC Information ----------------------------------------------

    @Insert
    Long[] insertViewRTCInfoData(List<VR_INFO> vr_info_List);

    @Query("SELECT COUNT(id) FROM VR_INFO ")
    int getNumOfRows();


    @Query("SELECT VR_LAND_OWNER_RES,VR_CULT_RES FROM VR_INFO WHERE VR_DST_ID = :VR_DST_ID AND VR_TLK_ID = :VR_TLK_ID AND VR_HBL_ID = :VR_HBL_ID AND VR_VLG_ID = :VR_VLG_ID " +
            "AND VR_SNO = :VR_SNO AND VR_HISSA_NM = :VR_HISSA_NM")
    List<VR_RES_Data> getVR_RES(String VR_DST_ID, String VR_TLK_ID, String VR_HBL_ID, String VR_VLG_ID, String VR_SNO, String VR_HISSA_NM);



    @Query("DELETE FROM VR_INFO where id =:id")
    int deleteById(int id);
    @Query("DELETE FROM VR_INFO")
    int deleteUserDataResponse();
}
