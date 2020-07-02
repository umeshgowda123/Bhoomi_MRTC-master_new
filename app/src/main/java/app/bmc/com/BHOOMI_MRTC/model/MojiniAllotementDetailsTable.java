package app.bmc.com.BHOOMI_MRTC.model;

public class MojiniAllotementDetailsTable {
    private String DistrictName;
    private String TalukName;
    private String HobliName;
    private String VillageName;
    private String ApplicationNo;
    private String TypeOfApplication;
    private String ApplAllottedTo;
    private String ApplCreatedDate;
    private String AllottedDate;
    private String CompletionDate;
    private String typofAppNm;
    private String SurveyNum;

    public MojiniAllotementDetailsTable(String districtName, String talukName, String hobliName, String villageName, String applicationNo, String typeOfApplication, String applAllottedTo, String applCreatedDate, String allottedDate, String completionDate, String typofAppNm, String surveyNum) {
        DistrictName = districtName;
        TalukName = talukName;
        HobliName = hobliName;
        VillageName = villageName;
        ApplicationNo = applicationNo;
        TypeOfApplication = typeOfApplication;
        ApplAllottedTo = applAllottedTo;
        ApplCreatedDate = applCreatedDate;
        AllottedDate = allottedDate;
        CompletionDate = completionDate;
        this.typofAppNm = typofAppNm;
        SurveyNum = surveyNum;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public String getTalukName() {
        return TalukName;
    }

    public String getHobliName() {
        return HobliName;
    }

    public String getVillageName() {
        return VillageName;
    }

    public String getApplicationNo() {
        return ApplicationNo;
    }

    public String getTypeOfApplication() {
        return TypeOfApplication;
    }

    public String getApplAllottedTo() {
        return ApplAllottedTo;
    }

    public String getApplCreatedDate() {
        return ApplCreatedDate;
    }

    public String getAllottedDate() {
        return AllottedDate;
    }

    public String getCompletionDate() {
        return CompletionDate;
    }

    public String getTypofAppNm() {
        return typofAppNm;
    }

    public String getSurveyNum() {
        return SurveyNum;
    }
}
