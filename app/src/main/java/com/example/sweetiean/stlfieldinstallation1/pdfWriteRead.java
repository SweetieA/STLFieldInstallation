package com.example.sweetiean.stlfieldinstallation1;

import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by sweetiean on 4/28/2015.
 */
public class pdfWriteRead {
    public pdfWriteRead(){}

    public Boolean writetask(String sysaid,String itemsarray) {
        try {
            String fpath = "/sdcard/" + sysaid + ".pdf";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }



            // step 1
            Document document = new Document();
            // step 2
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));

            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLDITALIC|Font.UNDERLINE);

            // step 3
            document.open();

            // step 4
            Paragraph paragraph1 = new Paragraph("FIELD INSTALLATION FORM", font2);
            paragraph1.setSpacingBefore(25);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setIndentationLeft(50);
            paragraph1.setIndentationRight(50);

            Paragraph paragraph2 = new Paragraph(Utility.getTodaysDate(), font2);
            paragraph2.setSpacingAfter(25);
            //paragraph2.setSpacingBefore(25);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            paragraph2.setIndentationLeft(50);
            paragraph2.setIndentationRight(50);
            //document.add(new Paragraph(Utility.getTodaysDate(), font1).setAlignment(Element.ALIGN_CENTER););
            document.add(paragraph1); document.add(paragraph2);
            document.add(new Paragraph("Sysaid ID: " + MainActivity.sysaidId.getText().toString(), font1));
            document.add(new Paragraph("Site ID: " + MainActivity.siteId.getText().toString(), font1));
            document.add(new Paragraph("Customer Name: " + MainActivity.customerName.getText().toString(), font1));
            document.add(new Paragraph("Site Contact: " + MainActivity.siteContact.getText().toString(), font1));
            document.add(new Paragraph("Task Type: " + MainActivity.taskSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Address: " + MainActivity.address.getText().toString(), font1));
            document.add(new Paragraph("Region: " + MainActivity.regionSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Phone: " + MainActivity.phone.getText().toString(), font1));
            document.add(new Paragraph("Fax: " + MainActivity.fax.getText().toString(), font1));
            document.add(new Paragraph("Mobile: " + MainActivity.mobile.getText().toString(), font1));
            document.add(new Paragraph("E-mail: " + MainActivity.email.getText().toString(), font1));
            document.add(new Paragraph("Landlord Name: " + MainActivity.landlordName.getText().toString(), font1));
            document.add(new Paragraph("Rent Status: " + MainActivity.rentStatusSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Location Cordinates: " + MainActivity.locationCords.getText().toString(), font1));
            document.add(new Paragraph("Engineer Name: " + MainActivity.engineer_name, font1));
            document.add(new Paragraph(" "));

            document.add(new Paragraph(itemsarray, font1));


            //document.add(new Paragraph("Equipment: " + MainActivity.equipment.getText().toString(), font1));
            //document.add(new Paragraph("Serial Number: " + MainActivity.serialNumber.getText().toString(), font1));
            //document.add(new Paragraph("Quantity: " + MainActivity.quantity.getText().toString(), font1));
            //document.add(new Paragraph("Checked: " + MainActivity.check.getText().toString(), font1));
            //document.add(new Paragraph("Remarks: " + MainActivity.remarks.getText().toString(), font1));
            //document.add(new Paragraph("Old Serial Number: " + MainActivity.oldSerialNumber.getText().toString(), font1));

            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+0+".jpg";
            Image img = Image.getInstance(filePath);
            img.scaleToFit(150, 150);
            document.add(img);

            String filePath1 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+1+".jpg";
            Image img1 = Image.getInstance(filePath1);
            img1.scaleToFit(150,150);
            document.add(img1);

            String filePath2 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+2+".jpg";
            Image img2 = Image.getInstance(filePath2);
            img2.scaleToFit(150,150);
            document.add(img2);

            String filePath3 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+3+".jpg";
            Image img3 = Image.getInstance(filePath3);
            img3.scaleToFit(150,150);
            document.add(img3);

            String filePath4 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+4+".jpg";
            Image img4 = Image.getInstance(filePath4);
            img4.scaleToFit(150,150);
            document.add(img4);

            String filePath5 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+5+".jpg";
            Image img5 = Image.getInstance(filePath5);
            img5.scaleToFit(150,150);
            document.add(img5);

            /*String imageUrl = MainActivity.instImg1.setImageURI();

            Image image = Image.getInstance(new URL(imageUrl));
            image.setAbsolutePosition(500f, 650f);
            document.add(image);*/

            //document.add(new Paragraph("Images: " + Utility.getBytes(((BitmapDrawable) MainActivity.instImg1.getDrawable()).getBitmap()) + " " + MainActivity.instImg2.getDrawable() + " " + MainActivity.instImg3.getDrawable()));

            document.add(new Paragraph("Engineer Signature: ", font1));
            String filePathSign = SignatureActivity.tempDir + "/" +
                    MainActivity.engineer_sign;
            Image imgSign = Image.getInstance(filePathSign);
            imgSign.scaleToFit(150,150);
            document.add(imgSign);

            // step 5
            document.close();

            Log.d("Success", "Success");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }


    public Boolean writesurvey(String sysaid, String taskArray) {
        try {
            String fpath = "/sdcard/" + sysaid + ".pdf";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }


            // step 1
            Document document = new Document();
            // step 2
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));

            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLDITALIC|Font.UNDERLINE);

            // step 3
            document.open();


            // step 4
            Paragraph paragraph1 = new Paragraph("FIELD INSTALLATION FORM", font2);
            paragraph1.setSpacingBefore(25);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setIndentationLeft(50);
            paragraph1.setIndentationRight(50);

            Paragraph paragraph2 = new Paragraph(Utility.getTodaysDate(), font2);
            paragraph2.setSpacingAfter(25);
            //paragraph2.setSpacingBefore(25);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            paragraph2.setIndentationLeft(50);
            paragraph2.setIndentationRight(50);

            document.add(paragraph1); document.add(paragraph2);
            document.add(new Paragraph("Sysaid ID: " + MainActivity.sysaidId.getText().toString(), font1));
            document.add(new Paragraph("Site ID: " + MainActivity.siteId.getText().toString(), font1));
            document.add(new Paragraph("Customer Name: " + MainActivity.customerName.getText().toString(), font1));
            document.add(new Paragraph("Site Contact: " + MainActivity.siteContact.getText().toString(), font1));
            document.add(new Paragraph("Task Type: " + MainActivity.taskSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Address: " + MainActivity.address.getText().toString(), font1));
            document.add(new Paragraph("Region: " + MainActivity.regionSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Phone: " + MainActivity.phone.getText().toString(), font1));
            document.add(new Paragraph("Fax: " + MainActivity.fax.getText().toString(), font1));
            document.add(new Paragraph("Mobile: " + MainActivity.mobile.getText().toString(), font1));
            document.add(new Paragraph("E-mail: " + MainActivity.email.getText().toString(), font1));
            document.add(new Paragraph("Landlord Name: " + MainActivity.landlordName.getText().toString(), font1));
            document.add(new Paragraph("Rent Status: " + MainActivity.rentStatusSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Location Cordinates: " + MainActivity.locationCords.getText().toString(), font1));
            document.add(new Paragraph("Engineer Name: " + MainActivity.engineer_name, font1));
            document.add(new Paragraph(" "));


            document.add(new Paragraph("AC Main Power Status: " + MainActivity.powerStatusSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Voltage Measurement: " + MainActivity.voltage.getText().toString(), font1));
            document.add(new Paragraph("Air Condition : " + MainActivity.airconSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Server Room Status: " + MainActivity.serverRoom.getText().toString(), font1));
            document.add(new Paragraph("Location of IDU: " + MainActivity.locationIdu.getText().toString(), font1));
            document.add(new Paragraph("Earthing: " + MainActivity.earthingSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Distance IDU: " + MainActivity.distanceIdu.getText().toString(), font1));
            document.add(new Paragraph("Security of Cables: " + MainActivity.security.getText().toString(), font1));
            document.add(new Paragraph("Ducts: " + MainActivity.ductsSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Unit to be Installed: " + MainActivity.unitSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Installation Type: " + MainActivity.installationTypeSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("Other ISP: " + MainActivity.ispSpinner.getSelectedItem().toString(), font1));
            //document.add(new Paragraph("ISP Type: " + MainActivity.radioTypeSpinner.getSelectedItem().toString(), font1));
            document.add(new Paragraph("ISP Name: " + MainActivity.ispName.getText().toString(), font1));
            //document.add(new Paragraph("SNR(RX): " + MainActivity.snrRX.getText().toString(), font1));
            //document.add(new Paragraph("Base Station: " + MainActivity.bs.getText().toString(), font1));

            document.add(new Paragraph(taskArray, font1));

            document.add(new Paragraph("Remarks: " + MainActivity.remark.getText().toString(), font1));

            //adding images
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+0+".jpg";
            Image img = Image.getInstance(filePath);
            img.scaleToFit(150, 150);
            document.add(img);

            String filePath1 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+1+".jpg";
            Image img1 = Image.getInstance(filePath1);
            img1.scaleToFit(150,150);
            document.add(img1);

            String filePath2 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+2+".jpg";
            Image img2 = Image.getInstance(filePath2);
            img2.scaleToFit(150,150);
            document.add(img2);

            String filePath3 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+3+".jpg";
            Image img3 = Image.getInstance(filePath3);
            img3.scaleToFit(150,150);
            document.add(img3);

            String filePath4 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+4+".jpg";
            Image img4 = Image.getInstance(filePath4);
            img4.scaleToFit(150,150);
            document.add(img4);

            String filePath5 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                    MainActivity.sysaidId.getText().toString()+"surv_"+5+".jpg";
            Image img5 = Image.getInstance(filePath5);
            img5.scaleToFit(150,150);
            document.add(img5);

            //document.add(new Chunk(img, img1, img2));


            document.add(new Paragraph("Engineer Signature: ", font1));
            String filePathSign = SignatureActivity.tempDir + "/" +
                    MainActivity.engineer_sign;
            Image imgSign = Image.getInstance(filePathSign);
            imgSign.scaleToFit(150, 150);
            document.add(imgSign);
            // step 5
            document.close();

            Log.d("Success", "Success");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }


    public String read(String sysaid) {
        BufferedReader br = null;
        String response = null;
        try {
            StringBuffer output = new StringBuffer();
            String fpath = "/sdcard/" + sysaid + ".pdf";

            PdfReader reader = new PdfReader(new FileInputStream(fpath));
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);

            StringWriter strW = new StringWriter();

            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                strategy = parser.processContent(i,
                        new SimpleTextExtractionStrategy());

                strW.write(strategy.getResultantText());

            }

            response = strW.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }
    
}
