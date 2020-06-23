package app.bmc.com.BHOOMI_MRTC.model;

import android.arch.persistence.room.ColumnInfo;
import android.support.annotation.NonNull;

import app.bmc.com.BHOOMI_MRTC.interfaces.FinancialYearInterface;

public class YearData implements FinancialYearInterface {

    @ColumnInfo(name = "Year")
    private String Year;

    @ColumnInfo(name = "Code")
    private int Code;


    @Override
    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    @Override
    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    @NonNull
    @Override
    public String toString() {
        return this.Year;
    }
}
