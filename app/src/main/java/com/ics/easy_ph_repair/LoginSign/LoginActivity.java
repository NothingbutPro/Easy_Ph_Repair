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

public class LoginActivity extends AppCompatActivity {
    Button logbtn,signup;
    TextView forpsss;
    EditText emailet,passedt;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logbtn = findViewById(R.id.logbtn);
        emailet = findViewById(R.id.emailet);
        passedt = findViewById(R.id.passedt);
        signup = findViewById(R.id.signup);
        forpsss = findViewById(R.id.forpsss);
        sessionManager = new SessionManager(this);
        sessionManager.setLogin(false);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!emailet.getText().toString().isEmpty() && !passedt.getText().toString().isEmpty()) {
                new LogMEIn(emailet.getText().toString() ,passedt.getText().toString() ).execute();
                }else {
                    Toast.makeText(LoginActivity.this, "Logging you in", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "Please fill all fields ", Toast.LENGTH_LONG).show();
                }
//                Intent intent = new Intent(view.getContext() , NavigationActivity.class);
//                startActivity(intent);
            }
        });

        forpsss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() , ForgottenPass.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() , SignUp.class);
                startActivity(intent);
            }
        });
    }
    public class LogMEIn extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String EMail;
        String Pass;
        String Passedt;
        String Mobedt;
        String Radiothing;

        public LogMEIn(String EMail, String Pass) {
            this.EMail = EMail;
            this.Pass = Pass;

        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Login);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("email", EMail);
                postDataParams.put("password", Pass);



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
                  //  String response = jsonObject.getString("responce");
                 //   Log.e("Response is", response);
                    String massage = jsonObject.getJSONObject("data").getString("name");
                    Toast.makeText(LoginActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this , NavigationActivity.class);
                    sessionManager.setLogin(true);
                    sessionManager.serverEmailLogin(jsonObject.getJSONObject("data").getString("user_id"));
//                    final int uSer_id = Log.e("USer id", "" + new SessionManager(LoginActivity.this).getWaiterName());
                    sessionManager.serverEmailLogin(jsonObject.getJSONObject("data").getString("name"),jsonObject.getJSONObject("data").getString("email"),jsonObject.getJSONObject("data").getString("mobile"));
                    startActivity(intent);
                    NavigationActivity.navController.popBackStack();
                    NavigationActivity.navController=null;
                    finish();
                    // String cus_code = dt.getString("cust_code");
//                    store = cus_code.toString();
//                    editor = sharedpreferences.edit();
//                    editor.putString("cus_code" , store);
                    // Toast.makeText(SingleUser.this, ""+cus_code, Toast.LENGTH_SHORT).show();
                    //     Log.e("Your Customer code is", cus_code);



                } catch (JSONException e) {

                    e.printStackTrace();
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
}
