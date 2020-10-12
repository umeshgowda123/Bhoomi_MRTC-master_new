package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Maintenance_Flags {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int ID;
    @ColumnInfo(name = "SRVC_NAME")
    private String SRVC_NAME;
    @ColumnInfo(name = "SRVC_STATUS")
    private String SRVC_STATUS;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSRVC_NAME() {
        return SRVC_NAME;
    }

    public void setSRVC_NAME(String SRVC_NAME) {
        this.SRVC_NAME = SRVC_NAME;
    }

    public String getSRVC_STATUS() {
        return SRVC_STATUS;
    }

    public void setSRVC_STATUS(String SRVC_STATUS) {
        this.SRVC_STATUS = SRVC_STATUS;
    }
}
