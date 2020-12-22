package app.bmc.com.BHOOMI_MRTC.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import app.bmc.com.BHOOMI_MRTC.model.BankMasterData;
import app.bmc.com.BHOOMI_MRTC.model.CalamityDetails;
import app.bmc.com.BHOOMI_MRTC.model.LandConversion_Final_Order_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.LandConversion_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.MPD_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.MST_VLM;
import app.bmc.com.BHOOMI_MRTC.model.MS_REPORT_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.Maintenance_Flags;
import app.bmc.com.BHOOMI_MRTC.model.PacsBankMasterData;
import app.bmc.com.BHOOMI_MRTC.model.RTC_VERIFICATION_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.R_LAND_REPORT_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.SeasonDetails;
import app.bmc.com.BHOOMI_MRTC.model.UPDATED_DATE;
import app.bmc.com.BHOOMI_MRTC.model.VR_INFO;
import app.bmc.com.BHOOMI_MRTC.model.V_MUTATION_STATUS_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.YearDetails;


@Database(entities = {MST_VLM.class, Maintenance_Flags.class, YearDetails.class, SeasonDetails.class,
        CalamityDetails.class, BankMasterData.class, PacsBankMasterData.class, VR_INFO.class, MPD_TABLE.class,
        MS_REPORT_TABLE.class, R_LAND_REPORT_TABLE.class, V_MUTATION_STATUS_TABLE.class, RTC_VERIFICATION_TABLE.class,
        LandConversion_TABLE.class, LandConversion_Final_Order_TABLE.class, UPDATED_DATE.class}, version = 2, exportSchema = false)
public abstract class DataBaseHelper extends RoomDatabase {
    public abstract DataBaseAccess daoAccess();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE UPDATED_DATE (id INTEGER NOT null, UPD_DATE TEXT, Primary Key (id))");
        }
    };
//
//    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("Alter TABLE MPD_TABLE ADD Column MPD_UPD_DATE TEXT");
////            database.execSQL("Update sqlite_sequence set seq = 0 where name!='MST_VLM'");
//        }
//    };
}
