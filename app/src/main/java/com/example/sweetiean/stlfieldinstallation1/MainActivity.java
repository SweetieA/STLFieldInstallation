package com.example.sweetiean.stlfieldinstallation1;

import android.IntentIntegrator;
import android.IntentResult;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {

    //overview widgets
    ListView overview_display;
    // Arraylist for overviews
    private ArrayList<ArrayList<String>> overList;
    private ArrayList<String> sysid;
    private ArrayList<String> engName;
    private ArrayList<String> taskType;
    private ArrayList<String> date;
    private FieldInstallationDB db1;
    public  static WebService axcall;
    private SQLiteDatabase sqldb;
    private overviewAdapter adapter;
    public static String sysaidIdFileName;
    public String sysaididContext;

    public static String DeviceId;
    public static String checkInstallType;
    public static String jsonString, itemClicked;

    public static String webaddress;
    public static Context con;


    //base data widgets start declaration
    public static EditText sysaidId, siteId, siteContact, address, phone, fax, mobile, email, landlordName;
    public static Spinner taskSpinner, regionSpinner, rentStatusSpinner;
    public static TextView locationCords;
    GoogleMap map;
    public static String engineer_name;
    public static String engineer_sign;
    public static final int SIGNATURE_ACTIVITY = 1;
    //base data widgets end declaration

    public static AutoCompleteTextView customerName, equipment;

    //task widgets start declaration
    public static EditText serialNumber, quantity, remarks, oldSerialNumber;
    public static CheckBox check;
    public static ImageView instImg1, instImg2, instImg3;
    Button takeInstPhoto;
    Bitmap bmp1;
    public static TextView task;
    final static int cameraData = 0;
    final static int cameraDataSurv= 0;
    Uri FileUri;
    //task widgets end declaration

    //locations TODO
   public LocationManager locationManager;
   public LocationListener locationListener;


    //survey widgets start declaration
    public static Spinner powerStatusSpinner, airconSpinner, earthingSpinner, ductsSpinner, unitSpinner, installationTypeSpinner,
            ispSpinner, radioTypeSpinner;
    public static EditText voltage, serverRoom, locationIdu, distanceIdu, security, ispName, snrRX, sntx, snrtx, snrssi, sncinr, bs, remark;
    ImageView survImg1, survImg2, survImg3, survImg4, survImg5, survImg6;
    Bitmap bmp;
    Button takeSurvPhoto;
    //survey widgets end declaration



    int counter = 0;
    int count = 0;

    public static String custName, custAccount, customer_full, itemName, itemID, equipment_full;
    private static final String TAG_NAME_CUSTOMER = "AccountName";
    private static final String TAG_NUMBER_CUSTOMER = "AccountNumber";
    private static final String TAG_NAME_ITEM = "ItemName";
    private static final String TAG_NUMBER_ITEM = "ItemID";





    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        webaddress = prefs.getString("ipKey", "null");

        con = this;
        //location updates
        locationListener = new myLocationListner();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this.locationListener);

        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        DeviceId = tm.getDeviceId();

        //location updates end TODO
        //start tab creation
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpecOverview = tabHost.newTabSpec("Overview");
        tabSpecOverview.setContent(R.id.overviewTab);
        tabSpecOverview.setIndicator("Overview");
        tabHost.addTab(tabSpecOverview);


        TabHost.TabSpec tabSpecBase = tabHost.newTabSpec("Base Data");
        tabSpecBase.setContent(R.id.baseDataTab);
        tabSpecBase.setIndicator("Base Data");
        tabHost.addTab(tabSpecBase);

        TabHost.TabSpec tabSpecSurvey = tabHost.newTabSpec("Survey");
        tabSpecSurvey.setContent(R.id.surveyTab);
        tabSpecSurvey.setIndicator("Survey/Pictures");
        tabHost.addTab(tabSpecSurvey);

        TabHost.TabSpec tabSpecTask = tabHost.newTabSpec("Task");
        tabSpecTask.setContent(R.id.taskTab);
        tabSpecTask.setIndicator("Task");
        tabHost.addTab(tabSpecTask);

        //end tab creation
        axcall = new WebService();
        //OVERVIEW WIDGETS

        overview_display = (ListView) findViewById(R.id.displayListView);
        db1 = new FieldInstallationDB(this);
        sqldb = db1.open();
        if (sqldb.isOpen()) {
            Cursor cursor = sqldb.query(FieldInstallationDB.DATABASE_TABLE_INFO,
                    new String[]{FieldInstallationDB.SYSAID_ID, FieldInstallationDB.TASK_TYPE, FieldInstallationDB.ENGINEER_NAME, FieldInstallationDB.DATE}, null, null, null, null, null);

            overList = new ArrayList<ArrayList<String>>();
            sysid = new ArrayList<String>();
            date = new ArrayList<String>();
            engName = new ArrayList<String>();
            taskType = new ArrayList<String>();

            if (cursor.moveToFirst()) {
                do {

                    sysid.add(cursor.getString(0));
                    taskType.add(cursor.getString(1));
                    engName.add(cursor.getString(2));
                    date.add(cursor.getString(3));

                } while (cursor.moveToNext());
                overList.add(sysid);
                overList.add(taskType);
                overList.add(engName);
                overList.add(date);

            }
            cursor.close();
        }

        if (overList.size() != 0) {
            adapter = new overviewAdapter(this, overList);
            overview_display.setAdapter(adapter);
        }

        //listview context menu
        registerForContextMenu(overview_display);//takes listview as argument. can take any view tho



        //BASE DATA WIDGETS START

        taskSpinner = (Spinner) findViewById(R.id.taskTypeSpinner);
        ArrayAdapter<CharSequence> taskAdapter = ArrayAdapter.createFromResource(this, R.array.taskTypeArray,
                android.R.layout.simple_spinner_item);

        taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskSpinner.setAdapter(taskAdapter);

        taskSpinner.setOnItemSelectedListener(new myGetTasktypeListner());

        regionSpinner = (Spinner) findViewById(R.id.regionSpinner);
        ArrayAdapter<CharSequence> regionAdapter = ArrayAdapter.createFromResource(this, R.array.regionArray,
                android.R.layout.simple_spinner_item);

        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionAdapter);


        rentStatusSpinner = (Spinner) findViewById(R.id.rentStatusSpinner);
        ArrayAdapter<CharSequence> rentStatusAdapter = ArrayAdapter.createFromResource(this, R.array.rentStatusArray,
                android.R.layout.simple_spinner_item);

        rentStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rentStatusSpinner.setAdapter(rentStatusAdapter);


        //integrating and setting up the map view.
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();


        //configure button to open signature activity and get signature

        Button getSignature = (Button) findViewById(R.id.getSignatureButton);
        getSignature.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignatureActivity.class);
                startActivityForResult(intent, SIGNATURE_ACTIVITY);
            }
        });


        sysaidId = (EditText) findViewById(R.id.sysaidIdEditText);
        if(sysaidId.getText().toString().length() == 0){
            sysaidId.setError("Sysaid Id is required");
        }

        siteId = (EditText) findViewById(R.id.siteIdEditText);

        customerName = (AutoCompleteTextView) findViewById(R.id.customerNameAutoCompleteTextView);
        if(customerName.getText().toString().length() == 0){
            customerName.setError("Customer Name is required");
        }
        FieldInstallationDB sqlitedb = new FieldInstallationDB(this);
        sqlitedb.openForRead();

        String[] accounts = sqlitedb.getAllCustNames();

        for(int i = 0; i < accounts.length; i++)
        {
            Log.i(this.toString(), accounts[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, accounts);
        customerName.setAdapter(adapter);
        customerName.setThreshold(1);

        siteContact = (EditText) findViewById(R.id.siteContactEditText);
        if(siteContact.getText().toString().length() == 0){
            siteContact.setError("Site Contact Name is required");
        }
        address = (EditText) findViewById(R.id.addressEditText);
        phone = (EditText) findViewById(R.id.phoneEditText);
        fax = (EditText) findViewById(R.id.faxEditText);
        mobile = (EditText) findViewById(R.id.mobileEditText);
        email = (EditText) findViewById(R.id.emailEditText);
        landlordName = (EditText) findViewById(R.id.landlordNameEditText);
        locationCords = (TextView) findViewById(R.id.locTextView);


        //BASE DATA WIDGETS END


        //INSTALLATION WIDGETS START

        task = (TextView) findViewById(R.id.taskTextView);

        equipment = (AutoCompleteTextView) findViewById(R.id.equipmentAutoCompleteTextView);
        FieldInstallationDB sqlitedb1 = new FieldInstallationDB(this);
        sqlitedb1.openForRead();

        String[] items = sqlitedb.getAllItemNames();

        for(int i = 0; i < items.length; i++)
        {
            Log.i(this.toString(), items[i]);
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        equipment.setAdapter(adapter1);
        equipment.setThreshold(1);

        serialNumber = (EditText) findViewById(R.id.serialEditText);
        quantity = (EditText) findViewById(R.id.quantityEditText);
        check = (CheckBox) findViewById(R.id.checkCheckBox);
        remarks = (EditText) findViewById(R.id.remarksEditText);
        oldSerialNumber = (EditText) findViewById(R.id.oldSerialEditText);
        instImg1 = (ImageView) findViewById(R.id.instImg1);
        instImg2 = (ImageView) findViewById(R.id.instImg2);
        instImg3 = (ImageView) findViewById(R.id.instImg3);
        takeInstPhoto = (Button) findViewById(R.id.takeInstPhotoBtn);

        //INSTALLATION WIDGETS END


        //SURVEY WIDGETS START

        powerStatusSpinner = (Spinner) findViewById(R.id.powerStatusSpinner);
        ArrayAdapter<CharSequence> powerStatusAdapter = ArrayAdapter.createFromResource(this, R.array.powerStatusArray,
                android.R.layout.simple_spinner_item);

        powerStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        powerStatusSpinner.setAdapter(powerStatusAdapter);

        airconSpinner = (Spinner) findViewById(R.id.airconSpinner);
        ArrayAdapter<CharSequence> airconAdapter = ArrayAdapter.createFromResource(this, R.array.airconArray,
                android.R.layout.simple_spinner_item);

        airconAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        airconSpinner.setAdapter(airconAdapter);


        earthingSpinner = (Spinner) findViewById(R.id.earthingSpinner);
        ArrayAdapter<CharSequence> earthingAdapter = ArrayAdapter.createFromResource(this, R.array.earthingArray,
                android.R.layout.simple_spinner_item);

        earthingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        earthingSpinner.setAdapter(earthingAdapter);


        ductsSpinner = (Spinner) findViewById(R.id.ductsSpinner);
        ArrayAdapter<CharSequence> ductsAdapter = ArrayAdapter.createFromResource(this, R.array.ductsArray,
                android.R.layout.simple_spinner_item);

        ductsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ductsSpinner.setAdapter(ductsAdapter);


        unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(this, R.array.unitArray,
                android.R.layout.simple_spinner_item);

        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unitAdapter);


        installationTypeSpinner = (Spinner) findViewById(R.id.installationTypeSpinner);
        ArrayAdapter<CharSequence> installationTypeAdapter = ArrayAdapter.createFromResource(this, R.array.installationTypeArray,
                android.R.layout.simple_spinner_item);

        installationTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        installationTypeSpinner.setAdapter(installationTypeAdapter);


        ispSpinner = (Spinner) findViewById(R.id.ispSpinner);
        ArrayAdapter<CharSequence> ispAdapter = ArrayAdapter.createFromResource(this, R.array.ispArray,
                android.R.layout.simple_spinner_item);

        ispAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ispSpinner.setAdapter(ispAdapter);


        radioTypeSpinner = (Spinner) findViewById(R.id.radioTypeSpinner);
        ArrayAdapter<CharSequence> ispTypeAdapter = ArrayAdapter.createFromResource(this, R.array.radioTypeArray,
                android.R.layout.simple_spinner_item);

        ispTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        radioTypeSpinner.setAdapter(ispTypeAdapter);

        voltage = (EditText) findViewById(R.id.voltageEditText);
        serverRoom = (EditText) findViewById(R.id.serverRoomEditText);
        locationIdu = (EditText) findViewById(R.id.locationIduEditText);
        distanceIdu = (EditText) findViewById(R.id.distanceIduEditText);
        security = (EditText) findViewById(R.id.securityEditText);
        ispName = (EditText) findViewById(R.id.ispNameEditText);
        snrRX = (EditText) findViewById(R.id.snrEditText);
        sntx = (EditText) findViewById(R.id.sntxEditText);
        snrtx = (EditText) findViewById(R.id.snrtxEditText);
        snrssi = (EditText)findViewById(R.id.snrssiEditText);
        sncinr = (EditText)findViewById(R.id.sncinrEditText);
        bs = (EditText) findViewById(R.id.bsEditText);
        remark = (EditText) findViewById(R.id.remarkEditText);
        survImg1 = (ImageView) findViewById(R.id.survImg1);
        survImg2 = (ImageView) findViewById(R.id.survImg2);
        survImg3 = (ImageView) findViewById(R.id.survImg3);
        survImg4 = (ImageView) findViewById(R.id.survImg4);
        survImg5 = (ImageView) findViewById(R.id.survImg5);
        survImg6 = (ImageView) findViewById(R.id.survImg6);
        takeSurvPhoto = (Button) findViewById(R.id.takeSurvPhotoBtn);

        //SURVEY WIDGETS END


    }



    public String getPDFFileName(){
        return sysaidIdFileName;
    }

    public void setPDFFileName(String _param){
        sysaidIdFileName = _param;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        int i = info.position;
        ArrayList StringArray = adapter.getsysaidId();
        this.setPDFFileName((StringArray.get(i).toString()));
        menu.setHeaderTitle("CASE " + StringArray.get(i).toString());
        //TODO clicked sysaidid
        sysaididContext = StringArray.get(i).toString();
        //menu.add(0, v.getId(), 0, "View");
        menu.add(0, v.getId(), 0, "Show Report");
        menu.add(0, v.getId(), 0, "Send as attachment");
        menu.add(0, v.getId(), 0, "Upload to ERP");
        menu.add(0, v.getId(), 0, "Upload report via webservice");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //int position = info.position;

        super.onContextItemSelected(item);
        if(item.getTitle() == "Show Report"){

            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            File file = new File("/sdcard/" + this.getPDFFileName() + ".pdf");
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            startActivity(intent);
        }
        if(item.getTitle()=="Upload report via webservice")
        {
            //

            checkInstallType = "uploadfile";

            uploadToERP uploadToERP = new uploadToERP(MainActivity.this);
            uploadToERP.execute();

        }


        if(item.getTitle()=="Send as attachment")
        {
            //
            this.sendMail(this.getPDFFileName());

        }
        if(item.getTitle()=="Upload to ERP")
        {
            //some function to return task type
            //TODO implement
            FieldInstallationDB test = new FieldInstallationDB(MainActivity.this);

            checkInstallType = test.getInstallType(this.getPDFFileName());

            uploadToERP uploadToERP = new uploadToERP(MainActivity.this);
            uploadToERP.execute();

        }

        return true;
    }


    public void sendMail(String _sysAid)
    {
        //The easiest way to send an e-mail is to create an Intent of type ACTION_SEND.

        Intent wanaemail = new Intent(Intent.ACTION_SEND);
        wanaemail.putExtra(Intent.EXTRA_SUBJECT, "Field Installation Form Automatically Sent From Tablet");
        wanaemail.putExtra(Intent.EXTRA_EMAIL, new String[]{"elad@stlghana.com", "eltahanma@stlghana.com", "frankow@stlghana.com", "perja@stlghana.com", "lydiame@stlghana.com", "ValeryRo@stlghana.com"});
        wanaemail.putExtra(Intent.EXTRA_TEXT, "Mail with an attachment CASE NUMBER: "+_sysAid);
        //to attach a single file, we add some extended data to our intent:
        File attachment = new File("/sdcard/"+this.getPDFFileName()+".pdf");
        wanaemail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(attachment));
        wanaemail.setType("application/pdf");
        startActivity(Intent.createChooser(wanaemail, "Send mail"));

    }


    public void sendCSV()
    {
        //The easiest way to send an e-mail is to create an Intent of type ACTION_SEND.

        Intent wanaemail = new Intent(Intent.ACTION_SEND);
        wanaemail.putExtra(Intent.EXTRA_SUBJECT, "Field Installation CSV Automatically Sent From Tablet");
        //wanaemail.putExtra(Intent.EXTRA_EMAIL, new String[]{"elad@stlghana.com", "eltahanma@stlghana.com", "frankow@stlghana.com", "perja@stlghana.com", "lydiame@stlghana.com"});
        wanaemail.putExtra(Intent.EXTRA_EMAIL, new String[]{"perja@stlghana.com"});
        wanaemail.putExtra(Intent.EXTRA_TEXT, "Mail with summary report ");
        //to attach a single file, we add some extended data to our intent:
        File attachment = new File("/sdcard/" + MainActivity.DeviceId +"_"+ Utility.getTodaysDate() + ".csv");
        wanaemail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(attachment));
        wanaemail.setType("application/csv");
        startActivity(Intent.createChooser(wanaemail, "Send mail"));

    }


    public void insertIntoInfoDb() {

        String sql_date = Utility.getTodaysDate();
        String sql_sysaid_id = sysaidId.getText().toString();
        String sql_site_id = siteId.getText().toString();
        String sql_customer_name = customerName.getText().toString();
        String sql_site_contact = siteContact.getText().toString();
        String sql_task_type = taskSpinner.getSelectedItem().toString();
        String sql_address = address.getText().toString();
        String sql_region = regionSpinner.getSelectedItem().toString();
        String sql_phone = phone.getText().toString();
        String sql_fax = fax.getText().toString();
        String sql_mobile = mobile.getText().toString();
        String sql_email = email.getText().toString();
        String sql_landlord_name = landlordName.getText().toString();
        String sql_rent_status = rentStatusSpinner.getSelectedItem().toString();
        String sql_loc_cords = locationCords.getText().toString();
        String sql_eng_name = engineer_name;
        String sql_eng_sign = engineer_sign;

        if (sql_task_type.equals("Survey")) {
            checkInstallType = "Survey";
        }
        else {
            checkInstallType = "Installation";
        }

        FieldInstallationDB site_info_record = new FieldInstallationDB(MainActivity.this);
        site_info_record.open();
        site_info_record.createSiteInfoRecord(sql_date, sql_sysaid_id, sql_site_id, sql_customer_name, sql_site_contact,
                sql_task_type, sql_address, sql_region, sql_phone, sql_fax, sql_mobile, sql_email,
                sql_landlord_name, sql_rent_status, sql_loc_cords, sql_eng_name, sql_eng_sign);
        site_info_record.close();

    }

    public void insertIntoSurveyDb() {
        String sql_sysaid_id = sysaidId.getText().toString();
        String sql_power_status = powerStatusSpinner.getSelectedItem().toString();
        String sql_voltage = voltage.getText().toString();
        String sql_aircon = airconSpinner.getSelectedItem().toString();
        String sql_server_room = serverRoom.getText().toString();
        String sql_location_idu = locationIdu.getText().toString();
        String sql_earthing = earthingSpinner.getSelectedItem().toString();
        String sql_distance_idu = distanceIdu.getText().toString();
        String sql_security = security.getText().toString();
        String sql_ducts = ductsSpinner.getSelectedItem().toString();
        String sql_unit = unitSpinner.getSelectedItem().toString();
        String sql_install_type = installationTypeSpinner.getSelectedItem().toString();
        String sql_isp = ispSpinner.getSelectedItem().toString();
        String sql_radio_type = "";
        String sql_isp_type = "";
        String sql_isp_name = ispName.getText().toString();
        String sql_snr_rx = "";
        String sql_bs = "";
        String sql_remark = remark.getText().toString();
        byte[] sql_surv_img_1 = Utility.getBytes(((BitmapDrawable) survImg1.getDrawable()).getBitmap());
        byte[] sql_surv_img_2 = Utility.getBytes(((BitmapDrawable) survImg2.getDrawable()).getBitmap());
        byte[] sql_surv_img_3 = Utility.getBytes(((BitmapDrawable) survImg3.getDrawable()).getBitmap());
        byte[] sql_surv_img_4 = Utility.getBytes(((BitmapDrawable) survImg4.getDrawable()).getBitmap());
        byte[] sql_surv_img_5 = Utility.getBytes(((BitmapDrawable) survImg5.getDrawable()).getBitmap());
        byte[] sql_surv_img_6 = Utility.getBytes(((BitmapDrawable) survImg6.getDrawable()).getBitmap());

        FieldInstallationDB surv_record = new FieldInstallationDB(MainActivity.this);
        surv_record.open();
        surv_record.createSurveyRecord(sql_sysaid_id, sql_power_status, sql_voltage, sql_aircon, sql_server_room,
                sql_location_idu, sql_earthing, sql_distance_idu, sql_security, sql_ducts, sql_unit, sql_install_type,
                sql_isp, sql_radio_type, sql_isp_type, sql_isp_name, sql_snr_rx, sql_bs, sql_remark, sql_surv_img_1,
                sql_surv_img_2, sql_surv_img_3, sql_surv_img_4, sql_surv_img_5, sql_surv_img_6);
        surv_record.close();
    }

    public void insertIntoSurveyTestDb() {
        String sql_sysaid_id = sysaidId.getText().toString();
        String sql_radio_type = radioTypeSpinner.getSelectedItem().toString();
        String sql_snr_rx = snrRX.getText().toString();
        String sql_snr_tx = sntx.getText().toString();
        String sql_snr_rtx = snrtx.getText().toString();
        String sql_snr_rssi = snrssi.getText().toString();
        String sql_snr_cinr = sncinr.getText().toString();
        String sql_bs = bs.getText().toString();

        FieldInstallationDB surv_test_record = new FieldInstallationDB(MainActivity.this);
        surv_test_record.open();
        surv_test_record.createSurveyTestRecord(sql_sysaid_id, sql_radio_type, sql_snr_rx, sql_snr_tx, sql_snr_rtx, sql_snr_rssi, sql_snr_cinr, sql_bs);
        surv_test_record.close();
    }

    public void testRadio(View view) {
        insertIntoSurveyTestDb();
        Toast.makeText(this, "Radio Test Data Recorded", Toast.LENGTH_LONG).show();

        snrRX.setText("");
        sntx.setText("");
        snrtx.setText("");
        snrssi.setText("");
        sncinr.setText("");
        bs.setText("");
    }

    public void insertIntoTaskDb() {
        String sql_sysaid_id = sysaidId.getText().toString();
        String sql_task_type = task.getText().toString();
        String sql_equipment = equipment.getText().toString();
        String sql_serial_number = serialNumber.getText().toString();
        String sql_quantity = quantity.getText().toString();
        String sql_check = (check.isChecked()) ? "True" : "False";
        String sql_remarks = remarks.getText().toString();
        String sql_old_serial_number = oldSerialNumber.getText().toString();
        //byte[] sql_inst_img_1 = Utility.getBytes(((BitmapDrawable) instImg1.getDrawable()).getBitmap());
        //byte[] sql_inst_img_2 = Utility.getBytes(((BitmapDrawable) instImg2.getDrawable()).getBitmap());
        //byte[] sql_inst_img_3 = Utility.getBytes(((BitmapDrawable) instImg3.getDrawable()).getBitmap());

        FieldInstallationDB task_record = new FieldInstallationDB(MainActivity.this);
        task_record.open();
        task_record.createTaskRecordNoImg(sql_sysaid_id, sql_task_type, sql_equipment, sql_serial_number,
                sql_quantity, sql_check, sql_remarks, sql_old_serial_number);

        task_record.close();

    }

    public void writeTaskPdf(){
        String filename = sysaidId.getText().toString();
        //String date = Utility.getTodaysDate();
        pdfWriteRead fop = new pdfWriteRead();
        FieldInstallationDB findtask = new FieldInstallationDB(MainActivity.this);
        String itemsArray;
        itemsArray = findtask.getTaskDataArray(filename);

        //fop.writetask(filename, taskArray);
        if (fop.writetask(filename, itemsArray)) {
            Toast.makeText(getApplicationContext(), filename +  ".pdf created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void writeSurveyPdf(){
        String filename = sysaidId.getText().toString();
        //String date = Utility.getTodaysDate();
        pdfWriteRead fop = new pdfWriteRead();
        FieldInstallationDB findSurveyTest = new FieldInstallationDB(MainActivity.this);
        String taskArray;
        taskArray = findSurveyTest.getSurvTestDataArray(filename);

        //fop.writesurvey(filename);
        if (fop.writesurvey(filename, taskArray)) {
            Toast.makeText(getApplicationContext(), filename +  ".pdf created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void saverecordFinal()
    {
        boolean insert = true;

        if(sysaidId.getText().toString().length() == 0){
            insert = false;
            Toast.makeText(this, "Sysaid Id is required", Toast.LENGTH_SHORT).show();
        }


        if(customerName.getText().toString().length() == 0){
            insert = false;
            Toast.makeText(this, "Customer Name is required", Toast.LENGTH_SHORT).show();
        }


        if(siteContact.getText().toString().length() == 0){
            insert = false;
            Toast.makeText(this, "Site Contact Name is required", Toast.LENGTH_SHORT).show();
        }

        if (count < 6) {

            Toast.makeText(this, "6 IMAGES REQUIRED", Toast.LENGTH_LONG).show();

        } else if(insert == true){
            String selection;
            insertIntoInfoDb();
            insertIntoSurveyDb();//because of the task pictures being saved in the survey db no matter what there shd be an insert

            selection = taskSpinner.getSelectedItem().toString();
            if (selection.equals("Survey")) {
                writeSurveyPdf();
                checkInstallType = "Survey";
            }
            else {
                writeTaskPdf();
                checkInstallType = "Installation";
            }

            Toast.makeText(this, "ALL DATA STORED SUCCESSFULLY", Toast.LENGTH_LONG).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    public void saveTaskInfo(View s) {

        if (taskSpinner.getSelectedItem().toString().equals("Survey")) {
            Toast.makeText(this, "You can only save information in the Survey Tab, for surveys", Toast.LENGTH_LONG).show();}
            else {
            //insertIntoInfoDb();
            insertIntoTaskDb();
            //insertIntoSurveyDb();
            //writeTaskPdf();
            Toast.makeText(this, "TASK STORED SUCCESSFULLY", Toast.LENGTH_LONG).show();
            //Intent intent = getIntent();
            //finish();
            //startActivity(intent);
        }
            //task.setText("");
            equipment.setText("");
            serialNumber.setText("");
            quantity.setText("");
            check.setText("");
            remarks.setText("");
            oldSerialNumber.setText("");



    }

    public void instPic(View pic) {
        if (counter <= 2) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    sysaidId.getText().toString()+"inst_"+counter+".jpg";
            File _file = new File(imageFilePath);
            FileUri = Uri.fromFile(_file);//it was declared globally
            i.putExtra(MediaStore.EXTRA_OUTPUT, FileUri);
            startActivityForResult(i, cameraData);
        } else {

            takeInstPhoto.setEnabled(false);
        }

    }

    public void survPic(View pix) {
        if (count <= 5) {
            Intent m = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    sysaidId.getText().toString()+"surv_"+count+".jpg";
            File _file = new File(imageFilePath);
            FileUri = Uri.fromFile(_file);//it was declared globally
            m.putExtra(MediaStore.EXTRA_OUTPUT, FileUri);
            startActivityForResult(m, cameraDataSurv);
        } else {

            takeSurvPhoto.setEnabled(false);
        }

    }



    public static void InsertCustIntoDB()
    {


        try{
            JSONArray jarray =new JSONArray(jsonString);

            FieldInstallationDB sqlitedb = new FieldInstallationDB(con);
            sqlitedb.deleteAllCustomers();
            sqlitedb.openForRead();



            for (int count = 0; count < jarray.length(); count++) {
                JSONObject obj = jarray.getJSONObject(count);
                custName = obj.getString(TAG_NAME_CUSTOMER);
                custAccount = obj.getString(TAG_NUMBER_CUSTOMER);

                customer_full = custName +" : " + custAccount;

                ContentValues cv = new ContentValues();
                cv.put(FieldInstallationDB.CUSTOMER_NAME, customer_full);
                cv.put(FieldInstallationDB.CUSTOMER_ACCOUNT, custAccount);
                sqlitedb.fieldInstDatabase.insert(FieldInstallationDB.TABLE_CUSTOMERS, null, cv);
            }

            int counter = sqlitedb.getCustomersCount();
            Log.i("Customers", String.valueOf(counter));

            if(counter == jarray.length()){
                Toast.makeText(con, String.valueOf(counter)+" Customers Saved to Database Successfully!", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(con, "FAILED!!!", Toast.LENGTH_SHORT).show();

            }

            sqlitedb.close();
        }
        catch (JSONException e){

            e.printStackTrace();
        }



    }

    public static void InsertItemsIntoDB()
    {


        try{
            JSONArray jarray =new JSONArray(jsonString);

            FieldInstallationDB sqlitedb = new FieldInstallationDB(con);
            sqlitedb.deleteAllItems();
            sqlitedb.openForRead();



            for (int count = 0; count < jarray.length(); count++) {
                JSONObject obj = jarray.getJSONObject(count);
                itemName = obj.getString(TAG_NAME_ITEM);
                itemID = obj.getString(TAG_NUMBER_ITEM);

                equipment_full = itemName +" : " + itemID;

                ContentValues cv = new ContentValues();
                cv.put(FieldInstallationDB.ITEM_NAME, equipment_full);
                cv.put(FieldInstallationDB.ITEM_NUMBER, itemID);
                sqlitedb.fieldInstDatabase.insert(FieldInstallationDB.TABLE_EQUIPMENT, null, cv);
            }

            int counter = sqlitedb.getItemsCount();
            Log.i("Items", String.valueOf(counter));

            if(counter == jarray.length()){
                Toast.makeText(con, String.valueOf(counter)+" Equipments Saved to Database Successfully!", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(con, "FAILED!!!", Toast.LENGTH_SHORT).show();

            }

            sqlitedb.close();
        }
        catch (JSONException e){

            e.printStackTrace();
        }



    }



    //methods to handle get location button click start

    public void getLocation(View v) {
        drawMarker();

    }

    private void drawMarker() {

        boolean isGPSEnabled;
        Location location;

        //TODO


        // Get the LocationManager object from the System Service LOCATION_SERVICE
        //locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Create a criteria object needed to retrieve the provider
        Criteria criteria = new Criteria();

        // Get the name of the best available provider
        String provider = locationManager.getBestProvider(criteria, true);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isGPSEnabled) {
            // We can use the provider immediately to get the last known location
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                map.clear();
                //  convert the location object to a LatLng object that can be used by the map API
                LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

                locationCords.setText(currentPosition.toString());

                // zoom to the current location
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));

                // add a marker to the map indicating our current position
                map.addMarker(new MarkerOptions().position(currentPosition).snippet("Lat:" + location.getLatitude() + "Lng:" + location.getLongitude()));
                //isGPSEnabled = true;
            } else {
                Toast.makeText(this, "Could not get location", Toast.LENGTH_LONG).show();
                locationCords.setText("Location not available");

            }
        } else {

            showSettingsAlert();
        }

    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS Settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to enable it?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();

    }

    //methods to handle get location button click end


    //method to handle scan button click start
    public void scan(View view) {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();

    }

    //method to handle scan button click end

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == cameraData) {
            switch (counter) {
                case 0:
                    if (resultCode == RESULT_OK) {
                       /* Bundle extra = data.getExtras();
                        bmp1 = (Bitmap) extra.get("data");
                        instImg1.setImageBitmap(bmp1);*/
                        Uri uri = FileUri;
                        bmp1 = BitmapFactory.decodeFile(uri.getPath());
                        instImg1.setImageBitmap(bmp1);
                        survImg1.setImageBitmap(bmp1);
                        counter++;
                    }
                    break;

                case 1:
                    if (resultCode == RESULT_OK) {
                        /*Bundle extra = data.getExtras();
                        bmp1 = (Bitmap) extra.get("data");*/
                        Uri uri = FileUri;
                        bmp1 = BitmapFactory.decodeFile(uri.getPath());
                        instImg2.setImageBitmap(bmp1);
                        survImg2.setImageBitmap(bmp1);
                        counter++;
                    }
                    break;

                case 2:
                    if (resultCode == RESULT_OK) {
                       /* Bundle extra = data.getExtras();
                        bmp1 = (Bitmap) extra.get("data");*/
                        Uri uri = FileUri;
                        bmp1 = BitmapFactory.decodeFile(uri.getPath());
                        instImg3.setImageBitmap(bmp1);
                        survImg3.setImageBitmap(bmp1);
                        counter++;
                    }
                    break;
            }

        }

        if (requestCode == cameraDataSurv) {
            switch (count) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        /*Bundle extras = data.getExtras();
                        bmp = (Bitmap) extras.get("data");*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        survImg1.setImageBitmap(bmp);
                        count++;
                    }
                    break;

                case 1:
                    if (resultCode == RESULT_OK) {
                        /*Bundle extras = data.getExtras();
                        bmp = (Bitmap) extras.get("data");*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        survImg2.setImageBitmap(bmp);
                        count++;
                    }
                    break;

                case 2:
                    if (resultCode == RESULT_OK) {
                        /*Bundle extras = data.getExtras();
                        bmp = (Bitmap) extras.get("data");*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        survImg3.setImageBitmap(bmp);
                        count++;
                    }
                    break;

                case 3:
                    if (resultCode == RESULT_OK) {
                        /*Bundle extras = data.getExtras();
                        bmp = (Bitmap) extras.get("data");*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        survImg4.setImageBitmap(bmp);
                        count++;
                    }
                    break;

                case 4:
                    if (resultCode == RESULT_OK) {
                        /*Bundle extras = data.getExtras();
                        bmp = (Bitmap) extras.get("data");*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        survImg5.setImageBitmap(bmp);
                        count++;
                    }
                    break;

                case 5:
                    if (resultCode == RESULT_OK) {
                        /*Bundle extras = data.getExtras();
                        bmp = (Bitmap) extras.get("data");*/
                        Uri uri = FileUri;
                        bmp = BitmapFactory.decodeFile(uri.getPath());
                        survImg6.setImageBitmap(bmp);
                        count++;
                    }
                    break;

            }

        }


        if (requestCode == SIGNATURE_ACTIVITY) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Signature not captured!", Toast.LENGTH_LONG).show();
            } else {
                Bundle bundle = data.getExtras();
                String status = bundle.getString("status");
                if (status.equalsIgnoreCase("done")) {
                    Toast toast = Toast.makeText(this, "Signature capture successful!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                }

            }

        }

        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                serialNumber.setText(scanContent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement
        switch (item.getItemId())
        {
            case R.id.title_activity_settings:
                Intent intent = new Intent(this, Setting.class);
                startActivity(intent);
                break;
            case R.id.save_tickets:
                saverecordFinal();
                break;
            case R.id.get_customers:
                itemClicked = "customers";
                downloadFromERP download_customers = new downloadFromERP(getParent());
                download_customers.execute();

                Toast.makeText(this, "Customers successfully downloaded.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.get_equipment:
                itemClicked = "items";
                downloadFromERP download_items = new downloadFromERP(getParent());
                download_items.execute();

                Toast.makeText(this, "Items successfully downloaded.", Toast.LENGTH_SHORT).show();
                break;
            /*case R.id.testinteg1:
                checkInstallType = "testWEBservice";
                displayTxt(axcall.hellowWorld().toString());
                break;
            case R.id.testinteg2:
                checkInstallType = "testERP";
                axcall.TestERP("it");
                break;
            case R.id.create_csv:
                createCSV();
                break;*/
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayTxt(String _txt)
    {

        Toast.makeText(this,_txt.toString(),Toast.LENGTH_LONG);
    }

    public void createCSV(){
        createCSV createCSV = new createCSV();
        FieldInstallationDB csvData = new FieldInstallationDB(MainActivity.this);
        String result = csvData.getInfoForCSV();
        result = result + "\n\n\n" + csvData.getTaskForCSV() + "\n\n\n" + csvData.getSurveyForCSV() + "\n\n\n" + csvData.getSurveyTestForCSV();

        if(createCSV.createCSVFile(result))
        {
            sendCSV();
            Toast.makeText(this, "File Created", Toast.LENGTH_SHORT).show();

        }


    }

    // Returns the contents of the file in a byte array.
    public static byte[] getBytesFromFile() throws IOException {

        File file = new File("/sdcard/" + sysaidIdFileName + ".pdf");
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }





}
