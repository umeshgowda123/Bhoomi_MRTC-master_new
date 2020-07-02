package app.bmc.com.BHOOMI_MRTC.model;
public class GetLandConversionFinalOrders_Table {
    private String REQ_ID;
    private String REQ_WFID;
    private String TypeOfConv;
    private String WFL_STG;
    private String LND_DCODE;
    private String LND_TCODE;
    private String LND_HCODE;
    private String LND_VCODE;
    private String DISTRICT_NAME;
    private String TALUKA_NAME;
    private String HOBLI_NAME;
    private String VILLAGE_NAME;
    private String SURVEYNO;
    private String ReturnPage;

    public GetLandConversionFinalOrders_Table(String req_id, String req_wfid, String typeOfConv, String wfl_stg, String lnd_dcode, String lnd_tcode, String lnd_hcode, String lnd_vcode, String district_name, String taluka_name, String hobli_name, String village_name, String surveyno, String returnPage) {
        REQ_ID = req_id;
        REQ_WFID = req_wfid;
        TypeOfConv = typeOfConv;
        WFL_STG = wfl_stg;
        LND_DCODE = lnd_dcode;
        LND_TCODE = lnd_tcode;
        LND_HCODE = lnd_hcode;
        LND_VCODE = lnd_vcode;
        DISTRICT_NAME = district_name;
        TALUKA_NAME = taluka_name;
        HOBLI_NAME = hobli_name;
        VILLAGE_NAME = village_name;
        SURVEYNO = surveyno;
        ReturnPage = returnPage;
    }

    public String getREQ_ID() {
        return REQ_ID;
    }

    public String getREQ_WFID() {
        return REQ_WFID;
    }

    public String getTypeOfConv() {
        return TypeOfConv;
    }

    public String getWFL_STG() {
        return WFL_STG;
    }

    public String getLND_DCODE() {
        return LND_DCODE;
    }

    public String getLND_TCODE() {
        return LND_TCODE;
    }

    public String getLND_HCODE() {
        return LND_HCODE;
    }

    public String getLND_VCODE() {
        return LND_VCODE;
    }

    public String getDISTRICT_NAME() {
        return DISTRICT_NAME;
    }

    public String getTALUKA_NAME() {
        return TALUKA_NAME;
    }

    public String getHOBLI_NAME() {
        return HOBLI_NAME;
    }

    public String getVILLAGE_NAME() {
        return VILLAGE_NAME;
    }

    public String getSURVEYNO() {
        return SURVEYNO;
    }

    public String getReturnPage() {
        return ReturnPage;
    }
}
