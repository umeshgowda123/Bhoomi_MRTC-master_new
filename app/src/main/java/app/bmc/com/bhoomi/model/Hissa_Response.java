package app.bmc.com.bhoomi.model;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Hissa Data response.
 */
public class Hissa_Response {
    private String Hissa_no;
    private String Surnoc;
    private int land_code;

    public String getHissa_no() {
        return Hissa_no;
    }

    public void setHissa_no(String hissa_no) {
        Hissa_no = hissa_no;
    }

    public String getSurnoc() {
        return Surnoc;
    }

    public void setSurnoc(String surnoc) {
        Surnoc = surnoc;
    }

    public int getLand_code() {
        return land_code;
    }

    public void setLand_code(int land_code) {
        this.land_code = land_code;
    }

    @Override
    public String toString() {
        return Hissa_no + "";
    }
}
