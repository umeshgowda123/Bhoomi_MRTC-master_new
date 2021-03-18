package app.bmc.com.BHOOMI_MRTC.model;

public class ClsAppLgs {
    int AppID, AppType;
    String IPAddress;

    public int getAppID() {
        return AppID;
    }

    public void setAppID(int appID) {
        AppID = appID;
    }

    public int getAppType() {
        return AppType;
    }

    public void setAppType(int appType) {
        AppType = appType;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }
}
