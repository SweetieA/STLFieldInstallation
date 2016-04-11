package com.example.sweetiean.stlfieldinstallation1;

/**
 * Created by perja on 8/18/14.
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService {
    static Context c;
    //Namespace of the Webservice - It is http://tempuri.org for .NET webservice
    private static String NAMESPACE = "http://tempuri.org/";
    //Webservice URL - It is asmx file location hosted in the server in case of .Net
    //Change the IP address to your machine IP address
    //private static String URL = "http://192.168.251.209/taskservice/uploaddata.asmx"; // new address added
    private static String URL = MainActivity.webaddress;
    //SOAP Action URI again http://tempuri.org
    private static String SOAP_ACTION = "http://tempuri.org/";








    public static String TestERP(String _companyName) {
        String resTxt = null;
        String webMethName="TestERP";
        // Create request


        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Property which holds input parameters

        PropertyInfo P1 = new PropertyInfo();
        P1.setName("_companyId");// Set Value
        P1.setValue(_companyName);
        P1.setType(String.class);
        request.addProperty(P1);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }





    public static String SendFile(byte[] _data, String _fileName) {
        String resTxt = null;

        String webMethName="UploadFile";
        // Create request


        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        MarshalBase64 marshal = new MarshalBase64();


        PropertyInfo P1 = new PropertyInfo();
        P1.setName("fileName");
        P1.setValue(_fileName);
        P1.setType(String.class);
        request.addProperty(P1);

        PropertyInfo p2=new PropertyInfo();
        p2.setName("f");
        p2.setType(_data);
        p2.setValue(_data);
        request.addProperty(p2);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        marshal.register(envelope);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }


    public static String hellowWorld() {
        String resTxt = null;
        String webMethName="HelloWorld";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }



    public static String pushToAx(String _result,String _Imei) {
        String resTxt = null;
        String webMethName="pushToAx";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo P1 = new PropertyInfo();
        PropertyInfo P2 = new PropertyInfo();
        // Set Name
        P1.setName("_result");
        // Set Value
        P1.setValue(_result);
        P1.setType(String.class);
        request.addProperty(P1);

        P2.setName("_Imei");
        // Set Value
        P2.setValue(_Imei);
        // Set dataType
        P2.setType(String.class);
        // Add the property to request object
        request.addProperty(P2);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }


    public static String pushInfoToAx(String _sysaid,String _date,String _site,String _customerName,String _siteContact,String _task,String _address,
                                      String _region,String _phone,String _fax,String _mobile,String _mail,String _landlordName,String _rent,
                                      String _location,String _engineerName,String _Imei) {
        String resTxt = null;
        String webMethName="pushInfoToAx";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo P1 = new PropertyInfo();
        PropertyInfo P2 = new PropertyInfo();
        PropertyInfo P3 = new PropertyInfo();
        PropertyInfo P4 = new PropertyInfo();
        PropertyInfo P5 = new PropertyInfo();
        PropertyInfo P6 = new PropertyInfo();
        PropertyInfo P7 = new PropertyInfo();
        PropertyInfo P8 = new PropertyInfo();
        PropertyInfo P9 = new PropertyInfo();
        PropertyInfo P10 = new PropertyInfo();
        PropertyInfo P11 = new PropertyInfo();
        PropertyInfo P12 = new PropertyInfo();
        PropertyInfo P13 = new PropertyInfo();
        PropertyInfo P14 = new PropertyInfo();
        PropertyInfo P15 = new PropertyInfo();
        PropertyInfo P16 = new PropertyInfo();
        PropertyInfo P17 = new PropertyInfo();



        P1.setName("_sysaid");
        P1.setValue(_sysaid);
        P1.setType(String.class);
        request.addProperty(P1);


        P2.setName("_date");
        P2.setValue(_date);
        P2.setType(String.class);
        request.addProperty(P2);


        P3.setName("_site");
        P3.setValue(_site);
        P3.setType(String.class);
        request.addProperty(P3);


        P4.setName("_customerName");
        P4.setValue(_customerName);
        P4.setType(String.class);
        request.addProperty(P4);


        P5.setName("_siteContact");
        P5.setValue(_siteContact);
        P5.setType(String.class);
        request.addProperty(P5);


        P6.setName("_task");
        P6.setValue(_task);
        P6.setType(String.class);
        request.addProperty(P6);


        P7.setName("_address");
        P7.setValue(_address);
        P7.setType(String.class);
        request.addProperty(P7);


        P8.setName("_region");
        P8.setValue(_region);
        P8.setType(String.class);
        request.addProperty(P8);


        P9.setName("_phone");
        P9.setValue(_phone);
        P9.setType(String.class);
        request.addProperty(P9);


        P10.setName("_fax");
        P10.setValue(_fax);
        P10.setType(String.class);
        request.addProperty(P10);


        P11.setName("_mobile");
        P11.setValue(_mobile);
        P11.setType(String.class);
        request.addProperty(P11);


        P12.setName("_mail");
        P12.setValue(_mail);
        P12.setType(String.class);
        request.addProperty(P12);


        P13.setName("_landlordName");
        P13.setValue(_landlordName);
        P13.setType(String.class);
        request.addProperty(P13);


        P14.setName("_rent");
        P14.setValue(_rent);
        P14.setType(String.class);
        request.addProperty(P14);


        P15.setName("_location");
        P15.setValue(_location);
        P15.setType(String.class);
        request.addProperty(P15);


        P16.setName("_engineerName");
        P16.setValue(_engineerName);
        P16.setType(String.class);
        request.addProperty(P16);


        P17.setName("_Imei");
        P17.setValue(_Imei);
        P17.setType(String.class);
        request.addProperty(P17);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }


    public static String pushSurveyToAx(String _sysaid,String _power,String _voltage,String _aircon,String _serverRoom,String _locationIdu,String _earth,
                                      String _distanceIdu,String _securityCables,String _ducts,String _unit,String _installationType,String _isp,String _ispName,
                                      String _remarks) {
        String resTxt = null;
        String webMethName="pushSurveyToAx";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo P1 = new PropertyInfo();
        PropertyInfo P2 = new PropertyInfo();
        PropertyInfo P3 = new PropertyInfo();
        PropertyInfo P4 = new PropertyInfo();
        PropertyInfo P5 = new PropertyInfo();
        PropertyInfo P6 = new PropertyInfo();
        PropertyInfo P7 = new PropertyInfo();
        PropertyInfo P8 = new PropertyInfo();
        PropertyInfo P9 = new PropertyInfo();
        PropertyInfo P10 = new PropertyInfo();
        PropertyInfo P11 = new PropertyInfo();
        PropertyInfo P12 = new PropertyInfo();
        PropertyInfo P13 = new PropertyInfo();
        PropertyInfo P14 = new PropertyInfo();
        PropertyInfo P15 = new PropertyInfo();


        // Set Name
        P1.setName("_sysaid");
        // Set Value
        P1.setValue(_sysaid);
        P1.setType(String.class);
        request.addProperty(P1);

        // Set Name
        P2.setName("_power");
        // Set Value
        P2.setValue(_power);
        P2.setType(String.class);
        request.addProperty(P2);

        // Set Name
        P3.setName("_voltage");
        // Set Value
        P3.setValue(_voltage);
        P3.setType(String.class);
        request.addProperty(P3);

        // Set Name
        P4.setName("_aircon");
        // Set Value
        P4.setValue(_aircon);
        P4.setType(String.class);
        request.addProperty(P4);

        // Set Name
        P5.setName("_serverRoom");
        // Set Value
        P5.setValue(_serverRoom);
        P5.setType(String.class);
        request.addProperty(P5);

        // Set Name
        P6.setName("_locationIdu");
        // Set Value
        P6.setValue(_locationIdu);
        P6.setType(String.class);
        request.addProperty(P6);

        // Set Name
        P7.setName("_earth");
        // Set Value
        P7.setValue(_earth);
        P7.setType(String.class);
        request.addProperty(P7);

        // Set Name
        P8.setName("_distanceIdu");
        // Set Value
        P8.setValue(_distanceIdu);
        P8.setType(String.class);
        request.addProperty(P8);

        // Set Name
        P9.setName("_securityCables");
        // Set Value
        P9.setValue(_securityCables);
        P9.setType(String.class);
        request.addProperty(P9);

        // Set Name
        P10.setName("_ducts");
        // Set Value
        P10.setValue(_ducts);
        P10.setType(String.class);
        request.addProperty(P10);

        // Set Name
        P11.setName("_unit");
        // Set Value
        P11.setValue(_unit);
        P11.setType(String.class);
        request.addProperty(P11);

        // Set Name
        P12.setName("_installationType");
        // Set Value
        P12.setValue(_installationType);
        P12.setType(String.class);
        request.addProperty(P12);

        // Set Name
        P13.setName("_isp");
        // Set Value
        P13.setValue(_isp);
        P13.setType(String.class);
        request.addProperty(P13);

        // Set Name
        P14.setName("_ispName");
        // Set Value
        P14.setValue(_ispName);
        P14.setType(String.class);
        request.addProperty(P14);

        // Set Name
        P15.setName("_remarks");
        // Set Value
        P15.setValue(_remarks);
        P15.setType(String.class);
        request.addProperty(P15);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }

    public static String pushSurveyTestToAx(String _sysaid,String _radioType,String _signalRx,String _signalTx,String _signalRtx,String _signalRssi,String _signalCinr,
                                        String _baseStation) {
        String resTxt = null;
        String webMethName="pushSurveyTestToAx";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo P1 = new PropertyInfo();
        PropertyInfo P2 = new PropertyInfo();
        PropertyInfo P3 = new PropertyInfo();
        PropertyInfo P4 = new PropertyInfo();
        PropertyInfo P5 = new PropertyInfo();
        PropertyInfo P6 = new PropertyInfo();
        PropertyInfo P7 = new PropertyInfo();
        PropertyInfo P8 = new PropertyInfo();


        // Set Name
        P1.setName("_sysaid");
        // Set Value
        P1.setValue(_sysaid);
        P1.setType(String.class);
        request.addProperty(P1);

        // Set Name
        P2.setName("_radioType");
        // Set Value
        P2.setValue(_radioType);
        P2.setType(String.class);
        request.addProperty(P2);

        // Set Name
        P3.setName("_signalRx");
        // Set Value
        P3.setValue(_signalRx);
        P3.setType(String.class);
        request.addProperty(P3);

        // Set Name
        P4.setName("_signalTx");
        // Set Value
        P4.setValue(_signalTx);
        P4.setType(String.class);
        request.addProperty(P4);

        // Set Name
        P5.setName("_signalRtx");
        // Set Value
        P5.setValue(_signalRtx);
        P5.setType(String.class);
        request.addProperty(P5);

        // Set Name
        P6.setName("_signalRssi");
        // Set Value
        P6.setValue(_signalRssi);
        P6.setType(String.class);
        request.addProperty(P6);

        // Set Name
        P7.setName("_signalCinr");
        // Set Value
        P7.setValue(_signalCinr);
        P7.setType(String.class);
        request.addProperty(P7);

        // Set Name
        P8.setName("_baseStation");
        // Set Value
        P8.setValue(_baseStation);
        P8.setType(String.class);
        request.addProperty(P8);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }

    public static String pushTaskToAx(String _sysaid,String _task,String _equipment,String _serial,String _quantity,String _checked,String _oldSerial,
                                            String _remarks) {
        String resTxt = null;
        String webMethName="pushTaskToAx";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo P1 = new PropertyInfo();
        PropertyInfo P2 = new PropertyInfo();
        PropertyInfo P3 = new PropertyInfo();
        PropertyInfo P4 = new PropertyInfo();
        PropertyInfo P5 = new PropertyInfo();
        PropertyInfo P6 = new PropertyInfo();
        PropertyInfo P7 = new PropertyInfo();
        PropertyInfo P8 = new PropertyInfo();


        // Set Name
        P1.setName("_sysaid");
        // Set Value
        P1.setValue(_sysaid);
        P1.setType(String.class);
        request.addProperty(P1);

        // Set Name
        P2.setName("_task");
        // Set Value
        P2.setValue(_task);
        P2.setType(String.class);
        request.addProperty(P2);

        // Set Name
        P3.setName("_equipment");
        // Set Value
        P3.setValue(_equipment);
        P3.setType(String.class);
        request.addProperty(P3);

        // Set Name
        P4.setName("_serial");
        // Set Value
        P4.setValue(_serial);
        P4.setType(String.class);
        request.addProperty(P4);

        // Set Name
        P5.setName("_quantity");
        // Set Value
        P5.setValue(_quantity);
        P5.setType(String.class);
        request.addProperty(P5);

        // Set Name
        P6.setName("_checked");
        // Set Value
        P6.setValue(_checked);
        P6.setType(String.class);
        request.addProperty(P6);

        // Set Name
        P7.setName("_oldSerial");
        // Set Value
        P7.setValue(_oldSerial);
        P7.setType(String.class);
        request.addProperty(P7);

        // Set Name
        P8.setName("_remarks");
        // Set Value
        P8.setValue(_remarks);
        P8.setType(String.class);
        request.addProperty(P8);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String customers() {
        String resTxt = null;
        String webMethName="downloadCustomerInfo";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            Object response = envelope.getResponse();
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
            //Assign error message to resTxt
            //resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String equipment() {
        String resTxt = null;
        String webMethName="downloadEquipmentInfo";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            Object response = envelope.getResponse();
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            resTxt = e.toString();
        }
        //Return resTxt to calling object
        return resTxt;

    }

}
