package com.ics.easy_ph_repair;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.ics.easy_ph_repair.LoginSign.LoginActivity;
import com.ics.easy_ph_repair.LoginSign.UpdateProfile;
import com.ics.easy_ph_repair.Session.SessionManager;
import com.ics.easy_ph_repair.WebUrls.Urls;
import com.ics.easy_ph_repair.ui.CollectUnit.CollectUnitFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class NavigationActivity extends AppCompatActivity {

     public AppBarConfiguration mAppBarConfiguration;
   public  static NavController navController;
    View hView;
    ImageView imageView;
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String str = intent.getStringExtra("Clicked");
                Toast.makeText(context, "Str is"+str, Toast.LENGTH_SHORT).show();
                if(str.equals("AddNewJob")) {


                    navController.navigate(R.id.nav_newjob);
                //    navController.popBackStack();
                    //finish();

                }else if(str.equals("Service_Status"))
                {
//                    finish();
//                    navController.popBackStack();
                    navController.navigate(R.id.nav_service_app);
                  //  navController.popBackStack();
                   // finish();
                }else if(str.equals("collect_unity"))
                {
//                    finish();
//                    navController.popBackStack();
                    navController.navigate(R.id.nav_collecunit);
                 //   navController.popBackStack();
                   // finish();
                }
                else if(str.equals("Service_Complete"))
                {
//                    finish();
//                    navController.popBackStack();
                    navController.navigate(R.id.nav_service);
                 //   navController.popBackStack();
                   // finish();
                }

                else if(str.equals("NotLoggedin")) {
                    //finish();
                //    NavigationActivity.navController = null;

                    Intent intent1 = new Intent(NavigationActivity.this, LoginActivity.class);
                    startActivity(intent1);
//                    navController.popBackStack();
                //    finish();
                }
              //  Toast.makeText(context, "NAv stsart"+navController.getGraph().getParent()., Toast.LENGTH_SHORT).show();
                // get all your data from intent and do what you want
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new GETUSERDATA().execute();
        //local Broad cast
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("Hey_Clicked"));
        //
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
     //   NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.nav_name);
         imageView = (ImageView)hView.findViewById(R.id.imageView);
//        Glide.get(this).getRequestManagerRetriever().get(this.getBaseContext()).
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() , UpdateProfile.class);
                startActivity(intent);
            }
        });
        TextView nav_email= (TextView)hView.findViewById(R.id.nav_emai);
        nav_user.setText(new SessionManager(this).getCoustId());
        nav_email.setText(new SessionManager(this).getUsername());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_newjob, R.id.nav_service_app,
                R.id.nav_logout, R.id.nav_collecunit, R.id.nav_service)
                .setDrawerLayout(drawer)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
             //   Toast.makeText(NavigationActivity.this, "Destination "+controller.getCurrentDestination().getLabel(), Toast.LENGTH_SHORT).show();
            }
        });

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                Toast.makeText(NavigationActivity.this, "clicked at"+menuItem.getTitle(), Toast.LENGTH_SHORT).show();
//                onSupportNavigateUp();
//                drawer.closeDrawer(Gravity.LEFT);
//                return false;
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        Toast.makeText(this, "on support called", Toast.LENGTH_SHORT).show();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        finish();
        navController.popBackStack();
        Intent intent = new Intent(NavigationActivity.this , NavigationActivity.class);
        startActivity(intent);
        finish();
//        navController.getCurrentDestination().removeAction(1);
        super.onBackPressed();
    }

    private class GETUSERDATA extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;



        protected void onPreExecute() {
            dialog = new ProgressDialog(NavigationActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(Urls.Base_Url+""+Urls.Userdata+"/"+new SessionManager(NavigationActivity.this).getWaiterName());

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("email", EMail);
//                postDataParams.put("password", Pass);



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

                    jsonObject = new JSONObject(result).getJSONObject("responce");
                    //  String response = jsonObject.getString("responce");
                    //   Log.e("Response is", response);
                    String massage = jsonObject.getString("user_id");

                    String customer_id = jsonObject.getString("customer_id");
                    Glide.with(NavigationActivity.this)
                            .applyDefaultRequestOptions(RequestOptions.errorOf(R.drawable.boyprofile))
                            .load("http://ihisaab.in/jobcart/uploads/user/"+jsonObject.get("profile_image"))
                            .into(imageView);
//                    Glide.with(NavigationActivity.this).load("http://ihisaab.in/jobcart/uploads/user/"+jsonObject.get("profile_image")).listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            Toast.makeText(NavigationActivity.this, "Failed to load Profile Image", Toast.LENGTH_SHORT).show();
//                            Glide.get(NavigationActivity.this).clearMemory();
////                            Glide.with(NavigationActivity.this).clear(target);
//                            Glide.with(NavigationActivity.this).load(R.drawable.boyprofile);
//                            imageView.setImageResource(R.drawable.boyprofile);
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            return false;
//                        }
//                    }).into(imageView);



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
