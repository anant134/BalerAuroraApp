package com.smartech.tour.baleraurora;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.smartech.tour.baleraurora.DB.DBHelper;
import com.smartech.tour.baleraurora.module.card;
import com.smartech.tour.baleraurora.module.cardState;
import com.smartech.tour.baleraurora.module.guestInfo;
import com.smartech.tour.baleraurora.module.operationData;
import com.smartech.tour.baleraurora.module.printData;
import com.smartech.tour.baleraurora.module.tripData;
import com.smartech.tour.baleraurora.module.userSelectedTripdData;
import com.whty.smartpos.gigantestourbooking.R;
import com.whty.smartpos.tysmartposapi.ITYSmartPosApi;
import com.smartech.tour.baleraurora.operationmodule.MyListener;
import com.smartech.tour.baleraurora.operationmodule.OperationInter;
import com.smartech.tour.baleraurora.operationmodule.ScannerDevice;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

import com.smartech.tour.baleraurora.module.user;

public class MainActivity extends Activity {
    private static final int PERMISSION_REQUEST_CAMERA = 0;

    public static ITYSmartPosApi smartPosApi;
    public static Context mContext;
    final Fragment login = new LoginFragment();
    OkHttpClient client = new OkHttpClient();
    ConstantVariables Constantvariable;
    user objuser;
    DBHelper mydb;
    ProgressDialog progressDialog;
    operationData opd;
    ArrayList<cardState> _cardState;
    ArrayList<printData> _printData;
    ArrayList<guestInfo> _guestInfo;
    ArrayList<guestInfo> _touristscandata;
    ArrayList<card> _card;
    userSelectedTripdData _userSelectedTripdData;
    String tripId="";
    public String scanqrdata="";
    public static tripData printtripdata;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smartPosApi = ITYSmartPosApi.get(this);
        smartPosApi.setListener(new MyListener());
        mContext = this;
        OperationInter.setActivity(this);
        String resprinter= OperationInter.getDevice(4).execute(0);
        String resprintersetup= OperationInter.getDevice(4).execute(1);
        String resscannersetup= OperationInter.getDevice(5).execute(0);

        Constantvariable=new ConstantVariables();
        Constantvariable.setDeviceid(OperationInter.getDevice(0).execute(2));

        objuser =new user();
        mydb = new DBHelper(this);
       // mydb.onCreate(this);
        opd=new operationData();
        printtripdata=new tripData();
        _guestInfo=new ArrayList<>();
        ArrayList<String> cards=new ArrayList<>();
        _cardState=new ArrayList<>();
        _card=new ArrayList<>();
        _printData=new ArrayList<>();
        _userSelectedTripdData=new userSelectedTripdData();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.container, new LandingFragment(),"Landing");
        fragmentTransaction.commit();

        IsConnectedToNet();


    }

    public  boolean IsConnectedToNet(){
        boolean isConnected=false;
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            isConnected=false;
           /* Description.setVisibility(View.INVISIBLE);
            new AlertDialog.Builder(WelcomePage.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(getResources().getString(R.string.internet_error))
                    .setPositiveButton("OK", null).show();*/
        }else{
            isConnected=true;
         /*   dialog = ProgressDialog.show(WelcomePage.this, "", "Loading...", true,false);
            new Welcome_Page().execute();*/
        }
        return isConnected;
    }
    public void ShowMessage(String message,Integer messagetype ){
        /*Toast toast=Toast.makeText(this.getApplicationContext(),message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.CENTER,10,10);
        View view=toast.getView();
        TextView view1=(TextView)view.findViewById(android.R.id.message);
        view1.setTextColor(Color.WHITE);
        switch (messagetype){
            case 1:
                view.setBackgroundResource(R.drawable.successmessagestyle);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.errormessagestyle);
                break;
            case 3:
                view.setBackgroundResource(R.drawable.warningmessagestyle);
                break;
            case 4:
                view.setBackgroundResource(R.drawable.infomessagestyle);
                break;
        }
        toast.show();*/
        Toast toast=Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK || resultCode == 20) {
            if (requestCode == 0) {
                String statusCode = data.getStringExtra("statusCode");
                String pinblock = data.getStringExtra("pinblock");
                Log.d("MainActivity", "pinblock : " + pinblock);
            } else if (requestCode == 1) {
                /*if (checkApkExist("com.whty.sycodescan")) {
                    ScannerDevice.scanData = data.getStringExtra("result");
                } else {
                    ScannerDevice.scanData = data.getStringExtra("SCAN_RESULT");
                }
                Log.d("MainActivity", ScannerDevice.scanData);*/
                ScannerDevice.scanData = data.getStringExtra("result");

            }
        }
    }

    private void startCamera() {
        //todo
    }
    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 检查这个包名的app是否存在
     *
     * @param packageName
     * @return
     */
    private boolean checkApkExist(String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            getPackageManager().getApplicationInfo(
                    packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
