package com.example.sweetiean.stlfieldinstallation1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

/**
 * Created by sweetiean on 1/8/2015.
 */
public class FieldInstallationDB {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "field_installation_db";

    private static final String DATABASE_TABLE_SURVEY = "survey_table";
    private static final String DATABASE_TABLE_TASK = "task_table";
    public static final String DATABASE_TABLE_INFO = "info_table";
    public static final String DATABASE_TABLE_SURVEY_TEST = "survey_test_table";
    public static final String TABLE_CUSTOMERS = "Customer_Table";
    public static final String TABLE_EQUIPMENT = "Equipment_Table";

    public static final String TABLE_USERS = "Users_Table";

    //BASE DATA TABLE FIELDS FOR INFO TABLE
    public static final String ROW_ID = "_id";//also used in all other tables
    public static final String SYSAID_ID = "sysaid_id";
    public static final String SITE_ID = "site_id";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String SITE_CONTACT = "site_contact_name";
    public static final String TASK_TYPE  = "task_type"; //Install,replacement,dismantle,relocation,maintenance,survey
    public static final String ADDRESS = "address";
    public static final String REGION = "region";
    public static final String PHONE = "phone";
    public static final String FAX = "fax";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String LANDLORD_NAME = "landlord_name";
    public static final String RENT_STATUS = "rent_status"; //rented, owned
    public static final String LOCATION = "location";
    public static final String ENGINEER_NAME = "engineer_name";
    public static final String ENGINEER_SIGN = "engineer_sign";
    public static final String DATE = "date";

    //SURVEY TABLE FIELDS FOR SURVEY TABLE
    public static final String POWER_STATUS = "power_status";//none, stable, unstable
    public static final String VOLTAGE_MEASUREMENT = "voltage_measure";
    public static final String AIRCON = "aircon";//none, installed
    public static final String SERVER_ROOM_STATUS = "server_room_status";
    public static final String LOCATION_IDU = "location_of_idu";
    public static final String EARTHING = "earthing";//none, installed
    public static final String DISTANCE_IDU  = "distance_idu";
    public static final String SECURITY_CABLES = "security_of_cables";
    public static final String DUCTS = "ducts";//no, yes
    public static final String UNIT_TYPE = "unit_to_be_installed";//none, VSAT, STAR, MESH, VOIP, RADIO P2P, RADIO SU
    public static final String INST_TYPE = "inst_type";//roof top, ground, wall mount, mast
    public static final String HAVE_ISP = "isp";//no, yes
    public static final String ISP_NAME = "isp_name";
    public static final String ISP_TYPE = "isp_type";
    public static final String RADIO = "radio_type";
    public static final String SNR_RX = "snr_rx";
    public static final String BS = "bs";
    public static final String REMARKS = "remarks";//also used in installation table
    public static final String SURVIMG_1 = "surv_img1";
    public static final String SURVIMG_2 = "surv_img2";
    public static final String SURVIMG_3 = "surv_img3";
    public static final String SURVIMG_4 = "surv_img4";
    public static final String SURVIMG_5 = "surv_img5";
    public static final String SURVIMG_6 = "surv_img6";

    //INSTALLATION TABLE FIELDS FOR TASK TABLE
    public static final String EQUIPMENT = "equipment";
    public static final String SERIAL_NO = "serial_number";
    public static final String QTY = "quantity";
    public static final String CHECKED = "is_checked";
    public static final String OLD_SERIAL_NO = "old_serial_number";
    public static final String INSTIMG_1 = "inst_img1";
    public static final String INSTIMG_2 = "inst_img2";
    public static final String INSTIMG_3 = "inst_img3";

    //SURVEY TEST TABLE FIELDS
    public static final String RADIO_TYPE = "radio_type";
    public static final String SIGNAL_RX = "snr_rx";
    public static final String SIGNAL_TX = "snr_tx";
    public static final String SIGNAL_RTX = "snr_rtx";
    public static final String SIGNAL_RSSI = "snr_rssi";
    public static final String SIGNAL_CINR = "snr_cinr";
    public static final String BASE_STATION = "base_station";

    //CUSTOMER FIELDS
    //public static final String CUSTOMER_NAME = "Customer_Name";
    public static final String CUSTOMER_ACCOUNT = "customer_account";

    //ITEMS FIELDS
    public static final String ITEM_NAME = "item_name";
    public static final String ITEM_NUMBER = "item_id";

    //USERS FIELDS
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";


    private DbHelper fieldInstHelper;
    public final Context fieldInstContext;
    public static SQLiteDatabase fieldInstDatabase;

    public long createSiteInfoRecord(String sql_date, String sql_sysaid_id, String sql_site_id, String sql_customer_name,
                             String sql_site_contact, String sql_task_type, String sql_address, String sql_region,
                             String sql_phone, String sql_fax, String sql_mobile, String sql_email, String sql_landlord_name,
                             String sql_rent_status, String sql_loc_cords, String sql_eng_name, String sql_eng_sign) {


        ContentValues cv = new ContentValues();

        cv.put(DATE, sql_date);
        cv.put(SYSAID_ID, sql_sysaid_id);
        cv.put(SITE_ID, sql_site_id);
        cv.put(CUSTOMER_NAME, sql_customer_name);
        cv.put(SITE_CONTACT, sql_site_contact);
        cv.put(TASK_TYPE, sql_task_type);
        cv.put(ADDRESS, sql_address);
        cv.put(REGION, sql_region);
        cv.put(PHONE, sql_phone);
        cv.put(FAX, sql_fax);
        cv.put(MOBILE, sql_mobile);
        cv.put(EMAIL, sql_email);
        cv.put(LANDLORD_NAME, sql_landlord_name);
        cv.put(RENT_STATUS, sql_rent_status);
        cv.put(LOCATION, sql_loc_cords);
        cv.put(ENGINEER_NAME, sql_eng_name);
        cv.put(ENGINEER_SIGN, sql_eng_sign);

        return fieldInstDatabase.insert(DATABASE_TABLE_INFO, null, cv);
    }


    public long createTaskRecordNoImg(String sql_sysaid_id, String sql_task_type, String sql_equipment, String sql_serial_number,
                                 String sql_quantity, String sql_check, String sql_remarks, String sql_old_serial_number
                                ) {

        ContentValues cv = new ContentValues();

        cv.put(SYSAID_ID, sql_sysaid_id);
        cv.put(TASK_TYPE, sql_task_type);
        cv.put(EQUIPMENT, sql_equipment);
        cv.put(SERIAL_NO, sql_serial_number);
        cv.put(QTY, sql_quantity);
        cv.put(CHECKED, sql_check);
        cv.put(REMARKS, sql_remarks);
        cv.put(OLD_SERIAL_NO, sql_old_serial_number);


        return fieldInstDatabase.insert(DATABASE_TABLE_TASK, null, cv);
    }

    public long createSurveyRecord(String sql_sysaid_id, String sql_power_status, String sql_voltage, String sql_aircon,
                                   String sql_server_room, String sql_location_idu, String sql_earthing, String sql_distance_idu,
                                   String sql_security, String sql_ducts, String sql_unit, String sql_install_type, String sql_isp,
                                   String sql_radio_type, String sql_isp_type, String sql_isp_name, String sql_snr_rx, String sql_bs,
                                   String sql_remark, byte[] sql_surv_img_1, byte[] sql_surv_img_2, byte[] sql_surv_img_3, byte[] sql_surv_img_4,
                                   byte[] sql_surv_img_5, byte[] sql_surv_img_6) {

        ContentValues cv = new ContentValues();

        cv.put(SYSAID_ID, sql_sysaid_id);
        cv.put(POWER_STATUS, sql_power_status);
        cv.put(VOLTAGE_MEASUREMENT, sql_voltage);
        cv.put(AIRCON, sql_aircon);
        cv.put(SERVER_ROOM_STATUS, sql_server_room);
        cv.put(LOCATION_IDU, sql_location_idu);
        cv.put(EARTHING, sql_earthing);
        cv.put(DISTANCE_IDU, sql_distance_idu);
        cv.put(SECURITY_CABLES, sql_security);
        cv.put(DUCTS, sql_ducts);
        cv.put(UNIT_TYPE, sql_unit);
        cv.put(INST_TYPE, sql_install_type);
        cv.put(HAVE_ISP, sql_isp);
        cv.put(RADIO, sql_radio_type);
        cv.put(ISP_TYPE, sql_isp_type);
        cv.put(ISP_NAME, sql_isp_name);
        cv.put(SNR_RX, sql_snr_rx);
        cv.put(BS, sql_bs);
        cv.put(REMARKS, sql_remark);
        cv.put(SURVIMG_1, sql_surv_img_1);
        cv.put(SURVIMG_2, sql_surv_img_2);
        cv.put(SURVIMG_3, sql_surv_img_3);
        cv.put(SURVIMG_4, sql_surv_img_4);
        cv.put(SURVIMG_5, sql_surv_img_5);
        cv.put(SURVIMG_6, sql_surv_img_6);

        return fieldInstDatabase.insert(DATABASE_TABLE_SURVEY, null, cv);

    }


    public long createSurveyTestRecord(String sql_sysaid_id, String sql_radio_type, String sql_snr_rx, String sql_snr_tx, String sql_snr_rtx,
                                       String sql_snr_rssi, String sql_snr_cinr, String sql_bs) {

        ContentValues cv = new ContentValues();

        cv.put(SYSAID_ID, sql_sysaid_id);
        cv.put(RADIO, sql_radio_type);
        cv.put(SIGNAL_RX, sql_snr_rx);
        cv.put(SIGNAL_TX, sql_snr_tx);
        cv.put(SIGNAL_RTX, sql_snr_rtx);
        cv.put(SIGNAL_RSSI, sql_snr_rssi);
        cv.put(SIGNAL_CINR, sql_snr_cinr);
        cv.put(BASE_STATION, sql_bs);

        return fieldInstDatabase.insert(DATABASE_TABLE_SURVEY_TEST, null, cv);
    }




    public String getTaskDataArray(String _sysAidId) {


        String[] columns = new String[]{ROW_ID, SYSAID_ID, TASK_TYPE, EQUIPMENT, SERIAL_NO, QTY, CHECKED, REMARKS, OLD_SERIAL_NO};
        fieldInstDatabase=openForRead();

        Cursor c = fieldInstDatabase.query(DATABASE_TABLE_TASK, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);

        String result="";

        int rowCount =   0;


        int iEquipment = c.getColumnIndex(EQUIPMENT);
        int iSerial = c.getColumnIndex(SERIAL_NO);
        int iQty = c.getColumnIndex(QTY);
        int iChecked = c.getColumnIndex(CHECKED);
        int iRemarks = c.getColumnIndex(REMARKS);
        int iSerialOld = c.getColumnIndex(OLD_SERIAL_NO);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            rowCount = rowCount +1;
            String ItemNr;
            String SerialNr;
            String Qtys;
            String Chkd;
            String Remark;
            String OldSrlNo;


            ItemNr =  "Item: "+c.getString(iEquipment);
            SerialNr = "Serial: " + c.getString(iSerial);
            Qtys =  "QTY: " + c.getString(iQty);
            Chkd =  "Checked: " + c.getString(iChecked);
            Remark =  "Remarks: " + c.getString(iRemarks);
            OldSrlNo =  "Old Serial: " + c.getString(iSerialOld);
            result = result + ItemNr +", "+SerialNr+", "+Qtys +", "+ Chkd +", "+ Remark +", "+ OldSrlNo +"\n";


        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }


        return result;
    }

    public String getSurvTestDataArray(String _sysAidId) {


        String[] columns = new String[]{ROW_ID, SYSAID_ID, RADIO_TYPE, SIGNAL_RX, SIGNAL_TX, SIGNAL_RTX, SIGNAL_RSSI, SIGNAL_CINR, BASE_STATION};
        fieldInstDatabase=openForRead();

        Cursor c = fieldInstDatabase.query(DATABASE_TABLE_SURVEY_TEST, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);

        String result="";

        int rowCount =   0;


        int iRadioType = c.getColumnIndex(RADIO_TYPE);
        int iSignalRX = c.getColumnIndex(SIGNAL_RX);
        int iSignalTX = c.getColumnIndex(SIGNAL_TX);
        int iSignalRTX = c.getColumnIndex(SIGNAL_RTX);
        int iSignalRSSI = c.getColumnIndex(SIGNAL_RSSI);
        int iSignalCINR = c.getColumnIndex(SIGNAL_CINR);
        int iBaseStation = c.getColumnIndex(BASE_STATION);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            rowCount = rowCount +1;
            String RadioType;
            String SignalRX;
            String SignalTX;
            String SignalRTX;
            String SignalRSSI;
            String SignalCINR;
            String BaseStation;


            RadioType =  "RadioType: "+c.getString(iRadioType);
            SignalRX = "Signal(RX): " + c.getString(iSignalRX);
            SignalTX =  "Signal(TX): " + c.getString(iSignalTX);
            SignalRTX =  "Signal(RTX): " + c.getString(iSignalRTX);
            SignalRSSI =  "Signal(RSSI): " + c.getString(iSignalRSSI);
            SignalCINR =  "Signal(CINR): " + c.getString(iSignalCINR);
            BaseStation =  "Base Station: " + c.getString(iBaseStation);
            result = result + RadioType +", "+SignalRX+", "+SignalTX +", "+ SignalRTX +", "+ SignalRSSI +", "+ SignalCINR +", "+ BaseStation +"\n";



        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }


        return result;
    }



    //methods to retrieve all data from database to export to csv file
    public String getInfoForCSV()  {
        String[] columns = new String[]{ROW_ID, DATE, SYSAID_ID, SITE_ID, CUSTOMER_NAME, SITE_CONTACT, TASK_TYPE, ADDRESS,
                REGION, PHONE, FAX, MOBILE, EMAIL, LANDLORD_NAME, RENT_STATUS, LOCATION, ENGINEER_NAME};

        Cursor c;
        String result;
        fieldInstDatabase = openForRead();

        c = fieldInstDatabase.query(DATABASE_TABLE_INFO, columns, SYSAID_ID + "!=?", new String[]{""}, null, null, null);



        int iRow = c.getColumnIndex(ROW_ID);
        int iDate = c.getColumnIndex(DATE);
        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iSite = c.getColumnIndex(SITE_ID);
        int iCustName = c.getColumnIndex(CUSTOMER_NAME);
        int iSiteCont = c.getColumnIndex(SITE_CONTACT);
        int iTask = c.getColumnIndex(TASK_TYPE);
        int iAddress = c.getColumnIndex(ADDRESS);
        int iRegion = c.getColumnIndex(REGION);
        int iPhone = c.getColumnIndex(PHONE);
        int iFax = c.getColumnIndex(FAX);
        int iMobile = c.getColumnIndex(MOBILE);
        int iMail = c.getColumnIndex(EMAIL);
        int iLanNam = c.getColumnIndex(LANDLORD_NAME);
        int iRent = c.getColumnIndex(RENT_STATUS);
        int iLocation = c.getColumnIndex(LOCATION);
        int iEngName = c.getColumnIndex(ENGINEER_NAME);


        result = "LINE TYPE,ROW NUMBER,SYSAID ID,DATE,SITE ID,CUSTOMER NAME,SITE_CONTACT,TASK_TYPE,ADDRESS,REGION,PHONE,FAX,MOBILE,EMAIL,LANDLORD NAME,RENT STATUS,LOCATION LATITUDE,LOCATION LONGITUDE,ENGINEER NAME" + "\n";

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + "INFO," + c.getString(iRow) + "," + c.getString(iSysaid) + "," + c.getString(iDate) + "," + c.getString(iSite) +
                    "," + c.getString(iCustName) + "," + c.getString(iSiteCont) + "," + c.getString(iTask) + "," + c.getString(iAddress) +
                    "," + c.getString(iRegion) + "," + c.getString(iPhone) + "," + c.getString(iFax) + "," + c.getString(iMobile) +
                    "," + c.getString(iMail) + "," + c.getString(iLanNam) + "," + c.getString(iRent) + "," + c.getString(iLocation) +
                    "," + c.getString(iEngName) + "\n";



        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }

        return result;
    }

    public String getTaskForCSV() {

        String[] columns = new String[]{ROW_ID, SYSAID_ID, TASK_TYPE, EQUIPMENT, SERIAL_NO, QTY, CHECKED, REMARKS, OLD_SERIAL_NO};

        Cursor c = fieldInstDatabase.query(DATABASE_TABLE_TASK, columns, null, null, null, null, null);

        String result;

        int iRow = c.getColumnIndex(ROW_ID);
        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iTask = c.getColumnIndex(TASK_TYPE);
        int iEquipment = c.getColumnIndex(EQUIPMENT);
        int iSerial = c.getColumnIndex(SERIAL_NO);
        int iQty = c.getColumnIndex(QTY);
        int iChecked = c.getColumnIndex(CHECKED);
        int iRemarks = c.getColumnIndex(REMARKS);
        int iSerialOld = c.getColumnIndex(OLD_SERIAL_NO);

        result = "LINE TYPE,ROW NUMBER,SYSAID ID,TASK TYPE,EQUIPMENT,SERIAL NO,QUANTITY,CHECKED,REMARKS,OLD SERIAL NO" + "\n";

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            result = result +"TASK,"+ c.getString(iRow) + "," + c.getString(iSysaid) + "," + c.getString(iTask) + "," + c.getString(iEquipment) + "," + c.getString(iSerial) +
                    "," + c.getString(iQty) + "," + c.getString(iChecked) +  "," + c.getString(iRemarks) +
                    "," + c.getString(iSerialOld) + "\n";

        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }


        return result;
    }

    public String getSurveyForCSV()  {
        String[] columns = new String[]{ROW_ID, SYSAID_ID, POWER_STATUS, VOLTAGE_MEASUREMENT, AIRCON,
                SERVER_ROOM_STATUS, LOCATION_IDU, EARTHING, DISTANCE_IDU, SECURITY_CABLES, DUCTS, UNIT_TYPE, INST_TYPE,
                HAVE_ISP, ISP_NAME, REMARKS};

        Cursor c;
        String result;
        fieldInstDatabase = openForRead();

        c = fieldInstDatabase.query(DATABASE_TABLE_SURVEY, columns, null, null, null, null, null);



        int iRow = c.getColumnIndex(ROW_ID);
        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iPower = c.getColumnIndex(POWER_STATUS);
        int iVoltage = c.getColumnIndex(VOLTAGE_MEASUREMENT);
        int iAircon = c.getColumnIndex(AIRCON);
        int iServerRoom = c.getColumnIndex(SERVER_ROOM_STATUS);
        int iLocation = c.getColumnIndex(LOCATION_IDU);
        int iEarth = c.getColumnIndex(EARTHING);
        int iDistanceIDU = c.getColumnIndex(DISTANCE_IDU);
        int iSecCables = c.getColumnIndex(SECURITY_CABLES);
        int iDucts = c.getColumnIndex(DUCTS);
        int iUnit = c.getColumnIndex(UNIT_TYPE);
        int iInstType = c.getColumnIndex(INST_TYPE);
        int iHaveIsp = c.getColumnIndex(HAVE_ISP);
        int iISPName = c.getColumnIndex(ISP_NAME);
        int Remarks = c.getColumnIndex(REMARKS);


        result = "LINE TYPE,ROW NUMBER,SYSAID ID,POWER STATUS,VOLTAGE MEASUREMENT,AIRCON,SERVER ROOM STATUS,LOCATION IDU,EARTHING,DISTANCE OF IDU,SECURITY OF CABLES," +
                    "DUCTS,UNIT TYPE,INST TYPE,HAVE ISP,ISP NAME,REMARKS" + "\n";

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + "SURVEY," + c.getString(iRow) + "," + c.getString(iSysaid) + "," + c.getString(iPower) + "," + c.getString(iVoltage) +
                    "," + c.getString(iAircon) + "," + c.getString(iServerRoom) + "," + c.getString(iLocation) + "," + c.getString(iEarth) +
                    "," + c.getString(iDistanceIDU) + "," + c.getString(iSecCables) + "," + c.getString(iDucts) + "," + c.getString(iUnit) +
                    "," + c.getString(iInstType) + "," + c.getString(iHaveIsp) + "," + c.getString(iISPName) + "," + c.getString(Remarks) + "\n";



        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }

        return result;
    }

    public String getSurveyTestForCSV() {

        String[] columns = new String[]{ROW_ID, SYSAID_ID, RADIO_TYPE, SIGNAL_RX, SIGNAL_TX, SIGNAL_RTX, SIGNAL_RSSI, SIGNAL_CINR, BASE_STATION};

        Cursor c = fieldInstDatabase.query(DATABASE_TABLE_SURVEY_TEST, columns, null, null, null, null, null);

        String result;

        int iRow = c.getColumnIndex(ROW_ID);
        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iRadioType = c.getColumnIndex(RADIO_TYPE);
        int iSignalRX = c.getColumnIndex(SIGNAL_RX);
        int iSignalTX = c.getColumnIndex(SIGNAL_TX);
        int iSignalRTX = c.getColumnIndex(SIGNAL_RTX);
        int iSignalRSSI = c.getColumnIndex(SIGNAL_RSSI);
        int iSignalCINR = c.getColumnIndex(SIGNAL_CINR);
        int iBaseStation = c.getColumnIndex(BASE_STATION);

        result = "LINE TYPE,ROW NUMBER,SYSAID ID,RADIO TYPE,SIGNAL RX,SIGNAL TX,SIGNAL RTX,SIGNAL_RSSI,SIGNAL_CINR,BASE_STATION" + "\n";

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            result = result +"SURVEY TEST,"+ c.getString(iRow) + "," + c.getString(iSysaid) + "," + c.getString(iRadioType) + "," + c.getString(iSignalRX) + "," + c.getString(iSignalTX) +
                    "," + c.getString(iSignalRTX) + "," + c.getString(iSignalRSSI) +  "," + c.getString(iSignalCINR) +
                    "," + c.getString(iBaseStation) + "\n";

        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }


        return result;
    }


    //get installation type
    public String getInstallType(String _sysAidId)  {
        String[] columns = new String[]{SYSAID_ID, TASK_TYPE};

        Cursor c;
        String result = "";
        fieldInstDatabase = openForRead();

        c = fieldInstDatabase.query(DATABASE_TABLE_INFO, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);




        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iTask = c.getColumnIndex(TASK_TYPE);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            result = c.getString(iSysaid);
            result = c.getString(iTask);



        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }

        return result;
    }

    //method to retrieve data by sysaid id to send to Dynamics Ax
    public String getInfoRecord(String _sysAidId)  {
        String[] columns = new String[]{DATE, SYSAID_ID, SITE_ID, CUSTOMER_NAME, SITE_CONTACT, TASK_TYPE, ADDRESS,
                REGION, PHONE, FAX, MOBILE, EMAIL, LANDLORD_NAME, RENT_STATUS, LOCATION, ENGINEER_NAME};

        Cursor c;
        String result = "";
        fieldInstDatabase = openForRead();

        c = fieldInstDatabase.query(DATABASE_TABLE_INFO, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);



        int iDate = c.getColumnIndex(DATE);
        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iSite = c.getColumnIndex(SITE_ID);
        int iCustName = c.getColumnIndex(CUSTOMER_NAME);
        int iSiteCont = c.getColumnIndex(SITE_CONTACT);
        int iTask = c.getColumnIndex(TASK_TYPE);
        int iAddress = c.getColumnIndex(ADDRESS);
        int iRegion = c.getColumnIndex(REGION);
        int iPhone = c.getColumnIndex(PHONE);
        int iFax = c.getColumnIndex(FAX);
        int iMobile = c.getColumnIndex(MOBILE);
        int iMail = c.getColumnIndex(EMAIL);
        int iLanNam = c.getColumnIndex(LANDLORD_NAME);
        int iRent = c.getColumnIndex(RENT_STATUS);
        int iLocation = c.getColumnIndex(LOCATION);
        int iEngName = c.getColumnIndex(ENGINEER_NAME);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){


            result = MainActivity.axcall.pushInfoToAx(c.getString(iSysaid), c.getString(iDate), c.getString(iSite),
                    c.getString(iCustName), c.getString(iSiteCont), c.getString(iTask), c.getString(iAddress),
                    c.getString(iRegion), c.getString(iPhone), c.getString(iFax), c.getString(iMobile),
                    c.getString(iMail), c.getString(iLanNam), c.getString(iRent), c.getString(iLocation), c.getString(iEngName), MainActivity.DeviceId.toString());



        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }

        return result;
    }

    public String getSurveyRecord(String _sysAidId)  {
        String[] columns = new String[]{SYSAID_ID, POWER_STATUS, VOLTAGE_MEASUREMENT, AIRCON,
                SERVER_ROOM_STATUS, LOCATION_IDU, EARTHING, DISTANCE_IDU, SECURITY_CABLES, DUCTS, UNIT_TYPE, INST_TYPE,
                HAVE_ISP, ISP_NAME, REMARKS};

        Cursor c;
        String result = "";
        fieldInstDatabase = openForRead();

        c = fieldInstDatabase.query(DATABASE_TABLE_SURVEY, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);



        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iPower = c.getColumnIndex(POWER_STATUS);
        int iVoltage = c.getColumnIndex(VOLTAGE_MEASUREMENT);
        int iAircon = c.getColumnIndex(AIRCON);
        int iServerRoom = c.getColumnIndex(SERVER_ROOM_STATUS);
        int iLocation = c.getColumnIndex(LOCATION_IDU);
        int iEarth = c.getColumnIndex(EARTHING);
        int iDistanceIDU = c.getColumnIndex(DISTANCE_IDU);
        int iSecCables = c.getColumnIndex(SECURITY_CABLES);
        int iDucts = c.getColumnIndex(DUCTS);
        int iUnit = c.getColumnIndex(UNIT_TYPE);
        int iInstType = c.getColumnIndex(INST_TYPE);
        int iHaveIsp = c.getColumnIndex(HAVE_ISP);
        int iISPName = c.getColumnIndex(ISP_NAME);
        int Remarks = c.getColumnIndex(REMARKS);




        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            result = MainActivity.axcall.pushSurveyToAx(c.getString(iSysaid),c.getString(iPower),c.getString(iVoltage),
                        c.getString(iAircon),c.getString(iServerRoom),c.getString(iLocation),c.getString(iEarth),c.getString(iDistanceIDU),
                        c.getString(iSecCables),c.getString(iDucts),c.getString(iUnit),c.getString(iInstType),c.getString(iHaveIsp),c.getString(iISPName),c.getString(Remarks));




        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }

        return result;
    }

    public String getSurvTestRecord(String _sysAidId) {


        String[] columns = new String[]{SYSAID_ID, RADIO_TYPE, SIGNAL_RX, SIGNAL_TX, SIGNAL_RTX, SIGNAL_RSSI, SIGNAL_CINR, BASE_STATION};
        fieldInstDatabase=openForRead();

        Cursor c = fieldInstDatabase.query(DATABASE_TABLE_SURVEY_TEST, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);

        String result="";



        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iRadioType = c.getColumnIndex(RADIO_TYPE);
        int iSignalRX = c.getColumnIndex(SIGNAL_RX);
        int iSignalTX = c.getColumnIndex(SIGNAL_TX);
        int iSignalRTX = c.getColumnIndex(SIGNAL_RTX);
        int iSignalRSSI = c.getColumnIndex(SIGNAL_RSSI);
        int iSignalCINR = c.getColumnIndex(SIGNAL_CINR);
        int iBaseStation = c.getColumnIndex(BASE_STATION);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            result = MainActivity.axcall.pushSurveyTestToAx(c.getString(iSysaid),c.getString(iRadioType),c.getString(iSignalRX),c.getString(iSignalTX),c.getString(iSignalRTX),
                            c.getString(iSignalRSSI),c.getString(iSignalCINR),c.getString(iBaseStation));



        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }


        return result;
    }

    public String getTaskRecord(String _sysAidId) {

        String[] columns = new String[]{SYSAID_ID, TASK_TYPE, EQUIPMENT, SERIAL_NO, QTY, CHECKED, REMARKS, OLD_SERIAL_NO};

        Cursor c = fieldInstDatabase.query(DATABASE_TABLE_TASK, columns, SYSAID_ID + "=?", new String[]{_sysAidId}, null, null, null);
        fieldInstDatabase = openForRead();

        String result = "";


        int iSysaid = c.getColumnIndex(SYSAID_ID);
        int iTask = c.getColumnIndex(TASK_TYPE);
        int iEquipment = c.getColumnIndex(EQUIPMENT);
        int iSerial = c.getColumnIndex(SERIAL_NO);
        int iQty = c.getColumnIndex(QTY);
        int iChecked = c.getColumnIndex(CHECKED);
        int iRemarks = c.getColumnIndex(REMARKS);
        int iSerialOld = c.getColumnIndex(OLD_SERIAL_NO);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            result = MainActivity.axcall.pushTaskToAx(c.getString(iSysaid) , c.getString(iTask) ,c.getString(iEquipment),c.getString(iSerial),
                        c.getString(iQty),c.getString(iChecked) , c.getString(iRemarks),c.getString(iSerialOld));


        }
        if (c != null && !c.isClosed())
        {
            c.close();
        }


        return result;
    }



    public int getCustomersCount() {

        fieldInstDatabase = openForRead();

        String countQuery = "SELECT " + CUSTOMER_NAME + " FROM " + TABLE_CUSTOMERS;
        Cursor cursor = fieldInstDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();

        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        this.close();

        // return count
        return count;
    }

    public void deleteAllCustomers(){
        fieldInstDatabase = openForRead();

        String deleteQuery = "DELETE FROM " + TABLE_CUSTOMERS;
        fieldInstDatabase.rawQuery(deleteQuery, null).moveToFirst();
        /*if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }*/
        this.close();
    }

    public int getItemsCount() {

        fieldInstDatabase = openForRead();

        String countQuery = "SELECT " + ITEM_NAME + " FROM " + TABLE_EQUIPMENT;
        Cursor cursor = fieldInstDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();

        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        this.close();

        // return count
        return count;
    }

    public void deleteAllItems(){
        fieldInstDatabase = openForRead();

        String deleteQuery = "DELETE FROM " + TABLE_EQUIPMENT;
        fieldInstDatabase.rawQuery(deleteQuery, null).moveToFirst();
        /*if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }*/
        this.close();
    }

    public String[] getAllCustNames()
    {
        fieldInstDatabase = openForRead();
        Cursor c = fieldInstDatabase.query(TABLE_CUSTOMERS, new String[] {CUSTOMER_NAME}, null, null, null, null, null);

        if(c.getCount() >0)
        {
            String[] str = new String[c.getCount()];
            int i = 0;

            while (c.moveToNext())
            {
                str[i] = c.getString(c.getColumnIndex(CUSTOMER_NAME));
                i++;
            }
            if (c != null && !c.isClosed())
            {
                c.close();
            }
            this.close();
            return str;
        }
        else
        {
            if (c != null && !c.isClosed())
            {
                c.close();
            }
            this.close();
            return new String[] {};
        }
    }
    public String[] getAllItemNames()
    {
        fieldInstDatabase = openForRead();
        Cursor c = fieldInstDatabase.query(TABLE_EQUIPMENT, new String[] {ITEM_NAME}, null, null, null, null, null);

        if(c.getCount() >0)
        {
            String[] str = new String[c.getCount()];
            int i = 0;

            while (c.moveToNext())
            {
                str[i] = c.getString(c.getColumnIndex(ITEM_NAME));
                i++;
            }

            if (c != null && !c.isClosed())
            {
                c.close();
            }
            this.close();
            return str;
        }
        else
        {
            if (c != null && !c.isClosed())
            {
                c.close();
            }
            this.close();
            return new String[] {};
        }
    }

    public long createUserRecord(String sql_username, String sql_password) {

        ContentValues cv = new ContentValues();

        cv.put(USERNAME, sql_username);
        cv.put(PASSWORD, sql_password);


        return fieldInstDatabase.insert(TABLE_USERS, null, cv);
    }


    public String getSinlgeEntry(String username)
    {
        Cursor cursor = fieldInstDatabase.query(TABLE_USERS, null, USERNAME + "=?", new String[]{username}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "USER DOES NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex(PASSWORD));
        cursor.close();
        return password;
    }


    public static class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){

            db.execSQL("CREATE TABLE " + DATABASE_TABLE_INFO + " ("
                            + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + SYSAID_ID + " TEXT, "
                            + SITE_ID + " TEXT, "
                            + CUSTOMER_NAME + " TEXT, "
                            + SITE_CONTACT + " TEXT, "
                            + TASK_TYPE + " TEXT, "
                            + ADDRESS + " TEXT, "
                            + REGION + " TEXT, "
                            + PHONE + " TEXT, "
                            + FAX + " TEXT, "
                            + MOBILE + " TEXT, "
                            + EMAIL + " TEXT, "
                            + LANDLORD_NAME + " TEXT, "
                            + RENT_STATUS + " TEXT, "
                            + LOCATION + " TEXT, "
                            + ENGINEER_NAME + " TEXT, "
                            + ENGINEER_SIGN + " TEXT, "
                            + DATE + " TEXT);"

            );

            db.execSQL("CREATE TABLE " + DATABASE_TABLE_SURVEY + " ("
                    + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SYSAID_ID + " TEXT, "
                    + POWER_STATUS + " TEXT, "
                    + VOLTAGE_MEASUREMENT + " TEXT, "
                    + AIRCON + " TEXT, "
                    + SERVER_ROOM_STATUS + " TEXT, "
                    + LOCATION_IDU + " TEXT, "
                    + EARTHING + " TEXT, "
                    + DISTANCE_IDU + " TEXT, "
                    + SECURITY_CABLES + " TEXT, "
                    + DUCTS + " TEXT, "
                    + UNIT_TYPE + " TEXT, "
                    + INST_TYPE + " TEXT, "
                    + HAVE_ISP + " TEXT, "
                    + RADIO + " TEXT, "
                    + ISP_TYPE + " TEXT, "
                    + ISP_NAME + " TEXT, "
                    + SNR_RX + " TEXT, "
                    + BS + " TEXT, "
                    + REMARKS + " TEXT, "
                    + SURVIMG_1 + " BLOB, "
                    + SURVIMG_2 + " BLOB, "
                    + SURVIMG_3 + " BLOB, "
                    + SURVIMG_4 + " BLOB, "
                    + SURVIMG_5 + " BLOB, "
                    + SURVIMG_6 + " BLOB);"

            );


            db.execSQL("CREATE TABLE " + DATABASE_TABLE_TASK + " ("
                    + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SYSAID_ID + " TEXT, "
                    + TASK_TYPE + " TEXT, "
                    + EQUIPMENT + " TEXT, "
                    + SERIAL_NO+ " TEXT, "
                    + QTY + " TEXT, "
                    + CHECKED + " TEXT, "
                    + REMARKS + " TEXT, "
                    + OLD_SERIAL_NO + " TEXT, "
                    + INSTIMG_1 + " BLOB, "
                    + INSTIMG_2 + " BLOB, "
                    + INSTIMG_3 + " BLOB);"

            );

            db.execSQL("CREATE TABLE " + DATABASE_TABLE_SURVEY_TEST + " ("
                            + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + SYSAID_ID + " TEXT, "
                            + RADIO_TYPE + " TEXT, "
                            + SIGNAL_RX + " TEXT, "
                            + SIGNAL_TX+ " TEXT, "
                            + SIGNAL_RTX + " TEXT, "
                            + SIGNAL_RSSI + " TEXT, "
                            + SIGNAL_CINR + " TEXT, "
                            + BASE_STATION + " TEXT);"

            );

            db.execSQL("CREATE TABLE " + TABLE_CUSTOMERS + " ("
                            + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + CUSTOMER_NAME + " TEXT, "
                            + CUSTOMER_ACCOUNT + " TEXT);"

            );

            db.execSQL("CREATE TABLE " + TABLE_EQUIPMENT + " ("
                            + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + ITEM_NAME + " TEXT, "
                            + ITEM_NUMBER + " TEXT);"

            );



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "("
                    + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USERNAME + " TEXT, "
                    + PASSWORD + " TEXT);"
            );

        }
    }


    public FieldInstallationDB(Context c){
        fieldInstContext = c;
    }

    public SQLiteDatabase open(){
        fieldInstHelper = new DbHelper(fieldInstContext);
        fieldInstDatabase = fieldInstHelper.getWritableDatabase();
        return fieldInstDatabase;
    }
    public SQLiteDatabase openForRead(){
        fieldInstHelper = new DbHelper(fieldInstContext);
        fieldInstDatabase = fieldInstHelper.getReadableDatabase();
        return fieldInstDatabase;
    }

    public void close(){
        fieldInstHelper.close();
    }



}
