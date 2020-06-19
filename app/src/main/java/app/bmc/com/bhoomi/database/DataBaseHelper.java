package app.bmc.com.bhoomi.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import app.bmc.com.bhoomi.model.BankMasterData;
import app.bmc.com.bhoomi.model.CalamityDetails;
import app.bmc.com.bhoomi.model.MST_VLM;
import app.bmc.com.bhoomi.model.PacsBankMasterData;
import app.bmc.com.bhoomi.model.SeasonDetails;
import app.bmc.com.bhoomi.model.YearDetails;


@Database(entities = {MST_VLM.class, YearDetails.class, SeasonDetails.class, CalamityDetails.class, BankMasterData.class, PacsBankMasterData.class}, version = 1, exportSchema = false)
public abstract class DataBaseHelper extends RoomDatabase {
    public abstract DataBaseAccess daoAccess();
}
