package com.example.sugarsense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Precautions_Page extends AppCompatActivity {

    int char_count = 0;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precautions_page);

        getWindow().setStatusBarColor(ContextCompat.getColor(Precautions_Page.this, R.color.blue));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        setTitle("Health Precautions");

        description = findViewById(R.id.txt);

        printText("Health Precautions");
    }

    private void printText(String text)
    {
        if(char_count <= text.length())
        {
            String fetchedText = text.substring(0,char_count);
            description.setText(fetchedText);

            new Handler().postDelayed(new Runnable()
            {
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
        Intent intent = new Intent(Precautions_Page.this, MainActivity.class);
        startActivity(intent);
        Animatoo.INSTANCE.animateDiagonal(Precautions_Page.this);
        finish();
    }

}