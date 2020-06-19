package app.bmc.com.bhoomi.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import app.bmc.com.bhoomi.interfaces.FinancialYearInterface;

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
