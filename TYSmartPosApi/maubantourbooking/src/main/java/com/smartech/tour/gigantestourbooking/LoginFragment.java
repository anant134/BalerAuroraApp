package com.smartech.tour.gigantestourbooking;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.smartech.tour.gigantestourbooking.module.accessRole;
import com.smartech.tour.gigantestourbooking.module.user;
import com.whty.smartpos.gigantestourbooking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LoginFragment extends Fragment implements View.OnClickListener {

    EditText txt_usranme,txt_password;
    Button btn_login,btn_cancel;
    MainActivity mainactivie;
    JSONObject objmsg=new JSONObject();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        txt_usranme = (EditText) view.findViewById(R.id.edt_username);
        txt_password = (EditText) view.findViewById(R.id.edt_password);
        btn_login=(Button)view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        mainactivie=((MainActivity) getActivity());
        mainactivie.progressDialog = new ProgressDialog(mainactivie);
        ArrayList lastloginuser=mainactivie.mydb.getAllUser();
        if(lastloginuser.size()>0){
            user u=(user) lastloginuser.get(0);
            txt_password.setText("");
            txt_usranme.setText(u.getUserName());

        }

        return  view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case  R.id.btn_login:
                try {
                    mainactivie.progressDialog.setMessage("Please wait..."); // Setting Message
                    mainactivie.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    mainactivie.progressDialog.show(); // Display Progress Dialog
                    mainactivie.progressDialog.setCancelable(false);

                    final Handler cwjHandler = new Handler();

                    final Runnable mUpdateResults = new Runnable() {
                        public void run() {
                            try {
                                mainactivie.ShowMessage(objmsg.getString("message"),objmsg.getInt("type"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void run() {
                            try {
                                objmsg=new JSONObject();
                                JSONObject obj=new JSONObject();
                                JSONObject objdata=new JSONObject();
                                obj.put("requestfor","userauth");

                                objdata.put("flag","auth");
                                objdata.put("username",txt_usranme.getText());
                                objdata.put("password",txt_password.getText());
                                obj.put("data",objdata);

                                String strResult = mainactivie.Constantvariable.funPostData(obj,"user.php").toString();
                                //if(!strResult.equals("")) {
                                JSONObject objJsonObject = new JSONObject(strResult);
                                if (objJsonObject.get("resultkey").equals(1)){
                                    //add to local db
                                    JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                                    String strName=objarr.getJSONObject(0).getString("username");
                                    String strRoleid=objarr.getJSONObject(0).getString("roleid");
                                    ArrayList<accessRole>  arrAccessRole=new ArrayList<>();
                                    JSONArray objArrAppmenu = new JSONArray(objarr.getJSONObject(0).getString("appmenu"));
                                    if(objArrAppmenu.length()>0){
                                        for (int i = 0; i < objArrAppmenu.length(); i++) {
                                            JSONObject objRowData= objArrAppmenu.getJSONObject(i);
                                            accessRole objAccessRole=new accessRole();
                                            objAccessRole.setID(objRowData.get("id").toString());
                                            objAccessRole.setDesc(objRowData.get("desc").toString());
                                            arrAccessRole.add(objAccessRole);
                                        }
                                    }

                                    mainactivie.objuser.setUserName(strName);
                                    mainactivie.objuser.setRoleid(strRoleid);
                                    mainactivie.objuser.setAccessRole(arrAccessRole);
                                    Gson gson=new Gson();
                                    mainactivie.mydb.insertUser(strName,gson.toJson(arrAccessRole),strRoleid);
                                    FragmentManager fm = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                    fragmentTransaction.replace(R.id.container,new LandingFragment(),"landing" );
                                    fragmentTransaction.commit();
                                    mainactivie.progressDialog.dismiss();
                                    objmsg.put("message","Welcome");
                                    objmsg.put("type",1);
                                }else{
                                    objmsg.put("message","Invaild username/password");
                                    objmsg.put("type",3);
                                    mainactivie.progressDialog.dismiss();
                                }

                            }catch(Exception ee){
                                ee.printStackTrace();
                                try {
                                    objmsg.put("message",ee.getMessage().toString());
                                    objmsg.put("type",3);
                                    mainactivie.progressDialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                            cwjHandler.post(mUpdateResults);
                        }
                    }).start();

                }catch (Exception e){
                    mainactivie.ShowMessage(e.getMessage().toString(),3);
                    mainactivie.progressDialog.dismiss();
                }
                break;



        }
    }

}