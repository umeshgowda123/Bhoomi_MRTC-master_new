package app.bmc.com.BHOOMI_MRTC.model;

public class PacsThreadSafeSingletonFarmerWiseClass {

    private static PacsThreadSafeSingletonFarmerWiseClass instance;
    private String response;


    private PacsThreadSafeSingletonFarmerWiseClass()
    {

    }

    public static synchronized PacsThreadSafeSingletonFarmerWiseClass getInstance() {
        if (instance == null) {
            instance = new PacsThreadSafeSingletonFarmerWiseClass();
        }
        return instance;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
