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
 * Created by Andy on 2016/5/7.
 */
public class ConnectDB3 {
    public final String MY_JSON ="MY_JSON";

    public final String URL = "http://10.0.3.2:8000/mark_download/";

    public void sync() {
        getJSON(URL);
    }

    public static String[][] getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            String myJSONString;
            String[][] result = new String[50][50];
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
                    java.net.URL url = new URL(uri);
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
                    // System.out.println(myJSONString);
                    extractJSON();
                    System.out.print("DAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    return null;

                } catch (Exception e) {
                    return null;
                }
            }
            public String extractJSON() {
                try {
                    JSONArray jsonArr = new JSONArray(myJSONString);
                    System.out.print(jsonArr.length());
                    String[][] innresult = new String[jsonArr.length()][4];
                    try {
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject mark = jsonArr.getJSONObject(i);
                            String CLOSE_AT = mark.getString("close_at");
                            String CONTENT = mark.getString("content");
                            String CREATED_AT = mark.getString("created_at");
                            String LATITUDE = mark.getString("latitude");
                            String LOCATION = mark.getString("location");
                            String LONGITUDE = mark.getString("longitude");
                            String OPEN_AT = mark.getString("open_at");
                            String TITLE = mark.getString("title");
                            String UPDATED_AT = mark.getString("updated_at");
                            String ZIP = mark.getString("zip");

                            result[i][0] = TITLE;
                            result[i][1] = CONTENT;
                            result[i][2] = LATITUDE;
                            result[i][3] = LONGITUDE;
                        }
                        /**for(int i = 0; i < jsonArr.length(); i++) {
                            for(int j = 0; j <= 3; j++) {
                                if(j == 0){[i][j] = innresult[i][j];}
                                if(j == 1){result[i][j] = innresult[i][j];}
                                if(j == 2){result[i][j] = innresult[i][j];}
                                if(j == 3){result[i][j] = innresult[i][j];}
                            }
                        }*/
                        return null;
                    } catch (JSONException e) {
                        System.out.print("BAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        e.printStackTrace();
                        return null;
                    }
                } catch (JSONException e) {
                    System.out.print("CAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    e.printStackTrace();
                    return null;
                }
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
        System.out.print(gj.extractJSON());
        return gj.result;
    }
}
