package com.spyromedia.android.kamvia;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class Globals {

    public static String URL = "http://18.220.53.162/kamvia/api";

    public static class CurrentUser{
        CurrentUser(){

        }
        public String USER_ID ="";
        public String USER_NAME = "";
        public String MOBILE_NUMBER = "";
        public  String USER_ROLE = "";
        public  String VERIFICATION ="";
    }
    public static CurrentUser currentUser;

    public static CurrentUser loadLoginInfo(Context context){
        CurrentUser cu=new CurrentUser();
        try{
            SharedPreferences sharedPreferences=context.getSharedPreferences("settings",0);
            cu.USER_ID=sharedPreferences.getString("USER_ID","");
            cu.USER_NAME=sharedPreferences.getString("USER_NAME","");
            cu.MOBILE_NUMBER=sharedPreferences.getString("MOBILE_NUMBER","");
            cu.USER_ROLE= sharedPreferences.getString("USER_ROLE","");
            cu.VERIFICATION= sharedPreferences.getString("VERIFICATION","");



        }catch (Exception e){
            Log.d("loadLoginInfo", e.getMessage());
        }finally {
            return cu;
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }



}
