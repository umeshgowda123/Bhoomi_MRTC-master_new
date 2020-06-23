package app.bmc.com.BHOOMI_MRTC.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class YearDetails{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Year")
    private String Year;

    @ColumnInfo(name = "Code")
    private int Code;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
