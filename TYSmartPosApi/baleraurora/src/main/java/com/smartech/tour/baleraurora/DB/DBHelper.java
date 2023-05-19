package com.smartech.tour.baleraurora.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.smartech.tour.baleraurora.module.accessRole;
import com.smartech.tour.baleraurora.module.device;
import com.smartech.tour.baleraurora.module.user;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tourbooking";
    //region user
    public static final String USER_TABLE_NAME = "User";
    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_LASTVISTED = "lastvisted";
    //endregion
    //region device setting
    public static final String DEVICE_TABLE_NAME = "device";
    public static final String DEVICE_COLUMN_DEVICEID = "deviceid";
    public static final String DEVICE_COLUMN_PORT = "port";
    public static final String DEVICE_COLUMN_CONNECTED = "connecteddb";
    public static final String DEVICE_COLUMN_ISSYNC = "issync";

    //endregion

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table User " +
                        "(username text,lastvisted text,accessrole text,roleid text)"
        );
        db.execSQL(
                "create table " + DEVICE_TABLE_NAME+
                        "(deviceid text primary key,port text,connecteddb text,issync bit)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)

    //region Uset
    public boolean insertUser (String username,String accessrole,String roleid) {


        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + USER_TABLE_NAME);
        db.execSQL("VACUUM");
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("accessrole", accessrole);
        contentValues.put("roleid", roleid);
        contentValues.put("lastvisted", String.valueOf(new Date()));
        Long res1= db.insert("User", null, contentValues);
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList getAllUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<user> array_list = new ArrayList<>();
        boolean table =doesTableExist(USER_TABLE_NAME);
        Cursor cursor = null;
        String Query ="select * from "+USER_TABLE_NAME;
        Cursor res = db.rawQuery( "select * from "+USER_TABLE_NAME, null );

        res.moveToFirst();
        while(res.isAfterLast() == false) {
            try {user u=new user();
                u.setUserName(res.getString(res.getColumnIndex("username")));
                u.setRoleid(res.getString(res.getColumnIndex("roleid")));
                String str = res.getString(res.getColumnIndex("lastvisted"));
                String _roles=res.getString(res.getColumnIndex("accessrole"));
                Gson gson = new Gson();
                ArrayList<accessRole> accessRolesarr=new ArrayList<>();
                JSONArray objaccdata=new JSONArray(_roles);
                for (int i = 0; i <objaccdata.length() ; i++) {
                    JSONObject objrole=objaccdata.getJSONObject(i);
                    String s="";
                    accessRole _accessRole=new accessRole();
                    _accessRole.setID(objrole.optString("ID"));
                    _accessRole.setDesc(objrole.optString("desc"));
                    accessRolesarr.add(_accessRole);
                }


                        //gson.fromJson(_roles, accessRole.class);

                u.setAccessRole(accessRolesarr);
                Date date=null;
                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
                // String temp = "Thu Dec 17 15:37:43 GMT+05:30 2015";
                try {
                    date = formatter.parse(str);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                u.setLastlogin(date);
                array_list.add(u);
                res.moveToNext();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return array_list;
    }
    //endregion

    //region Device setting
    public boolean insertdevice (String deviceid,String selectedport,String connectedtoport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_COLUMN_DEVICEID,deviceid );
        contentValues.put(DEVICE_COLUMN_PORT, selectedport);
        contentValues.put(DEVICE_COLUMN_CONNECTED, connectedtoport);
        contentValues.put(DEVICE_COLUMN_ISSYNC, 0);
        long s= db.insert(DEVICE_TABLE_NAME, String.valueOf(SQLiteDatabase.CONFLICT_REPLACE), contentValues);
        return true;
    }
    public boolean updatedevice (String deviceid,String selectedport,String connectedtoport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_COLUMN_CONNECTED,connectedtoport );
        contentValues.put(DEVICE_COLUMN_PORT, selectedport);
        contentValues.put(DEVICE_COLUMN_ISSYNC, 0);
        db.update(DEVICE_TABLE_NAME, contentValues, "deviceid = ?", new String[]{deviceid});
        return true;
    }
    public ArrayList getdevicedetail() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<device> array_list = new ArrayList<>();
        boolean table =doesTableExist(DEVICE_TABLE_NAME);
        Cursor res = db.rawQuery( "select * from "+DEVICE_TABLE_NAME, null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            try {
                device objdevice=new device();
                objdevice.setDeviceID(res.getString(res.getColumnIndex(DEVICE_COLUMN_DEVICEID)));
                objdevice.setPort(res.getString(res.getColumnIndex(DEVICE_COLUMN_PORT)));
                objdevice.setConnectedToDB(res.getString(res.getColumnIndex(DEVICE_COLUMN_CONNECTED)));
                objdevice.setIssync(res.getInt(res.getColumnIndex(DEVICE_COLUMN_ISSYNC))==1?true:false);

                array_list.add(objdevice);
                //array_list.add(res.getString(res.getColumnIndex("username")),"");
                res.moveToNext();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return array_list;
    }
    public void createdevice(){
        SQLiteDatabase db = this.getWritableDatabase();
       boolean istableexist= doesTableExist(DEVICE_TABLE_NAME);
        db.execSQL(
                "create table " +DEVICE_TABLE_NAME+
                        "(deviceid text,port text,connecteddb text)"
        );
    }
    //endregion


    public void dropdb(){
        SQLiteDatabase db = this.getWritableDatabase();
       // db.execSQL("DROP TABLE IF EXISTS User");

        onCreate(db);

    }
    public boolean doesTableExist( String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}
