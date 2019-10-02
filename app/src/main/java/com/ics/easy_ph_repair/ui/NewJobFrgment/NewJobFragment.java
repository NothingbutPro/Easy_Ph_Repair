package com.ics.easy_ph_repair.ui.NewJobFrgment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NewJobFragment extends Fragment {
    EditText Jobid,Cusid,Mobid,Modelid,Imeiid,Imeid2,Compalinid,Colourid;
    private GalleryViewModel galleryViewModel;
    public CheckBox simcid,memchid,fanid,battchid,bkchr,charid,headchid,usbcabid;
  public List<String> Accessories = new ArrayList<>();
  SessionManager sessionManager;
 //   String Accessories;
    int howmuchchek;
    public EditText jdate;
    Button sbmtjobbtn;
    private String Assories;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_newjob, container, false);
        sbmtjobbtn = root.findViewById(R.id.sbmtjobbtn);
        //check boxes++++++++++++
        sessionManager = new SessionManager(getActivity());

        simcid = root.findViewById(R.id.simcid);
        memchid = root.findViewById(R.id.memchid);
        fanid = root.findViewById(R.id.fanid);
        battchid = root.findViewById(R.id.battchid);
        bkchr = root.findViewById(R.id.bkchr);
        charid = root.findViewById(R.id.charid);
        headchid = root.findViewById(R.id.headchid);
        usbcabid = root.findViewById(R.id.usbcabid);

        //+++++++++++++++++++++++++
        jdate = root.findViewById(R.id.jdate);
        Jobid = root.findViewById(R.id.jbid);
        Cusid = root.findViewById(R.id.csid);
        Mobid = root.findViewById(R.id.mobid);
//        Firstid = root.findViewById(R.id.fnameid);
//        Lastid = root.findViewById(R.id.lasnameid);
        Modelid = root.findViewById(R.id.modelid);
        Imeiid = root.findViewById(R.id.iemiid1);
        Imeid2 = root.findViewById(R.id.iemiid2);
        Compalinid = root.findViewById(R.id.comid);
        Colourid = root.findViewById(R.id.colourid);
        new GETJBID().execute();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(" dd/MM/yyyy");
        System.out.println(dateFormat.format(cal.getTime()));
        jdate.setText(dateFormat.format(cal.getTime()));
        Log.e("Jobid" , ""+Jobid.getText().toString());
        Mobid.setText(sessionManager.getMobile());
//        navController.popBackStack();
        jdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub

                jdate.setFocusable(true);
            }
        });
        sbmtjobbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(simcid.isChecked())
                    {

                        Accessories.add(simcid.getText().toString());
                        howmuchchek =howmuchchek+1;
                    }
                    if(memchid.isChecked())
                    {
                        Accessories.add(memchid.getText().toString());
                        howmuchchek =howmuchchek+1;
                    }
                    if(fanid.isChecked())
                    {
                        Accessories.add(fanid.getText().toString());
                        howmuchchek =howmuchchek+1;
                    }
                    if(battchid.isChecked())
                    {
                        Accessories.add(battchid.getText().toString());
                        howmuchchek =howmuchchek+1;
                    }
                    if(bkchr.isChecked())
                    {
                        Accessories.add(bkchr.getText().toString());
                        howmuchchek =howmuchchek+1;
                    }
                    if(charid.isChecked())
                    {
                        Accessories.add(charid.getText().toString());
                        howmuchchek =howmuchchek+1;
                    }
                    if(headchid.isChecked())
                    {
                        Accessories.add(headchid.getText().toString());
                        howmuchchek =howmuchchek+1;
                    }
                Boolean bjdate =jdate.getText().toString().isEmpty();
                Boolean bJobid =Jobid.getText().toString().isEmpty();
                Boolean bCusid =Cusid.getText().toString().isEmpty();
                Boolean Mobidb =Mobid.getText().toString().isEmpty();
                Boolean Modelidb =Modelid.getText().toString().isEmpty();
                Boolean Colouridb =Colourid.getText().toString().isEmpty();
                Boolean Imeiidv =Imeiid.getText().toString().isEmpty();
                Boolean Imeid2b =Imeid2.getText().toString().isEmpty();
                Boolean Compalinidb =Compalinid.getText().toString().isEmpty();
                Log.e("jdate",""+jdate.getText().toString().isEmpty());
                Log.e("Jobid",""+Jobid.getText().toString().isEmpty());
                Log.e("Cusid",""+Cusid.getText().toString().isEmpty());
                Log.e("Mobid",""+Mobid.getText().toString().isEmpty());
                Log.e("Modelid",""+Modelid.getText().toString().isEmpty());
                Log.e("Colourid",""+Colourid.getText().toString().isEmpty());
                Log.e("Imeiid",""+Imeiid.getText().toString().isEmpty());
                Log.e("Imeid2",""+Imeid2.getText().toString().isEmpty());
                Log.e("Compalinid",""+Compalinid.getText().toString().isEmpty());
                {
//                    if(!jdate.getText().toString().isEmpty() && !Jobid.getText().toString().isEmpty()  && !Cusid.getText().toString().isEmpty() &&
//                    !Mobid.getText().toString().isEmpty() && !Modelid.getText().toString().isEmpty() && !Colourid.getText().toString().isEmpty() &&
//                     Imeiid.getText().toString().isEmpty()
//                    && !Imeid2.getText().toString().isEmpty() &&!Compalinid.getText().toString().isEmpty())
                    if(!bjdate &&!bCusid&&!Mobidb&&!Modelidb&&!Colouridb&&!Compalinidb)
                    {
                            if(howmuchchek>0) {

                                for(int i=0;i<howmuchchek;i++)
                                {

                                    if(i==0)
                                    {
                                        Log.e("Assories "+i , ""+Assories);
                                        Assories = Accessories.get(i);
                                    }else {
                                        if(i==howmuchchek-1) {
                                            Log.e("Assories "+i , ""+Assories);
                                            Assories = Assories +","+ Accessories.get(i);
                                        }else {
                                            Log.e("Assories "+i , ""+Assories);
                                            Assories = Assories + "," + Accessories.get(i);

                                        }
                                    }
                                }

                                Log.e("jdate" , "id jdate"+ jdate);

                            }
                      new SubMItJob(jdate, Jobid, Cusid, Mobid, Modelid, Colourid, Imeiid, Imeid2, Compalinid,Assories).execute();
                    }else {
                        Toast.makeText(getActivity(), "Fill all fields", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
    public class SubMItJob extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String Jdate;
        String jobid;
        String cusid;
        String modelid;
        String mobid;
        String colourid;
        String imeiid;
        String imeid2;
        String compalinid;
        String assories;

        public SubMItJob(EditText jdate, EditText jobid, EditText cusid, EditText mobid, EditText modelid, EditText colourid, EditText imeiid, EditText imeid2, EditText compalinid, String assories) {
            this.Jdate = jdate.getText().toString();
            this.jobid = jobid.getText().toString();
            this.cusid = cusid.getText().toString();
            this.modelid = modelid.getText().toString();
            this.mobid = mobid.getText().toString();
            this.colourid = colourid.getText().toString();
            this.imeiid = imeiid.getText().toString();
            this.imeid2 = imeid2.getText().toString();
            this.compalinid = compalinid.getText().toString();
            this.assories = assories;

        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Job_card);

                JSONObject postDataParams = new JSONObject();
                Log.e("Customer" , "id ios"+ new SessionManager(getActivity()).getCoustId());
                Log.e("jdate" , "id jdate"+ jdate);

                postDataParams.put("jobdate" , Jdate);
                postDataParams.put("customer_id" , cusid);
                postDataParams.put("mobile" , mobid);
                postDataParams.put("model" , modelid);
                postDataParams.put("colour" , colourid);
                postDataParams.put("imei_one" , imeiid);
                postDataParams.put("service_type" , "Mobile");
                postDataParams.put("imei_two" , imeid2);
                postDataParams.put("complant" , compalinid);
                postDataParams.put("accessories" , assories);
                postDataParams.put("user_id" ,  new SessionManager(getActivity()).getWaiterName());
                Log.e("assories",""+assories);
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
                    JSONObject jsonObject1 = jsonObject.getJSONObject("massage");
                    Boolean Responce = jsonObject.getBoolean("responce");
                    if(Responce.booleanValue()) {
                        Toast.makeText(getActivity(), "Job Submitted Success", Toast.LENGTH_SHORT).show();
//                        try {
//                            NewJobFragment.this.finalize();
//                        } catch (Throwable throwable) {
//                            throwable.printStackTrace();
//                        }
                        Intent intent = new Intent(getActivity() ,NavigationActivity.class );
                        startActivity(intent);
                        getActivity().finish();
                    }else {
                        Toast.makeText(getActivity(), "Some Problem please ", Toast.LENGTH_SHORT).show();
                    }
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
    public  class  GETJBID extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String EMail;
        String Pass;
        String Passedt;
        String Mobedt;
        String Radiothing;

        public GETJBID() {

        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Getjob_cust_id+"/"+new SessionManager(getActivity()).getWaiterName());

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("user_id" , new SessionManager(getActivity()).getWaiterName());



                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  /*milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("GET");
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
                    JSONObject jsonObject1 = jsonObject.getJSONObject("massage");
                  //  Jobid.setText(jsonObject1.getString("job_id"));
                    Cusid.setText(jsonObject1.getString("cust_id"));
                    Cusid.setFocusable(false);
                    Jobid.setFocusable(false);
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
