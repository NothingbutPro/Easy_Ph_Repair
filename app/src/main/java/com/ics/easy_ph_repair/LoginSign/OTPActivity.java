package com.ics.easy_ph_repair.LoginSign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ics.easy_ph_repair.NavigationActivity;
import com.ics.easy_ph_repair.R;
import com.ics.easy_ph_repair.Session.SessionManager;

public class OTPActivity extends AppCompatActivity {
    Button Verotp;
    EditText otpedt;
    TextView opttxt;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        sessionManager = new SessionManager(this);
        Verotp = findViewById(R.id.verotp);
        otpedt = findViewById(R.id.otpedt);
        opttxt = findViewById(R.id.opttxt);
        opttxt.setText(opttxt.getText().toString()+" "+sessionManager.getMobile());
        Verotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!otpedt.getText().toString().isEmpty())
                {
                    Intent intent = new Intent(view.getContext() , NavigationActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    otpedt.setError("Please Fill an OTP");
                }
            }
        });

    }
}
