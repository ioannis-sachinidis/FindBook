package com.example.findbook;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.example.findbook.BuildConfig.apikey;



public class LocationService //klasi pou ektelei tin ipiresia sindesis me ti google,to parsarisma twn dedomenwn kai ti metafora tous sto MapActivity pros emfanisi
{

    private static final String TAG = LocationService.class.getSimpleName();

    private static final String URL      = "https://maps.googleapis.com/maps/api/place";
    private static final String API_TYPE = "/findplacefromtext";
    private static final String OUTPUT   = "/json";
    private static final String KEY      = apikey;


    public LocationClass getLocationFromAPI(String input) {
        LocationClass geolocation = new LocationClass ();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //tis 2 parakatw entoles tis kalesa gia na mporesw na sindethw me ti google.TO android me empodize.
        StrictMode.setThreadPolicy(policy);


        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(URL + API_TYPE + OUTPUT);
            sb.append("?key=" + KEY);
            sb.append("&inputtype=textquery&fields=formatted_address,geometry");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Api Error", e);
//            return resultList1;
        } catch (IOException e) {
            Log.e(TAG, "Connection Error", e);
//            return "Error Connection";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        try {
            JSONObject Object = new JSONObject(jsonResults.toString ());
            JSONArray CandidaArray = Object.getJSONArray("candidates");
            for (int i = 0; i < CandidaArray.length(); i++) {
                JSONObject geometryObject = CandidaArray.getJSONObject(i).getJSONObject ("geometry");
                JSONObject locationlObj   = geometryObject.getJSONObject ("location");
                String address = CandidaArray.getJSONObject(i).getString ("formatted_address");

                String lat = locationlObj.getString ("lat");
                String lng = locationlObj.getString ("lng");

                geolocation.setAddress (address + " LAT" + lat + " LNG" + lng);
                geolocation.setLat (Float.parseFloat (lat));
                geolocation.setLng (Float.parseFloat (lng));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Cannot Parse Json", e);
        }

        return geolocation;
    }

}

