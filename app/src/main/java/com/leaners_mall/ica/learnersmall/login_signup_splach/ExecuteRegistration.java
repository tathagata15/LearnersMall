package com.leaners_mall.ica.learnersmall.login_signup_splach;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import com.leaners_mall.ica.learnersmall.utils.JSONParser;
/**
 * Created by tathagata on 15-Mar-16.
 */
public class ExecuteRegistration extends AsyncTask<String, String, JSONObject> {

    JSONObject jsonObj;
    public List<NameValuePair> params1;
    public String URL;
    JSONParser jsonParser;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        jsonParser = new JSONParser();
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        jsonObj = jsonParser.makeHttpRequest(URL, "POST", params1);
        Log.e("URL", jsonObj.toString());
        return jsonObj;
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);

    }
}