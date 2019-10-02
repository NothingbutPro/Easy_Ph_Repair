package com.ics.easy_ph_repair.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ics.easy_ph_repair.JavaFiles.ServiceStatusData;
import com.ics.easy_ph_repair.Basic.Phonedetails;
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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ServiceStatusAdapter extends RecyclerView.Adapter<ServiceStatusAdapter.MyViewHolder> {
    public static String JobId;
    private List<ServiceStatusData> modelList;
    String Selecteddel;
    private Context context;

    public ServiceStatusAdapter(Activity activity, List<ServiceStatusData> my_order_modelList) {
        this.context = activity.getBaseContext();
        this.modelList = my_order_modelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_order_no, tv_order_status, tv_order_date, tv_order_rs, tv_item,tv_order_pen;
        LinearLayout appbtn,payli;
        Button tv_accept_btn,tv_reject,tv_btn_pay;
        RadioGroup allraid;
        RadioButton payon ,cashpay;
        public MyViewHolder(View view) {
            super(view);
            tv_order_no = (TextView) view.findViewById(R.id.tv_order_no);
            tv_order_status = (TextView) view.findViewById(R.id.tv_order_status);
            payli = (LinearLayout) view.findViewById(R.id.payli);
            appbtn = (LinearLayout) view.findViewById(R.id.appbtn);
            payon =  view.findViewById(R.id.payon);
            cashpay = view.findViewById(R.id.cashpay);
            allraid =  view.findViewById(R.id.allraid);
            tv_btn_pay =  view.findViewById(R.id.tv_btn_pay);
            tv_order_date = (TextView) view.findViewById(R.id.tv_order_date);
            tv_accept_btn = (Button) view.findViewById(R.id.tv_accept_btn);
            tv_order_rs =  view.findViewById(R.id.tv_order_rs);
            tv_reject = (Button) view.findViewById(R.id.tv_reject);
            tv_order_pen = (TextView) view.findViewById(R.id.tv_order_pen);

        }
    }

    public ServiceStatusAdapter(List<ServiceStatusData> my_order_modelList) {
        this.modelList = my_order_modelList;
    }

    @Override
    public ServiceStatusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serviceapprovalitem, parent, false);

        context = parent.getContext();

        return new ServiceStatusAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServiceStatusAdapter.MyViewHolder holder, int position) {
        final ServiceStatusData my_locatio_model = modelList.get(position);
//        holder.tv_office_add.setText(my_locatio_model.getPg_descri());
        /*My_Locatio_model mList = modelList.get(position);

        holder.tv_office_add.setText(mList.getPg_descri());*/
        if(my_locatio_model.getCheck_status().equals("Pending")) {
            holder.tv_order_pen.setText(my_locatio_model.getCheck_status());
           // holder.tv_order_rs.setText("Payment of Repairing will be "+my_locatio_model.getQuotation_amount() +" rs");
            holder.tv_order_pen.setBackgroundColor(Color.rgb(204,204,0));
            holder.tv_order_status.setVisibility(View.GONE);
        }else  if(my_locatio_model.getCheck_status().equals("Repairing"))
        {
            holder.tv_order_pen.setText(my_locatio_model.getCheck_status());
            holder.tv_order_rs.setText("Payment of Repairing will be "+my_locatio_model.getQuotation_amount() +" rs");
            holder.tv_order_pen.setBackgroundColor(Color.RED);
        }else if(my_locatio_model.getCheck_status().equals("Check"))
        {
            holder.tv_order_pen.setText(my_locatio_model.getCheck_status()+"ed");
            holder.tv_order_pen.setBackgroundColor(Color.BLUE);
            holder.appbtn.setVisibility(View.VISIBLE);
            holder.tv_order_rs.setText("Payment of Repairing will be "+my_locatio_model.getQuotation_amount() +" rs");
            holder.tv_accept_btn.setText("Approve");
            holder.tv_reject.setText("Reject");
//            holder.tv_accept_btn.setText(""+my_locatio_model.get);
        }else if (my_locatio_model.getCheck_status().equals("Completed"))
        {
            holder.tv_order_pen.setText(my_locatio_model.getCheck_status()+"ed");
            holder.tv_order_rs.setText("Payment of Repairing will be "+my_locatio_model.getQuotation_amount() +" rs");
            holder.tv_order_pen.setBackgroundColor(Color.GREEN);
            holder.payli.setVisibility(View.VISIBLE);

        }else if (my_locatio_model.getCheck_status().equals("Paid"))
        {
            holder.tv_order_pen.setText(my_locatio_model.getCheck_status()+"ed");
            holder.tv_order_rs.setText("Paid Amount "+my_locatio_model.getQuotation_amount() +" rs");
            holder.tv_order_pen.setBackgroundColor(Color.GREEN);
//            holder.payli.setVisibility(View.VISIBLE);

        }
        holder.payon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Selecteddel = "";
                holder.allraid.clearCheck();

                Selecteddel = holder.payon.getText().toString();
                Toast.makeText(context, "you have selected "+Selecteddel, Toast.LENGTH_SHORT).show();
//                holder.cashpay.setChecked(false);
            }
        });
        holder.cashpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Selecteddel = "";
                holder.allraid.clearCheck();
                Selecteddel = holder.cashpay.getText().toString();
                Toast.makeText(context, "you have selected "+Selecteddel, Toast.LENGTH_SHORT).show();
//                holder.payon.setChecked(false);
            }
        });
        holder.allraid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //Toast.makeText(context, ""+holder., Toast.LENGTH_SHORT).show();
              //  Toast.makeText(context, "checked id is"+ radioGroup.getChildAt(i), Toast.LENGTH_SHORT).show();
            }
        });
    holder.tv_btn_pay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(holder.payon.isChecked() || holder.cashpay.isChecked())
            {
                new POstPayment(Selecteddel , my_locatio_model.getJobid() , new SessionManager(view.getContext()).getWaiterName() ,holder).execute();
            }
        }
    });
        holder.tv_accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Button closeButton;
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(view.getContext());
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage("Message") .setTitle("Title");

                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure to approve us to start repairing your device, it will cost "+my_locatio_model.getQuotation_amount()+" rs")
                        .setCancelable(false)
                        .setPositiveButton("Yes,Start Repairing", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.e("id is",""+new SessionManager(view.getContext()).getWaiterName());
                                new StartRepairng(my_locatio_model.getJobid(),3,holder).execute();
//                                dialog.dismiss();
//                                Toast.makeText(view.getContext(),"you choose yes action for alertbox",
//                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No,Wait", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button

                                dialog.cancel();
//                                Toast.makeText(view.getContext(),"you choose no action for alertbox",
//                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Attention !");
//                alert.getWindow().get
                alert.show();

        }

        });
        holder.tv_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(view.getContext());
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage("Message") .setTitle("Title");

                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure to reject us to start repairing your device, it is only costing "+my_locatio_model.getQuotation_amount()+" rs")
                        .setCancelable(false)
                        .setPositiveButton("Yes,Reject", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.e("id is",""+new SessionManager(view.getContext()).getWaiterName());
                                new StartRepairng(new SessionManager(view.getContext()).getWaiterName(),0, holder).execute();

                                dialog.dismiss();
//                                Toast.makeText(view.getContext(),"you choose yes action for alertbox",
//                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No,Wait", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                Log.e("id is",""+new SessionManager(view.getContext()).getWaiterName());
                                dialog.cancel();
//                                Toast.makeText(view.getContext(),"you choose no action for alertbox",
//                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Attention !");
//                alert.getWindow().get
                alert.show();


            }
        });
        holder.tv_order_no.setText(my_locatio_model.getJobid());
        holder.tv_order_date.setText(my_locatio_model.getJobdate());
        holder.tv_order_status.setText(my_locatio_model.getMobile());
//        holder.tv_order_status.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(holder.tv_order_status.getText().toString().equals("Tap Here to Assign"))
//                {
////                    CollectUnitFragment fragment = new CollectUnitFragment();
////                    Bundle bundle = new Bundle();
////                    Log.e("jobid" , ""+my_locatio_model.getJobid().toString());
////                    bundle.putString("jid", ""+my_locatio_model.getJobid().toString());
////                    JobId = my_locatio_model.getJobid().toString();
////                    fragment.setArguments(bundle);
////                    view.getContext().get
////                    view.getContext().
//                    ((Activity)context).finish();
////                    navController.popBackStack();
//                    navController.navigate(R.id.nav_collecunit);
//
//                }
//            }
//        });
        holder.tv_order_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() , Phonedetails.class);
                modelList.clear();
                intent.putExtra("jobdate" ,my_locatio_model.getJobdate() );
                intent.putExtra("first_name" ,my_locatio_model.getFirstName() );
                intent.putExtra("quotation_amount" ,my_locatio_model.getQuotation_amount());
                intent.putExtra("quotation_desc" ,my_locatio_model.getQuotation_desc());
                intent.putExtra("jobdate" ,my_locatio_model.getJobdate() );
                intent.putExtra("jobid" ,my_locatio_model.getJobid() );
                intent.putExtra("customer_id" ,my_locatio_model.getCustomerId() );
                intent.putExtra("mobile" ,my_locatio_model.getMobile() );
                intent.putExtra("model" ,my_locatio_model.getModel() );
                intent.putExtra("colour" ,my_locatio_model.getColour() );
                intent.putExtra("imei_one" ,my_locatio_model.getImeiOne() );
                intent.putExtra("complant" ,my_locatio_model.getComplant() );
//                intent.putExtra("quotation_amount" ,my_locatio_model.getComplant() );
                intent.putExtra("accessories" ,my_locatio_model.getAccessories() );
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    private class StartRepairng extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String id;
        int status;
        ServiceStatusAdapter.MyViewHolder  holder;

        public StartRepairng(String id, int status,  ServiceStatusAdapter.MyViewHolder holder) {
            this.id = id;
            this.status = status;
            this.holder = holder;

        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/jobcart/api/approve_working");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("jobid", id);
                postDataParams.put("status",status);



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
                    if(response)
                    {
                        if(status==0) {
                            holder.tv_order_pen.setText("Rejected Repairing ");
                            holder.appbtn.setVisibility(View.GONE);
                        }else {
                            holder.tv_order_pen.setText("Started Repairing ");
                            holder.appbtn.setVisibility(View.GONE);
                        }
                    }
//                    //   Log.e("Response is", response);
//                    String massage = jsonObject.getJSONObject("data").getString("name");



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

    private class POstPayment extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String selecteddel;
        String jobid;
        String sessionManager;
        ServiceStatusAdapter.MyViewHolder myViewHolder;

        public POstPayment(String selecteddel, String jobid, String sessionManager, MyViewHolder holder) {
            this.selecteddel = selecteddel;
            this.jobid = jobid;
            this.sessionManager = sessionManager;
            this.myViewHolder = holder;
        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Payment_status);

                JSONObject postDataParams = new JSONObject();
//                Log.e("user_id is" , ""+new SessionManager(getActivity()).getWaiterName());
//                postDataParams.put("user_id",sessionManager);
                postDataParams.put("jobid",jobid);
                postDataParams.put("selecteddel",selecteddel);


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
                    Boolean jsonObject1 = jsonObject.getBoolean("responce");
//                    JSONObject jsonObject1 = jsonObject.getJSONObject("massage");
                    if(jsonObject1)
                    {
                        //  myViewHolder.tv_order_status.setText("Wait for admin Approval");
                        myViewHolder.allraid.setVisibility(View.GONE);
                        myViewHolder.appbtn.setVisibility(View.GONE);
                        myViewHolder.payli.setVisibility(View.GONE);

                        myViewHolder.tv_order_rs.setText("Wait for admin Approval");
                    }
//                    navController.popBackStack(R.id.nav_service_app ,true);
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
