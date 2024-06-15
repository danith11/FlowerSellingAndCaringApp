package com.s22010334.finalproject.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//helper class for Map Page
public class DownloadUrl {
    public String ReadTheURL(String placeURL) throws IOException {
        String Data = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(placeURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
// Check if the connection was successful before proceeding
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
// Reading data
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
// Reading each line
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
// Converting StringBuilder to a String
                Data = stringBuilder.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
// Close resources properly in the finally block
            if (inputStream != null) {
                inputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return Data;
    }
}
        //Create a HTTP connection to communicate with the URL
//        String urlData ="";
//        HttpURLConnection httpURLConnection = null;
//        InputStream inputStream = null ;


//        try{
//            URL getUrl = new URL(url);
//            httpURLConnection = (HttpURLConnection) getUrl.openConnection();
//            httpURLConnection.connect();
//
//            inputStream = httpURLConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder stringBuffer = new StringBuilder();
//
//            String line = "";
//
//            while ((line = bufferedReader.readLine())!= null){
//                stringBuffer.append(line);
//            }
//            urlData = stringBuffer.toString();
//            bufferedReader.close();
//
//        } catch (Exception e){
//            Log.d("Exception",e.toString());
//        }finally {
//            inputStream.close();
//            httpURLConnection.disconnect();
//        }
//        return urlData;
//    }
//    String Data = "";
//    InputStream inputStream = null;
//    HttpURLConnection httpURLConnection = null;
//try {
//        URL url = new URL(placeURL);
//        httpURLConnection = (HttpURLConnection) url.openConnection();
//        httpURLConnection.connect();
//// Check if the connection was successful before proceeding
//        int responseCode = httpURLConnection.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            inputStream = httpURLConnection.getInputStream();
//// Reading data
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder stringBuilder = new StringBuilder();
//// Reading each line
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//// Converting StringBuilder to a String
//            Data = stringBuilder.toString();
//        }
//    } catch (MalformedURLException e) {
//        e.printStackTrace();
//    } catch (IOException e) {
//        e.printStackTrace();
//    } finally {
//// Close resources properly in the finally block
//        if (inputStream != null) {
//            inputStream.close();
//        }
//        if (httpURLConnection != null) {
//            httpURLConnection.disconnect();
//        }
//    }
//return Data;
//}

