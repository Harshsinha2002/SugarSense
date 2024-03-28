package com.example.sugarsense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.sugarsense.ui.home.Home_Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class Assistance_Page extends AppCompatActivity {

    TextView Description_txt;
    TextView See_more_less_txt;
    boolean see_more = true;
    String collapsed_Description_Text = "Dr. Shubhangi is a seasoned general practitioner renowned for her expertise in.....";
    String more_Description_Text = "Dr. Shubhangi is a seasoned general practitioner renowned for her expertise in managing diabetes. With a compassionate approach, she offers personalized care and comprehensive treatment plans to her patients.";
    MaterialCardView Assistance_sms_icon, Call_icon;
    MaterialButton Make_an_appointment;
    private  static  final  int Call_Permission_Code = 1000;
    private  static  final  int Sms_Permission_Code = 44;
    String appointment_message = "I wanted to make an appointment for Tomorrow please register.";
    String assistance_message = "Hello ma'am am in need of diabetes medical assistance please call me up when you are free.";
    boolean send_appointment_message = false;
    boolean send_assistance_message = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistance_page);

        getWindow().setStatusBarColor(ContextCompat.getColor(Assistance_Page.this, R.color.blue));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        setTitle("Medical Assistance");

        Assistance_sms_icon = findViewById(R.id.Sms_icon);
        Call_icon = findViewById(R.id.Call_icon);
        Make_an_appointment = findViewById(R.id.Make_an_appointment_btn);
        Description_txt = findViewById(R.id.Description_txt);
        See_more_less_txt = findViewById(R.id.See_more_less_txt);


        Assistance_sms_icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                send_assistance_message = true;
                send_appointment_message = false;
                sendSms(assistance_message);
            }
        });

        Call_icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                makeCall();
            }
        });

        Make_an_appointment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                send_appointment_message = true;
                send_assistance_message = false;
                sendSms(appointment_message);
            }
        });


        See_more_less_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(see_more == true)
                {
                    see_more = false;
                    Description_txt.setText(more_Description_Text);
                    See_more_less_txt.setTextColor(Color.RED);
                    See_more_less_txt.setText(R.string.see_less);
                }

                else
                {
                    see_more = true;
                    Description_txt.setText(collapsed_Description_Text);
                    See_more_less_txt.setTextColor(Color.BLUE);
                    See_more_less_txt.setText(R.string.see_more);
                }
            }
        });
    }

    public void sendSms(String message)
    {

        if(ContextCompat.checkSelfPermission(Assistance_Page.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Assistance_Page.this, new String[]{Manifest.permission.SEND_SMS}, Sms_Permission_Code);
        }

        else
        {
            String phone = "7011934577";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,message,null,null);
        }
    }

    public void makeCall()
    {
            String number = "7011934577";
            if(ContextCompat.checkSelfPermission(Assistance_Page.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(Assistance_Page.this, new String[]{Manifest.permission.CALL_PHONE}, Call_Permission_Code);
            }

            else
            {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

         if(requestCode == 1000 && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
         {
                makeCall();
         }

        else if(requestCode == 44 && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if(send_appointment_message)
            {
                sendSms(appointment_message);
            }
            else if (send_assistance_message)
            {
                sendSms(assistance_message);
            }
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Assistance_Page.this, MainActivity.class);
        startActivity(intent);
        Animatoo.INSTANCE.animateDiagonal(Assistance_Page.this);
        finish();
    }

}