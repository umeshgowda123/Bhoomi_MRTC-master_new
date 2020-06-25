package app.bmc.com.BHOOMI_MRTC.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SeasonDetails {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "MSTSEASON_ID")
    private int MSTSEASON_ID;

    @ColumnInfo(name = "MSTSEASON_VAL")
    private int MSTSEASON_VAL;

    @ColumnInfo(name = "MSTSEASON_DESC")
    private String MSTSEASON_DESC;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMSTSEASON_ID() {
        return MSTSEASON_ID;
    }

    public void setMSTSEASON_ID(int MSTSEASON_ID) {
        this.MSTSEASON_ID = MSTSEASON_ID;
    }

    public int getMSTSEASON_VAL() {
        return MSTSEASON_VAL;
    }

    public void setMSTSEASON_VAL(int MSTSEASON_VAL) {
        this.MSTSEASON_VAL = MSTSEASON_VAL;
    }

    public String getMSTSEASON_DESC() {
        return MSTSEASON_DESC;
    }

    public void setMSTSEASON_DESC(String MSTSEASON_DESC) {
        this.MSTSEASON_DESC = MSTSEASON_DESC;
    }
}
