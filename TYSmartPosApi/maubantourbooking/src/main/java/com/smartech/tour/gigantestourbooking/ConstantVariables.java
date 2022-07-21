package com.smartech.tour.gigantestourbooking;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class ConstantVariables {
    public static final String baseuserltest ="https://gigantes.smartpay.ph/gigantesphp/";
    //public static final String baseuserltest ="http://localhost:80/tourbookingphp/";
    public static final String baseuserllive ="https://gigantes.smartpay.ph//gigantesphp/";

    public String getCurrenturl() {
        return currenturl;
    }

    public void setCurrenturl(String currenturl) {
        this.currenturl = currenturl;
    }

    public String currenturl="";
    ConstantVariables(){
        setCurrenturl(baseuserllive);

    }

    public String funPostData(JSONObject objJsonArray,String Actionnname) {
        String strResponce = "";
        HttpURLConnection conn;
        try {
             URL url = new URL(currenturl+Actionnname);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(15000);
            String strStringMessage = objJsonArray.toString();
            OutputStream os = conn.getOutputStream();
            os.write(strStringMessage.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                BufferedReader brerr = new BufferedReader(new InputStreamReader(
                        (conn.getErrorStream())));
                String erroutput;
                StringBuffer outputbuffererr = new StringBuffer();

                while ((erroutput = brerr.readLine()) != null) {
                    outputbuffererr.append(erroutput);
                }
                brerr.close();
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            StringBuffer outputbuffer = new StringBuffer();

            while ((output = br.readLine()) != null) {
                outputbuffer.append(output);
            }
            br.close();
            strResponce = outputbuffer.toString();
            conn.disconnect();

        } catch (SocketTimeoutException ex) {
            strResponce = "";
            //conn.disconnect();

        } catch (Exception e) {
            Log.e("My App", e.getMessage());
        } finally {
            // conn.disconnect();
        }

        return strResponce;
    }
}
