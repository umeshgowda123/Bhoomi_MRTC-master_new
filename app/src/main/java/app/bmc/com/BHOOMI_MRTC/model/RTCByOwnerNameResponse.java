package app.bmc.com.BHOOMI_MRTC.model;

public class RTCByOwnerNameResponse {
    public String land_code;
    public String main_owner_no;
    public String owner_no;
    public String owner;
    public String survey_no;
    public String surnoc;
    public String hissa_no;
    public String distId;
    public String talkId;
    public String hblId;
    public String vilID;

    public RTCByOwnerNameResponse(){

    }
    public RTCByOwnerNameResponse(String land_code, String main_owner_no, String owner_no, String owner, String survey_no, String surnoc, String hissa_no, String distId, String talkId, String hblId) {
        this.land_code = land_code;
        this.main_owner_no = main_owner_no;
        this.owner_no = owner_no;
        this.owner = owner;
        this.survey_no = survey_no;
        this.surnoc = surnoc;
        this.hissa_no = hissa_no;
        this.distId = distId;
        this.talkId = talkId;
        this.hblId = hblId;
    }


    public String getVilID() {
        return vilID;
    }

    public void setVilID(String vilID) {
        this.vilID = vilID;
    }

    public String getLand_code() {
        return land_code;
    }

    public void setLand_code(String land_code) {
        this.land_code = land_code;
    }

    public String getMain_owner_no() {
        return main_owner_no;
    }

    public void setMain_owner_no(String main_owner_no) {
        this.main_owner_no = main_owner_no;
    }

    public String getOwner_no() {
        return owner_no;
    }

    public void setOwner_no(String owner_no) {
        this.owner_no = owner_no;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSurvey_no() {
        return survey_no;
    }

    public void setSurvey_no(String survey_no) {
        this.survey_no = survey_no;
    }

    public String getSurnoc() {
        return surnoc;
    }

    public void setSurnoc(String surnoc) {
        this.surnoc = surnoc;
    }

    public String getHissa_no() {
        return hissa_no;
    }

    public void setHissa_no(String hissa_no) {
        this.hissa_no = hissa_no;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getTalkId() {
        return talkId;
    }

    public void setTalkId(String talkId) {
        this.talkId = talkId;
    }

    public String getHblId() {
        return hblId;
    }

    public void setHblId(String hblId) {
        this.hblId = hblId;
    }
}