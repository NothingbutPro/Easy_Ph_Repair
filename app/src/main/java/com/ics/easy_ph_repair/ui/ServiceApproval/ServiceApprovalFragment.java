package com.ics.easy_ph_repair.ui.ServiceApproval;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.easy_ph_repair.Adapters.ServiceStatusAdapter;
import com.ics.easy_ph_repair.JavaFiles.ServiceStatusData;
import com.ics.easy_ph_repair.R;
import com.ics.easy_ph_repair.Session.SessionManager;
import com.ics.easy_ph_repair.WebUrls.Urls;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ServiceApprovalFragment extends Fragment {
    public ArrayList<ServiceStatusData> StatusDataArrayList = new ArrayList<>();
    private SlideshowViewModel slideshowViewModel;
    public RecyclerView servistatusrecyRecyclerView;
    ServiceStatusAdapter serviceStatusAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.serviceapproval, container, false);
        servistatusrecyRecyclerView = root.findViewById(R.id.servistatus);
        new GETMyREquest().execute();
        return root;
    }

    private class GETMyREquest extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String EMail;
        String Pass;
        String Passedt;
        String Mobedt;
        String Radiothing;

        public GETMyREquest() {

        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Getjobcart);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id",new SessionManager(getActivity()).getCoustId());


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
//                    JSONObject jsonObject1 = jsonObject.getJSONObject("massage");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        if(jsonObject1.getString("status").equals("1")) {
       StatusDataArrayList.add(new ServiceStatusData(jsonObject1.getString("id") ,jsonObject1.getString("user_id") ,jsonObject1.getString("jobdate")  ,
               jsonObject1.getString("jobid")  ,jsonObject1.getString("customer_id")  ,
               jsonObject1.getString("mobile")  ,jsonObject1.getString("first_name")  ,
               jsonObject1.getString("last_name")  ,jsonObject1.getString("model")  ,
               jsonObject1.getString("colour")  ,jsonObject1.getString("imei_one")  ,
               jsonObject1.getString("imei_two")  ,jsonObject1.getString("job_status")  ,
               jsonObject1.getString("invoice_status")  ,jsonObject1.getString("service_type")  ,
               jsonObject1.getString("warranty")  ,jsonObject1.getString("remarks")  ,
               jsonObject1.getString("accessories")  ,jsonObject1.getString("estimate")  ,
               jsonObject1.getString("complant")  ,jsonObject1.getString("status")
       ));
//                        }

                    }
                    serviceStatusAdapter = new ServiceStatusAdapter(getActivity() , StatusDataArrayList);
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    servistatusrecyRecyclerView.setLayoutManager(llm);
                    servistatusrecyRecyclerView.setAdapter(serviceStatusAdapter);
                    //  String response = jsonObject.getString("responce");
                    //   Log.e("Response is", response);

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