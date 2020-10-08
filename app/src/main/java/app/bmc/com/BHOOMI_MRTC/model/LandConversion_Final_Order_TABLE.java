package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LandConversion_Final_Order_TABLE {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "REQUEST_ID")
    private String REQUEST_ID;
    @ColumnInfo(name = "REQUEST_ID_RES")
    private String REQUEST_ID_RES;
    @ColumnInfo(name = "DST_ID")
    private String DST_ID;
    @ColumnInfo(name = "TLK_ID")
    private String TLK_ID;
    @ColumnInfo(name = "HBL_ID")
    private String HBL_ID;
    @ColumnInfo(name = "VLG_ID")
    private String VLG_ID;
    @ColumnInfo(name = "S_NO")
    private String S_NO;
    @ColumnInfo(name = "SNO_RES")
    private String SNO_RES;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getREQUEST_ID() {
        return REQUEST_ID;
    }

    public void setREQUEST_ID(String REQUEST_ID) {
        this.REQUEST_ID = REQUEST_ID;
    }

    public String getREQUEST_ID_RES() {
        return REQUEST_ID_RES;
    }

    public void setREQUEST_ID_RES(String REQUEST_ID_RES) {
        this.REQUEST_ID_RES = REQUEST_ID_RES;
    }

    public String getDST_ID() {
        return DST_ID;
    }

    public void setDST_ID(String DST_ID) {
        this.DST_ID = DST_ID;
    }

    public String getTLK_ID() {
        return TLK_ID;
    }

    public void setTLK_ID(String TLK_ID) {
        this.TLK_ID = TLK_ID;
    }

    public String getHBL_ID() {
        return HBL_ID;
    }

    public void setHBL_ID(String HBL_ID) {
        this.HBL_ID = HBL_ID;
    }

    public String getVLG_ID() {
        return VLG_ID;
    }

    public void setVLG_ID(String VLG_ID) {
        this.VLG_ID = VLG_ID;
    }

    public String getS_NO() {
        return S_NO;
    }

    public void setS_NO(String s_NO) {
        S_NO = s_NO;
    }

    public String getSNO_RES() {
        return SNO_RES;
    }

    public void setSNO_RES(String SNO_RES) {
        this.SNO_RES = SNO_RES;
    }
}
