package edu.nccumis.andy.maptest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "maps.sqlite";
    public SQLiteDatabase db;
    private static final int DB_VERSION = 1;
    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS MARKS");
        String sqlMap = "create table marks(id INTEGER PRIMARY KEY AUTOINCREMENT, mark_name text," +
                " mark_info text, latit numeric, longit numeric);";
        db.execSQL(sqlMap);

        String initMap = "insert into marks values(null, 'New Mark','Auto generated mark.', '25" +
                ".0329640', '121.5654270');";
        db.execSQL(initMap);

        //String[][] syncEle = ConnectDB2.getJSON("http://10.0.3.2:8000/mark_download/");
        //int l = syncEle.length;
        //if (syncEle != null){
            //db.execSQL("drop table if exists marks");
            //String renew = "create table marks(id INTEGER PRIMARY KEY AUTOINCREMENT, mark_name " +
            //        "text," +
            //        " mark_info text, latit numeric, longit numeric)";
         //   db.execSQL(renew);
        //    for(int i = 0; i < syncEle.length; i++) {
        //        insert(syncEle[i][0], syncEle[i][1], syncEle[i][2], syncEle[i][3]);
        //    }
       // }

    }

   /** public Cursor select()
    {
        String[] cols = {"id", "mark_name", "mark_info", "latit", "longit"};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("marks", cols, null,
            null, null,
            null,
            null);
        return cursor;
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists marks");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists marks");
        onCreate(db);
    }

    public long insert(String name, String info, String lati, String longi)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("mark_name", name);
        cv.put("mark_info", info);
        cv.put("latit", lati);
        cv.put("longit", longi);
        long row = db.insert("marks", null, cv);
        return row;
    }
}


/**
 * Created by Andy on 2016/5/7.
 */
/**class ConnectDB2{
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
                            for(int j = 0; j <= 3; j++) {
                                if(j == 0){innresult[i][j] = TITLE;}
                                if(j == 1){innresult[i][j] = CONTENT;}
                                if(j == 2){innresult[i][j] = LATITUDE;}
                                if(j == 3){innresult[i][j] = LONGITUDE;}
                            }
                        }
                    for(int i = 0; i < jsonArr.length(); i++) {
                        for(int j = 0; j <= 3; j++) {
                            if(j == 0){result[i][j] = innresult[i][j];}
                            if(j == 1){result[i][j] = innresult[i][j];}
                            if(j == 2){result[i][j] = innresult[i][j];}
                            if(j == 3){result[i][j] = innresult[i][j];}
                        }
                    }
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
        return gj.result;
    }

}**/

