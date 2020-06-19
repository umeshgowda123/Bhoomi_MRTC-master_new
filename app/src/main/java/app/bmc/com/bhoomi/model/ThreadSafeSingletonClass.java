package app.bmc.com.bhoomi.model;

public class ThreadSafeSingletonClass {

    private static ThreadSafeSingletonClass instance;
    private String response;


    private ThreadSafeSingletonClass()
    {

    }

    public static synchronized ThreadSafeSingletonClass getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingletonClass();
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
