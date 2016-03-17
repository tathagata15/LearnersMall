package com.leaners_mall.ica.learnersmall.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by prithaviraj on 3/1/2016.
 */
public class ConnectioTimeOut {

    private boolean dnsOkay = false;
    private static final int DNS_SLEEP_WAIT = 250;
    private synchronized boolean resolveDns(){

        RemoteDnsCheck check = new RemoteDnsCheck();
        check.execute();
        try {
            int timeSlept = 0;
            while(!dnsOkay && timeSlept<4000){
                //Log.d("RemoteDnsCheck", "sleeping");
                Thread.sleep(DNS_SLEEP_WAIT);
                timeSlept+=DNS_SLEEP_WAIT;
                //Log.d("RemoteDnsCheck", "slept");
            }
        } catch (InterruptedException e) {

        }

        if(!dnsOkay){
            Log.d("resolveDns", "cancelling");
            check.cancel(true);
            Log.d("resolveDns", "cancelled");
        }
        return dnsOkay;
    }

    private class RemoteDnsCheck extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.d("RemoteDnsCheck", "starting");
                dnsOkay = false;
                InetAddress addr = InetAddress.getByName("");
                if(addr!=null){
                    Log.d("RemoteDnsCheck", "got addr");
                    dnsOkay = true;
                }
            } catch (UnknownHostException e) {
                Log.d("RemoteDnsCheck", "UnknownHostException");
            }
            return null;
        }

    }
}
