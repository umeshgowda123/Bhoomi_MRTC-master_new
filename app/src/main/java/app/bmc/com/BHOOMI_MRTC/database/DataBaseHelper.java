package app.bmc.com.BHOOMI_MRTC.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import app.bmc.com.BHOOMI_MRTC.model.BankMasterData;
import app.bmc.com.BHOOMI_MRTC.model.CalamityDetails;
import app.bmc.com.BHOOMI_MRTC.model.MST_VLM;
import app.bmc.com.BHOOMI_MRTC.model.PacsBankMasterData;
import app.bmc.com.BHOOMI_MRTC.model.SeasonDetails;
import app.bmc.com.BHOOMI_MRTC.model.YearDetails;


@Database(entities = {MST_VLM.class, YearDetails.class, SeasonDetails.class, CalamityDetails.class, BankMasterData.class, PacsBankMasterData.class}, version = 1, exportSchema = false)
public abstract class DataBaseHelper extends RoomDatabase {
    public abstract DataBaseAccess daoAccess();
}
