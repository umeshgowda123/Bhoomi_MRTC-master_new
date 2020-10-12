package app.bmc.com.BHOOMI_MRTC.util;

public class Constants {
    public static final String DISTRICT_NAME = "district_name";
    public static final String DISTRICT_ID = "distric_id";
    public static final String TALUK_NAME = "taluk_name";
    public static final String TALUK_ID = "taluk_id";
    public static final String HOBLI_NAME = "hobli_name";
    public static final String HOBLI_ID = "hobli_id";
    public static final String SURVEY_NUMBER = "survey_no";
    public static final String HISSA_NAME = "hissa_name";
    public static final String LAND_NUMBER = "land_no";
    public static final String VILLAGE_NAME = "village_name";
    public static final String VILLAGE_ID = "village_id";
    public static final String ERR_MSG = "Server is busy.Please try again later";
    public static final String SHARED_PREF = "shared_pref";
    public static final String LANGUAGE = "language";
    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_KN = "kn";


//    public static final String outerapiUrl = "http://parihara.karnataka.gov.in/clwstest/LoaneeReportService/Service1.svc/GetAllDetailsByAadhaarNumber/";
    public static final String outerapiUrl = "https://parihara.karnataka.gov.in/clwstest/LoaneeReportService/Service1.svc/GetAllDetailsByAadhaarNumber/"; //SUSMITA 4:21.P.M.

//    public static final String apiUrlByRcNo = "http://parihara.karnataka.gov.in/clwstest/LoaneeReportService/Service1.svc/GetAllDetailsByRationCardNumber/";
    public static final String apiUrlByRcNo = "https://parihara.karnataka.gov.in/clwstest/LoaneeReportService/Service1.svc/GetAllDetailsByRationCardNumber/"; //SUSMITA 4:21.P.M.



    public static final String USER_NAME = "bhoomi";
    public static final String USER_PWD = "B$hom!i!25";




    public static final String REPORT_SERVICE_USER_NAME = "bhoomiWS@2019";
    public static final String REPORT_SERVICE_PASSWORD = "c2a2b557-c792-48f9-a4cd-56fdf45974b9";



    public static int convertInt(String paymentStatus) {
        try{
            return Integer.parseInt(paymentStatus);
        }catch (Exception e){
            return 0;
        }
    }

    public static final String SHARED_PREF_MaintenanceFlags = "SHARED_PREF_MaintenanceFlags";
    public static final String View_RTC_Info = "View_RTC_Info";
    public static final String RTC_XML_Verify = "RTC_XML_Verify";
    public static final String View_RTC_By_Owner = "View_RTC_By_Owner";
    public static final String Mutation_Pendency = "Mutation_Pendency";
    public static final String Mutation_Summary = "Mutation_Summary";
    public static final String Mutation_Status = "Mutation_Status";
    public static final String Restriction_On_Land = "Restriction_On_Land";
    public static final String Land_Conversion_ReqStatus = "Land_Conversion_ReqStatus";
    public static final String Land_Conversion_FinOrder = "Land_Conversion_FinOrder";
}
