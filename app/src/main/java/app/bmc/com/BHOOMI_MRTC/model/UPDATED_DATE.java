package app.bmc.com.BHOOMI_MRTC.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UPDATED_DATE {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "UPD_DATE")
    private String UPD_DATE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUPD_DATE() {
        return UPD_DATE;
    }

    public void setUPD_DATE(String UPD_DATE) {
        this.UPD_DATE = UPD_DATE;
    }
}
