package com.ics.easy_ph_repair.LoginSign;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.easy_ph_repair.Basic.NavigationActivity;
import com.ics.easy_ph_repair.R;
import com.ics.easy_ph_repair.Session.SessionManager;
import com.ics.easy_ph_repair.WebUrls.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class OtpActivity extends AppCompatActivity {
    String Name,Email,selectedtext,Distric,Town,State,Mobet,Passedt;
    Button verotp;
    TextView opttxt;
    EditText otpedt;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        sessionManager = new SessionManager(this);
        otpedt = findViewById(R.id.otpedt);
        opttxt = findViewById(R.id.opttxt);
        verotp = findViewById(R.id.verotp);
        Name=  getIntent().getStringExtra("Name");
        Mobet= getIntent().getStringExtra("Mobet");
       Email= getIntent().getStringExtra("Email");
        Passedt= getIntent().getStringExtra("Passedt");
       selectedtext= getIntent().getStringExtra("selectedtext");
      Distric=  getIntent().getStringExtra("Distric");
       Town= getIntent().getStringExtra("Town");
       State= getIntent().getStringExtra("State");
        opttxt.setText(otpedt.getText().toString()+""+Mobet.toString());
        try {
            if(!getIntent().getStringExtra("Mobet").isEmpty()) {
                new GetOTP(getIntent().getStringExtra("Mobet")).execute();
            }else {
                Toast.makeText(this, "Error Please fill Registration form again", Toast.LENGTH_LONG).show();
            }
        }catch (Exception ew)
        {
            Toast.makeText(this, "Error Please fill Registration form again", Toast.LENGTH_LONG).show();
            ew.printStackTrace();
        }
        verotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!otpedt.getText().toString().isEmpty()) {
                new CheckOTp(otpedt.getText().toString() ,getIntent().getStringExtra("Mobet") ).execute();
                }else {

                }
            }
        });
        
    }

    private class GetOTP extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String Mobedt;


        public GetOTP( String Mobedt) {

            this.Mobedt = Mobedt;

        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(OtpActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Otp_send);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("mobile", Mobedt);



                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  /*milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    Boolean response = jsonObject.getBoolean("responce");
                    Log.e("Response is", ""+response);
//                    String massage = jsonObject.getString("massage");
                    if(response) {
                        Toast.makeText(OtpActivity.this, "Otp Has been sent", Toast.LENGTH_SHORT).show();
                    }
                    // String cus_code = dt.getString("cust_code");
//                    store = cus_code.toString();
//                    editor = sharedpreferences.edit();
//                    editor.putString("cus_code" , store);
                    // Toast.makeText(SingleUser.this, ""+cus_code, Toast.LENGTH_SHORT).show();
                    //     Log.e("Your Customer code is", cus_code);



                } catch (JSONException e) {
                    try {
                        if(jsonObject.getString("error").contains("mobile") )
                        {
                            Toast.makeText(OtpActivity.this, "Mobile is already exist", Toast.LENGTH_SHORT).show();
                        }
                        if(jsonObject.getString("error").contains("email"))
                        {
                            Toast.makeText(OtpActivity.this, "Email is already exist", Toast.LENGTH_SHORT).show();
                        }
                        e.printStackTrace();
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }

    private class CheckOTp extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String Mobedt;
        String OtpStr;


        public CheckOTp( String OtpStr ,String Mobedt) {

            this.OtpStr = OtpStr;
            this.Mobedt = Mobedt;

        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(OtpActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Otp_match);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("mobile", Mobedt);
                postDataParams.put("otp", OtpStr);



                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  /*milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    Boolean response = jsonObject.getBoolean("responce");
                    Log.e("Response is", ""+response);
//                    String massage = jsonObject.getString("massage");
                    if(response) {
                        new Registerme(Name,Email,Passedt,Mobedt,selectedtext,Distric,Town,State).execute();
//                        Toast.makeText(OtpActivity.this, "Otp HAs been sent", Toast.LENGTH_SHORT).show();
                    }
                    // String cus_code = dt.getString("cust_code");
//                    store = cus_code.toString();
//                    editor = sharedpreferences.edit();
//                    editor.putString("cus_code" , store);
                    // Toast.makeText(SingleUser.this, ""+cus_code, Toast.LENGTH_SHORT).show();
                    //     Log.e("Your Customer code is", cus_code);



                } catch (JSONException e) {
                    try {
                        if(jsonObject.getString("error").contains("mobile") )
                        {
                            Toast.makeText(OtpActivity.this, "Mobile is already exist", Toast.LENGTH_SHORT).show();
                        }
                        if(jsonObject.getString("error").contains("email"))
                        {
                            Toast.makeText(OtpActivity.this, "Email is already exist", Toast.LENGTH_SHORT).show();
                        }
                        e.printStackTrace();
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }

    //++++++++++++++Register now

    private class Registerme extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String Name;
        String Email;
        String Passedt;
        String Mobedt;
        String Radiothing;
        String District;
        String Town;
        String State;

        public Registerme(String Name, String Email, String Passedt, String Mobedt, String Radiothing, String District, String Town, String State) {
            this.Name = Name;
            this.Email = Email;
            this.Passedt = Passedt;
            this.Mobedt = Mobedt;
            this.District = District;
            this.Radiothing = Radiothing;
            this.Town = Town;
            this.State = State;
        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(OtpActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Ragistration);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", Name);
                postDataParams.put("email", Email);
                postDataParams.put("password", Passedt);
                postDataParams.put("mobile", Mobedt);
                postDataParams.put("user_type", Radiothing);
                postDataParams.put("state", State);
                postDataParams.put("town", Town);
                postDataParams.put("district", District);


                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  /*milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    String response = jsonObject.getString("responce");
                    Log.e("Response is", response);
                    String massage = jsonObject.getString("massage");
                    Toast.makeText(OtpActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OtpActivity.this , NavigationActivity.class);
//                    intent.putExtra("number is" , )
                    sessionManager.setLogin(true);
                    sessionManager.serverEmailLogin(jsonObject.getJSONObject("massage").getString("user_id"));
                    sessionManager.serverEmailLogin(jsonObject.getJSONObject("massage").getString("name"),jsonObject.getJSONObject("massage").getString("email"),jsonObject.getJSONObject("massage").getString("mobile"));
                    //   sessionManager.serverEmailLogin();

                    startActivity(intent);
                    finish();
                    // String cus_code = dt.getString("cust_code");
//                    store = cus_code.toString();
//                    editor = sharedpreferences.edit();
//                    editor.putString("cus_code" , store);
                    // Toast.makeText(SingleUser.this, ""+cus_code, Toast.LENGTH_SHORT).show();
                    //     Log.e("Your Customer code is", cus_code);



                } catch (JSONException e) {
                    try {
                        if(jsonObject.getString("error").contains("mobile") )
                        {
                            Toast.makeText(OtpActivity.this, "Mobile is already exist", Toast.LENGTH_SHORT).show();
                        }
                        if(jsonObject.getString("error").contains("email"))
                        {
                            Toast.makeText(OtpActivity.this, "Email is already exist", Toast.LENGTH_SHORT).show();
                        }
                        e.printStackTrace();
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }


    //++++++++++++++++++++
}
