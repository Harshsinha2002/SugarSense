package com.example.sugarsense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Splash_Screen_Diabetes_Risk_Analysis extends AppCompatActivity {

    TextView Doctor_Welcome_Text;
    ImageView Doctor_Image;
    DatabaseReference db;
    String userid;
    String doctor_introduction_text;
    String user_profile_name;
    int char_count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_diabetes_risk_analysis);
        setTitle(null);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            userid = user.getEmail();
        }

        Doctor_Welcome_Text = findViewById(R.id.Doctor_Welcome_Text);
        Doctor_Image = findViewById(R.id.Doctor_Image);

        Doctor_Image.setImageDrawable(getDrawable(R.drawable.doctor_image));
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_right_to_left_animation);
        Doctor_Image.startAnimation(animation);
        db = FirebaseDatabase.getInstance().getReference("User Profile Name");
        db.child(userid.replace(".","")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    if (task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot = task.getResult();
                        user_profile_name = String.valueOf(dataSnapshot.getValue());

                        doctor_introduction_text = "Hello " + user_profile_name + ", I am Mr.Bot, your AI Medical Assistant. I will examine you on the basics of your BMI, Current Body Symptoms, Your Family Medical History and Your Blood Sugar Reports (if present), to analyse the Probability of Diabetes in your body and provide you medical assistance and remedies if needed.";
                        printText(doctor_introduction_text);
                    }
                }
            }
        });
    }

    private void printText(String text)
    {
        if(char_count <= text.length())
        {
            String fetchText = text.substring(0, char_count);
            Doctor_Welcome_Text.setText(fetchText);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    char_count++;
                    printText(text);
                }
            }, 70);
        }

        else
        {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_left_to_right_animation);
                    Doctor_Image.startAnimation(animation);
                    Doctor_Welcome_Text.setText("");
                }
            }, 1000);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    Doctor_Image.setImageDrawable(null);
                    startActivity(new Intent(Splash_Screen_Diabetes_Risk_Analysis.this, Diabetes_Risk_Analysis_Page.class));
                    finish();
                    Animatoo.INSTANCE.animateZoom(Splash_Screen_Diabetes_Risk_Analysis.this);
                }
            }, 2000);
        }
    }
}