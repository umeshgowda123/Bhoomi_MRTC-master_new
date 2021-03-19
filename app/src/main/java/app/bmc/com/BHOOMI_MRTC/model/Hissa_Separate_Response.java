package app.bmc.com.BHOOMI_MRTC.model;

import androidx.annotation.NonNull;

public class Hissa_Separate_Response {
    private String hissa_no;
    private int land_code;

    public String getHissa_no() {
        return hissa_no;
    }

    public void setHissa_no(String hissa_no) {
        this.hissa_no = hissa_no;
    }

    public int getLand_code() {
        return land_code;
    }

    public void setLand_code(int land_code) {
        this.land_code = land_code;
    }
    @NonNull
    @Override
    public String toString() {
        return hissa_no + "";
    }
}
