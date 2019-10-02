package com.ics.easy_ph_repair.Basic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ics.easy_ph_repair.R;

public class Phonedetails extends AppCompatActivity {
    TextView cstxt,mobtid,jbidtxt,fnametxt,modetxt,colotxt,imeitxt,imei2txt,comtxt,quotation,jbdate,accessories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonedetails);
        cstxt = findViewById(R.id.cstxt);
        mobtid = findViewById(R.id.mobtid);
        jbidtxt = findViewById(R.id.jbidtxt);
        fnametxt = findViewById(R.id.fnametxt);
        modetxt = findViewById(R.id.modetxt);
        jbdate = findViewById(R.id.jbdate);
        colotxt = findViewById(R.id.colotxt);
        accessories = findViewById(R.id.accessories);
        imeitxt = findViewById(R.id.imeitxt);
        imei2txt = findViewById(R.id.imei2txt);
        comtxt = findViewById(R.id.comtxt);
        quotation = findViewById(R.id.quotation);
        try {
            cstxt.setText(getIntent().getStringExtra("customer_id"));
            fnametxt.setText(getIntent().getStringExtra("first_name"));
            jbdate.setText(getIntent().getStringExtra("jobdate"));
            jbidtxt.setText(getIntent().getStringExtra("jobid"));
            quotation.setText(getIntent().getStringExtra("quotation_amount"));
            modetxt.setText(getIntent().getStringExtra("model"));
            colotxt.setText(getIntent().getStringExtra("colour"));
            imeitxt.setText(getIntent().getStringExtra("imei_one"));
            comtxt.setText(getIntent().getStringExtra("complant"));
            mobtid.setText(getIntent().getStringExtra("mobile"));
            accessories.setText(getIntent().getStringExtra("accessories"));
        }catch (Exception e)
        {

        }
    }
}
