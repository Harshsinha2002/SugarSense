package com.example.sugarsense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Health_Risk_Page extends AppCompatActivity {

    int char_count = 0;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_risk_page);

        getWindow().setStatusBarColor(ContextCompat.getColor(Health_Risk_Page.this, R.color.blue));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        setTitle("Health Risks");

        description = findViewById(R.id.txt);
        String text = getString(R.string.health_risks_of_diebetes);

        printText(text);
    }

    public void printText(String text)
    {
        if(char_count <= text.length())
        {
            String fetchText = text.substring(0,char_count);
            description.setText(fetchText);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    char_count++;
                    printText(text);
                }
            }, 140);
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Health_Risk_Page.this, MainActivity.class);
        startActivity(intent);
        Animatoo.INSTANCE.animateDiagonal(Health_Risk_Page.this);
        finish();
    }

}