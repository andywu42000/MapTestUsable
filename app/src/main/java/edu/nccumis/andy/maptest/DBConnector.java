package edu.nccumis.andy.maptest;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Andy on 2016/5/6.
 */
public class DBConnector {

    public static final String MY_JSON ="MY_JSON";

    private static final String JSON_URL = "http://10.0.3.2:8000/mark_download/";

    public static void sync() {
        getJSON(JSON_URL);
    }

    private static void getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            String myJSONString;
            final String JSON_ARRAY ="result";
            final String CLOSE_AT = "close_at";
            final String CONTENT = "content";
            final String CREATED_AT = "created_at";
            final String LATITUDE = "latitude";
            final String LOCATION = "location";
            final String LONGITUDE = "longitude";
            final String OPEN_AT = "open_at";
            final String TITLE = "title";
            final String UPDATED_AT = "updated_at";
            final String ZIP = "zip";
            JSONArray marks = null;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream
                            (), "utf-8"));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    myJSONString = sb.toString();
                    myJSONString = myJSONString.replace("\"", "");
                    myJSONString = myJSONString.replace("\\", "\"");
                    //myJSONString = myJSONString.replace("\\\"", "\'");
                    //myJSONString = myJSONString.replace("[", "");
                    //myJSONString = myJSONString.replace("]", "");
                    //myJSONString = myJSONString.replace("{", "");
                    //myJSONString = myJSONString.replace("}", "");
                    System.out.println(myJSONString);
                    extractJSON();
                    return null;

                } catch (Exception e) {
                    return null;
                }
            }
            private void extractJSON() {
                try {
                    //JSONObject jsonObj = new JSONObject(myJSONString);
                    JSONArray jsonArr = new JSONArray(myJSONString);

                    try {
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject marks = jsonArr.getJSONObject(i);
                            System.out.println(marks.getString(TITLE));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }
}
