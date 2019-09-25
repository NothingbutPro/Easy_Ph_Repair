package com.ics.easy_ph_repair.ui.CollectUnit;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ics.easy_ph_repair.Adapters.CollectorsAdapter;
import com.ics.easy_ph_repair.JavaFiles.Collectors;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import static com.ics.easy_ph_repair.Adapters.ServiceStatusAdapter.JobId;

public class CollectUnitFragment extends Fragment {
    EditText Service_Report ,Collected_by,SC_Received,Estimated_Approved,Dead_Chance_Approval,Serviced_By,SC_Delivered,Boy_Collected_on
    ,SC_Received_on,Estimate_App_on,Dead_Chance_App_On,Service_Deliverd_on,Boy_Deliverd_on,Service_Finish_On,jobidin;
    private ShareViewModel shareViewModel;
    RecyclerView get_delivery_pickup_rec;
    CollectorsAdapter collectorsAdapter;
    ArrayList<Collectors> collectorsArrayList = new ArrayList<>();
    ScrollView scrollView2;
    FloatingActionButton addmorefloat;
    Button Collecbtn;
    private DatePickerDialog datePickerDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.collectunit, container, false);
        Service_Report= root.findViewById(R.id.service_Report);
        get_delivery_pickup_rec= root.findViewById(R.id.get_delivery_pickup_rec);
        Collected_by= root.findViewById(R.id.collected_by);
        SC_Received= root.findViewById(R.id.sC_Received);
        jobidin= root.findViewById(R.id.jobidin);
        Estimated_Approved= root.findViewById(R.id.estimate_Approved);
        addmorefloat= root.findViewById(R.id.addmorefloat);
        Dead_Chance_Approval= root.findViewById(R.id.dead_Chance_Approved);
        scrollView2= root.findViewById(R.id.scrollView2);
        Serviced_By= root.findViewById(R.id.Serviced_By);
        SC_Delivered= root.findViewById(R.id.sC_Delivered);
        Boy_Collected_on= root.findViewById(R.id.boy_Collected_On);
        new Get_delivery_pickup().execute();
        addmorefloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scrollView2.getVisibility() == View.VISIBLE)
                {
                    scrollView2.setVisibility(View.GONE);
                }else {
                    scrollView2.setVisibility(View.VISIBLE);
                }
            }
        });
        Bundle bundle = this.getArguments();
        try {
            jobidin.setText(JobId);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        if (bundle != null) {
            String jid = bundle.getString("jid");
            jobidin.setText(jid);
            jobidin.setFocusable(false);

        }else {
            Toast.makeText(getActivity(), "Null Job id", Toast.LENGTH_SHORT).show();
        }
        Boy_Collected_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar dateSelected = Calendar.getInstance();
//                String myFormat = "MM/dd/yy"; //In which you need put here
//                final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yyyy"; // your format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                        Boy_Collected_on.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        SC_Received_on= root.findViewById(R.id.sc_Received_On);
        SC_Received_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar dateSelected = Calendar.getInstance();
//                String myFormat = "MM/dd/yy"; //In which you need put here
//                final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yyyy"; // your format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                        SC_Received_on.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        Estimate_App_on= root.findViewById(R.id.estimate_App_on);

        Estimate_App_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar dateSelected = Calendar.getInstance();
//                String myFormat = "MM/dd/yy"; //In which you need put here
//                final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yyyy"; // your format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                        Estimate_App_on.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        Dead_Chance_App_On= root.findViewById(R.id.dead_Chance_App_On);

        Dead_Chance_App_On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar dateSelected = Calendar.getInstance();
//                String myFormat = "MM/dd/yy"; //In which you need put here
//                final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yyyy"; // your format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                        Dead_Chance_App_On.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        Service_Finish_On= root.findViewById(R.id.service_Finish_On);
//
//        Service_Finish_On.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar dateSelected = Calendar.getInstance();
////                String myFormat = "MM/dd/yy"; //In which you need put here
////                final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
//                final Calendar myCalendar = Calendar.getInstance();
//                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        // TODO Auto-generated method stub
//                        myCalendar.set(Calendar.YEAR, year);
//                        myCalendar.set(Calendar.MONTH, monthOfYear);
//                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        String myFormat = "MM/dd/yyyy"; // your format
//                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
//
//                        Service_Finish_On.setText(sdf.format(myCalendar.getTime()));
//                    }
//
//                };
//                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//
//        });
        Service_Deliverd_on= root.findViewById(R.id.service_Delivered_On);

//        Service_Deliverd_on.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar dateSelected = Calendar.getInstance();
////                String myFormat = "MM/dd/yy"; //In which you need put here
////                final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
//                final Calendar myCalendar = Calendar.getInstance();
//                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        // TODO Auto-generated method stub
//                        myCalendar.set(Calendar.YEAR, year);
//                        myCalendar.set(Calendar.MONTH, monthOfYear);
//                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        String myFormat = "MM/dd/yyyy"; // your format
//                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
//
//                        Service_Deliverd_on.setText(sdf.format(myCalendar.getTime()));
//                    }
//
//                };
//                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//
//        });
        Boy_Deliverd_on= root.findViewById(R.id.boy_Delivered_On);

//        Boy_Deliverd_on.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar dateSelected = Calendar.getInstance();
////                String myFormat = "MM/dd/yy"; //In which you need put here
////                final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
//                final Calendar myCalendar = Calendar.getInstance();
//                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        // TODO Auto-generated method stub
//                        myCalendar.set(Calendar.YEAR, year);
//                        myCalendar.set(Calendar.MONTH, monthOfYear);
//                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        String myFormat = "MM/dd/yyyy"; // your format
//                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
//
//                        Boy_Deliverd_on.setText(sdf.format(myCalendar.getTime()));
//                    }
//
//                };
//                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//
//        });
        Collecbtn = root.findViewById(R.id.collecbtn);
        Collecbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean Service_Finish_Onb =Service_Report.getText().toString().isEmpty();
                Boolean Collected_byb =Collected_by.getText().toString().isEmpty();
                Boolean SC_Receivedb =SC_Received.getText().toString().isEmpty();
                Boolean Estimated_Approvedb =Estimated_Approved.getText().toString().isEmpty();
                Boolean Dead_Chance_Approvalb =Dead_Chance_Approval.getText().toString().isEmpty();
                Boolean Serviced_Byb =Serviced_By.getText().toString().isEmpty();
                Boolean SC_Deliveredb =SC_Delivered.getText().toString().isEmpty();
                Boolean Boy_Collected_onb =Boy_Collected_on.getText().toString().isEmpty();
                if( !(Service_Finish_Onb && Collected_byb && SC_Receivedb &&Estimated_Approvedb && Dead_Chance_Approvalb &&Serviced_Byb
                        && SC_Deliveredb &&Boy_Collected_onb)
                )
                {
                    new PostCollector(Service_Report,Collected_by,SC_Received,Estimated_Approved,Dead_Chance_Approval
                    ,Serviced_By,SC_Delivered,Boy_Collected_on,SC_Received_on,Estimate_App_on,Dead_Chance_App_On
                    ,Service_Finish_On,Service_Deliverd_on,Boy_Deliverd_on).execute();
                }else {
                    Toast.makeText(getActivity(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        final TextView textView = root.findViewById(R.id.text_share);
//        shareViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private class PostCollector extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String service_Report;
        String collected_by;
        String SC_Received;
        String estimated_Approved;
        String dead_Chance_Approval;
        String serviced_By;
        String SC_Delivered;
        String boy_Collected_on;
        String SC_Received_on;
        String estimate_App_on;
        String dead_Chance_App_On;
        String service_Finish_On;
        String service_Deliverd_on;
        String boy_Deliverd_on;

        public PostCollector(EditText service_Report, EditText collected_by, EditText SC_Received, EditText estimated_Approved, EditText dead_Chance_Approval, EditText serviced_By, EditText SC_Delivered, EditText boy_Collected_on, EditText SC_Received_on, EditText estimate_App_on, EditText dead_Chance_App_On, EditText service_Finish_On, EditText service_Deliverd_on, EditText boy_Deliverd_on) {
             this.service_Report =service_Report.getText().toString();
            this.collected_by = collected_by.getText().toString();
            this.SC_Received = SC_Received.getText().toString();
            this.estimated_Approved = estimated_Approved.getText().toString();
            this.dead_Chance_Approval = dead_Chance_Approval.getText().toString();
            this.serviced_By = serviced_By.getText().toString();
            this.SC_Delivered = SC_Delivered.getText().toString();
            this.boy_Collected_on = boy_Collected_on.getText().toString();
            this.SC_Received_on = SC_Received_on.getText().toString();
            this.estimate_App_on = estimate_App_on.getText().toString();
            this.dead_Chance_App_On = dead_Chance_App_On.getText().toString();
            this.service_Finish_On = service_Finish_On.getText().toString();
            this.service_Deliverd_on = service_Deliverd_on.getText().toString();
            this.boy_Deliverd_on = boy_Deliverd_on.getText().toString();


        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Delivery_boy_pickup);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id" ,new SessionManager(getActivity()).getCoustId());
                postDataParams.put("Service_Report" ,service_Report );
                postDataParams.put("Collected_by" ,collected_by );
                postDataParams.put("SC_Received" ,SC_Received );
                postDataParams.put("Estimated_Approved" ,estimated_Approved );
                postDataParams.put("Dead_Chance_Approval" ,dead_Chance_Approval );
                postDataParams.put("Serviced_By" ,serviced_By );
                postDataParams.put("SC_Delivered" ,SC_Delivered );
                postDataParams.put("Boy_Collected_on" ,boy_Collected_on );
                postDataParams.put("SC_Received_on" ,SC_Received_on );
                postDataParams.put("Estimate_App_on" ,estimate_App_on );
                postDataParams.put("Dead_Chance_App_On" ,dead_Chance_App_On );
                postDataParams.put("Service_Deliverd_on" ,"None" );
                postDataParams.put("service_Deliverd_on" ,"NOne" );
                postDataParams.put("Boy_Deliverd_on" ,"None" );



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
                    if(jsonObject.getBoolean("responce"))
                    {
                        Toast.makeText(getActivity(), "Collector Information Submitted Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity() , NavigationActivity.class);
                        startActivity(intent);
                        getActivity().finish();
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

    private class Get_delivery_pickup extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;


        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Get_delivery_boy_pickup);

                JSONObject postDataParams = new JSONObject();
                Log.e("Customer" , "id ios"+ new SessionManager(getActivity()).getCoustId());

                postDataParams.put("user_id" , new SessionManager(getActivity()).getCoustId());



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
//                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    Boolean Responce = jsonObject.getBoolean("responce");
                    if(Responce.booleanValue()) {
                        Toast.makeText(getActivity(), " Submitted Successfully", Toast.LENGTH_SHORT).show();
                        for(int i = 0;i<jsonObject.getJSONArray("data").length();i++)
                        {
                            JSONObject object = jsonObject.getJSONArray("data").getJSONObject(i);
                            collectorsArrayList.add(new Collectors(object.getString("id"),object.getString("Service_Report"),object.getString("Collected_by")
                                    ,object.getString("SC_Received"),object.getString("Estimated_Approved"),object.getString("Dead_Chance_Approval"),object.getString("Serviced_By")
                                    ,object.getString("SC_Delivered"),object.getString("Boy_Collected_on"),object.getString("SC_Received_on"),object.getString("Estimate_App_on")
                                    ,object.getString("Dead_Chance_App_On"),object.getString("Service_Deliverd_on"),object.getString("Boy_Deliverd_on"),object.getString("user_id"),object.getString("status")));

                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        get_delivery_pickup_rec.setItemAnimator(new DefaultItemAnimator());
                        get_delivery_pickup_rec.setLayoutManager(layoutManager);
                        collectorsAdapter = new CollectorsAdapter(getActivity() ,collectorsArrayList );
                        get_delivery_pickup_rec.setAdapter(collectorsAdapter);
//                        try {
//                            NewJobFragment.this.finalize();
//                        } catch (Throwable throwable) {
//                            throwable.printStackTrace();
//                        }
//                        Intent intent = new Intent(getActivity() ,NavigationActivity.class );
//                        startActivity(intent);
//                        getActivity().finish();
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

    @Override
    public void onDestroy() {
        JobId = "";
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        JobId = "";
        super.onDetach();
    }
}