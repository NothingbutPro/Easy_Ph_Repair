package com.ics.easy_ph_repair.ui.tools;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ics.easy_ph_repair.LoginSign.ForgottenPass;
import com.ics.easy_ph_repair.LoginSign.LoginActivity;
import com.ics.easy_ph_repair.LoginSign.SignUp;
import com.ics.easy_ph_repair.NavigationActivity;
import com.ics.easy_ph_repair.R;
import com.ics.easy_ph_repair.Session.SessionManager;
import com.ics.easy_ph_repair.WebUrls.Urls;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
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

public class ToolsProd extends Fragment {

    private ToolsViewModel toolsViewModel;
    Button logbtn,signup;
    TextView forpsss;
    EditText emailet,passedt;
    SessionManager sessionManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        logbtn = root.findViewById(R.id.logbtn);
        emailet = root.findViewById(R.id.emailet);
        passedt = root.findViewById(R.id.passedt);
        signup = root.findViewById(R.id.signup);
        forpsss = root.findViewById(R.id.forpsss);
        sessionManager = new SessionManager(getActivity());
        NavigationActivity.navController =null;
        sessionManager.setLogin(false);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!emailet.getText().toString().isEmpty() && !passedt.getText().toString().isEmpty()) {
                 //   new LogMEIn(emailet.getText().toString() ,passedt.getText().toString() ).execute();
                }else {
                    Toast.makeText(getActivity(), "Logging you in", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Please fill all fields ", Toast.LENGTH_LONG).show();
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
//        final TextView textView = root.findViewById(R.id.text_tools);
//        toolsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

// public class LogMEIn extends AsyncTask<String, Void, String> {
//        ProgressDialog dialog;
//        String result = "";
//        String EMail;
//        String Pass;
//        String Passedt;
//        String Mobedt;
//        String Radiothing;
//
//        public LogMEIn(String EMail, String Pass) {
//            this.EMail = EMail;
//            this.Pass = Pass;
//
//        }
//
//        protected void onPreExecute() {
//            dialog = new ProgressDialog(getActivity());
//            dialog.show();
//
//        }
//
//        protected String doInBackground(String... arg0) {
//
//
//
//            try {
//
//
//                MultipartEntity entity = new MultipartEntity(
//                        HttpMultipartMode.BROWSER_COMPATIBLE);
//                entity.addPart("user_id", new StringBody("" +new SessionManager(getActivity())));
//                entity.addPart("id", new StringBody("" +id));
//                entity.addPart("attachment", new FileBody(pic0));
//////                        result = Utilities.postEntityAndFindJson("https://www.spellclasses.co.in/DM/Api/taxreturn", entity);
//                result = Utilities.postEntityAndFindJson("http://ihisaab.in/vertical_homes/Api/sit_supervisor_attachment_cement", entity);
//
////                    entity.addPart("return_copy_upload", new FileBody(pic0));
////                    result = Utilities.postEntityAndFindJson("http://ihisaab.com/ihisaabv2/Api/taxreturn", entity);
////                    c1=0;
//
//
//                return result;
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                dialog.dismiss();
//
//                JSONObject jsonObject = null;
//                Log.e("PostRegistration", result.toString());
//                try {
//
//                    jsonObject = new JSONObject(result);
//                    //  String response = jsonObject.getString("responce");
//                    //   Log.e("Response is", response);
//                    String massage = jsonObject.getJSONObject("data").getString("name");
//                    Toast.makeText(getActivity(), "Successfully registered", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getActivity() , NavigationActivity.class);
//                    sessionManager.setLogin(true);
//                    sessionManager.serverEmailLogin(jsonObject.getJSONObject("data").getString("user_id"));
////                    final int uSer_id = Log.e("USer id", "" + new SessionManager(LoginActivity.this).getWaiterName());
//                    sessionManager.serverEmailLogin(jsonObject.getJSONObject("data").getString("name"),jsonObject.getJSONObject("data").getString("email"),jsonObject.getJSONObject("data").getString("mobile"));
//
//                    startActivity(intent);
//
//                   // getActivity().finish();
//                    // String cus_code = dt.getString("cust_code");
////                    store = cus_code.toString();
////                    editor = sharedpreferences.edit();
////                    editor.putString("cus_code" , store);
//                    // Toast.makeText(SingleUser.this, ""+cus_code, Toast.LENGTH_SHORT).show();
//                    //     Log.e("Your Customer code is", cus_code);
//
//
//
//                } catch (JSONException e) {
//
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//        public String getPostDataString(JSONObject params) throws Exception {
//
//            StringBuilder result = new StringBuilder();
//            boolean first = true;
//
//            Iterator<String> itr = params.keys();
//
//            while (itr.hasNext()) {
//
//                String key = itr.next();
//                Object value = params.get(key);
//
//                if (first)
//                    first = false;
//                else
//                    result.append("&");
//
//                result.append(URLEncoder.encode(key, "UTF-8"));
//                result.append("=");
//                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
//
//            }
//            return result.toString();
//        }
//    }
}