package app.bmc.com.BHOOMI_MRTC.interfaces;

import io.reactivex.annotations.NonNull;

public interface DTHVName_Interface {

    String getDistName();
    String getTalukName();
    String getHobliName();
    String getVillageName();

    @NonNull
    String toString();

}
