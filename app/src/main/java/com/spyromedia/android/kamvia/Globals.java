package com.spyromedia.android.kamvia;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Globals {

    public static String URL = "http://18.220.53.162/kamvia/api";
    public static int mExpandedPosition = -1;
    private static char[] hextable = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    public static class CurrentUser{
        CurrentUser(){

        }
        public String USER_ID ="";
        public String USER_NAME = "";
        public String MOBILE_NUMBER = "";
        public  String USER_ROLE = "";
        public  String VERIFICATION ="";
        public  String EMP_NUMBER = "";
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
            cu.EMP_NUMBER = sharedPreferences.getString("EMP_NUMBER","");



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


    static String md5(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(), 0, s.length());
            byte[] bytes = digest.digest();

            String hash = "";
            for (int i = 0; i < bytes.length; ++i)
            {
                int di = (bytes[i] + 256) & 0xFF;
                hash = hash + hextable[(di >> 4) & 0xF] + hextable[di & 0xF];
            }

            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
        }

        return "";
    }




}
