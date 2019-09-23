package com.ics.easy_ph_repair.LoginSign;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ics.easy_ph_repair.NavigationActivity;
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

public class SignUp extends AppCompatActivity {
    EditText Name,Email,Passedt,Mobet,Town,State,Distric,Conpass;
    RadioButton ownsp , indsp;
    SessionManager sessionManager;
    Button Sigbtn;
    androidx.appcompat.widget.Toolbar Toolbar;
    String selectedtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        Oiradiogroup = findViewById(R.id.oiradiogroup2);
        Name = findViewById(R.id.name);
        Toolbar = findViewById(R.id.toolbar);
        sessionManager = new SessionManager(this);
        Email = findViewById(R.id.email);
        Town = findViewById(R.id.town);
        Sigbtn = findViewById(R.id.sigbtn);
        State = findViewById(R.id.state);
        Distric = findViewById(R.id.distric);
        ownsp = findViewById(R.id.ownsp);
        indsp = findViewById(R.id.indsp);
        Passedt = findViewById(R.id.passedt);
        Conpass = findViewById(R.id.conpass);
        Mobet = findViewById(R.id.mobet);
        Toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() , LoginActivity.class);
                startActivity(intent);
            }
        });
        indsp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                selectedtext  = compoundButton.getText().toString();
                indsp.setChecked(true);
                ownsp.setChecked(false);
            }
        });

        ownsp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                selectedtext  = compoundButton.getText().toString();
                ownsp.setChecked(true);
                indsp.setChecked(false);
            }
        });
        Sigbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Name.getText().toString().isEmpty() && !Email.getText().toString().isEmpty() && !Passedt.getText().toString().isEmpty()
                        && !Mobet.getText().toString().isEmpty() && !(ownsp.isSelected()||indsp.isSelected() &&
                        !Town.getText().toString().isEmpty() &&
                        !Distric.getText().toString().isEmpty() && !State.getText().toString().isEmpty())) {
                    if(Conpass.getText().toString().equals(Passedt.getText().toString())) {
//                    RadioButton r = (RadioButton) Oiradiogroup.getChildAt(Oiradiogroup.getCheckedRadioButtonId());
//                    String selectedtext = r.getText().toString();
                        new Registerme(Name.getText().toString(), Email.getText().toString(), Passedt.getText().toString(), Mobet.getText().toString(), selectedtext,Distric.getText().toString()
                        ,Town.getText().toString() ,State.getText().toString()).execute();
                    }else {
                        Toast.makeText(SignUp.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(view.getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

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
            dialog = new ProgressDialog(SignUp.this);
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
                    Toast.makeText(SignUp.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this , OTPActivity.class);
//                    intent.putExtra("number is" , )
                    sessionManager.setLogin(true);
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
