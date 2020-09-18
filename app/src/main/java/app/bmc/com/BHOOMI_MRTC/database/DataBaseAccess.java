package app.bmc.com.BHOOMI_MRTC.database;

import androidx.room.Dao;
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
import app.bmc.com.BHOOMI_MRTC.model.MPD_RES_Data;
import app.bmc.com.BHOOMI_MRTC.model.MPD_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.MSR_RES_Data;
import app.bmc.com.BHOOMI_MRTC.model.MST_VLM;
import app.bmc.com.BHOOMI_MRTC.model.MS_REPORT_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.PacsBankMasterData;
import app.bmc.com.BHOOMI_MRTC.model.RLR_RES_Data;
import app.bmc.com.BHOOMI_MRTC.model.R_LAND_REPORT_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.SeasonData;
import app.bmc.com.BHOOMI_MRTC.model.SeasonDetails;
import app.bmc.com.BHOOMI_MRTC.model.TalukData;
import app.bmc.com.BHOOMI_MRTC.model.TalukDataKannda;
import app.bmc.com.BHOOMI_MRTC.model.VMS_RES_Data;
import app.bmc.com.BHOOMI_MRTC.model.VR_RES_Data;
import app.bmc.com.BHOOMI_MRTC.model.VR_INFO;
import app.bmc.com.BHOOMI_MRTC.model.V_MUTATION_STATUS_TABLE;
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
    int deleteResponseRow();

    //----------------------------------------DB Fun for Mutation Pendency Details  ----------------------------------------------
    @Insert
    Long[] insertMPDData(List<MPD_TABLE> MPD_List);

    @Query("SELECT COUNT(id) FROM MPD_TABLE ")
    int getNumOfRowsMPDTbl();

    @Query("SELECT MPD_RES FROM MPD_TABLE WHERE MPD_DST_ID = :MPD_DST_ID AND MPD_TLK_ID = :MPD_TLK_ID AND MPD_HBL_ID = :MPD_HBL_ID AND MPD_VLG_ID = :MPD_VLG_ID")
    List<MPD_RES_Data> getMPD_RES(int MPD_DST_ID, int MPD_TLK_ID, int MPD_HBL_ID, int MPD_VLG_ID);

    @Query("DELETE FROM MPD_TABLE where id =:id")
    int deleteByIdMPD(int id);

    @Query("DELETE FROM MPD_TABLE")
    int deleteResponse();

    //----------------------------------------DB Fun for Mutation Pendency Details  ----------------------------------------------

    @Insert
    Long[] insertMSRTable_Data(List<MS_REPORT_TABLE> MSR_Table);

    @Query("SELECT COUNT(id) FROM MS_REPORT_TABLE ")
    int getNumOfRowsMSR();

    @Query("SELECT MSR_RES FROM MS_REPORT_TABLE WHERE MSR_DST_ID = :MSR_DST_ID AND MSR_TLK_ID = :MSR_TLK_ID AND MSR_HBL_ID = :MSR_HBL_ID AND MSR_VLG_ID = :MSR_VLG_ID AND MSR_SNO = :MSR_SNO")
    List<MSR_RES_Data> getMSR_RES(int MSR_DST_ID, int MSR_TLK_ID, int MSR_HBL_ID, int MSR_VLG_ID, String MSR_SNO);

    @Query("DELETE FROM MS_REPORT_TABLE where id =:id")
    int deleteByIdMSR(int id);

    @Query("DELETE FROM MS_REPORT_TABLE")
    int deleteAllResponse();

    //----------------------------------------DB Fun for RestrictionOnLandReport ----------------------------------------------

    @Insert
    Long[] insertRestrictionOnLandReportData(List<R_LAND_REPORT_TABLE> RLR_Table);

    @Query("SELECT COUNT(id) FROM R_LAND_REPORT_TABLE ")
    int getNumOfRowsRLR();

    @Query("SELECT RLR_RES FROM R_LAND_REPORT_TABLE WHERE RLR_DST_ID = :RLR_DST_ID AND RLR_TLK_ID = :RLR_TLK_ID AND RLR_HBL_ID = :RLR_HBL_ID" +
            " AND RLR_VLG_ID = :RLR_VLG_ID AND RLR_SNO = :RLR_SNO AND RLR_SUROC = :RLR_SUROC AND RLR_HISSA = :RLR_HISSA")
    List<RLR_RES_Data> getRLR_RES(String RLR_DST_ID, String RLR_TLK_ID, String RLR_HBL_ID, String RLR_VLG_ID,
                                  String RLR_SNO, String RLR_SUROC, String RLR_HISSA);

    @Query("DELETE FROM R_LAND_REPORT_TABLE where id =:id")
    int deleteByIdRLR(int id);

    @Query("DELETE FROM R_LAND_REPORT_TABLE")
    int deleteAllRLRResponse();

    //----------------------------------------DB Fun for RestrictionOnLandReport ----------------------------------------------

    @Insert
    Long[] insertViewMutationStatusTableData(List<V_MUTATION_STATUS_TABLE> VMS_Table);

    @Query("SELECT COUNT(id) FROM V_MUTATION_STATUS_TABLE ")
    int getNumOfRowsVMS();

    @Query("SELECT VMS_RES FROM V_MUTATION_STATUS_TABLE WHERE VMS_DST_ID = :VMS_DST_ID AND VMS_TLK_ID = :VMS_TLK_ID " +
            "AND VMS_HBL_ID = :VMS_HBL_ID AND VMS_VLG_ID = :VMS_VLG_ID AND VMS_LAND_NO = :VMS_LAND_NO")
    List<VMS_RES_Data> getVMS_RES(int VMS_DST_ID, int VMS_TLK_ID, int VMS_HBL_ID, int VMS_VLG_ID, int VMS_LAND_NO);

    @Query("DELETE FROM V_MUTATION_STATUS_TABLE where id =:id")
    int deleteByIdVMS(int id);

    @Query("DELETE FROM V_MUTATION_STATUS_TABLE")
    int deleteAllVMSResponse();
}
